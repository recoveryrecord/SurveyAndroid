package com.recoveryrecord.surveyandroid;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;

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
