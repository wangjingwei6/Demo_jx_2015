package com.qnmlgb.bean;

import com.qnmlgb.util.Utils;

public class QuickData implements Comparable<QuickData>{
	private String name;
	private String pinyin;
	
	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	public QuickData(String name) {
		super();
		this.setName(name);
		pinyin = Utils.getPinYin(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int compareTo(QuickData another) {
		return pinyin.compareTo(another.getPinyin());
	}
	
}
