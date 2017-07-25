package com.qnmlgb.adapter;

import java.util.List;
import java.util.Map;

import com.ninexiu.wjw.R;
import com.qnmlgb.view.CircularImageView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SimpleLoadListAdapter extends BaseAdapter{
	
	private List<Map<String, Object>> allList;
	private LayoutInflater inflater;

	public SimpleLoadListAdapter(Context context,List<Map<String, Object>> allList) {
		this.allList = allList;
		this.inflater = LayoutInflater.from(context);
	}
	
	public void onDateChange(List<Map<String, Object>> allList) {
		this.allList = allList;
		this.notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return allList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return allList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder mViewHolder;
		if(convertView==null){
			convertView = inflater.inflate(R.layout.list_simple_item, null);
			mViewHolder = new ViewHolder(convertView);
			convertView.setTag(mViewHolder);
		}else{
			mViewHolder= (ViewHolder) convertView.getTag();
		}
		mViewHolder.name.setText(allList.get(position).get("name").toString()); 
		mViewHolder.desc.setText(allList.get(position).get("desc").toString()); 
		mViewHolder.img.setBackgroundResource((Integer) (allList.get(position).get("img"))); 
		
		return convertView;
	}
	
	class ViewHolder {
		TextView name;
		TextView desc;
		ImageView img;
		
		public ViewHolder(View view){
			name = (TextView)view.findViewById(R.id.name);
			desc = (TextView)view.findViewById(R.id.desc);
			img =  (CircularImageView)view.findViewById(R.id.cicularimage);
		}
	}

}
