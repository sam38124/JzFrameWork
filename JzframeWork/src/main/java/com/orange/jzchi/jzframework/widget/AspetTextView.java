package com.orange.jzchi.jzframework.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.orange.jzchi.R;

@SuppressLint("AppCompatCustomView")
public class AspetTextView extends TextView {
    String ratio;
    public AspetTextView(Context context) {
        super(context);
    }

    public AspetTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AspetTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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

    public AspetTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setTextSize(30);
    }
    @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        if(ratio!=null){
//            int width = MeasureSpec.getSize(widthMeasureSpec);
//            int height=MeasureSpec.getSize(heightMeasureSpec);
//            if(ratio.contains("%h")){
//                float sizepercent=Integer.parseInt(ratio.replace("%h", ""));
//                setTextSize(height*sizepercent/100);
//            }else if(ratio.contains("%w")){
//                float sizepercent=Integer.parseInt(ratio.replace("%w", ""));
//                setTextSize(width*sizepercent/100);
//            }
//        }
    }
}
