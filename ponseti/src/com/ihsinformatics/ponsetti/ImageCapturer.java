package com.ihsinformatics.ponsetti;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ihsinformatics.ponsetti.screens.ViewPatients;
import com.ihsinformatics.ponsetti.utils.ShowCamera;

public class ImageCapturer extends Activity {


	public static final String RESULT_IMAGE_BITMAP = "image_data";
	
	private Camera cameraObject;
	private ShowCamera showCamera;
	
	public Camera isCameraAvailiable() {
		Camera object = null;
		try {
			if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
				object = Camera.open();
			} else {
				int frontCameraId = findFrontCamera();
				if (frontCameraId != -1) {
					object = Camera.open(frontCameraId);
				} else {
					Toast.makeText(getApplicationContext(), "No camera found on this device", Toast.LENGTH_SHORT).show();
				}
			}

		} catch (Exception e) {
		}
		return object;
	}
	
	
	private PictureCallback capturedIt = new PictureCallback() {

		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			cameraObject.release();
			Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
			if (bitmap == null) {
				Toast.makeText(getApplicationContext(), "Picture not taken", Toast.LENGTH_SHORT).show();
			} else {
				ViewPatients.setCAPTURED_BITMAP(bitmap);
				setResult(RESULT_OK, getIntent());
				finish();
			}
			
			
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_capturer);
		cameraObject = isCameraAvailiable();
		showCamera = new ShowCamera(this, cameraObject);
		LinearLayout preview = (LinearLayout) findViewById(R.id.llCameraPreview);
		preview.addView(showCamera);
	}

	public void snapIt(View view) {
		cameraObject.takePicture(null, null, capturedIt);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private int findFrontCamera() {
		int fronCameraId = -1;
		int numberOfCameras = Camera.getNumberOfCameras();
		for (int i = 0; i < numberOfCameras; i++) {
			CameraInfo info = new CameraInfo();
			Camera.getCameraInfo(i, info);
			if (info.facing == CameraInfo.CAMERA_FACING_FRONT) {
				fronCameraId = i;
				break;
			}
		}
		return fronCameraId;
	}
}
