package com.qnmlgb;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.Transformation;

public class ResetHeightAnimation extends Animation{
	private View mParallaxImageView; //图片的view
	private int mTargetHeight; //目标高度(图片控件最初的高度,布局文件里有设置)
	private int mCurrentHeight; //当前高度(当前动画初始化时候取得,手滑动使图片拉伸的最终值)
	
	private int totalValue;  //当前高度和目标还原后的高度 的  差值
	
	
	public ResetHeightAnimation(View parallaxImageView,int targetHeight){
		super();
		
		this.mParallaxImageView = parallaxImageView;
		this.mTargetHeight = targetHeight;
		
		mCurrentHeight  = parallaxImageView.getHeight();
		totalValue = mTargetHeight - mCurrentHeight;  //负数值  当前高度大于 目标高度
		
		setDuration(400);
		setInterpolator(new OvershootInterpolator());
		//Test
	}
	
	/**
	 * 标示动画执行的进度或百分比     (不断调用) 
	 * 
	 *  interpolatedTime : 0- 0.5 - 1
	 */
	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t) {
		super.applyTransformation(interpolatedTime, t);
		
		int newHeight  = (int) (mCurrentHeight+totalValue*interpolatedTime); // 递减的值
		
		mParallaxImageView.getLayoutParams().height = newHeight;  //变化的值 赋值 给 图片控件的view
		
		mParallaxImageView.requestLayout(); //请求重绘imageView
		
	}
	

}
