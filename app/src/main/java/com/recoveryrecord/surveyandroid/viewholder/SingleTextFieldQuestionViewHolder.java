package com.recoveryrecord.surveyandroid.viewholder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;

import com.recoveryrecord.surveyandroid.QuestionState;
import com.recoveryrecord.surveyandroid.R;
import com.recoveryrecord.surveyandroid.question.SingleTextFieldQuestion;
import com.recoveryrecord.surveyandroid.question.Validation;
import com.recoveryrecord.surveyandroid.util.SimpleTextWatcher;
import com.recoveryrecord.surveyandroid.validation.ValidationResult;
import com.recoveryrecord.surveyandroid.validation.Validator;

import java.util.ArrayList;

public class SingleTextFieldQuestionViewHolder extends QuestionViewHolder<SingleTextFieldQuestion> {
    private static final String EDIT_TEXT_KEY = "edit_text";
    private static final String ANSWER_ON_EDIT_UPDATE_KEY = "answer_on_edit_update_key";

    private Validator mValidator;

    private TextInputLayout answerInputLayout;
    private TextInputEditText answerEdit;
    private TextWatcher editTextWatcher;
    private Button nextButton;

    public SingleTextFieldQuestionViewHolder(Context context, @NonNull View itemView, Validator validator) {
        super(context, itemView);
        mValidator = validator;

        answerInputLayout = itemView.findViewById(R.id.answer_input_layout);
        answerEdit = itemView.findViewById(R.id.answer_edit);
        nextButton = itemView.findViewById(R.id.next_button);
    }

    private Validator getValidator() {
        return mValidator;
    }

    public void bind(final SingleTextFieldQuestion question, final QuestionState questionState) {
        super.bind(question);
        answerInputLayout.setHint(question.label);
        if (question.maxChars != null) {
            answerInputLayout.setCounterEnabled(true);
            answerInputLayout.setCounterMaxLength(Integer.valueOf(question.maxChars));
        }
        if (question.input_type != null) {
            answerEdit.setInputType(question.input_type.equals("number") ? InputType.TYPE_CLASS_NUMBER : InputType.TYPE_CLASS_TEXT);
        }
        answerEdit.setText(questionState.getString(EDIT_TEXT_KEY));
        editTextWatcher = new SimpleTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                questionState.put(EDIT_TEXT_KEY, s.toString());
                if (questionState.getBool(ANSWER_ON_EDIT_UPDATE_KEY, false)) {
                    questionState.setAnswer(s.toString());
                }
            }
        };
        answerEdit.addTextChangedListener(editTextWatcher);
        answerEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    onNext(questionState, question.validations);
                    return true;
                }
                return false;
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNext(questionState, question.validations);
            }
        });
    }

    @Override
    protected void resetState() {
        super.resetState();
        if (editTextWatcher != null) {
            answerEdit.removeTextChangedListener(editTextWatcher);
        }
        answerEdit.setOnEditorActionListener(null);
        answerEdit.setText(null);
        answerEdit.setInputType(InputType.TYPE_CLASS_TEXT);
        answerInputLayout.setHint(null);
        answerInputLayout.setCounterEnabled(false);
        nextButton.setOnClickListener(null);
    }

    private void onNext(QuestionState questionState, ArrayList<Validation> validations) {
        String answer = answerEdit.getText() != null ? answerEdit.getText().toString() : null;
        if (getValidator() == null && validations != null && !validations.isEmpty()) {
            throw new IllegalStateException("No validator available for validations");
        }
        ValidationResult validationResult = getValidator() == null ?
                ValidationResult.success() :
                getValidator().validate(validations, answer);
        if (validationResult.isValid) {
            questionState.setAnswer(answer);
            questionState.put(ANSWER_ON_EDIT_UPDATE_KEY, true);
        } else {
            getValidator().validationFailed(validationResult.failedMessage);
        }
    }
}
