package com.vitruviussoftware.bunifish.asteroidtracker;

import httpParse.DownloadManager;
import httpParse.XmlParser;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;

import nasa.neoAstroid.nasa_neo;
import nasa.neoAstroid.nasa_neoArrayAdapter;
import nasa.neoAstroid.impackRisk.nasa_neoImpactAdapter;
import nasa.neoAstroid.impackRisk.nasa_neoImpactEntity;
import nasa.neoAstroid.news.asteroidNewsAdapter;
import nasa.neoAstroid.news.newsEntity;

@SuppressWarnings("unused")
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
    	if(DownloadManager.DownloadState_Impact.equals("yql")){
    		Log.i("yql", "Need to parse yql xml");
    		ParseImpactFeed(HTTPDATA);
    	}else if(DownloadManager.DownloadState_Impact.equals("nasa")){
        	List_NASA_IMPACT = AsteroidTrackerActivity.neo_AstroidFeed.getImpactList(HTTPDATA);	
    	}
    }

    public void LoadAdapters_IMPACT(Context ctext){
    	adapter_IMPACT = new nasa_neoImpactAdapter(ctext, R.layout.nasa_neo_impact_listview, List_NASA_IMPACT);
    }
    
    public void loadEntityLists_NEWS(String HTTPDATA){
    	List_NASA_News = AsteroidTrackerActivity.neo_AstroidFeed.parseNewsFeed(HTTPDATA);
    }
 
    public void LoadAdapters_NEWS(Context ctext){
		adapter_NEWS = new asteroidNewsAdapter(ctext, R.layout.jpl_asteroid_news, List_NASA_News);
    }

    public ArrayList ParseImpactFeed(String data){
    	ArrayList<nasa_neoImpactEntity> NEO_UPCOMINGList = new ArrayList();
		XmlParser xmlParser = new XmlParser(data);
		
		ArrayList<String> ImpactValues = xmlParser.getXpath("//tt/text()");
		ArrayList<String> ImpactValues_ImpactProb = xmlParser.getXpath("//tt/a/text()");
		int impactListSize = ImpactValues.size()/10;
		int bidx = 0;
		for(int i = 0; i < impactListSize; i++){
			List subImpactList = ImpactValues.subList(bidx, bidx+10);
			nasa_neoImpactEntity impact = new nasa_neoImpactEntity();
			Log.i("yql", "subImpactList:"+subImpactList.get(0).toString());
			impact.setName(subImpactList.get(0).toString());
			NEO_UPCOMINGList.add(impact);
			bidx = bidx+11;
		}
		for(int i = 0; i < NEO_UPCOMINGList.size(); i++){
			Log.i("yql", "NEO_UPCOMINGList NAMES: "+NEO_UPCOMINGList.get(i).getName());	
		}
//		Log.i("yql", "NEO_UPCOMINGList size:"+NEO_UPCOMINGList.size());
		Log.i("yql", "NEO_UPCOMINGList size:"+NEO_UPCOMINGList.get(0).getName());
////    	Log.i("yql", "name:"+name);
	  	Log.i("yql", "impactListSize:"+impactListSize);
    	Log.i("yql", "ImpactValues:"+ImpactValues.size());
    	Log.i("yql", "ImpactValues_ImpactProb:"+ImpactValues_ImpactProb.size());
    	return NEO_UPCOMINGList;
    }

}
