package com.recoveryrecord.surveyandroid.util;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.AppCompatRadioButton;
import android.util.AttributeSet;

public class TopAlignedRadioButton extends AppCompatRadioButton {
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
        int bottom = top + drawableHeight;
        int left = 0;

        buttonDrawable.setBounds(left, top, drawableWidth, bottom);
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
                background.setHotspotBounds(left, top, drawableWidth, bottom);
            }
        }
    }
}
