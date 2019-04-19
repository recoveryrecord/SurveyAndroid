package com.recoveryrecord.surveyandroid.viewholder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.recoveryrecord.surveyandroid.QuestionState;
import com.recoveryrecord.surveyandroid.R;
import com.recoveryrecord.surveyandroid.question.MultiSelectQuestion;
import com.recoveryrecord.surveyandroid.question.Option;
import com.recoveryrecord.surveyandroid.question.OtherOption;

public class MultiSelectQuestionViewHolder extends QuestionViewHolder<MultiSelectQuestion> {

    private ViewGroup answerCheckboxContainer;
    private Button nextButton;
    private SparseArray<View> otherMap = new SparseArray<>();

    public MultiSelectQuestionViewHolder(Context context, @NonNull View itemView) {
        super(context, itemView);

        answerCheckboxContainer = itemView.findViewById(R.id.answer_checkbox_container);
        nextButton = itemView.findViewById(R.id.next_button);
    }

    public void bind(MultiSelectQuestion question, QuestionState questionState) {
        super.bind(question);
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        for (Option option : question.options) {
            final CheckBox checkBox = new CheckBox(getContext());
            checkBox.setText(option.title);
            answerCheckboxContainer.addView(checkBox);
            if (option instanceof OtherOption) {
                View otherSection = layoutInflater.inflate(R.layout.view_other_multi_select, answerCheckboxContainer, false);
                answerCheckboxContainer.addView(otherSection);
                otherMap.put(checkBox.getId(), otherSection);
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        View otherSection = otherMap.get(buttonView.getId());
                        if (otherSection != null) {
                            otherSection.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                        }
                    }
                });
            }
        }
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
        answerCheckboxContainer.removeAllViews();
        otherMap.clear();
    }

    private void onNext() {
        // TODO
        Log.d("MultiSelectQuestion", "Clicked onNext");
    }
}
