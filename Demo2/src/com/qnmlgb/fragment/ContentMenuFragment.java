package com.qnmlgb.fragment;

import java.util.ArrayList;

import com.custom.textview.shimmertextview.Shimmer;
import com.custom.textview.shimmertextview.ShimmerTextView;
import com.flyco.animation.BaseAnimatorSet;
import com.flyco.animation.Attention.ShakeVertical;
import com.flyco.animation.Attention.Swing;
import com.flyco.animation.FlipEnter.FlipTopEnter;
import com.flyco.animation.FlipEnter.FlipVerticalSwingEnter;
import com.flyco.animation.FlipExit.FlipVerticalExit;
import com.flyco.animation.SlideEnter.SlideTopEnter;
import com.flyco.animation.SlideExit.SlideBottomExit;
import com.flyco.animation.SlideExit.SlideLeftExit;
import com.flyco.dialog.listener.OnBtnLeftClickL;
import com.flyco.dialog.listener.OnBtnRightClickL;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;
import com.flyco.dialog.widget.NormalDialog;
import com.igexin.sdk.PushManager;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.ninexiu.sixninexiu.lib.jazzypager.JazzyViewPager;
import com.ninexiu.sixninexiu.lib.jazzypager.JazzyViewPager.TransitionEffect;
import com.ninexiu.sixninexiu.lib.jazzypager.OutlineContainer;
import com.ninexiu.wjw.R;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.qnmlgb.activity.ArrayListViewActivity;
import com.qnmlgb.activity.AutoTranslateAnamationActivity;
import com.qnmlgb.activity.CustomProgressActivity;
import com.qnmlgb.activity.CustomViewpagerIndicatorActivity;
import com.qnmlgb.activity.EditMainUserIconActivity;
import com.qnmlgb.activity.GridViewActivity;
import com.qnmlgb.activity.MainUIActivity;
import com.qnmlgb.activity.PersonPhotoActivity;
import com.qnmlgb.activity.PersonSettingActivity;
import com.qnmlgb.activity.PullSwipeRefreshLayoutActivity;
import com.qnmlgb.activity.PullToRefreshListViewActivity;
import com.qnmlgb.activity.QuickIndexbarActivity;
import com.qnmlgb.activity.QuickofThreeasOneActivity;
import com.qnmlgb.activity.RecycleViewActivity;
import com.qnmlgb.activity.RefreshAndLoadListViewActivity;
import com.qnmlgb.activity.SimpleCustomListViewActivity;
import com.qnmlgb.activity.StickListViewActivity;
import com.qnmlgb.activity.SubPageActivity;
import com.qnmlgb.activity.ToggleButtonActivity;
import com.qnmlgb.adapter.HSVAdapter;
import com.qnmlgb.application.SysApplication;
import com.qnmlgb.bean.GetAllTypeData;
import com.qnmlgb.service.MediaPlayerService;
import com.qnmlgb.util.FileUtil;
import com.qnmlgb.util.MyImageLoaderUtil;
import com.qnmlgb.util.MyToast;
import com.qnmlgb.util.NetUtils;
import com.qnmlgb.util.NetUtils.NetType;
import com.qnmlgb.util.Utils;
import com.qnmlgb.util.Utils.LoginDialogListenner;
import com.qnmlgb.view.CircularImageView;
import com.qnmlgb.view.HSVLayout;
import com.qnmlgb.view.IOSTaoBaoDialog;
import com.qnmlgb.view.waveview.WaveView;
import com.umeng.socialize.bean.SocializeConfig;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.QQShareContent;
import com.umeng.socialize.media.QZoneShareContent;
import com.umeng.socialize.media.SinaShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ContentMenuFragment extends BaseFragment implements OnClickListener{
	
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			
			switch (msg.what) {
			case LOOP_IMAGE:
				//来回轮播================
				if(currentPosition>=0&&currentPosition<viewPageList.size()){
					viewpager.setCurrentItem(viewpager.getCurrentItem()+1);
					currentPosition= viewpager.getCurrentItem()+1;
				}else{
					viewpager.setCurrentItem(viewpager.getCurrentItem()-1);
					if((viewpager.getCurrentItem()==0)){
						currentPosition = 0;
					}
				}
				//来回轮播======================
				
				
				//顺序轮播=======================   没做出来！！！！！！！！！！！！！。。。。
				
				//顺序轮播=======================
				
				
				handler.sendEmptyMessageDelayed(LOOP_IMAGE, PHOTO_CHANGE_TIME);
					
				break;
//			case HANDLE_MSG:
//				handler.sendEmptyMessageDelayed(LOOP_IMAGE, PHOTO_CHANGE_TIME);
//				break;
				
			case HEART:
				if(heartNum<=19){ //循环20次
					if(progressNum<100){
						mWaveView.setProgress(progressNum);
						progressNum += 2;
						handler.sendEmptyMessageDelayed(HEART,500);
					}else{ //100+
						progressNum = 0;
						heartNum += 1;
						
						handler.post(new Runnable() {
							@Override
							public void run() {
								initHeart();
							}
						});
					}
					tv_hear_cum.setText(heartNum+"");
				}else{
					//效果停止，不再波动
					mWaveView.setProgress(100);
					handler.removeMessages(HEART);
				}
				
				
				
				
				break;
			default:
				break;
			}
			
			
		}
	};
	
	private WaveView mWaveView;
	private TextView tv_hear_cum;
	private int heartNum  = 0;
	private static int progressNum  = 0;
	private static final int HEART = 6;
	
	private WebView mWebView;
