package com.qnmlgb.activity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;

import org.json.JSONException;
import org.json.JSONObject;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.ninexiu.wjw.R;
import com.qnmlgb.application.SysApplication;
import com.qnmlgb.login.LoginActivity;
import com.qnmlgb.util.FileUtil;
import com.qnmlgb.util.IOUtils;
import com.qnmlgb.util.MyToast;
import com.qnmlgb.util.SPUtil;
import com.qnmlgb.util.Utils;
import com.qnmlgb.util.Utils.LoginDialogListenner;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

@SuppressLint("HandlerLeak")
public class SplashActivity extends Activity {

	private boolean isFirstLoginApp = true;
	private boolean isLogin = false;

	private final int SHOW_UPDATE_DIALOG =4;
	
	private final int MSG_LOGIN_GUIDE = 1;
	private final int MSG_LOGIN_MAIN = 2;
	private final int MSG_LOGIN_LOGINACTIVITY = 3;
	
	private int currentVersionCode;//当前应用版本号
	private String newApkUrl;//新版apk 的地址
	
//	private HorizontalProgressBarWithNumber updateProgress;
//	private LinearLayout linearUpdate;
	
	private LinearLayout upLoad; //下载更新布局
	private TextView load_desc; //下载更新提示

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {

			switch (msg.what) {
			case MSG_LOGIN_GUIDE:
				Intent guideIntent = new Intent(SplashActivity.this, GuideActivity.class);
				startActivity(guideIntent);
				finish();
				mHandler.removeCallbacksAndMessages(null);
				break;
			case MSG_LOGIN_MAIN:
				gotoMainUIActivty();
				mHandler.removeCallbacksAndMessages(null);
				break;
			case MSG_LOGIN_LOGINACTIVITY:

				Intent loginIntent = new Intent(SplashActivity.this, LoginActivity.class);
				startActivity(loginIntent);
				mHandler.removeCallbacksAndMessages(null);
				Utils.goInActivityAnimation(SplashActivity.this);
				finish();
				break;
				
			case SHOW_UPDATE_DIALOG:
				//显示的更新的对话框
				showUpdateDialog((String)msg.obj);
				break;
				
			default:
				break;
			}
		}

	};
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.splash_content);
		
		isLogin = SysApplication.isUserLogin;
		
		initUpLoadingLayout();
		
		getPackageInfo();
		
		initAnimation(); // 初始化动画

	}
	
	private void initUpLoadingLayout() {
		upLoad = (LinearLayout)findViewById(R.id.linear_upload);
		load_desc = (TextView)findViewById(R.id.loading_text);		
	}

	private void showUpdateDialog(String desc) {
			
			Utils.remindUserDialog(SplashActivity.this,"暂不更新","更新新版本", desc, new LoginDialogListenner() {
				
				@Override
				public void confirm() {
					//下载  New APK
					downloadAPK(newApkUrl);
				}
				
				@Override
				public void cancle() {
					//同版本，走login 或者 跳转主页面的逻辑
					selectIntentMessage();
				}
			});
			
		
	}
	

	private void getPackageInfo() {
		//获取手机包管理器服务
		PackageManager pm = getPackageManager();
		try {
			PackageInfo packageInfo = pm.getPackageInfo(getPackageName(),0);
			currentVersionCode = packageInfo.versionCode;
			
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
	}

	// ========================================================== 应用开启动画监听
	/**
	 * splash页面动画集合效果
	 */
	private void initAnimation() {
		RelativeLayout linear_splash = (RelativeLayout) findViewById(R.id.linear_splash);

		AnimationSet animationSet = new AnimationSet(false);

		// 旋转动画: 以中心点旋转, 0~360
		RotateAnimation rotateAnim = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		rotateAnim.setDuration(1500);
		rotateAnim.setFillAfter(true); // 设置动画执行完毕后, 控件停留在结束的状态下.

		// 缩放动画: 0~1 从没有到完全显示, 基于中心点进行缩放
		ScaleAnimation scaleAnim = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		scaleAnim.setDuration(1500);
		scaleAnim.setFillAfter(true);

		// 渐变动画: 从不显示到显示
		AlphaAnimation alphaAnim = new AlphaAnimation(0, 1);
		alphaAnim.setDuration(2000);
		alphaAnim.setFillAfter(true);

		// 把动画添加到集合中
		animationSet.addAnimation(rotateAnim);
		animationSet.addAnimation(scaleAnim);
		animationSet.addAnimation(alphaAnim);
		animationSet.setAnimationListener(new MyAnimationListener());

		linear_splash.startAnimation(animationSet);
	}

	class MyAnimationListener implements AnimationListener {

		@Override
		public void onAnimationEnd(Animation animation) {
			

			SPUtil sp = new SPUtil(SplashActivity.this, FileUtil.SAVE_USER_SPNAME);

			isFirstLoginApp = sp.getBooleanValue("isFirst", true);
			if (isFirstLoginApp) {
				mHandler.sendEmptyMessageDelayed(MSG_LOGIN_GUIDE, 1500);
			} else {
				
				checkUpdate();
			}
		}

		@Override
		public void onAnimationRepeat(Animation animation) {

		}

		@Override
		public void onAnimationStart(Animation animation) {

		}
	}

	// ========================================================== 应用开启动画监听
	
	
	/**
	 * 联网检查更新
	 */
	private void checkUpdate() {
		
		new Thread(new Runnable() {
			Message message = Message.obtain();
			
			@Override
			public void run() {
				try {
					
					URL url = new URL(getResources().getString(R.string.update_url));
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setRequestMethod("GET");
					conn.setConnectTimeout(5000);
					
					int responseCode = conn.getResponseCode();
					
					if(responseCode==200){
						InputStream inputStream = conn.getInputStream();
						//获取流内容 字符串形式
						
						String result = IOUtils.convertStream2String(inputStream);
						JSONObject jsonObj = new JSONObject(result);
						
						//获取联网获取的json信息
						int newVersionCode = jsonObj.getInt("versionCode");
						String desc = jsonObj.getString("versionName");
						newApkUrl = jsonObj.getString("apkurl");
						Log.i("CNM","JSON信息 :"+newVersionCode+"=="+desc+"=="+newApkUrl);
						
						if(currentVersionCode == newVersionCode){//同版本,不更新
							//同版本，走login 或者 跳转主页面的逻辑
							selectIntentMessage();
							
						}else{//创建更新提示框
							message.what = SHOW_UPDATE_DIALOG;
							message.obj = desc;
						}
						
						
					}else{
						showTaost(SplashActivity.this,"联网失败,2015_1");
						//同版本，走login 或者 跳转主页面的逻辑
						selectIntentMessage();
					}
					
				} catch (MalformedURLException e) {
					showTaost(SplashActivity.this,"MalformedURLException,2015_2");
					//同版本，走login 或者 跳转主页面的逻辑
					selectIntentMessage();
					
					e.printStackTrace();
				} catch (IOException e) {
					showTaost(SplashActivity.this,"服务器请求数据失败IOException,2015_3");
					//同版本，走login 或者 跳转主页面的逻辑
					selectIntentMessage();
					
					e.printStackTrace();
				} catch (JSONException e) {
					showTaost(SplashActivity.this,"JSON数据解析异常,2015_4");
					//同版本，走login 或者 跳转主页面的逻辑
					selectIntentMessage();
					
					e.printStackTrace();
				}
				
				mHandler.sendMessage(message);
			
			}
		}).start();
		
		
	}
	
	
	
	/**
	 *  更新下载新的APK 
	 * @param newApkUrl
	 * 
	 */
	private void downloadAPK(String newApkUrl) {
		
		upLoad.setVisibility(View.VISIBLE);
		
		HttpUtils mHttpUtils  = new HttpUtils();
		
		final String targetFile = FileUtil.UPDATE_APK_URL;//更新的apk文件位置
		
		mHttpUtils.download(newApkUrl,targetFile,new RequestCallBack<File>() {
			
			@Override
			public void onSuccess(ResponseInfo<File> arg0) {
				load_desc.setText("已下载: 100%");
				upLoad.setVisibility(View.GONE);
				
				//下载成功，就安装APK
				Intent intent = new Intent("android.intent.action.VIEW");
				intent.addCategory("android.intent.category.DEFAULT");
				intent.setDataAndType(Uri.fromFile(new File(targetFile)), "application/vnd.android.package-archive");
				startActivityForResult(intent,99);
				
			}

			public void onLoading(long total, long current, boolean isUploading) {
				super.onLoading(total, current, isUploading);
				Log.i("CNM","onLoading===== "+"total : "+total+" current : "+current +"___"+isUploading);
					//显示下载的进度
				float percnet= ((float)current/total);
				DecimalFormat  fnum   =   new   DecimalFormat("##0.00");  
				 String  desc = fnum.format(percnet*100);
				load_desc.setText("已下载: "+desc+"%");
				
			}
			
			@Override
			public void onFailure(HttpException arg0, String arg1) {
				//下载失败,跳转主页面
				gotoMainUIActivty();
			}
		});
		
		
	}
	

	/**
	 * 显示toast
	 * @param ctx
	 * @param msg
	 */
	public static void showTaost(final Activity ctx,final String msg){
		
		if("main".equals(Thread.currentThread().getName())){ // 判断 当前是否是在主线程 
			MyToast.MakeToast(ctx.getApplicationContext(),msg);
		}else{
			// 不是主线程 
			ctx.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					MyToast.MakeToast(ctx,msg);
				}
			});
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		gotoMainUIActivty();
	}
	
	
	private void selectIntentMessage(){
		if (isLogin) {
			mHandler.sendEmptyMessageDelayed(MSG_LOGIN_MAIN, 500);
		} else {// 未登录
			mHandler.sendEmptyMessageDelayed(MSG_LOGIN_LOGINACTIVITY, 300);
		}
	}
	
	
	/**
	 * 跳转主页面
	 */
	private void gotoMainUIActivty() {
		Intent mainIntent = new Intent(SplashActivity.this, MainUIActivity.class);
		startActivity(mainIntent);
		Utils.goInActivityAnimation(SplashActivity.this);
		finish();
	}

}
