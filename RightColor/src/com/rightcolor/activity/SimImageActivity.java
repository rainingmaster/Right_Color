package com.rightcolor.activity;

import com.rightcolor.Util.UtilCommon;
import com.rightcolor.Util.UtilSimulator;
import com.rightcolor.Util.UtilSimulator.Simulation;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class SimImageActivity extends Activity implements OnClickListener {
	
	/**
	 * 原始照片
	 */
	private Bitmap mRawPic;
	/**
	 * 压缩后图片
	 */
	private Bitmap mPressPic;
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
	 * 进度显示
	 */
	private ProgressBar mProgress;

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
 
		//显示初始图片
		mSimulatorView.setImageBitmap(mRawPic);
		
        /*//压缩图片
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
        mPressPic = UtilCommon.zoomImage(mRawPic, metric.widthPixels, metric.heightPixels);*/
		
		//转换图片
        mSimulator.setSrcBitmap(mRawPic);
        mSimPic = mSimulator.Simulator();
		
        //显示转换后图片
        mSimulatorView.setImageBitmap(mSimPic);
        
        mProgress = (ProgressBar) findViewById(R.id.progressBar);
        mProgress.setVisibility(View.GONE);
	}
	
	@Override
	public void onClick(View v) {
		// TODO 自动生成的方法存根
		if(v == mSimulatorView) {
			finish();//停止本页面，返回抓取
		}
	}
}
