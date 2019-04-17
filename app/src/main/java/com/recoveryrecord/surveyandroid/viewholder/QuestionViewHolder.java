package com.recoveryrecord.surveyandroid.viewholder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.recoveryrecord.surveyandroid.R;
import com.recoveryrecord.surveyandroid.question.Question;

public abstract class QuestionViewHolder<Q extends Question> extends RecyclerView.ViewHolder {

    private Context mContext;

    private TextView headerText;
    private TextView questionText;

    public QuestionViewHolder(Context context, @NonNull View itemView) {
        super(itemView);
        mContext = context;

        headerText = itemView.findViewById(R.id.header_text);
        questionText = itemView.findViewById(R.id.question_text);
    }

    protected Context getContext() {
        return mContext;
    }

    public void bind(Q question) {
        resetState();
        headerText.setText(question.header);
        questionText.setText(question.question);
    }

    protected void resetState() {
        headerText.setVisibility(View.VISIBLE);
    }
}
