package com.qnmlgb.fragment;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ninexiu.wjw.R;
import com.qnmlgb.activity.PersonPhotoActivity;
import com.qnmlgb.bean.GetAllTypeData;
import com.qnmlgb.util.MyToast;
import com.qnmlgb.util.Utils;
import com.qnmlgb.view.MyGridView;
import com.qnmlgb.view.MyGridView.OnScrollBottomListener;

public class MyImageFragment extends Fragment{
	private MyGridView gridView;
	private ImageViewAdapter mImageViewAdapter;
	
	private ArrayList<Integer> allData = new ArrayList<Integer>();
	private ArrayList<Integer> data0 = new ArrayList<Integer>();
	private ArrayList<Integer> data1 = new ArrayList<Integer>();
	private ArrayList<Integer> data2= new ArrayList<Integer>();
	private ArrayList<Integer> data3= new ArrayList<Integer>();
	
	private LinearLayout loadingMoreProgress;
	
	
	private static final int MESSAGE_ONE = 1;
	private static final int MESSAGE_TWO = 2;
	private static final int MESSAGE_THREE = 3;
//	private static final int MESSAGE_LOAD = 111;
	private static final int MESSAGE_NO = 666;
	
	private boolean isLoading = false; //是否正在加载
	
	
	private Handler mHandler  = new Handler(){
		public void handleMessage(android.os.Message msg) {
			
			switch (msg.what) {
			case MESSAGE_ONE:
//				mImageViewAdapter.addDatas(data1);
				showGridViewData(data1);  //调用适配器填充数据
				break;
			case MESSAGE_TWO:
//				mImageViewAdapter.addDatas(data2);
				showGridViewData(data2);  //调用适配器填充数据
				break;
			case MESSAGE_THREE:
//				mImageViewAdapter.addDatas(data3);
				showGridViewData(data3);  //调用适配器填充数据
				break;
			case MESSAGE_NO:
				MyToast.MakeToast(getActivity(),"没有数据了");
				break;
			default:
				break;
			}
			if(isLoading){
				isLoading = false;
				loadingMoreProgress.setVisibility(View.GONE);
			}
		}
	};
	
//	private int[] ImageViewIDs_one;
//	private int[] ImageViewIDs_two;
//	private int[] ImageViewIDs_three;
	
	private int pageNum = 0;

	private Bitmap bitmap;
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View content = inflater.inflate(R.layout.ns_my_image_layout, container,
				false);
		return content;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		View contentView = getView();
		initView(contentView);
		setFirstData();
	}

//	private void setListener() {
//		gridView.setmOnScrollBottomListener(new OnScrollBottomListener() {
//
//			@Override
//			public void onScrollBottom() {
//				
//			}
//		});		
//	}

	private void initView(View content) {
		loadingMoreProgress = (LinearLayout)content.findViewById(R.id.linear_loadingmore_progress);
		gridView = (MyGridView) content.findViewById(R.id.img_gridview);
		gridView.setmOnScrollBottomListener(mOnScrollBottomListener);
		
	}

	
	private void setFirstData() {
		data0 = GetAllTypeData.getDataOne(); //第一页数据
		showGridViewData(data0);  //适配器适配数据
	}
	
	private void showGridViewData(ArrayList<Integer> dataList) {
		if(mImageViewAdapter==null){
			mImageViewAdapter = new ImageViewAdapter(getActivity(), dataList);
			gridView.setAdapter(mImageViewAdapter);
		}else{
			mImageViewAdapter.addDatas(dataList);
		}		
	}
	
		
