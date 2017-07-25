package com.qnmlgb.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.qnmlgb.view.MaterialRippleLayout;

public class RippleImageButton extends ImageButton {

	public RippleImageButton(Context context, AttributeSet attrs,
			int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}

	public RippleImageButton(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}

	public RippleImageButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public RippleImageButton(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setOnClickListener(OnClickListener l) {
		// TODO Auto-generated method stub
		
		ViewGroup parent = (ViewGroup) this.getParent();
        if (parent != null && parent instanceof MaterialRippleLayout) {
        	
        }else {
        	MaterialRippleLayout.on(this).rippleOverlay(true)
    		.rippleAlpha(0.2f)
    		.rippleColor(0x0A000000)
    		.rippleHover(false)
    		.rippleRoundedCorners(25)
    		.create();
		}
		
		super.setOnClickListener(l);
	}
	
}
