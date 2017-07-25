package com.qnmlgb.activity;

import java.util.ArrayList;
import java.util.Collections;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

import com.ninexiu.wjw.R;
import com.qnmlgb.bean.GetAllTypeData;
import com.qnmlgb.bean.QuickData;
import com.qnmlgb.util.MyToast;
import com.qnmlgb.util.Utils;
import com.qnmlgb.util.Utils.LoginDialogListenner;
import com.qnmlgb.view.QuickIndexbarView;
import com.qnmlgb.view.SwipeView;
import com.qnmlgb.view.QuickIndexbarView.OnTouchIndexListener;
import com.qnmlgb.view.SwipeView.OnSwipeStatusChangeListener;

public class QuickIndexbarActivity extends Activity {

	private QuickIndexbarView mQuickIndexbarView;
	private ListView mQuickListView;
	private ArrayList<QuickData> mData = GetAllTypeData.getQuickData();; // 数据源

	private Handler handler = new Handler();
	private TextView showLargeIndex;
	private String showWord;
	private String[] indexArr = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W",
			"X", "Y", "Z" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setTheme(R.style.AnimationTheme);
		setContentView(R.layout.quick_indexbar_contentview);

		initView();
		initData();
	}

	private void initView() {
		mQuickIndexbarView = (QuickIndexbarView) findViewById(R.id.quick_indexbar_view);
		mQuickListView = (ListView) findViewById(R.id.quick_listview_noSwipe);

		showLargeIndex = (TextView) findViewById(R.id.index_word);
	}

	private void initData() {

		mQuickIndexbarView.setmTouchIndexListener(new OnTouchIndexListener() {

			@Override
			public void onTouchIndex(String word) {
				Log.e("CNM", "onTouchIndex = =" + word);
				// 展示一个大的提示view
				showRemindView(word);

				// 找到首字母和触摸字母相同的条目
				for (int i = 0; i < mData.size(); i++) {
					String currentWord = mData.get(i).getPinyin().charAt(0) + "";
					if (word.equals(currentWord)) { // 当前首字母 和
						// 导航栏字母相同,快速指引到listview的条目
						mQuickListView.setSelection(i);
						break;
					}

				}
			}
		});

		mQuickListView.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
					closeAllLayout();
				}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

				// 获取第一条显示的数据 firstVisibleItem
				QuickData quickData = mData.get(firstVisibleItem);

				// 获取bean数据里的 首字母
				String touchFirstWord = quickData.getPinyin().charAt(0) + "";

				// 根据listview中第一条滑动的bean数据里的首字母 比对出QuickIndexbarView 里的字母
				// 得到首字母相同的index 同步进QuickIndexbarView 显示当前选中字母不同颜色

				if (!touchFirstWord.equals(showWord)) {
					showWord = touchFirstWord; // 每次滑动listview显示的index 赋值给 需要展示的
					for (int i = 0; i < indexArr.length; i++) { // 遍历获取需要改变的quickindex
						if (showWord.equals(indexArr[i])) {//
							// 改变quickIndex展示
							mQuickIndexbarView.setChangeIndex(i);
						}
					}
				}

			}
		});

		// 获取数据源
		// mData =
		// ======== 给所有条目数据排序
		Collections.sort(mData);

		// 创建适配器
		MySwipeAdaptetr mMySwipeAdaptetr = new MySwipeAdaptetr(QuickIndexbarActivity.this, mData);
		mQuickListView.setAdapter(mMySwipeAdaptetr);

	}

	protected void showRemindView(String word) {
		showLargeIndex.setVisibility(View.VISIBLE);
		showLargeIndex.setText(word);

		handler.removeCallbacksAndMessages(null);
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				showLargeIndex.setVisibility(View.GONE);
			}
		}, 500);
	}

	class MySwipeAdaptetr extends BaseAdapter {
		private Context mContext;
		private ArrayList<QuickData> mDataList;

		public MySwipeAdaptetr(Context context, ArrayList<QuickData> dataList) {
			this.mContext = context;
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
				convertView = View.inflate(mContext, R.layout.list_quickindex_item, null);
			}
			holder = getHolder(convertView);

			// =======================设置数据
			QuickData mQuickData = mDataList.get(position); // 获取一个条目的数据
			String currentFirstWord = mQuickData.getPinyin().charAt(0) + "";
			// 思路: 如果是第一条。不比较 其他的通过上一个条目的数据 和当前条目数据的首字母作为比较
			// Same: 隐藏 当前条目数据的index内容 Not: 显示当前条目的index内容
			if (position > 0) {
				String lastFirstWord = mDataList.get(position - 1).getPinyin().charAt(0) + "";
				if (TextUtils.equals(currentFirstWord, lastFirstWord)) {
					// 隐藏 当前条目
					holder.content_index.setVisibility(View.GONE);
				} else {
					holder.content_index.setVisibility(View.VISIBLE);
					holder.content_index.setText(currentFirstWord);
				}

			} else { // 第一条 ,不作比较 直接显示
				holder.content_index.setVisibility(View.VISIBLE);
				holder.content_index.setText(currentFirstWord);
			}

			holder.swipeView.fastClose();
			holder.content_text.setText(mDataList.get(position).getName());

			holder.content_text.setOnLongClickListener(new OnLongClickListener() {

				@Override
				public boolean onLongClick(View v) {

					Utils.remindUserDialog(QuickIndexbarActivity.this, "后悔了", "确定", "你确认删除?", new LoginDialogListenner() {

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

			holder.content_text.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (Utils.isClickable()) {
						return;
					}

					if (unCloseSwipeViews.size() > 0) {
						closeAllLayout();
						unCloseSwipeViews.removeAll(unCloseSwipeViews);
					} else {
						MyToast.MakeToast(QuickIndexbarActivity.this, "您点击了 :" + mDataList.get(position).getName());
					}

				}
			});

			holder.delete.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					holder.swipeView.fastClose();
					if (unCloseSwipeViews.size() > 0) {
						closeAllLayout();
					}
					mDataList.remove(position);
					notifyDataSetChanged();
				}
			});

			holder.swipeView.setOnSwipeStatusChangeListener(mOnSwipeStatusChangeListener);

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
			TextView content_text, content_index, delete;

			public ViewHolder(View convertView) {
				swipeView = (SwipeView) convertView.findViewById(R.id.simple_swipeView);
				// content = (LinearLayout) convertView
				// .findViewById(R.id.simple_content);

				content_text = (TextView) convertView.findViewById(R.id.simple_name);

				content_index = (TextView) convertView.findViewById(R.id.quick_index);

				delete = (TextView) convertView.findViewById(R.id.simple_delete);
			}
		}

	}

	// ===========swipe

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

	// @Override
	// public void onScrollStateChanged(AbsListView view, int scrollState) {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// @Override
	// public void onScroll(AbsListView view, int firstVisibleItem,
	// int visibleItemCount, int totalItemCount) {
	//
	//
	// // 获取第一条显示的数据 firstVisibleItem
	//
	// // 获取bean数据里的 首字母
	//
	// // 根据listview中第一条滑动的bean数据里的首字母 比对出QuickIndexbarView 里的字母
	// // 得到首字母相同的index 同步进QuickIndexbarView 显示当前选中字母不同颜色
	//
	// }

}
