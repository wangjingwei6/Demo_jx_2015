package com.qnmlgb.activity;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ninexiu.wjw.R;
import com.qnmlgb.application.SysApplication;
import com.qnmlgb.util.FileUtil;
import com.qnmlgb.util.MyToast;
import com.qnmlgb.util.SPUtil;
import com.qnmlgb.util.Utils;
import com.qnmlgb.util.Utils.BaseInputDialogOnclickListener;
import com.qnmlgb.view.CircularImageView;

public class EditMainUserIconActivity extends BaseActivity implements
		OnClickListener {

	private CircularImageView userIcon;
	private EditText tvUserName;
	private TextView tvUserSex;
	private TextView tvUserAddress;
	private Button confim;
	private Dialog saveDialog;

	private String defaultUrl = "http://p.3761.com/pic/84201393378242.jpg";
	private String[] sexArray; // 男女
	private String[] addressArray; //地址
	private String[] photoTypeArray; //相册
	
	private boolean isModifyUserName = false;
	private boolean isUpdateContent = false;
	private SPUtil mSpUtil;
	private Editor editor;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mSpUtil = new SPUtil(this,"userInfo");
		initView();
		initData();
		initOnClick();
	}

	private void initView() {
		userIcon = (CircularImageView) findViewById(R.id.iv_user_head);
		tvUserName = (EditText) findViewById(R.id.et_nick_name);
		tvUserSex = (TextView) findViewById(R.id.tv_sex);
		tvUserAddress = (TextView) findViewById(R.id.tv_address);
		confim = (Button)findViewById(R.id.bt_modify_userinfo);
		
	}

	private void initOnClick() {
		userIcon.setOnClickListener(this);
//		findViewById(R.id.layout_nickname).setOnClickListener(this);
		tvUserName.setOnClickListener(this);
		findViewById(R.id.layout_sex).setOnClickListener(this);
		findViewById(R.id.layout_address).setOnClickListener(this);
		confim.setOnClickListener(this);
	}

	private void initData() {

//		displayImage(userIcon, defaultUrl);
		//1.从保存的图片文件里取
		Bitmap userHead = FileUtil.getBitmap(FileUtil.mEditFileName);
		if(userHead!=null&&!userHead.equals("")){
			userIcon.setImageBitmap(userHead);
		}
		
		String sexColor = mSpUtil.getStringValue("userColor", "");
		if(sexColor.equals("blue")){
			tvUserName.setTextColor(getResources().getColor(R.color.blue)); //男士的颜色
			tvUserSex.setTextColor(getResources().getColor(R.color.blue));
		}else if(sexColor.equals("red")){
			tvUserName.setTextColor(getResources().getColor(R.color.hong_girl)); //女士颜色
			tvUserSex.setTextColor(getResources().getColor(R.color.hong_girl));
		}else{}
		
		
		String userName = mSpUtil.getStringValue("userName", "未填写");
		if(!userName.toString().trim().equals("")&& !userName.equals("未填写")) {
			tvUserName.setText(userName);
		}

		String userSex = mSpUtil.getStringValue("userSex", "未填写");
		if(!userSex.equals("未填写")){
			tvUserSex.setText(userSex);
		}

		String userAddress = mSpUtil.getStringValue("userAddress", "未填写");
		tvUserAddress.setText(userAddress);
		if(!userAddress.equals("未填写")){
			tvUserAddress.setTextColor(getResources().getColor(R.color.blue));
		}

		TextView title = (TextView) findViewById(R.id.title);
		title.setText("编辑资料");

		sexArray = getResources().getStringArray(R.array.sex_select);
		addressArray = getResources().getStringArray(R.array.address_select);
		photoTypeArray  =  getResources().getStringArray(R.array.photoType_select);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.iv_user_head:
			
			updateUserIcon();
			break;
		case R.id.et_nick_name:
			
			showEditNickNameDialog();
			break;
		case R.id.layout_sex:
			
			showInfoItemDialog("选择性别","sex");
			break;
		case R.id.layout_address:
			
			showInfoItemDialog("选择城市","address");
			break;
		case R.id.bt_modify_userinfo:
			saveUserInfo();
			break;
		default:
			break;
		}

	}


	/**
	 * 上传头像提示选择对话框
	 */
	private void updateUserIcon() {
		
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			final AlertDialog dialog = builder.create();
			dialog.show();
			Window window = dialog.getWindow();
			View contentView = getLayoutInflater().inflate(R.layout.item_textview_poplayout, null);
			window.setContentView(contentView);
			TextView textView = (TextView) contentView.findViewById(R.id.vipName);
			textView.setText("上传头像");
			ListView listView = (ListView) contentView.findViewById(R.id.sex_pop_listview);
			listView.setVisibility(View.VISIBLE);
			PopAdapter adapter = new PopAdapter(this, photoTypeArray);
			listView.setAdapter(adapter);
			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					if (arg2 == 0) {
						if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
							takePhoto();
						} else {
							MyToast.MakeToast(EditMainUserIconActivity.this, "未检测到sd卡");
							return;
						}

					} else if (arg2 == 1) {
						if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
							invokePhoto();
						} else {
							MyToast.MakeToast(EditMainUserIconActivity.this, "未检测到sd卡");
							return;
						}
					}
					dialog.dismiss();
				}
			});

	}

	/**
	 *  更改名称
	 *  
	 */
	private void showEditNickNameDialog() {
		Utils.showInputDialog(this, "修改昵称","请输入您的昵称", new BaseInputDialogOnclickListener() {

			@Override
			public void onConfirm(String edtString) {
				// TODO Auto-generated method stub
				if (TextUtils.isEmpty(edtString)) {
					MyToast.MakeToast(EditMainUserIconActivity.this,"您还没有输入昵称呢");
				}else {
					isUpdateContent = true;
					isModifyUserName = true;
					tvUserName.setText(edtString);
					
					if(isUpdateContent||isModifyUserName){
						confim.setVisibility(View.VISIBLE);
					}else{
						confim.setVisibility(View.GONE);
					}
				}
			}
			
			@Override
			public void onCancel() {
				
			}
		});
	}
	
	
	/**
	 *   弹出  更改 item 列表,修改信息  
	 * @param text 
	 * @param type
	 */
	private void showInfoItemDialog(String text, final String type) {
		AlertDialog.Builder builder=new AlertDialog.Builder(this);
		final AlertDialog dialog=builder.create();
		dialog.show();
		Window window = dialog.getWindow();
		View contentView = getLayoutInflater().inflate(R.layout.item_textview_poplayout,null);
		window.setContentView(contentView);
		TextView textView=(TextView) contentView.findViewById(R.id.vipName);
		textView.setText(text);
		
		ListView sexListView=(ListView) contentView.findViewById(R.id.sex_pop_listview);
		ListView addressListView=(ListView) contentView.findViewById(R.id.address_pop_listview);
		
		PopAdapter adapter;
		if(type.equals("sex")){
			sexListView.setVisibility(View.VISIBLE);
			addressListView.setVisibility(View.GONE);
			
			adapter=new PopAdapter(this,sexArray);
			sexListView.setAdapter(adapter);
		}else if(type.equals("address")){
			addressListView.setVisibility(View.VISIBLE);
			sexListView.setVisibility(View.GONE);
			
			adapter=new PopAdapter(this,addressArray);
			adapter.notifyDataSetChanged();
			addressListView.setAdapter(adapter);
		}
		sexListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				
				Log.e("CNM","arg2 :"+arg2+"===="+"arg3 :"+arg3);
				
				isUpdateContent = true;
				
				switch (arg2) {
				case 0:
						tvUserSex.setText(sexArray[arg2]);
						tvUserSex.setTextColor(getResources().getColor(R.color.blue));
						tvUserName.setTextColor(getResources().getColor(R.color.blue));
					
					break;

				case 1:
						tvUserSex.setText(sexArray[arg2]);
						tvUserSex.setTextColor(getResources().getColor(R.color.hong_girl));
						tvUserName.setTextColor(getResources().getColor(R.color.hong_girl));
					break;
				}
				
				dialog.dismiss();
				
				if(isUpdateContent||isModifyUserName){
					confim.setVisibility(View.VISIBLE);
				}else{
					confim.setVisibility(View.GONE);
				}
			}
		});
		
		
		addressListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				isUpdateContent = true;
				  //地区
				tvUserAddress.setText(addressArray[position]);
				tvUserAddress.setTextColor(getResources().getColor(R.color.blue));
			
				dialog.dismiss();
				
				if(isUpdateContent||isModifyUserName){
					confim.setVisibility(View.VISIBLE);
				}else{
					confim.setVisibility(View.GONE);
				}
				
			}
		});
		
	}

	// =============================== 性别 地区适配器

	class PopAdapter extends BaseAdapter {
		Context context;
		String[] contents;

		public PopAdapter(Context context, String[] vips) {
			this.context = context;
			this.contents = vips;
		}

		@Override
		public int getCount() {
			return contents.length;
		}

		@Override
		public Object getItem(int position) {
			return contents.length;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder viewHolder;
			if (convertView == null) {
				viewHolder = new ViewHolder();
				convertView = LayoutInflater.from(context).inflate(
						R.layout.edit_popitem, null);
				viewHolder.textView = (TextView) convertView
						.findViewById(R.id.pop_item_info);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			viewHolder.textView.setText(contents[position]);
			return convertView;
		}

		class ViewHolder {
			TextView textView;
		}
	}
	
	
//=============================================照片选取的多种方式
	private Uri photoUri;
	
	 //从相册获取图片 Intent 请求码
	private int REQUEST_PHOTO = 1;
	 // 拍照得到图片 Intent 请求码
	private int REQUEST_CAMERA = 2;
	//裁剪
	private int REQUEST_PICKED = 3;
	
	private String saveStr;
	
	/**
	 * 手机拍照
	 */
	protected void takePhoto() {
		//以当前时间命名生成的图片
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		SimpleDateFormat timeStampFormat = new SimpleDateFormat(
		"yyyy_MM_dd_HH_mm_ss");
		String filename = timeStampFormat.format(new Date());
		ContentValues values = new ContentValues();
		values.put(Media.TITLE, filename);

		photoUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

		intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);

		startActivityForResult(intent, REQUEST_CAMERA);

	}
	
	/**
	 * 相册获取图片
	 */
	protected void invokePhoto() {
		Intent intent = new Intent(Intent.ACTION_PICK, null);
		intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
		try {
			startActivityForResult(intent, REQUEST_PHOTO);
		} catch (android.content.ActivityNotFoundException e) {
			return;
		}
	}
    
    /**
     *截取图片
     * 
     * @param uri
     */
    public void doCropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 120);
        intent.putExtra("outputY", 120);
        intent.putExtra("return-data", true);
        
        startActivityForResult(intent, REQUEST_PICKED);
    }
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode==REQUEST_CAMERA){  //拍照返回
			if(resultCode==Activity.RESULT_OK){ //成功返回
				isUpdateContent = true;
				Uri uri = null;
				if (data != null && data.getData() != null) {
					uri = data.getData();
				}
				// 一些机型无法从getData中获取uri，则需手动指定拍照后存储照片的Uri
				if (uri == null) {
					if (photoUri != null) {
						uri = photoUri;
					}
				}
				doCropPhoto(uri);
			}
			return;
		}else if(requestCode==REQUEST_PHOTO){ //相册返回
			if(resultCode== Activity.RESULT_OK){              
				isUpdateContent = true;
				if (data != null) {
	                // 得到图片的全路径
	                Uri uri = data.getData();
	                doCropPhoto(uri);
	            }
			}
		}else if(requestCode==REQUEST_PICKED){//裁剪返回
			if(resultCode == Activity.RESULT_OK){
				isUpdateContent = true;
				final Bitmap photo = data.getParcelableExtra("data");
				new Handler().post(new Runnable(){
	
					@Override
					public void run() {
						if(photo!=null){
							try {
								 saveStr = Utils.bitmapToBase64(photo);
								FileUtil.saveFile(photo,FileUtil.mEditFileName); //保存图片
								//保存后开启广播刷新操作
								File myTargetFile = new File(FileUtil.SAVE_REAL_PATH, FileUtil.mEditFileName);
								Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
								Uri uri = Uri.fromFile(myTargetFile);
								intent.setData(uri);
								EditMainUserIconActivity.this.sendBroadcast(intent);
								
							} catch (IOException e) {
								e.printStackTrace();
							}finally{
								if(photo!=null){
									photo.recycle();
								}
								runOnUiThread(new Runnable() {
									
									@Override
									public void run() {
										userIcon.setImageBitmap(Utils.base64ToBitmap(saveStr));
										MyToast.MakeToast(EditMainUserIconActivity.this,"操作完成并保存");
									}
								});
							}
			            }
					}
					
				});
				
			}
		}
		
		
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	
	
	
	
