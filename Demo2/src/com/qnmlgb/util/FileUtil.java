package com.qnmlgb.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

public class FileUtil {
	
	public static final String SAVE_USER_SPNAME = "userInfo";
	
	public static final String SAVE_FILE_PATH= 
			Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED)
			? Environment.getExternalStorageDirectory().getAbsolutePath(): "/mnt/sdcard";//保存到SD卡
	
	public static final String SAVE_REAL_PATH = SAVE_FILE_PATH + "/wjwPhoto";//保存的确切位置
	public static final String UPDATE_APK_URL = SAVE_FILE_PATH+"/newApk.apk"; //更新的Apk路径
	
	
	public static String mEditFileName = "/editSave.jpg"; //编辑资料保存的图片
	public static String mFileName = "/save.jpg"; //个人图片保存的文件名
	
	public static void saveFile(Bitmap bm, String fileName) throws IOException {
    	File foder = new File(SAVE_REAL_PATH);
    	if (!foder.exists()) {
    	foder.mkdirs();
    	}
    	File myTargetFile = new File(SAVE_REAL_PATH, fileName);
    	if (!myTargetFile.exists()) {
    		myTargetFile.createNewFile();
    	}
    	FileOutputStream fOut =  new FileOutputStream(myTargetFile);  
    	bm.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
    	fOut.flush();
    	fOut.close();
    	}
	
	
//	public static final String FILE_PATH = Environment.getExternalStorageDirectory().getPath() + "/wjwPhoto";
	// 从文件里取
	public static Bitmap getBitmap(String fileName) {
		File imagePic;
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
				imagePic = new File(SAVE_REAL_PATH + fileName);
				if (imagePic.exists()) {
					return BitmapFactory.decodeFile(imagePic.getAbsolutePath());
			}
		}
		return null;
	}
	
	
}
