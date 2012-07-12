package service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import utils.getHTTP;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import domains.nasa_neo;
import domains.nasa_neoImpactEntity;
import domains.newsEntity;


public class AsteroidTrackerService {

	private static String useServiceUri = "https://raw.github.com/AsteroidTracker/AsteroidTrackerService/master/useService";
	private static String URIRecent = "https://raw.github.com/AsteroidTracker/AsteroidTrackerService/master/neo_recent/recent";
	private static String URIUpcoming = "https://raw.github.com/AsteroidTracker/AsteroidTrackerService/master/neo_upcoming/upcoming";
	private static String URIImpact = "https://raw.github.com/AsteroidTracker/AsteroidTrackerService/master/neo_impact/impactRisk";
	private static String URINews = "https://raw.github.com/AsteroidTracker/AsteroidTrackerService/master/neo_news/latestnews";
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
//		 ArrayList test = new ArrayList();
//		return test;
	}
	public static ArrayList<nasa_neo> getUpcomingList(){
		Log.i("gitservice", "getUpcomingList");
		Type collectionType = new TypeToken<ArrayList<nasa_neo>>(){}.getType();
		return gson.fromJson(getHTTP.get(URIUpcoming), collectionType);
//		 ArrayList test = new ArrayList();
//			return test;
	}
	
	public static ArrayList<newsEntity> getLatestNews(){
		Log.i("gitservice", "getLatestNews");
		Type collectionType = new TypeToken<ArrayList<newsEntity>>(){}.getType();
//		return gson.fromJson(getHTTP.get(URINews), collectionType);
		ArrayList<newsEntity> newslist = gson.fromJson(getHTTP.get(URINews), collectionType);
		for(int i = 0; i < newslist.size(); i++){
			newslist.get(i).updateImageURLDrawable();
		}
		return newslist;
	}
	
	public static ArrayList<nasa_neoImpactEntity> getImpactData(){
		Log.i("gitservice", "getImpactData");
		Type collectionType = new TypeToken<ArrayList<nasa_neoImpactEntity>>(){}.getType();
		return gson.fromJson(getHTTP.get(URIImpact), collectionType);
	}
}
