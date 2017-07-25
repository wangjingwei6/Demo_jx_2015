package com.qnmlgb.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import com.ninexiu.wjw.R;
import com.qnmlgb.util.MyToast;
import com.qnmlgb.util.SystemBarTintManager;
import com.qnmlgb.util.Utils;
import com.qnmlgb.view.CalendarView;
import com.qnmlgb.view.RoundProgressBarWidthNumber;

public class SignActivity extends Activity implements OnClickListener {
	public SystemBarTintManager mTintManager;

	private ImageButton title_right;
	private TextView sign_button;
	private TextView desc_text_time;
	private TextView desc_text_sign;
	
	private CalendarView calendar;
	private RoundProgressBarWidthNumber reming_progress;
	private static final int MSG_SIGNIN = 666; 

	private String date; //日期时间戳String: yyyy-MM-dd
	private int today; //代表今天
	
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
				switch (msg.what) {
				case MSG_SIGNIN:
					int progress = reming_progress.getProgress();
					reming_progress.setProgress(++progress);
					if (progress >= 100) {
						mHandler.removeMessages(MSG_SIGNIN);
						if (reming_progress != null) {
							reming_progress.setVisibility(View.GONE);
							sign_button.setBackgroundResource(R.drawable.sign_gray_active);
							sign_button.setText("今日已签");
							sign_button.setEnabled(false);
							MyToast.MakeToastSign(SignActivity.this,"恭喜你,签到成功");
							return;
						}
					}
					mHandler.sendEmptyMessageDelayed(MSG_SIGNIN, 20);
					break;

				default:
					break;
				}
			
		}
		
	};

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setTheme(R.style.AnimationTheme);
		setContentView(R.layout.sign_calendar);
		changeSystemBar();

		initView();
		initData();
		initOnClick();

	}

	private void initData() {
		date = Utils.getDate();
		 if(date.substring(8, 9).equals("0")){  //说明 是 一个月的1-10号(01,02,03...)
			 today = Integer.parseInt(date.substring(9));
		 }else{
			 today = Integer.parseInt(date.substring(8));
		 }
		
		 calendar.getToday(today);
		
		desc_text_time.setText(date);
		
		//改变显示签到天数字体颜色
		String string_desc_text_sign = "连续签到" + (int)(Math.random()*4+1) + "天";
		
		int end = string_desc_text_sign.indexOf("天");
		SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(
				string_desc_text_sign);
		spannableStringBuilder.setSpan(
				new ForegroundColorSpan(Color.argb(255, 255, 57, 145)), 4,
				end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
		desc_text_sign.setText(spannableStringBuilder);
	}

	private void initView() {
		title_right = (ImageButton) findViewById(R.id.title_right);
		sign_button = (TextView) findViewById(R.id.sign_button);
		Utils.addRippleEffect(sign_button, 40);
		
		desc_text_time = (TextView) findViewById(R.id.desc_text_time);
		desc_text_sign = (TextView) findViewById(R.id.desc_text_sign);
		calendar = (CalendarView) findViewById(R.id.calendar);  //日历控件
		reming_progress = (RoundProgressBarWidthNumber)findViewById(R.id.reming_progress);
	}

	private void initOnClick() {
		title_right.setOnClickListener(this);
		sign_button.setOnClickListener(this);
	}

	private void changeSystemBar() {
		mTintManager = new SystemBarTintManager(this);
		mTintManager.setStatusBarTintEnabled(true);
		mTintManager.setStatusBarTintResource(R.color.hong);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_right:
			this.finish();
			
			break;
		case R.id.sign_button:
			reming_progress.setVisibility(View.VISIBLE);
			mHandler.sendEmptyMessage(MSG_SIGNIN);
			
//			mHandler.postDelayed(new Runnable() {
//				
//				@Override
//				public void run() {
//					sign_button.setBackgroundResource(R.drawable.sign_gray_active);
//					sign_button.setText("今日已签");
//					sign_button.setEnabled(false);
//				}
//			}, 1500);
			
		
			
			
			break;

		default:
			break;
		}
	}

}
