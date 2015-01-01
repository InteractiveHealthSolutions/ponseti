package com.ihsinformatics.ponsetti.screens;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ihsinformatics.ponsetti.R;
import com.ihsinformatics.ponsetti.database.data_access.AnswerDAO;
import com.ihsinformatics.ponsetti.database.data_access.FormDAO;
import com.ihsinformatics.ponsetti.database.data_access.PhotoDAO;
import com.ihsinformatics.ponsetti.database.pojos.Form;
import com.ihsinformatics.ponsetti.database.pojos.FormsTypes;
import com.ihsinformatics.ponsetti.database.pojos.Photo;
import com.ihsinformatics.ponsetti.utils.BitmapHolder;
import com.ihsinformatics.ponsetti.utils.Tools;
import com.ihsinformatics.ponsetti.utils.exceptions.BitmapConsumedException;
import com.ihsinformatics.ponsetti.view.PatientView;

public class ViewPatients extends Activity {

	private LinearLayout llMAin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_patients);

		llMAin = (LinearLayout) findViewById(R.id.llMain);

		List<Form> forms = new FormDAO(this).getForms(Form.COLUMN_TYPE_ID+"= '"+FormsTypes.PATIENT_FORM+"'");
		AnswerDAO answerDAO = new AnswerDAO(this);

		for (Form form : forms) {
			llMAin.addView(new PatientView(this, form, answerDAO.getPatientName(form.getFormId())));
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == Tools.REQUEST_TAKE_PHOTO) {
			Bitmap bitmap;
			try {
				bitmap = BitmapHolder.getinstance().consumeBitmaps(getClass().getName()).get(0);
			
				if (bitmap != null) {
					String icrId = data.getStringExtra(PatientView.ICR_ID);
					String photoContent = Tools.getInstance().getBitmapEncodedContent(bitmap);
					Photo photo = new Photo(photoContent, 0, icrId);
					new PhotoDAO(this).insert(photo);
					Toast.makeText(getApplicationContext(), "Picture taken and saved", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(this, "Picture not taken", Toast.LENGTH_SHORT).show();;
				}
			} catch (BitmapConsumedException e) {
				Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
				e.printStackTrace();
			}
		}
	}
}
