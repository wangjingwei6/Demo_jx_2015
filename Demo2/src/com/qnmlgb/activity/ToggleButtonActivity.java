package com.qnmlgb.activity;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.ninexiu.wjw.R;
import com.qnmlgb.view.MyToggleButton;
import com.qnmlgb.view.MyToggleButton.onToggleStateChangeListener;

public class ToggleButtonActivity extends Activity implements onToggleStateChangeListener{
	
	private MyToggleButton toggle1;
	private MyToggleButton toggle2;
	private MyToggleButton toggle3;
	private MyToggleButton toggle4;
	
	private ImageView img;
	private TextView tv;
	
	private static final int TOGGLE1 = 1;
	private static final int TOGGLE2 = 2;
	private static final int TOGGLE3 = 3;
	private static final int TOGGLE4 = 4;
	
	private boolean currentSelectButton;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setTheme(R.style.AnimationTheme);
		setContentView(R.layout.toggle);
		
		initView();
		setOnStateChangeListener();
	}




	private void setOnStateChangeListener() {
		toggle1.setOnToggleStateChangeListener(this,1);
		toggle2.setOnToggleStateChangeListener(this,2);
		toggle3.setOnToggleStateChangeListener(this,3);
		toggle4.setOnToggleStateChangeListener(this,4);
	}




	private void initView() {
		toggle1 = (MyToggleButton)findViewById(R.id.mytogglebutton1);
		toggle2 = (MyToggleButton)findViewById(R.id.mytogglebutton2);
		toggle3 = (MyToggleButton)findViewById(R.id.mytogglebutton3);
		toggle4 = (MyToggleButton)findViewById(R.id.mytogglebutton4);
		img = (ImageView)findViewById(R.id.toggle_showimage);
		tv = (TextView)findViewById(R.id.toggle_showtext);
		
		
	}


	@Override
	public void onToggleStateChange(boolean state, int id) {
			switch (id) {
			case TOGGLE1:
				if(state){
					showContent(TOGGLE1);
					dissmissOtherToggleButton(TOGGLE1);
				}else{
					dismissContent();
				}
				break;
			case TOGGLE2:
				if(state){
					showContent(TOGGLE2);
					dissmissOtherToggleButton(TOGGLE2);
				}else{
					dismissContent();
				}
				
				break;
			case TOGGLE3:
				if(state){
					showContent(TOGGLE3);
					dissmissOtherToggleButton(TOGGLE3);
				}else{
					dismissContent();
				}
	
				break;
			case TOGGLE4:
				if(state){
					showContent(TOGGLE4);
					dissmissOtherToggleButton(TOGGLE4);
				}else{
					dismissContent();
				}
				break;

			default:
				break;
			}
	}




	private void dissmissOtherToggleButton(int toggle) {
		switch (toggle) {
		case TOGGLE1:
			toggle2.setStatus(false);
			toggle3.setStatus(false);
			toggle4.setStatus(false);
			break;
		case TOGGLE2:
			toggle1.setStatus(false);
			toggle3.setStatus(false);
			toggle4.setStatus(false);
			break;
		case TOGGLE3:
			toggle1.setStatus(false);
			toggle2.setStatus(false);
			toggle4.setStatus(false);
			break;
		case TOGGLE4:
			toggle2.setStatus(false);
			toggle3.setStatus(false);
			toggle1.setStatus(false);
			break;
		default:
			break;
		}
	}




	private void dismissContent() {
			img.setVisibility(View.GONE);
			tv.setVisibility(View.GONE);
	}




	private void showContent(int id) {
		img.setVisibility(View.VISIBLE);
		tv.setVisibility(View.VISIBLE);
		switch (id) {
			case TOGGLE1:
				img.setBackgroundResource(R.drawable.shamao1);
				tv.setText("王丽欣 : 开启傻帽模式");
				break;
			case TOGGLE2:
				img.setBackgroundResource(R.drawable.shuaige1);
				tv.setText("经纬 : 开启帅哥模式");
				break;
			case TOGGLE3:
				img.setBackgroundResource(R.drawable.shamao2);
				tv.setText("王丽欣 : 开启傻帽模式");
				break;
			case TOGGLE4:
				img.setBackgroundResource(R.drawable.shuaige2);
				tv.setText("经纬 : 开启天才 模式");
				break;
			default:
				break;
			}

	}
}	
	