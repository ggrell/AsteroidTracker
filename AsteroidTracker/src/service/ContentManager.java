package service;

import java.util.ArrayList;
import java.util.List;

import com.vitruviussoftware.bunifish.asteroidtracker.R;

import domains.NearEarthObject;
import domains.Impact;
import domains.News;

import activities.AsteroidTrackerActivity;
import adapters.NewsAdapter;
import adapters.NearEarthObjectAdapter;
import adapters.ImpactAdapter;
import android.content.Context;
import android.util.Log;

public class ContentManager {

		public static NearEarthObjectAdapter adapter_RECENT; 
		public static NearEarthObjectAdapter adapter_UPCOMING; 
		public static ImpactAdapter adapter_IMPACT;
		public static NewsAdapter adapter_NEWS;
		public static List<NearEarthObject> List_NASA_RECENT;
		public static List<NearEarthObject> List_NASA_UPCOMING;
		public static List<Impact> List_NASA_IMPACT;
		public static List<News> List_NASA_News;

	    public static void LoadAdapters_NEO_Recent(Context ctext){
	    	adapter_RECENT = new NearEarthObjectAdapter(ctext, R.layout.nasa_neolistview, List_NASA_RECENT, "RECENT");
    	}
	    public static void LoadAdapters_NEO_Upcoming(Context ctext){
	    	adapter_UPCOMING = new NearEarthObjectAdapter(ctext, R.layout.nasa_neolistview, List_NASA_UPCOMING, "UPCOMING");
    	}
	    public static void LoadAdapters_NEO_News(Context ctext){
	    	adapter_NEWS = new NewsAdapter(ctext, R.layout.jpl_asteroid_news, List_NASA_News);
	    }
	    public static void LoadAdapters_NEO_Impact(Context ctext){
	    	adapter_IMPACT = new ImpactAdapter(ctext, R.layout.nasa_neo_impact_listview, List_NASA_IMPACT);
	    }
	    
	    public ArrayList ParseNewsFeed(){
			return null;
	    }

	    public ArrayList trimArray (ArrayList arraytoTrim){
	    	Log.v("ContentManager", "trimArray start size: "+arraytoTrim.size());
	    	for(int i = 0; i < arraytoTrim.size(); i++){
				if(arraytoTrim.get(i).toString().trim().length() == 0){
//					Log.v("ContentManager", "Removing null: Values: "+i+") "+arraytoTrim.get(i));
					arraytoTrim.remove(i);
				}
	    	}
	    	Log.v("ContentManager", "trimArray end size: "+arraytoTrim.size());
	    	return arraytoTrim;
	    }
	    
	    public void printArray(ArrayList array){
			Log.v("ContentManager", "printArray");
	    	for(int i = 0; i < array.size(); i++){
	    		Log.v("ContentManager", "printArray:"+i+") "+array.get(i));
	    	}
	    }
	    
}
