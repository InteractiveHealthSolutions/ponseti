package com.ihsinformatics.ponsetti.screens;

import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ihsinformatics.ponsetti.R;
import com.ihsinformatics.ponsetti.ViewVisitsActivity;
import com.ihsinformatics.ponsetti.database.data_access.AnswerDAO;
import com.ihsinformatics.ponsetti.database.data_access.FormDAO;
import com.ihsinformatics.ponsetti.database.data_access.HospitalDAO;
import com.ihsinformatics.ponsetti.database.data_access.PhotoDAO;
import com.ihsinformatics.ponsetti.export.ExportActivity;
import com.ihsinformatics.ponsetti.network.FormsGetter;
import com.ihsinformatics.ponsetti.network.FormsUploader;
import com.ihsinformatics.ponsetti.network.PhotosUploader;
import com.ihsinformatics.ponsetti.network.ServerCommunicationAdapter;
import com.ihsinformatics.ponsetti.network.ServerCommunicator;
import com.ihsinformatics.ponsetti.parser.FormParser;
import com.ihsinformatics.ponsetti.utils.Global;
import com.ihsinformatics.ponsetti.utils.enums.OperationType;
import com.ihsinformatics.ponsetti.utils.interfaces.OnDataParsedListener;
import com.ihsinformatics.ponsetti.utils.interfaces.OnOperationFinishedListener;
import com.ihsinformatics.ponsetti.view.NetworkProgressDialog;

public class MainMenu extends Activity implements OnClickListener, OnTouchListener, ServerCommunicationAdapter, OnOperationFinishedListener, OnDataParsedListener {
	
	ImageView btnViewPatient, btnViewEvaluator, btnAddPatient, btnAddEvaluator,
		btnTrainingVideos, btnMyAccount, btnDownload, btnUpload, btnExport;
	TextView tvBadge;
	NetworkProgressDialog dialog;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		tvBadge = (TextView) findViewById(R.id.tvBadge);
		btnViewPatient = (ImageView) findViewById(R.id.btnViewPatients);
		btnAddPatient = (ImageView) findViewById(R.id.btnAddPatient);
		btnAddEvaluator = (ImageView) findViewById(R.id.btnAddEvaluator);
		btnViewEvaluator = (ImageView) findViewById(R.id.btnViewEvaluators);
		btnTrainingVideos = (ImageView) findViewById(R.id.btnTrainingVideos);
		btnMyAccount = (ImageView) findViewById(R.id.btnMyAccount);
		btnUpload = (ImageView) findViewById(R.id.btnUpload);
		btnDownload = (ImageView) findViewById(R.id.btnDownload);
		btnExport = (ImageView) findViewById(R.id.btnExport);
		
		btnViewPatient.setOnClickListener(this);
		btnViewEvaluator.setOnClickListener(this);
		btnAddPatient.setOnClickListener(this);
		btnAddEvaluator.setOnClickListener(this);
		btnTrainingVideos.setOnClickListener(this);
		btnMyAccount.setOnClickListener(this);
		btnUpload.setOnClickListener(this);
		btnDownload.setOnClickListener(this);
		btnExport.setOnClickListener(this);
		
		btnViewPatient.setOnTouchListener(this);
		btnViewEvaluator.setOnTouchListener(this);
		btnAddPatient.setOnTouchListener(this);
		btnAddEvaluator.setOnTouchListener(this);
		btnTrainingVideos.setOnTouchListener(this);
		btnMyAccount.setOnTouchListener(this);
		btnUpload.setOnTouchListener(this);
		btnDownload.setOnTouchListener(this);
		btnExport.setOnTouchListener(this);
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		int numberOfUploadableForms = new FormDAO(this).getNumberOfUploadableForms();
		int numberOfUploadablePhotos = new PhotoDAO(this).getUploadablePhoto().size();
		int totalUploadables = numberOfUploadableForms+numberOfUploadablePhotos;
		if(totalUploadables >0) {
			int iw = btnUpload.getWidth();
			int iW = btnUpload.getDrawable().getIntrinsicWidth();
			btnUpload.setImageResource(R.drawable.upload_forms);
			btnUpload.setClickable(true);
			RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			layoutParams.setMargins(iW, 10, 0, 0);
			tvBadge.setLayoutParams(layoutParams);
			tvBadge.setVisibility(View.VISIBLE);
			tvBadge.setText(totalUploadables+"");
		} else {
			tvBadge.setVisibility(View.GONE);
			btnUpload.setImageResource(R.drawable.upload_forms_disabled);btnUpload.setClickable(false);
		}
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onClick(View v) {
		if(v == btnViewPatient) {
			startActivity(new Intent(MainMenu.this, ViewPatients.class));
		} else if(v == btnAddPatient) {
			startActivity(new Intent(MainMenu.this, PatientPermissionsDialog.class));
		} else if(v == btnAddEvaluator) {
			startActivity(new Intent(MainMenu.this, CreateEvaluator.class));
		} else if(v == btnViewEvaluator) {
			startActivity(new Intent(MainMenu.this, ViewEvaluators.class));
		} else if(v == btnTrainingVideos) {
			
		} else if(v == btnMyAccount) {
			startActivity(new Intent(MainMenu.this, ViewVisitsActivity.class));
		} else if(v == btnDownload) {
		
			dialog = new NetworkProgressDialog(this, "Getting hospitals list...");
			dialog.show();
			new ServerCommunicator(this, this, 0, Global.GET_HOSPITALS).execute(Global.CLIENT_KEY);
		} else if(v== btnExport) {
		
			startActivity(new Intent(this, ExportActivity.class));
		} else if(v==btnUpload) {
			dialog = new NetworkProgressDialog(this, "Uploading forms...");
			dialog.show();
			new FormsUploader(this, this).upload();
		}
		
	}

