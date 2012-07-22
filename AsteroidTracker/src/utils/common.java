package utils;

import android.util.Log;

public class common {

	
	public static XmlParser getXMLData(String URL_FEED) {
		Log.i("HTTP", "Getting Data");
		String data = HttpUtil.get(URL_FEED);
<<<<<<< HEAD
//		Log.i("HTTP", Integer.toString(data.length()));
//		Log.i("HTTP", data);
=======
>>>>>>> Asteroid_master_gitservice
		return new XmlParser(data);
	}
	
	public static String getHTTPData(String URL_FEED){
		Log.i("HTTP", "Getting Data");
		return HttpUtil.get(URL_FEED);
	}
	
}
