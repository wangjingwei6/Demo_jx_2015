package com.qnmlgb.fragment;

import java.util.ArrayList;

import com.ninexiu.sixninexiu.view.photoview.HackyViewPager;
import com.ninexiu.sixninexiu.view.photoview.PhotoView;
import com.ninexiu.wjw.R;
import com.qnmlgb.util.MyImageLoaderUtil;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class PersonPhotoFragment extends BaseFragment{

	private  ArrayList<String> photoList ;
//	private int currentPosition;
	
	private HackyViewPager mViewpager;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		View view = getView();
		
		initView(view);
		initData();
	}
	
	
	private void initView(View view) {
		mViewpager = (HackyViewPager)view.findViewById(R.id.album_viewpager);
	}
	
	
	private void initData() {
//		photoList.add("http://img.popoho.com/allimg/120505/155K040F-5.jpg");
//		photoList.add("http://www.jf258.com/uploads/2014-09-08/104734923.jpg");
//		photoList.add("http://www.jf258.com/uploads/2014-09-08/104735285.jpg");
		Intent intent =getActivity().getIntent();
		if(intent!=null){
			photoList  = intent.getStringArrayListExtra("photoList");
		}
		
		MyPagerViewAdapter  mMyPagerViewAdapter =  new  MyPagerViewAdapter();
		mViewpager.setAdapter(mMyPagerViewAdapter);

//		mViewpager.setOnPageChangeListener(new OnPageChangeListener() {
//
//			public void onPageSelected(int position) {
//				
//			}
//
//			public void onPageScrolled(int arg0, float arg1, int arg2) {
//
//			}
//
//			public void onPageScrollStateChanged(int arg0) {
//
//			}
//		});
	
	}
	
	
	
	class MyPagerViewAdapter extends PagerAdapter{

		private LayoutInflater mLayoutInflater;
		
		public MyPagerViewAdapter() {
			mLayoutInflater = LayoutInflater.from(getActivity());
		}
		
		@Override
		public int getCount() {
			if(photoList!=null){
				return photoList.size();
			}
			return 0;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}
	
		@Override
		public void destroyItem(View viewpager, int position, Object object) {
			((ViewPager)viewpager).removeView((View)object);
		}
		
		@Override
		public int getItemPosition(Object object) {
			// TODO Auto-generated method stub
			return POSITION_NONE;
		}
		
		
		@Override
		public Object instantiateItem(View container, int position) {
			
			RelativeLayout relativeLayout = null;
			if(relativeLayout==null){
				relativeLayout  =  (RelativeLayout)mLayoutInflater.inflate(R.layout.viewpageritem_image_layout,null);
			}
			mViewHolder = getViewHolder(relativeLayout);
			
			
//			final PhotoView imageView = (PhotoView) relativeLayout.findViewById(R.id.iv_photo);
			
			//ªÒ»°Õº∆¨µÿ÷∑
//			int number = (int)(Math.random()*2+1);
			String path  = photoList.get(position);
			MyImageLoaderUtil imageLoadUtil = MyImageLoaderUtil.getImageLoadUtil(getActivity());
			imageLoadUtil.init();
			imageLoadUtil.displayImage(mViewHolder.imageView, path);
			
			
			 ((ViewPager) container).addView(relativeLayout);
			return relativeLayout;
		}
		
		public ViewHodler getViewHolder(View convertView) {
			ViewHodler holder = (ViewHodler) convertView.getTag();
			if (holder == null) {
				holder = new ViewHodler(convertView);
				convertView.setTag(holder);
			}
			return holder;
		}
		
		ViewHodler mViewHolder;
		
		class ViewHodler {
			public ImageView imageView;

			public ViewHodler(View convertView) {
				imageView = (PhotoView) convertView.findViewById(R.id.iv_photo);
			}
		}
		
		
	}
	
	@Override
	protected View inflate(LayoutInflater inflater) {
		// TODO Auto-generated method stub
		return	inflater.inflate(R.layout.mutiltouch_person_photo_layout, null);
				
	}
	
}
