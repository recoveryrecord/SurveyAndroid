package com.recoveryrecord.surveyandroid.viewholder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import com.recoveryrecord.surveyandroid.QuestionState;
import com.recoveryrecord.surveyandroid.R;
import com.recoveryrecord.surveyandroid.question.SingleTextAreaQuestion;
import com.recoveryrecord.surveyandroid.util.SimpleTextWatcher;

public class SingleTextAreaQuestionViewHolder extends QuestionViewHolder<SingleTextAreaQuestion> {
    private static final String EDIT_TEXT_KEY = "edit_text";
    private static final String ANSWER_ON_EDIT_UPDATE_KEY = "answer_on_edit_update_key";

    private TextInputLayout answerInputLayout;
    private TextInputEditText answerEdit;
    private TextWatcher editTextWatcher;
    private Button nextButton;

    public SingleTextAreaQuestionViewHolder(Context context, @NonNull View itemView) {
        super(context, itemView);
        answerInputLayout = itemView.findViewById(R.id.answer_input_layout);
        answerEdit = itemView.findViewById(R.id.answer_edit);
        nextButton = itemView.findViewById(R.id.next_button);
    }

    public void bind(SingleTextAreaQuestion singleTextAreaQuestion, final QuestionState questionState) {
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
                if (questionState.getBool(ANSWER_ON_EDIT_UPDATE_KEY, false)) {
                    questionState.setAnswer(s.toString());
                }
            }
        };
        answerEdit.addTextChangedListener(editTextWatcher);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNext(questionState);
            }
        });
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

    private void onNext(QuestionState questionState) {
        // TODO: impl validation
        questionState.setAnswer(answerEdit.getText().toString());
        questionState.put(ANSWER_ON_EDIT_UPDATE_KEY, true);
    }
}
