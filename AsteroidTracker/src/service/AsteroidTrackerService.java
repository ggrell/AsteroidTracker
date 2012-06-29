package service;

import java.lang.reflect.Type;
import java.util.ArrayList;

import utils.getHTTP;


import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import domains.nasa_neo;


public class AsteroidTrackerService {

	private static String useServiceUri = "https://raw.github.com/AsteroidTracker/AsteroidTrackerService/master/useService";
	private static String URIRecent = "https://raw.github.com/AsteroidTracker/AsteroidTrackerService/master/neo_recent/recent";
	private static String URIUpcoming = "https://raw.github.com/AsteroidTracker/AsteroidTrackerService/master/neo_upcoming/upcoming";
	private static String URIImpact = "https://raw.github.com/AsteroidTracker/AsteroidTrackerService/master/useService";
	boolean useService = false;
	public static Gson gson = new Gson();
	
	public static boolean IsGitServiceAvailable(){
		Log.i("gitservice", "IsGitServiceAvailable");
//		String data = getHTTP.get(useServiceUri).trim();
		if (getHTTP.get(useServiceUri).trim().equals("true")){
			Log.i("gitservice", "IsGitServiceAvailable: "+ true);
			return true;
		}else {
			Log.i("gitservice", "IsGitServiceAvailable: "+ false);
			return false;
		}
	}
	
	public static ArrayList<nasa_neo> getRecentList(){
		Log.i("gitservice", "getRecentList");
		Type collectionType = new TypeToken<ArrayList<nasa_neo>>(){}.getType();
		return gson.fromJson(getHTTP.get(URIRecent), collectionType);
	}
	public static ArrayList<nasa_neo> getUpcomingList(){
		Log.i("gitservice", "getUpcomingList");
		Type collectionType = new TypeToken<ArrayList<nasa_neo>>(){}.getType();
		return gson.fromJson(getHTTP.get(URIUpcoming), collectionType);
	}
}
