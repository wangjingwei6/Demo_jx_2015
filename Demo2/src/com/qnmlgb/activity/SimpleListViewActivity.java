package com.qnmlgb.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ninexiu.wjw.R;
import com.qnmlgb.adapter.SimpleLoadListAdapter;
import com.qnmlgb.view.LoadListView;
import com.qnmlgb.view.LoadListView.ILoadListener;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.widget.ArrayAdapter;

public class SimpleListViewActivity extends Activity implements ILoadListener {

	private LoadListView listView;
	private ArrayList<String> list;

	List<Map<String, Object>> allList = new ArrayList<Map<String, Object>>();
	private SimpleLoadListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setTheme(R.style.AnimationTheme);
		setContentView(R.layout.list_simple);
		// SystemBarTintManager tintManager = new SystemBarTintManager(this);
		// tintManager.setStatusBarTintEnabled(true);
		// tintManager.setTintColor(getResources().getColor(R.color.baise));

		// adapter = SimpleAdapterForData();
		getFirstData();
		showListView(allList);
	}

	private void showListView(List<Map<String, Object>> allList) {
		if (adapter == null) {
			listView = (LoadListView) findViewById(R.id.mylistview);
			listView.setInterface(this);
			adapter = new SimpleLoadListAdapter(this, allList);
			listView.setAdapter(adapter);
		} else {
			adapter.onDateChange(allList);
		}
	}

	private void getFirstData() {
		allList = getSimpleData();
	}

	// private SimpleAdapter SimpleAdapterForData() {
	// SimpleAdapter adapter = new SimpleAdapter(SimpleListViewActivity.this,
	// allList, R.layout.list_simple_item,new String[]{"name","desc","img"},new
	// int[]{R.id.name,R.id.desc,R.id.cicularimage});
	// return adapter;
	// }

	private List<Map<String, Object>> getSimpleData() {
		Map map1 = new HashMap<String, Object>();
		map1.put("name", "陆依娜");
		map1.put("desc", "美女");
		map1.put("img", R.drawable.lyn_xx);

		Map map2 = new HashMap<String, Object>();
		map2.put("name", "林贞恩");
		map2.put("desc", "美女");
		map2.put("img", R.drawable.lzn);

		Map map3 = new HashMap<String, Object>();
		map3.put("name", "汪经纬");
		map3.put("desc", "帅哥");
		map3.put("img", R.drawable.wjw);

		Map map4 = new HashMap<String, Object>();
		map4.put("name", "李明明");
		map4.put("desc", "帅哥");
		map4.put("img", R.drawable.lmm);

		Map map5 = new HashMap<String, Object>();
		map5.put("name", "吴天庭");
		map5.put("desc", "帅哥");
		map5.put("img", R.drawable.wtt);

		Map map6 = new HashMap<String, Object>();
		map6.put("name", "黄家驹");
		map6.put("desc", "帅哥");
		map6.put("img", R.drawable.hjj);

		allList.add(map1);
		allList.add(map2);
		allList.add(map3);
		allList.add(map4);
		allList.add(map5);
		allList.add(map6);

		return allList;
	}

	private List<Map<String, Object>> getLoadData() {
		for (int i = 0; i < 2; i++) {
			Map map = new HashMap<String, Object>();
			map.put("name", "加载出来的新数据" + i);
			map.put("desc", "帅哥");
			map.put("img", R.drawable.wtt);
			allList.add(map);

		}

		return allList;
	}

	private ArrayAdapter<String> ArrayAdapetForData() {
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.listview_item, getArrayData());
		return adapter;
	}

	private ArrayList<String> getArrayData() {
		list = new ArrayList<String>();
		int i;
		for (i = 0; i < 20; i++) {
			list.add("王尼玛" + i);
		}
		return list;
	}

	@Override
	public void finish() {
		super.finish();
		// overridePendingTransition(R.anim.set_in_left,R.anim.set_out_right);
	}

	/**
	 * 加载数据回掉接口
	 */
	@Override
	public void onLoad() {
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				// 获取更多数据
				getLoadData();
				// 更新listview显示；
				showListView(allList);
				// 通知listview加载完毕
				listView.loadComplete();
			}
		}, 2000);
	}
}
