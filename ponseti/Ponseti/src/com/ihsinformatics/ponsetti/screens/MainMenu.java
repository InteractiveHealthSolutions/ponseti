package com.ihsinformatics.ponsetti.screens;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import au.com.bytecode.opencsv.CSVWriter;

import com.ihsinformatics.ponsetti.R;
import com.ihsinformatics.ponsetti.database.data_access.AnswerDAO;
import com.ihsinformatics.ponsetti.database.data_access.FormDAO;
import com.ihsinformatics.ponsetti.database.data_access.HospitalDAO;
import com.ihsinformatics.ponsetti.database.data_access.PhotoDAO;
import com.ihsinformatics.ponsetti.database.pojos.Form;
import com.ihsinformatics.ponsetti.database.pojos.FormsTypes;
import com.ihsinformatics.ponsetti.network.FormsGetter;
import com.ihsinformatics.ponsetti.network.FormsUploader;
import com.ihsinformatics.ponsetti.network.PhotosUploader;
import com.ihsinformatics.ponsetti.network.ServerCommunicationAdapter;
import com.ihsinformatics.ponsetti.network.ServerCommunicator;
import com.ihsinformatics.ponsetti.parser.FormParser;
import com.ihsinformatics.ponsetti.utils.Global;
import com.ihsinformatics.ponsetti.utils.Tools;
import com.ihsinformatics.ponsetti.utils.enums.OperationType;
import com.ihsinformatics.ponsetti.utils.interfaces.OnDataParsedListener;
import com.ihsinformatics.ponsetti.utils.interfaces.OnOperationFinishedListener;
import com.ihsinformatics.ponsetti.view.NetworkProgressDialog;

public class MainMenu extends Activity implements OnClickListener, OnTouchListener, ServerCommunicationAdapter, OnOperationFinishedListener, OnDataParsedListener {
	
	ImageView btnMenu;
	TextView btnViewPatient, btnViewEvaluator, btnAddEvaluator,
	btnTrainingVideos, btnViewVisits, tvBadge, btnDownload, btnUpload, btnAddPatient;
	NetworkProgressDialog dialog;
	PopupMenu pmUpload, pmMenu;
	MenuItem miUploadForms, miUploadPhotos;
	LinearLayout llInformationBar;
	Tools tools;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		tools = Tools.getInstance();
		tvBadge = (TextView) findViewById(R.id.tvBadge);
		llInformationBar = (LinearLayout) findViewById(R.id.llInformationBar);
		btnViewPatient = (TextView) findViewById(R.id.btnViewPatients);
		btnAddPatient = (TextView) findViewById(R.id.btnAddPatient);
		btnAddEvaluator = (TextView) findViewById(R.id.btnAddEvaluator);
		btnViewEvaluator = (TextView) findViewById(R.id.btnViewEvaluators);
		btnTrainingVideos = (TextView) findViewById(R.id.btnTrainingVideos);
		btnViewVisits = (TextView) findViewById(R.id.btnMyAccount);
		btnUpload = (TextView) findViewById(R.id.btnUpload);
		btnDownload = (TextView) findViewById(R.id.btnDownload);
		btnMenu = (ImageView) findViewById(R.id.btnExport);
		btnViewPatient.setOnClickListener(this);
		btnViewEvaluator.setOnClickListener(this);
		btnAddPatient.setOnClickListener(this);
		btnAddEvaluator.setOnClickListener(this);
		btnTrainingVideos.setOnClickListener(this);
		btnViewVisits.setOnClickListener(this);
		btnUpload.setOnClickListener(this);
		btnDownload.setOnClickListener(this);
		btnMenu.setOnClickListener(this);
		
		btnViewPatient.setOnTouchListener(this);
		btnViewEvaluator.setOnTouchListener(this);
		btnAddPatient.setOnTouchListener(this);
		btnAddEvaluator.setOnTouchListener(this);
		btnTrainingVideos.setOnTouchListener(this);
		btnViewVisits.setOnTouchListener(this);
		btnUpload.setOnTouchListener(this);
		btnDownload.setOnTouchListener(this);
		btnMenu.setOnTouchListener(this);
		
