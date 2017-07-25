package com.qnmlgb.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ninexiu.wjw.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;


public class ListViewActivity extends Activity {
	
	private ListView listView;
	private ArrayList<String> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setTheme(R.style.AnimationTheme);
		setContentView(R.layout.list);
//		  SystemBarTintManager tintManager = new SystemBarTintManager(this);  
//		  tintManager.setStatusBarTintEnabled(true);
//		  tintManager.setTintColor(getResources().getColor(R.color.baise));
		  
		  
		listView = (ListView)findViewById(R.id.mylistview);
		
//		ArrayAdapter<String> adapter = ArrayAdapetForData();
		
		SimpleAdapter adapter = SimpleAdapterForData();
		
		listView.setAdapter(adapter);
	}

	private SimpleAdapter SimpleAdapterForData() {
		SimpleAdapter adapter = new SimpleAdapter(ListViewActivity.this, getSimpleData(), R.layout.list_swipe_person_item,new String[]{"name","desc","img"},new int[]{R.id.name,R.id.desc,R.id.cicularimage});
		return  adapter;
	}

	private List<Map<String,Object>> getSimpleData() {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>(); 
		Map map1 = new HashMap<String, Object>();
		map1.put("name","陆依娜");
		map1.put("desc", "美女");
		map1.put("img", R.drawable.lyn_xx);
		
		Map map2 = new HashMap<String, Object>();
		map2.put("name","林贞恩");
		map2.put("desc", "美女");
		map2.put("img", R.drawable.lzn);
		
		Map map3 = new HashMap<String, Object>();
		map3.put("name","汪经纬");
		map3.put("desc", "帅哥");
		map3.put("img", R.drawable.wjw);
		
		Map map4 = new HashMap<String, Object>();
		map4.put("name","李明明");
		map4.put("desc", "帅哥");
		map4.put("img", R.drawable.lmm);
		
		Map map5 = new HashMap<String, Object>();
		map5.put("name","吴天庭");
		map5.put("desc", "帅哥");
		map5.put("img", R.drawable.wtt);
		
		Map map6 = new HashMap<String, Object>();
		map6.put("name","黄家驹");
		map6.put("desc", "帅哥");
		map6.put("img", R.drawable.hjj);
		
		list.add(map1);
		list.add(map2);
		list.add(map3);
		list.add(map4);
		list.add(map5);
		list.add(map6);
		
		return list;
	}

	private ArrayAdapter<String> ArrayAdapetForData() {
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.listview_item, getArrayData()); 
		return adapter;
	}

	private ArrayList<String> getArrayData() {
		list = new ArrayList<String>();
		int i;
		for(i =0;i<20;i++){
			list.add("王尼玛"+i);
		}
		return list;
	}
	@Override
	public void finish() {
		super.finish();
//		overridePendingTransition(R.anim.set_in_left,R.anim.set_out_right);
	}
}
