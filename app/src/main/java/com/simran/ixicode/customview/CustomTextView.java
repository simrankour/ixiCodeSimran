package com.simran.ixicode.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.simran.ixicode.MyApplication;
import com.simran.ixicode.R;


public class CustomTextView extends android.support.v7.widget.AppCompatTextView {
    public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CustomTextView(Context context) {
        super(context);
        init(null);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CustomView);
            String fontName = a.getString(R.styleable.CustomView_fontName);
            Typeface myTypeface = null;
            if (fontName != null) {
                myTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/" + fontName);
                setTypeface(myTypeface);
            } else {
                myTypeface = MyApplication.myTypeface;
//                myTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/OpenSans-Regular.ttf");
//                myTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Regular.ttf");
                setTypeface(myTypeface);
            }
            a.recycle();
        }
    }
}