package com.qnmlgb.fragment;

import java.util.ArrayList;

import com.ninexiu.wjw.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.qnmlgb.activity.PersonPhotoActivity;
import com.qnmlgb.util.MyImageLoaderUtil;
import com.qnmlgb.view.CircularImageView;
import com.qnmlgb.view.PagerSlidingTabStrip;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.TextView;

public class PersonCenterFragment extends BaseFragment implements OnClickListener{
	
	private static final int PAGE_NUM = 3;
	public static final int PAGE_BASIC = 0;
	public static final int PAGE_GARAGE = 1;
	public static final int PAGE_GUARD = 2;

	private String[] titleStrings = new String[] { "基本资料", "我的车库", "个人相册" };

	private Fragment fragements[] = new Fragment[3];

	private ViewPager viewPager;
	public CircularImageView avatar;
	public TextView name;
	private PagerSlidingTabStrip indicator;
	public View loadingView;
	
	private MyBasicPageFragment basicPageFragment;
	private MyCatFragment catFragment;
	private MyImageFragment photoFragment;
	
	private TextView title;

	private int position; // 个人头像的 索引值(集合里三种头像)
	private ArrayList<String> photoList; // 头像集合 (网络地址参数)
	private String img1 = "http://img.popoho.com/allimg/120505/155K040F-5.jpg";
	private String img2 = "http://www.jf258.com/uploads/2014-09-08/104734923.jpg";
	private String img3 = "http://www.jf258.com/uploads/2014-09-08/104735285.jpg";
	private String img4 = "http://p.3761.com/pic/84201393378242.jpg";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		View view = getView();
		
		initView(view);
		initAvatarData();
		setFragment();
		
		PersonalCenterAdapter mPersonalCenterAdapter = new PersonalCenterAdapter(
				getActivity().getSupportFragmentManager());
		viewPager.setAdapter(mPersonalCenterAdapter);
		viewPager.setOffscreenPageLimit(2);
		viewPager.setCurrentItem(0);
		indicator.setShouldExpand(true);
		indicator.setViewPager(viewPager);
		indicator.setTextColorResource(R.color.hong,
				R.color.livehall_tab_text_unselected);
		indicator.setTextSize(getResources().getDimensionPixelSize(
				R.dimen.livehall_tab_textsize));

		viewPager.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				v.getParent().requestDisallowInterceptTouchEvent(true);
				return false;
			}
		});
		
	}

	private void initAvatarData() {
		photoList = new ArrayList<String>();
		position = (int) (Math.random() * 3 + 1);
		switch (position) {
		case 0:
			photoList.add(img1);
			break;
		case 1:
			photoList.add(img2);
			break;
		case 2:
			photoList.add(img3);
			break;
		case 3:
			photoList.add(img4);
			break;

		default:
			break;
		}
		String path = photoList.get(0);
		MyImageLoaderUtil imageLoadUtil = MyImageLoaderUtil.getImageLoadUtil(getActivity());
		imageLoadUtil.init();
		imageLoadUtil.displayImage(avatar, path);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		avatar = null;
		name = null;
		System.gc();
	}

	private void initView(View view) {
		indicator = (PagerSlidingTabStrip)view.findViewById(R.id.rank_indicator);
		viewPager = (ViewPager)view.findViewById(R.id.centerpager);
		avatar = (CircularImageView)view.findViewById(R.id.imageButton_personal_icon);
		avatar.setOnClickListener(this);
		name = (TextView)view.findViewById(R.id.center_name);

		title = (TextView)view.findViewById(R.id.title);
		title.setText("个人");
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
			Intent intent = new Intent(getActivity(),
					PersonPhotoActivity.class);
//			intent.putExtra(SubPageActivity.CLASSFRAMENT, PersonPhotoFragment.class);
			intent.putStringArrayListExtra("photoList", photoList);
			intent.putExtra("type","fromNet");
			getActivity().startActivity(intent);
			getActivity().overridePendingTransition(R.anim.zoom_in, 0);//切换Activity的过渡动画 
			break;

		default:
			break;
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
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
	protected View inflate(LayoutInflater inflater) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.personal_center, null);
	}

}
