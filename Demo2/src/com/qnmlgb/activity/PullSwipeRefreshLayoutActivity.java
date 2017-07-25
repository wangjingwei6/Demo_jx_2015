package com.qnmlgb.activity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ninexiu.wjw.R;
import com.qnmlgb.view.MyCustomSwipeRefreshLayout;
import com.qnmlgb.view.MyCustomSwipeRefreshLayout.OnLoadListener;


public class PullSwipeRefreshLayoutActivity extends BaseActivity{
	
	private MyCustomSwipeRefreshLayout mSwipeRefreshLayout;
	private ListView listView;
	private ArrayAdapter mAdapter;
	private List<String> datas = new ArrayList<String>(Arrays.asList("汪经纬","吴天庭","王丽欣","陆依娜","刘仁涛"));

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mSwipeRefreshLayout = (MyCustomSwipeRefreshLayout) findViewById(R.id.id_swipe_ly);
		mSwipeRefreshLayout.setColorSchemeResources(R.color.blue,R.color.yellow,R.color.hong,R.color.green);
//		mSwipeRefreshLayout.setColorSchemeResources(R.color.hong);
		listView = (ListView)findViewById(R.id.id_listview);
		
		mSwipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				mSwipeRefreshLayout.postDelayed(new Runnable() {
					
					@Override
					public void run() {
						mSwipeRefreshLayout.setRefreshing(false);
						datas.addAll(0,Arrays.asList("新增的 A", "新增的 B", "新增的 C"));
						mAdapter.notifyDataSetChanged();
					}
				}, 3000);
			}
		});
		mSwipeRefreshLayout.setOnLoadListener(new OnLoadListener() {
			
			@Override
			public void onLoad() {
				mSwipeRefreshLayout.postDelayed(new Runnable() {
					
					@Override
					public void run() {
						mSwipeRefreshLayout.setLoading(false);
						datas.addAll(Arrays.asList("AAA ", "BBB", "CCC"));
						mAdapter.notifyDataSetChanged();
					}
				}, 2000); 
 			}
		});
//		mSwipeRefreshLayout.setColorSchemeResources(R.color.blue,R.color.yellow,R.color.hong,R.color.green);
		
		mAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, datas);
		
		listView.setAdapter(mAdapter);
	}
	
	
	
	@Override
	protected void setContentView() {
		setContentView(R.layout.pull_to_refresh_swiperefreshlayout);
	}

}
