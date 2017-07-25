package com.qnmlgb.application;

import java.util.LinkedList;
import java.util.List;

import com.ninexiu.wjw.R;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.qnmlgb.login.LoginActivity;
import com.qnmlgb.util.FileUtil;
import com.qnmlgb.util.SPUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap.Config;
import android.widget.ImageView;

public class SysApplication extends Application {
	private List<Activity> mList = new LinkedList<Activity>();
	private static SysApplication instance;
	public static int musicIndex = 1;
	public static ImageLoader mImageLoader;
	public static Context applicationContext;
	public static DisplayImageOptions mOptions;
	public static ImageLoaderConfiguration configuration;
	
	public static boolean isUserLogin ;

	public SysApplication() {
		
	}

	public static SysApplication getInstance() {
		if (null == instance) {
			instance = new SysApplication();
		}
		return instance;
	}

	@Override
	public void onCreate() {
		super.onCreate();

		applicationContext = getApplicationContext();

		mImageLoader = getImageLoaderConfig();

		mOptions = new DisplayImageOptions.Builder()
				.bitmapConfig(Config.RGB_565)
				.showStubImage(R.drawable.default_404)
				.showImageForEmptyUri(R.drawable.default_404)
				.showImageOnFail(R.drawable.default_404)
				.showImageOnLoading(R.drawable.default_404).cacheOnDisc()
				.imageScaleType(ImageScaleType.IN_SAMPLE_INT).build();
		
		
		SPUtil sp = new SPUtil(applicationContext,FileUtil.SAVE_USER_SPNAME);
		isUserLogin = sp.getBooleanValue("isUserLogin", false);
	}

	public static ImageLoader getImageLoaderConfig() {
		if (mImageLoader == null && applicationContext != null) {
			mImageLoader = ImageLoader.getInstance();
			configuration = new ImageLoaderConfiguration.Builder(
					applicationContext).threadPoolSize(1)
					.memoryCache(new WeakMemoryCache()).build();
			mImageLoader = ImageLoader.getInstance();
			mImageLoader.init(configuration);
		}

		return mImageLoader;
	}

	// add Activity
	@SuppressWarnings("unchecked")
	public void addActivity(Activity activity) {
		mList.add(activity);
	}

	public void exit() {
		try {
			for (Activity activity : mList) {
				if (activity != null)
					activity.finish();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.exit(0);
		}
	}

	public void onLowMemory() {
		super.onLowMemory();
		System.gc();
	}

	public static void displayImage(ImageView imageView, String url) {
		mImageLoader.displayImage(url, imageView, mOptions, null);
	}

	public static void displayImage(ImageView imageView, String url,
			ImageLoadingListener listener) {
		mImageLoader.displayImage(url, imageView, mOptions, listener);
	}
}