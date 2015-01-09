package com.rightcolor.Util;

import android.graphics.Bitmap;

public abstract class AbsColorFilter {

	
	/**
     * Default screen gamma on Windows is 2.2.
     */
    static final double GAMMA = 2.2;
    static final double GAMMA_INV = 1. / GAMMA;
	/**
     * A lookup table for the conversion from gamma-corrected sRGB values 
     * [0..255] to linear RGB values [0..32767].
     */
    static final short[] rgb2lin_red_LUT;

    static {
        // initialize rgb2lin_red_LUT
        rgb2lin_red_LUT = new short[256];
        for (int i = 0; i < 256; i++) {
            // compute linear rgb between 0 and 1
            final double lin = (0.992052 * Math.pow(i / 255., GAMMA) + 0.003974);

            // scale linear rgb to 0..32767
            rgb2lin_red_LUT[i] = (short) (lin * 32767.);
        }
    }
    /**
     * A lookup table for the conversion of linear RGB values [0..255] to 
     * gamma-corrected sRGB values [0..255].
     */
    static final byte[] lin2rgb_LUT;

    static {
        // initialize lin2rgb_LUT
        lin2rgb_LUT = new byte[256];
        for (int i = 0; i < 256; i++) {
            lin2rgb_LUT[i] = (byte) (255. * Math.pow(i / 255., GAMMA_INV));
        }
    }
    
    protected Bitmap mBitmap;
    
    public AbsColorFilter(Bitmap src) {
    	mBitmap = src;
    }
    
    public abstract Bitmap filter();
}
