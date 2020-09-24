package com.recoveryrecord.surveyandroid;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;

// Slides new questions in from the right.
public class SurveyQuestionItemAnimator extends DefaultItemAnimator {

    @Override
    public boolean animateAppearance(@NonNull RecyclerView.ViewHolder viewHolder, @Nullable ItemHolderInfo preLayoutInfo, @NonNull ItemHolderInfo postLayoutInfo) {
        if (preLayoutInfo == null) {
            preLayoutInfo = new ItemHolderInfo();
            preLayoutInfo.setFrom(viewHolder);
        }
        preLayoutInfo.left = postLayoutInfo.right;
        return this.animateMove(viewHolder, preLayoutInfo.left, preLayoutInfo.top, postLayoutInfo.left, postLayoutInfo.top);
    }
}