//		ImageViewIDs_one = new int[] { R.drawable.dog1, R.drawable.dog2,
//				R.drawable.dog3, R.drawable.dog4, R.drawable.dog5,
//				R.drawable.dog6, R.drawable.dog7, R.drawable.dog8,
//				R.drawable.dog9, R.drawable.dog10, R.drawable.dog11,
//				R.drawable.dog12, R.drawable.dog13, R.drawable.dog14,
//				R.drawable.dog15, R.drawable.dog16, R.drawable.dog17,
//				R.drawable.dog18, R.drawable.dog19, R.drawable.dog20,
//				R.drawable.dog21 };
//
//		ImageViewIDs_two = new int[] { R.drawable.face1, R.drawable.face2,
//				R.drawable.face3, R.drawable.face4, R.drawable.face5,
//				R.drawable.face6, R.drawable.face7, R.drawable.face8,
//				R.drawable.face9, R.drawable.face10, R.drawable.face11,
//				R.drawable.face12, R.drawable.face13, R.drawable.face14,
//				R.drawable.face15 };
//
//		ImageViewIDs_three = new int[] { R.drawable.person1,
//				R.drawable.person2, R.drawable.person3, R.drawable.person4,
//				R.drawable.person5, R.drawable.person6, R.drawable.person7,
//				R.drawable.person8, R.drawable.person9, R.drawable.person10,
//				R.drawable.person11, R.drawable.person12, R.drawable.person13,
//				R.drawable.person14, R.drawable.person15, R.drawable.person16,
//				R.drawable.person18 };


	@Override
	public void onStop() {
		super.onStop();
		if (getActivity() != null) {
			getActivity().finish();
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		// 先判断是否已经回收  
		if(bitmap != null && !bitmap.isRecycled()){  
		    // 回收并且置为null  
		    bitmap.recycle();  
		    bitmap = null;  
		}  
		System.gc(); 
	}

	private int count;
	
	class ImageViewAdapter extends BaseAdapter {
		private Context mContext;
//		private int[] imgIds;
		private ArrayList<Integer> imgIds;
		private LayoutInflater mInflater;

		public ImageViewAdapter(Context context, ArrayList<Integer> imgIDs) {
			this.mContext = context;
			this.imgIds = imgIDs;
			this.mInflater = LayoutInflater.from(mContext);
		}

		public void addDatas(ArrayList<Integer> datas){
			imgIds.addAll(datas);
			this.notifyDataSetChanged();
		}
		
		@Override
		public int getCount() {
			return imgIds.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@SuppressWarnings("deprecation")
		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			Log.i("CNM","getview() == position : _"+position);
		 //=================  减少 getview()次数
			if (position == 0){  
				count++;  
		    }else{  
		    	count = 0;  
		    }  
		    if (count > 1){  
		        Log.i("CNM", "<getView> drop !!!");  
		        return convertView;  
		    }  
		 //=======================================
			
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.image_gridview_item,
						null);
			}
			mViewHolder = getViewHolder(convertView);
			// mViewHolder.img.setBackgroundResource(imgIds[position]);
				
			bitmap = Utils.readBitmap(getActivity(), imgIds.get(position)); //节省内存  缩放成原图片的1/3，再分配内存
			Drawable resizeImage = Utils.resizeImage(bitmap, 208, 256); //缩放图片
			mViewHolder.img.setImageDrawable(resizeImage);
//			mViewHolder.img.setImageBitmap(bitmap);
			
			mViewHolder.img.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(mContext,PersonPhotoActivity.class);
					intent.putExtra("type","gridview");
					intent.putExtra("photoId",imgIds.get(position));
					startActivity(intent);
					((Activity) mContext).overridePendingTransition(R.anim.zoom_in, 0);//切换Activity的过渡动画
				}
			});
			
			return convertView;
		}

		public ViewHodler getViewHolder(View convertView) {
			ViewHodler holder = (ViewHodler) convertView.getTag();
			if (holder == null) {
				holder = new ViewHodler(convertView);
				convertView.setTag(holder);
			}
			return holder;
		}

		ViewHodler mViewHolder;

		class ViewHodler {
			public ImageView img;

			public ViewHodler(View convertView) {
				img = (ImageView) convertView.findViewById(R.id.img_item);
			}
		}

	}

	OnScrollBottomListener mOnScrollBottomListener = new OnScrollBottomListener() {
		
		@Override
		public void onScrollBottom() {
			if(!isLoading){
				isLoading = true;
				loadingMoreProgress.setVisibility(View.VISIBLE);
				loadingMoreData();
			}
		
		}
	};


	protected void loadingMoreData() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				Message mMessage  =  mHandler.obtainMessage();
				
				// 获取更多数据
				pageNum++;
				switch (pageNum) {
				case 1:
					data1 = GetAllTypeData.getDataTwo(); //第二页
					mMessage.what = MESSAGE_ONE;
					break;
				case 2:
					data2 = GetAllTypeData.getDataThree(); //第三页
					mMessage.what = MESSAGE_TWO;
					break;
				case 3:
					data3 = GetAllTypeData.getDataFour(); //第四页
					mMessage.what = MESSAGE_THREE;
					break;
				default:
//					MyToast.MakeToast(getActivity(),"没有数据了");
					mMessage.what = MESSAGE_NO;
					break;
				}
				mHandler.sendMessageDelayed(mMessage,1800);
			}
		}).start();
	}

}
