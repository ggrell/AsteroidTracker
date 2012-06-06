package com.vitruviussoftware.bunifish.asteroidtracker;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import nasa.neoAstroid.nasa_neo;
import nasa.neoAstroid.nasa_neoArrayAdapter;
import nasa.neoAstroid.impackRisk.nasa_neoImpactAdapter;
import nasa.neoAstroid.impackRisk.nasa_neoImpactEntity;
import nasa.neoAstroid.news.asteroidNewsAdapter;
import nasa.neoAstroid.news.newsEntity;

public class ContentManager {

	public static nasa_neoArrayAdapter adapter_RECENT; 
	public static nasa_neoArrayAdapter adapter_UPCOMING; 
	public static nasa_neoImpactAdapter adapter_IMPACT;
	public static asteroidNewsAdapter adapter_NEWS;
	public static List<nasa_neo> List_NASA_RECENT;
	public static List<nasa_neo> List_NASA_UPCOMING;
	public static List<nasa_neoImpactEntity> List_NASA_IMPACT;
	public static List<newsEntity> List_NASA_News;

	
    public void loadEntityLists_NEO(String HTTPDATA){
	  	List_NASA_RECENT = AsteroidTrackerActivity.neo_AstroidFeed.getRecentList(HTTPDATA);
    	List_NASA_UPCOMING = AsteroidTrackerActivity.neo_AstroidFeed.getUpcomingList(HTTPDATA);
    }
		
    public void LoadAdapters_NEO(Context ctext){
    	adapter_RECENT = new nasa_neoArrayAdapter(ctext, R.layout.nasa_neolistview, List_NASA_RECENT, "RECENT");
    	adapter_UPCOMING = new nasa_neoArrayAdapter(ctext, R.layout.nasa_neolistview, List_NASA_UPCOMING, "UPCOMING");
    }
    
    public void loadEntityLists_IMPACT(String HTTPDATA){
    	List_NASA_IMPACT = AsteroidTrackerActivity.neo_AstroidFeed.getImpactList(HTTPDATA);
    }

    public void LoadAdapters_IMPACT(Context ctext){
    	adapter_IMPACT = new nasa_neoImpactAdapter(ctext, R.layout.nasa_neo_impact_listview, AsteroidTrackerActivity.List_NASA_IMPACT);
    }
    
    public void loadEntityLists_NEWS(String HTTPDATA){
    	List_NASA_News = AsteroidTrackerActivity.neo_AstroidFeed.parseNewsFeed(HTTPDATA);
    }
 
    public void LoadAdapters_NEWS(Context ctext){
		adapter_NEWS = new asteroidNewsAdapter(ctext, R.layout.jpl_asteroid_news, AsteroidTrackerActivity.List_NASA_News);
    }
}
