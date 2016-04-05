package com.example.chen.adapter.weidgt;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.example.chen.adapter.adapter.LeftDelteAdapter;

/**
 * Created by fulin on 2016/4/5 0005.
 */
public class MyLinearLayout extends LinearLayout {
    private LeftDelteAdapter adapter;
    public MyLinearLayout(Context context) {
        super(context);
    }

    public MyLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LeftDelteAdapter getTagAdapter() {
        return adapter;
    }

    public void setTagAdapter(LeftDelteAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (adapter!=null){
            if (adapter.pos!=-1){
                adapter.closeAllItems();
                return true;
            }
        }
        return super.dispatchTouchEvent(ev);
    }
}
