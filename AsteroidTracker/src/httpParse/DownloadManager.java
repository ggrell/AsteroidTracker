package httpParse;

import com.vitruviussoftware.bunifish.asteroidtracker.AsteroidTrackerActivity;
import com.vitruviussoftware.bunifish.asteroidtracker.ContentManager;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.ListView;

public class DownloadManager {

	public static final String URL_NEOMain="http://neo.jpl.nasa.gov/";
	public static final String URL_NEOImpact_base="http://neo.jpl.nasa.gov/risk/";	
	public static final String URL_NEOImpact_OrbitalBase="http://ssd.jpl.nasa.gov/sbdb.cgi?sstr={NEONAME};orb=1";			
	public static final String URL_NASA_NEO = "http://neo.jpl.nasa.gov/ca/";
	public static final String URL_NASA_NEO_IMPACT_FEED = "http://neo.jpl.nasa.gov/risk/";
	public static final String URL_JPL_AsteroidNewsFeed="http://www.jpl.nasa.gov/multimedia/rss/asteroid.xml";
	
	public static boolean useYqlService = true;
	public static final String URL_YQL_NEO = "";
	// Json call, unstructured, not as usable.
	// public static final String URL_YQL_Impact = "http://query.yahooapis.com/v1/public/yql?q=SELECT%20*%20FROM%20html%20WHERE%20url%3D%22http%3A%2F%2Fneo.jpl.nasa.gov%2Frisk%2F%22%20and%20xpath%3D%22%2F%2Ftable%5B2%5D%2F%2Ftt%22&format=json&callback=cbfunc";
	public static final String URL_YQL_Impact = "http://query.yahooapis.com/v1/public/yql?q=SELECT%20*%20FROM%20html%20WHERE%20url%3D%22http%3A%2F%2Fneo.jpl.nasa.gov%2Frisk%2F%22%20and%20xpath%3D%22%2F%2Ftable%5B2%5D%2F%2Ftt%22";
	public static final String URL_YQL_News = "";
	public static String DownloadState_Impact = "yql";
	
	ContentManager contentManager = new ContentManager();

	public DownloadManager(final Activity parentActivity, ListView view){

		Thread Download_NEO = new Thread() {
			public void run() {	 
				if(!DownloadHelper.refresh){
						String data = getData(URL_NASA_NEO);
						contentManager.loadEntityLists_NEO(data);
						DownloadHelper.refresh = true;	
				}
				contentManager.LoadAdapters_NEO(parentActivity);
					parentActivity.runOnUiThread(new Runnable() {
			               public void run() {
			            	   LoadingDialogHelper.dialog.setMessage("Loading NASA NEO Feed...");
			            	   Log.i("HTTPFEED", "Setting data: IMPACT");
			            	   if(parentActivity instanceof AsteroidTrackerActivity){
				            	   ((AsteroidTrackerActivity) parentActivity).SetAdapters_NEO_Recent();
			            	   }
			            	   ContentManager.adapter_RECENT.notifyDataSetChanged();
			            	   ContentManager.adapter_UPCOMING.notifyDataSetChanged();
			               }
					});
					LoadingDialogHelper.closeDialog();
			}};
		Download_NEO.start();
		
		Thread Download_Impact = new Thread() {
			public void run() {	 
				if(!DownloadHelper.refresh){
					String data = DownloadFeed(URL_YQL_Impact, URL_NASA_NEO_IMPACT_FEED);
//					String data = getData(URL_NASA_NEO_IMPACT_FEED);
					contentManager.loadEntityLists_IMPACT(data);
					DownloadHelper.refresh = true;
				}
				contentManager.LoadAdapters_IMPACT(parentActivity);
					parentActivity.runOnUiThread(new Runnable() {
			               public void run() {
			            	   LoadingDialogHelper.dialog.setMessage("Loading NASA Impact Risk Feed...");
			            	   Log.i("HTTPFEED", "Setting data: IMPACT");
//			            	   if(parentActivity instanceof AsteroidTrackerActivity){
//				            	   ((AsteroidTrackerActivity) parentActivity).SetAdapters_IMPACT();
//			            	   }
			            	   ContentManager.adapter_IMPACT.notifyDataSetChanged();
			               }
					});
					LoadingDialogHelper.closeDialog();
			}};
		Download_Impact.start();
		
		Thread Download_News = new Thread() {
				public void run() {	 
					if(!DownloadHelper.refresh){
						String data = getData(URL_JPL_AsteroidNewsFeed);
						contentManager.loadEntityLists_NEWS(data);
						DownloadHelper.refresh = true;
					}
					contentManager.LoadAdapters_NEWS(parentActivity);
						parentActivity.runOnUiThread(new Runnable() {
				               public void run() {
				            	   LoadingDialogHelper.dialog.setMessage("Loading NASA News Feed...");
				            	   Log.i("HTTPFEED", "Setting data: IMPACT");
//				            	   if(parentActivity instanceof AsteroidTrackerActivity){
//					            	   ((AsteroidTrackerActivity) parentActivity).SetAdapters_NEWS();
//				            	   }
				            	   ContentManager.adapter_NEWS.notifyDataSetChanged();
				               }
						});
						LoadingDialogHelper.closeDialog();
				}};
		Download_News.start();
	}
	
	public DownloadManager(){}
	
	public String DownloadFeed(String UrlYql, String NasaUrl){
		String data = getData(UrlYql);
		DownloadState_Impact = "yql";
		if (data.equals("Timeout")){
			DownloadState_Impact = "nasa";
			Log.i("yql", "Timeout calling yql, defaulting to direct call");
			data = getData(NasaUrl);
		}
		return data;
	}
	
	public String getData(String URL) {
		Log.i("HTTPFEED", "Getting data: "+URL);
		String data = common.getHTTPData(URL);
//		Log.i("neo", "DATA: "+data);
		return data;
}

}
