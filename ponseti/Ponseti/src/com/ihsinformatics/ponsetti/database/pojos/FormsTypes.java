package com.ihsinformatics.ponsetti.database.pojos;

import java.io.Serializable;

public class FormsTypes implements Serializable {

	/**
	 * Generated serial version uid
	 */
	private static final long serialVersionUID = -2351888259701726570L;

	public static String COLUMN_TYPE_ID = "type_id";
	public static String COLUMN_TYPE_NAME = "type_name";

	public static String EVALUATOR_FORM = "evaluator";
	public static String PATIENT_FORM = "patient";
	public static String VISIT_FORM = "visit";

	String typeId;
	String typeName;

	public FormsTypes() {

	}

	public FormsTypes(String typeId, String typeName) {
		super();
		this.typeId = typeId;
		this.typeName = typeName;
	}

	public FormsTypes(String typeId) {
		super();
		this.typeId = typeId;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

}
