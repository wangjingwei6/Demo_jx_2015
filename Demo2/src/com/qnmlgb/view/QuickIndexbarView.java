package com.qnmlgb.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 	
 * @author wang jingwei
 *	
 *		1.初始化 (创建一个画笔,给画笔一些基本的属性)
 *		2.用画笔绘制view
 */

@SuppressLint({ "ClickableViewAccessibility", "DrawAllocation" })
public class QuickIndexbarView extends View{
	
	private int cellHeight; //一个格子的高度
	private int cellWidth;//一个格子的宽度
	
	private String[] indexArr = { "A", "B", "C", "D", "E", "F", "G", "H", "I",
			"J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
			"W", "X", "Y", "Z" };
	private Paint mPaint;

	public QuickIndexbarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		init();
	}

	/**
	 *  1.初始化
	 */
	private void init() {
		mPaint = new Paint();
		mPaint.setAntiAlias(true); //抗锯齿
		mPaint.setTypeface(Typeface.DEFAULT_BOLD);//默认粗体
		
	}
	
	/**
	 *	2. 绘制view(测量出格子的宽高   绘制每个字母 根据格子的宽高来测量字母的位置 绘制)
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		//只获取一次一个格子的宽高
		if(cellHeight==0){
			cellHeight = getMeasuredHeight()/indexArr.length;
		}
		if(cellWidth==0){
			cellWidth = getMeasuredWidth();
		}
		
		for(int i = 0; i<indexArr.length;i++){
			
			mPaint.setColor(i==touchIndex ? Color.parseColor("#00B2FD") : Color.parseColor("#666666") );
			mPaint.setTextSize(i==touchIndex ? 42 : 30);
			//存储字母text的参数的对象 
			Rect  mBounds  =  new  Rect();
			//获得字母text的参数 宽高
			mPaint.getTextBounds(indexArr[i],0,indexArr[i].length(),mBounds);
			
			// 获取字母宽度的两种方式，注意的其中的区别
//			 float x = cellWidth / 2 - mPaint.measureText(indexArr[i]) / 2;
			float x  =  cellWidth/2 - mBounds.width()/2;
			float y  =  cellHeight/2 + mBounds.height()/2 + i*cellHeight;   
			
			canvas.drawText(indexArr[i], x, y,mPaint);
			
		}
	}
	
	
		private int touchIndex = -1;  //当前手指触碰到的位置
	
		@Override
		public boolean onTouchEvent(MotionEvent event) {
			
			int y = (int) event.getY(); //Y轴页面触摸的位置
			
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
			case MotionEvent.ACTION_MOVE:
				//按下或者移动 触发事件
				
				if(y / cellHeight != touchIndex){  //防止move事件一直刷新 回调事件
					 touchIndex = y/cellHeight;
					 
					 if(0 < touchIndex && touchIndex < indexArr.length){ //在指定view的起点到终点 范围
						 	// 回调监听
						 if(mTouchIndexListener!=null){
							 String word = indexArr[touchIndex];
							 mTouchIndexListener.onTouchIndex(word);
						 }
					 }
					 
				}
				
				break;
			case MotionEvent.ACTION_UP:
				touchIndex = -1;
				break;

			default:
				break;
			}
			
			
			
			
			invalidate();
			
			return true;
		}
	
		private OnTouchIndexListener mTouchIndexListener;
		
//		public OnTouchIndexListener getmTouchIndexListener() {
//			return mTouchIndexListener;
//		}

		public void setmTouchIndexListener(OnTouchIndexListener mTouchIndexListener) {
			this.mTouchIndexListener = mTouchIndexListener;
		}

		public interface OnTouchIndexListener{
			void onTouchIndex(String word);
		}

		public void setChangeIndex(int i) {
			touchIndex = i ;
			invalidate();
		}

}
