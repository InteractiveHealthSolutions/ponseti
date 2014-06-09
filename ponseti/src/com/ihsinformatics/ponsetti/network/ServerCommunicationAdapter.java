package com.ihsinformatics.ponsetti.network;

public interface ServerCommunicationAdapter {

	public  void xmlrpcCallResponse(Object res, int requestId);
	public  void xmlrpcCallResponse(Object res, String requestId);

}
