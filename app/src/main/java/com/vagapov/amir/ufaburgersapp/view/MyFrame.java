package com.vagapov.amir.ufaburgersapp.view;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewParent;
import android.widget.FrameLayout;



public class MyFrame extends FrameLayout {



    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(ev.getActionMasked() == MotionEvent.ACTION_DOWN){
            ViewParent p = getParent();
            if(p != null){
                p.requestDisallowInterceptTouchEvent(true);
            }
        }
        return false;
    }

    public MyFrame(@NonNull Context context) {
        super(context);
    }

    public MyFrame(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyFrame(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MyFrame(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
