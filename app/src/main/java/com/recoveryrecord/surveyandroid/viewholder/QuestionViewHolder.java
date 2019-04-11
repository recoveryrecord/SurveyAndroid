package com.recoveryrecord.surveyandroid.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.recoveryrecord.surveyandroid.question.Question;

public abstract class QuestionViewHolder<Q extends Question> extends RecyclerView.ViewHolder {

    public QuestionViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public abstract void bind(Q question);
}
