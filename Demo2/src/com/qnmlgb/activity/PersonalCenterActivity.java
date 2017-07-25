package com.qnmlgb.activity;

import java.util.ArrayList;

import com.custom.textview.titanictextview.Titanic;
import com.custom.textview.titanictextview.TitanicTextView;
import com.ninexiu.wjw.R;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.qnmlgb.application.SysApplication;
import com.qnmlgb.bean.GetAllTypeData;
import com.qnmlgb.fragment.MyBasicPageFragment;
import com.qnmlgb.fragment.MyCatFragment;
import com.qnmlgb.fragment.MyImageFragment;
import com.qnmlgb.fragment.PersonPhotoFragment;
import com.qnmlgb.util.MyImageLoaderUtil;
import com.qnmlgb.util.SPUtil;
import com.qnmlgb.util.Utils;
import com.qnmlgb.view.CircularImageView;
import com.qnmlgb.view.PagerSlidingTabStrip;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PersonalCenterActivity extends BaseActivity implements OnClickListener {

	private static final int PAGE_NUM = 3;
	public static final int PAGE_BASIC = 0;
	public static final int PAGE_GARAGE = 1;
	public static final int PAGE_GUARD = 2;

	private String[] titleStrings = new String[] { "基本资料", "我的车库", "个人相册" };

	private Fragment fragements[] = new Fragment[3];

	private ViewPager viewPager;
	public CircularImageView avatar;
	private PagerSlidingTabStrip indicator;
	public View loadingView;

	private TextView title;
	private TitanicTextView person_name;
	
	private MyBasicPageFragment basicPageFragment;
	private MyCatFragment catFragment;
	private MyImageFragment photoFragment;
	
	private int position; // 个人头像的 索引值(集合里三种头像)
	private ArrayList<String> photoList; // 头像集合 (网络地址参数)

	private Titanic titanic;

	private RelativeLayout loading;
	
	private int errorCount = 0;
	private ArrayList<String> urls;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initView();
		initAvatarData();
		setFragment();

		loading.setVisibility(View.VISIBLE);
		
		PersonalCenterAdapter mPersonalCenterAdapter = new PersonalCenterAdapter(getSupportFragmentManager());
		viewPager.setAdapter(mPersonalCenterAdapter);
		viewPager.setOffscreenPageLimit(2);
		viewPager.setCurrentItem(0);
		indicator.setShouldExpand(true);
		indicator.setViewPager(viewPager);
		indicator.setTextColorResource(R.color.hong, R.color.livehall_tab_text_unselected);
		indicator.setTextSize(getResources().getDimensionPixelSize(R.dimen.livehall_tab_textsize));

		viewPager.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				v.getParent().requestDisallowInterceptTouchEvent(true);
				return false;
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				loading.setVisibility(View.GONE);
			}
		}, 3000);
	}

	private void initAvatarData() {
		photoList = new ArrayList<String>();
		urls = GetAllTypeData.getAllNetImageUrl();
		position = (int) (Math.random() * 12 + 1);
		String path = urls.get(position - 1);
		photoList.add(path);
		disPlayImage(path);
		// MyImageLoaderUtil imageLoadUtil =
		// MyImageLoaderUtil.getImageLoadUtil(PersonalCenterActivity.this);
		// imageLoadUtil.init();
		// imageLoadUtil.displayImage(avatar, path);

	}

	private void disPlayImage(final String path) {
		SysApplication.displayImage(avatar, path, new ImageLoadingListener() {

			@Override
			public void onLoadingStarted(String arg0, View arg1) {
				Log.i("ImageLoader", "===onLoadingStarted=== :path :"+path);
				Log.e("ImageLoader", "===onLoadingStarted");
			}

			@Override
			public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
				Log.e("ImageLoader", "===onLoadingFailed");
				if(errorCount>2){
					disPlayImage(urls.get((int) (Math.random()*6+1)));
				}else{
					disPlayImage(path);
				}
				errorCount++;
			}

			@Override
			public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
				Log.e("ImageLoader", "===onLoadingComplete");
			}

			@Override
			public void onLoadingCancelled(String arg0, View arg1) {
				Log.e("ImageLoader", "===onLoadingCancelled");
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		avatar = null;
		System.gc();
	}

	private void initView() {
		loading = (RelativeLayout) findViewById(R.id.loading_layout);
		indicator = (PagerSlidingTabStrip) findViewById(R.id.rank_indicator);
		viewPager = (ViewPager) findViewById(R.id.centerpager);
		avatar = (CircularImageView) findViewById(R.id.imageButton_personal_icon);
		avatar.setOnClickListener(this);

		person_name = (TitanicTextView) findViewById(R.id.person_name);
		if(SysApplication.isUserLogin){
			person_name.setText(new SPUtil(this, "userInfo").getStringValue("userName", "未填写"));
		}else{
			person_name.setText("未登录");
		}

		titanic = new Titanic();
		titanic.start(person_name);

	}

	private void setFragment() {
		basicPageFragment = new MyBasicPageFragment();
		catFragment = new MyCatFragment();
		photoFragment = new MyImageFragment();

		fragements[0] = basicPageFragment;
		fragements[1] = catFragment;
		fragements[2] = photoFragment;

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.imageButton_personal_icon:
			Intent intent = new Intent(PersonalCenterActivity.this, PersonPhotoActivity.class);
			// intent.putExtra(SubPageActivity.CLASSFRAMENT,
			// PersonPhotoFragment.class);
			intent.putStringArrayListExtra("photoList", photoList);
			intent.putExtra("type", "fromNet");
			startActivity(intent);
			Utils.scaleInActivityAnimation(this);
			break;

		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	}

	class PersonalCenterAdapter extends FragmentStatePagerAdapter {

		public PersonalCenterAdapter(FragmentManager fragmentManager) {
			super(fragmentManager);
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return titleStrings[position];
		}

		@Override
		public int getCount() {
			return PAGE_NUM;
		}

		@Override
		public Fragment getItem(int position) {
			return fragements[position];
		}
	}

	@Override
	public void finish() {
		Log.e("CNM", "finish================");
		// TODO Auto-generated method stub
		// super.finish();
		titanic.cancel();
	}

	/**
	 * 软键盘返回键的监听
	 */
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Log.e("CNM", "onBackPressed==================");
		super.onBackPressed();
		super.finish();
	}

	@Override
	protected void setContentView() {
		setContentView(R.layout.personal_center);
	}

}
