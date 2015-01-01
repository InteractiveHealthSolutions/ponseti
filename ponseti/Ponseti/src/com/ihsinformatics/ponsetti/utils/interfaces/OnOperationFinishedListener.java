package com.ihsinformatics.ponsetti.utils.interfaces;

import java.util.List;

import com.ihsinformatics.ponsetti.parser.FormParser;
import com.ihsinformatics.ponsetti.utils.enums.OperationType;

public interface OnOperationFinishedListener {
	public void onOperationFinished(OperationType operationType);
}
