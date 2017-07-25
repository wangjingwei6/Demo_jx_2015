package com.qnmlgb.activity;

import java.util.Arrays;
import java.util.LinkedList;

import android.app.ListActivity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.ninexiu.wjw.R;

public class PullToRefreshListViewActivity extends ListActivity {
	
	private PullToRefreshListView mPullToRefreshListView;
	private LinkedList<String> mItemList;
	private ArrayAdapter<String> adapter;
	private String[] data = new String[] { "经纬1", "经纬2", "经纬3", "经纬4",
			"经纬5", "经纬6", "经纬7", "经纬8", "经纬9", "经纬10", "经纬11",
			"经纬12" };
	private boolean isRefreshing = false;
	private Context context;
	private int mItemCount=0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.pulltorefresh);
		setProgressBarIndeterminateVisibility(true);

		context = this;
		
		// 初始化控件
		mPullToRefreshListView = (PullToRefreshListView) findViewById(R.id.pull_refresh_list);
		mPullToRefreshListView.setMode(Mode.BOTH);
		
//		ILoadingLayout loadingLayoutProxy = mPullToRefreshListView.getLoadingLayoutProxy();
//		loadingLayoutProxy.setPullLabel("拉吧拉吧");
//		loadingLayoutProxy.setRefreshingLabel("刷新完成..");
//		loadingLayoutProxy.setReleaseLabel("放开准备刷新咯..。。");
//		loadingLayoutProxy.setLastUpdatedLabel("最后更新时间:" + str);
		
		
		mItemList = new LinkedList<String>();
		mItemList.addAll(Arrays.asList(data));
		
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, mItemList);
		// 设置pull-to-refresh模式为Mode.Both
		
		mPullToRefreshListView.setAdapter(adapter);
//		mPullToRefreshListView.setMode(Mode.BOTH);

		// 设置上拉下拉事件
		mPullToRefreshListView
				.setOnRefreshListener(new OnRefreshListener<ListView>() {
					@Override
					public void onRefresh(
							PullToRefreshBase<ListView> refreshView) {
						 //设置下拉时显示的日期和时间
						String str = DateUtils.formatDateTime
								(PullToRefreshListViewActivity.this, System.currentTimeMillis(), 
					DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
						
							if (refreshView.isHeaderShown()) {
								
								mPullToRefreshListView.getLoadingLayoutProxy().setRefreshingLabel("正在刷新");
								mPullToRefreshListView.getLoadingLayoutProxy().setPullLabel("下拉刷新");
								mPullToRefreshListView.getLoadingLayoutProxy().setReleaseLabel("释放开始刷新");
								mPullToRefreshListView.getLoadingLayoutProxy().setLastUpdatedLabel("最后更新时间:" + str);
								// 模拟加载任务
								new GetDataTask(1).execute();
								// 下拉刷新 业务代码
							} else if (refreshView.isFooterShown()) {
								// 上拉加载更多 业务代码
								
								mPullToRefreshListView.getLoadingLayoutProxy().setRefreshingLabel("正在加载");
								mPullToRefreshListView.getLoadingLayoutProxy().setPullLabel("上拉加载更多");
								mPullToRefreshListView.getLoadingLayoutProxy().setReleaseLabel("释放开始加载");
								mPullToRefreshListView.getLoadingLayoutProxy().setLastUpdatedLabel("最后加载时间:" + str);

								// 模拟加载任务
								new GetDataTask(2).execute();
								
							} 
						}
				});


	}
	

	private class GetDataTask extends AsyncTask<Void, Void, String> {
		
		private int type;
		public GetDataTask(int num){
			this.type = num;
		}

		@Override
		protected String doInBackground(Void... params) {
			 try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "love to be 王丽欣"+"_"+(++mItemCount);
		}

		protected void onPostExecute(String result) {
//			if(mItemList!=null){
//				mItemList.removeAll(Arrays.asList(data));
//			}
			
			if(type==1){
				//下拉刷新
				mItemList.add(0,result);
			}else if(type ==2){
				//上啦加载
				mItemList.addLast(result);
			}
//			mItemList.addAll(Arrays.asList(data));
			adapter.notifyDataSetChanged();
			// Call onRefreshComplete when the list has been refreshed.
			mPullToRefreshListView.onRefreshComplete();
		}
	}

}
