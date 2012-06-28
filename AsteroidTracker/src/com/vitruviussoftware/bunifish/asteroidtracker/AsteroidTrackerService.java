package com.vitruviussoftware.bunifish.asteroidtracker;

import java.lang.reflect.Type;
import java.util.ArrayList;

import nasa.neoAstroid.nasa_neo;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import httpParse.getHTTP;

public class AsteroidTrackerService {

	private static String useServiceUri = "https://raw.github.com/AsteroidTracker/AsteroidTrackerService/master/useService";
	private static String URIRecent = "https://raw.github.com/AsteroidTracker/AsteroidTrackerService/master/neo_recent/recent";
	private static String URIUpcoming = "https://raw.github.com/AsteroidTracker/AsteroidTrackerService/master/useService";
	private static String URIImpact = "https://raw.github.com/AsteroidTracker/AsteroidTrackerService/master/useService";
	boolean useService = false;
	public Gson gson = new Gson();
	
	public static void main(String args){
		String data = getHTTP.get(useServiceUri);
		System.out.print("DATA: "+ data);

	}
	
	public boolean IsGitServiceAvailable(){
		Log.i("gitservice", "IsGitServiceAvailable");
		String data= getHTTP.get(useServiceUri);
		if (data.equals("true")){
			return true;
		}else {
			return false;
		}
	}
	
	public String retrieveRecent(){
		return getHTTP.get(URIRecent);
	}

	public void getRecentList(){
		Log.i("gitservice", "getRecentList");
		Type collectionType = new TypeToken<ArrayList<nasa_neo>>(){}.getType();
		ArrayList<nasa_neo> recentList = gson.fromJson(retrieveRecent(), collectionType);
		Log.i("gitservice", "recentList:" + recentList.size());

	}
	
}
