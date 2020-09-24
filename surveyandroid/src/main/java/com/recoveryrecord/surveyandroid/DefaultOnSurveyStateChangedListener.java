package com.recoveryrecord.surveyandroid;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import com.recoveryrecord.surveyandroid.util.KeyboardUtil;

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
        if (adapterPosition < getAdapter().getItemCount() - 1) {
            // Only animate if this is a brand-new question
            getAdapter().notifyItemChanged(adapterPosition, Boolean.FALSE);
            return;
        }
        getAdapter().notifyItemInserted(adapterPosition);
        mSmoothScroller.setTargetPosition(adapterPosition);
        KeyboardUtil.hideKeyboard(getContext(), getRecyclerView());
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
    public void questionsRemoved(int startAdapterPosition, int itemCount) {
        if (getAdapter() == null) {
            Log.e(TAG, "Adapter is null during questionRemoved");
            return;
        }
        if (startAdapterPosition > 0) {
            // This fixes the ItemDecoration "footer"
            getAdapter().notifyItemChanged(startAdapterPosition - 1, Boolean.FALSE);
        }
        getAdapter().notifyItemRangeRemoved(startAdapterPosition, itemCount);
    }

    @Override
    public void questionChanged(int adapterPosition) {
        if (getAdapter() == null) {
            Log.e(TAG, "Adapter is null during questionChanged");
            return;
        }
        getAdapter().notifyItemChanged(adapterPosition);
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

    /**
     * This only works if the LayoutManager is a subclass of LinearLayoutManager
     * @return true if scroll was successful (it will return false if already at the top)
     */
    @Override
    public boolean scrollBackOneQuestion() {
        if (!(getLayoutManager() instanceof LinearLayoutManager)) {
            return false;
        }
        LinearLayoutManager llm = (LinearLayoutManager) getLayoutManager();
        int completelyVisiblePosition = llm.findFirstCompletelyVisibleItemPosition();
        int adapterPosition = completelyVisiblePosition == RecyclerView.NO_POSITION ?
                    llm.findFirstVisibleItemPosition() - 1 :
                    completelyVisiblePosition - 1;
        if (adapterPosition < 0) {
            return false;
        }
        mSmoothScroller.setTargetPosition(adapterPosition);
        KeyboardUtil.hideKeyboard(getContext(), getRecyclerView());
        // This ensures that the keyboard finishes hiding before we start scrolling
        getRecyclerView().post(new Runnable() {
            @Override
            public void run() {
                if (getLayoutManager() != null) {
                    getLayoutManager().startSmoothScroll(mSmoothScroller);
                }
            }
        });
        return true;
    }
}