//	private ImageView mImageView;
//	private RelativeLayout myProgress;

	private Button left_but;
	private Button arraylistView_but;
	private Button pullTorefreshlist_but;
	private Button simplelistView_but;
	private Button customViewpagerIndicator;
	private Button autoTranslateAnamation_but;
	private Button Three_quickIndex_swip_pullImg;
	private Button quick_index_but;
	private Button refreshAndLoad_but;
	private Button pullswiperefresh_but;
	private Button recycleview_but;
	private Button gridView_but;
	private Button sticklistview_but;
	private CircularImageView hostimage;
	private Button hostimage_edit;
	private TextView fenxiang;
	private TextView setting;
	private TextView togglebutton;
	private TextView progress;

	private ShimmerTextView edit_text1;
	private ShimmerTextView edit_text2;
	
	private RelativeLayout rela_edit;

	private ImageView imageview_setting;


	public final int TYPE_ONEIMAGE = 1;
	public final int TYPE_TWOIMAGE = 2;
	public final int TYPE_THREEIMAGE = 3;
	public final int TYPE_FOURIMAGE = 4;
	public final int TYPE_FIVEIMAGE = 5;
	public final int TYPE_SIXIMAGE = 6;
	public final int TYPE_SEVENIMAGE = 7;

	private UMSocialService mController;
	private UMImage mUmImage;
	private HSVLayout mHSlayout;
	public int[] imgIDs;

	private String mShareContent = "大哥\n大哥只是掩饰 \n能做对爱侣\n堕落成朋友谁心息\n我要爱情不需要登对\n不需得你允许\n兄妹真有趣\n不需要分居\n忘记辈份再追，http://www.baidu.com";
	private String Umtitle = "LOVE";
	private String imgUrl;
	private int[] idImgs = new int[] { R.drawable.flower,
			R.drawable.flowermore4, R.drawable.flowermore,
			R.drawable.flowermore5 };
	private int[] noWifiImgs = new int[] { R.drawable.save_nowifi,
			R.drawable.save_nowifi2 };

	private int loop = 4; // 递归弹出对话框次数

	private MyImageLoaderUtil imageLoadUtil; // ImageLoader
	private int errorCount=0;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mainUi = (MainUIActivity)getActivity();
		slidingMenu = mainUi.getSlidingMenu();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		View view = getView();
		init();
		initView(view);
		initViewPager(view);

		initPersonIcon();
		initImageAdapter(); // 画廊数据适配器
		initOnclick();
		
		loopViewPager(); //轮播效果
		initHeart();//爱心效果

		// *****************推送过来带来的值 以及操作
		String stringExtra = getActivity().getIntent().getStringExtra("isPush");
		if (!TextUtils.isEmpty(stringExtra) && stringExtra.equals("true")) {
//			int idImg = (int) (Math.random() * 4);
//			int Image = idImgs[idImg];
//			mImageView.setBackgroundResource(Image);

			// 播放一首音乐 死结 简易版实现音乐播放器，只有一首歌
			SystemClock.sleep(1500);
			Intent intent = new Intent(getActivity(),
					MediaPlayerService.class);
			getActivity().startService(intent);

			// 弹出一个对话框(你懂的..1111)
			handler.postDelayed(new Runnable() {

				@Override
				public void run() {
					Utils.remindUserDialog(getActivity(), "残忍考虑=_=",
							"愿意~^_^~", "瞧瞧想一会0.0\n如果你也开心,你愿意做我的女朋友嘛?",
							new LoginDialogListenner() {

								@Override
								public void confirm() {
									// 同意 浪漫页面 +music
									// 1.结束当前的服务
									Intent intent = new Intent(
											getActivity(),
											MediaPlayerService.class);
									getActivity().stopService(intent);
									// 2.跳转浪漫页面
									ShowLoveFragment.setType("push");
									Intent loveIntent = new Intent(
											getActivity(),
											SubPageActivity.class);
									loveIntent.putExtra("isPush", "true");
									loveIntent.putExtra(
											SubPageActivity.CLASSFRAMENT,
											ShowLoveFragment.class);
									startActivity(loveIntent);
								}

								@Override
								public void cancle() {
									// 拒绝 递归请求
									if (loop > 1) {
										loveLoop();
									}

								}
							});
				}
			}, 2500);

		} else {
			// 判断图片请求方式 本地 或者 网络请求
//			loadImageForActiveNetworkStatusInfo();
		}

		// 第三方
		initGeTui();
		initUmeng();

	}
	
	
	
	private void initHeart() {
		handler.removeMessages(HEART);
		if (mWaveView != null) {
			mWaveView.setProgress(0);
		}
		handler.post(new Runnable() {
			
			@Override
			public void run() {
				Message ms = new Message();
				ms.what = HEART;
				handler.sendMessage(ms);
//				handler.sendEmptyMessageDelayed(ms.what, 1200);
			}
		});
	}

	private void init() {
		SysApplication.getInstance().addActivity(getActivity());
		// imageLoadUtil =
		// MyImageLoaderUtil.getImageLoadUtil(TestMainActivity.this);
		// imageLoadUtil.init();
	}

	/**
	 * 获取网络连接状态信息
	 */
