package androidxmlrpc;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.net.URI;
import java.net.URL;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.Context;


/**
 * XMLRPCClient allows to call remote XMLRPC method.
 * 
 * <p>
 * The following table shows how XML-RPC types are mapped to java call parameters/response values.
 * </p>
 * 
 * <p>
 * <table border="2" align="center" cellpadding="5">
 * <thead><tr><th>XML-RPC Type</th><th>Call Parameters</th><th>Call Response</th></tr></thead>
 * 
 * <tbody>
 * <td>int, i4</td><td>byte<br />Byte<br />short<br />Short<br />int<br />Integer</td><td>int<br />Integer</td>
 * </tr>
 * <tr>
 * <td>i8</td><td>long<br />Long</td><td>long<br />Long</td>
 * </tr>
 * <tr>
 * <td>double</td><td>float<br />Float<br />double<br />Double</td><td>double<br />Double</td>
 * </tr>
 * <tr>
 * <td>string</td><td>String</td><td>String</td>
 * </tr>
 * <tr>
 * <td>boolean</td><td>boolean<br />Boolean</td><td>boolean<br />Boolean</td>
 * </tr>
 * <tr>
 * <td>dateTime.iso8601</td><td>java.util.Date<br />java.util.Calendar</td><td>java.util.Date</td>
 * </tr>
 * <tr>
 * <td>base64</td><td>byte[]</td><td>byte[]</td>
 * </tr>
 * <tr>
 * <td>array</td><td>java.util.List&lt;Object&gt;<br />Object[]</td><td>Object[]</td>
 * </tr>
 * <tr>
 * <td>struct</td><td>java.util.Map&lt;String, Object&gt;</td><td>java.util.Map&lt;String, Object&gt;</td>
 * </tr>
 * </tbody>
 * </table>
 * </p>
 * <p>
 * You can also pass as a parameter any object implementing XMLRPCSerializable interface. In this
 * case your object overrides getSerializable() telling how to serialize to XMLRPC protocol
 * </p>
 */

public class CopyOfXMLRPCClient extends XMLRPCCommon {
	private HttpsClient client;
	private HttpPost postMethod;
	private HttpParams httpParams;

	/**
	 * XMLRPCClient constructor. Creates new instance based on server URI
	 * @param XMLRPC server URI
	 */
	public CopyOfXMLRPCClient(URI uri, Context context) {
		postMethod = new HttpPost(uri);
		postMethod.addHeader("Content-Type", "text/xml");
		
		// WARNING
		// I had to disable "Expect: 100-Continue" header since I had 
		// two second delay between sending http POST request and POST body 
		httpParams = postMethod.getParams();
		HttpProtocolParams.setUseExpectContinue(httpParams, false);
		client = new HttpsClient(context);
	}
	
	/**
	 * Convenience constructor. Creates new instance based on server String address
	 * @param XMLRPC server address
	 */
	public CopyOfXMLRPCClient(String url, Context context) {
		this(URI.create(url), context);
	}

	/**
	 * Convenience XMLRPCClient constructor. Creates new instance based on server URL
	 * @param XMLRPC server URL
	 */
	public CopyOfXMLRPCClient(URL url, Context context) {
		this(URI.create(url.toExternalForm()), context);
	}

	/**
	 * Convenience constructor. Creates new instance based on server String address
	 * @param XMLRPC server address
	 * @param HTTP Server - Basic Authentication - Username
	 * @param HTTP Server - Basic Authentication - Password
	 */	
	public CopyOfXMLRPCClient(URI uri, String username, String password, Context context) {
        this(uri, context);
        
        ((DefaultHttpClient) client).getCredentialsProvider().setCredentials(
        new AuthScope(uri.getHost(), uri.getPort(),AuthScope.ANY_REALM),
        new UsernamePasswordCredentials(username, password));
    }

	/**
	 * Convenience constructor. Creates new instance based on server String address
	 * @param XMLRPC server address
	 * @param HTTP Server - Basic Authentication - Username
	 * @param HTTP Server - Basic Authentication - Password
	 */
	public CopyOfXMLRPCClient(String url, String username, String password, Context context) {
		this(URI.create(url), username, password, context);
	}

	/**
	 * Convenience constructor. Creates new instance based on server String address
	 * @param XMLRPC server url
	 * @param HTTP Server - Basic Authentication - Username
	 * @param HTTP Server - Basic Authentication - Password
	 */
	public CopyOfXMLRPCClient(URL url, String username, String password, Context context) {
		this(URI.create(url.toExternalForm()), username, password, context);
	}

	/**
	 * Sets basic authentication on web request using plain credentials
	 * @param username The plain text username
	 * @param password The plain text password
	 */
	public void setBasicAuthentication(String username, String password) {
		((DefaultHttpClient) client).getCredentialsProvider().setCredentials(
		        new AuthScope(postMethod.getURI().getHost(), postMethod.getURI().getPort(),
AuthScope.ANY_REALM),
		        new UsernamePasswordCredentials(username, password));
	}

