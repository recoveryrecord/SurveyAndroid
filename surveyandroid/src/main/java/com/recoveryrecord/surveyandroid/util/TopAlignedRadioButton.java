package com.recoveryrecord.surveyandroid.util;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatRadioButton;

public class TopAlignedRadioButton extends AppCompatRadioButton {

    private static final int EXTRA_LEFT_PADDING_DP = -2;

    private static final float MULTI_LINE_DRAWABLE_VERTICAL_CORRECTION_DP = -5.71428f;

    public TopAlignedRadioButton(Context context) {
        super(context);
    }

    public TopAlignedRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TopAlignedRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable buttonDrawable = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            buttonDrawable = getButtonDrawable();
        }
        if (buttonDrawable == null) {
            super.onDraw(canvas);
            return;
        }
        final int drawableHeight = buttonDrawable.getIntrinsicHeight();
        final int drawableWidth = buttonDrawable.getIntrinsicWidth();

        int top = 0;

        if (getLineCount() > 1) {
            top = (int)convertDpToPx(getContext(), MULTI_LINE_DRAWABLE_VERTICAL_CORRECTION_DP);
        }

        int bottom = top + drawableHeight;
        int left = (int)convertDpToPx(getContext(), EXTRA_LEFT_PADDING_DP);
        int right = left + drawableWidth;

        buttonDrawable.setBounds(left, top, right, bottom);
        buttonDrawable.draw(canvas);

        // We don't want CompoundButton to redraw the same drawable, but if we set it to null
        // the TextView won't draw in the proper place.  So we copy and set it to transparent.
        Drawable buttonDrawableCopy = buttonDrawable.getConstantState().newDrawable().mutate();
        buttonDrawableCopy.setAlpha(0);
        setButtonDrawable(buttonDrawableCopy);
        super.onDraw(canvas);
        setButtonDrawable(buttonDrawable);

        final Drawable background = getBackground();
        if (background != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                background.setHotspotBounds(left, top, right, bottom);
            }
        }
    }

    public float convertDpToPx(Context context, float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }
}
