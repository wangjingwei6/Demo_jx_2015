package com.qnmlgb.adapter;


import com.ninexiu.wjw.R;
import com.qnmlgb.util.Utils;

import android.annotation.SuppressLint;
import android.content.Context;  
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;  
import android.view.View;  
import android.view.ViewGroup;  
import android.widget.BaseAdapter;  
import android.widget.ImageView;  
  
/** 
 * 为画廊定义适配器 
 * @author Administrator 
 * 
 */  
@SuppressLint("ViewHolder")
public class HSVAdapter extends BaseAdapter {  
  
    public int[]imgIDs;  
    private Context context;
	private Bitmap readBitmap;  
    public HSVAdapter(Context context,int[]imgs){  
        this.context=context;  
        this.imgIDs = imgs;
    }  
    @Override
	public int getCount() {
		return 96;
	}
	@Override
	public Object getItem(int position) {
		return position;
	}
	@Override
	public long getItemId(int position) {
		return position;
	}  
    
    public void addObject(int[]imgs){  
        notifyDataSetChanged();  
    }  
    @SuppressWarnings("deprecation")
	@Override  
    public View getView(int position, View arg1, ViewGroup arg2) {  
        View view = LayoutInflater.from(context).inflate(R.layout.image_gallery_item,null);  
        ImageView image=(ImageView)view.findViewById(R.id.img_item); 
        readBitmap = Utils.readBitmap(context,imgIDs[position%imgIDs.length],2);
        Drawable resizeImage = Utils.resizeImage(readBitmap,204,208);
        image.setBackgroundDrawable(resizeImage);
//      image.setBackgroundDrawable(new BitmapDrawable(readBitmap));
        return view;  
    }
    
    
	public void recycle() {
		// 先判断是否已经回收  
		if(readBitmap != null && !readBitmap.isRecycled()){  
		    // 回收并且置为null  
			readBitmap.recycle();  
			readBitmap = null;  
		}  
		System.gc(); 		
	}
	
}  