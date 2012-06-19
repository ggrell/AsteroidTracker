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
	
    public void loadEntityLists_NEO_Recent(String HTTPDATA){
    	if(DownloadManager.DownloadState_NEO.equals("yql")){
    		List_NASA_RECENT = ParseNeoRecentFeed(HTTPDATA);
    	}else{
    		List_NASA_RECENT = AsteroidTrackerActivity.neo_AstroidFeed.getRecentList(HTTPDATA);
    	}
    }
    
    public void loadEntityLists_NEO_Upcoming(String HTTPDATA){
    	if(DownloadManager.DownloadState_NEO.equals("yql")){
    		List_NASA_UPCOMING = ParseNeoUpcomingFeed(HTTPDATA);
    	}else{
    		List_NASA_UPCOMING = AsteroidTrackerActivity.neo_AstroidFeed.getUpcomingList(HTTPDATA);
    	}
    }
	
    public void loadEntityLists_NEWS(String HTTPDATA){
    	List_NASA_News = AsteroidTrackerActivity.neo_AstroidFeed.parseNewsFeed(HTTPDATA);
    }
    
    public void LoadAdapters_NEO(Context ctext){
    	adapter_RECENT = new nasa_neoArrayAdapter(ctext, R.layout.nasa_neolistview, List_NASA_RECENT, "RECENT");
    	adapter_UPCOMING = new nasa_neoArrayAdapter(ctext, R.layout.nasa_neolistview, List_NASA_UPCOMING, "UPCOMING");
    }
    
    public void loadEntityLists_IMPACT(String HTTPDATA){
    	if(DownloadManager.DownloadState_Impact.equals("yql")){
    		Log.i("yql", "Need to parse yql xml");
    		List_NASA_IMPACT = ParseImpactFeed(HTTPDATA);
    	}else if(DownloadManager.DownloadState_Impact.equals("nasa")){
        	List_NASA_IMPACT = AsteroidTrackerActivity.neo_AstroidFeed.getImpactList(HTTPDATA);	
    	}
    }

    public void LoadAdapters_IMPACT(Context ctext){
    	adapter_IMPACT = new nasa_neoImpactAdapter(ctext, R.layout.nasa_neo_impact_listview, List_NASA_IMPACT);
    }
    
    public void LoadAdapters_NEWS(Context ctext){
		adapter_NEWS = new asteroidNewsAdapter(ctext, R.layout.jpl_asteroid_news, List_NASA_News);
    }

    public ArrayList ParseImpactFeed(String data){
    	ArrayList<nasa_neoImpactEntity> NEO_UPCOMINGList = new ArrayList();
		XmlParser xmlParser = new XmlParser(data);
		ArrayList<String> ImpactValues = xmlParser.getXpath("//tt/text()");
		ArrayList<String> ImpactValues_ImpactProb = xmlParser.getXpath("//tt/a/text()");
    	ImpactValues = trimArray(ImpactValues);
		int impactListSize = ImpactValues.size()/10;
		int bidx = 0;
//    	Log.v("ContentManager", "ParseImpactFeed: size:"+impactListSize);
//    	printArray(ImpactValues);
		for(int i = 0; i < impactListSize; i++){
//			Log.v("ContentManager", "ParseImpactFeed: bidx: "+bidx);
			List subImpactList = ImpactValues.subList(bidx, bidx+10);
//			Log.v("ContentManager", "ParseImpactFeed: name: "+i+") "+subImpactList.get(1).toString());
			nasa_neoImpactEntity impact = new nasa_neoImpactEntity();
			impact.setName(subImpactList.get(0).toString());
			impact.setYearRange(subImpactList.get(1).toString().trim());
			impact.setPotentialImpacts(subImpactList.get(2).toString().trim());
			impact.setImpactProbabilites(ImpactValues_ImpactProb.get(i));
			impact.setVInfinity(subImpactList.get(4).toString().trim());
			impact.setH_AbsoluteMag(subImpactList.get(5).toString().trim());
			impact.setEstimagesDiameter(subImpactList.get(6).toString().trim());
			impact.setPalermoScaleAve(subImpactList.get(7).toString().trim());
			impact.setPalermoScaleMax(subImpactList.get(8).toString().trim());
			impact.setTorinoScale(subImpactList.get(9).toString().trim());
			impact.setIcon(com.vitruviussoftware.bunifish.asteroidtracker.AsteroidTrackerActivity.drawable);
			NEO_UPCOMINGList.add(impact);
			bidx = bidx+10;
		}
    	return NEO_UPCOMINGList;
    }

    public ArrayList<nasa_neo>  ParseNeoRecentFeed(String data){
    	ArrayList<nasa_neo> recentlist = new ArrayList();
		ArrayList<nasa_neo> nasaNeoList_SortingList = new ArrayList<nasa_neo>();
    	if(data == null || data.length() == 0){
    		nasa_neo astroid = new nasa_neo();
			astroid = new nasa_neo();
			astroid.setName("Unable to retrieve Asteroid feed");
			recentlist.add(astroid);
		}else{
    	XmlParser xmlParser = new XmlParser(data);
		ArrayList<String> NEORecent_Values = xmlParser.getXpath("//td//font/text()");
		NEORecent_Values = trimArray(NEORecent_Values);
		int recentListSize = NEORecent_Values.size()/7;
		int bidx = 0;
		for(int i = 0; i < recentListSize; i++){
//			Log.v("ContentManager", "NEORecent Value: "+i+") "+NEORecent_Values.get(i));
			List subRecentList = NEORecent_Values.subList(bidx, bidx+7);
			nasa_neo astroid = new nasa_neo();
			astroid.setName(subRecentList.get(0).toString().trim());
			astroid.setDateStr(subRecentList.get(1).toString().trim());
			astroid.SetMissDistance_AU(subRecentList.get(2).toString().trim());
			astroid.setMissDistance_LD(subRecentList.get(3).toString().trim());
			astroid.setEstimatedDiameter(subRecentList.get(4).toString().trim());
			astroid.setHmagnitude(subRecentList.get(5).toString().trim());
			astroid.setRelativeVelocity(subRecentList.get(6).toString().trim());
			astroid.setIcon(com.vitruviussoftware.bunifish.asteroidtracker.AsteroidTrackerActivity.drawable);
			nasaNeoList_SortingList.add(astroid);
			bidx = bidx+7;
		}
	}
		for(int i = nasaNeoList_SortingList.size()-1; i >= 0; i--){
			recentlist.add(nasaNeoList_SortingList.get(i));
		}	
    	return recentlist;    	
    }
    
    public ArrayList<nasa_neo>  ParseNeoUpcomingFeed(String data){
    	Log.v("ContentManager", "trimArray start size: UpcomingFeed");
    	ArrayList<nasa_neo> upcominglist = new ArrayList();
		ArrayList<nasa_neo> nasaNeoList_SortingList = new ArrayList<nasa_neo>();
		if(data == null || data.length() == 0){
    		nasa_neo astroid = new nasa_neo();
			astroid = new nasa_neo();
			astroid.setName("Unable to retrieve Asteroid feed");
			upcominglist.add(astroid);
			}else{
				XmlParser xmlParser = new XmlParser(data);
				ArrayList<String> NEOUpcoming_Values = xmlParser.getXpath("//td//font/text()");
				NEOUpcoming_Values = trimArray(NEOUpcoming_Values);
				int recentListSize = NEOUpcoming_Values.size()/7;
				int bidx = 0;
				for(int i = 0; i < recentListSize; i++){
					List subList = NEOUpcoming_Values.subList(bidx, bidx+7);
					Log.v("ContentManager", "NEOUpcomingt Value: "+i+") "+NEOUpcoming_Values.get(i));
					nasa_neo astroid = new nasa_neo();
					astroid.setName(subList.get(0).toString().trim());
					astroid.setDateStr(subList.get(1).toString().trim());
					astroid.SetMissDistance_AU(subList.get(2).toString().trim());
					astroid.setMissDistance_LD(subList.get(3).toString().trim());
					astroid.setEstimatedDiameter(subList.get(4).toString().trim());
					astroid.setHmagnitude(subList.get(5).toString().trim());
					astroid.setRelativeVelocity(subList.get(6).toString().trim());
					astroid.setIcon(com.vitruviussoftware.bunifish.asteroidtracker.AsteroidTrackerActivity.drawable);
					bidx = bidx+7;
					upcominglist.add(astroid);
					if(i == 15){
						break;
					}
				}
			}
		return upcominglist;
    }   
 
    public ArrayList ParseNewsFeed(){
		return null;
    }

    public ArrayList trimArray (ArrayList arraytoTrim){
    	Log.v("ContentManager", "trimArray start size: "+arraytoTrim.size());
    	for(int i = 0; i < arraytoTrim.size(); i++){
			if(arraytoTrim.get(i).toString().trim().length() == 0){
//				Log.v("ContentManager", "Removing null: Values: "+i+") "+arraytoTrim.get(i));
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
