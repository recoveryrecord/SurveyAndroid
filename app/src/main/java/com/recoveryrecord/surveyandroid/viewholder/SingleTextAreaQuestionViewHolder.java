package com.recoveryrecord.surveyandroid.viewholder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.recoveryrecord.surveyandroid.R;
import com.recoveryrecord.surveyandroid.question.SingleTextAreaQuestion;

public class SingleTextAreaQuestionViewHolder extends QuestionViewHolder<SingleTextAreaQuestion> {

    private TextInputLayout answerInputLayout;
    private TextInputEditText answerEdit;
    private Button nextButton;

    public SingleTextAreaQuestionViewHolder(Context context, @NonNull View itemView) {
        super(context, itemView);
        answerInputLayout = itemView.findViewById(R.id.answer_input_layout);
        answerEdit = itemView.findViewById(R.id.answer_edit);
        nextButton = itemView.findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNext();
            }
        });
    }

    @Override
    public void bind(SingleTextAreaQuestion singleTextAreaQuestion) {
        super.bind(singleTextAreaQuestion);
        if (singleTextAreaQuestion.maxChars != null) {
            answerInputLayout.setCounterEnabled(true);
            answerInputLayout.setCounterMaxLength(Integer.valueOf(singleTextAreaQuestion.maxChars));
        }
    }

    @Override
    protected void resetState() {
        super.resetState();
        answerEdit.setText(null);
        answerInputLayout.setCounterEnabled(false);
    }

    private void onNext() {
        // TODO
        Log.d("SingleTextArea", "Clicked onNext");
    }
}
