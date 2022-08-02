package com.orange.jzchi.jzframework.Widget;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatImageView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

/**
 * 自定义的view，能够随意拖动。
 */

public class DragImageView extends AppCompatImageView {
    public DragImageView(Context context) {
        super(context);
    }

    public DragImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DragImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private float downX;
    private float downY;

    private long downTime;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        if (this.isEnabled()) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    downX = event.getX();
                    downY = event.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    final float xDistance = event.getX() - downX;
                    final float yDistance = event.getY() - downY;
                    if (xDistance != 0 && yDistance != 0) {
                        int l = (int) (getLeft() + xDistance);
                        int r = (int) (getRight() + xDistance);
                        int t = (int) (getTop() + yDistance);
                        int b = (int) (getBottom() + yDistance);
                        this.layout(l, t, r, b);

                    }
                    break;
                case MotionEvent.ACTION_UP:
                    setPressed(false);
                    break;
                case MotionEvent.ACTION_CANCEL:
                    setPressed(false);
                    break;
            }
            return true;
        }
        return false;
    }

}