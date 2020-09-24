package com.recoveryrecord.surveyandroid.viewholder;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import com.recoveryrecord.surveyandroid.QuestionState;
import com.recoveryrecord.surveyandroid.question.DatePickerQuestion;

public class DatePickerQuestionViewHolder extends QuestionViewHolder<DatePickerQuestion> {

    public DatePickerQuestionViewHolder(Context context, @NonNull View itemView) {
        super(context, itemView);
    }

    public void bind(DatePickerQuestion question, QuestionState questionState) {
        super.bind(question);
        // TODO
    }
}
