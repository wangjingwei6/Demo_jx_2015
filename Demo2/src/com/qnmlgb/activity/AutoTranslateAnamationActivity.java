package com.qnmlgb.activity;

import com.ninexiu.wjw.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class AutoTranslateAnamationActivity extends Activity{
	private ImageView imageview;
	private ImageView imageView_second;
	private LinearLayout linear_1;  //左边图所有布局
	private LinearLayout linear_2;  //右边图所在布局
	private int measuredWidthLeftImage;  //左侧布局的宽度
	public float toXDelta;  //动画停止的位置
	private  float fromXDelta;  //动画开始的位置
	private int imageViewWidth;
	private float toLeft;
	private int START_ANIMATION = 666;
	private int count=0;
	
	Handler mHandler = new Handler(new Callback() {
		
		@Override
		public boolean handleMessage(Message msg) {
			switch (msg.what) {
			case 666:
				Log.e("Animation", "handler接收到开始动画的处理信息");
				Animation translationAnimation = translationAnimation(fromXDelta,toXDelta);
				imageview.startAnimation(translationAnimation);
				break;

			default:
				break;
			}
			
			
			
			return false;
		}
	});
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setTheme(R.style.AnimationTheme);
		setContentView(R.layout.auto_animation);
		setProgressBarIndeterminateVisibility(true);
		
		
		imageview = (ImageView) findViewById(R.id.img_signal);
		imageView_second = (ImageView)findViewById(R.id.second_img);
		linear_1 = (LinearLayout)findViewById(R.id.linear_1);
		linear_2 = (LinearLayout)findViewById(R.id.linear_2);
		
		ViewTreeObserver viewTreeObserver = linear_1.getViewTreeObserver();
		viewTreeObserver.addOnPreDrawListener(new OnPreDrawListener() {
			
			@Override
			public boolean onPreDraw() {
				int[] position = new int[2];  
				linear_1.getLocationOnScreen(position);  
				toLeft = position[0];
				
				int[] position2 = new int[2];  
				linear_2.getLocationOnScreen(position2);
				
				int[]positionView  = new int[2];
				imageview.getLocationOnScreen(positionView);
				
				measuredWidthLeftImage = linear_1.getMeasuredWidth();
				
				imageViewWidth =imageview.getMeasuredWidth();
				fromXDelta =(float)imageViewWidth;
				
				 toXDelta = (float) -(positionView[0]-(measuredWidthLeftImage+toLeft-(1.5*toLeft)));
				
				  if(count==0){
					  Message msg = new Message();    
				      msg.what = START_ANIMATION;  
				      mHandler.sendMessage(msg);
				      count++;
				  }
				     
				return true;
			}
		});
	}
		
//		DisplayMetrics metric = new DisplayMetrics();  
//		getWindowManager().getDefaultDisplay().getMetrics(metric);  
//		int Screenwidth = metric.widthPixels;
		
//		imageView_second.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//					Intent intent = new Intent(AutoTranslateAnamationActivity.this,SecondActivity.class);
//					AutoTranslateAnamationActivity.this.startActivity(intent);
//			}
//		});
//
//	}
	
	/**	  
	 * 
	 * 	  1.平移前的view 所在当前屏幕的初始X值   currentX
	 * 	  2.平后后view 所在当前屏幕的最终X值       lastX
	 * 		currentX  =  imageview获取当前屏幕的X值
	 * 		lastX  =  linear_1 的宽度+左边距    -  linear_1的左边距* 左边距*1.5
	 * 
	 * 		移动后的位置 （toXDelta） =  -(currentX - lastX)
	 * 		移动前的位置(fromXDelta)  =  -(当前 imageView的宽度)
	 */
	
	public Animation translationAnimation(float fromXDelta,float toXDelta){
			Animation mAnimation =  new TranslateAnimation(fromXDelta, toXDelta, 0, 0);
			mAnimation.setInterpolator(new DecelerateInterpolator());
			mAnimation.setRepeatCount(-1);
			mAnimation.setDuration(3000);
			mAnimation.setFillAfter(true);                   //停留在结束位置
			mAnimation.startNow();
			return mAnimation;
	}


}
