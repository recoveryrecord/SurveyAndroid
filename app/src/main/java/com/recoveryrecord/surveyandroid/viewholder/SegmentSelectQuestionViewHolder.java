package com.recoveryrecord.surveyandroid.viewholder;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.AttrRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.recoveryrecord.surveyandroid.QuestionState;
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

    public void bind(SegmentSelectQuestion question, QuestionState questionState) {
        super.bind(question);
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        for (int i = 0; i < question.values.size(); i++) {
            RadioButton  radioButton = (RadioButton) layoutInflater.inflate(R.layout.radio_button_segment, segmentSelector, false);
            @AttrRes int backgroundAttr;
            if (i == 0) {
                backgroundAttr = R.attr.firstSegmentBackground;
            } else if (i + 1 == question.values.size()) {
                backgroundAttr = R.attr.lastSegmentBackground;
            } else {
                backgroundAttr = R.attr.middleSegmentBackground;
            }
            TypedValue typedValue = new TypedValue();
            Resources.Theme theme = getContext().getTheme();
            theme.resolveAttribute(backgroundAttr, typedValue, true);
            radioButton.setBackgroundResource(typedValue.resourceId);
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
