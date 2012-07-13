package activities;

import adapters.NewsAdapter;
import adapters.NearEarthObjectAdapter;
import adapters.ImpactAdapter;
import android.net.Uri;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import service.AsteroidTrackerService;
import service.ContentManager;
import service.neoAstroidFeed;
import utils.LoadingDialogHelper;
import com.vitruviussoftware.bunifish.asteroidtracker.R;
import com.vitruviussoftware.bunifish.asteroidtracker.R.drawable;
import com.vitruviussoftware.bunifish.asteroidtracker.R.id;
import com.vitruviussoftware.bunifish.asteroidtracker.R.layout;
import com.vitruviussoftware.bunifish.asteroidtracker.R.menu;
import domains.NearEarthObject;
import domains.Impact;
import domains.News;
//import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TabHost.TabSpec;

public class AsteroidTrackerActivity extends ListActivity {

	ListView ls1_ListView_Recent;
	ListView ls2_ListView_Upcoming;
	ListView ls3_ListView_Impact;
	ListView ls4_ListView_News;
	View ImgView;
	public static Drawable drawable;
	static TabHost tabHost;
	TabSpec TabSpec1_Recent;
	TabSpec TabSpec2_Upcoming;
	TabSpec TabSpec3_Impact;
	TabSpec TabSpec4_News;
	public static boolean refresh = false;
	int closeDialog = 0;
	boolean UseGitService;
	Handler handler;
	ProgressDialog dialog;
    NotificationManager mNotificationManager;
    public static ContentManager contentManager = new ContentManager();
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState){
    	super.onCreate(savedInstanceState);
		setContentView(R.layout.asteroid_main_tablayout);
		setTitle("Asteroid Tracker");
		Resources res = getResources();
		drawable = res.getDrawable(R.drawable.asteroid);
		TabAndListViewSetup();
		processFeeds();
    }

    public void TabAndListViewSetup(){
    	tabHost=(TabHost)findViewById(R.id.tabHost);
		tabHost.setup();
		tabHost.getTabWidget().setDividerDrawable(R.drawable.silverbar);
		
		ls1_ListView_Recent = new ListView(AsteroidTrackerActivity.this);   
		TabSpec1_Recent=tabHost.newTabSpec("Tab 1");
		TabSpec1_Recent.setIndicator("RECENT");
		TabSpec1_Recent.setContent(R.id.tab1);
		
		ls2_ListView_Upcoming = new ListView(AsteroidTrackerActivity.this);  
		TabSpec2_Upcoming=tabHost.newTabSpec("Tab 2");
		TabSpec2_Upcoming.setIndicator("UPCOMING");
		TabSpec2_Upcoming.setContent(R.id.tab2);
		
		ls3_ListView_Impact = new ListView(AsteroidTrackerActivity.this);  
		TabSpec3_Impact=tabHost.newTabSpec("Tab 3");
		TabSpec3_Impact.setIndicator("IMPACT RISK");
		TabSpec3_Impact.setContent(R.id.tab3);
		
		ls4_ListView_News = new ListView(AsteroidTrackerActivity.this); 
		TabSpec4_News=tabHost.newTabSpec("Tab 4");
		TabSpec4_News.setIndicator("NEWS");
		TabSpec4_News.setContent(R.id.tab4);
		
		tabHost.addTab(TabSpec1_Recent);
		tabHost.addTab(TabSpec2_Upcoming);
		tabHost.addTab(TabSpec3_Impact);
		tabHost.addTab(TabSpec4_News);
    }
    
    public void processFeeds(){
    	UseGitService = AsteroidTrackerService.IsGitServiceAvailable();
    	LoadingDialogHelper.progressDialog(this);
    	tabHost.setCurrentTab(0);
    	processImpactFeed();
//		processNEOFeed();
		processAsteroidNewsFeed();
		processNEOFeedRecent();
		processNEOFeedUpcoming();
    	
    }
    
    public void processNEOFeed(){
			Thread checkUpdate = new Thread() {
			public void run() {
				Log.i("gitservice", "processNEOFeed");
				if(AsteroidTrackerService.IsGitServiceAvailable()){
					Log.i("gitservice", "Start main dl");
					contentManager.List_NASA_RECENT = AsteroidTrackerService.getRecentList();
					contentManager.List_NASA_UPCOMING = AsteroidTrackerService.getUpcomingList();
					contentManager.LoadAdapters_NEO_Recent(AsteroidTrackerActivity.this);
					contentManager.LoadAdapters_NEO_Upcoming(AsteroidTrackerActivity.this);
					Log.i("gitservice", "dl done");
				} else{
						if(!refresh){
							String HTTPDATA =  contentManager.neo_AstroidFeed.getAstroidFeedDATA(contentManager.neo_AstroidFeed.URL_NASA_NEO);
							contentManager.loadEntityLists_NEO(HTTPDATA);
						}
//						contentManager.LoadAdapters_NEO();
						refresh = true;
					}
				AsteroidTrackerActivity.this.runOnUiThread(new Runnable() {
		               public void run() {
		            	   LoadingDialogHelper.dialog.setMessage("Loading NASA NEO Feed...");
		            	   Log.i("HTTPFEED", "Setting data: NEO");
		            	   SetAdapters_NEO();
		               }
		           });
				Log.i("HTTPFEED", "closeing-Dialog:"+closeDialog);
				LoadingDialogHelper.closeDialog();
			}};
			checkUpdate.start();
			}
    
    public void processNEOFeedRecent(){
		Thread checkUpdate = new Thread() {
		public void run() {
			Log.i("gitservice", "processNEOFeed");
			if(UseGitService){
			    contentManager.List_NASA_RECENT = AsteroidTrackerService.getRecentList();
			    contentManager.LoadAdapters_NEO_Recent(AsteroidTrackerActivity.this);
			} else{
//					if(!refresh){
//						String HTTPDATA =  AsteroidTrackerActivity.neo_AstroidFeed.getAstroidFeedDATA(AsteroidTrackerActivity.neo_AstroidFeed.URL_NASA_NEO);
//						loadEntityLists_NEO(HTTPDATA);
//					}
//					LoadAdapters_NEO();
//					refresh = true;
				}
			AsteroidTrackerActivity.this.runOnUiThread(new Runnable() {
	               public void run() {
	            	   LoadingDialogHelper.dialog.setMessage("Loading NASA NEO Feed...");
	            	   Log.i("HTTPFEED", "Setting data: NEO");
	            	   SetAdapters_NEO();
	               }
	           });
			Log.i("HTTPFEED", "closeing-Dialog:"+closeDialog);
			LoadingDialogHelper.closeDialog();
		}};
		checkUpdate.start();
		}
    
    public void processNEOFeedUpcoming(){
		Thread checkUpdate = new Thread() {
		public void run() {
			Log.i("gitservice", "processNEOFeedUpcoming");
			if(UseGitService){
			    contentManager.List_NASA_UPCOMING = AsteroidTrackerService.getUpcomingList();
			    contentManager.LoadAdapters_NEO_Upcoming(AsteroidTrackerActivity.this);
			} else{
//					if(!refresh){
//						String HTTPDATA =  AsteroidTrackerActivity.neo_AstroidFeed.getAstroidFeedDATA(AsteroidTrackerActivity.neo_AstroidFeed.URL_NASA_NEO);
//						loadEntityLists_NEO(HTTPDATA);
//					}
//					LoadAdapters_NEO();
//					refresh = true;
				}
			AsteroidTrackerActivity.this.runOnUiThread(new Runnable() {
	               public void run() {
	            	   LoadingDialogHelper.dialog.setMessage("Loading NASA NEO Feed...");
	            	   Log.i("HTTPFEED", "Setting data: NEO");
	            	   SetAdapters_NEO();
	               }
	           });
			Log.i("HTTPFEED", "closeing-Dialog:"+closeDialog);
			LoadingDialogHelper.closeDialog();
		}};
		checkUpdate.start();
		}
    
    public void processImpactFeed(){
		Thread ImpactUpdate = new Thread() {
			public void run() {	
				if(UseGitService){
				    contentManager.List_NASA_IMPACT = AsteroidTrackerService.getImpactData();
				    contentManager.LoadAdapters_NEO_Impact(AsteroidTrackerActivity.this);
				} else{
//					if(!refresh){
//						String HTTP_IMPACT_DATA =  AsteroidTrackerActivity.neo_AstroidFeed.getAstroidFeedDATA(AsteroidTrackerActivity.neo_AstroidFeed.URL_NASA_NEO_IMPACT_FEED);
//						loadEntityLists_IMPACT(HTTP_IMPACT_DATA);
//					}
//					LoadAdapters_IMPACT();
//					refresh = true;
				}
				AsteroidTrackerActivity.this.runOnUiThread(new Runnable() {
		               public void run() {
		            	   LoadingDialogHelper.dialog.setMessage("Loading NASA Impact Risk Feed...");
		            	   Log.i("HTTPFEED", "Setting data: IMPACT");
		            	   SetAdapters_IMPACT();
		               }
		           });
				Log.i("HTTPFEED", "closeing-Dialog:"+closeDialog);
				LoadingDialogHelper.closeDialog();
			}};
			ImpactUpdate.start();
    }
    
    public void processAsteroidNewsFeed(){
		Thread NewsUpdate = new Thread() {
			public void run() {
				if(UseGitService){
				    contentManager.List_NASA_News = AsteroidTrackerService.getLatestNews();
				    contentManager.LoadAdapters_NEO_News(AsteroidTrackerActivity.this);
				} else{	 
//					if(!refresh){
//						String HTTP_NEWS_DATA = AsteroidTrackerActivity.neo_AstroidFeed.getAstroidFeedDATA(AsteroidTrackerActivity.neo_AstroidFeed.URL_JPL_AsteroidNewsFeed);
//						loadEntityLists_NEWS(HTTP_NEWS_DATA);
//					}
//					LoadAdapters_NEWS();
//					refresh = true;
				}
			    AsteroidTrackerActivity.this.runOnUiThread(new Runnable() {
		               public void run() {
		            	   LoadingDialogHelper.dialog.setMessage("Loading NASA News Feed...");
		            	   Log.i("HTTPFEED", "Setting data: NEWS");
		            	   SetAdapters_NEWS();
		               }
		           });
				Log.i("HTTPFEED", "closeing-Dialog:"+closeDialog);
				LoadingDialogHelper.closeDialog();
			}};
			NewsUpdate.start();
    }

    public void loadEntityLists_NEO(String HTTPDATA){
        contentManager.List_NASA_RECENT = contentManager.neo_AstroidFeed.getRecentList(HTTPDATA);
        contentManager.List_NASA_UPCOMING = contentManager.neo_AstroidFeed.getUpcomingList(HTTPDATA);
    }
    
    public void loadEntityLists_IMPACT(String HTTPDATA){
        contentManager.List_NASA_IMPACT = contentManager.neo_AstroidFeed.getImpactList(HTTPDATA);
    }
    
    public void loadEntityLists_NEWS(String HTTPDATA){
        contentManager.List_NASA_News = contentManager.neo_AstroidFeed.parseNewsFeed(HTTPDATA);
    }
       
    public void SetAdapters_NEO(){
    	setListAdapter(ContentManager.adapter_RECENT);
//    		spec1.setContent(new TabHost.TabContentFactory(){
//   		        public View createTabContent(String tag)
//   		        {
//   		        	ls1.setAdapter(AsteroidTrackerActivity.adapter);
//   		            return ls1;
//   		        }       
//   		    });
    		TabSpec2_Upcoming.setContent(new TabHost.TabContentFactory(){
		        public View createTabContent(String tag)
		        {
		        	ls2_ListView_Upcoming.setAdapter(ContentManager.adapter_UPCOMING);
		            return ls2_ListView_Upcoming;
		        }       
		    });
//    		checkAlerts();
    }
    
    public void SetAdapters_IMPACT(){
    	TabSpec3_Impact.setContent(new TabHost.TabContentFactory(){
	        public View createTabContent(String tag)
	        {
	        	ls3_ListView_Impact.setAdapter(ContentManager.adapter_IMPACT);
	        	ls3_ListView_Impact.setOnItemClickListener(ImpactRiskClickListener);
	        	return ls3_ListView_Impact;
	        }       
	    });	
    }
    
    public void SetAdapters_NEWS(){
    	TabSpec4_News.setContent(new TabHost.TabContentFactory(){
   	        public View createTabContent(String tag)
   	        {
   	        	ls4_ListView_News.setAdapter(ContentManager.adapter_NEWS);
   	        	ls4_ListView_News.setOnItemClickListener(Asteroid_NewsArticle_ClickListener);
   	        	return ls4_ListView_News;
   	        }       
   	    });	
    }
    
    public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.mainmenu, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.quit:
			finish();
			return true;
		case R.id.about:
			openAbout();
			return true;
		case R.id.refresh:
			refresh = false;
			closeDialog = 0;
			processFeeds();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
}

	public void openAbout() {
		Intent i = new Intent(AsteroidTrackerActivity.this, About.class);
        startActivity(i);	
       }
	
	public void checkAlerts(){
		Iterator<NearEarthObject> iterator = contentManager.List_NASA_UPCOMING.iterator();
//		nasa_neo ntest = List_NASA_UPCOMING.get(0);
		while (iterator.hasNext()) {
			NearEarthObject ntest = iterator.next();
//			Log.v("UPCOMING", "ALERT TEST");
//			Log.v("UPCOMING", ntest.getAlertMSG());
			if(ntest.getAlertMSG() != ""){
				Log.v("UPCOMING", ntest.getName());
				Log.v("UPCOMING", ntest.getAlertMSG());	
				callNotifyService(setupNotificationMessage("AsteroidAlert", ntest.getName()+" is passing closer than our moon"));
			}
			}

//		}

	}
	
	
	public Notification setupNotificationMessage(String notificationTitle, String notifiationText){
		Intent intent = new Intent(this,AsteroidTrackerActivity.class);  
		  Notification notification = new Notification(R.drawable.asteroid, "AsteroidTracker - ALERT", System.currentTimeMillis());  
		  notification.flags |= Notification.FLAG_AUTO_CANCEL;
		  PendingIntent activity = PendingIntent.getActivity(this, 0, intent, 0);
		  notification.setLatestEventInfo(this,notificationTitle,notifiationText, PendingIntent.getActivity(this.getBaseContext(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)); 
		  return notification;
		}
	public void callNotifyService(Notification notification) {
		mNotificationManager.notify(0, notification);	
	}
	
	public OnItemClickListener ImpactRiskClickListener = new OnItemClickListener() {
		public void onItemClick(AdapterView parent, View view, int position, long id) {
		    Object object = AsteroidTrackerActivity.this.ls3_ListView_Impact.getAdapter().getItem(position);	
		    Impact asteroidEntity = (Impact) object;
		    Intent openArticleView = new Intent(AsteroidTrackerActivity.this, activities.ImpactRiskDetailView.class);
			Log.i("track", Integer.toString(position));
		    openArticleView.putExtra("position", position);
		    startActivity(openArticleView);
        };
	};

	public OnItemClickListener Asteroid_NewsArticle_ClickListener = new OnItemClickListener() {
		public void onItemClick(AdapterView parent, View view, int position, long id) {
		    Object object = AsteroidTrackerActivity.this.ls4_ListView_News.getAdapter().getItem(position);	
		    News asteroidEntity = (News) object;
		    Intent i = new Intent(Intent.ACTION_VIEW);
			i.setData(Uri.parse(asteroidEntity.artcileUrl));
			startActivity(i);
			
        };
	};
}