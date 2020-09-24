package com.recoveryrecord.surveyandroid.viewholder;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import com.recoveryrecord.surveyandroid.QuestionState;
import com.recoveryrecord.surveyandroid.question.DynamicLabelTextFieldQuestion;

public class DynamicLabelTextFieldQuestionViewHolder extends QuestionViewHolder<DynamicLabelTextFieldQuestion> {

    public DynamicLabelTextFieldQuestionViewHolder(Context context, @NonNull View itemView) {
        super(context, itemView);
    }

    public void bind(DynamicLabelTextFieldQuestion question, QuestionState questionState) {
        super.bind(question);
        // TODO
    }
}
