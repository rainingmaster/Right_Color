package com.rightcolor.widget;

import java.io.IOException;


import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.view.Surface;
import android.view.SurfaceHolder;

/**
 * �Զ���camera��ʹ�õ���ģʽ
 * @author Administrator
 *
 */
public class CameraManager {

	private static Camera mHardCamera;
	private static boolean mPreview = false;
	private static Context mContext;
	
	public CameraManager(Context context) {
		// TODO �Զ����ɵĹ��캯�����
		mContext = context;
	}
	
	public Camera open() {
		if(mHardCamera == null) {
			mHardCamera = Camera.open();
			  
            Camera.Parameters parameters = mHardCamera.getParameters();
            /* ÿ�������ͷ����5֡���棬 */  
            parameters.setPreviewFrameRate(5);  
            /* ������Ƭ�������ʽ:jpg */  
            parameters.setPictureFormat(PixelFormat.JPEG);
            /* ��Ƭ���� */  
            parameters.set("jpeg-quality", 85);
			parameters.set("orientation", "portrait");
            mHardCamera.setDisplayOrientation(90);//���android2.2��֮ǰ�İ汾
            parameters.setRotation(90);//ȥ��android2.0��֮ǰ�İ汾
            /* �����������赽 mHardCamera ������ */
            mHardCamera.getParameters().getSupportedSceneModes();
            mHardCamera.setParameters(parameters);
            
            mPreview = false;
		}
		return mHardCamera;
	}
	
	public void setPreviewDisplay(SurfaceHolder holder) {
		try {
			mHardCamera.setPreviewDisplay(holder);
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}
	
	public void startPreview() {
		if(!mPreview) {
			mHardCamera.startPreview();
		}
		mPreview = true;
	}
	
	public void stopPreview() {
		if(mPreview) {
			mHardCamera.stopPreview();
		}
		mPreview = false;
	}
	
	public void release() {
		if(mHardCamera != null) {
			stopPreview();
			mHardCamera.release();
			mHardCamera = null;
		}
	}
	
	public void setPreviewCallback(Camera.PreviewCallback callback) {
		mHardCamera.setPreviewCallback(callback);
	}
	
	public void takePicture(Camera.PictureCallback callback) {
		mHardCamera.takePicture(null, null, callback);
	}
	
	public void CameraRotation(){
		if(mHardCamera != null) {
			Camera.Parameters parameters = mHardCamera.getParameters();
	
			int rotation = ((Activity)mContext).getWindowManager().getDefaultDisplay().getRotation();
			
			switch (rotation) {  
			case Surface.ROTATION_0:
				parameters.set("orientation", "portrait");
				mHardCamera.setDisplayOrientation(90);//���android2.2��֮ǰ�İ汾
	            parameters.setRotation(90);//ȥ��android2.0��֮ǰ�İ汾
	            break;
			case Surface.ROTATION_90:  
				parameters.set("orientation", "landscape");
				mHardCamera.setDisplayOrientation(0);
	            parameters.setRotation(0);
	            break;
			case Surface.ROTATION_180: 
				parameters.set("orientation", "portrait");
				mHardCamera.setDisplayOrientation(270);
	            parameters.setRotation(270);
	            break;
			case Surface.ROTATION_270: 
				parameters.set("orientation", "landscape");
				mHardCamera.setDisplayOrientation(180);
	            parameters.setRotation(180);
	            break;
			}
			mHardCamera.setParameters(parameters);
		}
	}
	
	public void setParameters(Camera.Parameters parameters) {
		mHardCamera.setParameters(parameters);
	}
	
	public Camera.Parameters getParameters() {
		return mHardCamera.getParameters();
	}
	
	

}
