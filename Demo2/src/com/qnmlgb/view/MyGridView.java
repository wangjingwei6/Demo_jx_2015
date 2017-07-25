package com.qnmlgb.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.GridView;

/**
 * 
 * @author wangjingwei 解决gridview 加载大数据卡顿的问题
 *
 */
public class MyGridView extends GridView implements OnScrollListener {
	public static boolean isOnMeasure;

	public MyGridView(Context context) {
		super(context);
	}

	public MyGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		Log.d("CNM", "onMeasure");
		isOnMeasure = true;
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		Log.d("CNM", "onLayout");
		isOnMeasure = false;
		super.onLayout(changed, l, t, r, b);
	}
	
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		switch (scrollState) {
		case OnScrollListener.SCROLL_STATE_IDLE:  //滑动停止
			if(view.getLastVisiblePosition()==(view.getCount()-1)){
				Log.i("onScrollStateChanged", "到底部啦。可以请求刷新咯~~~~~~");
				if(mOnScrollBottomListener!=null){
					mOnScrollBottomListener.onScrollBottom();
				}
			}
			
			break;
			
		case OnScrollListener.SCROLL_STATE_FLING:
			Log.i("onScrollStateChanged", "开始滚动：SCROLL_STATE_FLING");
			break;
		case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
			Log.i("onScrollStateChanged", "开始滚动：SCROLL_STATE_FLING");
			break;
			
		default:
			break;
		}

	}

	
	private  OnScrollBottomListener  mOnScrollBottomListener;
	
	public void setmOnScrollBottomListener(OnScrollBottomListener mOnScrollBottomListener) {
		this.setOnScrollListener(this);
		this.mOnScrollBottomListener = mOnScrollBottomListener;
	}


	public interface OnScrollBottomListener{
		void  onScrollBottom(); 
	}
	
	
	public void removeOnScrollBottomListener(){
			mOnScrollBottomListener=null;
	}

}
