package com.ihsinformatics.ponsetti.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.ihsinformatics.ponsetti.utils.exceptions.BitmapConsumedException;

import android.graphics.Bitmap;

public class BitmapHolder implements Serializable {

	/**
	 * Generated serialVersionUID
	 */
	private static final long serialVersionUID = -4193964431587206272L;
	private List<Bitmap> bitmaps;
	private static BitmapHolder instance;
	private String consumer;
	private int clickedItemIndex;
	
	private BitmapHolder() {
	}
	
	public static BitmapHolder getinstance() {
		if(instance == null) {
			instance = new BitmapHolder();
		}
		
		return instance;
	}

	public List<Bitmap> consumeBitmaps(String consumer) throws BitmapConsumedException {
		if (bitmaps == null) {
			throw new BitmapConsumedException("Bitmap is already consumed by class" + this.consumer);
		}
		
		this.consumer = consumer;
		List<Bitmap> temp = bitmaps;
		bitmaps = null;
		return temp;
	}

	public BitmapHolder addBitmap(Bitmap bitmap) {
		if(bitmaps==null) {
			bitmaps = new ArrayList<Bitmap>();
		}
		this.bitmaps.add(bitmap);
		
		return this;
	}
	
	public BitmapHolder setBitmaps(List<Bitmap> bitmaps) {
		this.bitmaps = bitmaps;
		
		return this;
	}
	
	public BitmapHolder setClickedItemIndex(int clickedItemIndex) {
		this.clickedItemIndex = clickedItemIndex;
		
		return this;
	}
	
	public int getClickedItemIndex() {
		
		return clickedItemIndex;
	}
}
