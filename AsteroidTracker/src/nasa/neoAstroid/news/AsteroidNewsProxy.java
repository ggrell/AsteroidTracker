package nasa.neoAstroid.news;

import httpParse.XmlParser;

import java.util.ArrayList;

import android.util.Log;

public class AsteroidNewsProxy {

	public static final String URL_JPL_AsteroidNewsFeed="http://www.jpl.nasa.gov/multimedia/rss/asteroid.xml";
	public static String LastBuildDate_DB="";
	public static String LastBuildDate_Net="";

	
	public ArrayList<newsEntity> parseNewsFeed(String data){
		if (data.length() < 1){
			ArrayList emtpy = new ArrayList();
			return emtpy;
		}else{
			XmlParser xmlParser = new XmlParser(data);
			return xmlParser.getXpath_getNewsItem();
		}
	}
	
	public static void IsRSSFeedNew(){
		
	}
	
}
