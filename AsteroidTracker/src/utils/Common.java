package utils;

import android.util.Log;

public class Common {

    HttpUtil httpUtil = new HttpUtil();
	
	public XmlParser getXMLData(String URL_FEED) {
		Log.i("HTTP", "Getting Data");
		String data = httpUtil.get(URL_FEED);
		return new XmlParser(data);
	}
	
	public String getHTTPData(String URL_FEED){
		Log.i("HTTP", "Getting Data");
		return httpUtil.get(URL_FEED);
	}
	
}
