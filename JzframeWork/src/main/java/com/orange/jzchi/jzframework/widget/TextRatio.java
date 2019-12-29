package com.orange.jzchi.jzframework.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.orange.jzchi.R;

@SuppressLint("AppCompatCustomView")
public class TextRatio extends TextView {
    String ratio;
    int width;
    int height;
    public TextRatio(Context context) {
        super(context);
    }

    public TextRatio(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.CustomTitleView,
                0, 0);

        try {
            ratio=a.getString(R.styleable.CustomTitleView_TextSizeRatio);
        } finally {
            a.recycle();
        }
    }

    public TextRatio(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomTitleView, defStyleAttr, 0);
        int n = typedArray.getIndexCount();
        for (int i = 0; i < n; i++) {
            int arr = typedArray.getIndex(i);//获得单个属性值
            //文字
            if (arr == R.styleable.CustomTitleView_TextSizeRatio) {
                ratio = typedArray.getString(arr);
            }
        }
        typedArray.recycle();
    }

    public TextRatio(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        Log.e("TextRatio:", ""+ratio);
        if(ratio!=null){
            if(ratio.contains("%h")){
                float sizepercent=Integer.parseInt(ratio.replace("%h", ""));
                setTextSize(height*sizepercent/100);
            }else if(ratio.contains("%w")){
                float sizepercent=Integer.parseInt(ratio.replace("%w", ""));
                setTextSize(width*sizepercent/100);
            }
        }
        super.onDraw(canvas);
    }
    @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if(ratio!=null){
            width = MeasureSpec.getSize(widthMeasureSpec);
             height=MeasureSpec.getSize(heightMeasureSpec);
        }
    }
}
