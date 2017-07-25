package com.qnmlgb.activity;

import com.flyco.animation.BaseAnimatorSet;
import com.flyco.animation.Attention.ShakeVertical;
import com.flyco.animation.Attention.Swing;
import com.flyco.animation.FlipEnter.FlipTopEnter;
import com.flyco.animation.SlideEnter.SlideTopEnter;
import com.flyco.animation.SlideExit.SlideBottomExit;
import com.flyco.animation.SlideExit.SlideLeftExit;
import com.flyco.dialog.listener.OnBtnLeftClickL;
import com.flyco.dialog.listener.OnBtnRightClickL;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;
import com.flyco.dialog.widget.NormalDialog;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.ninexiu.wjw.R;
import com.qnmlgb.application.SysApplication;
import com.qnmlgb.fragment.ContentMenuFragment;
import com.qnmlgb.fragment.LeftMenuFragment;
import com.qnmlgb.service.MediaPlayerService;
import com.qnmlgb.util.SystemBarTintManager;
import com.umeng.socialize.bean.SocializeConfig;
import com.umeng.socialize.sso.UMSsoHandler;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Toast;

public class MainUIActivity extends SlidingFragmentActivity {

	private final String LEFT_MENU_TAG = "left_menu"; // 左侧菜单Fragment的标记,
														// 相当于控件的id,
														// 后期可以使用tag标记找到他
	private final String MAIN_CONTENT_TAG = "main_content"; // 主界面正文的Fragment的标记

