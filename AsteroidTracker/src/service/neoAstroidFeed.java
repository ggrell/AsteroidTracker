package service;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.xml.xpath.XPathExpressionException;

import utils.XmlParser;
import utils.common;


import com.vitruviussoftware.bunifish.asteroidtracker.R;

import domains.Nasa_neoEntityDeprecated;
import domains.NearEarthObject;
import domains.Impact;
import domains.News;

import activities.AsteroidTrackerActivity;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class neoAstroidFeed {

	public static final String URL_NearEarthObjects="http://neo.jpl.nasa.gov/ca/";
	public static final String URL_NEOMain="http://neo.jpl.nasa.gov/";
	public static final String URL_NEOImpact_base="http://neo.jpl.nasa.gov/risk/";	
	public static final String URL_NEOImpact_OrbitalBase="http://ssd.jpl.nasa.gov/sbdb.cgi?sstr={NEONAME};orb=1";			
	public static XmlParser parser;
	public static List<Nasa_neoEntityDeprecated> nasa_neoEntries = new ArrayList<Nasa_neoEntityDeprecated>();
	public static String Data;
	public static String[] index ={"Close Approach Date", "Miss Distance (AU)","Miss Distance (LD)","Estimated Diameter", "H(mag)","Relative Velocity"};
	public static String XPATH_URL = "//a/@href";
	public static String XPATH_NAME = "//a/text()";
	public static String XPATH_DATE = "/tr/td/font/text()";
	public static String XPATH_DATA= "//font/text()";
	public static String XPATH_IMPACT_NAME = "/tr/td/a/tt/text()";
	public static String XPATH_IMPACT_IMPACTPROBABILITY= "//a/text()";
	public static String XPATH_IMPACT_DATA= "//td/tt/text()";
	public static final String RECENT_baseSearchString = "RECENT CLOSE APPROACHES TO EARTH";
	public static final String RECENT_endSearchString = "</table>";
	public static final String UPCOMING_baseSearchString = "UPCOMING CLOSE APPROACHES TO EARTH";
	public static final String UPCOMING_endSearchString = "</table>";
	public static final String URL_NASA_NEO = "http://neo.jpl.nasa.gov/ca/";
	public static final String URL_NASA_NEO_IMPACT_FEED = "http://neo.jpl.nasa.gov/risk/";
	public static final String URL_JPL_AsteroidNewsFeed="http://www.jpl.nasa.gov/multimedia/rss/asteroid.xml";

	
	public static String[] getEntries(XmlParser xmlParser) {
		String[] entryArray = new String[1];
		return entryArray;
	}
	
	public static String[] loadParseEntries(XmlParser xmlParser) {
		String[] entryArray = new String[1];
		return entryArray;
	}

	public static List<Nasa_neoEntityDeprecated> getandLoadUSGSData(String URL_FEED){
		parser = common.getXMLData(URL_FEED);
		loadParseEntries(parser);
		return getEntries();
	}
	
	public static List<Nasa_neoEntityDeprecated> getEntries() {
    	List<Nasa_neoEntityDeprecated> entries = new ArrayList<Nasa_neoEntityDeprecated>();
    	return entries; 
    }
	
	public static List<Nasa_neoEntityDeprecated> getandLoaAstroidData(String URL_FEED){
//		Log.i("HTTP", "getandLoaAstroidData");
		parser = common.getXMLData(URL_FEED);
//		parser = getXMLData(URL_pastDay);
//		usgsEarthQuakeFeed.loadParseEntries(parser);
		return getEntries();
	}

	public String getAstroidFeedDATA(String URL) {
		Log.i("HTTPFEED", "Getting data: "+URL);
		String data = common.getHTTPData(URL);
//		Log.i("neo", "DATA: "+data);
		return data;
}
	
	public List getRecentList(String DATA){
		ArrayList<NearEarthObject> NEO_RECENTList = parseDATA(DATA, RECENT_baseSearchString, RECENT_endSearchString, "recent");
		return NEO_RECENTList;		
	}
		
	public List getUpcomingList(String DATA){
		ArrayList<NearEarthObject> NEO_UPCOMINGList = parseDATA(DATA, UPCOMING_baseSearchString, UPCOMING_endSearchString, "upcoming");
		return NEO_UPCOMINGList;		
	}
	public List getImpactList(String DATA){
		ArrayList<Impact> NEO_UPCOMINGList = parseImpactDATA(DATA, "Recently Observed Objects", "Objects Not Recently Observed", "impact");
		return NEO_UPCOMINGList;		
	}

	private static ArrayList wordcount(String data, String searchFOR){
		ArrayList rowIndex = new ArrayList();
		int lastIndex = 0;
		int count =0;
		while(lastIndex != -1){
		       lastIndex = data.indexOf(searchFOR,lastIndex+1);
		       rowIndex.add(lastIndex);
		       if( lastIndex != -1){
		             count++;
		       }
			}
		rowIndex.add(count);
		return rowIndex;
		}
	
	public ArrayList<NearEarthObject> parseDATA(String data, String startingPoint, String endingPoint, String type){
		ArrayList<NearEarthObject> nasaNeoList = new ArrayList<NearEarthObject>();
		ArrayList<NearEarthObject> nasaNeoList_SortingList = new ArrayList<NearEarthObject>();
		if(data == null || data.length() == 0){
			NearEarthObject astroid = new NearEarthObject();
			astroid.setName("Unable to retrieve Asteroid feed");
			nasaNeoList.add(astroid);
		}else{
		int bIDX_Recent = data.indexOf(startingPoint);
		int eIDX_Recent = data.indexOf(endingPoint,bIDX_Recent);
		String subRecent = data.substring(bIDX_Recent,eIDX_Recent);
		ArrayList rowIndex = wordcount(subRecent, "<tr>");
		int rowCount = (Integer) rowIndex.get(rowIndex.size()-1);
		try{
		for(int i = 2; i < rowCount; i++){
			int beginRow = (Integer) rowIndex.get(i);
			int endRow = subRecent.indexOf("</tr>", beginRow);
			String rowData = subRecent.substring(beginRow,endRow);
			rowData = rowData+"</tr>";
			rowData = rowData.replace("nowrap", " ");
			String xmlBegin="<?xml version=\"1.0\"?><!DOCTYPE some_name [<!ENTITY nbsp \"&#160;\">]>";
			String testXMLParse = xmlBegin+rowData;
			XmlParser xmlParser = new XmlParser(testXMLParse);
			ArrayList<String> URL = xmlParser.getXpath(XPATH_URL);
			ArrayList<String> name = xmlParser.getXpath(XPATH_NAME);
			ArrayList<String> Data = xmlParser.getXpath(XPATH_DATA);
			NearEarthObject astroid = new NearEarthObject();
			for(int loop = 0; loop < URL.size(); loop++){
				astroid.setURL(URL.get(loop).toString().trim());
//				Log.i("neo", "DATA: "+name.get(loop).toString().trim());
				astroid.setName(name.get(loop).toString().trim());
//				astroid.setDate(Data.get(0).toString().trim());
				astroid.setDateStr(Data.get(0).toString().trim());
				astroid.SetMissDistance_AU(Data.get(1).toString().trim());
				astroid.setMissDistance_LD(Data.get(2).toString().trim());
				astroid.setEstimatedDiameter(Data.get(3).toString().trim());
				astroid.setHmagnitude(Data.get(4).toString().trim());
				astroid.setRelativeVelocity(Data.get(5).toString().trim());
				astroid.setIcon(activities.AsteroidTrackerActivity.drawable);
			}
			nasaNeoList_SortingList.add(astroid);
			if(type.equals("upcoming")){
				if(i == 15){
					break;
				}
			}
		}
		} catch (MalformedURLException e) {
			//TODO add error log here. 
			e.printStackTrace();
		}
		if(type.equals("recent")){
			for(int i = nasaNeoList_SortingList.size()-1; i >= 0; i--){
				nasaNeoList.add(nasaNeoList_SortingList.get(i));
			}	
		}else{
			nasaNeoList = nasaNeoList_SortingList;
		}
		return nasaNeoList;
		}
		return nasaNeoList;
	}


	public ArrayList<Impact> parseImpactDATA(String data, String startingPoint, String endingPoint, String type){
		ArrayList<Impact> nasaNeoList = new ArrayList<Impact>();

		if(data == null || data.length() == 0){
//			Log.e("parsedata", "IM EMPTY");
			Impact astroid = new Impact();
			astroid.setName("Unable to retrieve Asteroid feed");
			nasaNeoList.add(astroid);
		}else{
//		Log.i("parsedata", "IMPACT IM NOT EMPTY");
		int bIDX_Recent = data.indexOf(startingPoint);
		int eIDX_Recent = data.indexOf(endingPoint,bIDX_Recent);
		String subRecent = data.substring(bIDX_Recent,eIDX_Recent);
//		Log.i("parsedata", "TEST: "+subRecent);
//		Log.i("parsedata", "bIDX_Recent: "+bIDX_Recent);
//		Log.i("parsedata", "eIDX_Recent: "+eIDX_Recent);
		ArrayList rowIndex = wordcount(subRecent, "<tr");
		int rowCount = (Integer) rowIndex.get(rowIndex.size()-1);
//		Log.i("parsedata", "rowCount: "+rowCount);
		for(int i = 1; i < rowCount; i++){
			int beginRow = (Integer) rowIndex.get(i);
			int endRow = subRecent.indexOf("</tr>", beginRow);
			String rowData = subRecent.substring(beginRow,endRow);
			rowData = rowData+"</tr>";
			rowData = rowData.replace("nowrap", " ");
			rowData = rowData.replace("<a href=", "<a href=\"");
			rowData = rowData.replace("<a href=\"\"", "<a href=\"");
			rowData = rowData.replace(".html", ".html\"");
//			Log.i("parsedata", "New rowData: "+rowData);
			String xmlBegin="<?xml version=\"1.0\"?><!DOCTYPE some_name [<!ENTITY nbsp \"&#160;\">]>";
			String testXMLParse = xmlBegin+rowData;
//			Log.i("parsedata", "testXMLParse: "+ testXMLParse);
			XmlParser xmlParser = new XmlParser(testXMLParse);
//			ArrayList<String> URL = xmlParser.getXpath(XPATH_URL);
			ArrayList<String> name = xmlParser.getXpath(XPATH_IMPACT_NAME);
			ArrayList<String> Data = xmlParser.getXpath(XPATH_IMPACT_DATA);
			ArrayList<String> IMPACTPROB = xmlParser.getXpath(XPATH_IMPACT_IMPACTPROBABILITY);
			Impact asteroidImpact = new Impact();
//				Log.i("parsedata", "DATA  DataSIZE: "+ Data.size());
//				Log.i("parsedata", "NAME  DataSIZE: "+ name.size());
//				Log.i("parsedata", "IMPACTPROB  DataSIZE: "+ IMPACTPROB.size());			
			if(name.size() != 0){
//				Log.i("parsedata", "NAME: "+name.get(0).toString().trim());
				asteroidImpact.setName(name.get(0).toString().trim());	
			}
			if(IMPACTPROB.size() != 0){
//				Log.i("parsedata", "NAME: "+name.get(0).toString().trim());
				asteroidImpact.setImpactProbabilites(IMPACTPROB.get(0).toString().trim());	
			}
//			for(int loop = 0; loop < Data.size(); loop++){
			if(Data.size() != 0){
//				Log.i("parsedata", "DATA setYearRange: "+Data.get(0).toString().trim());
				asteroidImpact.setYearRange(Data.get(0).toString().trim());
				asteroidImpact.setPotentialImpacts(Data.get(1).toString().trim());
				asteroidImpact.setVInfinity(Data.get(2).toString().trim());
				asteroidImpact.setH_AbsoluteMag(Data.get(3).toString().trim());
				asteroidImpact.setEstimagesDiameter(Data.get(4).toString().trim());
				asteroidImpact.setPalermoScaleAve(Data.get(5).toString().trim());
				asteroidImpact.setPalermoScaleMax(Data.get(6).toString().trim());
				asteroidImpact.setTorinoScale(Data.get(7).toString().trim());
				asteroidImpact.setIcon(activities.AsteroidTrackerActivity.drawable);
//			}
			}
			nasaNeoList.add(asteroidImpact);
		}
		return nasaNeoList;
		}
		return nasaNeoList;
}

	public ArrayList<News> parseNewsFeed(String data){
		Log.i("news", "data2"+data);
		XmlParser xmlParser = new XmlParser(data);
		return xmlParser.getXpath_getNewsItem();
	}
}