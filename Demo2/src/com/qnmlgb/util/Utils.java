package com.qnmlgb.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.ninexiu.wjw.R;
import com.qnmlgb.login.BaseRegeistOrLogin;
import com.qnmlgb.view.MaterialRippleLayout;

public class Utils {

	private static Bitmap bitmap;
	private static long lastClickTime;

	// 登录进度条 回调接口
	public interface LoginDialogListenner {
		public void confirm();

		public void cancle();
	}

	// 输入框编辑回调接口
	public interface BaseInputDialogOnclickListener {

		public void onConfirm(String edtString);
		public void onCancel();
	}
	
	// 注册回调接口
	
	public interface  UserRegeist{
		public void onConfirm(String userName,String passWord);
		public void onCancel();
	}
	
	/**
	 * 获取手机屏幕密度 比值 
	 */
	
	public static float getDensity(Activity activity){
		
		DisplayMetrics metric = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
		Float density = metric.density; // 屏幕密度（0.75 / 1.0 / 1.5 / 2.0 / 3.0）
		return  density;
	}
	
	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 * */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	
//==========================================================================================================
	public final static String MD5(String s) {
		MessageDigest md;
		try {
			// 生成一个MD5加密计算摘要
			md = MessageDigest.getInstance("MD5");
			// 计算md5函数
			md.update(s.getBytes());
			// digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
			// BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
			String result = new BigInteger(1, md.digest()).toString(16);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 数据加密 不能有特殊字符
	 * 
	 * @param text
	 * @return
	 * @author by_wsc
	 * @email wscnydx@gmail.com
	 * @date 日期：2014-1-15 下午3:15:58
	 */
	public static String stringEncode(String text) {
		if (TextUtils.isEmpty(text)) {
			return "";
		}
		String encode = Base64.encodeToString(text.getBytes(), Base64.DEFAULT);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < encode.length(); i++) {
			char oldChar = encode.charAt(i);
			char newChar = (char) (oldChar * 2 + 1);
			sb.append(newChar);
		}
		return sb.toString();
	}

	/**
	 * 数据解密 不能有特殊字符
	 * 
	 * @param text
	 * @return
	 * @author by_wsc
	 * @email wscnydx@gmail.com
	 * @date 日期：2014-1-15 下午3:18:56
	 */
	public static String stringDecode(String text) {
		if (TextUtils.isEmpty(text)) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < text.toString().length(); i++) {
			char oldChar = text.toString().charAt(i);
			char newChar = (char) ((oldChar - 1) / 2);
			sb.append(newChar);
		}

		return new String(Base64.decode(sb.toString(), Base64.DEFAULT));

	}
	
	
	
	
	/**
	 * bitmap转为base64
	 * 
	 * @param bitmap
	 * @return
	 */
	public static String bitmapToBase64(Bitmap bitmap) {

		String result = null;
		ByteArrayOutputStream baos = null;
		try {
			if (bitmap != null) {
				baos = new ByteArrayOutputStream();
				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

				baos.flush();
				baos.close();

				byte[] bitmapBytes = baos.toByteArray();
				result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (baos != null) {
					baos.flush();
					baos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * base64转为bitmap
	 * 
	 * @param base64Data
	 * @return
	 */
	public static Bitmap base64ToBitmap(String base64Data) {
		byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
		return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
	}

	/**
	 * 转换图片成圆形
	 * 
	 * @param bitmap
	 *            传入Bitmap对象
	 * @return
	 */
	public static Bitmap toRoundBitmap(Bitmap bitmap) {
		try {

			Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
					bitmap.getHeight(), Config.ARGB_8888);
			Canvas canvas = new Canvas(output);

			final int color = 0xff424242;

			final Paint paint = new Paint();
			final Rect rect = new Rect(0, 0, bitmap.getWidth(),
					bitmap.getHeight());

			final RectF rectF = new RectF(rect);
			final float roundPx = bitmap.getWidth() / 2;

			paint.setAntiAlias(true);
			canvas.drawARGB(0, 0, 0, 0);
			paint.setColor(color);
			canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
			paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
			canvas.drawBitmap(bitmap, rect, rect, paint);
			return output;

		} catch (Exception e) {
			e.printStackTrace();
		} catch (Error e) {
			e.printStackTrace();
			System.gc();
		}
		return null;
	}
	
	
	
	
//======================================================================================================

	/**
	 * 给view 添加点击动画 必须在设置点击事件之前设置
	 * 
	 * @param view
	 * @return
	 */
	public static void addRippleEffect(View view) {
		MaterialRippleLayout.on(view).rippleOverlay(true).rippleAlpha(0.2f)
				.rippleColor(0x0A000000).rippleHover(true).create();
	}

	/**
	 * 
	 * @param view
	 * @param radiusDp
	 *            圆角的dp值
	 */
	public static void addRippleEffect(View view, int radiusDp) {
		MaterialRippleLayout.on(view).rippleOverlay(true).rippleAlpha(0.2f)
				.rippleColor(0x0A000000).rippleHover(true)
				.rippleRoundedCorners(radiusDp).create();
	}

	public static void addRippleEffectToItem(View view) {
		MaterialRippleLayout.on(view).rippleOverlay(true).rippleAlpha(0.2f)
				.rippleColor(0x0A000000).rippleHover(true)
				.rippleInAdapter(true).create();
	}

	/**
	 * 通过两次点击毫秒值间隔判断是否可以点击
	 * 
	 * @param lastTime
	 * @return
	 */
	public static boolean isClickable() {
		long time = System.currentTimeMillis();
		long timeD = time - lastClickTime;
		if (0 < timeD && timeD < 800) {
			return true;
		}
		lastClickTime = time;
		return false;
	}

	/**
	 * 缩放图片
	 * 
	 * @param bitmap
	 * @param w 目标图片宽度 
	 * @param h	目标图片高度 
	 * @return
	 */

	public static Drawable resizeImage(Bitmap bitmap, int w, int h) {
		if (bitmap == null) {
			return null;
		}
		// load the origial Bitmap
		Bitmap BitmapOrg = bitmap;

		int width = BitmapOrg.getWidth(); // 原始图片宽度	
		int height = BitmapOrg.getHeight(); //原始图片高度

		// calculate the scale
		float scaleWidth = ((float) w) / width;
		float scaleHeight = ((float) h) / height;

		// create a matrix for the manipulation
		Matrix matrix = new Matrix();
		// resize the Bitmap
		matrix.postScale(scaleWidth, scaleHeight);
		// if you want to rotate the Bitmap
		// matrix.postRotate(45);

		// recreate the new Bitmap
		Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, width,
				height, matrix, true);

		// make a Drawable from Bitmap to allow to set the Bitmap
		// to the ImageView, ImageButton or what ever
		return new BitmapDrawable(resizedBitmap);

	}

	public void drawableToBitamp(Drawable drawable) {

		int w = drawable.getIntrinsicWidth();
		int h = drawable.getIntrinsicHeight();
		System.out.println("Drawable转Bitmap");
		Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
				: Bitmap.Config.RGB_565;
		bitmap = Bitmap.createBitmap(w, h, config);
		// 注意，下面三行代码要用到，否在在View或者surfaceview里的canvas.drawBitmap会看不到图
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, w, h);
		drawable.draw(canvas);
	}

	/**
	 * 省内存方式从本地读取图片 ，避免OOM  指定缩小比例
	 */

	public static Bitmap readBitmap(Context context, int resId,int num) {
			
				InputStream	is = context.getResources().openRawResource(resId);
					BitmapFactory.Options options = new BitmapFactory.Options();
					options.inJustDecodeBounds = true; // 不返回实际bitmap。不分配内存空间 但是可以得到一些图片信息
					options.inSampleSize = num;

					options.inPreferredConfig = Bitmap.Config.RGB_565;
					options.inPurgeable = true; // 让系统能及时回收内存
					options.inInputShareable = true; // 让系统能及时回收内存

					options.inJustDecodeBounds = false;
					bitmap = BitmapFactory.decodeStream(is, null, options);
		
					return bitmap;
	}
	
	/**
	 * 省内存方式从本地读取图片 ，避免OOM
	 */

	public static Bitmap readBitmap(Context context, int resId) {
			
				InputStream	is = context.getResources().openRawResource(resId);
					BitmapFactory.Options options = new BitmapFactory.Options();
					options.inJustDecodeBounds = true; // 不返回实际bitmap。不分配内存空间 但是可以得到一些图片信息
					options.inSampleSize = 3;

					options.inPreferredConfig = Bitmap.Config.RGB_565;
					options.inPurgeable = true; // 让系统能及时回收内存
					options.inInputShareable = true; // 让系统能及时回收内存

					options.inJustDecodeBounds = false;
					bitmap = BitmapFactory.decodeStream(is, null, options);
		
					return bitmap;
	}

	
	
//==================================================================================================	

	/**
	 * 生成圆形进度条(中间带图片的)
	 * 
	 * @param context
	 * @return
	 */
	public static Dialog showProgressDialog(Context context, String message,
			boolean cancelableOnTouch) {
		Dialog dialog = new Dialog(context, R.style.loading_dialog);
		dialog.setCanceledOnTouchOutside(cancelableOnTouch);
		dialog.setCancelable(cancelableOnTouch);
		View view = LayoutInflater.from(context).inflate(R.layout.ns_loading,
				null);
		dialog.setContentView(view);
		TextView tv = (TextView) view.findViewById(R.id.tipTextView);
		tv.setText(message);
		ImageView imageView = (ImageView) view.findViewById(R.id.img);
		imageView.startAnimation(AnimationUtils.loadAnimation(context,
				R.anim.loading_animation));
		return dialog;
	}
	
	/**
	 * 生成圆形进度条
	 * 
	 * @param context
	 * @return
	 */
	public static Dialog showProgressDialogNoCenterImg(Context context, String message,
			boolean cancelableOnTouch) {
		Dialog dialog = new Dialog(context, R.style.loading_dialog);
		dialog.setCanceledOnTouchOutside(cancelableOnTouch);
		dialog.setCancelable(cancelableOnTouch);
		View view = LayoutInflater.from(context).inflate(R.layout.ns_loading_two,
				null);
		dialog.setContentView(view);
		TextView tv = (TextView) view.findViewById(R.id.tipTextView);
		tv.setText(message);
		ImageView imageView = (ImageView) view.findViewById(R.id.img);
		imageView.startAnimation(AnimationUtils.loadAnimation(context,R.anim.loading_animation));
		return dialog;
	}
	
	  /**  
     * dialog   生成小的旋转进度条   不带图片,配文字
     * @return
     */
	public static Dialog showSmallProgressDialog(Context mContext, String msg) {
		LayoutInflater inflater = LayoutInflater.from(mContext);
		View view = inflater.inflate(R.layout.ns_dialog_loading, null);
		LinearLayout layout = (LinearLayout) view.findViewById(R.id.ns_dialog_loading);
		// 图标和文字
		ImageView loadImg = (ImageView) view.findViewById(R.id.iv_loding);
		TextView message = (TextView) view.findViewById(R.id.tv_dialog);

		// 加载动画
		Animation anim = AnimationUtils.loadAnimation(mContext,R.anim.loading_animation);
		loadImg.startAnimation(anim);
		message.setText(msg);

		Dialog loadDialog = new Dialog(mContext, R.style.loading_dialog);
		loadDialog.setCancelable(true);
		loadDialog.setContentView(layout, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT));
		return loadDialog;
	}
	
