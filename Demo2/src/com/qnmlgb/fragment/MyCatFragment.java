package com.qnmlgb.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.ninexiu.wjw.R;
import com.qnmlgb.adapter.CarGridViewAdapter;


public class MyCatFragment extends Fragment {

	
	private String []nameIDs ;
	private int [] smallIcons;
	private int [] bigIcons;
	
	private GridView car_gridview;
	private CarGridViewAdapter mCarGridViewAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater,
			 ViewGroup container,  Bundle savedInstanceState) {
		View content = inflater.inflate(R.layout.ns_my_carport_layout,
				container, false);
		initView(content);
		initData();
		setData();
		return content;
	}
	
	private void initView(View content) {
		car_gridview = (GridView)content.findViewById(R.id.car_gridview);
	}

	
	private void initData() {
		nameIDs = new String[]{"标志307","马自达","奥迪A4","雷克萨斯","宝马Z4","奔驰SLK","路虎","兰博基尼","保时捷",};
		smallIcons = new int []
				{R.drawable.biaozhi_title,R.drawable.mazida_title,R.drawable.aodi_title,
				R.drawable.leikesasi_title,R.drawable.baoma_title,R.drawable.benzi_tltle,
				R.drawable.luhu_title,R.drawable.lanbojini_title,R.drawable.baoshijie_title,};
		bigIcons = new int []
				{R.drawable.biaozhi,R.drawable.mazida,R.drawable.aodi,
				R.drawable.leikesasi,R.drawable.baoma,R.drawable.benzi,
				R.drawable.luhu,R.drawable.lanbojini,R.drawable.baoshijie,};
	}

	private void setData() {
		mCarGridViewAdapter = new CarGridViewAdapter(getActivity(), nameIDs, smallIcons, bigIcons);
		car_gridview.setAdapter(mCarGridViewAdapter);
	}
	
	@Override
	public void onActivityCreated( Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		System.gc();
	}
	

}
