package com.qnmlgb.activity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ninexiu.wjw.R;
import com.qnmlgb.adapter.RecycleStaggeredAdapter;
import com.qnmlgb.adapter.RecycleStaggeredAdapter.OnItemClickLitener;
//import com.qnmlgb.adapter.RecycleGridAdapter.OnRecyclerViewItemClickListener;
import com.qnmlgb.application.SysApplication;
import com.qnmlgb.bean.GetAllTypeData;
import com.qnmlgb.bean.RecycleViewBean;
import com.qnmlgb.util.Utils;
import com.qnmlgb.view.CustomRecycleDecoration;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class RecycleViewActivity extends Activity {

	private RecyclerView recycle;
	private List<RecycleViewBean> data;
	private int column = 2;
	private RecycleStaggeredAdapter mGridAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recycle);
		setTheme(R.style.AnimationTheme);
		// setContentView(R.layout.recycle);
		android.app.ActionBar ac = getActionBar();
		ac.show();

		column = (int) (Math.random() * 4 + 1);
		initView();
	}

	private void initView() {

		// LinearLayoutManager（垂直布局、水平布局）、GridLayoutManager（网格布局）、StaggeredGridLayoutManager（瀑布流布局）；

		recycle = (RecyclerView) findViewById(R.id.my_recycler_view);

		// 设置adapter
		initData();
		mGridAdapter = new RecycleStaggeredAdapter(data, column);
		recycle.setAdapter(mGridAdapter);
		recycle.setHasFixedSize(true);
		// 设置item动画
		recycle.setItemAnimator(new DefaultItemAnimator());
		// 设置item之间的间隔
		CustomRecycleDecoration decoration = new CustomRecycleDecoration(16);
		recycle.addItemDecoration(decoration);

		// 设置layoutManager
		// 这里设置瀑布流 效果 第一个参数多少列 第二个参数垂直方向
		if (column != 3) {
			if(column==4){
				recycle.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
			}else{
				recycle.setLayoutManager(new StaggeredGridLayoutManager(column, StaggeredGridLayoutManager.VERTICAL));
			}
		} else {
			recycle.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
			// recycle.setLayoutManager(new LinearLayoutManager(this,
			// LinearLayoutManager.HORIZONTAL, true));
		}

		mGridAdapter.setOnItemClickLitener(new OnItemClickLitener() {

//			private int i;
//			private int num;

			@Override
			public void onItemClick(View view, int position) {
				Intent intent = new Intent(RecycleViewActivity.this, PersonPhotoActivity.class);
				intent.putExtra("type", "gridview");
				intent.putExtra("photoId",data.get(position).getImageView());
				startActivity(intent);
				RecycleViewActivity.this.overridePendingTransition(R.anim.zoom_in, 0);// 切换Activity的过渡动画
			}

			@Override
			public void onItemLongClick(View view, int position) {
				deleteData(position);
			}
		});

	}

	private void initData() {
		data = new ArrayList<RecycleViewBean>();
		// data.add(new
		// RecycleViewBean(GetAllTypeData.getImageViewId(0),"第一只狗"));
		// data.add(new
		// RecycleViewBean(GetAllTypeData.getImageViewId(1),"第二只狗"));
		// data.add(new
		// RecycleViewBean(GetAllTypeData.getImageViewId(2),"第三只狗"));
		// data.add(new
		// RecycleViewBean(GetAllTypeData.getImageViewId(3),"第四只狗"));
		// data.add(new
		// RecycleViewBean(GetAllTypeData.getImageViewId(4),"第五只狗"));
		// data.add(new
		// RecycleViewBean(GetAllTypeData.getImageViewId(5),"第六只狗"));
		// data.add(new
		// RecycleViewBean(GetAllTypeData.getImageViewId(6),"第七只狗"));

		data.add(new RecycleViewBean(GetAllTypeData.getImageViewId(25), "嘟嘟"));
		data.add(new RecycleViewBean(GetAllTypeData.getImageViewId(19), "第三朵花"));
		data.add(new RecycleViewBean(GetAllTypeData.getImageViewId(20), "第四朵花"));
		data.add(new RecycleViewBean(GetAllTypeData.getImageViewId(10), "第十一只狗"));
		data.add(new RecycleViewBean(GetAllTypeData.getImageViewId(11), "第十二只狗"));
		data.add(new RecycleViewBean(GetAllTypeData.getImageViewId(26), "嘟嘟"));
		data.add(new RecycleViewBean(GetAllTypeData.getImageViewId(21), "第五朵花"));
		data.add(new RecycleViewBean(GetAllTypeData.getImageViewId(22), "第六朵花"));
		data.add(new RecycleViewBean(GetAllTypeData.getImageViewId(3), "嘟嘟"));
		data.add(new RecycleViewBean(GetAllTypeData.getImageViewId(11), "第十八只狗"));
		data.add(new RecycleViewBean(GetAllTypeData.getImageViewId(23), "第七朵花"));
		data.add(new RecycleViewBean(GetAllTypeData.getImageViewId(17), "第十三只狗"));
		data.add(new RecycleViewBean(GetAllTypeData.getImageViewId(1), "嘟嘟"));
		data.add(new RecycleViewBean(GetAllTypeData.getImageViewId(14), "第十五只狗"));
		data.add(new RecycleViewBean(GetAllTypeData.getImageViewId(24), "第八朵花"));
		data.add(new RecycleViewBean(GetAllTypeData.getImageViewId(5), "第六只狗"));
		data.add(new RecycleViewBean(GetAllTypeData.getImageViewId(15), "第七只狗"));
		data.add(new RecycleViewBean(GetAllTypeData.getImageViewId(27), "嘟嘟"));
		data.add(new RecycleViewBean(GetAllTypeData.getImageViewId(7), "第八只狗"));
		data.add(new RecycleViewBean(GetAllTypeData.getImageViewId(16), "第九只狗"));
		data.add(new RecycleViewBean(GetAllTypeData.getImageViewId(9), "第十只狗"));
		data.add(new RecycleViewBean(GetAllTypeData.getImageViewId(2), "嘟嘟"));

	}

	public void addData(int position) {
		mGridAdapter.notifyItemInserted(position);
		data.add(position, new RecycleViewBean(GetAllTypeData.getImageViewId(24), "新花朵"));
	}
	
	public void deleteData(int position){
		mGridAdapter.notifyItemRemoved(position);
		data.remove(position);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_add:
			addData(1);
			return true;
		case R.id.action_delete:
			deleteData(1);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	// protected void setContentView() {
	// setContentView(R.layout.recycle);
	// }

}