	@Override
	public boolean onTouch(View view, MotionEvent event) {
		int action = event.getAction();
		ImageView v = (ImageView) view;
		if(action == MotionEvent.ACTION_DOWN) {
			if(v == btnViewPatient) {
				v.setImageResource(R.drawable.view_patients_colored);
			} else if(v == btnAddPatient) {
				v.setImageResource(R.drawable.add_new_patient_colored);
			} else if(v == btnViewEvaluator) {
				v.setImageResource(R.drawable.view_evaluator_colored);
			} else if(v == btnAddEvaluator) {
				v.setImageResource(R.drawable.add_evaluator_colored);
			} else if(v == btnTrainingVideos) {
				v.setImageResource(R.drawable.training_video_colored);
			} else if(v == btnMyAccount) {
				v.setImageResource(R.drawable.my_account_colored);
			} else if(v==btnUpload) {
				v.setImageResource(R.drawable.upload_forms_hovered);
			} else if(v==btnDownload) {
				v.setImageResource(R.drawable.download_forms_hovered);
			} else if(v==btnExport) {
				v.setImageResource(R.drawable.export_hovered);
			}
		} else if(action == MotionEvent.ACTION_UP) {
			if(v == btnViewPatient) {
				v.setImageResource(R.drawable.view_patients);
			} else if(v == btnAddPatient) {
				v.setImageResource(R.drawable.add_new_patient);
			} else if(v == btnViewEvaluator) {
				v.setImageResource(R.drawable.view_evaluator);
			} else if(v == btnAddEvaluator) {
				v.setImageResource(R.drawable.add_evaluator);
			} else if(v == btnTrainingVideos) {
				v.setImageResource(R.drawable.training_video);
			} else if(v == btnMyAccount) {
				v.setImageResource(R.drawable.my_account);
			} else if(v==btnUpload) {
				v.setImageResource(R.drawable.upload_forms);
			} else if(v==btnDownload) {
				v.setImageResource(R.drawable.download_forms);
			} else if(v==btnExport) {
				v.setImageResource(R.drawable.export);
			}
		}
		
		return false;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void xmlrpcCallResponse(Object res, int requestId) {
		
		switch (requestId) {
		case 0:
			HospitalDAO hospitalDAO = new HospitalDAO();
			
			Object[] fetchesHospitals = (Object[]) res;
			List<String> currentHospitals = hospitalDAO.getHospitalsNames(this);
			String tempHospital;
			for(Object fetchedHospital:fetchesHospitals) {
				tempHospital = (String) fetchedHospital;
				if(!currentHospitals.contains(tempHospital)) {
					hospitalDAO.insert(this, tempHospital);
				}
			}
			
			dialog.setMessage("Getting forms list...");
			new ServerCommunicator(this, this, 1, Global.LIST_LATEST_FORMS_FOR_HOSPITALS).execute(Global.CLIENT_KEY, new HospitalDAO().getHospitalsNames(this));
			
			break;
			
		case 1:
			// Server sends HashMap of forms when we call Global.LIST_LATEST_FORMS_FOR_HOSPITALS
			if(res instanceof HashMap<?, ?>) {
				setDialogText("Downloading forms...\n0% done");
				new FormsGetter((HashMap<String, String>) res, this).start(this);
				
			} else {
				
			}
			break;
			
		default:
			break;
		}
		
	}

	@Override
	public void onDataParsed(List<FormParser> parsedForms, int t) {
		dialog.dismiss();
		if(parsedForms.size() > 0) {
			AnswerDAO answerDAO =  new AnswerDAO(this);
			for(FormParser parsedForm: parsedForms) {
				if(parsedForm.getTag() == FormParser.TAG_NEW) {
					answerDAO.insert(parsedForm.parse().getAnswers());
				} else if(parsedForm.getTag() == FormParser.TAG_EXISTING) {
					answerDAO.update(parsedForm.parse().getAnswers());
				}
			}
		}
	}
	
	public void setDialogText(String message) {
		dialog.setMessage(message);
	}

	@Override
	public void xmlrpcCallResponse(Object res, String requestId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onOperationFinished(OperationType operationType) {
		
		if(operationType== OperationType.FORMS_UPLOAD) {
			new PhotosUploader(this, this).upload();
			if(dialog.isShowing()) {
				dialog.setMessage("Uploading photos...");
			}
		} else if(operationType== OperationType.PHOTOS_UPLOAD) {
			if(dialog.isShowing()) {
				dialog.dismiss();
				onResume();
			}
		}
		
	}

}