	private SystemBarTintManager mTintManager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTheme(R.style.AnimationTheme);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			setTranslucentStatus(false);
		}
		mTintManager = new SystemBarTintManager(this);
		mTintManager.setStatusBarTintEnabled(true);
		mTintManager.setStatusBarTintResource(R.color.blue);

		// 配置主页面布局
		setContentView(R.layout.main_ui);

		// 配置左侧菜单布局
		setBehindContentView(R.layout.left_menu);
		// 配置菜单可用的模式: 左侧菜单可用
		SlidingMenu slidingMenu = getSlidingMenu();
		slidingMenu.setMode(SlidingMenu.LEFT);

		// 配置菜单可以拖拽的区域，整个屏幕都可以拖拽
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

		// 配置主界面留在屏幕上的宽度：240
		slidingMenu.setBehindOffset(240);

		initFragfment();

	}

	private void initFragfment() {
		// 获取FragmentManager管理器对象
		FragmentManager fm = getSupportFragmentManager();
		// 开启事务
		FragmentTransaction ft = fm.beginTransaction(); // 获取事务操作对象

		// 替换Fragment
		ft.replace(R.id.fl_left_menu, new LeftMenuFragment(), LEFT_MENU_TAG); // 把帧布局替换成左侧菜单Fragment对象
		ft.replace(R.id.fl_main, new ContentMenuFragment(), MAIN_CONTENT_TAG); // 把帧布局替换成主界面正文Fragment对象

		// 提交事务
		ft.commit();

	}

	@TargetApi(19)
	protected void setTranslucentStatus(boolean on) {
		Window win = getWindow();
		WindowManager.LayoutParams winParams = win.getAttributes();
		final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
		if (on) {
			winParams.flags |= bits;
		} else {
			winParams.flags &= ~bits;
		}
		win.setAttributes(winParams);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		UMSsoHandler ssoHandler = SocializeConfig.getSocializeConfig().getSsoHandler(requestCode);
		if (ssoHandler != null) {
			ssoHandler.authorizeCallBack(requestCode, resultCode, data);
		}
	}

	
	
	// ================================================================返回监听

	public final int TYPE_ONE = 1;
	public final int TYPE_TWO = 2;
	public final int TYPE_THREE = 3;
	public final int TYPE_FOUR = 4;

	public void onBackPressed() {
		showTypeDialog((int) (Math.random() * 4 + 1));
	}

	private void showTypeDialog(int type) {
		BaseAnimatorSet base_in;
		BaseAnimatorSet base_out;
		final NormalDialog dialog;

		switch (type) {
		case TYPE_ONE:
			base_in = new FlipTopEnter();
			base_out = new SlideLeftExit();

			// 展示Dialog
			dialog = new NormalDialog(MainUIActivity.this);
			dialog.dividerColor(getResources().getColor(R.color.green))
					.titleTextColor(getResources().getColor(R.color.blue))
					// title内容颜色
					.titleTextSize(20.0f).style(NormalDialog.STYLE_TWO).content("您确定不再看看?").contentTextSize(17.0f)
					.btnText("再看看", "狠心逃走")
					.btnTextColor(getResources().getColor(R.color.hong), getResources().getColor(R.color.gray))
					.showAnim(base_in).dismissAnim(base_out).show();

			dialog.setOnBtnLeftClickL(new OnBtnLeftClickL() {

				@Override
				public void onBtnLeftClick() {
					Toast.makeText(MainUIActivity.this, "再看看", Toast.LENGTH_SHORT).show();
					dialog.dismiss();
				}
			});

			dialog.setOnBtnRightClickL(new OnBtnRightClickL() {

				@Override
				public void onBtnRightClick() {
					Toast.makeText(MainUIActivity.this, "狠心逃走", Toast.LENGTH_SHORT).show();
					dialog.dismiss();
					finishApp();
				}
			});
			break;
		case TYPE_TWO:
			base_in = new SlideTopEnter();
			base_out = new SlideBottomExit();

			dialog = new NormalDialog(this);
			dialog.cornerRadius(5)
					// 对话框的圆角conner
					.isTitleShow(false)
					// 是否展示标题内容
					.bgColor(getResources().getColor(R.color.black_S))
					// 对话框背景颜色
					.dividerColor(getResources().getColor(R.color.black_SS))
					// 对话框里按钮间的分割线颜色
					.btnColorPress(getResources().getColor(R.color.black_SSS))
					// 确认，取消按钮背景颜色
					.content("你确定要狠心的退出程序?")
					// 内容
					.contentTextColor(getResources().getColor(R.color.white))
					// 内容颜色
					.contentGravity(Gravity.CENTER)
					// 内容位置
					.btnText("确定", "再想想")
					// 左右点击按钮
					.btnTextSize(15.5f, 15.5f)
					// 按钮字体大小
					.btnTextColor(getResources().getColor(R.color.white), getResources().getColor(R.color.white))// 按钮字体颜色
					.showAnim(base_in) // 对话框进入动画
					.dismissAnim(base_out) // 对话框退出动画
					.show();

			dialog.setOnBtnLeftClickL(new OnBtnLeftClickL() {

				@Override
				public void onBtnLeftClick() {
					Toast.makeText(MainUIActivity.this, "确定", Toast.LENGTH_SHORT).show();
					dialog.dismiss();
					finishApp();
				}
			});

			dialog.setOnBtnRightClickL(new OnBtnRightClickL() {

				@Override
				public void onBtnRightClick() {
					Toast.makeText(MainUIActivity.this, "再想想", Toast.LENGTH_SHORT).show();
					dialog.dismiss();
				}
			});

			break;
		case TYPE_THREE:

			String[] args = new String[] { "版本更新", "帮助与反馈", "狠心退出JW" };
			final ActionSheetDialog mActionSheetDialog = new ActionSheetDialog(MainUIActivity.this, args, null);
			mActionSheetDialog.isTitleShow(false).show();
			mActionSheetDialog.setOnOperItemClickL(new OnOperItemClickL() {

				@Override
				public void onOperItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					mActionSheetDialog.dismiss();
					exitDialog();
				}
			});

			break;

		case TYPE_FOUR:
			base_in = new Swing();
			base_out = new ShakeVertical();

			final NormalDialog mNormalDialog = new NormalDialog(MainUIActivity.this);
			mNormalDialog.cornerRadius(5).isTitleShow(false).content("爷，要走了吗~ 0_0\r\n要不再瞧瞧，说不定还有你想看的哦~")
					.contentGravity(Gravity.CENTER).titleLineColor(Color.parseColor("#00000000"))
					.dividerColor(getResources().getColor(R.color.hong)).btnText("再看看", "退出")
					.btnTextColor(getResources().getColor(R.color.gray), getResources().getColor(R.color.blue))
					.showAnim(base_in).dismissAnim(base_out).show();

			mNormalDialog.setOnBtnLeftClickL(new OnBtnLeftClickL() {

				@Override
				public void onBtnLeftClick() {
					mNormalDialog.dismiss();
				}
			});

			mNormalDialog.setOnBtnRightClickL(new OnBtnRightClickL() {

				@Override
				public void onBtnRightClick() {
					finishApp();
					mNormalDialog.dismiss();
				}
			});

			break;

		default:
			break;
		}

	}

	public void exitDialog() {
		BaseAnimatorSet base_in = new Swing();
		BaseAnimatorSet base_out = new ShakeVertical();

		final NormalDialog xxx = new NormalDialog(MainUIActivity.this);
		xxx.cornerRadius(5).isTitleShow(false).content("爷，要走了吗~ 0_0\r\n要不再瞧瞧，说不定还有你想看的哦~")
				.contentGravity(Gravity.CENTER).titleLineColor(Color.parseColor("#00000000"))
				.dividerColor(getResources().getColor(R.color.hong)).btnText("再看看", "退出")
				.btnTextColor(getResources().getColor(R.color.gray), getResources().getColor(R.color.blue))
				.showAnim(base_in).dismissAnim(base_out).show();

		xxx.setOnBtnLeftClickL(new OnBtnLeftClickL() {

			@Override
			public void onBtnLeftClick() {
				xxx.dismiss();
			}
		});

		xxx.setOnBtnRightClickL(new OnBtnRightClickL() {

			@Override
			public void onBtnRightClick() {
				xxx.dismiss();
				finishApp();
			}
		});
	}

	protected void finishApp() {
		// TestMainActivity.super.onBackPressed();
		finish();
		SysApplication.getInstance().exit();
	}

}
