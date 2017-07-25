package com.qnmlgb.adapter;

import java.util.List;

import com.ninexiu.wjw.R;
import com.qnmlgb.bean.RecycleViewBean;
import com.qnmlgb.util.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

//================设置Grid适配器   瀑布流的效果

public class RecycleStaggeredAdapter extends RecyclerView.Adapter<RecycleStaggeredAdapter.GridItemViewHolder> {

	private List<RecycleViewBean> recycleViewBean;
	private int column;
	private Context context;
	
	public interface OnItemClickLitener {
		void onItemClick(View view, int position);

		void onItemLongClick(View view,int position);
	}

	public OnItemClickLitener mOnItemClickLitener;

	public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
		this.mOnItemClickLitener = mOnItemClickLitener;
	}

	public RecycleStaggeredAdapter(List<RecycleViewBean> bean, int arg) {
		this.recycleViewBean = bean;
		this.column = arg;
	}

	@Override
	public int getItemCount() {
		return recycleViewBean.size();
	}

	public void onBindViewHolder(final GridItemViewHolder staggeredHolder,  int position) {
		
		setUpItemClick(staggeredHolder);
		
		Bitmap readBitmap = null;
		if (column == 1) {
//			readBitmap = Utils.readBitmap(context, recycleViewBean
//					.get(position).getImageView());
//			gridholder.imageView.setImageBitmap(readBitmap);
			staggeredHolder.imageView.setImageResource(recycleViewBean.get(position).getImageView());
			
		} else if (column == 2||column==4) {
			readBitmap = Utils.readBitmap(context, recycleViewBean
					.get(position).getImageView(), 3);
			staggeredHolder.imageView.setImageBitmap(readBitmap);
		} else if (column == 3) {
			readBitmap = Utils.readBitmap(context, recycleViewBean
					.get(position).getImageView());
			staggeredHolder.imageView.setImageDrawable(Utils.resizeImage(readBitmap,
					540, 300));
		}

		// 将数据保存在itemView的Tag中，以便点击时进行获取
				//gridholder.itemView.setTag(R.id.tag_item,recycleViewBean.get(position).getImageView());
				//gridholder.itemView.setTag(R.id.tag_longitem,position);
		
		staggeredHolder.textView.setText(recycleViewBean.get(position).getTitle());
		// gridholder.imageView.setImageResource(recycleViewBean.get(position).getImageView());;
	}

	public GridItemViewHolder onCreateViewHolder(ViewGroup viewgroup, int arg1) {
		this.context = viewgroup.getContext();
		// View view =
		// LayoutInflater.from(viewgroup.getContext()).inflate(R.layout.recycle_item,null);
		View view = LayoutInflater.from(viewgroup.getContext()).inflate(
				R.layout.recycle_item, viewgroup, false);
		GridItemViewHolder mGridItemViewHolder = new GridItemViewHolder(view);
		return mGridItemViewHolder;
	}

	@SuppressWarnings("rawtypes")
	public class GridItemViewHolder extends RecyclerView.ViewHolder {
		private ImageView imageView;
		private TextView textView;

		public GridItemViewHolder(View itemView) {
			super(itemView);
			imageView = (ImageView) itemView
					.findViewById(R.id.recycle_item_img);
			textView = (TextView) itemView
					.findViewById(R.id.recycle_item_title);

		}

	}

	protected void setUpItemClick(final GridItemViewHolder staggeredHolder) {
		if(mOnItemClickLitener!=null){
			staggeredHolder.itemView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					int layoutPosition = staggeredHolder.getPosition();
					mOnItemClickLitener.onItemClick(staggeredHolder.itemView, layoutPosition);
				}
			});
			
			staggeredHolder.itemView.setOnLongClickListener(new OnLongClickListener() {
				
				@Override
				public boolean onLongClick(View v) {
					int layoutPosition = staggeredHolder.getPosition();
					mOnItemClickLitener.onItemLongClick(staggeredHolder.itemView, layoutPosition);
					return false;
				}
			});
		}
	}
	
	
//	@Override
//	public void onClick(View v) {
//		if (mOnItemClickLitener != null) {
//			// 注意这里使用getTag方法获取数据                     	mOnItemClickLitener.onItemClick(v, (Integer) v.getTag(R.id.tag_item));
//			int layoutPosition = staggeredHolder.get
//		
//		}
//	}
//
//	@Override
//	public boolean onLongClick(View v) {
//		if (mOnItemClickLitener != null) {
//			mOnItemClickLitener.onItemLongClick(v,
//					(Integer)v.getTag(R.id.tag_longitem));
//		}
//		return true;
//	}

//	public void setRecycleViewItemClickListener(
//			OnRecyclerViewItemClickListener listener) {
//		this.mOnRecycleViewItemClickListener = listener;
//	}

}
