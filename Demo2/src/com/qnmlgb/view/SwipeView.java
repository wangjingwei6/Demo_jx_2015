package com.qnmlgb.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.support.v4.widget.ViewDragHelper.Callback;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;


public class SwipeView extends FrameLayout{

	public SwipeView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	private View contentView,deleteView;
	private int deleteWidth,deleteHeight,contentWidth;
	private ViewDragHelper viewDragHelper;
	private boolean swipeStatus;  //true:open,false:close
	private boolean isCanSwipe = true;  //是否可以滑动
	
	public boolean isCanSwipe() {
		return isCanSwipe;
	}

	
	public void setCanSwipe(boolean isCanSwipe) {
		this.isCanSwipe = isCanSwipe;
	}

	private void init(){
		viewDragHelper = ViewDragHelper.create(this, callback);
	}
	
	
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		contentView = getChildAt(0);
		deleteView = getChildAt(1);
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		contentWidth = contentView.getMeasuredWidth();
		deleteHeight = deleteView.getMeasuredHeight();
		deleteWidth = deleteView.getMeasuredWidth();
	}
	
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		
		contentView.layout(0, 0, contentWidth, deleteHeight);
		deleteView.layout(contentWidth, 0, contentWidth+deleteWidth, deleteHeight);
	}

	private ViewDragHelper.Callback callback = new Callback() {
		@Override
		public boolean tryCaptureView(View child, int pointerId) {
			return child==contentView || child==deleteView;
		}
		@Override
		public void onViewCaptured(View capturedChild, int activePointerId) {
			super.onViewCaptured(capturedChild, activePointerId);
		}

		@Override
		public int getViewHorizontalDragRange(View child) {
			return deleteWidth;
		}

		@Override
		public void onViewDragStateChanged(int state) {
			super.onViewDragStateChanged(state);
		}
	
		@Override
		public int clampViewPositionHorizontal(View child, int left, int dx) {
			if(child==contentView){
				if(left>0)left=0;
				if(left<-deleteWidth)left = -deleteWidth;
			}else {
				if(left>contentWidth)left = contentWidth;
				if(left<(contentWidth-deleteWidth))left = contentWidth-deleteWidth;
			}
			return left;
		}
		
		@Override
		public void onViewPositionChanged(View changedView, int left, int top,int dx, int dy) {
			super.onViewPositionChanged(changedView, left, top, dx, dy);
			Log.i("SwipeView", "left = ="+left);
			Log.i("SwipeView", "dx = ="+dx);
			if(dx<0){
				direction="left";
				Log.i("SwipeView", "onViewPositionChanged = = direction=left");
			}else if(dx>0){
				direction="right";
				Log.i("SwipeView", "onViewPositionChanged = = direction=right");
			}
			
			if(changedView==contentView){
				deleteView.layout(deleteView.getLeft()+dx, 0, deleteView.getRight()+dx, deleteHeight);
//				deleteView.layout(contentView.getRight()+dx, 0, deleteView.getRight()+dx, deleteHeight);
//				contentView.layout(contentView.getLeft()+dx, 0, contentView.getRight()+dx, deleteHeight);
			}else {
				contentView.layout(contentView.getLeft()+dx, 0, contentView.getRight()+dx, deleteHeight);
			}
			
			if(contentView.getLeft()==0 && mStatus != Status.Close){
				mStatus = Status.Close;
				if(onSwipeStatusChangeListener!=null){
					onSwipeStatusChangeListener.onClose(SwipeView.this);
				}
			}else if (contentView.getLeft()==-deleteWidth && mStatus != Status.Open) {
				mStatus = Status.Open;
				if(onSwipeStatusChangeListener!=null){
					onSwipeStatusChangeListener.onOpen(SwipeView.this);
				}
			}else if (mStatus != Status.Swiping) {
				mStatus = Status.Swiping;
				if(onSwipeStatusChangeListener!=null){
					onSwipeStatusChangeListener.onSwiping(SwipeView.this);
				}
			}
		}
		
		/**
		 * TouchUp
		 */
		@Override
		public void onViewReleased(View releasedChild, float xvel, float yvel) {
			super.onViewReleased(releasedChild, xvel, yvel);
			if(TextUtils.equals(direction,"left")){  //往左边滑动
				Log.e("SwipeView","direction == left");
				if(contentView.getLeft()<-2){
					//打开
					open();
					Log.i("SwipeView","direction == left** open");
				}
			}else if(TextUtils.equals(direction,"right")){  //往右边滑动
				if(contentView.getLeft()>-(deleteWidth*7/8)){
					close();
					Log.i("SwipeView","direction == right ** close");
				}
			}
		}
		
	};
	
	public void open(){
		viewDragHelper.smoothSlideViewTo(contentView, -deleteWidth, 0);
		ViewCompat.postInvalidateOnAnimation(SwipeView.this);
	}
	
	//置为初始状态
	public void fastClose(){
		contentView.layout(0, 0, contentWidth, deleteHeight);
		deleteView.layout(contentWidth, 0, contentWidth+deleteWidth, deleteHeight);
		
		mStatus = Status.Close;
		if(onSwipeStatusChangeListener!=null){
			onSwipeStatusChangeListener.onClose(SwipeView.this);
		}
	}
	
	public void close(){
		viewDragHelper.smoothSlideViewTo(contentView, 0, 0);
		ViewCompat.postInvalidateOnAnimation(SwipeView.this);
	}
	
	public void computeScroll() {
		if(viewDragHelper.continueSettling(true)){//scroller.computeOffset()
			ViewCompat.postInvalidateOnAnimation(SwipeView.this);
		}
	};
	
	public boolean onInterceptTouchEvent(android.view.MotionEvent ev) {
		return viewDragHelper.shouldInterceptTouchEvent(ev);

	};
	
	private float lastX,lastY;
	private float  downX = 0 ;
	private String direction;
	@SuppressLint("NewApi")
	@Override
	public boolean onTouchEvent(MotionEvent event) {
			float x =  event.getX();
			float y =  event.getY();
			
			float upX = 0;
			
			 Log.e("SwipeView","x **"+x);
			switch (event.getAction()) {
			
			case MotionEvent.ACTION_DOWN:
				break;
			case MotionEvent.ACTION_MOVE:
				float deltaX = Math.abs(x-lastX);
				 Log.e("SwipeView","** MotionEvent.ACTION_MOVE **  deltaX =="+deltaX);
				float deltaY = Math.abs(y-lastY);
				 Log.e("SwipeView","** MotionEvent.ACTION_MOVE **  deltaY =="+deltaY);
//				int deltaX = Math.abs(x - lastX);
//				int deltaY = Math.abs(y - lastY);
				
				if(deltaX>deltaY){
					 Log.e("SwipeView","** MotionEvent.ACTION_MOVE **  deltaX > deltaY");
					requestDisallowInterceptTouchEvent(true);//让listview不再拦截该事件
				}else if(deltaX<deltaY){
					 Log.e("SwipeView","**MotionEvent.ACTION_MOVE ** deltaX < deltaY ");
					requestDisallowInterceptTouchEvent(false);  
				}
				break;
			case MotionEvent.ACTION_UP:
				break;
			}
			lastX = x;
			lastY = y;
			viewDragHelper.processTouchEvent(event);
			return true;
	}
	

	public void setOnSwipeStatusChangeListener(
			OnSwipeStatusChangeListener onSwipeStatusChangeListener) {
		this.onSwipeStatusChangeListener = onSwipeStatusChangeListener;
	}

	private Status mStatus = Status.Close;
	public enum Status{
		Open,Close,Swiping
	}
	
	private OnSwipeStatusChangeListener onSwipeStatusChangeListener;
	
	public interface OnSwipeStatusChangeListener{
		void onOpen(SwipeView openSwipeView);
		void onClose(SwipeView closeSwipeView);
		void onSwiping(SwipeView swipingSwipeView);
	}
}