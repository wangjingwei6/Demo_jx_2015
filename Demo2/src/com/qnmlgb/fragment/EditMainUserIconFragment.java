//package com.qnmlgb.fragment;
//
//import com.ninexiu.wjw.R;
//import com.qnmlgb.view.CircularImageView;
//
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.widget.EditText;
//import android.widget.TextView;
//
//public class EditMainUserIconFragment extends BaseFragment implements OnClickListener{
//
//	private CircularImageView userIcon;
//	private EditText tvUserName;
//	private TextView tvUserSex;
//	private TextView tvUserAddress;
//
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//	}
//
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container,
//			Bundle savedInstanceState) {
//		return super.onCreateView(inflater, container, savedInstanceState);
//	}
//
//	@Override
//	public void onActivityCreated(Bundle savedInstanceState) {
//		super.onActivityCreated(savedInstanceState);
//
//		View view = getView();
//		initView(view);
//		initOnClick(view);
//		initData();
//
//	}
//
//	private void initView(View view) {
//		userIcon = (CircularImageView)view.findViewById(R.id.iv_user_head);
//		tvUserName = (EditText) view.findViewById(R.id.et_nick_name);
//		tvUserSex = (TextView) view.findViewById(R.id.tv_sex);
//		tvUserAddress = (TextView) view.findViewById(R.id.tv_address);
//	}
//
//	private void initOnClick(View view) {
////		view.findViewById(R.id.layout_nickname).setOnClickListener(this);
//		tvUserName.setOnClickListener(this);
//		view.findViewById(R.id.layout_sex).setOnClickListener(this);
//		view.findViewById(R.id.layout_address).setOnClickListener(this);
//	}
//
//	private void initData() {
//		
//	}
//
//
//	@Override
//	public void onClick(View v) {
//		
//	}
//
//	
//	@Override
//	protected View inflate(LayoutInflater inflater) {
//		return inflater.inflate(R.layout.edit_user_info_main, null);
//	}
//	
//}
