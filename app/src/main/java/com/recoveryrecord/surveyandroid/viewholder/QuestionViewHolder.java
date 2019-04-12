package com.recoveryrecord.surveyandroid.viewholder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.recoveryrecord.surveyandroid.question.Question;

public abstract class QuestionViewHolder<Q extends Question> extends RecyclerView.ViewHolder {

    private Context mContext;

    public QuestionViewHolder(Context context, @NonNull View itemView) {
        super(itemView);
        mContext = context;
    }

    protected Context getContext() {
        return mContext;
    }

    public abstract void bind(Q question);
}
