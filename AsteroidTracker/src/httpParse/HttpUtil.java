package httpParse;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URISyntaxException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;

import android.util.Log;

public class HttpUtil {

	public static String get(String URL) {
		URL.trim();
		Log.i("HTTPCLIENT", "Getting: "+URL);
		String responseData = "";
		StringBuffer sb = new StringBuffer();
		HttpClient httpClient = new DefaultHttpClient();
		
		HttpGet request = new HttpGet();
		HttpParams params = new BasicHttpParams();
		params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, new Integer(1500));
		request.setParams(params);
		try {
			Log.i("HTTPCLIENT", "Setting URI");
			request.setURI(new URI(URL));
			Log.i("HTTPCLIENT", "Done setting URI: " + request.getURI());
			HttpResponse response = httpClient.execute(request);
			Log.i("HTTPCLIENT", "response-StatusLine: "+response.getStatusLine().getStatusCode());
			int status = response.getStatusLine().getStatusCode();
			if (status != HttpStatus.SC_OK) {
				ByteArrayOutputStream ostream = new ByteArrayOutputStream();
				response.getEntity().writeTo(ostream);
			} else {
				InputStream content = response.getEntity().getContent();
				BufferedReader in = new BufferedReader(new InputStreamReader(content));
				String inputLine;
				while ((inputLine = in.readLine()) != null)
					sb.append(inputLine);
				in.close();
				content.close(); // this will also close the connection
			}
			responseData = sb.toString();
		} catch (ClientProtocolException e) {
			Log.e("HTTPCLIENT", "ClientProtocolException");
			e.printStackTrace();
		} catch (URISyntaxException e1) {
			Log.e("HTTPCLIENT", "HTTP ERROR: URISyntaxException");
			e1.printStackTrace();
		}catch (ConnectTimeoutException e ){
			 return "Timeout";
		}
		catch (SocketTimeoutException e){
			 return "Timeout";
		} catch (Exception e){
			Log.e("HTTPCLIENT", "Exception");
			Log.e("HTTPCLIENT", "HTTP ERROR");
			Log.e("HTTPCLIENT", "Message: "+e.getMessage());
			Log.e("HTTPCLIENT", "Cause: "+e.getCause());
			Log.e("HTTPCLIENT", "LocalizedMessage: "+e.getLocalizedMessage());
			return "";
		}
		return responseData;
	}

}
