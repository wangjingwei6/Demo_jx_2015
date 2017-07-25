package com.qnmlgb.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetUtils {
	
	public static enum NetType {
		MOBILE, WIFI, NONET
	}

	/**
	 * 获取网络是否可用
	 * 
	 * @author gengsy
	 * @param context
	 * @return
	 */
	public static boolean hasNetwork(Context context) {
		ConnectivityManager con = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo workinfo = con.getActiveNetworkInfo();
		if (workinfo == null || !workinfo.isAvailable()) {
			return false;
		}
		return true;
	}
	
	/**
	 * 获取网络类型
	 * 
	 * @author gengsy
	 * @param context
	 * @return
	 */
	public static NetType getCurrentNetType(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE); 
        if (cm != null) {
        	NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
        	if (activeNetworkInfo != null) {
        		if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI ) {
            		return NetType.WIFI;
    			}else if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
    				return NetType.MOBILE;
    			}
			}
		}
        
        return NetType.NONET;
	}

}
