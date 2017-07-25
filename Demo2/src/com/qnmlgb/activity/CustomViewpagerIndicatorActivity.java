package com.qnmlgb.activity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.ninexiu.wjw.R;
import com.qnmlgb.fragment.CustomVpSimpleFragment;
import com.qnmlgb.view.CustomViewpagerIndicator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

public class CustomViewpagerIndicatorActivity extends BaseActivity{

	private ViewPager mViewPager;
	private CustomViewpagerIndicator mIndicator;
	private List<String>mTitles = Arrays.asList("短信1","收藏2","推荐3","短信4","收藏5","推荐6","短信7","收藏8","推荐9");
	private List<CustomVpSimpleFragment>mContents = new ArrayList<CustomVpSimpleFragment>();
	private FragmentPagerAdapter mAdapter;
	
	@Override
	protected void setContentView() {
		setContentView(R.layout.custom_viewpager_indicator_act);
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		initViews();
		initDatas();
		mIndicator.setVisibleTabCount(4);
		mIndicator.setTabItemTitles(mTitles);
		
		mViewPager.setAdapter(mAdapter);
		mIndicator.setViewPager(mViewPager,0);
//		mViewPager.setOnPageChangeListener(new OnPageChangeListener() { 
//			
//			@Override
//			public void onPageSelected(int arg0) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			@Override
//			public void onPageScrolled(int arg0, float arg1, int arg2) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			@Override
//			public void onPageScrollStateChanged(int arg0) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
	}

	private void initViews() {
		mViewPager 	= (ViewPager)findViewById(R.id.id_viewpager);
		mIndicator  = (CustomViewpagerIndicator)findViewById(R.id.id_indicator);
	}
	
	
	private void initDatas() {
		
		for(String title:mTitles){
			CustomVpSimpleFragment fragment = CustomVpSimpleFragment.newInstance(title);
			mContents.add(fragment);
		}
		
		mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
			
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return mContents.size();
			}
			
			@Override
			public Fragment getItem(int position) {
				// TODO Auto-generated method stub
				return mContents.get(position);
			}
		};
		
	}

	
	
	
}
