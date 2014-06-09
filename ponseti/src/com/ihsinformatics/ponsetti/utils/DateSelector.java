package com.ihsinformatics.ponsetti.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TimePicker;

import com.ihsinformatics.ponsetti.R;

public class DateSelector extends Activity implements OnClickListener {

	public final static int DATE_TYPE_DATE = 0;
	public final static int DATE_TYPE_DATE_TIME = 1;
	public final static int DATE_TYPE_TIME = 2;
	public final static String DATE_TYPE = "content";
	Button btnSubmit;
	DatePicker datePicker;
	TimePicker timePicker;
	int yr;
	String cYr;
	final String[] dayInMonth = { "01", "02", "03", "04", "05", "06", "07",
			"08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18",
			"19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29",
			"30", "31" };

	final String[] month = { "01", "02", "03", "04", "05", "06", "07", "08",
			"09", "10", "11", "12" };
	
	int dialogType;
	String selectedTime;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_date_selector);

		btnSubmit = (Button) findViewById(R.id.btnSubmit);
		datePicker = (DatePicker) findViewById(R.id.datePicker);
		
		
		btnSubmit.setOnClickListener(this);
		
		Intent i = getIntent();
		dialogType = i.getIntExtra(DATE_TYPE, 0);
		if (DATE_TYPE_DATE_TIME==dialogType || DATE_TYPE_TIME == dialogType){
			((LinearLayout)findViewById(R.id.llDateTime)).setVisibility(View.VISIBLE);
			timePicker = (TimePicker) findViewById(R.id.timePicker);
			timePicker.setIs24HourView(true);
			timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
				
				public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
					if(minute > 9) {
						selectedTime = hourOfDay+":"+minute;
					} else {
						selectedTime = hourOfDay+":0"+minute;
					}
					
				}
			});
		}
		
		/*Date date = new Date();
		
		datePicker.setMaxDate(date.getTime());*/
	}
    
	public void onClick(View v) {
		int id = v.getId();
		
		switch (id) {
		case R.id.btnSubmit: {
			datePicker.clearFocus();
			Intent sendBack = new Intent();
			String selectedDate = dayInMonth[datePicker.getDayOfMonth() - 1] + "/"+ month[(datePicker.getMonth())] + "/" + datePicker.getYear()/*parseYear(dp.getYear())*/;
			if (selectedTime == null) {
				DateFormat dfTime = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
				selectedTime = dfTime.format(new Date());
			}
			if(DATE_TYPE_DATE == dialogType) {				
				sendBack.putExtra("date", selectedDate);
			} else if(DATE_TYPE_DATE_TIME==dialogType) {
				sendBack.putExtra("date", selectedDate+" "+ selectedTime);
			} else if( DATE_TYPE_TIME == dialogType) {
				sendBack.putExtra("date", selectedTime);
			}
			setResult(RESULT_OK, sendBack);
			finish();
			break;
		}
		default:
			break;
		}
	}

	

	@SuppressWarnings("unused")
	private String parseYear(int yr) {
		String y = Integer.toString(datePicker.getYear());
		y = y.substring(y.length() - 2, y.length());
		return y;
	}
	
}
