package com.recoveryrecord.surveyandroid;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SpaceOnLastItemDecoration extends RecyclerView.ItemDecoration {

    private int mExtraBottomSpace;

    public SpaceOnLastItemDecoration(int extraBottomSpace) {
        mExtraBottomSpace = extraBottomSpace;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int itemPosition = parent.getChildAdapterPosition(view);
        int itemCount = state.getItemCount();

        if (itemCount > 0 && itemPosition == itemCount - 1) {
            outRect.bottom = mExtraBottomSpace;
        }
    }
}
