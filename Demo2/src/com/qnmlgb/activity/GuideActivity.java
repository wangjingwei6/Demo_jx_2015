package com.qnmlgb.activity;

import java.util.ArrayList;
import java.util.List;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;

import com.ninexiu.sixninexiu.lib.jazzypager.JazzyViewPager;
import com.ninexiu.sixninexiu.lib.jazzypager.JazzyViewPager.TransitionEffect;
import com.ninexiu.sixninexiu.lib.jazzypager.OutlineContainer;
import com.ninexiu.wjw.R;
import com.qnmlgb.login.LoginActivity;
import com.qnmlgb.util.SPUtil;


public class GuideActivity extends BaseActivity{
	
//	private SPUtil mSputil;
//	
//	private List<ImageView> imageViewList;
//	
//	private ViewPager mViewPager;
//	private Button startBut;
//	private LinearLayout linear_point_group;
//	private View point_red;
//	private int basicWidth;// 点的间距
//
//	private float density = 2.0f;// 屏幕密度
//	
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//	getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
//			WindowManager.LayoutParams.FLAG_FULLSCREEN);
//		mSputil = new SPUtil(this,"userInfo");
//		
//		initView();
//		fillData();//填充数据
//		
//	}
//
//	private void initView() {
//		
//		mViewPager = (ViewPager) findViewById(R.id.guideviewpager);
//		startBut = (Button) findViewById(R.id.btn_start_main);
//		linear_point_group = (LinearLayout)findViewById(R.id.guide_point_linear_group);
//		point_red = findViewById(R.id.guide_red_point);
//		
//		startBut.setOnClickListener(this);
//		
//	}
//	
//	private void fillData() {
//		
//		initData();
//		
//		GuidePagerAdapter mAdapter = new GuidePagerAdapter();
//		mViewPager.setAdapter(mAdapter);
//		mViewPager.setOnPageChangeListener(new MyOnPageChangeListener());
//		
//		point_red.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
//			
//			@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
//			@Override
//			public void onGlobalLayout() {
//				//此方法只使用一次,绘制视图树。获取点的间距 
//				point_red.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//				
//				//点的间距= 第1个点 的左边 - 第0个点的左边
//				basicWidth = linear_point_group.getChildAt(1).getLeft() 
//						- linear_point_group.getChildAt(0).getLeft();
//				Log.e("CNM","basicWidth : "+basicWidth);
//			}
//		});
//		
//	}
//
//	private void initData() {
//		//获取图片ID
//		int[]pagerImgIds = new int[]{R.drawable.guide_1,R.drawable.guide_2,R.drawable.guide_3};
//		
//		imageViewList = new ArrayList<ImageView>();
//		ImageView img;
//		for(int i = 0;i<pagerImgIds.length;i++){
//			img = new ImageView(this);
//			img.setBackgroundResource(pagerImgIds[i]);
//			imageViewList.add(img);
//			
//			DisplayMetrics metric = new DisplayMetrics();
//			getWindowManager().getDefaultDisplay().getMetrics(metric);
//			density = metric.density; // 屏幕密度（0.75 / 1.0 / 1.5）
//			
//			Log.e("CNM","density :"+density);
//			
//			//向线性布局添加一个灰色的点
//			View point = new View(this);
//			point.setBackgroundResource(R.drawable.point_normal);
//			LayoutParams params = null;
//			
//			//因为手机分辨率不同 所以做一个适配
//			if(density==0.75){
//				params = new LayoutParams(8,8);
//			}else if(density == 1.0){
//				params = new LayoutParams(10,10);
//			}else if(density == 1.5){
//				params = new LayoutParams(15,15);
//			}else if(density == 2.0){
//				params = new LayoutParams(20,20);
//			}else if(density ==3.0){ //3.0...
//				params = new LayoutParams(30,30);
//			}else{//默认 2.0
//				params = new LayoutParams(20,20);
//			}
//			
//			if(i!= 0){
//				params.leftMargin = 24;
//			}
//			point.setLayoutParams(params);
//			linear_point_group.addView(point);
//		}
//	}
//
//	class MyOnPageChangeListener implements OnPageChangeListener {
//
//		/**
//		 * 当页面滚动时触发此方法
//		 * @param position 当前正在被选中的position
//		 * @param positionOffset 页面移动的比值
//		 * @param positionOffsetPixels 页面移动的像素
//		 */
//		@Override
//		public void onPageScrolled(int position, float positionOffset,
//				int positionOffsetPixels) {
//			Log.e("CNM","onPageScrolled====basicWidth "+basicWidth);
//			// 点最终移动的距离 = 间距 * 比值;
//			int leftMargin = (int) (basicWidth * (position + positionOffset));
////			System.out.println("红色的点移动的距离: " + leftMargin + ", 当前页面的索引: " + position + ", 比值: " + positionOffset);
//			
//			RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) point_red.getLayoutParams();
//			lp.leftMargin = leftMargin;
//			point_red.setLayoutParams(lp);
//		}
//
//		/**
//		 * 当页面选中时触发此方法
//		 */
//		@Override
//		public void onPageSelected(int position) {
//			if(position == imageViewList.size() -1) {
//				startBut.setVisibility(View.VISIBLE);
//			} else {
//				startBut.setVisibility(View.GONE);
//			}
//		}
//
//		/**
//		 * 当页面滚动状态改变时触发此方法
//		 */
//		@Override
//		public void onPageScrollStateChanged(int state) {
//			
//		}
//	}
//
//	@Override
//	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.btn_start_main:
//			mSputil.writeBooleanValue("isFirst",false);
//			
//			Intent toMainIntent = new Intent(GuideActivity.this,TestMainActivity.class);
//			startActivity(toMainIntent);
//			finish();
//			break;
//
//		default:
//			break;
//		}
//	}
//	
//	//====================GuideAdapter
//	class GuidePagerAdapter extends PagerAdapter {
//
//		@Override
//		public int getCount() {
//			return imageViewList.size();
//		}
//
//		@Override
//		public boolean isViewFromObject(View arg0, Object arg1) {
//			return arg0 == arg1;
//		}
//
//		@Override
//		public void destroyItem(ViewGroup container, int position, Object object) {
//			container.removeView((View) object);
//		}
//
//		@Override
//		public Object instantiateItem(ViewGroup container, int position) {
//			// 向ViewPager中添加一个ImageView
//			ImageView iv = imageViewList.get(position);
//			container.addView(iv);
//			// 把添加的ImageView返回回去
//			return iv;
//		}
//	}
	
	
	
	
//两种方式 实现======================================================================================  JazzyViewPager
	private static final String TAG = "GuideActivity";

