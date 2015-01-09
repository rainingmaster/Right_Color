package com.rightcolor.activity;

import java.io.ObjectOutputStream.PutField;

import org.json.JSONException;

import com.rightcolor.Util.UtilCommon;
import com.rightcolor.Util.UtilSimulator;
import com.rightcolor.Util.UtilSimulator.Simulation;
import com.rightcolor.widget.ButtonBar;
import com.rightcolor.widget.CameraManager;
import com.rightcolor.widget.VerticalSeekBar;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class CatchPicture extends Activity {

	private static final String TAG = "CameraActivity";
	
	/**
	 * ��ʾԤ��
	 */
	private SurfaceView mCameraView;
	
	/**
	 * ����Ĺػ���
	 */
	private CameraManager mCamera;
	
	/**
	 * �����holder
	 */
	private SurfaceHolder mHolder;
	
	/**
	 * ��ʾsimlatorview���״�壬�ֱ���ʾfocusebar��simbar
	 */
	private boolean toggle = true;
	
	/**
	 * ת������
	 */
	private Simulation mSimType;

	/**
	 * �������seekbar
	 */
	private VerticalSeekBar mFocusSeek;
	
	/**
	 * ����ת���İ�ť��
	 */
	private ButtonBar mSimButton;

	/**
	 * ת��bar
	 */
	private LinearLayout mSimBar;
	
	/**
	 * �������seekbar
	 */
	private LinearLayout mFocusBar;
	
	/**
	 * ԭʼͼƬ
	 */
	private Bitmap mRawPic;
	
	/**
	 * ѹ��ͼƬ
	 */
	private Bitmap mPressPic;
	
	/**
	 * ������Ƭ����·��
	 */
	public String mSavePath;
	
	private ProgressBar mProgress;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���ڱ���,��ʵ������manifest�ļ�����ע��
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// ȫ�� 
	    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.catch_activity);
		//������ת
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
		
		mCamera = new CameraManager(this);
		mCamera.open();
		
		mCameraView =  (SurfaceView) findViewById(R.id.cl_camera_preview);
		mCameraView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				manToggle();
			}
		});
		mHolder = mCameraView.getHolder();
		mHolder.addCallback(new SurfaceViewCallback());
		
		mSimBar = (LinearLayout)findViewById(R.id.cl_choose_bar);
		mSimButton = new ButtonBar(this);
		mSimButton.setDeutanOnClickListener(new OnClickListener() {//���õ��Ч��
			
			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				mSimType = Simulation.deutan;
				mCamera.takePicture(new TakePicture());
			}
		});
		mSimButton.setProtanOnClickListener(new OnClickListener() {//���õ��Ч��
			
			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				mSimType = Simulation.protan;
				mCamera.takePicture(new TakePicture());
			}
		});
		mSimButton.setTritanOnClickListener(new OnClickListener() {//���õ��Ч��
			
			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				mSimType = Simulation.tritan;
				mCamera.takePicture(new TakePicture());
			}
		});
		mSimBar.addView(mSimButton);
		
		//���Խ���
		mFocusBar = (LinearLayout)findViewById(R.id.cl_focus_bar);
		mFocusSeek = (VerticalSeekBar)findViewById(R.id.simSeekBar);
		mFocusBar.setVisibility(View.GONE);
		
		//����������Ƭ����·��
		mSavePath = UtilCommon.getSDCardPath() + "/RightColor/raw.jpg";
		mProgress = (ProgressBar)findViewById(R.id.progressCatch);
		mProgress.setVisibility(View.GONE);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO �Զ����ɵķ������	
		mCamera.CameraRotation();
		
		super.onConfigurationChanged(newConfig);
	}
	
	@Override
	protected void onResume() {
		// TODO �Զ����ɵķ������
		Log.d(TAG,"onResume");
		//openCamera(mHolder);
		super.onResume();
	}

	@Override
	protected void onPause() {
		// TODO �Զ����ɵķ������
		Log.d(TAG,"onPause");
		//releaseCamera();
		super.onPause();
	}
	
	private void openCamera(SurfaceHolder holder) {
		mCamera.open();  
        mCamera.setPreviewDisplay(holder); 
        mCamera.startPreview(); 
        
    	Camera.Parameters parameters = mCamera.getParameters();
		if(parameters.isZoomSupported()) {
			int a = parameters.getMaxZoom();
			mFocusSeek.setMax(a);
		}
		mFocusSeek.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO �Զ����ɵķ������
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO �Զ����ɵķ������
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO �Զ����ɵķ������
				Camera.Parameters parameters = mCamera.getParameters();
				if(parameters.isSmoothZoomSupported()) {//�Ƿ�֧�ֱ佹
			        parameters.setZoom(progress);
			        mCamera.setParameters(parameters);
				}
			}
		});
	}
	
	private void releaseCamera() {
		mCamera.release();
	}

	private void manToggle() {
		if(toggle) {
			mFocusBar.setVisibility(View.VISIBLE);
			mSimBar.setVisibility(View.GONE);
		}
		else {
			mFocusBar.setVisibility(View.GONE);
			mSimBar.setVisibility(View.VISIBLE);
		}
		toggle = !toggle;
	}
	
	/**
	 * ��ת��ת��ҳ��
	 */
	private void turnToImage() {
		Bundle bundle = new Bundle();
		bundle.putString("path", mSavePath);
		bundle.putSerializable("type", mSimType);
		  
		Intent intent = new Intent();
		intent.putExtras(bundle);
		intent.setClass(CatchPicture.this, SimImageActivity.class);
		startActivity(intent);
	}
	
	private final class SurfaceViewCallback implements Callback {

		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			// TODO �Զ����ɵķ������
			Log.d(TAG,"surfaceCreated");
			openCamera(holder);
		}

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {
			// TODO �Զ����ɵķ������
			Log.d(TAG,"surfaceChanged");
		}

		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
			// TODO �Զ����ɵķ������
			Log.d(TAG,"surfaceDestroyed"); 
			releaseCamera();
		}
		
		
	}
	
	/**
	 * �������ʾ��ת��view--SimulatorView��
	 * @author Administrator
	 *
	 */
	public final class TakePicture implements Camera.PictureCallback {
		
		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			// TODO �Զ����ɵķ������
	        mPressPic = null;
	        mRawPic = null;
	        
			mProgress.setVisibility(View.VISIBLE);
			mRawPic = BitmapFactory.decodeByteArray(data, 0, data.length);

	        mCamera.stopPreview();
	        
	        //ѹ��ͼƬ
			DisplayMetrics metric = new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(metric);
	        mPressPic = UtilCommon.zoomImage(mRawPic, metric.widthPixels, metric.heightPixels);
	        
	        //UtilCommon.saveBitmap(mRawPic, mSavePath);
	        UtilCommon.saveBitmap(mPressPic, mSavePath);
       
			turnToImage();
			mProgress.setVisibility(View.GONE);
		}
		
	}
}
