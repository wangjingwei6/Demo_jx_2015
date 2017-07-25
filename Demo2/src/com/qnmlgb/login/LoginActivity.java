package com.qnmlgb.login;

//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.IOException;

import com.ninexiu.wjw.R;
//import com.qnmlgb.activity.BaseActivity;
import com.qnmlgb.activity.MainUIActivity;
import com.qnmlgb.application.SysApplication;
import com.qnmlgb.util.FileUtil;
import com.qnmlgb.util.MyToast;
import com.qnmlgb.util.SPUtil;
import com.qnmlgb.util.Utils;
import com.qnmlgb.util.Utils.UserRegeist;
import com.qnmlgb.view.CircularImageView;
import com.qnmlgb.view.PanningView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.os.Bundle;
//import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LoginActivity extends Activity implements OnClickListener {
	private PanningView panningView;

	private CircularImageView userIcon;
	private EditText account;
	private EditText password;
	private TextView regeist;
	private TextView login;
	private LinearLayout toMain;

	private SPUtil sp;

	private UserOperator mUserOperator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login_main);

		sp = new SPUtil(LoginActivity.this, "userInfo");
		mUserOperator = new UserOperatorImpl();

		initView();
		initOnClick();
		initUserIcon();
	}

	private void initUserIcon() {
		// 如果登陆过 存储用户用过的图片
		// 1.从保存的图片文件里取
		Bitmap userHead = FileUtil.getBitmap(FileUtil.mEditFileName);
		if (userHead != null && !userHead.equals("")) {
			userIcon.setImageBitmap(userHead);
		}
	}

	private void initView() {
		panningView = (PanningView) findViewById(R.id.panningView);

		userIcon = (CircularImageView) findViewById(R.id.login_icon);
		account = (EditText) findViewById(R.id.edit_account);
		password = (EditText) findViewById(R.id.edit_password);
		regeist = (TextView) findViewById(R.id.tv_regeist);
		login = (TextView) findViewById(R.id.tv_login);
		toMain = (LinearLayout) findViewById(R.id.linear_look_tomain);
	}

	private void initOnClick() {
		regeist.setOnClickListener(this);
		login.setOnClickListener(this);
		toMain.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		String userName = account.getText().toString();
		String passWord = password.getText().toString();
		switch (v.getId()) {
		case R.id.tv_regeist: // 注册
			showRegeistPopWindow();
			break;
		case R.id.tv_login:// 登录
			loginUser(userName, passWord);
			break;
		case R.id.linear_look_tomain:
			toMainUIActivity();
			break;
		default:
			break;
		}
	}

	/**
	 * 注册实现的弹出框
	 */
	private void showRegeistPopWindow() {
		Utils.remindUserRegeist_x(LoginActivity.this, new UserRegeist() {

			@Override
			public void onConfirm(String userName, String passWord) { // 获取到注册页面的账户名和密码
																		// (已经过滤)
																		// 进行IO操作

				// 封装数据源到用户bean中
				UserBean userBean = new UserBean();
				userBean.setUserName(userName);
				userBean.setPassWord(passWord);

				// 调用注册操作类
				boolean flag = mUserOperator.regeist(userBean);

				if (flag) {
					MyToast.MakeToast(LoginActivity.this, "注册成功!");
				} else {
					MyToast.MakeToast(LoginActivity.this, "注册失败,信息保存异常!");
				}

			}

			@Override
			public void onCancel() {
			}
		});

	}

	/**
	 * 登录实现
	 * 
	 * @param userName
	 * @param passWord2
	 */
	private void loginUser(String userName, String passWord) {

		int code = mUserOperator.userLogin(userName, passWord); // 登陆操作,IO

		switch (code) {
		case 1:
			// 保存已经登录的信息到 临时文件 SharedPreferences ==userInfo
			Editor editor = sp.getEditor();
			editor.putBoolean("isUserLogin", true);
			editor.commit();
			SysApplication.isUserLogin = true;
			
			MyToast.MakeToast(LoginActivity.this,"登陆成功!");
			toMainUIActivity();
			break;
		case 2: //用户名空
			account.setText("");
			password.setText("");
			MyToast.MakeToast(LoginActivity.this,"用户名为空");
			
			break;
		case 3://用户名错误
			account.setText("");
			password.setText("");
			
			MyToast.MakeToast(LoginActivity.this,"用户名错误");
			break;
		case 4://密码空

			password.setText("");
			MyToast.MakeToast(LoginActivity.this,"密码为空");
			break;
		case 5://密码错误
			
			password.setText("");
			MyToast.MakeToast(LoginActivity.this,"密码错误");
			break;
		case 6: //用户名密码为空
			MyToast.MakeToast(LoginActivity.this,"用户名密码为空");
			break;
		case 7://用户名密码错误
			account.setText("");
			password.setText("");
			MyToast.MakeToast(LoginActivity.this,"用户名密码错误");
			break;
		default:
			break;
		}

	}

	private void toMainUIActivity() {
		Intent toMain = new Intent(LoginActivity.this, MainUIActivity.class);
		startActivity(toMain);
		Utils.goInActivityAnimation(this);
		finish();
	}

	// ================================================================================================================

	// /**
	// * 登录操作
	// * @param userName 用户名
	// * @param passWord 密码
	// */
	// private void loginUser(String userName, String passWord) {
	// String oldUserName = null;
	// String oldPassWord = null;
	//
	// // 取出文件保存的账户数据 IO操作
	// //封装数据源 字符流即可
	// String filepath = FileUtil.SAVE_FILE_PATH+"/UserAccount.txt";
	//
	// File file ;
	// StringBuffer sbf;
	// BufferedReader br = null;
	// try {
	// sbf = new StringBuffer();
	// file= new File(filepath);
	// if(!file.exists()){
	// file.createNewFile();
	// }
	// br = new BufferedReader(new FileReader(file));
	// String str= null;
	// while((str=br.readLine())!=null){
	// sbf.append(str);
	// }
	//
	// String[] strs = sbf.toString().split("=");
	// oldUserName = strs[0];
	// oldPassWord = strs[1];
	//
	// } catch (IOException e) {
	// e.printStackTrace();
	// MyToast.MakeToast(LoginActivity.this,"检查账户信息出异常!");
	// }finally {
	// if(br!=null){
	// try {
	// br.close();
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
	// }
	//
	//
	// if(TextUtils.equals(oldUserName,userName)&&TextUtils.equals(oldPassWord,
	// passWord)){//登录成功
	// //保存已经登录的信息到 临时文件 SharedPreferences ==userInfo
	// Editor editor = sp.getEditor();
	// editor.putBoolean("isUserLogin",true);
	// editor.commit();
	// SysApplication.isUserLogin = true;
	// MyToast.MakeToast(LoginActivity.this,"登陆成功!");
	// toMainUIActivity();
	//
	// }else{ //错误提示
	//
	// if(TextUtils.isEmpty(userName)&&TextUtils.isEmpty(passWord)){
	// MyToast.MakeToast(LoginActivity.this,"用户名密码不能为空!");
	// return;
	// }
	//
	// if(!TextUtils.equals(oldUserName,userName)){
	// account.setText("");
	// password.setText("");
	// if(TextUtils.isEmpty(userName)){
	// MyToast.MakeToast(LoginActivity.this,"用户名不能为空!");
	// }else{
	// MyToast.MakeToast(LoginActivity.this,"用户名错误!");
	// }
	// }else if(!TextUtils.equals(oldPassWord, passWord)){
	// if(TextUtils.isEmpty(passWord)){
	// MyToast.MakeToast(LoginActivity.this,"密码不能为空 !");
	//
	// }else{
	// MyToast.MakeToast(LoginActivity.this,"密码错误!");
	// }
	// password.setText("");
	// }else{
	// MyToast.MakeToast(LoginActivity.this,"用户名密码错误!");
	// account.setText("");
	// password.setText("");
	// }
	// }
	//
	// }
	//
	//
	// /**
	// * 注册的Pop对话框
	// */
	// private void showRegeistPopWindow() {
	// Utils.remindUserRegeist(this,new BaseRegeistOrLogin() {
	//
	// @Override
	// public void regeistUser(String userName, String passWord) {
	// //获取到注册页面的账户名和密码 (已经过滤) 进行IO操作
	//
	// //封装数据源
	// StringBuffer sb = new StringBuffer();
	// sb.append(userName).append("=").append(passWord);
	//
	// //封装目的地
	// File file ;
	// BufferedWriter bf = null;
	// try {
	// file = new File(FileUtil.SAVE_FILE_PATH+"/UserAccount.txt");
	// if(file.exists()&&file.length()>0){//如果文件存在并且有数据，先删除文件
	// file.delete();
	// }
	// bf = new BufferedWriter(new FileWriter(file));
	// bf.write(sb.toString());
	// bf.newLine();
	// bf.flush();
	// MyToast.MakeToast(LoginActivity.this,"注册成功!");
	//
	// } catch (IOException e) {
	// e.printStackTrace();
	// MyToast.MakeToast(LoginActivity.this,"注册失败,信息保存异常！");
	// }finally {
	// if(bf!=null){
	// try {
	// bf.close();
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
	// }
	//
	//
	// }
	//
	// @Override
	// public void loginUser(String userName, String passWord) {
	//
	// }
	// });
	//
	// }

	// ================================================================================================================
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);
		if (hasFocus) {
			panningView.postDelayed(new Runnable() {

				@Override
				public void run() {
					panningView.startPanning();
				}
			}, 1000);
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
		panningView.stopPanning();
	}

	@Override
	protected void onPause() {
		super.onPause();
		// panningView.stopPanning();
	}

}
