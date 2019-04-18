package com.recoveryrecord.surveyandroid.viewholder;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.recoveryrecord.surveyandroid.R;
import com.recoveryrecord.surveyandroid.question.SegmentSelectQuestion;

public class SegmentSelectQuestionViewHolder extends QuestionViewHolder<SegmentSelectQuestion> {

    private RadioGroup segmentSelector;
    private ViewGroup tagContainer;
    private TextView lowTagText;
    private TextView highTagText;

    public SegmentSelectQuestionViewHolder(Context context, @NonNull View itemView) {
        super(context, itemView);

        segmentSelector = itemView.findViewById(R.id.segment_selector);
        tagContainer = itemView.findViewById(R.id.tag_container);
        lowTagText = itemView.findViewById(R.id.low_tag);
        highTagText = itemView.findViewById(R.id.high_tag);
    }

    @Override
    public void bind(SegmentSelectQuestion question) {
        super.bind(question);
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        for (int i = 0; i < question.values.size(); i++) {
            RadioButton  radioButton = (RadioButton) layoutInflater.inflate(R.layout.radio_button_segment, segmentSelector, false);
            @DrawableRes int background;
            if (i == 0) {
                background = R.drawable.first_segment_background;
            } else if (i + 1 == question.values.size()) {
                background = R.drawable.last_segment_background;
            } else {
                background = R.drawable.middle_segment_background;
            }
            radioButton.setBackgroundResource(background);
            radioButton.setText(question.values.get(i));
            segmentSelector.addView(radioButton);
        }
        tagContainer.setVisibility(question.lowTag != null || question.highTag != null ? View.VISIBLE : View.GONE);
        lowTagText.setText(question.lowTag);
        highTagText.setText(question.highTag);
    }

    @Override
    protected void resetState() {
        super.resetState();
        segmentSelector.removeAllViews();
    }
}
