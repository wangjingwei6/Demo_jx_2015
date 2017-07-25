package com.qnmlgb.util;

import com.ninexiu.wjw.R;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

public class MyToast {
	private static Toast toast;
	private static Toast toast1;
	private static Toast toast2;
	
	private static View view; //弹出吐司提醒的view
	private static SpannableStringBuilder spannableStringBuilder; //字体颜色
	//private static String str="恭喜您!\n获得保时捷911 15天!";
	
	/**
	 *  其他吐丝
	 * @param context
	 * @param message
	 */
	public static void MakeToast(Context context, String message) {
		try {
			if(context!=null){
				LayoutInflater inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View view = inflate.inflate(R.layout.custom_toast, null);
				if(toast==null){
					toast = new Toast(context);
					toast.setView(view);
					toast.setText(message);
					toast.setDuration(Toast.LENGTH_SHORT);
				}else{
					toast.setView(view);
					toast.setText(message);
				}
				toast.show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 		签到涉及的吐丝
	 * @param context
	 * @param message
	 */
	public static void MakeToastSign(Context context, String message) {
		try {
			if(context!=null){
				LayoutInflater inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				view = inflate.inflate(R.layout.ns_sign_toast, null);
			//	view.setPadding(100, 75, 100, 75);
				if(toast1==null){
					toast1 = new Toast(context);
					toast1.setGravity(Gravity.CENTER, 0, 0);
					toast1.setView(view);
					toast1.setText(message);
					toast1.setDuration(Toast.LENGTH_SHORT);
				}else{
					toast1.setView(view);
					toast1.setText(message);
				}
				toast1.show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 *  签到涉及的吐丝
	 * @param context
	 * @param message
	 */
	public static void MakeToastSignLong(Context context, String message) {
		try {
			// MyToast.MakeToastSign(SignActivity.this,"签到成功!");
			int end = message.indexOf("天");
			int start=message.indexOf("911")+"911".length()+1;
		
			spannableStringBuilder=new SpannableStringBuilder(message);
			spannableStringBuilder.setSpan(new ForegroundColorSpan(Color.argb(255,255,6,127)), start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
			spannableStringBuilder.setSpan(new RelativeSizeSpan(0.8f), 3, 13, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
			if(context!=null){
				LayoutInflater inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View view = inflate.inflate(R.layout.ns_sign_toast, null);
				view.setPadding(100, 75, 100, 75);
				if(toast==null){
					toast2 = new Toast(context);
					toast2.setGravity(Gravity.CENTER, 0, 0);
					toast2.setView(view);
					toast2.setText(spannableStringBuilder);
					toast2.setDuration(Toast.LENGTH_SHORT);
				}else{
					toast2.setView(view);
					toast2.setText(spannableStringBuilder);
				}
				toast2.show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	

}