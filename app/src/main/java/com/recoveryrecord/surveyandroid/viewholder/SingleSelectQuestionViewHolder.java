package com.recoveryrecord.surveyandroid.viewholder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.recoveryrecord.surveyandroid.R;
import com.recoveryrecord.surveyandroid.question.Option;
import com.recoveryrecord.surveyandroid.question.OtherOption;
import com.recoveryrecord.surveyandroid.question.SingleSelectQuestion;

public class SingleSelectQuestionViewHolder extends QuestionViewHolder<SingleSelectQuestion> {

    private TextView headerText;
    private TextView questionText;
    private RadioGroup answerSelector;
    private ViewGroup otherSection;
    private EditText editOther;
    private Button nextButton;

    public SingleSelectQuestionViewHolder(Context context, @NonNull View itemView) {
        super(context, itemView);

        headerText = itemView.findViewById(R.id.header_text);
        questionText = itemView.findViewById(R.id.question_text);
        answerSelector = itemView.findViewById(R.id.answer_selector);
        otherSection = itemView.findViewById(R.id.other_section);
        editOther = itemView.findViewById(R.id.edit_other);
        nextButton = itemView.findViewById(R.id.next_button);
    }

    @Override
    public void bind(SingleSelectQuestion question) {
        headerText.setText(question.header);
        questionText.setText(question.question);
        for (Option option : question.options) {
            final RadioButton radioButton = new RadioButton(getContext());
            radioButton.setText(option.title);
            if (option instanceof OtherOption) {
                radioButton.setId(R.id.other_button);
            }
            answerSelector.addView(radioButton);
        }
        answerSelector.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                otherSection.setVisibility(checkedId == R.id.other_button ? View.VISIBLE : View.GONE);
            }
        });
    }
}
