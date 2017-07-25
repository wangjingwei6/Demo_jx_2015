package com.qnmlgb.activity;

import com.ninexiu.wjw.R;
import com.qnmlgb.util.MyToast;
import com.qnmlgb.util.SystemBarTintManager;
import com.qnmlgb.util.Utils;
import com.qnmlgb.view.ResizeLayout;
import com.qnmlgb.view.ResizeLayout.OnkeyboardShowListener;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

/*
 * 支付宝界面
 */
@SuppressLint("NewApi")
public class ZhifuActivity extends BaseActivity implements OnClickListener {

	protected String accountname;
	protected String accountpwd;


	private int[] tvIds = new int[] { R.id.tv_one_charge_thiry,
			R.id.tv_one_charge_fifty, R.id.tv_one_charge_one_hundred,
			R.id.tv_one_charge_thiry_hundred, R.id.tv_one_charge_five_hundred,
			R.id.tv_one_charge_one_thousand };
	private TextView[] tv = new TextView[tvIds.length];
	private TextView linear_thirty;
	private TextView linear_fifty;
	private TextView linear_hundred;
	private TextView linear_three_hundred;
	private TextView linear_five_hundred;
	private TextView linear_one_thousand;
	private LinearLayout clearBut_ll;
	private ImageView left_btn;
	private EditText et_input;
	private Button confirmBut;
	private ImageButton clearBut;
	private ScrollView mScrollView;
	private LinearLayout charge_content;
	
	public SystemBarTintManager mTintManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mTintManager = new SystemBarTintManager(this);
		mTintManager.setStatusBarTintEnabled(true);
		mTintManager.setStatusBarTintResource(R.color.hong);
		
