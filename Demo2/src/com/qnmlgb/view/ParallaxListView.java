package com.qnmlgb.view;

import com.qnmlgb.ResetHeightAnimation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.ListView;

@SuppressLint("NewApi")
public class ParallaxListView extends ListView{
	
	private ImageView parallImageView;
	private int orginalHeight; //控件原始高度
	private int maxHeight; //控件最大高度(根据原型图片真实高度)

	public ParallaxListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public void setParallaxImageView(ImageView parallaxImageView){
		this.parallImageView = parallaxImageView;
		
		orginalHeight = parallImageView.getHeight();
		Log.i("CNM", "orginalHeight: "+orginalHeight);
		
		maxHeight = parallImageView.getDrawable().getIntrinsicHeight();
		Log.i("CNM", "maxHeight: "+maxHeight);
	}
	
	
	@Override
	protected boolean overScrollBy(int deltaX, int deltaY, int scrollX,
			int scrollY, int scrollRangeX, int scrollRangeY,
			int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
				//deltaY: y方向手指滑动的距离,正：底部到头(手指向上滑动)  负：顶部到头(手指向下滑动)
				//scrollY：y方向滚动的距离
				//maxOverScrollY：最大可滑动的距离
				//isTouchEvent: 是否是手指拖动到头， true：是    false:惯性滑动到头
		
		Log.i("CNM", "deltaY: "+deltaY +"  isTouchEvent:"+isTouchEvent);
		
		if(deltaY<0 && isTouchEvent){ // 手指向下滑动 到顶部,为负值   并且满足  由手指拖到顶部并非惯性 到顶部
			parallImageView.getLayoutParams().height = parallImageView.getHeight() - deltaY/3; //向下滑动增加的距离
			
			Log.i("CNM", "parallImageView.getHeight(): "+parallImageView.getHeight());
			
			if(parallImageView.getLayoutParams().height>maxHeight){  //当下拉使图片高度增大 到一定程度 超过 最大图片高度时
				parallImageView.getLayoutParams().height = maxHeight;
			}
			
			parallImageView.requestLayout();  //请求重新绘制布局
			
		}
		return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX,
				scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_UP:
			// 当触摸 互动结束的时候调用     恢复imageview初始化大小，并且给一个弹性效果的动画
			ResetHeightAnimation mReserHeightAnimation = new ResetHeightAnimation(parallImageView, orginalHeight);
			startAnimation(mReserHeightAnimation);
			
			break;

		default:
			break;
		}
		
		return super.onTouchEvent(ev);
	}
	

}
