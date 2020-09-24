package com.recoveryrecord.surveyandroid.viewholder;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import com.recoveryrecord.surveyandroid.QuestionState;
import com.recoveryrecord.surveyandroid.question.YearPickerQuestion;

public class YearPickerQuestionViewHolder extends QuestionViewHolder<YearPickerQuestion> {

    public YearPickerQuestionViewHolder(Context context, @NonNull View itemView) {
        super(context, itemView);
    }

    public void bind(YearPickerQuestion question, QuestionState questionState) {
        super.bind(question);
        // TODO
    }
}
