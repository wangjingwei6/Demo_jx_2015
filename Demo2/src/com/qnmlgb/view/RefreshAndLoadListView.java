package com.qnmlgb.view;

import com.ninexiu.wjw.R;
import com.qnmlgb.util.Utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * 
 * 
 * 
 * @author wangjingwei
 *
 */
public class RefreshAndLoadListView extends ListView implements
		OnScrollListener {
	//======================上拉加载相关
	private View footer;// 底部布局；
	int totalItemCount;// 总数量；
	int lastVisibleItem;// 最后一个可见的item；
	boolean isLoadingMore;// 正在加载；
	IRefreshAndLoadListener iRefreshAndLoadListener;
	private LayoutInflater inflater;

	//======================= 下拉刷新相关
	private View mHeaderView; // 头布局的view对象
	private TextView tvState; //刷新显示状态的文字说明
	private TextView tvLastUpdateTime; //最后刷新的时间
	private ImageView ivArrow; //下拉刷新箭头图标
	private ProgressBar mProgressbar; //下拉刷新环形进度条
	
	private RotateAnimation upAnimation;//箭头向上旋转动画
	private RotateAnimation downAnimation;//箭头向下旋转动画
	
	private int mMessureHeadHeight; // 头布局的高度
	private int downY; // 手指按下时候Y轴的偏移量
	private final int PULL_DOWN = 0; // 下拉刷新
	private final int RELEASE_REFRESH = 1; // 释放刷新
	private final int REFRESHING = 2; // 正在刷新中
	private int currentStatus = PULL_DOWN; // 当前刷新状态 (默认为下拉刷新)

	public RefreshAndLoadListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initHeadView(context);
		initFootView(context);
	}

	public RefreshAndLoadListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initHeadView(context);
		initFootView(context);
	}

	public RefreshAndLoadListView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		initHeadView(context);
		initFootView(context);
	}

	/**
	 * 添加顶部头布局到ListView
	 * 
	 * @param context
	 */
	private void initHeadView(Context context) {
		inflater = LayoutInflater.from(context);
		mHeaderView = inflater.inflate(R.layout.refresh_listview_header, null); //下拉刷新头布局
		ivArrow = (ImageView) mHeaderView.findViewById(R.id.iv_refresh_listview_header_arrow);
		mProgressbar = (ProgressBar) mHeaderView.findViewById(R.id.pb_refresh_listview_header);
		tvState = (TextView) mHeaderView.findViewById(R.id.tv_refresh_listview_header_state);
		tvLastUpdateTime = (TextView) mHeaderView.findViewById(R.id.tv_refresh_listview_header_last_update_time);
		tvLastUpdateTime.setText(Utils.getCurrentTime());

		mHeaderView.measure(0, 0);
		mMessureHeadHeight = mHeaderView.getMeasuredHeight();
		mHeaderView.setPadding(0, -mMessureHeadHeight, 0, 0);
		this.addHeaderView(mHeaderView);
		initAnimation();
	}
	
	/**
	 * 添加底部加载提示布局到listview
	 * 
	 * @param context
	 */
	private void initFootView(Context context) {
		inflater = LayoutInflater.from(context);
		footer = inflater.inflate(R.layout.ns_livehall_loading_morepage, null);
		footer.findViewById(R.id.load_layout).setVisibility(View.GONE);
		this.addFooterView(footer);
		this.setOnScrollListener(this);
	}

	/**
	 * 初始化下拉刷新头布局箭头需要的动画
	 */
	private void initAnimation() {
		upAnimation = new RotateAnimation(
				0, -180, 
				Animation.RELATIVE_TO_SELF, 0.5f, 
				Animation.RELATIVE_TO_SELF, 0.5f);
		upAnimation.setDuration(500);
		upAnimation.setFillAfter(true);
		
		downAnimation = new RotateAnimation(
				-180, -360, 
				Animation.RELATIVE_TO_SELF, 0.5f, 
				Animation.RELATIVE_TO_SELF, 0.5f);
		downAnimation.setDuration(500);
		downAnimation.setFillAfter(true);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:

			downY = (int) ev.getY();

			break;
		case MotionEvent.ACTION_MOVE:

			if(currentStatus == REFRESHING){
				break;
			}
			
			int moveY = (int) ev.getY();
			int diffY = moveY - downY;

			// 如果Y轴移动的距离大于0，并且listView当前最顶部显示的item索引为0.执行下拉刷新的操作
			if (diffY > 0 && getFirstVisiblePosition() == 0) {

				int paddingTop = -mMessureHeadHeight + diffY / 2;

				if (paddingTop > 0 &&currentStatus!= RELEASE_REFRESH) {
					// 头布局完全显示,进入释放刷新状态
					currentStatus = RELEASE_REFRESH;
					refreshHeadView(); // 刷新头布局显示

				} else if (paddingTop < 0 && currentStatus!= PULL_DOWN) {
					// 手指Y轴移动 头布局没有完全显示 下拉刷新状态
					currentStatus = PULL_DOWN;
					refreshHeadView();
				}
				
				mHeaderView.setPadding(0,paddingTop,0,0);
				return true;
			}

			break;
		case MotionEvent.ACTION_UP:
			//判断手指抬起时候头布局的状态 (下拉刷新  或者  释放刷新)
			if(currentStatus == PULL_DOWN){ // 头布局是下拉刷新的范围。收回  隐藏头布局
				mHeaderView.setPadding(0,-mMessureHeadHeight, 0,0);
			}else if(currentStatus == RELEASE_REFRESH){//头布局是释放刷新的范围 ，进入 正在刷新中状态
				currentStatus = REFRESHING;
				refreshHeadView();
				mHeaderView.setPadding(0,0,0,0);//头布局正好完全显示，准备回调  刷新数据
				
				//刷新数据回调
				if(iRefreshAndLoadListener!=null){
					iRefreshAndLoadListener.refresh();
				}
			}
			
			break;
		default:
			break;
		}

		return super.onTouchEvent(ev);
	}

	/**
	 * 刷新头布局显示
	 */
	private void refreshHeadView() {
		switch (currentStatus) {
		case PULL_DOWN:
			
			ivArrow.startAnimation(upAnimation);
			tvState.setText("下拉刷新");
			
			break;
		case RELEASE_REFRESH:
			ivArrow.startAnimation(downAnimation);
			tvState.setText("释放刷新");
			
			break;
		case REFRESHING:
			ivArrow.clearAnimation(); //清除动画
			ivArrow.setVisibility(View.GONE);
			mProgressbar.setVisibility(View.VISIBLE);
			tvState.setText("正在刷新中..");
			break;

		default:
			break;
		}
	}

	// ==============================================
	/**
	 * 滚动监听事件 ,处理底部布局的监听回调
	 */
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		this.lastVisibleItem = firstVisibleItem + visibleItemCount;
		this.totalItemCount = totalItemCount;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		if (totalItemCount == lastVisibleItem
				&& scrollState == SCROLL_STATE_IDLE) {
			if (!isLoadingMore) {
				isLoadingMore = true;
				footer.findViewById(R.id.load_layout).setVisibility(
						View.VISIBLE);
				// 加载更多
				iRefreshAndLoadListener.onLoad();
			}
		}
	}

	// ==============================================

	
	/**
	 * 下拉刷新或者加载更多 完成时, 调用此方法, 隐藏头布局或脚布局
	 */
	public void onRefreshFinish() {
		if(isLoadingMore) {
			// 当前是加载更多, 隐藏脚布局
			isLoadingMore = false;
			footer.findViewById(R.id.load_layout).setVisibility(View.GONE);
		} else {
			// 当前是下拉刷新, 隐藏头布局
			mHeaderView.setPadding(0, -mMessureHeadHeight, 0, 0);
			currentStatus = PULL_DOWN;
			ivArrow.setVisibility(View.VISIBLE);
			tvState.setText("下拉刷新");
			mProgressbar.setVisibility(View.INVISIBLE);
			tvLastUpdateTime.setText(Utils.getCurrentTime());
		}
	}
	
	
	/**
	 *   listview 的 下拉刷新 和 加载更多 数据回调接口
	 * @param iRefreshAndLoadListener
	 */
	public void setInterface(IRefreshAndLoadListener iRefreshAndLoadListener) {
		this.iRefreshAndLoadListener = iRefreshAndLoadListener;
	}

	// 加载更多数据的回调接口
	public interface IRefreshAndLoadListener {
		public void refresh();
		public void onLoad();
	}
}
