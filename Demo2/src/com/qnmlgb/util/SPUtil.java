package com.qnmlgb.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import java.util.Map;

public class SPUtil{
	private SharedPreferences sharedPreferences;
	private Context context;
	private String preFileName;
	private Editor edit;

	public SPUtil(Context context, String sharePreFileName){
		this.context = context;
		this.preFileName = sharePreFileName;
		refresh();
		getEditor();
	}
	
	@SuppressLint("CommitPrefEdits")
	public Editor getEditor(){
		edit = sharedPreferences.edit();
		return edit;
	}

	@SuppressWarnings("static-access")
	public void refresh(){
		  sharedPreferences = context.getSharedPreferences(preFileName, context.MODE_PRIVATE);
	}
	
	public String getStringValue(String key, String defValue){
		return sharedPreferences.getString(key, defValue);
	}

	public boolean getBooleanValue(String key, boolean defValue){
		return sharedPreferences.getBoolean(key, defValue);
	}

	public float getFloatValue(String key, float defValue){
		return sharedPreferences.getFloat(key, defValue);
	}

	public int getIntValue(String key, int defValue){
		return sharedPreferences.getInt(key, defValue);
	}

	public long getLongValue(String key, long defValue){
		return sharedPreferences.getLong(key, defValue);
	}

	public boolean writeBooleanValue(String key, boolean value){
		return sharedPreferences.edit().putBoolean(key, value).commit();
	}

	public boolean writeStringValue(String key, String value){
		return sharedPreferences.edit().putString(key, value).commit();
	}
	
	public boolean writeStringValueSimple(String key, String value){
		return edit.putString(key, value).commit();
	}

	public boolean writeFloatValue(String key, float value){
		return sharedPreferences.edit().putFloat(key, value).commit();
	}

	public boolean writeLongValue(String key, long value){
		return sharedPreferences.edit().putLong(key, value).commit();
	}

	public boolean writeIntValue(String key, int value){
		return sharedPreferences.edit().putInt(key, value).commit();
	}
	

	@SuppressWarnings("rawtypes")
	public Map getAll(){
		return sharedPreferences.getAll();
	}

	public boolean contains(String key){
		return sharedPreferences.contains(key);
	}

	public boolean delete(String key){
		return sharedPreferences.edit().remove(key).commit();
	}
	
	public boolean removeAll() {
		return sharedPreferences.edit().clear().commit();
	}
	

}