	private JazzyViewPager viewPager;
	private List<View> viewPageList;
	private float density = 2.0f;// 屏幕密度
	private int pageCount = 3;
	
	private int currentPosition;


	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		viewPager = (JazzyViewPager) findViewById(R.id.guideviewpager);
		viewPager.setTransitionEffect(TransitionEffect.Tablet);
		viewPager.setFadeEnabled(true);
		viewPager.setPageMargin(0);
		viewPageList = new ArrayList<View>();
		viewPageList.add(getPagerItem(0));
		viewPageList.add(getPagerItem(1));
		viewPageList.add(getPagerItem(2));
		
		viewPager.setAdapter(new PagerAdapter() {

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
				container.removeView(viewPager.findViewFromObject(position));
			}

			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				Log.i("CNM","PagerAdapter====position :"+position);
				
				container.addView(viewPageList.get(position));
				viewPager.setObjectForPosition(viewPageList.get(position),
						position);
				return viewPageList.get(position);
			}

			@Override
			public int getCount() {
				return viewPageList.size();
			}
		});
		
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			/**
			 * 当页面滚动时触发此方法
			 * @param position 当前正在被选中的position
			 * @param positionOffset 页面移动的比值
			 * @param positionOffsetPixels 页面移动的像素
			 */
			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
				
				// 点最终移动的距离 = 间距 * 比值;
//				int leftMargin = (int) (basicWidth * (position + positionOffset));
//				Log.e("CNM","红色的点移动的距离: " + leftMargin + ", 当前页面的索引: " + position + ", 比值: " + positionOffset);
//				
//				FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams)point_red.getLayoutParams();
//				lp.leftMargin = leftMargin;
//				point_red.setLayoutParams(lp);
				
				//这里因为position数据不对 所以逻辑不能实现
			}

			/**
			 * 当页面选中时触发此方法
			 */
			@Override
			public void onPageSelected(int position) {
				Log.e("CNM","onPageSelected====position :"+position);
				
				if(position == viewPageList.size() -1) {
					enter.setVisibility(View.VISIBLE);
				} else {
					enter.setVisibility(View.GONE);
				}
			}

			/**
			 * 当页面滚动状态改变时触发此方法
			 */
			@Override
			public void onPageScrollStateChanged(int state) {
				Log.e("CNM","onPageScrollStateChanged====position :"+state);
					
				}
		});

	}

	private View point_1;
	private View point_2;
	private View point_3;
	private ImageView enter;
	private View getPagerItem(int pageNum) {
		View view = getLayoutInflater().inflate(R.layout.guide_content_noviewpager, null);
		ImageView background = (ImageView) view.findViewById(R.id.guide_bg);//viewpager 的大图片
		 enter = (ImageView) view.findViewById(R.id.btn_start_main); //点击体验按钮
		 point_1 =view.findViewById(R.id.guide_point1);
		 point_2 =view.findViewById(R.id.guide_point2);
		 point_3 =view.findViewById(R.id.guide_point3);
		 
		 if(pageNum==0){
				point_2.setBackgroundResource(R.drawable.point_red);
				point_2.setBackgroundResource(R.drawable.point_normal);
				point_3.setBackgroundResource(R.drawable.point_normal);
			}else if(pageNum==1){
				point_1.setBackgroundResource(R.drawable.point_normal);
				point_2.setBackgroundResource(R.drawable.point_red);
				point_3.setBackgroundResource(R.drawable.point_normal);
			}else if(pageNum==2){
				point_1.setBackgroundResource(R.drawable.point_normal);
				point_2.setBackgroundResource(R.drawable.point_normal);
				point_3.setBackgroundResource(R.drawable.point_red);
			}
		 
		switch (pageNum) {
		case 0:
			background.setImageResource(R.drawable.guide_1);
			enter.setVisibility(View.GONE);
			break;
		case 1:
			background.setImageResource(R.drawable.guide_2);
			enter.setVisibility(View.GONE);
			break;
		case 2:
			background.setImageResource(R.drawable.guide_3);
			enter.setVisibility(View.VISIBLE);
//			enter.setVisibility(View.VISIBLE);
			enter.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					Intent intent = new Intent(GuideActivity.this,
							MainUIActivity.class);
					startActivity(intent);
					SPUtil	mSputil = new SPUtil(GuideActivity.this,"userInfo");
					mSputil.writeBooleanValue("isFirst",false);
					finish();
				}
			});
			
			
			break;
		default:
			break;
		}
		
		return view;
	}
	@Override
	protected void setContentView() {
		setContentView(R.layout.guide_content);
	}

}
