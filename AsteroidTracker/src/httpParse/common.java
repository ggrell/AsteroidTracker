package httpParse;

import android.util.Log;

public class common {

	
	public static XmlParser getXMLData(String URL_FEED) {
		Log.i("HTTP", "Getting Data");
		String data = getHTTP.get(URL_FEED);
//		Log.i("HTTP", Integer.toString(data.length()));
//		Log.i("HTTP", data);
		return new XmlParser(data);
	}
	
	public static String getHTTPData(String URL_FEED){
		Log.i("HTTP", "Getting Data");
		return getHTTP.get(URL_FEED);
	}
	
}