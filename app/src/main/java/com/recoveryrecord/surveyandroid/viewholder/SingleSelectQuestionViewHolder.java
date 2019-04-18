package com.recoveryrecord.surveyandroid.viewholder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.recoveryrecord.surveyandroid.R;
import com.recoveryrecord.surveyandroid.question.Option;
import com.recoveryrecord.surveyandroid.question.OtherOption;
import com.recoveryrecord.surveyandroid.question.SingleSelectQuestion;

public class SingleSelectQuestionViewHolder extends QuestionViewHolder<SingleSelectQuestion> {

    private RadioGroup answerSelector;
    private ViewGroup otherSection;
    private EditText editOther;
    private Button nextButton;

    public SingleSelectQuestionViewHolder(Context context, @NonNull View itemView) {
        super(context, itemView);

        answerSelector = itemView.findViewById(R.id.answer_selector);
        otherSection = itemView.findViewById(R.id.other_section);
        editOther = itemView.findViewById(R.id.edit_other);
        nextButton = itemView.findViewById(R.id.next_button);
    }

    @Override
    public void bind(SingleSelectQuestion question) {
        super.bind(question);
        for (Option option : question.options) {
            final RadioButton radioButton = new RadioButton(getContext());
            radioButton.setText(option.title);
            if (option instanceof OtherOption) {
                radioButton.setTag(R.id.is_other, Boolean.TRUE);
            }
            answerSelector.addView(radioButton);
        }
        answerSelector.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton selectedButton = answerSelector.findViewById(checkedId);
                if (selectedButton.getTag(R.id.is_other) != null && selectedButton.getTag(R.id.is_other) == Boolean.TRUE) {
                    otherSection.setVisibility(View.VISIBLE);
                } else {
                    otherSection.setVisibility(View.GONE);
                }

            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNext();
            }
        });

    }

    @Override
    protected void resetState() {
        super.resetState();
        answerSelector.removeAllViews();
        editOther.setText(null);
    }

    private void onNext() {
        // TODO
        Log.d("SingleSelectQuestion", "Clicked onNext");
    }
}
