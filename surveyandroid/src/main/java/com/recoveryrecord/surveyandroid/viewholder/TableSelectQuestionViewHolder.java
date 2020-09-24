package com.recoveryrecord.surveyandroid.viewholder;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import com.recoveryrecord.surveyandroid.QuestionState;
import com.recoveryrecord.surveyandroid.question.TableSelectQuestion;

public class TableSelectQuestionViewHolder extends QuestionViewHolder<TableSelectQuestion> {

    public TableSelectQuestionViewHolder(Context context, @NonNull View itemView) {
        super(context, itemView);
    }

    public void bind(TableSelectQuestion question, QuestionState questionState) {
        super.bind(question);
        // TODO
    }
}
