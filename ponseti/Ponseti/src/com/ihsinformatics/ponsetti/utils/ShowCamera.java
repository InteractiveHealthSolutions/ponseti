package com.ihsinformatics.ponsetti.utils;

import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class ShowCamera extends SurfaceView implements SurfaceHolder.Callback {

   private SurfaceHolder holdMe;
   private Camera camera;
   Context context;

   public ShowCamera(Context context,Camera camera) {
      super(context);
      this.context = context;
      this.camera = camera;
      holdMe = getHolder();
      
      holdMe.addCallback(this);
   }
   /*List<Size> supportedPreviewSizes;
   Size previewSize;*/
   @Override
   protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
      /* final int width = resolveSize(getSuggestedMinimumWidth(), widthMeasureSpec);
       final int height = resolveSize(getSuggestedMinimumHeight(), heightMeasureSpec);
       setMeasuredDimension(width, height);
       supportedPreviewSizes = camera.getParameters().getSupportedPreviewSizes();
       if (supportedPreviewSizes != null) {
          previewSize = getOptimalPreviewSize(supportedPreviewSizes, width, height);
       }*/
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
   }
   @Override
   public void surfaceCreated(SurfaceHolder holder) {
      try   {
    	  camera.setPreviewDisplay(holder);
    	  camera.startPreview(); 
    	  
    	  /*Camera.Parameters parameters = camera.getParameters();
    	  parameters.setPreviewSize(previewSize.width, previewSize.height);
    	  camera.setParameters(parameters);
    	  camera.startPreview();*/
    	  
    	  
    	  
      } catch (IOException e) {
      }
   }
   @Override
   public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
   	// TODO Auto-generated method stub
   	
   }
   @Override
   public void surfaceDestroyed(SurfaceHolder arg0) {
   }


/*private Camera.Size getOptimalPreviewSize(List<Camera.Size> sizes, int w, int h) {
    final double ASPECT_TOLERANCE = 0.1;
    double targetRatio=(double)h / w;

    if (sizes == null) return null;

    Camera.Size optimalSize = null;
    double minDiff = Double.MAX_VALUE;

    int targetHeight = h;

    for (Camera.Size size : sizes) {
        double ratio = (double) size.width / size.height;
        if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE) continue;
        if (Math.abs(size.height - targetHeight) < minDiff) {
            optimalSize = size;
            minDiff = Math.abs(size.height - targetHeight);
        }
    }

    if (optimalSize == null) {
        minDiff = Double.MAX_VALUE;
        for (Camera.Size size : sizes) {
            if (Math.abs(size.height - targetHeight) < minDiff) {
                optimalSize = size;
                minDiff = Math.abs(size.height - targetHeight);
            }
        }
    }
    return optimalSize;
}*/
}