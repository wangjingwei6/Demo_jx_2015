package com.qnmlgb.net;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

public class AsyncTaskThread extends Thread {

	private String url;
	private ImageView mImageView;
	private Bitmap mBitmap;

	public AsyncTaskThread(String url, Handler handler, ImageView imageView) {
		this.url = url;
		this.mImageView = imageView;
	}


	@Override
	public void run() {
		super.run();

		new MyAsyncTask().execute(url);

	}


	public class MyAsyncTask extends AsyncTask<String, Void, Bitmap> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			super.onProgressUpdate(values);
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			super.onPostExecute(result);
			mImageView.setImageBitmap(mBitmap);
		}

		@Override
		protected Bitmap doInBackground(String... params) {
			
			HttpURLConnection mHttpURLConnection;
			String url = params[0];
			URL httpUrl;
			InputStream in = null;
		
			try {
				httpUrl = new URL(url);
				mHttpURLConnection = (HttpURLConnection) httpUrl
						.openConnection();
				mHttpURLConnection.setReadTimeout(5000);
				mHttpURLConnection.setRequestMethod("GET");

				in = mHttpURLConnection.getInputStream();
				BufferedInputStream mBUBufferedInputStream = null ;
				if (null != in) {
					 mBUBufferedInputStream = new BufferedInputStream(in);
				}

				mBitmap = BitmapFactory.decodeStream(mBUBufferedInputStream);
				
				if (in != null) {
					in.close();
				}

				if (mBUBufferedInputStream != null) {
					mBUBufferedInputStream.close();
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

				return mBitmap;
			}

	}

}
