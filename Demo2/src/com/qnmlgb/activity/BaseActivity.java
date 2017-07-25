package com.qnmlgb.activity;

import com.ninexiu.wjw.R;
import com.qnmlgb.util.Utils;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;


/**
 * 基类
 * @date 2015年7月22日15:19:16
 */
@TargetApi(Build.VERSION_CODES.KITKAT)
public abstract class BaseActivity extends FragmentActivity {
//	public SystemBarTintManager mTintManager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setTheme(R.style.AnimationTheme);
		//=============================    沉浸式的另外一种方式 动态代码设置
//		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//			setTranslucentStatus(false);
//		}
//		mTintManager = new SystemBarTintManager(this);
//		mTintManager.setStatusBarTintEnabled(true);
//		mTintManager.setStatusBarTintResource(R.color.ns_main_title_bg);
		
		//==============================
		setContentView();
		setBackListener();
	}
	
	
	private void setBackListener() {
		if(findViewById(R.id.left_btn) == null){
			return;
		}
		findViewById(R.id.left_btn).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	
	protected abstract void setContentView();
	
	
//	@TargetApi(19)
//	protected void setTranslucentStatus(boolean on) {
//		Window win = getWindow();
//		WindowManager.LayoutParams winParams = win.getAttributes();
//		final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
//		if (on) {
//			winParams.flags |= bits;
//		} else {
//			winParams.flags &= ~bits;
//		}
//		win.setAttributes(winParams);
//	}
	
}