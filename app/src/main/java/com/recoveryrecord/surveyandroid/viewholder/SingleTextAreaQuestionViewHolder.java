package com.recoveryrecord.surveyandroid.viewholder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.recoveryrecord.surveyandroid.R;
import com.recoveryrecord.surveyandroid.question.SingleTextAreaQuestion;

public class SingleTextAreaQuestionViewHolder extends QuestionViewHolder<SingleTextAreaQuestion> {

    private EditText textArea;
    private Button nextButton;

    public SingleTextAreaQuestionViewHolder(Context context, @NonNull View itemView) {
        super(context, itemView);
        textArea = itemView.findViewById(R.id.area_text);
        nextButton = itemView.findViewById(R.id.next_button);
    }

    @Override
    public void bind(SingleTextAreaQuestion singleTextAreaQuestion) {
        super.bind(singleTextAreaQuestion);
    }

    @Override
    protected void resetState() {
        super.resetState();
        textArea.setText(null);
    }
}
