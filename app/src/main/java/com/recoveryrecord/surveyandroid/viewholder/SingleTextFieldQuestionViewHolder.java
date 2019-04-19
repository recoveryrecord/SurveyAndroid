package com.recoveryrecord.surveyandroid.viewholder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;

import com.recoveryrecord.surveyandroid.QuestionState;
import com.recoveryrecord.surveyandroid.R;
import com.recoveryrecord.surveyandroid.question.SingleTextFieldQuestion;

public class SingleTextFieldQuestionViewHolder extends QuestionViewHolder<SingleTextFieldQuestion> {

    private TextInputLayout answerInputLayout;
    private TextInputEditText answerEdit;
    private Button nextButton;

    public SingleTextFieldQuestionViewHolder(Context context, @NonNull View itemView) {
        super(context, itemView);

        answerInputLayout = itemView.findViewById(R.id.answer_input_layout);
        answerEdit = itemView.findViewById(R.id.answer_edit);
        answerEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    onNext();
                    return true;
                }
                return false;
            }
        });

        nextButton = itemView.findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNext();
            }
        });
    }

    public void bind(SingleTextFieldQuestion question, QuestionState questionState) {
        super.bind(question);
        answerInputLayout.setHint(question.label);
        if (question.maxChars != null) {
            answerInputLayout.setCounterEnabled(true);
            answerInputLayout.setCounterMaxLength(Integer.valueOf(question.maxChars));
        }
        if (question.input_type != null) {
            answerEdit.setInputType(question.input_type.equals("number") ? InputType.TYPE_CLASS_NUMBER : InputType.TYPE_CLASS_TEXT);
        }
    }

    @Override
    protected void resetState() {
        super.resetState();
        answerEdit.setText(null);
        answerEdit.setInputType(InputType.TYPE_CLASS_TEXT);
        answerInputLayout.setHint(null);
        answerInputLayout.setCounterEnabled(false);

    }

    private void onNext() {
        // TODO
        Log.d("SingleTextField", "Clicked onNext");
    }
}
