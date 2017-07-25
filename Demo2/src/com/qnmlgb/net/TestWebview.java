package com.qnmlgb.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.os.Handler;
import android.webkit.WebView;

public class TestWebview extends Thread{

	
	private String url;
	private Handler handler;
	private WebView mWebView;

	public TestWebview(String url, Handler handler, WebView webview) {
		this.url = url;
		this.handler = handler;
		this.mWebView = webview;
	}


	 @Override
	public void run() {
		super.run();
		try {
			URL httpUrl = new URL(url);
			HttpURLConnection mHttpURLConnection = (HttpURLConnection) httpUrl
					.openConnection();
			mHttpURLConnection.setReadTimeout(5000);
			mHttpURLConnection.setRequestMethod("GET");
			final StringBuffer sb = new StringBuffer();
			BufferedReader bf = new BufferedReader(new InputStreamReader(
					mHttpURLConnection.getInputStream()));
			String str;
			while ((str = bf.readLine()) != null) {
				sb.append(str);
			}

			handler.post(new Runnable() {

				@Override
				public void run() {
					mWebView.loadData(sb.toString(), "text/html;charset=utf-8",
							null);
				}
			});

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
