package com.qnmlgb.activity;

import com.ninexiu.wjw.R;
import com.qnmlgb.fragment.PersonPhotoFragment;

import android.content.Intent;
import android.os.Bundle;


public class MoNiActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		getSupportFragmentManager().beginTransaction()
		.add(R.id.container, new PersonPhotoFragment()).commitAllowingStateLoss();
	}

	@Override
	protected void setContentView() {
		setContentView(R.layout.fragmentactivity);		
	}

	@Override
	protected void onPause() {
		super.onPause();
	}


	@Override
	protected void onResume() {
		super.onResume();
	}	
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Intent intent = new Intent(MoNiActivity.this,
				PersonalCenterActivity.class);
		startActivity(intent);
		this.overridePendingTransition(R.anim.anim_in_left,R.anim.anim_out_right);
	}
	
}
