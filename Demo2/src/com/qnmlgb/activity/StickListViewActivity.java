package com.qnmlgb.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.flyco.animation.BaseAnimatorSet;
import com.flyco.animation.FlipEnter.FlipHorizontalSwingEnter;
import com.flyco.animation.FlipEnter.FlipTopEnter;
import com.flyco.animation.FlipExit.FlipHorizontalExit;
import com.flyco.animation.SlideEnter.SlideTopEnter;
import com.flyco.animation.SlideExit.SlideLeftExit;
import com.flyco.dialog.entity.DialogMenuItem;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.NormalListDialog;
import com.ninexiu.wjw.R;
import com.qnmlgb.view.stickylistviewheader.StickyListHeadersAdapter;
import com.qnmlgb.view.stickylistviewheader.StickyListHeadersListView;
import com.qnmlgb.view.stickylistviewheader.StickyListHeadersListView.OnHeaderClickListener;

public class StickListViewActivity extends Activity implements
		OnHeaderClickListener, OnItemClickListener {

	private StickyListHeadersListView stickyList;
	private StickyListAdapter adapter;
	private View loading;
	private RelativeLayout loadingMore;
	private ProgressBar loading_morepage_progress;

	private boolean isLastRow; // 最后一行
	private boolean isLoading; // 是否正在加载
	private AnimationDrawable loadingAnimation;

	private ArrayList<String> listData;
	private ArrayList<DialogMenuItem> dialoglist;
	private String[] dialoglist2;

	public final int TYPE_ONE = 1;
	public final int TYPE_TWO = 2;

	private OnScrollListener mOnScrollListener = new OnScrollListener() {

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			if (isLastRow && scrollState == SCROLL_STATE_IDLE) {
				Toast.makeText(StickListViewActivity.this, "滑到底啦！",
						Toast.LENGTH_LONG).show();

				Log.e("haha", "滑到底了！！！！！！！");
				getMoreData();
			}
		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			int lastItem = firstVisibleItem + visibleItemCount;
			if (totalItemCount > 0 && totalItemCount == lastItem) {// 最后一行
				isLastRow = true;
			} else {
				isLastRow = false;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setTheme(R.style.AnimationTheme);
		setContentView(R.layout.sticklistview_content);
		listData = new ArrayList<String>();
		isLoading = false;
		initView();
	}

	@Override
	protected void onResume() {
		super.onResume();
		initData();
		initdialogData();

		Log.e("haha", "onresume!!");
	}

	private void initdialogData() {
		dialoglist = new ArrayList<DialogMenuItem>();
		DialogMenuItem mDialogMenuItem1 = new DialogMenuItem("陆依娜",
				R.drawable.dialog_1);
		DialogMenuItem mDialogMenuItem2 = new DialogMenuItem("林贞恩",
				R.drawable.dialog_2);
		DialogMenuItem mDialogMenuItem3 = new DialogMenuItem("黄家驹",
				R.drawable.dialog_3);
		DialogMenuItem mDialogMenuItem4 = new DialogMenuItem("吴天庭",
				R.drawable.dialog_4);
		DialogMenuItem mDialogMenuItem5 = new DialogMenuItem("汪经纬",
				R.drawable.dialog_5);
		DialogMenuItem mDialogMenuItem6 = new DialogMenuItem("赵日天",
				R.drawable.dialog_6);
		dialoglist.add(mDialogMenuItem1);
		dialoglist.add(mDialogMenuItem2);
		dialoglist.add(mDialogMenuItem3);
		dialoglist.add(mDialogMenuItem4);
		dialoglist.add(mDialogMenuItem5);
		dialoglist.add(mDialogMenuItem6);
		
		dialoglist2 = new String[]{"王尼玛","叶良晨","赵日天","龙傲天","武则天"};
	}

	private void initData() {
		int i;
		for (i = 0; i < 20; i++) {
			listData.add("神马都是浮云  : " + i);
		}

		adapter = new StickyListAdapter();
		adapter.init(StickListViewActivity.this, listData);
		stickyList.setAdapter(adapter);
	}

	protected void getMoreData() {
		loadingMore.setVisibility(View.VISIBLE);
		if (!isLoading) { // 不是正在加载
			isLoading = true;
			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {

					for (int x = 0; x < 15; x++) {
						listData.add((Math.random() * 40) + "");
					}
					loadingFinish();
				}
			}, 1500);

		}
	}

	protected void loadingFinish() {
		loadingMore.setVisibility(View.GONE);
		isLoading = false;
		adapter.notifyDataSetChanged();
	}

	private void initView() {
		stickyList = (StickyListHeadersListView) findViewById(R.id.sticklist_content);
		loading = findViewById(R.id.loading_layout);
		loadingMore = (RelativeLayout) findViewById(R.id.loading_morepage_layout);
		loading_morepage_progress = (ProgressBar) loadingMore
				.findViewById(R.id.loading_morepage_progress);

		stickyList.setOnScrollListener(mOnScrollListener);
		stickyList.setOnItemClickListener(this);
		stickyList.setOnHeaderClickListener(this);
	}

	class StickyListAdapter extends BaseAdapter implements
			StickyListHeadersAdapter {

		public Context mContext;
		private ArrayList<String> mList;
		private LayoutInflater mInflater;

		public void init(Context context, ArrayList<String> list) {
			this.mContext = context;
			this.mList = list;
			this.mInflater = LayoutInflater.from(mContext);
		}

		@Override
		public int getCount() {
			return mList.size();
		}

		@Override
		public Object getItem(int position) {
			return mList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ContentViewHolder mContentViewHolder;
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.sticklist_item, null);
				mContentViewHolder = new ContentViewHolder(convertView);
				convertView.setTag(mContentViewHolder);
			} else {
				mContentViewHolder = (ContentViewHolder) convertView.getTag();
			}

			mContentViewHolder.imageView
					.setImageResource(R.drawable.icon_default_avatar);
			mContentViewHolder.desc.setText(mList.get(position));

			return convertView;
		}

		@Override
		public View getHeaderView(int position, View convertView,
				ViewGroup parent) {
			GroupTitleViewHolder mTitleViewholder;
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.sticklist_item_title,
						null);
				mTitleViewholder = new GroupTitleViewHolder(convertView);
				convertView.setTag(mTitleViewholder);
			} else {
				mTitleViewholder = (GroupTitleViewHolder) convertView.getTag();
			}

			mTitleViewholder.groupName.setText("Head : " + position / 6);

			return convertView;
		}

		@Override
		public long getHeaderId(int position) {
			return position / 6 + 666;
		}

		class ContentViewHolder {
			public ImageView imageView;
			public TextView desc;

			public ContentViewHolder(View view) {
				imageView = (ImageView) view.findViewById(R.id.imageview);
				desc = (TextView) view.findViewById(R.id.stick_item_text);
			}
		}

		class GroupTitleViewHolder {
			public TextView groupName;

			public GroupTitleViewHolder(View view) {
				groupName = (TextView) view.findViewById(R.id.group_title);
			}
		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		showMyDialog((int)(Math.random()*2+1));

		Toast.makeText(StickListViewActivity.this, "点击position = " + position,
				Toast.LENGTH_SHORT).show();
	
	}

	private void showMyDialog(int type) {
		BaseAnimatorSet bas_in;
		BaseAnimatorSet bas_out;
		
		switch (type) {
		case TYPE_ONE:
			
			bas_in = new SlideTopEnter();
			bas_out = new SlideLeftExit();

			// 触发listDialog  展示 图片和 文字
			final NormalListDialog normalDialog = new NormalListDialog(
					StickListViewActivity.this, dialoglist);
			normalDialog.title("我的朋友")
					//
					.titleBgColor(getResources().getColor(R.color.green_accent))
					.titleTextColor(getResources().getColor(R.color.hong))
					// normalDialog.isTitleShow(false)
					.showAnim(bas_in)//
					.dismissAnim(bas_out)//
					.show();
			normalDialog.setOnOperItemClickL(new OnOperItemClickL() {
				@Override
				public void onOperItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					normalDialog.dismiss();
				}
			});
			
			break;
		case TYPE_TWO:
			bas_in = new FlipHorizontalSwingEnter();
			bas_out = new FlipHorizontalExit();
			
			//只展示文字
			final NormalListDialog normaldialog   = new NormalListDialog(StickListViewActivity.this,dialoglist2);
			normaldialog.title("英雄人物")
			.showAnim(bas_in)
			.dismissAnim(bas_out)
			.show();
			
			normaldialog.setOnOperItemClickL(new OnOperItemClickL() {
				
				@Override
				public void onOperItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
					normaldialog.dismiss();
				}
			});

			break;

		default:
			break;
		}
	}

	@Override
	public void onHeaderClick(StickyListHeadersListView l, View header,
			int itemPosition, long headerId, boolean currentlySticky) {
		Toast.makeText(StickListViewActivity.this, "点击 header :  " + headerId,
				Toast.LENGTH_SHORT).show();
	}

}