	  /**  
     * dialog   生成小的旋转进度条   不带图片,配文字
     * @return
     */
	public static Dialog showSmallMultiColorProgressDialog(Context mContext, String msg) {
		LayoutInflater inflater = LayoutInflater.from(mContext);
		View view = inflater.inflate(R.layout.ns_dialog_loading, null);
		LinearLayout layout = (LinearLayout) view
				.findViewById(R.id.ns_dialog_loading);
		// 图标和文字
		ImageView loadImg = (ImageView) view.findViewById(R.id.iv_loding);
		loadImg.setBackgroundResource(R.drawable.multicolor_progress);
		TextView message = (TextView) view.findViewById(R.id.tv_dialog);
		message.setTextColor(mContext.getResources().getColor(R.color.white));

		// 加载动画
		Animation anim = AnimationUtils.loadAnimation(mContext,
				R.anim.loading_animation);
		loadImg.startAnimation(anim);
		message.setText(msg);

		Dialog loadDialog = new Dialog(mContext, R.style.loading_dialog);
		loadDialog.setCancelable(true);
		loadDialog.setContentView(layout, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT));
		return loadDialog;
	}
	
	
	/**
	 * 用户提醒弹出框
	 * @param context
	 * @param confirmStr 确定按钮文本内容
	 * @param cancelStr  取消按钮文本内容
	 * @param describe   提醒内容
	 * @param dialogType 提醒类型
	 * @param loginDialogListenner
	 */
    public static void remindUserDialog(Context context,String cancelStr ,String confirmStr,String describe,
    		final LoginDialogListenner loginDialogListenner) {
    	final AlertDialog dialog = new AlertDialog.Builder(context).create();
		dialog.show();
		dialog.setCancelable(false);
		dialog.setCanceledOnTouchOutside(false);
		Window window = dialog.getWindow();
		View view = LayoutInflater.from(context).inflate(R.layout.remind_user_pop, null);
		window.setContentView(view);
		window.setGravity(Gravity.CENTER);
		window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
		
//		ImageView remind_user_type_icon = (ImageView) view.findViewById(R.id.remind_user_type_icon);
		TextView tv_remind_user_info = (TextView) view.findViewById(R.id.tv_remind_user_info);
		Button remind_user_bt_01 = (Button) view.findViewById(R.id.remind_user_bt_01);
		Button remind_user_bt_02 = (Button) view.findViewById(R.id.remind_user_bt_02);
		remind_user_bt_01.setText(TextUtils.isEmpty(cancelStr)?"取消":cancelStr);
		remind_user_bt_02.setText(TextUtils.isEmpty(confirmStr)?"确定":confirmStr);
		tv_remind_user_info.setText(describe);
		
		
		remind_user_bt_01.setOnClickListener(new OnClickListener() {
    		
    		@Override
    		public void onClick(View arg0) {
    			dialog.cancel();
    			loginDialogListenner.cancle();
    		}
    	});
		remind_user_bt_02.setOnClickListener(new OnClickListener() {
    		
    		@Override
    		public void onClick(View arg0) {
    			if(dialog != null){
    				dialog.dismiss();
    			}
    			loginDialogListenner.confirm();
    		}
    	});
    }
    
    
    /**
	 * 修改昵称对话框
	 * 
	 * @param mContext
	 * @param title
	 * @param mOnclickListener
	 */
	public static void showInputDialog(Context mContext, String title,String hint,
			final BaseInputDialogOnclickListener mOnclickListener) {

		final AlertDialog dialog = new AlertDialog.Builder(mContext).create();
//		dialog.setView(((Activity) mContext).getLayoutInflater().inflate(
//				R.layout.ns_input_dialog_layout, null));
		dialog.show();
		Window window = dialog.getWindow();
		View view = LayoutInflater.from(mContext).inflate(
				R.layout.ns_input_dialog_layout, null);

		WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
		params.width = (int) (((WindowManager) view.getContext()
				.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay()
				.getWidth() / 10f * 8);
		
		dialog.getWindow().setAttributes(params);
		window.setContentView(view);

		final EditText editText = (EditText) view.findViewById(R.id.eidt);
		if(title.equals("密码验证")){
			editText.setInputType(InputType.TYPE_CLASS_TEXT |InputType.TYPE_TEXT_VARIATION_PASSWORD );
			InputFilter[] filters = {new InputFilter.LengthFilter(9)};
			editText.setFilters(filters);
		}
		editText.setHint(hint);
		editText.setBackgroundResource(R.drawable.shape_base_input_edittext_bg);
//		editText.setPadding(10,8,10,8);
		// editText.setFocusable(true);
		editText.setFocusableInTouchMode(true);
		TextView titleTv = (TextView) view.findViewById(R.id.title);
		titleTv.setText(title);
		TextView cancel = (TextView) view.findViewById(R.id.cancel);
		TextView confirm = (TextView) view.findViewById(R.id.confirm);
		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (dialog != null) {
					mOnclickListener.onCancel();
					dialog.dismiss();
				}
			}
		});

		confirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (dialog != null) {
					String edtString = editText.getText().toString();
					if (!TextUtils.isEmpty(edtString.trim())) {
						mOnclickListener.onConfirm(edtString);
						dialog.dismiss();
					} else {
						mOnclickListener.onConfirm(null);
					}

				}

			}
		});

	}
    
	
	/** 
	 * 用户注册提醒弹出框  (方式一  ) 需要过滤注册条件
	 * @param context
	 * @param loginDialogListenner
	 */
    public static void remindUserRegeist(final Context context,final BaseRegeistOrLogin mBaseRegeistOrLogin) {
    	final AlertDialog dialog = new AlertDialog.Builder(context).create();
		dialog.show();
		dialog.setCancelable(false);
		dialog.setCanceledOnTouchOutside(false);
		Window window = dialog.getWindow();
		View view = LayoutInflater.from(context).inflate(R.layout.remind_regeist_dialog, null);
		window.setContentView(view);
		window.setGravity(Gravity.CENTER);
		window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
		
		TextView tv_remind_user_info = (TextView) view.findViewById(R.id.tv_remind_user_info);
		Button remind_user_bt_01 = (Button) view.findViewById(R.id.regeist_remind_user_bt_01);
		Button remind_user_bt_02 = (Button) view.findViewById(R.id.regeist_remind_user_bt_02);
		
		final EditText account  =  (EditText)view.findViewById(R.id.regeist_edit_account);
		final EditText password  =  (EditText)view.findViewById(R.id.regeist_edit_password);
		
		remind_user_bt_01.setOnClickListener(new OnClickListener() {
    		
    		@Override
    		public void onClick(View arg0){
    			if(dialog != null&&dialog.isShowing()){
    				dialog.dismiss();
    			}
    			dialog.cancel();
    		}
    	});
		remind_user_bt_02.setOnClickListener(new OnClickListener() {
    		
    		@Override
    		public void onClick(View arg0) {
    			 String userName = account.getText().toString().trim();
    			 String passWord =  password.getText().toString().trim();
    			
    			if(!TextUtils.isEmpty(userName)&&userName.getBytes().length>6&&passWord.getBytes().length>=6&&!TextUtils.isEmpty(passWord)){
    				mBaseRegeistOrLogin.regeistUser(userName, passWord);
    				if(dialog != null&&dialog.isShowing()){
        				dialog.dismiss();
        			}
    			}else{
    				if(userName.getBytes().length<6){
    					MyToast.MakeToast(context, "注册账号太短");
    				}else if(passWord.getBytes().length<6){
    					MyToast.MakeToast(context, "注册密码太短");
    				}else{
    					MyToast.MakeToast(context, "注册格式不对");
    				}
    			}
    			
    		}
    	});
    }
	
    
    /**
	 * 用户注册提醒弹出框  (方式二  )  需要过滤注册条件
	 * @param context
	 * @param loginDialogListenner
	 */
    public static void remindUserRegeist_x(final Context context,final UserRegeist mUserRegeist) {
    	final AlertDialog dialog = new AlertDialog.Builder(context).create();
		dialog.show();
		dialog.setCancelable(false);
		dialog.setCanceledOnTouchOutside(false);
		Window window = dialog.getWindow();
		View view = LayoutInflater.from(context).inflate(R.layout.remind_regeist_dialog, null);
		window.setContentView(view);
		window.setGravity(Gravity.CENTER);
		window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
		
		TextView tv_remind_user_info = (TextView) view.findViewById(R.id.tv_remind_user_info);
		Button remind_user_bt_01 = (Button) view.findViewById(R.id.regeist_remind_user_bt_01);
		Button remind_user_bt_02 = (Button) view.findViewById(R.id.regeist_remind_user_bt_02);
		
		final EditText account  =  (EditText)view.findViewById(R.id.regeist_edit_account);
		final EditText password  =  (EditText)view.findViewById(R.id.regeist_edit_password);
		
		remind_user_bt_01.setOnClickListener(new OnClickListener() {
    		
    		@Override
    		public void onClick(View arg0){
    			if(dialog != null&&dialog.isShowing()){
    				dialog.dismiss();
    			}
    			
    			mUserRegeist.onCancel();
    		}
    	});
		remind_user_bt_02.setOnClickListener(new OnClickListener() {
    		
    		@Override
    		public void onClick(View arg0) {
    			 String userName = account.getText().toString().trim();
    			 String passWord =  password.getText().toString().trim();
    			
    			if(!TextUtils.isEmpty(userName)&&userName.getBytes().length>6&&passWord.getBytes().length>=6&&!TextUtils.isEmpty(passWord)){
    				mUserRegeist.onConfirm(userName, passWord);
    				
    				if(dialog != null&&dialog.isShowing()){
        				dialog.dismiss();
        			}
    			}else{
    				if(userName.getBytes().length<6){
    					MyToast.MakeToast(context, "注册账号太短");
    				}else if(passWord.getBytes().length<6){
    					MyToast.MakeToast(context, "注册密码太短");
    				}else{
    					MyToast.MakeToast(context, "注册格式不对");
    				}
    			}
    			
    		}
    	});
    }
    
    

    //================================================================================================
    
	/**
	 * 毫秒值转换为日期
	 * 
	 */

	public static String getDate() {
		// 毫秒转换为日期
		// DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		long now = System.currentTimeMillis();
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(now);
		// System.out.println(now + " = " +
		// formatter.format(calendar.getTime()));
		return formatter.format(calendar.getTime());
	}

	
	/**
	 * 获取当前时间, 格式为: 1990-09-09 09:09:09
	 * @return
	 */
	public static String getCurrentTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date());
	}
	
	/**
	 * return 返回当前时间的 00:00 字符串格式
	 * 
	 * @param second
	 *            传入的秒值
	 * @param remoteViews
	 */
	public static String ShowTime(Long second, RemoteViews remoteViews) {
		long currentTime = second;
		long totalMinutes = currentTime / 60;
		long currentMinute = totalMinutes % 60;
		long totalHour = totalMinutes / 60;
		long currentHour = totalHour % 24;
		if (currentMinute < 10) {
			return ((currentHour + 8) + ":" + "0" + currentMinute);
		}
		return ((currentHour + 8) + ":" + currentMinute);
	}

	
	/**
	 * 不应该被频繁调用，它消耗一定内存
	 * @param hanzi
	 * @return
	 */
	public static String getPinYin(String hanzi){
		String pinyin = "";
		
		//傻逼 -> SHABI shabi 
		HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();//控制转换是否大小写，是否带音标
		format.setCaseType(HanyuPinyinCaseType.UPPERCASE);//大写
		format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		
		//由于不能直接对多个汉字转换，只能对单个汉字转换
		char[] arr = hanzi.toCharArray();
		for (int i = 0; i < arr.length; i++) {
			if(Character.isWhitespace(arr[i]))continue;//如果是空格，则不处理，进行下次遍历
			
			//汉字是2个字节存储，肯定大于127，所以大于127就可以当为汉字转换
			if(arr[i]>127){
				try {
					//由于多音字的存在，单 dan shan
					String[] pinyinArr = PinyinHelper.toHanyuPinyinStringArray(arr[i], format);
					
					if(pinyinArr!=null){
						pinyin += pinyinArr[0];
					}else {
						pinyin += arr[i];
					}
				} catch (BadHanyuPinyinOutputFormatCombination e) {
					e.printStackTrace();
					//不是正确的汉字
					pinyin += arr[i];
				}
			}else {
				//不是汉字，
				pinyin += arr[i];
			}
		}
		
		return pinyin;
	}
	
	// ================================================================================================
	
	/**
	 * 	 跳转进入 In 新的页面动画效果    左出右入 +渐变透明度
	 * @param context 传入的 Context
	 */
	public static void goInActivityAnimation(Context context) {
		if (context != null) {
			((Activity) context).overridePendingTransition(R.anim.anim_in_right, R.anim.anim_out_left);	
		}
	}
	
	/**
	 * 	Activity 跳转进入 In 新的页面动画效果     左出右入 +渐变透明度
	 * @param act 传入的 Activity
	 */
	public static void goInActivityAnimation(Activity act) {
		if (act != null) {
			act.overridePendingTransition(R.anim.anim_in_right, R.anim.anim_out_left);
		}
	}
	
	
	/**
	 * 	 退出 Exit 当前页面动画效果    左入右出+渐变透明度
	 * @param context 传入的Context
	 */
	public static void exitActivityAnimation(Context context) {
		if (context != null) {
			((Activity) context).overridePendingTransition(R.anim.anim_in_left, R.anim.anim_out_right);	
		}
	}
	
	
	/**
	 * 	Activity 退出 Exit 当前页面动画效果     左入右出+渐变透明度
	 * @ act  传入的Activity
	 */
	public static void exitActivityAnimation(Activity act) {
		if (act != null) {
			act.overridePendingTransition(R.anim.anim_in_left, R.anim.anim_out_right);	
		}
	}
	
	
	
	/**
	 * 	 进入 In 当前页面动画效果     缩放扩大进入+渐变透明度
	 * @ context  传入的Context
	 */
	public static void scaleInActivityAnimation(Context context) {
		if (context != null) {
			((Activity) context).overridePendingTransition(R.anim.zoom_in,0);
		}
	}
	
	/**
	 * 	Activity In  缩放扩大进入+渐变透明度
	 * @ act  传入的Activity
	 */
	public static void scaleInActivityAnimation(Activity act) {
		if (act != null) {
			act.overridePendingTransition(R.anim.zoom_in,0);
		}
	}
	
	/**
	 * 	 退出  Exit 当前页面动画效果     缩小退出+渐变透明度
	 * @ context  传入的Context
	 */
	public static void scaleOutActivityAnimation(Context context) {
		if (context != null) {
			((Activity) context).overridePendingTransition(0, R.anim.zoom_out);	
		}
	}
	
	/**
	 * 	Activity 退出 Exit   缩小退出+渐变透明度
	 * @ act  传入的Activity
	 */
	public static void scaleOutActivityAnimation(Activity act) {
		if (act != null) {
			act.overridePendingTransition(0, R.anim.zoom_out);
		}
	}
	
//====================================================================================================
	
	
}
