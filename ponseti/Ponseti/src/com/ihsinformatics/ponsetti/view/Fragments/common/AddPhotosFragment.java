package com.ihsinformatics.ponsetti.view.Fragments.common;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.ihsinformatics.ponsetti.R;
import com.ihsinformatics.ponsetti.database.data_access.FormDAO;
import com.ihsinformatics.ponsetti.database.data_access.PhotoDAO;
import com.ihsinformatics.ponsetti.database.pojos.Answer;
import com.ihsinformatics.ponsetti.database.pojos.Form;
import com.ihsinformatics.ponsetti.database.pojos.FormsTypes;
import com.ihsinformatics.ponsetti.database.pojos.Photo;
import com.ihsinformatics.ponsetti.screens.AddVisit;
import com.ihsinformatics.ponsetti.utils.DateSelector;
import com.ihsinformatics.ponsetti.utils.Tools;
import com.ihsinformatics.ponsetti.utils.ValidationFailureInformation;
import com.ihsinformatics.ponsetti.view.Fragments.MyFragment;

public class AddPhotosFragment extends MyFragment implements OnCheckedChangeListener, OnClickListener {

	TextView tvAddPhoto;
	LinearLayout llPhotoViewLeft, llPhotoViewRight;
	
	List<Form> forms;
	Form form;
	ArrayList<Bitmap> bitmapsList;
	boolean switchLayout;
	
	public AddPhotosFragment() {
		super();
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		switchLayout = true;
		bitmapsList = new ArrayList<Bitmap>();
		forms = new FormDAO(rootView.getContext()).getForms(Form.COLUMN_TYPE_ID+"= '"+FormsTypes.EVALUATOR_FORM+"'");
		
		tvAddPhoto = (TextView) rootView.findViewById(R.id.tvAddPhoto);
		tvAddPhoto.setOnClickListener(this);
		llPhotoViewLeft = (LinearLayout) rootView.findViewById(R.id.llPhotoViewLeft);
		llPhotoViewRight = (LinearLayout) rootView.findViewById(R.id.llPhotoViewRight);
		
		return rootView;
	}
	
	@Override
	public void onResume() {
		ViewTreeObserver vto = llPhotoViewLeft.getViewTreeObserver();
		vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
			
			@Override
			public void onGlobalLayout() {
				checkAndFillData();
				
			}
		});
		
		super.onResume();
	}
	
	@Override
	public List<Answer> getAnswers(int formId) {
		List<Answer> answers = new ArrayList<Answer>();
		answers.add(new Answer(tools.getViewText(tvAddPhoto), AddVisit.QUESTION_HOSPITAL_OR_CLINIC, formId));
		
		return answers;
	}
	
	@Override
	protected void checkAndFillData() {
		if(dataLoadRequest!= null && dataLoadRequest) {
			dataLoadRequest  = false;
			tvAddPhoto.setClickable(false);
			PhotoDAO photoDAO = new PhotoDAO(rootView.getContext());
			ArrayList<Photo> photosList = photoDAO.getPhotos(Photo.COLUMN_PARENT_NODE+"='"+form.getIcrId()+"'");
			Tools tools = Tools.getInstance();
			for(Photo photo : photosList) {
				addBitmap(tools.getBitmapFromEncodedContent(photo.getContent()));
			}
		}
	}
	
	@Override
	public void onClick(View v) {
		Intent i = new Intent(getActivity(), DateSelector.class);
		i.putExtra(DateSelector.DATE_TYPE, DateSelector.DATE_TYPE_DATE);
		if(v==tvAddPhoto) {
			Tools.getInstance().dispatchTakePictureIntent((Activity)rootView.getContext(), forms.toString());
		}
	}
	
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		
	}

	@Override
	public ValidationFailureInformation validate() {
		
		return null;
	}
	
	public void enableDataLoading(Form form) {
		this.form = form;
		dataLoadRequest = true;
	}
	
	public ArrayList<Bitmap> getPhotos() {
		
		return bitmapsList;
	}
	
	public void addBitmap(Bitmap bitmap) {
		bitmapsList.add(bitmap);
		ImageView imageView = new ImageView(getActivity());
		imageView.setBackgroundDrawable(getResources().getDrawable(R.drawable.drop_shadow));
		imageView.setImageBitmap(bitmap);
		if(switchLayout) {
			llPhotoViewLeft.addView(imageView);
			switchLayout = false;
		} else {
			llPhotoViewRight.addView(imageView);
			switchLayout = true;
		}
		
		LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, llPhotoViewLeft.getWidth());
		imageView.setLayoutParams(layoutParams);
	}
}