	/**
	 * Call method with optional parameters. This is general method.
	 * If you want to call your method with 0-8 parameters, you can use more
	 * convenience call() methods
	 * 
	 * @param method name of method to call
	 * @param params parameters to pass to method (may be null if method has no parameters)
	 * @return deserialized method return value
	 * @throws XMLRPCException
	 */
	@SuppressWarnings("unchecked")
	public Object callEx(String method, Object[] params) throws XMLRPCException {
		try {
			// prepare POST body
			String body = methodCall(method, params);

			// set POST body
			HttpEntity entity = new StringEntity(body);
			postMethod.setEntity(entity);

			//Log.d(Tag.LOG, "ros HTTP POST");
			// execute HTTP POST request
			HttpResponse response = client.execute(postMethod);
			//Log.d(Tag.LOG, "ros HTTP POSTed");

			// check status code
			int statusCode = response.getStatusLine().getStatusCode();
			
			// Test code to read response from server
			InputStreamReader ir = new InputStreamReader(response.getEntity().getContent());
			BufferedReader br = new BufferedReader(ir);
			String b, resp = "";
			while ((b = br.readLine()) != null) {
				resp += b;
			}
			System.out.println(resp);
			 
			
			
			//Log.d(Tag.LOG, "ros status code:" + statusCode);
			if (statusCode != HttpStatus.SC_OK) {
				throw new XMLRPCException("HTTP status code: " + statusCode + " != " + HttpStatus.SC_OK);
			}

			// parse response stuff
			//
			// setup pull parser
			XmlPullParser pullParser = XmlPullParserFactory.newInstance().newPullParser();
			entity = response.getEntity();
			Reader reader = new InputStreamReader(new BufferedInputStream(entity.getContent()));
		// for testing purposes only
		// reader = new StringReader("<?xml version='1.0'?><methodResponse><params><param><value>\n\n\n</value></param></params></methodResponse>");
			pullParser.setInput(reader);
			
			// lets start pulling...
			pullParser.nextTag();
			pullParser.require(XmlPullParser.START_TAG, null, Tag.METHOD_RESPONSE);
			
			pullParser.nextTag(); // either Tag.PARAMS (<params>) or Tag.FAULT (<fault>)  
			String tag = pullParser.getName();
			if (tag.equals(Tag.PARAMS)) {
				// normal response
				pullParser.nextTag(); // Tag.PARAM (<param>)
				pullParser.require(XmlPullParser.START_TAG, null, Tag.PARAM);
				pullParser.nextTag(); // Tag.VALUE (<value>)
				// no parser.require() here since its called in XMLRPCSerializer.deserialize() below
				
				// deserialize result
				Object obj = iXMLRPCSerializer.deserialize(pullParser);
				entity.consumeContent();
				return obj;
			} else
			if (tag.equals(Tag.FAULT)) {
				// fault response
				pullParser.nextTag(); // Tag.VALUE (<value>)
				// no parser.require() here since its called in XMLRPCSerializer.deserialize() below

				// deserialize fault result
				Map<String, Object> map = (Map<String, Object>) iXMLRPCSerializer.deserialize(pullParser);
				String faultString = (String) map.get(Tag.FAULT_STRING);
				int faultCode = (Integer) map.get(Tag.FAULT_CODE);
				entity.consumeContent();
				throw new XMLRPCFault(faultString, faultCode);
			} else {
				entity.consumeContent();
				throw new XMLRPCException("Bad tag <" + tag + "> in XMLRPC response - neither <params> nor <fault>");
			}
		} catch (XMLRPCException e) {
			// catch & propagate XMLRPCException/XMLRPCFault
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			// wrap any other Exception(s) around XMLRPCException
			throw new XMLRPCException(e);
		}
	}
	
	private String methodCall(String method, Object[] params)
	throws IllegalArgumentException, IllegalStateException, IOException {
		StringWriter bodyWriter = new StringWriter();
		serializer.setOutput(bodyWriter);
		serializer.startDocument(null, null);
		serializer.startTag(null, Tag.METHOD_CALL);
		// set method name
		serializer.startTag(null, Tag.METHOD_NAME).text(method).endTag(null, Tag.METHOD_NAME);
		
		serializeParams(params);

		serializer.endTag(null, Tag.METHOD_CALL);
		serializer.endDocument();

		return bodyWriter.toString();
	}

	
	
	/**
	 * Convenience method call with one parameter
	 * 
	 * @param method name of method to call
	 * @param p0 method's parameter
	 * @return deserialized method return value
	 * @throws XMLRPCException
	 */
	public Object call(String method, Object... params) throws XMLRPCException {
		
		return callEx(method, params);
	}
	
}
