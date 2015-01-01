package com.ihsinformatics.ponsetti.utils.interfaces;

import java.util.List;

import com.ihsinformatics.ponsetti.database.pojos.Form;
import com.ihsinformatics.ponsetti.parser.FormParser;

public interface OnDataParsedListener {
	public void onDataParsed(List<Form> processedForms, List<FormParser> parsedForms, int t);
}
