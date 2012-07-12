package service;

import java.util.ArrayList;
import java.util.List;

import com.vitruviussoftware.bunifish.asteroidtracker.R;

import domains.nasa_neo;
import domains.nasa_neoImpactEntity;
import domains.newsEntity;

import activities.AsteroidTrackerActivity;
import adapters.asteroidNewsAdapter;
import adapters.nasa_neoArrayAdapter;
import adapters.nasa_neoImpactAdapter;
import android.content.Context;
import android.util.Log;

public class ContentManager {

		public static nasa_neoArrayAdapter adapter_RECENT; 
		public static nasa_neoArrayAdapter adapter_UPCOMING; 
		public static nasa_neoImpactAdapter adapter_IMPACT;
		public static asteroidNewsAdapter adapter_NEWS;
		public static List<nasa_neo> List_NASA_RECENT;
		public static List<nasa_neo> List_NASA_UPCOMING;
		public static List<nasa_neoImpactEntity> List_NASA_IMPACT;
		public static List<newsEntity> List_NASA_News;

	    public static void LoadAdapters_NEO_Recent(Context ctext){
	    	adapter_RECENT = new nasa_neoArrayAdapter(ctext, R.layout.nasa_neolistview, List_NASA_RECENT, "RECENT");
    	}
	    public static void LoadAdapters_NEO_Upcoming(Context ctext){
	    	adapter_UPCOMING = new nasa_neoArrayAdapter(ctext, R.layout.nasa_neolistview, List_NASA_UPCOMING, "UPCOMING");
    	}
	    public static void LoadAdapters_NEO_News(Context ctext){
	    	adapter_NEWS = new asteroidNewsAdapter(ctext, R.layout.jpl_asteroid_news, List_NASA_News);
	    }
	    public static void LoadAdapters_NEO_Impact(Context ctext){
	    	adapter_IMPACT = new nasa_neoImpactAdapter(ctext, R.layout.nasa_neo_impact_listview, List_NASA_IMPACT);
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
