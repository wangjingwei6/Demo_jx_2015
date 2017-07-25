package com.qnmlgb.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.ninexiu.wjw.R;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.qnmlgb.service.MediaPlayerService;
import com.qnmlgb.service.MusicService;
import com.qnmlgb.util.MyImageLoaderUtil;
import com.qnmlgb.util.MyToast;
import com.qnmlgb.util.Utils;
import com.qnmlgb.view.MarqueeText;

public class ShowLoveFragment extends Fragment {
	private Context mContext;
	private ImageView showloveimg;
	// private Dialog mDialog;
	private View progress;
	private TextView next;
	private MarqueeText tv_ljz;
	private MarqueeText tv_ahm;

	private int[] imgs = new int[] { R.drawable.flowermore2,
			R.drawable.flowermore4 };
	private String img_one = "http://img1.3lian.com/img2012/2/0220/7/d/37.jpg";
	private String img_two = "http://img1.3lian.com/img2012/2/0220/7/d/35.jpg";
	private String img_three = "http://pic11.nipic.com/20101127/2531170_092836072754_2.jpg";
	private String imagePath = "";

	public final int TYPE_ONEIMAGE = 1;
	public final int TYPE_TWOIMAGE = 2;
	public final int TYPE_THREEIMAGE = 3;

	private static String type = null;
	private static boolean musictype = false; // false 爱很美    true 死结
	private String pushType = "";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mContext = getActivity();

		Intent intent = getActivity().getIntent();
		if (intent != null) {
			pushType = intent.getStringExtra("isPush");

			if (pushType!=null&&pushType.equals("true")) {
				Intent ahmIntent = new Intent(getActivity(), MediaPlayerService.class);
				getActivity().startService(ahmIntent);
			}
		}

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.love_background_layout, null);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		// getActivity().requestWindowFeature(Window.FEATURE_NO_TITLE);
		// getActivity().setTheme(R.style.AnimationActivity);
		View content = getView();
		initView(content);
		initData();
	}

	private void initData() {
		if (null != type && type.equals("push")) {
			showloveimg.setBackgroundResource(R.drawable.flowermore2);
		} else {
			// int imgIndex = (int) (Math.random()*2);
			// showloveimg.setBackgroundResource(imgs[imgIndex]);

			// 换用网络请求方式: ImageLoader
			MyImageLoaderUtil imageLoadUtil = MyImageLoaderUtil
					.getImageLoadUtil(getActivity());
			imageLoadUtil.init();
			getImagePath();
			// imageLoadUtil.displayImage(showloveimg, imagePath);
			imageLoadUtil.displayImage(showloveimg, imagePath,
					new ImageLoadingListener() {

						@Override
						public void onLoadingStarted(String arg0, View arg1) {
							progress.setVisibility(View.VISIBLE);
							progress.startAnimation(AnimationUtils
									.loadAnimation(getActivity(),
											R.anim.loading_animation));
						}

						@Override
						public void onLoadingFailed(String arg0, View arg1,
								FailReason arg2) {
							progress.setVisibility(View.GONE);
							progress.clearAnimation();
							MyToast.MakeToast(getActivity(),
									"出现未知的网络请求错误,图片下载失败");
						}

						@Override
						public void onLoadingComplete(String arg0, View arg1,
								Bitmap arg2) {
							progress.setVisibility(View.GONE);
							progress.clearAnimation();
						}

						@Override
						public void onLoadingCancelled(String arg0, View arg1) {
							progress.setVisibility(View.GONE);
							progress.clearAnimation();
						}
					});
		}

		next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				if(pushType!=null&&pushType.equals("true")){  //推送 点击进来的逻辑
					
					pushType = "";
					musictype  = true;  //下次点击播放 	死结
					
					tv_ljz.setVisibility(View.GONE);
					tv_ahm.setVisibility(View.VISIBLE);
					
					Intent ahmIntent = new Intent(getActivity(),
							MediaPlayerService.class);
					getActivity().stopService(ahmIntent);
					
					Intent ljzIntent = new Intent(getActivity(),
							MusicService.class);
					getActivity().startService(ljzIntent);
					
					
				}else{
					if (musictype == false) {
						musictype = true;
						
						tv_ljz.setVisibility(View.GONE);
						tv_ahm.setVisibility(View.VISIBLE);
						
						Intent ahmIntent = new Intent(getActivity(),
								MusicService.class);
						getActivity().startService(ahmIntent);
						
						Intent ljzIntent = new Intent(getActivity(),
								MediaPlayerService.class);
						getActivity().stopService(ljzIntent);
						
					} else {
						musictype = false;
						
						tv_ljz.setVisibility(View.VISIBLE);
						tv_ahm.setVisibility(View.GONE);
						
						Intent ljzIntent = new Intent(getActivity(),
								MediaPlayerService.class);
						getActivity().startService(ljzIntent);
						
						Intent ahmIntent = new Intent(getActivity(),
								MusicService.class);
						getActivity().stopService(ahmIntent);
						
					}
					
				}
				

			}
		});

	}

	private void getImagePath() {
		int x = (int) (Math.random() * 2 + 1);
		switch (x) {
		case TYPE_ONEIMAGE:
			imagePath = img_one;
			break;
		case TYPE_TWOIMAGE:
			imagePath = img_two;
			break;
		case TYPE_THREEIMAGE:
			imagePath = img_three;
			break;
		default:
			break;
		}
	}

	private void initView(View view) {
		showloveimg = (ImageView) view.findViewById(R.id.showloveimg);
		next = (TextView) view.findViewById(R.id.next);
		tv_ljz = (MarqueeText) view.findViewById(R.id.tv_ljz);
		tv_ahm = (MarqueeText) view.findViewById(R.id.tv_ahm);

		progress = (View) view.findViewById(R.id.custom_progress);

	}

	public static void setType(String push) {
		type = push;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		musictype = false;
		Intent ahmIntent = new Intent(getActivity(), MusicService.class);
		getActivity().stopService(ahmIntent);
		Intent ljzIntent = new Intent(getActivity(), MediaPlayerService.class);
		getActivity().stopService(ljzIntent);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		musictype = false;
		Intent ahmIntent = new Intent(getActivity(), MusicService.class);
		getActivity().stopService(ahmIntent);
		Intent ljzIntent = new Intent(getActivity(), MediaPlayerService.class);
		getActivity().stopService(ljzIntent);
	}

}
