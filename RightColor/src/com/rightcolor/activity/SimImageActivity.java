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
	 * ԭʼ��Ƭ
	 */
	private Bitmap mRawPic;
	/**
	 * ѹ����ͼƬ
	 */
	private Bitmap mPressPic;
	/**
	 * �����ͼƬ
	 */
	private Bitmap mSimPic;
	
	/**
	 * ת������
	 */
	private Simulation mSimType;
	
	/**
	 * ת����Ԥ��ͼ
	 */
	private ImageView mSimulatorView;
	
	/**
	 * ת�����߼�
	 */
	private UtilSimulator mSimulator;
	
	/**
	 * ������ʾ
	 */
	private ProgressBar mProgress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.simulator_activity);
		
		//��ȡIntent�е�Bundle����
        Bundle bundle = this.getIntent().getExtras();
        
        //��ȡBundle�е����ݣ�ע�����ͺ�key
        String picPath = bundle.getString("path");
        mSimType = (Simulation) bundle.getSerializable("type");
        
		mSimulator = new UtilSimulator();
		mSimulator.setSimulationType(mSimType);
		
		mRawPic = BitmapFactory.decodeFile(picPath);
		
		//������ʾͼƬ
		mSimulatorView = (ImageView)findViewById(R.id.showView);
		mSimulatorView.setClickable(true);
		mSimulatorView.setOnClickListener(this);
 
		//��ʾ��ʼͼƬ
		mSimulatorView.setImageBitmap(mRawPic);
		
        /*//ѹ��ͼƬ
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
        mPressPic = UtilCommon.zoomImage(mRawPic, metric.widthPixels, metric.heightPixels);*/
		
		//ת��ͼƬ
        mSimulator.setSrcBitmap(mRawPic);
        mSimPic = mSimulator.Simulator();
		
        //��ʾת����ͼƬ
        mSimulatorView.setImageBitmap(mSimPic);
        
        mProgress = (ProgressBar) findViewById(R.id.progressBar);
        mProgress.setVisibility(View.GONE);
	}
	
	@Override
	public void onClick(View v) {
		// TODO �Զ����ɵķ������
		if(v == mSimulatorView) {
			finish();//ֹͣ��ҳ�棬����ץȡ
		}
	}
}
