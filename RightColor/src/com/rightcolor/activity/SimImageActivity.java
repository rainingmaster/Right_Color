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
	 * ԭʼ��Ƭ
	 */
	private Bitmap mRawPic;
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
	 * ����UI����Handler
	 */
	private Handler mUIHandler;

	/**
	 * ת������
	 */
	private ProgressBar mProgress;
	
	/**
	 * ���水ť
	 */
	private Button mSave;
	
	/**
	 * ��ʾ/���ؿؼ�
	 */
	private boolean mToggle = false;
	
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
		
		//���水ť
		mSave = (Button)findViewById(R.id.saveButton);
		mSave.setOnClickListener(this);
		
		manToggle();
 
		//��ʾ��ʼͼƬ
		mSimulatorView.setImageBitmap(mRawPic);
		
		mProgress = (ProgressBar)findViewById(R.id.progressSim);
		
		//�����������̵߳�handler  
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
	        //��ʾת����ͼƬ
	        mSimulatorView.setImageBitmap(mSimPic);
	        mProgress.setVisibility(View.GONE);
		}
		
	};

	/**
	 * ת��ͼƬ
	 */
	private void simulatorImage() {
		//ת��ͼƬ
        mSimulator.setSrcBitmap(mRawPic);
        mSimPic = mSimulator.Simulator();
	}
	
	@Override
	public void onClick(View v) {
		// TODO �Զ����ɵķ������
		if(v == mSimulatorView) {
			manToggle();
			//finish();//ֹͣ��ҳ�棬����ץȡ
		}
		else if(v == mSave) {
			String path = UtilCommon.getSDCardPath() + "/RightColor/" + System.currentTimeMillis() + ".jpg";
			UtilCommon.saveBitmap(mSimPic, path);
			Toast.makeText(v.getContext(),
					"�ѽ�ͼƬ������" + path,
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
