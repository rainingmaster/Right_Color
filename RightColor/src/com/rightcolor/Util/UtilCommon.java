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
     * 获取SDCard的目录路径功能
     * @return
     */
	public static String getSDCardPath(){
		File sdcardDir = null;
		//判断SDCard是否存在
		boolean sdcardExist = Environment.getExternalStorageState().equals(android.os.Environment. MEDIA_MOUNTED);
		if(sdcardExist){
			sdcardDir = Environment.getExternalStorageDirectory();
		}
		return sdcardDir.getAbsolutePath();
	}
	
	/***
     * 图片的缩放方法
     * 
     * @param bgimage
     *            ：源图片资源
     * @param newWidth
     *            ：缩放后宽度
     * @param newHeight
     *            ：缩放后高度
     * @return
     */
    public static Bitmap zoomImage(Bitmap bgimage, double newWidth,
                    double newHeight) {
            // 获取这个图片的宽和高
            float width = bgimage.getWidth();
            float height = bgimage.getHeight();
            // 创建操作图片用的matrix对象
            Matrix matrix = new Matrix();
            // 计算宽高缩放率
            float scaleWidth = ((float) newWidth) / width;
            float scaleHeight = ((float) newHeight) / height;
            // 缩放图片动作
            matrix.postScale(scaleWidth, scaleHeight);
            Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
                            (int) height, matrix, true);
            return bitmap;
    }
    
   	/**采用jpeg格式将bitmap保存到本地
   	 * @param b 待存储bmp图片
   	 * @param strFileName  存储完整路径
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
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
    }

}
