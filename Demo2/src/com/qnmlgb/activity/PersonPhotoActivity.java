package com.qnmlgb.activity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ninexiu.sixninexiu.view.photoview.HackyViewPager;
import com.ninexiu.sixninexiu.view.photoview.PhotoView;
import com.ninexiu.sixninexiu.view.photoview.PhotoViewAttacher.OnPhotoTapListener;
import com.ninexiu.wjw.R;
import com.qnmlgb.util.FileUtil;
import com.qnmlgb.util.MyImageLoaderUtil;
import com.qnmlgb.util.MyToast;
import com.qnmlgb.util.Utils;
import com.qnmlgb.view.CircularImageView;

public class PersonPhotoActivity extends Activity {

	private  ArrayList<String> photoList ;
	private String adapterType;
	
	private HackyViewPager mViewpager;
	
	private Intent intent;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setTheme(R.style.AppThemeNoNavigation);
		setContentView(R.layout.mutiltouch_person_photo_layout);
		initView();
		initData();
	}
	
	private void initView() {
		mViewpager = (HackyViewPager)findViewById(R.id.album_viewpager);
	}
	
	
	private void initData() {
		intent = getIntent();
		if(intent!=null){
			adapterType = intent.getStringExtra("type");
		}
		
		MyPagerViewAdapter  mMyPagerViewAdapter =  new  MyPagerViewAdapter(adapterType);
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
		private String type;
		
		public MyPagerViewAdapter(String adapterType) {
			mLayoutInflater = LayoutInflater.from(PersonPhotoActivity.this);
			this.type = adapterType;
		}
		
		@Override
		public int getCount() {
			if(photoList!=null){
				return photoList.size();
			}
			return 1;
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
			
			
			if(type!=null){
				if(type.equals("local")){
					//1.从保存的图片文件里取
					Bitmap userHead = FileUtil.getBitmap(FileUtil.mEditFileName);
					if(userHead!=null&&!userHead.equals("")){
						mViewHolder.imageView.setImageBitmap(userHead);
					}
					implementPhotoListener(mViewHolder.imageView,FileUtil.mEditFileName,false);
					
				}else if(type.equals("fromNet")){
					
					photoList  = intent.getStringArrayListExtra("photoList"); //网络地址
					//获取图片地址
//					int number = (int)(Math.random()*2+1);
					String path  = photoList.get(position);
					MyImageLoaderUtil imageLoadUtil = MyImageLoaderUtil.getImageLoadUtil(PersonPhotoActivity.this);
					imageLoadUtil.init();
					imageLoadUtil.displayImage(mViewHolder.imageView, path);
					
					implementPhotoListener(mViewHolder.imageView,path,true);
				}else if(type.equals("gallery")){
					
					int imgID = intent.getIntExtra("photoId",R.drawable.default_404);
					mViewHolder.imageView.setImageResource(imgID);
					implementPhotoListener(mViewHolder.imageView,"gallery_"+imgID+FileUtil.mFileName,true);
					
				}else if(type.equals("gridview")){
					
					int imgID = intent.getIntExtra("photoId",R.drawable.default_404);
					mViewHolder.imageView.setImageResource(imgID);
					implementPhotoListener(mViewHolder.imageView,"gridview_"+imgID+FileUtil.mFileName,true);
					
				}else{
					MyToast.MakeToast(PersonPhotoActivity.this,"未知错误");
				}
			}
			
			
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
			public PhotoView imageView;

			public ViewHodler(View convertView) {
				imageView = (PhotoView) convertView.findViewById(R.id.iv_photo);
			}
		}
		
		
	}
	
	public void implementPhotoListener(final PhotoView imageView,final String path,boolean isShowPop) {
		
		imageView.setOnPhotoTapListener(new OnPhotoTapListener() {
					@Override
					public void onPhotoTap(View view, float x, float y) {
//						Intent intent = new Intent(PersonPhotoActivity.this,
//								PersonalCenterActivity.class);
//						startActivity(intent);
						PersonPhotoActivity.this.finish();
					}
				});
		
		if(isShowPop){
			imageView.setOnLongClickListener(new OnLongClickListener() {
				
				@Override
				public boolean onLongClick(View v) {
					imageView.setDrawingCacheEnabled(true);
					Bitmap drawingCache = imageView.getDrawingCache();
					String pathName = path.substring(path.lastIndexOf("/")+1);  //从图片的网络地址截取作为  文件名保存地址
					
					// 弹出  popwindow
					upPopWindow(drawingCache,pathName);
//					imageView.setDrawingCacheEnabled(false);
					return true;
				}
			});
		}
		
	}

	private View popwindowView;
	private PopupWindow mPopupWindow;
	private CircularImageView saveImg;
	private TextView cancel;
	
	private void upPopWindow(Bitmap bmp , String fileName) {
		if(popwindowView==null){
			popwindowView = getLayoutInflater().inflate(R.layout.popupwindow_albumbrower, null);
		}
		
		if(mPopupWindow==null){
			mPopupWindow =  new PopupWindow(popwindowView,ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			mPopupWindow.setFocusable(true);
			mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
			mPopupWindow.setOutsideTouchable(true);
			
			cancel = (TextView) popwindowView.findViewById(R.id.tv_cancel);
			saveImg = (CircularImageView)popwindowView.findViewById(R.id.save_photo);
			
			setPopWindowListener(cancel,saveImg,bmp,fileName);
		}
		
		mPopupWindow.showAtLocation(popwindowView,Gravity.BOTTOM,0, 0);
	}

	/**
	 * 
	 * @param cancel2    	取消
	 * @param saveImg2 		保存图片
	 * @param bmp			需要保存的图片
	 * @param pathName		文件名
	 */
	private void setPopWindowListener(TextView cancel2,CircularImageView saveImg2,final Bitmap bmp,final String fileName) {
		
		cancel2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mPopupWindow != null) {
					mPopupWindow.dismiss();
				}
			}
		});
		
		
		saveImg2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				try {
					FileUtil.saveFile(bmp, fileName);
					
					//保存后开启广播刷新操作
					File myTargetFile = new File(FileUtil.SAVE_REAL_PATH, fileName);
					Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
					Uri uri = Uri.fromFile(myTargetFile);
					intent.setData(uri);
					PersonPhotoActivity.this.sendBroadcast(intent);
					
					MyToast.MakeToast(PersonPhotoActivity.this, "保存成功");
					
					if (mPopupWindow != null) {
						mPopupWindow.dismiss();
					}
					
				} catch (IOException e) {
					e.printStackTrace();
					MyToast.MakeToast(PersonPhotoActivity.this, "保存失败");
				}
				
			}
		});
		
	}

//	@Override
//	protected void setContentView() {
//		setContentView(R.layout.mutiltouch_person_photo_layout);
//	}
//	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	@Override
	public void finish() {
		super.finish();
		Utils.scaleOutActivityAnimation(this);
	}
	
}
