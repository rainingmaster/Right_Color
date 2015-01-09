package com.rightcolor.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.LinearLayout;

public class ButtonBar extends LinearLayout {
	
	private Context mContext;
	private Button btnDeutan;
	private Button btnProtan;
	private Button btnTritan;
	
	public ButtonBar(Context context) {
        super(context);
        init(context);
    }

    public ButtonBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    
    public void init(Context context) {
    	mContext = context;
    	
    	btnDeutan = new Button(mContext);
    	btnDeutan.setText("btnDeutan");
    	
    	btnProtan = new Button(mContext);
    	btnProtan.setText("btnProtan");
    	
    	btnTritan = new Button(mContext);
    	btnTritan.setText("btnTritan");
    	
    	addView(btnDeutan);
    	addView(btnProtan);
    	addView(btnTritan);
    	
    	setOrientation(LinearLayout.VERTICAL);
    }
    
    public void setDeutanOnClickListener(OnClickListener l) {
    	btnDeutan.setOnClickListener(l);
    }
    
    public void setProtanOnClickListener(OnClickListener l) {
    	btnProtan.setOnClickListener(l);
    }
    
    public void setTritanOnClickListener(OnClickListener l) {
    	btnTritan.setOnClickListener(l);
    }
}
