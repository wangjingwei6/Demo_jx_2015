package com.qnmlgb.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.ninexiu.wjw.R;
import com.qnmlgb.view.HorizontalProgressBarWithNumber;
import com.qnmlgb.view.RoundProgressBarWidthNumber;

public class CustomProgressActivity extends Activity {

	private HorizontalProgressBarWithNumber mProgressBar1;
	private HorizontalProgressBarWithNumber mProgressBar2;

	private RoundProgressBarWidthNumber mProgressBar3;
	private RoundProgressBarWidthNumber mProgressBar4;

	private static final int MSG_PROGRESS_UPDATE_H1 = 1111;
	private static final int MSG_PROGRESS_UPDATE_H2 = 2222;
	private static final int MSG_PROGRESS_UPDATE_R1 = 3333;
	private static final int MSG_PROGRESS_UPDATE_R2 = 4444;
	
	private TextView remind;

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {

			switch (msg.what) {
			case MSG_PROGRESS_UPDATE_H1:
				int progress = mProgressBar1.getProgress();
				mProgressBar1.setProgress(++progress);
				if (progress >= 100) {
					mHandler.removeMessages(MSG_PROGRESS_UPDATE_H1);
					if (mProgressBar1 != null) {
						mProgressBar1.setVisibility(View.INVISIBLE);
						mHandler.sendEmptyMessage(MSG_PROGRESS_UPDATE_H2);
					}
				}
				mHandler.sendEmptyMessageDelayed(MSG_PROGRESS_UPDATE_H1, 50);

				break;
			case MSG_PROGRESS_UPDATE_H2:
				int progress2 = mProgressBar2.getProgress();
				mProgressBar2.setProgress(++progress2);
				if (progress2 >= 100) {
					mHandler.removeMessages(MSG_PROGRESS_UPDATE_H2);
					if (mProgressBar2 != null) {
						mProgressBar2.setVisibility(View.INVISIBLE);
						mHandler.sendEmptyMessage(MSG_PROGRESS_UPDATE_R1);
					}
				}
				mHandler.sendEmptyMessageDelayed(MSG_PROGRESS_UPDATE_H2, 100);

				break;
			case MSG_PROGRESS_UPDATE_R1:
				
				int progress3= mProgressBar3.getProgress();
				mProgressBar3.setProgress(++progress3);
				if (progress3 >= 100) {
					mHandler.removeMessages(MSG_PROGRESS_UPDATE_R1);
					if (mProgressBar3 != null) {
						mProgressBar3.setVisibility(View.INVISIBLE);
						mHandler.sendEmptyMessage(MSG_PROGRESS_UPDATE_R2);
					}
				}
				mHandler.sendEmptyMessageDelayed(MSG_PROGRESS_UPDATE_R1, 100);

				break;
			case MSG_PROGRESS_UPDATE_R2:
				
				int progress4 = mProgressBar4.getProgress();
				mProgressBar4.setProgress(++progress4);
				if (progress4 >= 100) {
					mHandler.removeMessages(MSG_PROGRESS_UPDATE_R2);
					if (mProgressBar4 != null) {
						mProgressBar4.setVisibility(View.INVISIBLE);
						remind.setVisibility(View.VISIBLE);
					}
				}
				mHandler.sendEmptyMessageDelayed(MSG_PROGRESS_UPDATE_R2, 100);

				break;

			default:
				break;
			}
		}
	};
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.custom_progress);
		mProgressBar1 = (HorizontalProgressBarWithNumber) findViewById(R.id.id_progressbar01);
		mProgressBar2 = (HorizontalProgressBarWithNumber) findViewById(R.id.id_progressbar02);
		mProgressBar3 = (RoundProgressBarWidthNumber) findViewById(R.id.id_progress03);
		mProgressBar4 = (RoundProgressBarWidthNumber) findViewById(R.id.id_progress04);
		
		remind = (TextView)findViewById(R.id.remind);

		mHandler.sendEmptyMessage(MSG_PROGRESS_UPDATE_H1);

		}
}
