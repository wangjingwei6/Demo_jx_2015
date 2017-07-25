package com.qnmlgb.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.flyco.animation.BaseAnimatorSet;
import com.flyco.animation.FlipEnter.FlipVerticalSwingEnter;
import com.flyco.animation.FlipExit.FlipVerticalExit;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.NormalTipDialog;
import com.flyco.dialog.widget.base.BaseDialog;
import com.flyco.dialog.widget.base.BottomBaseDialog;
import com.flyco.dialog.widget.base.BottomTopBaseDialog;
import com.flyco.dialog.widget.base.TopBaseDialog;
import com.nineoldandroids.animation.ObjectAnimator;
import com.ninexiu.wjw.R;
import com.qnmlgb.activity.PersonPhotoActivity;
import com.qnmlgb.activity.PersonSettingActivity;
import com.qnmlgb.activity.PersonalCenterActivity;
import com.qnmlgb.activity.SignActivity;
import com.qnmlgb.activity.SubPageActivity;
import com.qnmlgb.activity.ZhifuActivity;
import com.qnmlgb.fragment.PersonCenterFragment;
import com.qnmlgb.util.Utils;

public class IOSTaoBaoDialog extends TopBaseDialog {
    private RelativeLayout ll_signin;
    private RelativeLayout ll_person;
    private RelativeLayout ll_charge;                                            
    private RelativeLayout ll_service;
                                  
    public IOSTaoBaoDialog(Context context, View animateView) {
        super(context, animateView);
    }

    public IOSTaoBaoDialog(Context context) {
        super(context);
    }

    @Override
    public View onCreateView() {
        View inflate = View.inflate(context, R.layout.dialog_ios_taobao, null);
        ll_signin = (RelativeLayout) inflate.findViewById(R.id.ll_signin);
        ll_person = (RelativeLayout) inflate.findViewById(R.id.ll_person);
        ll_charge = (RelativeLayout) inflate.findViewById(R.id.ll_charge);
        ll_service = (RelativeLayout) inflate.findViewById(R.id.ll_service);                                               
        
        Utils.addRippleEffect(ll_signin, 40);
        Utils.addRippleEffect(ll_person, 40);
        Utils.addRippleEffect(ll_charge, 40);
        Utils.addRippleEffect(ll_service, 40);
        
        return inflate;
    }

    @Override
    public boolean setUiBeforShow() {
    	ll_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	if(Utils.isClickable()){
            		return;
            	}
            	Intent personIntent = new Intent(context,
            			SignActivity.class);
            	context.startActivity(personIntent);
            	Utils.goInActivityAnimation(context);
            	
            }
        });
    	ll_person.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	if(Utils.isClickable()){
            		return;
            	}
            	Intent personIntent = new Intent(context,
            			PersonalCenterActivity.class);
            	context.startActivity(personIntent);
            	Utils.goInActivityAnimation(context);
//            	Intent intent = new Intent(context,    // 跳转之后没有保存 activity  未解决
//            			SubPageActivity.class);
//    			intent.putExtra(SubPageActivity.CLASSFRAMENT, PersonCenterFragment.class);
//    			context.startActivity(intent);
//                dismiss();
            }
        });
    	ll_charge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//            	BaseAnimatorSet bas_in = new FlipVerticalSwingEnter();
//    			BaseAnimatorSet bas_out = new FlipVerticalExit();
//    			
//    			 final NormalTipDialog dialog = new NormalTipDialog(context);
//    	            dialog	
//    	            		.dividerColor(context.getResources().getColor(R.color.green_accent))
//    	            		.content("啥也没有 待..")//
//    	                    .btnText("继续逛逛")//
//    	                    .contentGravity(Gravity.CENTER)
//    	                    .btnTextColor(Color.BLUE)
//    	                    .contentTextColor(Color.GREEN)
//    	                    .showAnim(bas_in)//
//    	                    .dismissAnim(bas_out)//
//    	                    .show();
//
//    	            dialog.setOnBtnClickL(new OnBtnClickL() {
//    	                @Override
//    	                public void onBtnClick() {
//    	                    dialog.dismiss();
//    	                }
//    	            });
//            	
//            	
//                dismiss();
//                
                // 充值页面
                Intent zhifuIntent = new Intent(context,
            			ZhifuActivity.class);
            	context.startActivity(zhifuIntent);
                
            }
            
        });
    	ll_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	if(Utils.isClickable()){
            		return;
            	}
            	BaseAnimatorSet bas_in = new FlipVerticalSwingEnter();
    			BaseAnimatorSet bas_out = new FlipVerticalExit();
    			
    			 final NormalTipDialog dialog = new NormalTipDialog(context);
    	            dialog	
    	            		.dividerColor(context.getResources().getColor(R.color.green_accent))
    	            		.content("你不是汪经纬，没有服务权限~")//
    	                    .btnText("继续逛逛")//
    	                    .showAnim(bas_in)//
    	                    .dismissAnim(bas_out)//
    	                    .show();

    	            dialog.setOnBtnClickL(new OnBtnClickL() {
    	                @Override
    	                public void onBtnClick() {
    	                    dialog.dismiss();
    	                }
    	            });
            	
            	
                dismiss();
            }
        });
    	
    	dismiss();
        return false;
    }

    private BaseAnimatorSet windowInAs;
    private BaseAnimatorSet windowOutAs;

    @Override
    protected BaseAnimatorSet getWindowInAs() {
        if (windowInAs == null) {
            windowInAs = new WindowsInAs();
        }
        return windowInAs;
    }

    @Override
    protected BaseAnimatorSet getWindowOutAs() {
        if (windowOutAs == null) {
            windowOutAs = new WindowsOutAs();
        }
        return windowOutAs;
    }

    class WindowsInAs extends BaseAnimatorSet {
        @Override
        public void setAnimation(View view) {
            ObjectAnimator rotationX = ObjectAnimator.ofFloat(view, "rotationX", 10, 0f).setDuration(150);
            rotationX.setStartDelay(200);
            animatorSet.playTogether(
                    ObjectAnimator.ofFloat(view, "scaleX", 1.0f, 0.8f).setDuration(350),
                    ObjectAnimator.ofFloat(view, "scaleY", 1.0f, 0.8f).setDuration(350),
//                    ObjectAnimator.ofFloat(view, "alpha", 1.0f, 0.5f).setDuration(350),
                    ObjectAnimator.ofFloat(view, "rotationX", 0f, 10).setDuration(200),
                    rotationX,
                    ObjectAnimator.ofFloat(view, "translationY", 0, -0.1f * dm.heightPixels).setDuration(350)
            );
        }
    }

    class WindowsOutAs extends BaseAnimatorSet {
        @Override
        public void setAnimation(View view) {
            ObjectAnimator rotationX = ObjectAnimator.ofFloat(view, "rotationX", 10, 0f).setDuration(150);
            rotationX.setStartDelay(200);
            animatorSet.playTogether(
                    ObjectAnimator.ofFloat(view, "scaleX", 0.8f, 1.0f).setDuration(350),
                    ObjectAnimator.ofFloat(view, "scaleY", 0.8f, 1.0f).setDuration(350),
//                    ObjectAnimator.ofFloat(view, "alpha", 1.0f, 0.5f).setDuration(350),
                    ObjectAnimator.ofFloat(view, "rotationX", 0f, 10).setDuration(200),
                    rotationX,
                    ObjectAnimator.ofFloat(view, "translationY", -0.1f * dm.heightPixels, 0).setDuration(350)
            );
        }
    }
    
}