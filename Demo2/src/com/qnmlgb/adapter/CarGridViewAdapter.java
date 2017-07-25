package com.qnmlgb.adapter;

import com.ninexiu.wjw.R;
import com.qnmlgb.util.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CarGridViewAdapter extends BaseAdapter {

	private Context mContext;
	private String []carNames;
	private int []smallIcons;
	private int []bigIcons;

	public CarGridViewAdapter(Context context,String[]nameIDs,int[]smallIDs,int[]bigIDs) {
		this.mContext = context;
		this.carNames = nameIDs;
		this.smallIcons = smallIDs;
		this.bigIcons = bigIDs;
	}

	@Override
	public int getCount() {
		return carNames.length;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		GViewHolder holder;
		if (convertView == null) {
			holder = new GViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.shop_car_gridview_item, null, false);
			holder.car_smallicon = (ImageView) convertView
					.findViewById(R.id.car_smallicon);
			holder.car_title = (TextView) convertView
					.findViewById(R.id.car_title);
			holder.car_img = (ImageView) convertView.findViewById(R.id.car_img);
			convertView.setTag(holder);
		} else {
			holder = (GViewHolder) convertView.getTag();
		}
		holder.car_title.setText(carNames[position]);
		holder.car_smallicon.setBackgroundResource(smallIcons[position]);
//		holder.car_img.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources(), bigIcons[position]));
		Bitmap readBitmap = BitmapFactory.decodeResource(mContext.getResources(), bigIcons[position]);
		Drawable resizeImage = Utils.resizeImage(readBitmap,144,115); //ÐÞ¸ÄÍ¼Æ¬³ß´çËõ·Å
		holder.car_img.setImageDrawable(resizeImage);
		
		return convertView;
	}

	public static class GViewHolder {

		ImageView car_smallicon;
		TextView car_title;
		ImageView car_img;
		TextView car_price;

	}

}
