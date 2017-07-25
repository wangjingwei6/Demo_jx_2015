package com.qnmlgb.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

@SuppressLint("NewApi")
public class CustomVpSimpleFragment extends Fragment{

	private String mTiles;
	public static final String BUNDLE_TITLE = "title";
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			
		Bundle bundle = getArguments();
		if(bundle!=null){
			mTiles = bundle.getString(BUNDLE_TITLE);
		}

		TextView tv = new TextView(getActivity());
		tv.setText(mTiles);
		tv.setGravity(Gravity.CENTER);
			
		return tv;
		
	}
	
	
	
	/**
	 *   通过title 初始化一个fragment
	 * @param title
	 * @return
	 */
	public static CustomVpSimpleFragment newInstance(String title){
		Bundle bundle = new Bundle();
		bundle.putString(BUNDLE_TITLE,title);
		
		CustomVpSimpleFragment fragmengt =  new CustomVpSimpleFragment();
		fragmengt.setArguments(bundle);
		
		return fragmengt;
		
		
	}
	

}
