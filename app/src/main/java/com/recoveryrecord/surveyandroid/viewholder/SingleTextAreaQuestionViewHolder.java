package com.recoveryrecord.surveyandroid.viewholder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import com.recoveryrecord.surveyandroid.Answer;
import com.recoveryrecord.surveyandroid.QuestionState;
import com.recoveryrecord.surveyandroid.R;
import com.recoveryrecord.surveyandroid.question.SingleTextAreaQuestion;
import com.recoveryrecord.surveyandroid.question.Validation;
import com.recoveryrecord.surveyandroid.util.KeyboardUtil;
import com.recoveryrecord.surveyandroid.util.SimpleTextWatcher;
import com.recoveryrecord.surveyandroid.validation.ValidationResult;
import com.recoveryrecord.surveyandroid.validation.Validator;

import java.util.ArrayList;

public class SingleTextAreaQuestionViewHolder extends QuestionViewHolder<SingleTextAreaQuestion> {
    private static final String EDIT_TEXT_KEY = "edit_text";
    private static final String HAS_BEEN_ANSWERED_KEY = "has_been_answered_key";

    private Validator mValidator;

    private TextInputLayout answerInputLayout;
    private TextInputEditText answerEdit;
    private TextWatcher editTextWatcher;
    private Button nextButton;

    public SingleTextAreaQuestionViewHolder(Context context, @NonNull View itemView, Validator validator) {
        super(context, itemView);
        mValidator = validator;

        answerInputLayout = itemView.findViewById(R.id.answer_input_layout);
        answerEdit = itemView.findViewById(R.id.answer_edit);
        nextButton = itemView.findViewById(R.id.next_button);
    }

    private Validator getValidator() {
        return mValidator;
    }

    public void bind(final SingleTextAreaQuestion singleTextAreaQuestion, final QuestionState questionState) {
        super.bind(singleTextAreaQuestion);
        if (singleTextAreaQuestion.maxChars != null) {
            answerInputLayout.setCounterEnabled(true);
            answerInputLayout.setCounterMaxLength(Integer.valueOf(singleTextAreaQuestion.maxChars));
        }
        answerEdit.setText(questionState.getString(EDIT_TEXT_KEY));
        editTextWatcher = new SimpleTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                questionState.put(EDIT_TEXT_KEY, s.toString());
                if (hasBeenAnswered(questionState)) {
                    questionState.setAnswer(new Answer(s.toString()));
                }
            }
        };
        answerEdit.addTextChangedListener(editTextWatcher);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNext(questionState, singleTextAreaQuestion.validations);
            }
        });
        if (!hasBeenAnswered(questionState)) {
            answerEdit.requestFocus();
            KeyboardUtil.showKeyboardDelayed(getContext(), answerEdit, 500L);
        }
    }

    @Override
    protected void resetState() {
        super.resetState();
        if (editTextWatcher != null) {
            answerEdit.removeTextChangedListener(editTextWatcher);
        }
        answerEdit.setText(null);
        answerInputLayout.setCounterEnabled(false);
        nextButton.setOnClickListener(null);
    }

    private void onNext(QuestionState questionState, ArrayList<Validation> validations) {
        String answerStr = answerEdit.getText() != null ? answerEdit.getText().toString() : null;
        if (getValidator() == null && validations != null && !validations.isEmpty()) {
            throw new IllegalStateException("No validator available for validations");
        }
        ValidationResult validationResult = getValidator() == null ?
                ValidationResult.success() :
                getValidator().validate(validations, answerStr);
        if (validationResult.isValid) {
            questionState.setAnswer(new Answer(answerStr));
            setHasBeenAnswered(questionState);
            answerEdit.clearFocus();
            KeyboardUtil.hideKeyboard(getContext(), answerEdit);
        } else {
            getValidator().validationFailed(validationResult.failedMessage);
            answerEdit.requestFocus();
            KeyboardUtil.showKeyboard(getContext(), answerEdit);
        }
    }

    // Returns true if the answer for this has been set before
    private boolean hasBeenAnswered(QuestionState questionState) {
        return questionState.getBool(HAS_BEEN_ANSWERED_KEY, false);
    }

    private void setHasBeenAnswered(QuestionState questionState) {
        questionState.put(HAS_BEEN_ANSWERED_KEY, true);
    }
}
