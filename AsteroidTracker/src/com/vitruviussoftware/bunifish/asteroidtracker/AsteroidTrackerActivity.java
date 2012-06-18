package com.vitruviussoftware.bunifish.asteroidtracker;

import android.net.Uri;
import android.os.Bundle;
import httpParse.DownloadHelper;
import httpParse.DownloadManager;
import httpParse.LoadingDialogHelper;
import com.vitruviussoftware.bunifish.asteroidtracker.R;
import nasa.neoAstroid.neoAstroidFeed;
import nasa.neoAstroid.impackRisk.nasa_neoImpactEntity;
import nasa.neoAstroid.news.newsEntity;
import android.app.ListActivity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
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
	static neoAstroidFeed neo_AstroidFeed = new neoAstroidFeed();
	ProgressDialog dialog;
	Handler handler;
	int closeDialog = 0;
	NotificationManager mNotificationManager;
	DownloadManager dlmanager;
	//	callNotifyService(setupNotificationMessage("", ""));
	
//	long startTime;
//	long endTime;
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
//		mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);		
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
		
		SetAdapters_NEO_Recent();
		SetAdapters_NEO_Upcoming();
		SetAdapters_IMPACT();
		SetAdapters_NEWS();
		}
    
    public void processFeeds(){
		LoadingDialogHelper.progressDialog(this);
		NetworkUtil.netCheckin(this);
//      startTime = System.currentTimeMillis();
		dlmanager = new DownloadManager(this, this.getListView());
		tabHost.setCurrentTab(0);    	
    }
         
    public void SetAdapters_NEO_Recent(){
    	setListAdapter(ContentManager.adapter_RECENT);
//    	TabSpec1_Recent.setContent(new TabHost.TabContentFactory(){
//	        public View createTabContent(String tag)
//	        {
//	        	ls1_ListView_Recent.setAdapter(ContentManager.adapter_RECENT);
//	            return ls1_ListView_Recent;
//	        }  
//        });
    }
    public void SetAdapters_NEO_Upcoming(){
		TabSpec2_Upcoming.setContent(new TabHost.TabContentFactory(){
	        public View createTabContent(String tag)
	        {
	        	ls2_ListView_Upcoming.setAdapter(ContentManager.adapter_UPCOMING);
	            return ls2_ListView_Upcoming;
	        }       
	    });
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
			DownloadHelper.refresh = false;
			closeDialog = 0;
			processFeeds();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
}

	public void openAbout() {
		Intent i = new Intent(AsteroidTrackerActivity.this, about.class);
        startActivity(i);	
       }
	
	public void checkAlerts(){
//		Iterator<nasa_neo> iterator = List_NASA_UPCOMING.iterator();
////		nasa_neo ntest = List_NASA_UPCOMING.get(0);
//		while (iterator.hasNext()) {
//			nasa_neo ntest = iterator.next();
////			Log.v("UPCOMING", "ALERT TEST");
////			Log.v("UPCOMING", ntest.getAlertMSG());
//			if(ntest.getAlertMSG() != ""){
//				Log.v("UPCOMING", ntest.getName());
//				Log.v("UPCOMING", ntest.getAlertMSG());	
//				callNotifyService(setupNotificationMessage("AsteroidAlert", ntest.getName()+" is passing closer than our moon"));
//			}
//			}
//
////		}

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
		    nasa_neoImpactEntity asteroidEntity = (nasa_neoImpactEntity) object;
		    Intent openArticleView = new Intent(AsteroidTrackerActivity.this, nasa.neoAstroid.impackRisk.ImpactRiskDetailView.class);
			Log.i("track", Integer.toString(position));
		    openArticleView.putExtra("position", position);
		    startActivity(openArticleView);
        };
	};

	public OnItemClickListener Asteroid_NewsArticle_ClickListener = new OnItemClickListener() {
		public void onItemClick(AdapterView parent, View view, int position, long id) {
		    Object object = AsteroidTrackerActivity.this.ls4_ListView_News.getAdapter().getItem(position);	
		    newsEntity asteroidEntity = (newsEntity) object;
		    Intent i = new Intent(Intent.ACTION_VIEW);
			i.setData(Uri.parse(asteroidEntity.artcileUrl));
			startActivity(i);
			
        };
	};
}