		initView();
		setOnClick();
	}

	private void initView() {
		
		TextView title = (TextView) findViewById(R.id.title);
		title.setText("充值");
		linear_thirty = (TextView) findViewById(R.id.tv_one_charge_thiry);
		linear_fifty = (TextView) findViewById(R.id.tv_one_charge_fifty);
		linear_hundred = (TextView) findViewById(R.id.tv_one_charge_one_hundred);
		linear_three_hundred = (TextView) findViewById(R.id.tv_one_charge_thiry_hundred);
		linear_five_hundred = (TextView) findViewById(R.id.tv_one_charge_five_hundred);
		linear_one_thousand = (TextView) findViewById(R.id.tv_one_charge_one_thousand);
		
		linear_thirty.setOnClickListener(this);
		linear_fifty.setOnClickListener(this);
		linear_hundred.setOnClickListener(this);
		linear_three_hundred.setOnClickListener(this);
		linear_five_hundred.setOnClickListener(this);
		linear_one_thousand.setOnClickListener(this);
		clearBut_ll = (LinearLayout)findViewById(R.id.charge_close_ll);
		left_btn = (ImageView) findViewById(R.id.left_btn);
		findViewById(R.id.line_shadow).setVisibility(View.VISIBLE);
		for (int i = 0; i < tvIds.length; i++) {
			tv[i] = (TextView) findViewById(tvIds[i]);
		}

		et_input = (EditText) findViewById(R.id.charge_et_input);
		et_input.requestFocus();
		
		confirmBut = (Button) findViewById(R.id.zhifu);
		Utils.addRippleEffect(confirmBut);
		
		clearBut = (ImageButton) findViewById(R.id.charge_close);
		mScrollView = (ScrollView) findViewById(R.id.scroll);
		
		ResizeLayout mResizeLayout = (ResizeLayout) findViewById(R.id.fl_resize_layout);
		mResizeLayout.setOnKeyboardShowListener(new OnkeyboardShowListener() {
			public void onKeyboardShowOver() {
				
			}
			public void onKeyboardShow(int keyHeight) {
				mScrollView.postDelayed(new Runnable() {
					
					@Override
					public void run() {
						mScrollView.scrollTo(0, 300);
					}
				},200);
			}
			
			public void onKeyboardHide() {
				mScrollView.scrollTo(0,0);
				
			}
		});
		charge_content = (LinearLayout)findViewById(R.id.charge_content);
		Animation startAnimation = AnimationUtils.loadAnimation(ZhifuActivity.this,R.anim.charge);
		charge_content.startAnimation(startAnimation);
		if (VERSION.SDK_INT > android.os.Build.VERSION_CODES.HONEYCOMB) // add 3.1.2 java.lang.NoSuchMethodError android.view.View.setLayerType
        { 
			charge_content.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        } 
	}

	private void setOnClick() {
		confirmBut.setOnClickListener(this);
		left_btn.setOnClickListener(this);
		clearBut_ll.setOnClickListener(this);
		linear_thirty.setOnClickListener(this);
		linear_fifty.setOnClickListener(this);
		linear_hundred.setOnClickListener(this);
		linear_three_hundred.setOnClickListener(this);
		linear_five_hundred.setOnClickListener(this);
		linear_one_thousand.setOnClickListener(this);

		et_input.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				clearBut.setVisibility(View.VISIBLE);
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			@Override
			public void afterTextChanged(Editable s) {

			}
		});

	}

	@Override
	public void onClick(View v) {
		if(Utils.isClickable()){
			return;
		}
		switch (v.getId()) {
			case R.id.tv_one_charge_thiry:
				changeBg(v.getId());
				//弹出支付平台框
				showChargeDialog(10+"");
				break;
			case R.id.tv_one_charge_fifty:
				changeBg(v.getId());
				//弹出支付平台框
				showChargeDialog(50+"");
				break;
			case R.id.tv_one_charge_one_hundred:
				changeBg(v.getId());
				//弹出支付平台框
				showChargeDialog(100+"");
				break;
			case R.id.tv_one_charge_thiry_hundred:
				changeBg(v.getId());
				//弹出支付平台框
				showChargeDialog(300+"");
				break;
			case R.id.tv_one_charge_five_hundred:
				changeBg(v.getId());
				//弹出支付平台框
				showChargeDialog(500+"");
				break;
			case R.id.tv_one_charge_one_thousand:
				changeBg(v.getId());
				//弹出支付平台框
				showChargeDialog(1000+"");
				break;
		case R.id.left_btn:
			finish();
			break;
		case R.id.zhifu:
			String input = et_input.getText().toString().trim();
			if (TextUtils.isEmpty(input)) {
				MyToast.MakeToast(ZhifuActivity.this, "请输入充值金额 ");
				return;
			}
			if(input.length()>6){
				MyToast.MakeToast(ZhifuActivity.this, "充值金额太大"); 
				return;
			}
			int chargeMoney = Integer.valueOf(input);
			if (chargeMoney < 10) {
				MyToast.MakeToast(ZhifuActivity.this, "充值金额应大于或等于10元");
				return;
			}
			//弹出支付平台框
			showChargeDialog(input);
			break;
		case R.id.charge_close_ll:
			et_input.setText("");
			clearBut.setVisibility(View.GONE);
			changeBg(R.id.charge_close);
			break;
		default:
			break;
		}
	}

	private void changeBg(int id) {
		for (int i = 0; i < tvIds.length; i++) {
			if (tvIds[i] == id) {
				tv[i].setTextColor(getResources().getColor(R.color.hong));
				tv[i].setTextSize(22);
				et_input.setText(tv[i].getText().toString().substring(1));
			} else {
				tv[i].setTextColor(getResources().getColor(R.color.charge_channel_title));
				tv[i].setTextSize(16);
			}
		}
	}


	private AlertDialog chargeChannelDialog;
	/**
	 * 显示支付平台窗口
	 */
	private void showChargeDialog(final String money){
		if(chargeChannelDialog!=null && chargeChannelDialog.isShowing()){
			return;
		}
		chargeChannelDialog = new AlertDialog.Builder(this).create();
		chargeChannelDialog.show();
//		chargeChannelDialog.getWindow().setLayout(NineShowApplication.getScreenWidth(this), NineShowApplication.getScreenHeight(this));
		chargeChannelDialog.setCanceledOnTouchOutside(true);
		Window window = chargeChannelDialog.getWindow();
		final View view = LayoutInflater.from(this).inflate(R.layout.ns_charge_dialog, null);
		window.setContentView(view);
//		LinearLayout ll_charge_bg = (LinearLayout)view.findViewById(R.id.ll_charge_bg);
		TextView tv_charge_title = (TextView)view.findViewById(R.id.tv_charge_title);
		LinearLayout ll_charge_01 = (LinearLayout)view.findViewById(R.id.ll_charge_01);
		LinearLayout ll_charge_02 = (LinearLayout)view.findViewById(R.id.ll_charge_02);
		LinearLayout ll_charge_03 = (LinearLayout)view.findViewById(R.id.ll_charge_03);
		LinearLayout ll_charge_04 = (LinearLayout)view.findViewById(R.id.ll_charge_04);
		String chargeMoney = getResources().getString(R.string.charge_money, Integer.parseInt(money)*1000+"",money);
		SpannableStringBuilder wealthBuilder = getChargeBuilder(chargeMoney,Integer.parseInt(money)*1000+"",money);
		tv_charge_title.setText(wealthBuilder);
//		ll_charge_bg.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				if(chargeChannelDialog!=null && chargeChannelDialog.isShowing()){
//					chargeChannelDialog.dismiss();
//				}
//			}
//		});
		
		//微信充值  
		ll_charge_01.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				MyToast.MakeToast(ZhifuActivity.this,"微信支付 :"+money+" ￥");
			}
		});
		//支付宝充值
		ll_charge_02.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				MyToast.MakeToast(ZhifuActivity.this,"支付宝支付 :"+money+" ￥");
			}
		});
		//银联
		ll_charge_03.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				MyToast.MakeToast(ZhifuActivity.this,"银联支付 :"+money+" ￥");
			}
		});
		//手机卡充值
		ll_charge_04.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				MyToast.MakeToast(ZhifuActivity.this,"手机充值卡支付 :"+money+" ￥");
			}
		});
	}
	/**
	 * 设置 金币显示的颜色
	 * 
	 * @param sourceContent
	 * @return
	 */
	private SpannableStringBuilder getChargeBuilder(String sourceContent,String org1,String org2) {
		SpannableStringBuilder mBuilder = new SpannableStringBuilder(sourceContent);
		mBuilder.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.hong)), 5, 5+org1.length(),
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		mBuilder.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.hong)), 8+org1.length(),sourceContent.length()-1,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		return mBuilder;
	}


	@Override
	protected void setContentView() {
		setContentView(R.layout.charge_layout_money);
		
	}
	
}