package com.rightcolor.Util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.Surface;

public class UtilCommon {	
    /**
     * ��ȡSDCard��Ŀ¼·������
     * @return
     */
	public static String getSDCardPath(){
		File sdcardDir = null;
		//�ж�SDCard�Ƿ����
		boolean sdcardExist = Environment.getExternalStorageState().equals(android.os.Environment. MEDIA_MOUNTED);
		if(sdcardExist){
			sdcardDir = Environment.getExternalStorageDirectory();
		}
		return sdcardDir.getAbsolutePath();
	}
	
	/***
     * ͼƬ�����ŷ���
     * 
     * @param bgimage
     *            ��ԴͼƬ��Դ
     * @param newWidth
     *            �����ź���
     * @param newHeight
     *            �����ź�߶�
     * @return
     */
    public static Bitmap zoomImage(Bitmap bgimage, double newWidth,
                    double newHeight) {
            // ��ȡ���ͼƬ�Ŀ�͸�
            float width = bgimage.getWidth();
            float height = bgimage.getHeight();
            // ��������ͼƬ�õ�matrix����
            Matrix matrix = new Matrix();
            // ������������
            float scaleWidth = ((float) newWidth) / width;
            float scaleHeight = ((float) newHeight) / height;
            // ����ͼƬ����
            matrix.postScale(scaleWidth, scaleHeight);
            Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
                            (int) height, matrix, true);
            return bitmap;
    }
    
   	/**����jpeg��ʽ��bitmap���浽����
   	 * @param b ���洢bmpͼƬ
   	 * @param strFileName  �洢����·��
   	 */
    public static void saveBitmap (Bitmap b, String sFilePath) {
    	FileOutputStream fos = null;  
        try {
        	File f = new File(sFilePath);
            if (!f.getParentFile().exists()) {
            	f.getParentFile().mkdirs();
            }
            fos = new FileOutputStream(f);
            if (null != fos)  
            {  
                b.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.flush();  
                fos.close();  
            }
		} catch (FileNotFoundException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
    }

}
