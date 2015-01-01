package com.ihsinformatics.ponsetti.view.Fragments.manage_photos;

import java.util.ArrayList;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.ihsinformatics.ponsetti.R;
import com.ihsinformatics.ponsetti.database.data_access.PhotoDAO;
import com.ihsinformatics.ponsetti.database.pojos.Form;
import com.ihsinformatics.ponsetti.database.pojos.Photo;
import com.ihsinformatics.ponsetti.screens.PhotoViewer;
import com.ihsinformatics.ponsetti.utils.BitmapHolder;
import com.ihsinformatics.ponsetti.utils.Tools;
import com.ihsinformatics.ponsetti.utils.interfaces.OnFormSelectedListener;


public class PhotosContainerFragment extends Fragment implements OnFormSelectedListener {
	
	private ArrayList<Bitmap> bitmapsList;
	private int switchLayout;
	private LinearLayout llPhotoViewLeft, /*llPhotoViewCenter,*/ llPhotoViewRight;
	
   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	  View rootView = inflater.inflate(R.layout.fragment_photos_container, container, false);
      
	  llPhotoViewLeft = (LinearLayout) rootView.findViewById(R.id.llPhotoViewLeft);
	  // llPhotoViewCenter = (LinearLayout) rootView.findViewById(R.id.llPhotoViewCenter);
	  llPhotoViewRight = (LinearLayout) rootView.findViewById(R.id.llPhotoViewRight);
	  
      return rootView;
   }

	@Override
	public void onFormSelected(Form form) {
		llPhotoViewLeft.removeAllViewsInLayout();
		llPhotoViewRight.removeAllViewsInLayout();
		if(form!=null) {
			bitmapsList = new ArrayList<Bitmap>();
			switchLayout = 0;
			PhotoDAO photoDAO = new PhotoDAO(getActivity());
			ArrayList<Photo> photosList = photoDAO.getPhotos(Photo.COLUMN_PARENT_NODE+"='"+form.getIcrId()+"'");
			Tools tools = Tools.getInstance();
			for(Photo photo : photosList) {
				addBitmap(tools.getBitmapFromEncodedContent(photo.getContent()));
			}
		} else {
			
		}
	}
	
	public void addBitmap(Bitmap bitmap) {
		bitmapsList.add(bitmap);
		ImageView imageView = new ImageView(getActivity());
		imageView.setTag(bitmapsList.size()-1);
		imageView.setBackgroundDrawable(getResources().getDrawable(R.drawable.drop_shadow));
		imageView.setImageBitmap(bitmap);
		if(switchLayout == 0) {
			llPhotoViewLeft.addView(imageView);
			switchLayout = 1;
		} else if(switchLayout == 1) {
			llPhotoViewRight.addView(imageView);
			switchLayout = 0;
		}
		
		imageView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// BitmapHolder.getinstance().addBitmap(((BitmapDrawable)((ImageView)v).getDrawable()).getBitmap()); For viewing single image
				BitmapHolder.getinstance().setClickedItemIndex((Integer)v.getTag()).setBitmaps(bitmapsList);
				startActivity(new Intent(getActivity(), PhotoViewer.class));
				
			}
		});
		
		LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, llPhotoViewLeft.getWidth());
		imageView.setLayoutParams(layoutParams);
	}
}
