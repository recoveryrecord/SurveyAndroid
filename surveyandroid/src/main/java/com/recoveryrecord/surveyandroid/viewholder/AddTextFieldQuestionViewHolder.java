package com.recoveryrecord.surveyandroid.viewholder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import com.recoveryrecord.surveyandroid.QuestionState;
import com.recoveryrecord.surveyandroid.question.AddTextFieldQuestion;

public class AddTextFieldQuestionViewHolder extends QuestionViewHolder<AddTextFieldQuestion> {

    public AddTextFieldQuestionViewHolder(Context context, @NonNull View itemView) {
        super(context, itemView);
    }

    public void bind(AddTextFieldQuestion question, QuestionState questionState) {
        super.bind(question);
        // TODO

    }
}
