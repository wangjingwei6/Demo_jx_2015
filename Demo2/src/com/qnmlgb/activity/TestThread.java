package com.qnmlgb.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.qnmlgb.util.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.webkit.WebView;
import android.widget.ImageView;

public class TestThread extends Thread {

	private String url;
	private Handler handler;
	private WebView mWebView;
	private ImageView mImageView;

	public TestThread(String url, Handler handler, WebView webview) {
		this.url = url;
		this.handler = handler;
		this.mWebView = webview;
	}

	public TestThread(String url, Handler handler, ImageView imageView) {
		this.url = url;
		this.handler = handler;
		this.mImageView = imageView;
	}

//	@Override
//	public void run() {
//		super.run();
//		try {
//			URL httpUrl = new URL(url);
//			HttpURLConnection mHttpURLConnection = (HttpURLConnection) httpUrl
//					.openConnection();
//			mHttpURLConnection.setReadTimeout(5000);
//			mHttpURLConnection.setRequestMethod("GET");
//			final StringBuffer sb = new StringBuffer();
//			BufferedReader bf = new BufferedReader(new InputStreamReader(
//					mHttpURLConnection.getInputStream()));
//			String str;
//			while ((str = bf.readLine()) != null) {
//				sb.append(str);
//			}
//
//			handler.post(new Runnable() {
//
//				@Override
//				public void run() {
//					mWebView.loadData(sb.toString(), "text/html;charset=utf-8",
//							null);
//				}
//			});
//
//		} catch (MalformedURLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

	@Override
	public void run() {
		super.run();
		HttpURLConnection mHttpURLConnection;
		URL httpUrl;
		InputStream in;
		FileOutputStream out = null;
		File download = null;
		try {
			httpUrl = new URL(url);
			mHttpURLConnection = (HttpURLConnection) httpUrl.openConnection();
			mHttpURLConnection.setReadTimeout(5000);
			mHttpURLConnection.setRequestMethod("GET");
			
			 in = mHttpURLConnection.getInputStream();
			 String name = String.valueOf(System.currentTimeMillis());
			 if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){ //SD¿¨´æÔÚ
				 
				 File parent= Environment.getExternalStorageDirectory();
				 download = new File(parent,name);
				 out = new FileOutputStream(download);
			 }else{
				 return;
			 }
			 
			 byte[]buffer = new byte[2048];
			 int len  ;
			 if(out!=null){
				while((len=in.read(buffer))!=-1){
					out.write(buffer, 0, len);
				} 
			 }
			 
			 final Bitmap bitmap = BitmapFactory.decodeFile(download.getAbsolutePath());
			 
			 handler.post(new Runnable() {
				
				@Override
				public void run() {
					mImageView.setImageBitmap(bitmap);
					Utils.addRippleEffect(mImageView);
				}
			});
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
