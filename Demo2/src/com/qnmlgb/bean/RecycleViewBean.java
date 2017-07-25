package com.qnmlgb.bean;

public class RecycleViewBean {

	private int imageView;
	private String title;

	public RecycleViewBean(int img, String title) {
		this.imageView = img;
		this.title = title;
	}

	public void setImageView(int imageView) {
		this.imageView = imageView;
	}

	public int getImageView() {
		return imageView;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

}
