package com.ihsinformatics.ponsetti.screens;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ihsinformatics.interactivepaginatortable.NumberOfColumnsException;
import com.ihsinformatics.interactivepaginatortable.OnItemClickListener;
import com.ihsinformatics.interactivepaginatortable.PaginatorTable;
import com.ihsinformatics.interactivepaginatortable.PaginatorTableConfig;
import com.ihsinformatics.ponsetti.R;
import com.ihsinformatics.ponsetti.database.data_access.FormDAO;
import com.ihsinformatics.ponsetti.database.pojos.Form;
import com.ihsinformatics.ponsetti.database.pojos.FormsTypes;

public class ViewVisitsActivity extends FragmentActivity implements OnItemClickListener {

	/**
	 * Generated serialVersionUID
	 */
	private static final long serialVersionUID = -3469367765738265572L;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_visits);
		LinearLayout llMain = (LinearLayout) findViewById(R.id.llMain);
		ArrayList<String[]> list = new ArrayList<String[]>();
		List<Form> forms = new FormDAO(this).getForms(Form.COLUMN_TYPE_ID+"= '"+FormsTypes.VISIT_FORM+"'");
		String[] temp;
		for(Form f: forms) {
			temp = new String[]{"",""};
			temp[0] = f.getIcrId();
			temp[1] = f.getTimeStamp()+"";
			list.add(temp);
		}
		if(list.size()>0) {
			try {
				PaginatorTable llTable = new PaginatorTable(this, new PaginatorTableConfig(15,new String[]{"ICR ID", "Timestamp"}, list)
				.setFontSize(22)
				.setHeadingFontSize(22)
				.setPageTransformationStyle(PaginatorTable.TRANSFORMATION_STYLE_DEPTH_PAGE)
				.getPaginatorTableIntent(this), this);
				
				llMain.addView(llTable);
			} catch (NumberOfColumnsException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onItemClicked(View v, String text) {
		Intent intent = new Intent(this, AddVisit.class);
		intent.putExtra("form", new FormDAO(this).getForm(Form.COLUMN_ICR_ID+"='"+((TextView)v).getText().toString()+"'"));
		startActivity(intent);
	}
}
