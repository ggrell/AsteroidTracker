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

		public NearEarthObjectAdapter adapter_RECENT; 
		public NearEarthObjectAdapter adapter_UPCOMING; 
		public ImpactAdapter adapter_IMPACT;
		public NewsAdapter adapter_NEWS;
		public List<NearEarthObject> List_NASA_RECENT;
		public List<NearEarthObject> List_NASA_UPCOMING;
		public List<Impact> List_NASA_IMPACT;
		public List<News> List_NASA_News;
		public neoAstroidFeed neo_AstroidFeed = new neoAstroidFeed();
		
	    public void LoadAdapters_NEO_Recent(Context ctext){
            long startTime = System.currentTimeMillis();
	    	adapter_RECENT = new NearEarthObjectAdapter(ctext, R.layout.nasa_neolistview, this.List_NASA_RECENT, "RECENT");
            long elapsedTime = System.currentTimeMillis() - startTime;
	    	Log.v("ContentManager", "LoadAdapters_NEO_Recent done " + elapsedTime);
    	}
	    
	    public void LoadAdapters_NEO_Upcoming(Context ctext){
            long startTime = System.currentTimeMillis();
	    	adapter_UPCOMING = new NearEarthObjectAdapter(ctext, R.layout.nasa_neolistview, this.List_NASA_UPCOMING, "UPCOMING");
            long elapsedTime = System.currentTimeMillis() - startTime;
	          Log.v("ContentManager", "LoadAdapters_NEO_Upcoming done " + elapsedTime);
    	}
	    
	    public void LoadAdapters_NEO_News(Context ctext){
            long startTime = System.currentTimeMillis();
	    	adapter_NEWS = new NewsAdapter(ctext, R.layout.jpl_asteroid_news, this.List_NASA_News);
            long elapsedTime = System.currentTimeMillis() - startTime;
	         Log.v("ContentManager", "LoadAdapters_NEO_News done" + elapsedTime);
	    }
	    
	    public void LoadAdapters_NEO_Impact(Context ctext){
            long startTime = System.currentTimeMillis();
	    	adapter_IMPACT = new ImpactAdapter(ctext, R.layout.nasa_neo_impact_listview, this.List_NASA_IMPACT);
            long elapsedTime = System.currentTimeMillis() - startTime;
	    	Log.v("ContentManager", "LoadAdapters_NEO_Impact done" + elapsedTime);
	    }
	    
	    public void loadEntityLists_NEO(String HTTPDATA){
	        List_NASA_RECENT = neo_AstroidFeed.getRecentList(HTTPDATA);
	        List_NASA_UPCOMING = neo_AstroidFeed.getUpcomingList(HTTPDATA);
	    }
	    
	    public void loadEntityLists_IMPACT(String HTTPDATA){
	        List_NASA_IMPACT = neo_AstroidFeed.getImpactList(HTTPDATA);
	    }
	    
//	    public void loadEntityLists_NEWS(String HTTPDATA){
//	        List_NASA_News = neo_AstroidFeed.parseNewsFeed(HTTPDATA);
//	    }
	    
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
