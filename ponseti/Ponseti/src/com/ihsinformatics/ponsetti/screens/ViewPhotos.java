package com.ihsinformatics.ponsetti.screens;

import android.app.Activity;
import android.os.Bundle;

import com.ihsinformatics.ponsetti.R;
import com.ihsinformatics.ponsetti.database.pojos.Form;
import com.ihsinformatics.ponsetti.utils.interfaces.OnFormSelectedListener;
import com.ihsinformatics.ponsetti.view.Fragments.manage_photos.PhotosContainerFragment;
import com.ihsinformatics.ponsetti.view.Fragments.manage_photos.PhotosFilterFragment;

public class ViewPhotos extends Activity implements OnFormSelectedListener  {

	PhotosContainerFragment photosContainerFragment;
	PhotosFilterFragment photosFilterFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_photos);
		photosContainerFragment = (PhotosContainerFragment) getFragmentManager().findFragmentById(R.id.fragmentPhotoContainer);
		photosFilterFragment = (PhotosFilterFragment) getFragmentManager().findFragmentById(R.id.fragmentPhotoFilter);
	}

	@Override
	public void onFormSelected(Form form) {
		photosContainerFragment.onFormSelected(form);
	}
	
	

}
