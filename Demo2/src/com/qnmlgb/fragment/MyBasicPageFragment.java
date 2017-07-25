package com.qnmlgb.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ninexiu.wjw.R;
import com.qnmlgb.activity.PersonInfoActivity;

public class MyBasicPageFragment extends Fragment{
	private LinearLayout linear_person_info;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		View content = getView();
		initView(content);
		setListenner();

	}

	private void initView(View content) {
		linear_person_info = (LinearLayout) content.findViewById(R.id.linear_person_info);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View content=inflater.inflate(R.layout.page_basic_info, container, false);
		return content;
	}
	
	
	
	public void setListenner() {
		linear_person_info.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
					Intent intent = new Intent(getActivity(),PersonInfoActivity.class);
					getActivity().startActivity(intent);
			}
		});
	}
	


	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	
}
