package utils;

//import java.io.BufferedReader;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.net.URI;
//import java.net.URISyntaxException;
//import org.apache.http.HttpResponse;
//import org.apache.http.HttpStatus;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.conn.ConnectTimeoutException;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.params.BasicHttpParams;
//import org.apache.http.params.CoreConnectionPNames;
//import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;


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
    
//    public String test(String URI){
//        URL url;
//        String response = "";
//        try {
//            url = new URL(URI);
//            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
//            response = readStream(in);
////          urlConnection.disconnect();
//            }catch (Exception e) {
//                e.printStackTrace();
//                }
//            return response;
//    }
//
//    private String readStream(InputStream in) {
//        BufferedReader reader = null;
//        StringBuffer sb = new StringBuffer();
//        try {
//            reader = new BufferedReader(new InputStreamReader(in));
//            String line = "";
//            while ((line = reader.readLine()) != null) {
//                sb.append(line);
//              System.out.println(line);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (reader != null) {
//              try {
//                reader.close();
//              } catch (IOException e) {
//                e.printStackTrace();
//              }
//            }
//        }
//        return sb.toString();
//
//    }
    
//     public static String get(String URL) {
//       Log.i("HTTPCLIENT", "Getting: "+URL);
//       StringBuffer sb = new StringBuffer();
//       HttpClient httpClient = new DefaultHttpClient();
//       HttpGet request = new HttpGet();
//       HttpParams params = new BasicHttpParams();
//       params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, new Integer(2500));
//       request.setParams(params);
//       try {
//           request.setURI(new URI(URL));
//           HttpResponse response = httpClient.execute(request);
//           int status = response.getStatusLine().getStatusCode();
//           Log.i("HTTPCLIENT", "status: "+status);
//           if (status != HttpStatus.SC_OK) {
//               Log.i("HTTPCLIENT", "HttpStatus isnt ok");
//               ByteArrayOutputStream ostream = new ByteArrayOutputStream();
//               response.getEntity().writeTo(ostream);
//               Log.i("HTTPCLIENT", "sb: "+response.getEntity().getContent().toString());
//           } else {
//                Log.i("HTTPCLIENT", "HttpStatus is ok");
//               InputStream content = response.getEntity().getContent();
//               BufferedReader in = new BufferedReader(new InputStreamReader(content));
//               String inputLine;
//               while ((inputLine = in.readLine()) != null)
//                   sb.append(inputLine);
//               in.close();
//               content.close(); // this will also close the connection
//           }
//           Log.i("HTTPCLIENT", "sb: "+sb.toString());
//       }catch (ConnectTimeoutException e ){
//           Log.e("HTTPCLIENT", "Timeout");
//            return "Timeout";
//       }catch (Exception e){
//           Log.e("HTTPCLIENT", "Exception");
//           Log.e("HTTPCLIENT", "HTTP ERROR");
//           Log.e("HTTPCLIENT", e.getMessage());
//           Log.e("HTTPCLIENT", e.getLocalizedMessage());
//           e.printStackTrace();
//           return "Exception";
//       }
//       Log.i("HTTPCLIENT", "responseData: "+sb.toString());
//       return sb.toString();
//   }
}
