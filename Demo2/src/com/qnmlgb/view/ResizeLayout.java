package com.qnmlgb.view;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class ResizeLayout extends FrameLayout {
	 
	 
    private OnkeyboardShowListener mChangedListener;
    private boolean misKeyboardshow = false;
    private final int THRESHOLD = 100;
    /**
     * @param context
     * @param attrs
     */
    public ResizeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
     
    public static interface OnkeyboardShowListener {
        public void onKeyboardShow(int keyHeight);
        public void onKeyboardHide();
        public void onKeyboardShowOver();
    }
 
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (oldh - h > THRESHOLD && w==oldw) { //键盘弹出了
            misKeyboardshow = true;
            if (mChangedListener != null) {
                mChangedListener.onKeyboardShow(oldh - h);
            }
        } else if (h - oldh > THRESHOLD && w==oldw) { //键盘隐藏了
            misKeyboardshow = false;
            if (mChangedListener != null) {
                mChangedListener.onKeyboardHide();
            }
        } 
    }
 
    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
            int bottom) {
        super.onLayout(changed, left, top, right, bottom);        
        if (mChangedListener != null && misKeyboardshow) {
            mChangedListener.onKeyboardShowOver();
        }
    }
     
    public boolean isKeyboardShowing() {
        return misKeyboardshow;
    }
 
    public void setOnKeyboardShowListener(OnkeyboardShowListener listener) {
        mChangedListener = listener;
    }
}