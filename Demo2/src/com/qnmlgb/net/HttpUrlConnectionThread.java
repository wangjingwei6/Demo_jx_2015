package com.qnmlgb.net;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.widget.ImageView;


public class HttpUrlConnectionThread extends Thread {
	private String url;
	private ImageView imageView;
	private Handler handler;

	public HttpUrlConnectionThread(String url, ImageView imageView, Handler handler) {
		super();
		this.url = url;
		this.imageView = imageView;
		this.handler = handler;
	}

	@Override
	public void run() {
		super.run();
		try {
			URL httpUrl = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) httpUrl
					.openConnection();

			//设置超链接
			connection.setReadTimeout(5000);
			connection.setRequestMethod("GET");
			connection.setDoInput(true);
			connection.setDoOutput(true);

			// 得到可以读取文件的输入流
			InputStream in = connection.getInputStream();
			FileOutputStream out = null;
			File downLoadFile = null;
			// 文件目录
			String fileName = String.valueOf(System.currentTimeMillis());
			if (Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {
				// sd卡目录
				File parent = Environment.getExternalStorageDirectory();
				downLoadFile = new File(parent, fileName);

				out = new FileOutputStream(downLoadFile);
			}

			
			byte[] b = new byte[1024];
			int len;
			if (out != null) {
				while ((len = in.read(b)) != -1) {
					out.write(b, 0, len);

				}
			}

			final Bitmap bitmap = BitmapFactory.decodeFile(downLoadFile
					.getAbsolutePath());
			;
			//这一步是刷新UI
			handler.post(new Runnable() {

				@Override
				public void run() {
					// 更新主线程
					imageView.setImageBitmap(bitmap);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
