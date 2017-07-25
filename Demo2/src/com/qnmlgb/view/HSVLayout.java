package com.qnmlgb.view;

import com.ninexiu.wjw.R;
import com.qnmlgb.activity.PersonPhotoActivity;
import com.qnmlgb.adapter.HSVAdapter;
import com.qnmlgb.bean.GetAllTypeData;

import android.app.Activity;
import android.content.Context;  
import android.content.Intent;
import android.util.AttributeSet;  
import android.view.View;  
import android.widget.LinearLayout;  
import android.widget.Toast;
  
public class HSVLayout extends LinearLayout {  
  
    private HSVAdapter adapter;  
    private Context context;
    private int[] imgIds;
  
    public HSVLayout(Context context, AttributeSet attrs) {  
        super(context, attrs);  
        this.context = context;  
        this.imgIds =GetAllTypeData.getInstance().getImgIDs();
    }  
  
    public void setAdapter(HSVAdapter adapter) {  
    	 this.adapter = adapter;  
        for (int i = 0; i < adapter.getCount(); i++) {  
        	final int position =  (Integer) adapter.getItem(i);  
            View view = adapter.getView(i, null, null);  
            view.setPadding(6, 0, 6, 0);  
            // 为视图设定点击监听器    
            view.setOnClickListener(new OnClickListener() {  
                @Override  
                public void onClick(View v) {  
                	
                	//跳转	personPhotoActivity
//                    Toast.makeText(context, "您选择了" +position+" 个image",  
//                            Toast.LENGTH_SHORT).show();  
                    
                    Intent photoIntent = new Intent(context,
        					PersonPhotoActivity.class);
//        			intent.putExtra(SubPageActivity.CLASSFRAMENT, PersonPhotoFragment.class);
        			photoIntent.putExtra("type","gallery");
        			photoIntent.putExtra("photoId",imgIds[position%imgIds.length]);
        			context.startActivity(photoIntent);
        			((Activity) context).overridePendingTransition(R.anim.zoom_in, 0);//切换Activity的过渡动画                      
                }  
            });  
            this.setOrientation(HORIZONTAL);  
            this.addView(view, new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT
                    /*LayoutParams.WRAP_CONTENT*/, LayoutParams.WRAP_CONTENT));  
        }  
    }  
}  