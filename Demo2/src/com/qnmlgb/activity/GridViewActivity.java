package com.qnmlgb.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.qnmlgb.view.ScaleLinearLayout;
import com.qnmlgb.view.ScaleLinearLayout.OnLayoutClickListener;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.ninexiu.wjw.R;
import com.qnmlgb.bean.RecycleViewBean;
import com.qnmlgb.util.Utils;

@SuppressLint("CutPasteId")
public class GridViewActivity extends Activity implements OnClickListener,
		OnLayoutClickListener {
	private String[] strIds = new String[] { "全部", "安徽", "四川", "吉林", "北京",
			"福建", "贵州", "黑龙江", "天津", "江西", "云南", "上海", "河北", "山东", "西藏", "江苏",
			"山西", "河南", "陕西", "浙江", "内蒙古", "湖北", "甘肃" };

	private int[] moreIconID = new int[] { R.drawable.ns_live_more_charge_nor,
			R.drawable.ns_live_more_deluxe_nor,
			R.drawable.ns_live_more_edit_nor, R.drawable.ns_live_more_fans_nor,
			R.drawable.ns_live_more_luck_nor, R.drawable.ns_live_more_luck_pre,
			R.drawable.ns_live_more_service_nor,
			R.drawable.ns_live_more_service_pre,
			R.drawable.ns_live_more_shop_nor,
			R.drawable.ns_live_more_subtitle_nor,
			R.drawable.ns_live_more_subtitle_pre,
			R.drawable.ns_live_more_zodiac_nor,
			R.drawable.ns_live_more_zodiac_pre };
	private String[] moreTextID = new String[] { "充值", "商城", "关注", "粉丝榜", "广播",
			"弹幕", "修改昵称", "幸运转盘", "客服中心", "个人中心", "特效设置", "排行榜", "搜索" };

	private GridView mGridView;
	private GridView mMoreGridView_two;

	private Button confirm;
	private TextView province;
	private TextView city;
	private TextView hospital;
	private TextView restore;
	private ImageButton province_but;
	private ImageButton city_but;
	private ImageButton hospital_but;

	private boolean province_isShow = true;
	private boolean city_isShow = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setTheme(R.style.AnimationTheme);
		setContentView(R.layout.gridview_layout);

		initView();
		initData();
		setOnClick();
	}

	private void initView() {
		province = (TextView) findViewById(R.id.province_text);
		city = (TextView) findViewById(R.id.city_text);
		hospital = (TextView) findViewById(R.id.hospital_text);

		confirm = (Button) findViewById(R.id.confirm);
		Utils.addRippleEffect(confirm);
		mGridView = (GridView) findViewById(R.id.live_more_grid);
		mMoreGridView_two = (GridView) findViewById(R.id.live_more_grid_two);

		province_but = (ImageButton) findViewById(R.id.province_but);
		city_but = (ImageButton) findViewById(R.id.city_but);
		hospital_but = (ImageButton) findViewById(R.id.hospital_but);
		restore = (TextView) findViewById(R.id.restore);

		province_but.setBackgroundResource(R.drawable.btn_vip_white_expand);
		city_but.setBackgroundResource(R.drawable.btn_vip_white_expand);
		hospital_but.setBackgroundResource(R.drawable.btn_vip_white_expand);
		mGridView.setVisibility(View.VISIBLE);
		mMoreGridView_two.setVisibility(View.VISIBLE);

	}

	private void initData() {
		
		// 创建适配器
		GridViewAdapter mGridViewAdapter = new GridViewAdapter(this, strIds);
		mGridView.setAdapter(mGridViewAdapter);
		ScaleGridViewAdapter2 mGridViewAdapter2 = new ScaleGridViewAdapter2(
				GridViewActivity.this, moreIconID, moreTextID);
		
		mGridViewAdapter2.setOnLayoutClickListenear(this);
		mMoreGridView_two.setAdapter(mGridViewAdapter2);
	}

	private void setOnClick() {
		province_but.setOnClickListener(this);
		city_but.setOnClickListener(this);
		restore.setOnClickListener(this);
	}

	class GridViewAdapter extends BaseAdapter {
		private String[] strs;
		private Context mContext;

		public GridViewAdapter(Context context, String[] strIds) {
			this.strs = strIds;
			this.mContext = context;
		}

		@Override
		public int getCount() {
			return strs.length;
		}

		@Override
		public Object getItem(int position) {
			return strs[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view;
			final ViewHolder mViewHolder;
			if (convertView == null) {// 没有缓存
				view = View.inflate(mContext, R.layout.grid_view, null);
				mViewHolder = new ViewHolder();
				mViewHolder.mTextView = (TextView) view.findViewById(R.id.tv);
				view.setTag(mViewHolder);
			} else {
				view = convertView;
				mViewHolder = (ViewHolder) view.getTag();
			}

			mViewHolder.mTextView.setText(strs[position]);
			mViewHolder.mTextView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					province.setText(mViewHolder.mTextView.getText().toString());
				}
			});

			return view;
		}
	};

	static class ViewHolder {
		private TextView mTextView;
	}

	// =============================================================

	public class ScaleGridViewAdapter2 extends BaseAdapter {

		private int[] moreIconID;
		private String[] moreTextID;

		private Context mContext;
		private OnLayoutClickListener mOnLayoutClickListener;

		public ScaleGridViewAdapter2(Context mContext, int[] iconIDs,
				String[] textIDs) {
			// TODO Auto-generated constructor stub
			this.mContext = mContext;
			this.moreIconID = iconIDs;
			this.moreTextID = textIDs;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return moreTextID.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View view;
			ScaleViewHolder holder;
			if (convertView == null) {
				view = View.inflate(mContext, R.layout.live_room_more_griditem,
						null);
				holder = new ScaleViewHolder();
				holder.mScaleLinearLayout = (ScaleLinearLayout) view
						.findViewById(R.id.more_item);
				holder.imageView = (ImageView) view
						.findViewById(R.id.more_icon);
				holder.textView = (TextView) view.findViewById(R.id.more_text);
				view.setTag(holder);
			} else {
				view = convertView;
				holder = (ScaleViewHolder) view.getTag();
			}
			holder.mScaleLinearLayout.setTag(R.id.tag_live_more, position);
			holder.mScaleLinearLayout
					.setOnLayoutClickListener(mOnLayoutClickListener);
			holder.imageView.setImageResource(moreIconID[position]);
			holder.textView.setText(moreTextID[position]);
			return view;
		}

		class ScaleViewHolder {

			public ImageView imageView;
			public ScaleLinearLayout mScaleLinearLayout;
			public TextView textView;

		}
		
		public void setOnLayoutClickListenear(OnLayoutClickListener mOnLayoutClickListener){
			this.mOnLayoutClickListener = mOnLayoutClickListener;
		}


	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.province_but:
			if (!province_isShow) {
				province_but
						.setBackgroundResource(R.drawable.btn_vip_white_put);
				mGridView.setVisibility(View.VISIBLE);
				province_isShow = true;
			} else {
				province_but
						.setBackgroundResource(R.drawable.btn_vip_white_expand);
				mGridView.setVisibility(View.GONE);
				province_isShow = false;
			}
			break;
			
		case R.id.city_but:
			if(!city_isShow){
				city_but.setBackgroundResource(R.drawable.btn_vip_white_put);
				mMoreGridView_two.setVisibility(View.VISIBLE);
				city_isShow =true;
			}else{
				city_but.setBackgroundResource(R.drawable.btn_vip_white_expand);
				mMoreGridView_two.setVisibility(View.GONE);
				city_isShow =false;
			}
			
			break;
		case R.id.restore:
			province.setText("未选择");

			break;

		default:
			break;
		}
	}

	@Override
	public void onLayoutClick(View v) {

		int position = ((Integer) v.getTag(R.id.tag_live_more)).intValue();
		switch (position) {
		case 0:
			Toast.makeText(GridViewActivity.this,"触摸了 :"+position,Toast.LENGTH_SHORT).show();
			break;
		case 1:
			Toast.makeText(GridViewActivity.this,"触摸了 :"+position,Toast.LENGTH_SHORT).show();
			break;
		case 2:
			Toast.makeText(GridViewActivity.this,"触摸了 :"+position,Toast.LENGTH_SHORT).show();
			break;
		case 3:
			Toast.makeText(GridViewActivity.this,"触摸了 :"+position,Toast.LENGTH_SHORT).show();
			break;
		case 4:
			Toast.makeText(GridViewActivity.this,"触摸了 :"+position,Toast.LENGTH_SHORT).show();
			break;
		case 5:
			Toast.makeText(GridViewActivity.this,"触摸了 :"+position,Toast.LENGTH_SHORT).show();
			break;
		case 6:
			Toast.makeText(GridViewActivity.this,"触摸了 :"+position,Toast.LENGTH_SHORT).show();
			break;
		case 7:
			Toast.makeText(GridViewActivity.this,"触摸了 :"+position,Toast.LENGTH_SHORT).show();
			break;
		case 8:
			Toast.makeText(GridViewActivity.this,"触摸了 :"+position,Toast.LENGTH_SHORT).show();
			break;
		case 9:
			Toast.makeText(GridViewActivity.this,"触摸了 :"+position,Toast.LENGTH_SHORT).show();
			break;
		case 10:
			Toast.makeText(GridViewActivity.this,"触摸了 :"+position,Toast.LENGTH_SHORT).show();
			break;
		case 11:
			Toast.makeText(GridViewActivity.this,"触摸了 :"+position,Toast.LENGTH_SHORT).show();
			break;
		case 12:
			Toast.makeText(GridViewActivity.this,"触摸了 :"+position,Toast.LENGTH_SHORT).show();
			break;
		case 13:
			Toast.makeText(GridViewActivity.this,"触摸了 :"+position,Toast.LENGTH_SHORT).show();
			break;

		default:
			break;
		}
	}

}