//=============================================================================================
	
	

	/**
	 * 保存用户信息
	 */
	private void saveUserInfo() {
		editor = mSpUtil.getEditor();
		editor.putString("userName",tvUserName.getText().toString());
		editor.putString("userSex",tvUserSex.getText().toString());
		editor.putString("userAddress",tvUserAddress.getText().toString());
		
		//此处逻辑 根据 保存过的性别 来记录显示 用户名的 颜色   
		String sex = tvUserSex.getText().toString();
		if(sex.equals("男")){
			editor.putString("userColor","blue");
		}else if(sex.equals("女")){
			editor.putString("userColor","red");
		}else{
			
		}
		editor.commit();

		int x = (int) (Math.random()*2+1);
		if(x==1){
			saveDialog = Utils.showSmallProgressDialog(EditMainUserIconActivity.this,"保存中..");
		}else if(x==2){
			saveDialog = Utils.showSmallMultiColorProgressDialog(EditMainUserIconActivity.this,"保存中..");
		}else{}
		
		saveDialog.show();
		
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				if(saveDialog!=null&&saveDialog.isShowing()){
					saveDialog.dismiss();
				}
				MyToast.MakeToast(EditMainUserIconActivity.this,"更新成功");
				EditMainUserIconActivity.this.finish();
			}
		}, 1500);
		
		
	}
	
	
	@SuppressWarnings("static-access")
	private void displayImage(ImageView imageView, String url) {
		SysApplication.displayImage(imageView, url);
	}

	@Override
	protected void setContentView() {
		setContentView(R.layout.edit_user_info_main);
	}

}
