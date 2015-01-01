/**
 * This class handles all HTTPS (secure) calls
 */

package androidxmlrpc;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;

import android.content.Context;
import android.util.Log;

import com.ihsinformatics.ponsetti.R;

/**
 * @author owais.hussain@irdresearch.org
 * 
 */
public class HttpsClient extends DefaultHttpClient
{
	
	private static final int	HTTP_PORT	= 80;
	private static final int	HTTPS_PORT	= 443;
	private final Context		context;

	public HttpsClient (Context context)
	{
		this.context = context;
	}

	public String request (String requestUri)
	{
		return request (new HttpGet (requestUri));
	}
	
	public String request (HttpUriRequest request)
	{
		String responseString = null;
		try
		{
			HttpsClient client = new HttpsClient (context);
			HttpResponse response = client.execute (request);
			StatusLine statusLine = response.getStatusLine ();
			Log.d ("HttpsClient", "Http response code: " + statusLine.getStatusCode ());
			if (statusLine.getStatusCode () == HttpStatus.SC_OK)
			{
				ByteArrayOutputStream out = new ByteArrayOutputStream ();
				response.getEntity ().writeTo (out);
				out.close ();
				responseString = out.toString ();
				//Log.d (TAG, responseString);
			}
			else
			{
				response.getEntity ().getContent ().close ();
			}
		}
		catch (ClientProtocolException e)
		{
			e.printStackTrace ();
		}
		catch (IllegalStateException e)
		{
			e.printStackTrace ();
		}
		catch (IOException e)
		{
			e.printStackTrace ();
		}
		return responseString;
	}

	

	@Override
	protected ClientConnectionManager createClientConnectionManager ()
	{
		// Get an instance of the Bouncy Castle KeyStore format
		try
		{
			KeyStore trusted = KeyStore.getInstance ("BKS");
			// Get the raw resource, containing keystore with your trusted certificates (root & intermediate certs)
			/* This keystore was created using a utility called Keystore Explorer */
			InputStream in = context.getResources ().openRawResource (R.raw.ponseti);
			// Initialize the keystore with the provided trusted certificates
			// Also provide the password of the keystore
			trusted.load (in, "jingle94".toCharArray ());
			// Pass the keystore to the SSLSocketFactory, which is responsible for the server certificate verification
			SSLSocketFactory socketFactory = new SSLSocketFactory (trusted);
			// Hostname verification from certificate. Use SSLSocketFactory.STRICT_HOSTNAME_VERIFIER for production
			socketFactory.setHostnameVerifier (SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

			SchemeRegistry registry = new SchemeRegistry ();
			registry.register (new Scheme ("http", PlainSocketFactory.getSocketFactory (), HTTP_PORT));
			// Register for port 443 our SSLSocketFactory with our keystore to the ConnectionManager
			registry.register (new Scheme ("https", socketFactory, HTTPS_PORT));
			return new SingleClientConnManager (getParams (), registry);
		}
		catch (KeyStoreException e)
		{
			throw new AssertionError (e);
		}
		catch (NoSuchAlgorithmException e)
		{
			throw new AssertionError (e);
		}
		catch (CertificateException e)
		{
			throw new AssertionError (e);
		}
		catch (KeyManagementException e)
		{
			throw new AssertionError (e);
		}
		catch (UnrecoverableKeyException e)
		{
			throw new AssertionError (e);
		}
		catch (IOException e)
		{
			throw new AssertionError (e);
		}
	}
}
