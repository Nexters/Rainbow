package com.nexters.rainbow.rainbowcouple.common.widget;

import android.content.Context;
import android.util.AttributeSet;

public class AppCompatEditText extends android.support.v7.widget.AppCompatEditText {

    public AppCompatEditText(Context context) {
        super(context);
    }

    public AppCompatEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AppCompatEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public String getString() {
        return String.valueOf(this.getText());
    }
}
