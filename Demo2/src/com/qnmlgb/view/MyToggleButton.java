package com.qnmlgb.view;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * 
 * @author wangjingwei
 *
 */
public class MyToggleButton extends View{

	private Context mContext;
//	private EffectSettingManager effectSettingManager;
	
	private onToggleStateChangeListener mOnToggleStateChangeListener;
	private Bitmap switchBackgroundGrayBitmap;// 滑动开关灰色背景图
	private Bitmap switchBackgroundRedBitmap;// 滑动开关红色背景图
	private Bitmap slideBackgroundBitmap;// 滑动块图片

	private int currentX; // 当触摸时, 触摸的那个点的x轴的值
	private boolean currentStatus; // 开关当前的状态,初始化false
	private boolean isSliding = false; //初始化是否正在滑动，默认为false
	
	private boolean isTouch=false;
	
	private int mId;

	public MyToggleButton(Context context) {
		super(context);
//		init();
	}
	public MyToggleButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = getContext();
//		init();
		String namespace = "http://schemas.android.com/apk/res/com.ninexiu.wjw";
		// 取出背景图片ID
		int switchBackgroundgrayID = attrs.getAttributeResourceValue(namespace,
				"switchBackgroundgray", -1);
		int switchBackgroundredID = attrs.getAttributeResourceValue(namespace,
				"switchBackgroundred", -1);
		int sllideBackgroundID = attrs.getAttributeResourceValue(namespace,
				"slideButtonBackground", -1);
		
		setSwitchBackgroundGray(switchBackgroundgrayID);
		setSwitchBackgroundRed(switchBackgroundredID);
		setSlideBackground(sllideBackgroundID);
	}
	
	
	public MyToggleButton(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

	}

	public void setStatus(boolean isActive){
			this.currentStatus = isActive;
			invalidate();
	}
	
	
	
	/**
	 * 设置控制开关的滑动块背景图片
	 * 
	 * @param sllideBackgroundID
	 */
	private void setSlideBackground(int sllideBackgroundID) {
		slideBackgroundBitmap = BitmapFactory.decodeResource(getResources(), sllideBackgroundID);
	}

	/**
	 * 设置开关的红色背景图片
	 * 
	 * @param switchBackgroundredID
	 */
	private void setSwitchBackgroundRed(int switchBackgroundredID) {
		switchBackgroundRedBitmap = BitmapFactory.decodeResource(getResources(),switchBackgroundredID);
	}

	/**
	 * 设置开关的灰色背景图片
	 * 
	 * @param switchBackgroundgrayID
	 */
	private void setSwitchBackgroundGray(int switchBackgroundgrayID) {
		switchBackgroundGrayBitmap = BitmapFactory.decodeResource(getResources(),switchBackgroundgrayID);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		// 当前控件宽高设置成背景图片宽高
		setMeasuredDimension(switchBackgroundGrayBitmap.getWidth(), switchBackgroundGrayBitmap.getHeight());
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		//把默认灰色背景图片画到当前控件
//		canvas.drawBitmap(switchBackgroundGrayBitmap,0,0,null);
		//把滑动开关画到当前控件
//		canvas.drawBitmap(slideBackgroundBitmap, 0,0, null);
		if(isSliding){//正在滑动
			int left = currentX - slideBackgroundBitmap.getWidth() / 2;
			int rightAlign = switchBackgroundGrayBitmap.getWidth()- slideBackgroundBitmap.getWidth();
			
			if(left<0){
				left=0;
			}else if(left>rightAlign){
				left =rightAlign;
			}
			int templeft =left;
			if(currentStatus){
				canvas.drawBitmap(switchBackgroundRedBitmap, 0, 0 , null);
			}else{
				canvas.drawBitmap(switchBackgroundGrayBitmap, 0, 0 , null);
			}
			canvas.drawBitmap(slideBackgroundBitmap, templeft, 0 , null);
			
			
		}else{   //停止滑动
//			if(currentStatus){//当前状态是开的状态
//				//把默认背景图片切换成红色背景图片
//				canvas.drawBitmap(switchBackgroundRedBitmap,0,0,null);
//				int left = switchBackgroundRedBitmap.getWidth()-slideBackgroundBitmap.getWidth();
//				canvas.drawBitmap(slideBackgroundBitmap, left, 0, null);
//				currentStatus = true;
//			}else{
//				canvas.drawBitmap(switchBackgroundGrayBitmap, 0, 0, null);
//				canvas.drawBitmap(slideBackgroundBitmap ,0, 0 ,null);
//				currentStatus = false;
//			}
//			
			//当前没有滑动，说明是纯点击事件  
			changeShowToggleButton(canvas,currentStatus); 
		}
		
		Log.i("MyToggleButton", "MyToggleButton : 触发 onDraw方法");
		
	}
	
	public void setToggleStatus(boolean status){

		currentStatus = status;
		
		invalidate();
	}
	
	
	/**
	 * 
	 */
	public void changeShowToggleButton(Canvas canvas,boolean currentStatus){
		if(currentStatus){//当前状态是开的状态
			//把默认背景图片切换成红色背景图片
			canvas.drawBitmap(switchBackgroundRedBitmap,0,0,null);
			int left = switchBackgroundRedBitmap.getWidth()-slideBackgroundBitmap.getWidth();
			canvas.drawBitmap(slideBackgroundBitmap, left, 0, null);
			
		}else{
			canvas.drawBitmap(switchBackgroundGrayBitmap, 0, 0, null);
			canvas.drawBitmap(slideBackgroundBitmap ,0, 0 ,null);
		}
	}
	
	int firstX= 0;
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
//		int firstX = (int) event.getX();
		Log.e("MyToggleButton", "触摸=="+firstX);
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			Log.e("MyToggleButton", "ACTION_DOWN=触发"+"=="+(int) event.getX());
			isSliding =false;
			firstX = currentX = (int) event.getX();
			break;
		case MotionEvent.ACTION_MOVE:
			Log.e("MyToggleButton", "ACTION_MOVE=触发"+"=="+(int) event.getX());
//			isSliding =true;
			if(Math.abs(firstX-event.getX())>1){
				isSliding = true;
				currentX = (int) event.getX();
			}
			
			break;
		case MotionEvent.ACTION_UP:
			Log.e("MyToggleButton", "ACTION_UP=触发");
			
			if(isSliding){ //滑动
				Log.e("MyToggleButton", "ACTION_UP=滑动滑动滑动滑动滑动滑动");
				 isSliding =false;
				currentX = (int) event.getX();
				int center = switchBackgroundGrayBitmap.getWidth()/2; 
					boolean state = currentX>center;
					//触发回调事件
					if(state!=currentStatus&&mOnToggleStateChangeListener!=null){
						currentStatus = state;
						mOnToggleStateChangeListener.onToggleStateChange(currentStatus,mId);
					}
					currentStatus = state;  // true 开  false 关
			}else{  //点击事件，没有滑动
				Log.e("MyToggleButton", "ACTION_UP=点击点击点击点击点击");
				currentStatus = !currentStatus;
				
				if(mOnToggleStateChangeListener!=null){
					mOnToggleStateChangeListener.onToggleStateChange(currentStatus,mId);
				}
			}
			
			break;
		default:
			break;
		}
		invalidate(); //刷新当前控件 触发ondraw方法
		return true;  //当前控件来处理此次触摸事件
	}

	public void setOnToggleStateChangeListener(onToggleStateChangeListener listener,int id){
		this.mOnToggleStateChangeListener = listener;
		this.mId =id; 
	}
	
	
	public interface onToggleStateChangeListener{
		public void onToggleStateChange(boolean state,int id);
	}

}
