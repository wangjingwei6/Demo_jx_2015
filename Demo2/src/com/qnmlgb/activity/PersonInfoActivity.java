package com.qnmlgb.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

import com.ninexiu.wjw.R;
import com.qnmlgb.fragment.ShowLoveFragment;
import com.qnmlgb.util.MyToast;
import com.qnmlgb.util.Utils;
import com.qnmlgb.util.Utils.BaseInputDialogOnclickListener;
import com.qnmlgb.view.CircularImageView;
import com.qnmlgb.view.SquareImageView;

public class PersonInfoActivity extends Activity implements OnClickListener {
	private CircularImageView person_musicimg;
	private SquareImageView img1;
	private SquareImageView img2;
	private SquareImageView img3;
	private SquareImageView img4;

	private int[] imgs = new int[] { R.drawable.person7, R.drawable.person_info_img1, R.drawable.person_info_img2,
			R.drawable.person11, };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setTheme(R.style.AnimationTheme);
		setContentView(R.layout.person_info);
		initView();
		setOnclick();
	}

	private void setOnclick() {
		person_musicimg.setOnClickListener(this);
		img1.setOnClickListener(this);
		img2.setOnClickListener(this);
		img3.setOnClickListener(this);
		img4.setOnClickListener(this);
	}

	private void initView() {
		person_musicimg = (CircularImageView) findViewById(R.id.person_musicimg);
		img1 = (SquareImageView) findViewById(R.id.img1);
		img2 = (SquareImageView) findViewById(R.id.img2);
		img3 = (SquareImageView) findViewById(R.id.img3);
		img4 = (SquareImageView) findViewById(R.id.img4);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img1:
			toPersonIntentActivity(imgs[0]);
			break;
		case R.id.img2:
			toPersonIntentActivity(imgs[1]);
			break;
		case R.id.img3:
			toPersonIntentActivity(imgs[2]);
			break;
		case R.id.img4:
			toPersonIntentActivity(imgs[3]);
			break;
		case R.id.person_musicimg:
			Utils.showInputDialog(this, "密码验证", "请输入你的密码", new BaseInputDialogOnclickListener() {

				@Override
				public void onConfirm(String edtString) {
					if(edtString.equals("wanglixin")){
						Intent intent = new Intent(PersonInfoActivity.this, SubPageActivity.class);
						intent.putExtra(SubPageActivity.CLASSFRAMENT, ShowLoveFragment.class);
						startActivity(intent);
						finish();
					}else{
						MyToast.MakeToast(PersonInfoActivity.this, "密码错误请重新输入！");
					}
				}

				@Override
				public void onCancel() {

				}
			});

			break;

		default:
			break;
		}

	}

	private void toPersonIntentActivity(int imgId) {
		Intent intent = new Intent(PersonInfoActivity.this, PersonPhotoActivity.class);
		intent.putExtra("type", "gallery");
		intent.putExtra("photoId", imgId);
		startActivity(intent);
		PersonInfoActivity.this.overridePendingTransition(R.anim.zoom_in, 0);// 切换Activity的过渡动画
	}
}
