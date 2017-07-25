package com.qnmlgb.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.flyco.animation.BaseAnimatorSet;
import com.flyco.animation.FlipEnter.FlipHorizontalSwingEnter;
import com.flyco.animation.FlipExit.FlipHorizontalExit;
import com.flyco.animation.SlideEnter.SlideTopEnter;
import com.flyco.animation.SlideExit.SlideLeftExit;
import com.flyco.dialog.entity.DialogMenuItem;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.NormalListDialog;
import com.ninexiu.wjw.R;
import com.qnmlgb.util.Utils;
import com.qnmlgb.util.Utils.LoginDialogListenner;
import com.qnmlgb.view.CircularImageView;
import com.qnmlgb.view.RefreshAndLoadListView;
import com.qnmlgb.view.SwipeView;
import com.qnmlgb.view.SwipeView.OnSwipeStatusChangeListener;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

@SuppressLint({ "ShowToast", "ClickableViewAccessibility" })
public class SimpleCustomListViewActivity extends Activity {
	
	private ListView listView;
	private ArrayList<String> list;
	
	public final int TYPE_ONE = 1;
	public final int TYPE_TWO = 2;
	
	private ArrayList<DialogMenuItem> dialoglist;
	private String[] dialoglist2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setTheme(R.style.AnimationTheme);
		setContentView(R.layout.list);
//		  SystemBarTintManager tintManager = new SystemBarTintManager(this);  
//		  tintManager.setStatusBarTintEnabled(true);
//		  tintManager.setTintColor(getResources().getColor(R.color.baise));
		  
		  
		listView = (ListView)findViewById(R.id.listview);
		initData();
	}
	
	//baseAdapter的数据
	private void initData() {
		List<Map<String, Object>> dataList = getSimpleData();  //数据源
		//创建适配器
		MySwipeAdaptetr mMySwipeAdaptetr = new MySwipeAdaptetr(SimpleCustomListViewActivity.this, dataList);
		
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

	private void initdialogData() {
		dialoglist = new ArrayList<DialogMenuItem>();
		DialogMenuItem mDialogMenuItem1 = new DialogMenuItem("王丽欣",
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

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		initdialogData();
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
					SimpleCustomListViewActivity.this, dialoglist);
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
			final NormalListDialog normaldialog   = new NormalListDialog(SimpleCustomListViewActivity.this,dialoglist2);
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

	
	
//	private SimpleAdapter SimpleAdapterForData() {
//		SimpleAdapter adapter = new SimpleAdapter(SimpleListViewActivity.this, getSimpleData(), R.layout.list_simple_item,new String[]{"name","desc","img"},new int[]{R.id.name,R.id.desc,R.id.cicularimage});
//		return  adapter;
//	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List<Map<String,Object>> getSimpleData() {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>(); 
		Map map1 = new HashMap<String, Object>();
		map1.put("name","陆依娜");
		map1.put("desc", "美女");
		map1.put("img", R.drawable.lyn_xx);
		
		Map map2 = new HashMap<String, Object>();
		map2.put("name","林贞恩");
		map2.put("desc", "美女");
		map2.put("img", R.drawable.lzn);
		
		Map map3 = new HashMap<String, Object>();
		map3.put("name","汪经纬");
		map3.put("desc", "帅哥");
		map3.put("img", R.drawable.wjw);
		
		Map map4 = new HashMap<String, Object>();
		map4.put("name","李明明");
		map4.put("desc", "帅哥");
		map4.put("img", R.drawable.lmm);
		
		Map map5 = new HashMap<String, Object>();
		map5.put("name","吴天庭");
		map5.put("desc", "帅哥");
		map5.put("img", R.drawable.wtt);
		
		Map map6 = new HashMap<String, Object>();
		map6.put("name","黄家驹");
		map6.put("desc", "帅哥");
		map6.put("img", R.drawable.hjj);
		
		Map map7 = new HashMap<String, Object>();
		map7.put("name","王丽欣");
		map7.put("desc", "女神");
		map7.put("img", R.drawable.dog11);
		
		Map map8 = new HashMap<String, Object>();
		map8.put("name","单身狗");
		map8.put("desc", "狗带");
		map8.put("img", R.drawable.dog5);
		
		Map map9= new HashMap<String, Object>();
		map9.put("name","汪经纬");
		map9.put("desc", "帅哥");
		map9.put("img", R.drawable.wjw);
		
		list.add(map1);
		list.add(map2);
		list.add(map3);
		list.add(map4);
		list.add(map5);
		list.add(map6);
		list.add(map7);
		list.add(map8);
		list.add(map9);
		
		return list;
	}
			//普通数据
	//=============================================================================================================
	private ArrayAdapter<String> ArrayAdapetForData() {
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.listview_item, getArrayData()); 
		return adapter;
	}

	private ArrayList<String> getArrayData() {
		list = new ArrayList<String>();
		int i;
		for(i =0;i<20;i++){
			list.add("王尼玛"+i);
		}
		return list;
	}
	//=============================================================================================================
	
	
	
	//======================================BaseAdapter适配器  Swipeview
	
	
	class MySwipeAdaptetr extends BaseAdapter{
		private Context mContext;
		private List<Map<String, Object>> mDataList;
		
		public MySwipeAdaptetr(Context context,List<Map<String, Object>> dataList){
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
				convertView = View.inflate(mContext,R.layout.ns_person_swipe_message_item, null);
			}
			 holder = getHolder(convertView);
			//设置数据
			 holder.swipeView.fastClose();
			 holder.name.setText((String)(mDataList.get(position).get("name")));
			 holder.content_text.setText((String)(mDataList.get(position).get("desc")));
			 holder.imageView.setBackgroundResource((Integer) (mDataList.get(position).get("img")));
			 holder.delete.setText("删除");
			 
			 
			 holder.content.setOnLongClickListener(new OnLongClickListener() {
				
				@Override
				public boolean onLongClick(View v) {
					
					Utils.remindUserDialog(SimpleCustomListViewActivity.this, "后悔了", "确定", "你确认删除?", new LoginDialogListenner() {
						
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
						showMyDialog((int)(Math.random()*2+1));
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
			TextView name,content_text; // 标题,时间,内容
			CircularImageView imageView;

			public ViewHolder(View convertView) {
				swipeView = (SwipeView) convertView
						.findViewById(R.id.swipeView);
				content = (LinearLayout) convertView
						.findViewById(R.id.content);
				delete = (TextView) convertView.findViewById(R.id.delete);

				name = (TextView) convertView.findViewById(R.id.name);
				imageView = (CircularImageView) convertView
						.findViewById(R.id.cicularimage);
				content_text = (TextView) convertView
						.findViewById(R.id.desc);

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
	
	
	@Override
	public void finish() {
		super.finish();
//		overridePendingTransition(R.anim.set_in_left,R.anim.set_out_right);
	}
}