//	private void loadImageForActiveNetworkStatusInfo() {
//
//		NetType currentNetType = NetUtils
//				.getCurrentNetType(TestMainActivity.this);
//
//		if (currentNetType.equals(NetType.NONET)) {
//			// 取资源目录的
//			int idImg = (int) (Math.random() * 2);
////			mImageView.setBackgroundResource(noWifiImgs[idImg]);
//
//		} else if (currentNetType.equals(NetType.MOBILE)) {
//			// 取资源目录的
//			int idImg = (int) (Math.random() * 2);
////			mImageView.setBackgroundResource(noWifiImgs[idImg]);
//		} else if (currentNetType.equals(NetType.WIFI)) {
//			// 请求网络图片
//			initNetImg((int) (Math.random() * 7 + 1));
//		} else {
//			MyToast.MakeToast(TestMainActivity.this, "图片判断出现未知的状况啦！");
//		}
//
//	}

	@Override
	public void onResume() {
		super.onResume();
		initPersonIcon();
	}

	@Override
	public void onStop() {
		super.onStop();
		Intent intent = new Intent(getActivity(),
				MediaPlayerService.class);
		getActivity().stopService(intent);
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		
		mShimmer.cancel();
		Intent intent = new Intent(getActivity(),
				MediaPlayerService.class);
		getActivity().stopService(intent);
		
		mHSVAdapter.recycle(); // 提醒画廊adapter回收数据
	}

	/**
	 * 初始化个人图标
	 */
	private void initPersonIcon() {
		// TODO
		// imageLoadUtil.displayImage(hostimage,"http:www.xxx.com");

		// SysApplication.displayImage(hostimage, "");

		// 1.从保存的图片文件里取
		Bitmap userHead = FileUtil.getBitmap(FileUtil.mEditFileName);
		if (userHead != null && !userHead.equals("")) {
			hostimage.setImageBitmap(userHead);
		}
	}

	/**
	 * 画廊数据适配器
	 */
	private void initImageAdapter() {
		imgIDs = GetAllTypeData.getInstance().getImgIDs();
		mHSVAdapter = new HSVAdapter(getActivity(), imgIDs);
		mHSlayout.setAdapter(mHSVAdapter);
	}

	private void initNetImg(final int x) {

		// new TestThread("http://www.baidu.com", handler, mWebView).start();

		// Toast.makeText(TestMainActivity.this, "加载网络图片中，请稍等..",
		// Toast.LENGTH_LONG).show();

//		int x = (int) (Math.random() * 7 + 1);

		switch (x) {
		case TYPE_ONEIMAGE:
			imgUrl = "http://pica.nipic.com/2008-03-19/2008319183523380_2.jpg";
			break;
		case TYPE_TWOIMAGE:
			imgUrl = "http://pic77.nipic.com/file/20150911/19291311_182646967000_2.jpg";
			break;
		case TYPE_THREEIMAGE:
			imgUrl = "http://pic78.nipic.com/file/20150915/17961491_110210994000_2.jpg";
			break;
		case TYPE_FOURIMAGE:
			imgUrl = "http://pics.sc.chinaz.com/files/pic/pic9/201511/apic16041.jpg";
			break;
		case TYPE_FIVEIMAGE:
			imgUrl = "http://pic18.nipic.com/20120105/9218468_125815225174_2.jpg";
			break;
		case TYPE_SIXIMAGE:
			imgUrl = "http://img2.3lian.com/2014/c7/42/d/26.jpg";
			break;
		case TYPE_SEVENIMAGE:
			imgUrl = "http://img.taopic.com/uploads/userup/26658/110620205S92B5Qc4.jpg";
			break;
		default:
			break;
		}

		// ============================================== 多种请求方式

		// =====AsyncTaskThread
		// handler.postDelayed(new Runnable() {
		// @Override
		// public void run() {
		// new AsyncTaskThread(imgUrl,handler, mImageView).start();
		// }
		// }, 500);

		// ImageLoader================

		SysApplication.displayImage(imageView, imgUrl,
				new ImageLoadingListener() {

					@Override
					public void onLoadingStarted(String arg0, View arg1) {
						Log.i("ImageLoader", "main_onLoadingStarted ====imgUrl :"+imgUrl);
						Log.e("ImageLoader", "main_onLoadingStarted");
//							myProgress.setVisibility(View.VISIBLE);
//							myProgress.startAnimation(AnimationUtils
//									.loadAnimation(TestMainActivity.this,
//											R.anim.loading_animation));
						if(x==4){
							loadImgDialog = Utils.showSmallMultiColorProgressDialog(getActivity(), "下载中..");
							loadImgDialog.show();
						}
					}

					@Override
					public void onLoadingFailed(String arg0, View arg1,
							FailReason arg2) {
						MyToast.MakeToast(getActivity(),
								"出现未知的网络请求错误,图片下载失败");
						Log.e("ImageLoader", "main_onLoadingFailed");
//							myProgress.setVisibility(View.GONE);
//							myProgress.clearAnimation();
							
							if(errorCount>2){
								initNetImg((int)(Math.random()*4+1));
							}else{
								initNetImg(x);
							}
							errorCount++;
					}

					@Override
					public void onLoadingComplete(String arg0, View arg1,
							Bitmap arg2) {
						Log.e("ImageLoader", "main_onLoadingComplete");
						//对图片进行缩放处理 和控件的宽高一样   防止拉伸 
						int width = viewpager.getWidth();
						int height = viewpager.getHeight();
						if(width!=0&&height!=0){
							Drawable resizeImage = Utils.resizeImage(arg2,width,height);
							imageView.setImageDrawable(resizeImage);
						}
						
//						imageView.setImageBitmap(arg2);
//							myProgress.setVisibility(View.GONE)    ;
//							myProgress.clearAnimation();
						if(loadImgDialog!=null&&loadImgDialog.isShowing()){
							loadImgDialog.dismiss();
						}
					}

					@Override
					public void onLoadingCancelled(String arg0, View arg1) {
//							myProgress.setVisibility(View.GONE);
//							myProgress.clearAnimation();
						if(loadImgDialog!=null&&loadImgDialog.isShowing()){
							loadImgDialog.dismiss();
						}
					}
				});
	}

	private void initGeTui() {
		// 个推
		PushManager.getInstance().initialize(getActivity().getApplicationContext());
	}

	private void initUmeng() {
		com.umeng.socialize.utils.Log.LOG = true;

		mController = UMServiceFactory.getUMSocialService("com.umeng.share");
		mController.setShareContent(mShareContent);
		mUmImage = new UMImage(getActivity(), BitmapFactory.decodeResource(
				getResources(), R.drawable.person14));
		// mController.setShareMedia(new
		// UMImage(this,BitmapFactory.decodeResource(getResources(),R.drawable.lyn_xxx)));
	}

	private void SharaUmeng() {
		// Bitmap bitmap_lynxxx =
		// BitmapFactory.decodeResource(this.getResources(),
		// R.drawable.lyn_xxx);
		//
		// Bitmap bitmap_lynxx =
		// BitmapFactory.decodeResource(this.getResources(), R.drawable.lyn_xx);
		//
		// Drawable resizeImage_xxx= Utils.resizeImage(bitmap_lynxxx, 32, 66);
		// Drawable resizeImage_xx= Utils.resizeImage(bitmap_lynxx, 32, 66);
		//
		// BitmapDrawable xxx = (BitmapDrawable) resizeImage_xxx;
		// BitmapDrawable xx = (BitmapDrawable) resizeImage_xx;
		// Bitmap bitmap_lyn_xxx = xxx.getBitmap();
		// Bitmap bitmap_lyn_xx = xx.getBitmap();
		
		String WX_AppID = "wxa67bc6972dc23f53";
		String AppSecret = "d4624c36b6795d1d99dcf0547af5443d"; 

		// 添加微信平台
		UMWXHandler wxHandler = new UMWXHandler(getActivity(), WX_AppID, AppSecret);
		if (wxHandler != null) {
			wxHandler.addToSocialSDK();
		}

		WeiXinShareContent mweiXinShareContent = new WeiXinShareContent();
		mweiXinShareContent.setTitle("微信_王丽欣 点击进入空间");
		mweiXinShareContent.setShareContent(mShareContent);
		mweiXinShareContent
				.setTargetUrl("http://user.qzone.qq.com/464933600/main");
		mweiXinShareContent.setShareImage(mUmImage);
		mController.setShareMedia(mweiXinShareContent);
		//

		// 添加微信朋友圈
		UMWXHandler wxCircleHandler = new UMWXHandler(getActivity(), WX_AppID, AppSecret);
		if (wxCircleHandler != null) {
			wxCircleHandler.setToCircle(true);
			wxCircleHandler.addToSocialSDK();
		}

		// 设置微信朋友圈分享内容
		CircleShareContent circleMedia = new CircleShareContent();
		circleMedia.setTitle("微信_王丽欣 点击进入空间");
		circleMedia.setShareContent(mShareContent); // BUG:这里添加分享出去的内容是title里的内容
													// ，setShareContent 如果不添加
													// content内容为空
		circleMedia.setShareImage(mUmImage);
		circleMedia.setTargetUrl("http://user.qzone.qq.com/464933600/main");
		mController.setShareMedia(circleMedia);

		// qq 和 qq空间
		UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(getActivity(), "100424468",
				"c7394704798a158208a74ab60104f0ba");
		if (qqSsoHandler != null) {
			qqSsoHandler.addToSocialSDK();
		}
		QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(getActivity(),
				"100424468", "c7394704798a158208a74ab60104f0ba");
		if (qZoneSsoHandler != null) {
			qZoneSsoHandler
					.setTargetUrl("http://user.qzone.qq.com/464933600/main");
			qZoneSsoHandler.addToSocialSDK();
		}

		QQShareContent qqShareContent = new QQShareContent();
		qqShareContent
				.setShareContent("大哥只是掩饰 \n能做对爱侣\n堕落成朋友谁心息\n我要爱情不需要登对\n不需得你允许\n兄妹真有趣\n不需要分居\n忘记辈份再追 ");
		qqShareContent.setTitle("点击进入王丽欣的QQ空间");
		qqShareContent.setShareImage(mUmImage);
		qqShareContent.setTargetUrl("http://user.qzone.qq.com/464933600/main");
		mController.setShareMedia(qqShareContent);

		QZoneShareContent mQZoneShareContent = new QZoneShareContent();
		mQZoneShareContent
				.setShareContent("大哥只是掩饰 \n能做对爱侣\n堕落成朋友谁心息\n我要爱情不需要登对\n不需得你允许\n兄妹真有趣\n不需要分居\n忘记辈份再追 ");
		mQZoneShareContent.setTitle("点击进入王丽欣的QQ空间");
		mQZoneShareContent.setShareImage(mUmImage);
		mQZoneShareContent
				.setTargetUrl("http://user.qzone.qq.com/464933600/main");
		mController.setShareMedia(mQZoneShareContent);

		SinaSsoHandler mSinaSsoHandler = new SinaSsoHandler();
		SinaShareContent mSinaShareContent = new SinaShareContent();
		mSinaShareContent
				.setShareContent("大哥只是掩饰 \n能做对爱侣\n堕落成朋友谁心息\n我要爱情不需要登对\n不需得你允许\n兄妹真有趣\n不需要分居\n忘记辈份再追 ");
		mSinaShareContent.setTitle("点击进入王丽欣的QQ空间");
		mSinaShareContent.setShareImage(mUmImage);
		mSinaShareContent
				.setTargetUrl("http://user.qzone.qq.com/464933600/main");
		mController.setShareMedia(mSinaShareContent);

		if (mSinaSsoHandler != null) {
			mSinaSsoHandler
					.setTargetUrl("http://user.qzone.qq.com/464933600/main");
			mController.getConfig().setSsoHandler(mSinaSsoHandler);
			mController.getConfig().setSinaCallbackUrl("http://sns.whalecloud.com/sina2/callback");
		}
		mController.openShare(getActivity(), false);
	}

	private void initView(View view) {
		mWaveView = (WaveView)view.findViewById(R.id.wave_view);
		tv_hear_cum =(TextView)view.findViewById(R.id.tv_hear_cum);
		
		mWebView = (WebView)view.findViewById(R.id.mwebview);
//		mImageView = (ImageView) findViewById(R.id.download_imageview); //TODO
		imageview_setting = (ImageView) view.findViewById(R.id.imageview_setting);
		left_but = (Button)view.findViewById(R.id.left_but);
		hostimage = (CircularImageView) view.findViewById(R.id.hostimage);
		hostimage_edit = (Button) view.findViewById(R.id.hostimage_edit);
		arraylistView_but = (Button) view.findViewById(R.id.arraylistView_but);
		pullTorefreshlist_but = (Button) view.findViewById(R.id.pulltorefreshlistview);
		simplelistView_but = (Button) view.findViewById(R.id.simplelistview_but);
		autoTranslateAnamation_but = (Button) view.findViewById(R.id.AutoTranslateAnamation_but);
		quick_index_but = (Button) view.findViewById(R.id.quick_index_but);
		refreshAndLoad_but = (Button) view.findViewById(R.id.refresh_load_but);
		pullswiperefresh_but = (Button)view.findViewById(R.id.pullswiperefresh_but);
		recycleview_but = (Button)view.findViewById(R.id.recycleview_but);
		customViewpagerIndicator =(Button)view.findViewById(R.id.Custom_viewpager_indicator);
		Three_quickIndex_swip_pullImg = (Button) view.findViewById(R.id.Three_quickIndex_swip_pullImg);
		gridView_but = (Button) view.findViewById(R.id.GridView_but);
		sticklistview_but = (Button) view.findViewById(R.id.sticklistview_but);

		togglebutton = (TextView) view.findViewById(R.id.togglebutton);
		fenxiang = (TextView) view.findViewById(R.id.fenxiang);
		setting = (TextView) view.findViewById(R.id.setting);
		progress = (TextView) view.findViewById(R.id.progress);
		rela_edit = (RelativeLayout) view.findViewById(R.id.rela_edit);
		mHSlayout = (HSVLayout) view.findViewById(R.id.hs_layout);

//		myProgress = (RelativeLayout) findViewById(R.id.custom_progress_main);
		edit_text1 = (ShimmerTextView)view.findViewById(R.id.edit_text1);
		edit_text2 = (ShimmerTextView)view.findViewById(R.id.edit_text2);
		mShimmer = new Shimmer();
		mShimmer.start(edit_text1);
		mShimmer.start(edit_text2);
		
		Utils.addRippleEffect(left_but);
		Utils.addRippleEffect(hostimage_edit);
		Utils.addRippleEffect(arraylistView_but, 40);
		Utils.addRippleEffect(pullTorefreshlist_but, 40);
		Utils.addRippleEffect(simplelistView_but, 40);
		Utils.addRippleEffect(sticklistview_but, 40);
		Utils.addRippleEffect(quick_index_but, 40);
		Utils.addRippleEffect(pullswiperefresh_but, 40);
		Utils.addRippleEffect(refreshAndLoad_but, 40);
		Utils.addRippleEffect(recycleview_but, 40);
		Utils.addRippleEffect(customViewpagerIndicator, 40);
		Utils.addRippleEffect(Three_quickIndex_swip_pullImg, 40);
		Utils.addRippleEffect(autoTranslateAnamation_but, 40);
		Utils.addRippleEffect(gridView_but, 40);
		Utils.addRippleEffect(togglebutton, 40);
		Utils.addRippleEffect(setting, 40);
		Utils.addRippleEffect(fenxiang, 40);
		Utils.addRippleEffect(progress, 40);
		Utils.addRippleEffect(imageview_setting, 40);

	}

	private void initOnclick() {
		left_but.setOnClickListener(this);
		hostimage.setOnClickListener(this);
		hostimage_edit.setOnClickListener(this);
		imageview_setting.setOnClickListener(this);
		arraylistView_but.setOnClickListener(this);
		pullTorefreshlist_but.setOnClickListener(this);
		simplelistView_but.setOnClickListener(this);
		sticklistview_but.setOnClickListener(this);
		quick_index_but.setOnClickListener(this);
		refreshAndLoad_but.setOnClickListener(this);
		pullswiperefresh_but.setOnClickListener(this);
		recycleview_but.setOnClickListener(this);
		customViewpagerIndicator.setOnClickListener(this);
		Three_quickIndex_swip_pullImg.setOnClickListener(this);
		autoTranslateAnamation_but.setOnClickListener(this);
		gridView_but.setOnClickListener(this);
		togglebutton.setOnClickListener(this);
		setting.setOnClickListener(this);
		fenxiang.setOnClickListener(this);
		progress.setOnClickListener(this);
		rela_edit.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		if (Utils.isClickable()) {
			return;
		}
		switch (v.getId()) {
		case R.id.rela_edit:
			goIOSTaoBaoDialog();
			break;
			//TODO
		case R.id.left_but:
			slidingMenu.toggle();
			break;
		case R.id.hostimage:
			// DisplayMetrics metric = new DisplayMetrics();
			// getWindowManager().getDefaultDisplay().getMetrics(metric);
			// int Screenwidth = metric.widthPixels;
			// ObjectAnimator animator = ObjectAnimator.ofFloat(hostimage,
			// "alpha", 0, 1, 1, 1);
			// ObjectAnimator animator1 = ObjectAnimator.ofFloat(hostimage,
			// "scaleX",1f,2f,1f);//X轴平移旋转
			// AnimatorSet mAnimationSet = new AnimatorSet();
			// mAnimationSet.playTogether(animator,animator1);
			// mAnimationSet.setDuration(2000);
			// mAnimationSet.setInterpolator(new BounceInterpolator());
			// mAnimationSet.start();

			Intent photoIntent = new Intent(getActivity(),
					PersonPhotoActivity.class);
			// intent.putExtra(SubPageActivity.CLASSFRAMENT,
			// PersonPhotoFragment.class);
			photoIntent.putExtra("type", "local");
			getActivity().startActivity(photoIntent);
			getActivity().overridePendingTransition(R.anim.zoom_in, 0);// 切换Activity的过渡动画

			break;
		case R.id.hostimage_edit:
			Intent editIconIntent = new Intent(getActivity(),
					EditMainUserIconActivity.class);
			// editIconIntent.putExtra(SubPageActivity.CLASSFRAMENT,EditMainUserIconFragment.class);
			startActivity(editIconIntent);

			break;
		case R.id.imageview_setting:
			goIOSTaoBaoDialog();
			break;

		case R.id.setting:
			// Toast.makeText(TestMainActivity.this, "个人设置", 500).show();
			Intent personIntent = new Intent(getActivity(),
					PersonSettingActivity.class);
			startActivity(personIntent);
			break;
		case R.id.arraylistView_but:

			Toast.makeText(getActivity(), "跳转arraylistViewActivity",
					500).show();
			Intent intent = new Intent(getActivity(),
					ArrayListViewActivity.class);
			startActivity(intent);
			break;
		case R.id.pulltorefreshlistview:
			Toast.makeText(getActivity(),
					"跳转pulltorefreshlistviewActivity", 500).show();
			Intent pulltorefreshIntent = new Intent(getActivity(),
					PullToRefreshListViewActivity.class);
			startActivity(pulltorefreshIntent);

			break;
		case R.id.simplelistview_but:

			Toast.makeText(getActivity(), "跳转arraylistViewActivity",
					500).show();
			Intent intent1 = new Intent(getActivity(),
					SimpleCustomListViewActivity.class);
			startActivity(intent1);
			break;
		case R.id.quick_index_but:
			Intent quickIntent = new Intent(getActivity(),
					QuickIndexbarActivity.class);
			startActivity(quickIntent);
			break;
		case R.id.refresh_load_but:
			Intent simpleLoadIntent = new Intent(getActivity(),
					RefreshAndLoadListViewActivity.class);
			startActivity(simpleLoadIntent);
			break;
		case R.id.pullswiperefresh_but:
			Intent pullswiperefreshIntent = new Intent(getActivity(),
					PullSwipeRefreshLayoutActivity.class);
			startActivity(pullswiperefreshIntent);
			break;
		case R.id.recycleview_but:
			Intent recycleIntent = new Intent(getActivity(),
					RecycleViewActivity.class);
			startActivity(recycleIntent);
			Utils.goInActivityAnimation(getActivity());
			break;
		case R.id.Custom_viewpager_indicator:
			
			Intent customIndicatorIntent =  new Intent (getActivity(),CustomViewpagerIndicatorActivity.class);
			startActivity(customIndicatorIntent);
			
			break;
		case R.id.Three_quickIndex_swip_pullImg:
			Intent threeIntent = new Intent(getActivity(),
					QuickofThreeasOneActivity.class);
			startActivity(threeIntent);

			break;
		case R.id.AutoTranslateAnamation_but:

			Toast.makeText(getActivity(),
					"跳转autoTranslateAnamationActivity", 500).show();
			Intent intent2 = new Intent(getActivity(),
					AutoTranslateAnamationActivity.class);
			startActivity(intent2);
			break;
		case R.id.GridView_but:

			Toast.makeText(getActivity(), "跳转GridViewActivity", 500)
					.show();
			Intent intent3 = new Intent(getActivity(),
					GridViewActivity.class);
			startActivity(intent3);

			break;

		case R.id.sticklistview_but:

			Toast.makeText(getActivity(), "跳转StickListViewActivity",
					500).show();
			Intent intent4 = new Intent(getActivity(),
					StickListViewActivity.class);
			startActivity(intent4);

			break;

		case R.id.togglebutton:
			Toast.makeText(getActivity(), "跳转ToggleButton", 500).show();
			Intent intent5 = new Intent(getActivity(),
					ToggleButtonActivity.class);
			startActivity(intent5);
			break;
		case R.id.fenxiang:
			SharaUmeng();
			break;
		case R.id.progress:
			Intent progressIntent = new Intent(getActivity(),
					CustomProgressActivity.class);
			startActivity(progressIntent);
			break;

		default:
			break;
		}
	}

	private void goIOSTaoBaoDialog() {
		BaseAnimatorSet in = new FlipVerticalSwingEnter();
		BaseAnimatorSet out = new FlipVerticalExit();
		IOSTaoBaoDialog mIosTaoBaoDialog = new IOSTaoBaoDialog(
				getActivity());
		mIosTaoBaoDialog.showAnim(in).dismissAnim(out).show();
	}


	public void loveLoop() {
		BaseAnimatorSet base_in = new Swing();
		BaseAnimatorSet base_out = new ShakeVertical();

		final NormalDialog xxx = new NormalDialog(getActivity());
		xxx.cornerRadius(5)
				.isTitleShow(false)
				.contentTextSize(13.5f)
				.content("欣欣，考虑好了嘛~ 0_0\r\nyes or ..<(＾－＾)>")
				.contentGravity(Gravity.CENTER)
				.titleLineColor(Color.parseColor("#00000000"))
				.dividerColor(Color.BLUE)
				// parseColor("#E56ba4")
				.btnText("稍稍考虑", "好嘛")
				.btnTextColor(getResources().getColor(R.color.gray),
						getResources().getColor(R.color.blue))
				.showAnim(base_in).dismissAnim(base_out).show();

		xxx.setOnBtnLeftClickL(new OnBtnLeftClickL() {

			@Override
			public void onBtnLeftClick() {
				if (loop > 1) {
					loveLoop();
					loop--;
				}
				xxx.dismiss();
			}
		});

		xxx.setOnBtnRightClickL(new OnBtnRightClickL() {

			@Override
			public void onBtnRightClick() {
				xxx.dismiss();
				// 显示浪漫页面 TODO
				Intent loveIntent = new Intent(getActivity(),
						SubPageActivity.class);
				loveIntent.putExtra(SubPageActivity.CLASSFRAMENT,
						ShowLoveFragment.class);
				startActivity(loveIntent);
			}
		});
	}



	// =========================================== Jazzviewpager

	private NetType currentNetType;
	
	private JazzyViewPager viewpager;
	private ArrayList<View> viewPageList;
	private Dialog loadImgDialog;

	private ImageView imageView;
	private View point_1;
	private View point_2;
	private View point_3;
	private View point_4;

	private void initViewPager(View view) {
		
		currentNetType = NetUtils.getCurrentNetType(getActivity());
		
		viewpager = (JazzyViewPager)view.findViewById(R.id.loop_viewpager);
		viewpager.setTransitionEffect(TransitionEffect.CubeOut);
		viewpager.setFadeEnabled(true);
		viewpager.setPageMargin(0);

		viewPageList = new ArrayList<View>();
		viewPageList.add(getItemView(0));
		viewPageList.add(getItemView(1));
		viewPageList.add(getItemView(2));
		viewPageList.add(getItemView(3));

		viewpager.setAdapter(new PagerAdapter() {

			@Override
			public boolean isViewFromObject(View view, Object object) {
				if (view instanceof OutlineContainer) {
					return ((OutlineContainer) view).getChildAt(0) == object;
				} else {
					return view == object;
				}
			}

			@Override
			public void destroyItem(ViewGroup container, int position,
					Object object) {
				container.removeView(viewpager.findViewFromObject(position));
			}

			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				Log.i("CNM", "PagerAdapter====position :" + position);

				container.addView(viewPageList.get(position));
				viewpager.setObjectForPosition(viewPageList.get(position),
						position);
				return viewPageList.get(position);
			}

			@Override
			public int getCount() {
				return viewPageList.size();
			}
		});

	}

	private View getItemView(int pagerNum) {
		RelativeLayout rela = (RelativeLayout) getActivity().getLayoutInflater().inflate(
				R.layout.loopviewpager, null);
		imageView = (ImageView) rela.findViewById(R.id.loop_imageview); // 轮播图
		point_1 = rela.findViewById(R.id.loop_point1);
		point_2 = rela.findViewById(R.id.loop_point2);
		point_3 = rela.findViewById(R.id.loop_point3);
		point_4 = rela.findViewById(R.id.loop_point4);
		
		switch (pagerNum) {
		case 0:
			if(currentNetType == NetType.WIFI){
				initNetImg(2);
			}else{//NetType.MOBILE  NetType.NONET  else
				imageView.setBackgroundResource(R.drawable.save_nowifi);
			}
			point_1.setBackgroundResource(R.drawable.point_white);
			point_2.setBackgroundResource(R.drawable.point_normal);
			point_3.setBackgroundResource(R.drawable.point_normal);
			point_4.setBackgroundResource(R.drawable.point_normal);
			break;
		case 1:
			if(currentNetType == NetType.WIFI){
				initNetImg(3);
			}else{ //NetType.MOBILE  NetType.NONET  else
				imageView.setBackgroundResource(R.drawable.save_nowifi2);
			}
			point_1.setBackgroundResource(R.drawable.point_normal);
			point_2.setBackgroundResource(R.drawable.point_white);
			point_3.setBackgroundResource(R.drawable.point_normal);
			point_4.setBackgroundResource(R.drawable.point_normal);
			break;
		case 2:
			if(currentNetType == NetType.WIFI){
				initNetImg(4);
			}else{//NetType.MOBILE  NetType.NONET  else
				imageView.setBackgroundResource(R.drawable.flowermore4);
			}
			point_1.setBackgroundResource(R.drawable.point_normal);
			point_2.setBackgroundResource(R.drawable.point_normal);
			point_3.setBackgroundResource(R.drawable.point_white);
			point_4.setBackgroundResource(R.drawable.point_normal);
			break;
		case 3:
			if(currentNetType == NetType.WIFI){
				initNetImg(5);
			}else{//NetType.MOBILE  NetType.NONET  else
				imageView.setBackgroundResource(R.drawable.flowermore5);
			}
			point_1.setBackgroundResource(R.drawable.point_normal);
			point_2.setBackgroundResource(R.drawable.point_normal);
			point_3.setBackgroundResource(R.drawable.point_normal);
			point_4.setBackgroundResource(R.drawable.point_white);
			break;

		default:
			break;
		}

		return rela;
	}
	
	private static final int LOOP_IMAGE = 0;
	private static final int PHOTO_CHANGE_TIME  = 2600;
	private int currentPosition=0;
	private HSVAdapter mHSVAdapter;
	private MainUIActivity mainUi;
	private SlidingMenu slidingMenu;
	private Shimmer mShimmer;
	private void loopViewPager() {
		handler.post(new Runnable() {
			
			@Override
			public void run() {
				handler.sendEmptyMessageDelayed(LOOP_IMAGE, PHOTO_CHANGE_TIME);
			}
		});
	}

	@Override
	protected View inflate(LayoutInflater inflater) {
		return inflater.inflate(R.layout.main_content, null);
	}
	
	// -========================================== Jazzviewpager
}
