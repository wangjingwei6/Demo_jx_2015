package com.qnmlgb.fragment;

import com.ninexiu.wjw.R;
import com.qnmlgb.util.Utils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;


/**
 * 基类
 * @author wsc
 *
 * @email jacen@wscnydx.com
 * @date 2014-3-17 涓嬪崍3:53:29
 */
public abstract class BaseFragment extends Fragment {

	protected View view;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			 ViewGroup container,  Bundle savedInstanceState) {
		view = inflate(inflater);
		setBackListener();
		return view;
	}
	
	private void setBackListener(){
		
		if(view.findViewById(R.id.left_btn) == null){
			return;
		}
		
		view.findViewById(R.id.left_btn).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				getActivity().overridePendingTransition(R.anim.anim_in_left, R.anim.anim_out_right);
			}
		});
	}
	
	
	protected abstract View inflate(LayoutInflater inflater);
	
}
