package nasa.neoAstroid.news;

import httpParse.XmlParser;

import java.util.ArrayList;

import android.util.Log;

public class AsteroidNewsProxy {

	public static final String URL_JPL_AsteroidNewsFeed="http://www.jpl.nasa.gov/multimedia/rss/asteroid.xml";

	public ArrayList<newsEntity> parseNewsFeed(String data){
		Log.i("news", "data2"+data);
		XmlParser xmlParser = new XmlParser(data);
		return xmlParser.getXpath_getNewsItem();
	}
	
}
