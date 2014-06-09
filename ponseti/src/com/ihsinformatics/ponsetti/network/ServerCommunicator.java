package com.ihsinformatics.ponsetti.network;


import com.ihsinformatics.ponsetti.utils.Global;

import android.content.Context;
import android.os.AsyncTask;
import androidxmlrpc.XMLRPCClient;
import androidxmlrpc.XMLRPCException;

public class ServerCommunicator extends AsyncTask<Object, Integer, Object> {

	XMLRPCClient client;
	ServerCommunicationAdapter asyncTaskAdaptee;
	int requestId;
	String functionName;
	String sRequestId;
	
	public ServerCommunicator(ServerCommunicationAdapter asyncTaskAdaptee, Context context, int requestId, String functionName) {
		this.sRequestId = null;
		this.requestId = requestId;
		init(asyncTaskAdaptee, context, functionName);
	}
	
	public ServerCommunicator(ServerCommunicationAdapter asyncTaskAdaptee, Context context, String requestId, String functionName) {
		this.requestId = -1;
		this.sRequestId = requestId;
		init(asyncTaskAdaptee, context, functionName);
		
	}
	
	private void init(ServerCommunicationAdapter asyncTaskAdaptee, Context context, String functionName) {
		this.asyncTaskAdaptee = asyncTaskAdaptee;
		this.functionName = functionName;
		client = new XMLRPCClient(Global.HTTPS_URL, context);
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
		if(requestId != -1) {
			asyncTaskAdaptee.xmlrpcCallResponse(result, requestId);
		} else if(sRequestId != null) {
			asyncTaskAdaptee.xmlrpcCallResponse(result, sRequestId);
		}
		super.onPostExecute(result);
	}

}
