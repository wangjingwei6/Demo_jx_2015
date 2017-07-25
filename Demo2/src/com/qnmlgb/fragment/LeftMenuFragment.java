package com.qnmlgb.fragment;

import java.util.ArrayList;
import java.util.Arrays;

import com.custom.textview.shimmertextview.Shimmer;
import com.custom.textview.shimmertextview.ShimmerTextView;
import com.custom.textview.titanictextview.Titanic;
import com.custom.textview.titanictextview.TitanicTextView;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.ninexiu.wjw.R;
import com.qnmlgb.activity.MainUIActivity;
import com.qnmlgb.activity.PersonInfoActivity;
import com.qnmlgb.activity.PersonalCenterActivity;
import com.qnmlgb.application.SysApplication;
import com.qnmlgb.util.FileUtil;
import com.qnmlgb.util.SPUtil;
import com.qnmlgb.util.Utils;
import com.qnmlgb.view.CircularImageView;

import android.R.color;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class LeftMenuFragment extends BaseFragment implements OnItemClickListener,OnClickListener {

	private CircularImageView userIcon;
	private ListView listView;
	private TextView  desc, sex;
	private TitanicTextView nickName;
	private Button person;
	private ShimmerTextView left_desc;
	private int currentSelectItem; // 当前被选中菜单的变量

	private ArrayList<String> arraylist = new ArrayList<String>(Arrays.asList("个性装扮", "我的收藏", "我的相册", "设置中心"));
	private LeftMenuAdapter mAdapter;
	
	private MainUIActivity mainUI;
	private SlidingMenu slidingMenu;
	private Titanic titanic; //textview 水波纹
	private Shimmer mShimmer; //textview 闪光
	
	@Override
	protected View inflate(LayoutInflater inflater) {
		return inflater.inflate(R.layout.left_menu_layout, null);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mainUI = (MainUIActivity) getActivity();
		slidingMenu = mainUI.getSlidingMenu();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	//只执行一次
		View view = getView();
		initView(view);
		initOnclick();
		setMenuData();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		Log.e("CNM","=======onResume");
		initLeftPersonInfo();
		
		initTextStyle();
	
	}

	private void initTextStyle() {
		titanic= new Titanic();
		titanic.start(nickName);
		
		mShimmer = new Shimmer();
		mShimmer.start(left_desc);
	}

	private void initLeftPersonInfo() {
		
		if(SysApplication.isUserLogin){ //登录状态
			// 1.从保存的图片文件里取
			Bitmap userHead = FileUtil.getBitmap(FileUtil.mEditFileName);
			if (userHead != null && !userHead.equals("")) {
				userIcon.setImageBitmap(userHead);
			}
			
			SPUtil mSpUtil = new SPUtil(getActivity(),"userInfo");
			String userName = mSpUtil.getStringValue("userName", "未填写");
			if(!userName.toString().trim().equals("")&& !userName.equals("未填写")) {
				nickName.setText(userName);
			}

			String userSex = mSpUtil.getStringValue("userSex", "未填写");
			if(!userSex.equals("未填写")){
				sex.setText(userSex);
			}
		}else{
			nickName.setText("未登录");
			sex.setText("?");
		}
	
	}

	private void initOnclick() {
		userIcon.setOnClickListener(this);
		person.setOnClickListener(this);
		nickName.setOnClickListener(this);
	}

	private void initView(View view) {
		
		userIcon = (CircularImageView)view.findViewById(R.id.left_usericon);
		nickName = (TitanicTextView)view.findViewById(R.id.left_nickname);
		desc = (TextView) view.findViewById(R.id.left_desc);
		sex = (TextView) view.findViewById(R.id.left_sex);
		person = (Button) view.findViewById(R.id.left_person);
		left_desc = (ShimmerTextView)view.findViewById(R.id.left_desc);

		listView = (ListView) view.findViewById(R.id.left_listview);
//		listView.setBackgroundColor(getActivity().getResources().getColor(R.color.blue));
		listView.setCacheColorHint(color.transparent);
		listView.setDividerHeight(0);
		
		//判断手机分辨率密度比 动态设置listview位置
		float density = Utils.getDensity(getActivity());
		int top = 40;
		if(density==0.75){
			top = 40;
		}else if(density==1.0){
			top = 60;
		}else if(density==1.5){
			top = 90;
		}else if(density ==2.0){
			top = 120;
		}else if(density ==3.0){
			top = 180;
		}else if(density>3.0){
			top = 210;
		}
		listView.setPadding(0,top,0,0);
	}

	private void setMenuData() {
		currentSelectItem = 0;
		mAdapter = new LeftMenuAdapter(getActivity(), arraylist);
		
		listView.setAdapter(mAdapter);
		listView.setOnItemClickListener(this);
	}

	class LeftMenuAdapter extends BaseAdapter {

		private ArrayList<String> mDataList;
		private Context mContext;
		private LayoutInflater mLayoutInflater;

		public LeftMenuAdapter(Context context, ArrayList<String> arraylist) {
			this.mDataList = arraylist;
			this.mContext = context;
			mLayoutInflater = LayoutInflater.from(mContext);
		}

		@Override
		public int getCount() {
			return mDataList.size();
		}

		@SuppressLint("ViewHolder")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView tv = (TextView) View.inflate(mContext, R.layout.left_menu_item, null);
			tv.setText(mDataList.get(position).toString());

			// 当前获取的条目的位置和被选中的条目的位置一样, 应该把当前的条目置为红色
			tv.setEnabled(position == currentSelectItem);
			return tv;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return mDataList.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

	}



	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		currentSelectItem = position;
		mAdapter.notifyDataSetChanged();
		slidingMenu.toggle(); // 控制菜单显示还是隐藏, 如果当前菜单是显示, 就隐藏.		
	}

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.left_person:
			Intent personIntent  = new Intent(getActivity(),PersonalCenterActivity.class);
			getActivity().startActivity(personIntent);
			break;
		case R.id.left_usericon:
			Intent intent = new Intent(getActivity(),PersonInfoActivity.class);
			getActivity().startActivity(intent);
			break;
		case R.id.left_nickname:
			titanic.cancel();
			break;
		default:
			break;
		}
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		titanic.cancel();
		mShimmer.cancel();
	}

}
