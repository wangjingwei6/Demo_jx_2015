package com.qnmlgb.view;


import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;

@SuppressLint("NewApi")
public class ScaleLinearLayout extends LinearLayout implements View.OnTouchListener {

	private ScaleAnimation scaleAnimation = new ScaleAnimation(1.2f, 1f, 1.2f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
	private ScaleAnimation scaleAnimation1 = new ScaleAnimation(1, 1.2f, 1, 1.2f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
	private OnLayoutClickListener mOnClickListener;

	@SuppressLint("ClickableViewAccessibility")
	public ScaleLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		setOnTouchListener(this);
		// TODO Auto-generated constructor stub
	}

	public ScaleLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		setOnTouchListener(this);
	}

	public ScaleLinearLayout(Context context) {
		super(context);
		setOnTouchListener(this);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 播放动画
	 * 
	 * @param view
	 */
	private void startScaleDownAnimation(View view) {
		scaleAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
		scaleAnimation.setDuration(50);
		scaleAnimation.setFillAfter(true);
		view.startAnimation(scaleAnimation);
	}

	/**
	 * 播放动画
	 * 
	 * @param view
	 */
	private void startScaleUpAnimation(View view) {
		scaleAnimation1.setInterpolator(new AccelerateDecelerateInterpolator());
		scaleAnimation1.setDuration(50);
		scaleAnimation1.setFillAfter(true);
		view.startAnimation(scaleAnimation1);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			startScaleUpAnimation(this);
			return true;
		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			startScaleDownAnimation(this);
			mOnClickListener.onLayoutClick(this);
		} else if (event.getAction() == MotionEvent.ACTION_CANCEL) {
			startScaleDownAnimation(this);
			return true;
		}

		return false;

	}

	
	public interface OnLayoutClickListener {

		public void onLayoutClick(View v);
	}


	public void setOnLayoutClickListener(OnLayoutClickListener onClickListener) {
		this.mOnClickListener = onClickListener;
	}

}