		pmUpload = new PopupMenu(MainMenu.this, btnUpload);
		pmUpload.getMenuInflater().inflate(R.menu.upload_menu, pmUpload.getMenu());
          
        pmUpload.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {  
              	if(item.getItemId() == R.id.action_upload_froms) {
              		uploadForms();
              	} else if(item.getItemId() == R.id.action_upload_phosts) {
              		uploadPhotos();
              	}
          		return true;  
            }
      });
        
        
        pmMenu = new PopupMenu(MainMenu.this, btnMenu);
        pmMenu.getMenuInflater().inflate(R.menu.export_menu, pmMenu.getMenu());
          
        pmMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
        	List<String[]> data = null;
            public boolean onMenuItemClick(MenuItem item) {
            	String fileName = item.getTitle()+".csv";
              	if(item.getItemId() == R.id.action_export_patient_forms) {
              		data = new AnswerDAO(MainMenu.this).getAnswers(FormsTypes.PATIENT_FORM);
              		
              	} else if(item.getItemId() == R.id.action_export_visit_forms) {
              		data = new AnswerDAO(MainMenu.this).getAnswers(FormsTypes.VISIT_FORM);
              	} else if(item.getItemId() == R.id.action_export_evaluator_forms) {
              		data = new AnswerDAO(MainMenu.this).getAnswers(FormsTypes.EVALUATOR_FORM);
              	} else if(item.getItemId() == R.id.action_export) {
              		
              		return true;
              	} else if(item.getItemId() == R.id.action_logout) {
              		PreferenceManager.getDefaultSharedPreferences(MainMenu.this).edit().putString("client_key", null).apply();
              		startActivity(new Intent(MainMenu.this, Login.class));
              		finish();
              		return true;
              	} else if(item.getItemId() == R.id.action_exit) {
              		finish();
              		return true;
              	}
              	try {
					export(data, fileName);
				} catch (IOException e) {
					e.printStackTrace();
					return false;
				}
          		return true;  
            }
      });
        
        miUploadForms = pmUpload.getMenu().getItem(0);
		miUploadPhotos = pmUpload.getMenu().getItem(1);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		displayUploadInformation();
		super.onWindowFocusChanged(hasFocus);
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
			startActivity(new Intent(MainMenu.this, ViewPhotos.class));
		} else if(v == btnViewVisits) {
			startActivity(new Intent(MainMenu.this, ViewVisitsActivity.class));
		} else if(v == btnDownload) {
		
			dialog = new NetworkProgressDialog(this, "Getting hospitals list...");
			dialog.show();
			new ServerCommunicator(this, this, 0, Global.GET_HOSPITALS).execute(Global.CLIENT_KEY);
		} else if(v== btnMenu) {
			pmMenu.show();
		} else if(v==btnUpload) {
          pmUpload.show();
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		int action = event.getAction();
		
		if(v.isClickable()) {
			if(action == MotionEvent.ACTION_DOWN) {
				if(v==btnMenu) {
					((ImageView)v).setBackgroundColor(getResources().getColor(R.color.gray_light_dbdbdb));
				}
			} else if(action == MotionEvent.ACTION_UP) {
				if(v==btnMenu) {
					((ImageView)v).setBackgroundColor(getResources().getColor(android.R.color.transparent));
				}
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
				List<Form> downloadableForms = findDownloadableForms((HashMap<String, String>) res);
				new FormsGetter(downloadableForms, this).start(this);
				
			} else {
				
			}
			break;
			
		default:
			break;
		}
		
	}
	
	@Override
	public void xmlrpcCallResponse(Object res, String requestId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDataParsed(List<Form> processedForms, List<FormParser> parsedForms, int t) {
		dialog.dismiss();
	}
	
	public void setDialogText(String message) {
		dialog.setMessage(message);
	}

	

	@Override
	public void onOperationFinished(OperationType operationType) {
		llInformationBar.setVisibility(View.GONE);
		displayUploadInformation();
		if(operationType== OperationType.FORMS_UPLOAD) {
			
		} else if(operationType== OperationType.PHOTOS_UPLOAD) {
			
		}
		
	}
	
	private void uploadForms() {
		llInformationBar.setVisibility(View.VISIBLE);
		new FormsUploader(MainMenu.this, MainMenu.this).upload();
	}
	
	private void uploadPhotos() {
		llInformationBar.setVisibility(View.VISIBLE);
		new PhotosUploader(this, this).upload();
	}
	
	private void export(List<String[]> data, String fileName) throws IOException {
		if(data!=null && data.size()>0) {
			File exportDir = new File(Environment.getExternalStorageDirectory(), "DCIM/Ponseti");
			if (!exportDir.exists()) {
				exportDir.mkdirs();
			}
		
			File file = new File(exportDir, fileName);
		
			file.createNewFile();
			CSVWriter csvWrite = new CSVWriter(new FileWriter(file));
		
			csvWrite.writeAll(data);
		
			csvWrite.close();
			Toast.makeText(this, "data exported successfully to "+exportDir.getPath()+"/"+fileName, Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(this, "No data found", Toast.LENGTH_LONG).show();
		}
	}
	
	private void displayUploadInformation() {
		int numberOfUploadableForms = new FormDAO(this).getNumberOfUploadableForms();
		int numberOfUploadablePhotos = new PhotoDAO(this).getReadyToUploadPhotos().size();
		miUploadForms.setTitle(getString(R.string.action_upload_forms)+" ("+numberOfUploadableForms+")");
		miUploadPhotos.setTitle(getString(R.string.action_upload_photos)+" ("+numberOfUploadablePhotos+")");
		
		if(numberOfUploadableForms == 0) {
			miUploadForms.setEnabled(false);
		} else {
			miUploadForms.setEnabled(true);
		}
		
		if(numberOfUploadablePhotos == 0) {
			miUploadPhotos.setEnabled(false);
		} else {
			miUploadPhotos.setEnabled(true);
		}
		
		int totalUploadables = numberOfUploadableForms+numberOfUploadablePhotos;
		if(totalUploadables >0) {
			int iw = btnUpload.getWidth();
			tools.changeTextViewBackGround(btnUpload, getResources().getDrawable(R.drawable.state_list_light_green_shadow_bottom));
			btnUpload.setClickable(true);
			btnUpload.setTouchDelegate(null);
			String sTotalUploadables = totalUploadables+"";
			tvBadge.setText(sTotalUploadables);
			tvBadge.setVisibility(View.VISIBLE);
			RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			layoutParams.setMargins(iw-10, 0, 0, 0);
			tvBadge.setLayoutParams(layoutParams);
		} else {
			tvBadge.setVisibility(View.GONE);
			tools.changeTextViewBackGround(btnUpload, getResources().getDrawable(R.drawable.layer_list_disabled));
			btnUpload.setClickable(false);
		}
	}
	
	private List<Form> findDownloadableForms(HashMap<String, String> formsList) {
		List<Form> downloadableForms = new ArrayList<Form>();
		Iterator<String> itr = formsList.keySet().iterator();
		String temp;
		FormDAO formDAO = new FormDAO(this);
		while(itr.hasNext()) {
			temp = itr.next();
			
			Form form = formDAO.getForm(Form.COLUMN_ICR_ID+" = '"+temp+"' and "+Form.COLUMN_TIME_STAMP +"< "+Integer.parseInt(formsList.get(temp)));			
			if(form != null) {
				form.setTimeStamp(Integer.parseInt(formsList.get(temp)));
				downloadableForms.add(form);
				
			} else {
				form = formDAO.getForm(Form.COLUMN_ICR_ID+" = '"+temp+"'");
				if(form == null) {
					downloadableForms.add(new Form(temp, Integer.parseInt(formsList.get(temp)), null, true));
				}
			}
		}
		
		return downloadableForms;
	}

}
