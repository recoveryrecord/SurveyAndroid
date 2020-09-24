package com.recoveryrecord.surveyandroid.viewholder;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        if (TextUtils.isEmpty(question.header)) {
            headerText.setVisibility(View.GONE);
        } else {
            headerText.setText(question.header);
        }
        questionText.setText(question.question);
    }

    protected void resetState() {
        headerText.setVisibility(View.VISIBLE);
    }
}
