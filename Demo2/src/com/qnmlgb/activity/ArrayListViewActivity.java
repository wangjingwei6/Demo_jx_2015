package com.qnmlgb.activity;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;

import com.ninexiu.wjw.R;
import com.qnmlgb.util.MyToast;
import com.qnmlgb.util.Utils;
import com.qnmlgb.util.Utils.LoginDialogListenner;
import com.qnmlgb.view.ParallaxListView;
import com.qnmlgb.view.SwipeView;
import com.qnmlgb.view.SwipeView.OnSwipeStatusChangeListener;

@SuppressLint("NewApi")
public class ArrayListViewActivity extends Activity {
	
	private ParallaxListView listView;
	private ArrayList<String> list;
	private ImageView parallImageView;
	
	private View head;

	@SuppressWarnings("rawtypes")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setTheme(R.style.AnimationTheme);
		setContentView(R.layout.arraylist);
		
//		  SystemBarTintManager tintManager = new SystemBarTintManager(this);  
//		  tintManager.setStatusBarTintEnabled(true);
//		  tintManager.setTintColor(getResources().getColor(R.color.baise));
		  
		listView = (ParallaxListView)findViewById(R.id.parallaxListView);
		listView.setOverScrollMode(AbsListView.OVER_SCROLL_NEVER);
		
		head = View.inflate(ArrayListViewActivity.this, R.layout.layout_head, null);
		parallImageView = (ImageView) head.findViewById(R.id.parallaxImageView);
		
		head.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			
			@Override
			public void onGlobalLayout() {
				listView.setParallaxImageView(parallImageView);
				head.getViewTreeObserver().removeOnGlobalLayoutListener(this);
			}
		});
		
		listView.addHeaderView(head);
		initData();
		
//		ArrayAdapter  adapter = new ArrayAdapter<String>(this,R.layout.listview_item, getData()); 
//		listView.setAdapter(adapter);
	}

//	private ArrayList<String> getData() {
//		list = new ArrayList<String>();
//		int i;
//		for(i =0;i<20;i++){
//			list.add("王尼玛"+i);
//		}
//		return list;
//	}
	
	//baseAdapter的数据
	private void initData() {
		ArrayList<String> dataList = getSimpleData();  //数据源
		//创建适配器
		MySwipeAdaptetr mMySwipeAdaptetr = new MySwipeAdaptetr(ArrayListViewActivity.this, dataList);
		
		listView.setAdapter(mMySwipeAdaptetr);
		
		
		listView.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				if(scrollState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL ){
					closeAllLayout();
				}
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				
			}
		});
		
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private ArrayList<String> getSimpleData() {
		ArrayList<String> arrayList =  new ArrayList<String>();
		for(int x = 0;x<20;x++){
			arrayList.add("汪经纬"+ x+"个人");
		}
		return arrayList;
	}
	
	//======================================BaseAdapter适配器  Swipeview
	
	
	class MySwipeAdaptetr extends BaseAdapter{
		private Context mContext;
		private ArrayList<String> mDataList;
		
		public MySwipeAdaptetr(Context context, ArrayList<String> dataList){
			this.mContext = context ;
			this.mDataList = dataList;
		}

		@Override
		public int getCount() {
			return mDataList.size();
		}

		@Override
		public Object getItem(int position) {
			return mDataList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = View.inflate(mContext,R.layout.ns_simple_text_swipe_message_item, null);
			}
			 holder = getHolder(convertView);
			//设置数据
			 holder.swipeView.fastClose();
			 holder.content_text.setText(mDataList.get(position));
			 holder.content.setOnLongClickListener(new OnLongClickListener() {
				
				@Override
				public boolean onLongClick(View v) {
					
					Utils.remindUserDialog(ArrayListViewActivity.this, "后悔了", "确定", "你确认删除?", new LoginDialogListenner() {
						
						@Override
						public void confirm() {
							holder.swipeView.fastClose();
							mDataList.remove(position);
							notifyDataSetChanged();
						}
						
						@Override
						public void cancle() {
						}
					});
					
					return false;
				}
			});
			 
			 holder.content.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(Utils.isClickable()){
						return;
					}
					if(unCloseSwipeViews.size()>0){
						closeAllLayout();
						unCloseSwipeViews.removeAll(unCloseSwipeViews);
					}else{
						MyToast.MakeToastSign(ArrayListViewActivity.this,"点击了 :"+mDataList.get(position));
					}
				}
			});
			 
			 
			 holder.delete.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					holder.swipeView.fastClose();
					mDataList.remove(position);
					notifyDataSetChanged();
				}
			});
			 
			 
			 holder.swipeView
				.setOnSwipeStatusChangeListener(mOnSwipeStatusChangeListener);
			 
			return convertView;
		}
		
		private ViewHolder getHolder(View convertView) {
			ViewHolder viewHolder = (ViewHolder) convertView.getTag();
			if (viewHolder == null) {
				viewHolder = new ViewHolder(convertView);
				convertView.setTag(viewHolder);
			}
			return viewHolder;
		}
		
		
		private ViewHolder holder;
		
		class ViewHolder {
			SwipeView swipeView;
			LinearLayout content;
			TextView delete;
			TextView content_text; 

			public ViewHolder(View convertView) {
				swipeView = (SwipeView) convertView
						.findViewById(R.id.simple_swipeView);
				content = (LinearLayout) convertView
						.findViewById(R.id.simple_content);
				delete = (TextView) convertView.findViewById(R.id.simple_delete);

				content_text = (TextView) convertView
						.findViewById(R.id.simple_name);
			}
		}
		
	}
	
	
	private ArrayList<SwipeView> unCloseSwipeViews = new ArrayList<SwipeView>();// 包含处于打开状态和滑动状态的
	
	SwipeView.OnSwipeStatusChangeListener mOnSwipeStatusChangeListener = new OnSwipeStatusChangeListener() {

		@Override
		public void onOpen(SwipeView openSwipeView) {
			
			for (int i = 0; i < unCloseSwipeViews.size(); i++) {
				if (unCloseSwipeViews.get(i) != openSwipeView) {
					unCloseSwipeViews.get(i).close();
				}
			}
			if (!unCloseSwipeViews.contains(openSwipeView)) {
				unCloseSwipeViews.add(openSwipeView);
			}
		}

		@Override
		public void onClose(SwipeView closeSwipeView) {
			unCloseSwipeViews.remove(closeSwipeView);
		}

		@Override
		public void onSwiping(SwipeView swipingSwipeView) {
			closeAllLayout();
			
			if (!unCloseSwipeViews.contains(swipingSwipeView)) {
				unCloseSwipeViews.add(swipingSwipeView);
			}
		}
	};
	
	private void closeAllLayout() {
		for (int i = 0; i < unCloseSwipeViews.size(); i++) {
			unCloseSwipeViews.get(i).close();
		}
	}

	
}
