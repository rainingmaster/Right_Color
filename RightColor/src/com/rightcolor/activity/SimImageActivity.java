package com.rightcolor.activity;

import com.rightcolor.Util.UtilCommon;
import com.rightcolor.Util.UtilSimulator;
import com.rightcolor.Util.UtilSimulator.Simulation;
import com.rightcolor.activity.CatchPicActivity.TakePicture;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class SimImageActivity extends Activity implements OnClickListener {
	
	/**
	 * 原始照片
	 */
	private Bitmap mRawPic;
	/**
	 * 处理后图片
	 */
	private Bitmap mSimPic;
	
	/**
	 * 转换类型
	 */
	private Simulation mSimType;
	
	/**
	 * 转换后预览图
	 */
	private ImageView mSimulatorView;
	
	/**
	 * 转换工具集
	 */
	private UtilSimulator mSimulator;

	/**
	 * 更新UI处理Handler
	 */
	private Handler mUIHandler;

	/**
	 * 转换进度
	 */
	private ProgressBar mProgress;
	
	/**
	 * 保存按钮
	 */
	private Button mSave;
	
	/**
	 * 显示/隐藏控件
	 */
	private boolean mToggle = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.simulator_activity);
		
		//获取Intent中的Bundle对象
        Bundle bundle = this.getIntent().getExtras();
        
        //获取Bundle中的数据，注意类型和key
        String picPath = bundle.getString("path");
        mSimType = (Simulation) bundle.getSerializable("type");
        
		mSimulator = new UtilSimulator();
		mSimulator.setSimulationType(mSimType);
		
		mRawPic = BitmapFactory.decodeFile(picPath);
		
		//用于显示图片
		mSimulatorView = (ImageView)findViewById(R.id.showView);
		mSimulatorView.setClickable(true);
		mSimulatorView.setOnClickListener(this);
		
		//保存按钮
		mSave = (Button)findViewById(R.id.saveButton);
		mSave.setOnClickListener(this);
		
		manToggle();
 
		//显示初始图片
		mSimulatorView.setImageBitmap(mRawPic);
		
		mProgress = (ProgressBar)findViewById(R.id.progressSim);
		
		//创建属于主线程的handler  
		mUIHandler=new Handler();
		new Thread(){  
            public void run(){    
            	simulatorImage();
            	mUIHandler.post(UiRun);   
                }                     
        }.start();
	}
	
	private Runnable UiRun = new Runnable() {

		@Override
		public void run() {
	        //显示转换后图片
	        mSimulatorView.setImageBitmap(mSimPic);
	        mProgress.setVisibility(View.GONE);
		}
		
	};

	/**
	 * 转换图片
	 */
	private void simulatorImage() {
		//转换图片
        mSimulator.setSrcBitmap(mRawPic);
        mSimPic = mSimulator.Simulator();
	}
	
	@Override
	public void onClick(View v) {
		// TODO 自动生成的方法存根
		if(v == mSimulatorView) {
			manToggle();
			//finish();//停止本页面，返回抓取
		}
		else if(v == mSave) {
			String path = UtilCommon.getSDCardPath() + "/RightColor/" + System.currentTimeMillis() + ".jpg";
			UtilCommon.saveBitmap(mSimPic, path);
			Toast.makeText(v.getContext(),
					"已将图片保存在" + path,
					Toast.LENGTH_LONG).show();
		}
	}
	
	private void manToggle() {
		if(mToggle) {
			mSave.setVisibility(View.VISIBLE);
		}
		else {
			mSave.setVisibility(View.GONE);
		}
		mToggle = !mToggle;
	}
}
