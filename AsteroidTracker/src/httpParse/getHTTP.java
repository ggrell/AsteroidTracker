package httpParse;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

public class getHTTP {

	public static String get(String URL) {
		URL.trim();
		Log.i("HTTPCLIENT", "Getting: "+URL);
		String responseData = "";
		StringBuffer sb = new StringBuffer();
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet request = new HttpGet();
		HttpParams params = new BasicHttpParams();
		params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, new Integer(2000));
		request.setParams(params);
		try {
			request.setURI(new URI(URL));
			HttpResponse response = httpClient.execute(request);
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
		}catch (ConnectTimeoutException e ){
			 return "Timeout";
		}catch (Exception e){
			Log.e("HTTPCLIENT", "Exception");
			Log.e("HTTPCLIENT", "HTTP ERROR");
			Log.e("HTTPCLIENT", e.getMessage());
			Log.e("HTTPCLIENT", e.getLocalizedMessage());
			return "Exception";
		}
		return responseData;
	}

}
