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

import android.graphics.drawable.Drawable;
import android.util.Log;

public class HttpUtil {

     public String get(String URL) {
       StringBuffer sb = new StringBuffer();
       HttpClient httpClient = new DefaultHttpClient();
       HttpGet request = new HttpGet(URL);
       HttpParams params = new BasicHttpParams();
       params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, new Integer(3000));
       request.setParams(params);
       try {
           HttpResponse response = httpClient.execute(request);
           int status = response.getStatusLine().getStatusCode();
           if (status != HttpStatus.SC_OK) {
               Log.d("HTTPCLIENT", "HttpStatus isnt ok");
               ByteArrayOutputStream ostream = new ByteArrayOutputStream();
               response.getEntity().writeTo(ostream);
               Log.d("HTTPCLIENT", "sb: "+response.getEntity().getContent().toString());
               throw new Exception();
           } else {
               InputStream content = response.getEntity().getContent();
               BufferedReader in = new BufferedReader(new InputStreamReader(content));
               String inputLine;
               while ((inputLine = in.readLine()) != null)
                   sb.append(inputLine);
               in.close();
               content.close(); // this will also close the connection
//               long startTime = System.currentTimeMillis();
//               long elapsedTime = System.currentTimeMillis() - startTime;
//               Log.i("HTTPCLIENT", "Time URL: "+URL+", "+elapsedTime);
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
       return sb.toString();
   }

     public static Drawable getImageData(String URL) {
         HttpClient httpClient = new DefaultHttpClient();
         HttpGet request = new HttpGet(URL);
         HttpParams params = new BasicHttpParams();
         params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, new Integer(3000));
         request.setParams(params);
         Drawable image = null;
         try {
             HttpResponse response = httpClient.execute(request);
             int status = response.getStatusLine().getStatusCode();
             if (status != HttpStatus.SC_OK) {
                 Log.d("HTTPCLIENT", "HttpStatus isnt ok");
             } else {
                 InputStream content = response.getEntity().getContent();
                 image = Drawable.createFromStream(content, "src");
                 content.close();
                 content = null;
             }
         }catch (ConnectTimeoutException e ){
             Log.e("HTTPCLIENT", "Timeout");
              return image;
         }catch (Exception e){
             Log.e("HTTPCLIENT", "Exception");
             Log.e("HTTPCLIENT", "HTTP ERROR");
             Log.e("HTTPCLIENT", e.getMessage());
             Log.e("HTTPCLIENT", e.getLocalizedMessage());
             e.printStackTrace();
             return image;
         }
         return image;
     }

}
