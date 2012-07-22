package utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;

import android.util.Log;

public class HttpUtil {
    
    public String test(String URL) {
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(URL);
        StringBuffer sb = new StringBuffer();
        try {
        HttpResponse response = client.execute(request);
        BufferedReader rd = new BufferedReader
            (new InputStreamReader(response.getEntity().getContent()));
        String line = "";
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } 
        return sb.toString();
    }
    
     public static String get(String URL) {
//       Log.i("HTTPCLIENT", "Getting: "+URL);
       StringBuffer sb = new StringBuffer();
       HttpClient httpClient = new DefaultHttpClient();
       HttpGet request = new HttpGet(URL);
       HttpParams params = new BasicHttpParams();
       params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, new Integer(2500));
       request.setParams(params);
       try {
           long startTime = System.currentTimeMillis();
           HttpResponse response = httpClient.execute(request);
           int status = response.getStatusLine().getStatusCode();
//           Log.i("HTTPCLIENT", "status: "+status);
           if (status != HttpStatus.SC_OK) {
               Log.i("HTTPCLIENT", "HttpStatus isnt ok");
               ByteArrayOutputStream ostream = new ByteArrayOutputStream();
               response.getEntity().writeTo(ostream);
               Log.i("HTTPCLIENT", "sb: "+response.getEntity().getContent().toString());
           } else {
//                Log.i("HTTPCLIENT", "HttpStatus is ok");
               InputStream content = response.getEntity().getContent();
               BufferedReader in = new BufferedReader(new InputStreamReader(content));
               String inputLine;
               while ((inputLine = in.readLine()) != null)
                   sb.append(inputLine);
               in.close();
               content.close(); // this will also close the connection
               long elapsedTime = System.currentTimeMillis() - startTime;
               Log.i("HTTPCLIENT", "Time URL: "+URL+", "+elapsedTime);
           }
       }catch (ConnectTimeoutException e ){
           Log.e("HTTPCLIENT", "Timeout");
            return "Timeout";
       }catch (Exception e){
           Log.e("HTTPCLIENT", "Exception");
           Log.e("HTTPCLIENT", "HTTP ERROR");
           Log.e("HTTPCLIENT", e.getMessage());
           Log.e("HTTPCLIENT", e.getLocalizedMessage());
           e.printStackTrace();
           return "Exception";
       }
//       Log.i("HTTPCLIENT", "Done getting: "+URL);
       return sb.toString();
   }
}
