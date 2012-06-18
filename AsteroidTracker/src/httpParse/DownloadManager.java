package httpParse;

import com.vitruviussoftware.bunifish.asteroidtracker.AsteroidTrackerActivity;
import com.vitruviussoftware.bunifish.asteroidtracker.ContentManager;

import android.app.Activity;
import android.content.Context;
import android.os.Debug;
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
	public static final String URL_YQL_NEO_Recent = "http://query.yahooapis.com/v1/public/yql?q=SELECT%20*%20FROM%20html%20WHERE%20url%3D%22http%3A%2F%2Fneo.jpl.nasa.gov%2Fca%2F%22%20and%20xpath%3D%22%2F%2Ftr%5Bpreceding%3A%3Afont%5Btext()%3D'RECENT%20CLOSE%20APPROACHES%20TO%20EARTH'%5D%20and%20following%3A%3Afont%5Btext()%3D'UPCOMING%20CLOSE%20APPROACHES%20TO%20EARTH'%5D%5D%22%20";
	public static final String URL_YQL_NEO_Upcoming = "http://query.yahooapis.com/v1/public/yql?q=SELECT%20*%20FROM%20html%20WHERE%20url%3D%22http%3A%2F%2Fneo.jpl.nasa.gov%2Fca%2F%22%20and%20xpath%3D%22%2F%2Ftr%5Bpreceding%3A%3Afont%5Btext()%3D'UPCOMING%20CLOSE%20APPROACHES%20TO%20EARTH'%5D%20and%20following%3A%3Aimg%5B%40alt%3D'Menu'%5D%5D%22%20";
	public static final String URL_YQL_Impact = "http://query.yahooapis.com/v1/public/yql?q=SELECT%20*%20FROM%20html%20WHERE%20url%3D%22http%3A%2F%2Fneo.jpl.nasa.gov%2Frisk%2F%22%20and%20xpath%3D%22%2F%2Ftable%5B2%5D%2F%2Ftt%22";
	public static final String URL_YQL_News = "";
	public static String DownloadState_Impact = "yql";
	public static String DownloadState_NEO = "yql";
	public static String DownloadState_Upcoming = "yql";
	public static String DownloadState_News = "yql";
	//public static boolean IsYQLOn = false;
	
	ContentManager contentManager = new ContentManager();

	public DownloadManager(final Activity parentActivity, ListView view){
		Thread Download_NEO = new Thread() {
		public void run() {	 
				if(!DownloadHelper.refresh){
						String dataRecent = DownloadFeed(URL_YQL_NEO_Recent);
						String dataUpcoming = "";
						if(dataRecent.equals("Timeout")){
							Log.v("DownloadManager_Asteroid", "Timeout NEO, call NASA page");
							DownloadState_NEO = "nasa";
							dataRecent = DownloadFeed(URL_NASA_NEO);
						}
						if(DownloadState_NEO.equals("yql")){
							Log.v("DownloadManager_Asteroid", "Getting Upcoming data");
							dataUpcoming = DownloadFeed(URL_YQL_NEO_Upcoming);
						}
						if(DownloadState_NEO.equals("yql")){
							contentManager.loadEntityLists_NEO_Recent(dataRecent);
							contentManager.loadEntityLists_NEO_Upcoming(dataUpcoming);
						}else{
							contentManager.loadEntityLists_NEO_Recent(dataRecent);
							contentManager.loadEntityLists_NEO_Upcoming(dataRecent);							
						}
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
					String data = DownloadFeed(URL_YQL_Impact);
					if(data.equals("Timeout")){
						data = DownloadFeed(URL_NASA_NEO_IMPACT_FEED);
					}
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
	
	public String DownloadFeed(String URL){
		Log.i("DownloadFeed", "Getting data: "+URL);
		return common.getHTTPData(URL);
	}
	
	public String getData(String URL) {
		Log.i("HTTPFEED", "Getting data: "+URL);
		String data = common.getHTTPData(URL);
		return data;
}

}
