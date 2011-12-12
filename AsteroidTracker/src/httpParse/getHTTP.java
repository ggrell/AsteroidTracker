package httpParse;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.ByteArrayBuffer;

import android.util.Log;

public class getHTTP {

	public static String get(String URL) {
		URL.trim();
		Log.i("HTTPCLIENT", "Getting: "+URL);
		String responseData = "";
		StringBuffer sb = new StringBuffer();
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet request = new HttpGet();
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
		} catch (ClientProtocolException e) {
			Log.e("HTTPCLIENT", "ClientProtocolException");
			e.printStackTrace();
		} catch (IOException e) {
			Log.e("HTTPCLIENT", "HTTP ERROR");
			Log.e("HTTPCLIENT", e.getMessage());
			Log.e("HTTPCLIENT", e.getLocalizedMessage());
		} catch (URISyntaxException e1) {
			Log.e("HTTPCLIENT", "HTTP ERROR: URISyntaxException");
			e1.printStackTrace();
		} catch (Exception e){
			Log.e("HTTPCLIENT", "Exception");
			return "";
		}
		return responseData;
	}

	
	public static byte[] getImageData(String ImageURL){
		URL url;
		InputStream inputStream;
		try {
			url = new URL(ImageURL);
			URLConnection ucon = url.openConnection();
			inputStream = ucon.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(inputStream,128);
			ByteArrayBuffer baf = new ByteArrayBuffer(128);
			int current = 0;
			while ((current = bis.read()) != -1) {
			        baf.append((byte) current);
			}
			return baf.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
			ByteArrayBuffer baf = new ByteArrayBuffer(128);
			return baf.toByteArray();
		}
	}
	
}
