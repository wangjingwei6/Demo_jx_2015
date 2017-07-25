package com.qnmlgb.util;

import android.content.Context;
import android.graphics.Bitmap.Config;
import android.widget.ImageView;

import com.ninexiu.wjw.R;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class MyImageLoaderUtil {

	private  DisplayImageOptions mOptions;
	private static ImageLoaderConfiguration configuration = null;
	private static ImageLoader mImageLoader = null;
	private static Context mContext = null;
	
	private static MyImageLoaderUtil mMyImageLoaderUtil = null;

	private MyImageLoaderUtil(Context context) {
		this.mContext = context;
		mImageLoader = getImageLoaderConfig();	  
	}

	public static MyImageLoaderUtil getImageLoadUtil(Context context) {
		if (null == mMyImageLoaderUtil) {
			mMyImageLoaderUtil = new MyImageLoaderUtil(context);
		} else {
		}
		
		return mMyImageLoaderUtil;
	}

	public void init() {
		mOptions = new DisplayImageOptions.Builder()
				.bitmapConfig(Config.RGB_565)
				.showStubImage(R.drawable.default_404)
				.showImageForEmptyUri(R.drawable.default_404)
				.showImageOnFail(R.drawable.default_404)
				.showImageOnLoading(R.drawable.default_404).cacheOnDisc()
				.imageScaleType(ImageScaleType.IN_SAMPLE_INT).build();
	}

	public static ImageLoader getImageLoaderConfig() {
		if (mImageLoader == null) {
			mImageLoader = ImageLoader.getInstance();
			configuration = new ImageLoaderConfiguration.Builder(mContext).threadPoolSize(1)
					.memoryCache(new WeakMemoryCache()).build();
			mImageLoader = ImageLoader.getInstance();
			mImageLoader.init(configuration);
		}
		return mImageLoader;
	}

	public  void displayImage(ImageView imageView, String url) {
		mImageLoader.displayImage(url, imageView, mOptions, null);
	}

	public  void displayImage(ImageView imageView, String url,
			ImageLoadingListener listener) {
		mImageLoader.displayImage(url, imageView, mOptions, listener);
	}
	
	//============================================================================

}
