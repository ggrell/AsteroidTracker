package httpParse;

import com.vitruviussoftware.bunifish.asteroidtracker.AsteroidTrackerActivity;
import com.vitruviussoftware.bunifish.asteroidtracker.ContentManager;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

public class DownloadManager {

	public static final String URL_NEOMain="http://neo.jpl.nasa.gov/";
	public static final String URL_NEOImpact_base="http://neo.jpl.nasa.gov/risk/";	
	public static final String URL_NEOImpact_OrbitalBase="http://ssd.jpl.nasa.gov/sbdb.cgi?sstr={NEONAME};orb=1";			
	public static final String URL_NASA_NEO = "http://neo.jpl.nasa.gov/ca/";
	public static final String URL_NASA_NEO_IMPACT_FEED = "http://neo.jpl.nasa.gov/risk/";
	public static final String URL_JPL_AsteroidNewsFeed="http://www.jpl.nasa.gov/multimedia/rss/asteroid.xml";
	ContentManager contentManager = new ContentManager();
	LoadingDialogHelper dialogHelper = new LoadingDialogHelper();
	
	public DownloadManager(final Activity parentActivity){
		Thread Download_NEO = new Thread() {
			public void run() {	 
				if(!DownloadHelper.refresh){
					String data = getData(URL_NEOMain);
					contentManager.loadEntityLists_NEO(data);
					DownloadHelper.refresh = true;
					parentActivity.runOnUiThread(new Runnable() {
			               public void run() {
			            	   dialogHelper.setMessage("Loading NASA Impact Risk Feed...");
			            	   Log.i("HTTPFEED", "Setting data: IMPACT");
//			            	   SetAdapters_IMPACT();
			               }
					});
					DownloadHelper.Done++;
				}
			}};
			Download_NEO.start();
		Thread Download_Impact = new Thread() {
			public void run() {	 
				if(!DownloadHelper.refresh){
					String data = getData(URL_NASA_NEO_IMPACT_FEED);
					contentManager.loadEntityLists_IMPACT(data);
					DownloadHelper.refresh = true;
					DownloadHelper.Done++;
				}
			}};
			Download_Impact.start();
		Thread Download_News = new Thread() {
			public void run() {	 
				if(!DownloadHelper.refresh){
					String data = getData(URL_JPL_AsteroidNewsFeed);
					contentManager.loadEntityLists_NEWS(data);
					DownloadHelper.refresh = true;
					DownloadHelper.Done++;
				}
			}};
			Download_News.start();
	
	}
	public static void main(String[] args){
		DownloadManager checkIt = new DownloadManager();
		LoadingDialogHelper dialogHelper = new LoadingDialogHelper();
		dialogHelper.progressDialog(this);
	}
	
	
	public void startDownload(String URL){
		String HTTPDATA = getData(URL);
		
	}
	
	public String getData(String URL) {
		Log.i("HTTPFEED", "Getting data: "+URL);
		String data = common.getHTTPData(URL);
//		Log.i("neo", "DATA: "+data);
		return data;
}
	
}
