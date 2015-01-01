package com.ihsinformatics.ponsetti.network;


import com.ihsinformatics.ponsetti.utils.Global;

import android.content.Context;
import android.os.AsyncTask;
import androidxmlrpc.CopyOfXMLRPCClient;
import androidxmlrpc.XMLRPCClient;
import androidxmlrpc.XMLRPCException;

public class CopyOfServerCommunicator extends AsyncTask<Object, Integer, Object> {

	CopyOfXMLRPCClient client;
	ServerCommunicationAdapter asyncTaskAdaptee;
	int requestId;
	String functionName;
	
	public CopyOfServerCommunicator(ServerCommunicationAdapter asyncTaskAdaptee, Context context, int requestId, String functionName) {
		this.asyncTaskAdaptee = asyncTaskAdaptee;
		this.requestId = requestId;
		this.functionName = functionName;
		client = new CopyOfXMLRPCClient(Global.HTTPS_URL, context);
	}
	
	@Override
	protected Object doInBackground(Object... params) {
		Object result = null;
		try {
			result = (Object) client.call(functionName, params);

		} catch (XMLRPCException e) {
			result = e.getMessage();
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	protected void onPostExecute(Object result) {
		asyncTaskAdaptee.xmlrpcCallResponse(result, requestId);
		super.onPostExecute(result);
	}

}
