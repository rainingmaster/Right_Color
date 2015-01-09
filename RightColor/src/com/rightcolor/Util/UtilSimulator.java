/**
 * Thanks for github.com/nvkelso/color-oracle-java,the project help me a lots
 * And thanks for my girlfriend
 */
package com.rightcolor.Util;

import android.graphics.Bitmap;

public class UtilSimulator {
	
	/**
     * Enumerate the four possible states of the current simulation.
     */
    public enum Simulation {

        normal, deutan, protan, tritan
    }

	private AbsColorFilter clFiter;
	private Bitmap mSrcBitmap;
	private Bitmap mDstBitmap;
    private Simulation mSimulationType;
    
    public UtilSimulator() {
    	
    }
    
    public Bitmap getSrcBitmap() {
		return mSrcBitmap;
	}

	public void setSrcBitmap(Bitmap mSrcBitmap) {
		this.mSrcBitmap = mSrcBitmap;
	}
    
    public Bitmap getDstBitmap() {
		return mDstBitmap;
	}

	public Simulation getSimulationType() {
		return mSimulationType;
	}

	public void setSimulationType(Simulation mSimulationType) {
		this.mSimulationType = mSimulationType;
	}
    
    public Bitmap Simulator() {
    	if(mSrcBitmap != null){
	        switch (mSimulationType) {
	        case deutan:
	            this.clFiter = new RedGreenFilter(9591, 23173, -730, mSrcBitmap);
	        	mDstBitmap = clFiter.filter();
	            break;
	        case protan:
	        	this.clFiter = new RedGreenFilter(3683, 29084, 131, mSrcBitmap);
	        	mDstBitmap = clFiter.filter();
	            break;
	        case tritan:
	        	this.clFiter = new TritanFilter(mSrcBitmap);
	        	mDstBitmap = clFiter.filter();
	            break;
			default:
				break;
		    }
	        return mDstBitmap;
    	}
    	return null;
	}

}
