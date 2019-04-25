package com.recoveryrecord.surveyandroid;

import android.content.Context;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;

public class DefaultOnSurveyStateChangedListener implements OnSurveyStateChangedListener {
    private static final String TAG = DefaultOnSurveyStateChangedListener.class.getSimpleName();

    private Context mContext;
    private RecyclerView mRecyclerView;
    private LinearSmoothScroller mSmoothScroller;


    public DefaultOnSurveyStateChangedListener(Context context, RecyclerView recyclerView) {
        mContext = context;
        mRecyclerView = recyclerView;
        mSmoothScroller = new LinearSmoothScroller(getContext()) {
            @Override
            protected int getVerticalSnapPreference() {
                return LinearSmoothScroller.SNAP_TO_START;
            }

            @Override
            protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                return 100F  / (float)displayMetrics.densityDpi;
            }
        };
    }

    private Context getContext() {
        return mContext;
    }

    private RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    private RecyclerView.Adapter getAdapter() {
        return getRecyclerView().getAdapter();
    }

    private RecyclerView.LayoutManager getLayoutManager() {
        return getRecyclerView().getLayoutManager();
    }

    @Override
    public void questionInserted(final int adapterPosition) {
        if (getAdapter() == null) {
            Log.e(TAG, "Adapter is null during questionInserted");
            return;
        }
        if (adapterPosition > 0) {
            // This fixes the ItemDecoration "footer"
            getAdapter().notifyItemChanged(adapterPosition - 1, Boolean.FALSE);
        }
        getAdapter().notifyItemInserted(adapterPosition);
        mSmoothScroller.setTargetPosition(adapterPosition);
        // This ensures that the keyboard finishes hiding before we start scrolling
        getRecyclerView().post(new Runnable() {
            @Override
            public void run() {
                if (getLayoutManager() != null) {
                    getLayoutManager().startSmoothScroll(mSmoothScroller);
                }
            }
        });
    }

    @Override
    public void questionRemoved(int adapterPosition) {
        if (getAdapter() == null) {
            Log.e(TAG, "Adapter is null during questionRemoved");
            return;
        }
        if (adapterPosition > 0) {
            // This fixes the ItemDecoration "footer"
            getAdapter().notifyItemChanged(adapterPosition - 1, Boolean.FALSE);
        }
        getAdapter().notifyItemRemoved(adapterPosition);
    }

    @Override
    public void submitButtonInserted(int adapterPosition) {
        if (getAdapter() == null) {
            Log.e(TAG, "Adapter is null during submitButtonInserted");
            return;
        }
        if (adapterPosition > 0) {
            // This fixes the ItemDecoration "footer"
            getAdapter().notifyItemChanged(adapterPosition - 1, Boolean.FALSE);
        }
        getAdapter().notifyItemInserted(adapterPosition);
    }
}
