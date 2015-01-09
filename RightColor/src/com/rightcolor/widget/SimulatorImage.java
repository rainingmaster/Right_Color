package com.rightcolor.widget;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.rightcolor.Util.UtilCommon;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class SimulatorImage extends RelativeLayout implements OnClickListener {
	
	private Context mContext;
	private ImageView mSimImageView;
	private Bitmap mSimBitmap;
	private Button mSave;

	public SimulatorImage(Context context) {
        super(context);
        init(context);
    }

    public SimulatorImage(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    
    private void init(Context context) {
    	mContext = context;
    	mSimImageView = new ImageView(mContext);
    	RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT, 
				LinearLayout.LayoutParams.MATCH_PARENT);
		mSimImageView.setLayoutParams(layoutParams);
		
		mSave = new Button(mContext);
		mSave.setOnClickListener(this);
    }
    
    public void setImageBitmap(Bitmap bitmap) {
    	mSimImageView.setImageBitmap(bitmap);
    }

	@Override
	public void onClick(View v) {
		// TODO 自动生成的方法存根
		FileOutputStream fos = null;  
            try {
				fos = new FileOutputStream(UtilCommon.getSDCardPath() + "/RightColor/" + System.currentTimeMillis() + ".jpg");
	            if (null != fos)  
	            {  
	            	mSimBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
	                fos.flush();  
	                fos.close();  
	            } 
			} catch (FileNotFoundException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}  
	}
}
