package com.qnmlgb.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.flyco.animation.BaseAnimatorSet;
//import com.flyco.animation.Attention.ShakeVertical;
//import com.flyco.animation.Attention.Swing;
//import com.flyco.animation.Attention.Tada;
//import com.flyco.animation.BounceEnter.BounceTopEnter;
//import com.flyco.animation.FallEnter.FallEnter;
//import com.flyco.animation.FallEnter.FallRotateEnter;
//import com.flyco.animation.ZoomEnter.ZoomInBottomEnter;
import com.flyco.animation.ZoomEnter.ZoomInEnter;
import com.flyco.animation.ZoomExit.ZoomOutBottomExit;
import com.flyco.dialog.listener.OnBtnLeftClickL;
import com.flyco.dialog.listener.OnBtnRightClickL;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;
import com.flyco.dialog.widget.NormalDialog;
import com.ninexiu.wjw.R;
import com.qnmlgb.application.SysApplication;
import com.qnmlgb.util.FileUtil;
import com.qnmlgb.util.SPUtil;
import com.qnmlgb.util.Utils;
import com.qnmlgb.view.PanningView;

public class PersonSettingActivity extends Activity implements OnClickListener {

	private TextView logingOut;
	private TextView sign;

	private final int TYPE_ONE = 1;
	private final int TYPE_TWO = 2;
	private Handler mHandler = new Handler();
	private Dialog dialog;
	private PanningView panningView; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		   getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,      
                   WindowManager.LayoutParams. FLAG_FULLSCREEN);   
        
		setTheme(R.style.AnimationTheme);
		setContentView(R.layout.setting_layout);
		SysApplication.getInstance().addActivity(this);

		initView();
		setOnClick();
	}

	private void initView() {
		logingOut = (TextView) findViewById(R.id.zhuxiao);
		sign = (TextView) findViewById(R.id.qiandao);
		panningView = (PanningView)findViewById(R.id.panningView);

		Utils.addRippleEffect(logingOut, 40);
		Utils.addRippleEffect(sign, 40);
	}

	private void setOnClick() {
		logingOut.setOnClickListener(this);
		sign.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (Utils.isClickable()) {
			return;
		}
		switch (v.getId()) {
		case R.id.zhuxiao:
			showLoginOutDialog((int) (Math.random() * 2 + 1));
			break;
		case R.id.qiandao:
			dialog = Utils.showProgressDialog(this, "Loading..",true);
			dialog.show();
			mHandler.postDelayed(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					if(dialog!=null&&dialog.isShowing()){
						dialog.dismiss();
						Intent intent = new Intent(PersonSettingActivity.this,SignActivity.class);
						PersonSettingActivity.this.startActivity(intent);
					}
				}
			}, 3000);
			
			
			break;

		default:
			break;
		}
	}

	private void showLoginOutDialog(int type) {
		switch (type) {
		case TYPE_ONE:

			String[] args = new String[] { "版本更新", "帮助与反馈", "狠心退出JW" };
			final ActionSheetDialog mActionSheetDialog = new ActionSheetDialog(
					PersonSettingActivity.this, args, null);
			mActionSheetDialog.isTitleShow(false).show();
			mActionSheetDialog.setOnOperItemClickL(new OnOperItemClickL() {

				@Override
				public void onOperItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					mActionSheetDialog.dismiss();
					finishApp();
				}
			});
			
			break;
		case TYPE_TWO:
//			BaseAnimatorSet base_in = new Swing();
//			BaseAnimatorSet base_out = new ShakeVertical();
			BaseAnimatorSet base_in = new ZoomInEnter();
			BaseAnimatorSet base_out = new ZoomOutBottomExit();
			
			final NormalDialog mNormalDialog =  new NormalDialog(PersonSettingActivity.this);
			mNormalDialog
			.cornerRadius(5)    
			.isTitleShow(false)
			.content("爷，要走了吗~ 0_0\r\n要不再瞧瞧，说不定还有你想看的哦~")
			.contentGravity(Gravity.CENTER)
			.titleLineColor(Color.parseColor("#00000000"))
			.dividerColor(getResources().getColor(R.color.hong))
			.btnText("再看看","退出")
			.btnTextColor(getResources().getColor(R.color.gray), getResources().getColor(R.color.blue))
			.showAnim(base_in)
			.dismissAnim(base_out)
			.show();
			
			mNormalDialog.setOnBtnLeftClickL(new OnBtnLeftClickL() {
				
				@Override
				public void onBtnLeftClick() {
					mNormalDialog.dismiss();
				}
			});
			
			mNormalDialog.setOnBtnRightClickL(new OnBtnRightClickL() {
				
				@Override
				public void onBtnRightClick() {
					mNormalDialog.dismiss();
					finishApp();
				}
			});

			break;
		default:
			break;
		}

	}

	private void finishApp(){
		//更改信息 应用登录状态设置为false
		SPUtil sp = new SPUtil(PersonSettingActivity.this,FileUtil.SAVE_USER_SPNAME);
		sp.writeBooleanValue("isUserLogin", false);
		SysApplication.getInstance().exit();
//		SysApplication.isUserLogin = false;
	}
	
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);
		if(hasFocus){
			panningView.postDelayed(new Runnable() {
				
				@Override
				public void run() {
					panningView.startPanning();
				}
			}, 800);
		}
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		panningView.stopPanning();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		// android.os.Process.killProcess(android.os.Process.myPid());
	}
}
