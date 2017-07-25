package com.qnmlgb.view;


import java.lang.reflect.Field;
import java.lang.reflect.Method;


import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.ScrollView;
import android.widget.Scroller;
 
/**重写ScrollView
 * 解决ScrollView嵌套ViewPager出现的滑动冲突问题
 * @date 2015-11-14 上午11:04:22
 * @author liuxiong
 *
 */
public class MyScrollView extends ScrollView {    
	private GestureDetector mGestureDetector;
	private int Scroll_height = 0;
	private int view_height = 0;
	protected Field scrollView_mScroller;
	private static final String TAG = "CustomScrollView";
	private OnScrollListener onScrollListener;
	/**
	 * 主要是用在用户手指离开MyScrollView，MyScrollView还在继续滑动，我们用来保存Y的距离，然后做比较
	 */
	private int lastScrollY;
	

	public MyScrollView(Context context, AttributeSet attrs) {
	
		super(context, attrs);
		mGestureDetector = new GestureDetector(context, new YScrollDetector());
		setFadingEdgeLength(0);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
			stopAnim();
		}
		boolean ret = super.onInterceptTouchEvent(ev);
		boolean ret2 = mGestureDetector.onTouchEvent(ev);
		return ret && ret2;
	}

	// Return false if we're scrolling in the x direction
	class YScrollDetector extends SimpleOnGestureListener {
		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
			if (Math.abs(distanceY) > Math.abs(distanceX)) {
				return true;
			}
			return false;
		}
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		boolean stop = false;
		if (Scroll_height - view_height == t) {
			stop = true;
		}

		if (t == 0 || stop == true) {
			try {
				if (scrollView_mScroller == null) {
					scrollView_mScroller = getDeclaredField(this, "mScroller");
				}

				Object ob = scrollView_mScroller.get(this);
				if (ob == null || !(ob instanceof Scroller)) {
					return;
				}
				Scroller sc = (Scroller) ob;
				sc.abortAnimation();

			} catch (Exception e) {
				
			}
		}
		super.onScrollChanged(l, t, oldl, oldt);
	}

	private void stopAnim() {
		try {
			if (scrollView_mScroller == null) {
				scrollView_mScroller = getDeclaredField(this, "mScroller");
			}

			Object ob = scrollView_mScroller.get(this);
			if (ob == null) {
				return;
			}
			Method method = ob.getClass().getMethod("abortAnimation");
			method.invoke(ob);
		} catch (Exception ex) {
			
		}
	}

	@Override
	protected int computeVerticalScrollRange() {
		Scroll_height = super.computeVerticalScrollRange();
		return Scroll_height;
		
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		if (changed == true) {
			view_height = b - t;
		}
	}

	@Override
	public void requestChildFocus(View child, View focused) {
		if (focused != null && focused instanceof WebView) {
			return;
		}
		super.requestChildFocus(child, focused);
	}

	/**
	 * 获取一个对象隐藏的属性，并设置属性为public属性允许直接访问
	 * 
	 * @return {@link Field} 如果无法读取，返回null；返回的Field需要使用者自己缓存，本方法不做缓存�?
	 */
	public static Field getDeclaredField(Object object, String field_name) {
		
		Class<?> cla = object.getClass();
		Field field = null;
		for (; cla != Object.class; cla = cla.getSuperclass()) {
			try {
				field = cla.getDeclaredField(field_name);
				field.setAccessible(true);
				return field;
			} catch (Exception e) {

			}
		}
		return null;
	}
	
	
	/**
	 * 设置滚动接口
	 * @param onScrollListener
	 */
	public void setOnScrollListener(OnScrollListener onScrollListener) {
		this.onScrollListener = onScrollListener;
	}


	/**
	 * 用于用户手指离开MyScrollView的时候获取MyScrollView滚动的Y距离，然后回调给onScroll方法中
	 */
	private Handler handler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			int scrollY = MyScrollView.this.getScrollY();
			//此时的距离和记录下的距离不相等，在隔5毫秒给handler发送消息
			if(lastScrollY != scrollY){
				lastScrollY = scrollY;
				//延迟时间不要太短  否则 lastScrollY 与 scrollY很容易相等 
				handler.sendMessageDelayed(handler.obtainMessage(), 40);  
			}
			if(onScrollListener != null){
				
				onScrollListener.onScroll(scrollY);
			}
			
		};

	}; 

	/**
	 * 重写onTouchEvent， 当用户的手在MyScrollView上面的时候，
	 * 直接将MyScrollView滑动的Y方向距离回调给onScroll方法中，当用户抬起手的时候，
	 * MyScrollView可能还在滑动，所以当用户抬起手我们隔5毫秒给handler发送消息，在handler处理
	 * MyScrollView滑动的距离
	 */
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if(onScrollListener != null){
			onScrollListener.onScroll(lastScrollY = this.getScrollY());
		}
		switch(ev.getAction()){
		case MotionEvent.ACTION_UP:
	         handler.sendMessageDelayed(handler.obtainMessage(), 40);  
			break;
		}
		return super.onTouchEvent(ev);
	}


	/**
	 * 
	 * 滚动的回调接口
	 * 
	 * @author xiaanming
	 *
	 */
	public interface OnScrollListener{
		/**
		 * 回调方法， 返回MyScrollView滑动的Y方向距离
		 * @param scrollY
		 * 				
		 */
		public void onScroll(int scrollY);
	}
	
	
	
	
}