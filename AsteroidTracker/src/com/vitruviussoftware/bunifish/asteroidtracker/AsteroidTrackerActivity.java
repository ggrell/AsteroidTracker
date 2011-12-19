package com.vitruviussoftware.bunifish.asteroidtracker;

import android.net.Uri;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.vitruviussoftware.bunifish.asteroidtracker.R;
import nasa.neoAstroid.neoAstroidFeed;
import nasa.neoAstroid.impackRisk.nasa_neoImpactAdapter;
import nasa.neoAstroid.impackRisk.nasa_neoImpactEntity;
import nasa.neoAstroid.neo.nasa_neo;
import nasa.neoAstroid.neo.nasa_neoArrayAdapter;
import nasa.neoAstroid.news.AsteroidNewsProxy;
import nasa.neoAstroid.news.NewsDB_Adtapter;
import nasa.neoAstroid.neo.NeoDBAdapter;
import nasa.neoAstroid.news.asteroidNewsAdapter;
import nasa.neoAstroid.news.newsEntity;
import nasa.neoAstroid.neo.nasa_neo;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TabHost;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TabHost.TabSpec;

public class AsteroidTrackerActivity extends ListActivity {

	static nasa_neoArrayAdapter adapter_RECENT; 
	static nasa_neoArrayAdapter adapter_UPCOMING; 
	static nasa_neoImpactAdapter adapter_IMPACT;
	static asteroidNewsAdapter adapter_NEWS;
	public static List<nasa_neo> List_NASA_RECENT;
	public static List<nasa_neo> List_NASA_UPCOMING;
	public static List<nasa_neoImpactEntity> List_NASA_IMPACT;
	public static List<newsEntity> List_NASA_News;
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
	static AsteroidNewsProxy NewsProxy = new AsteroidNewsProxy();
	public static boolean refresh = false;
	ProgressDialog dialog;
	Handler handler;
	int closeDialog = 0;
	
	private NewsDB_Adtapter NewDBHelper; 
	private NeoDBAdapter NeoDBAdapter;
	
	//	private NewsDB_LastUpdate_Adapter newsUpdateHelper; 
	private Cursor cursor;
	
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

		NeoDBAdapter = new NeoDBAdapter(this);
		NeoDBAdapter.open();
		
		NewDBHelper = new NewsDB_Adtapter(this);
		NewDBHelper.open();
//		dbHelper.deleteAllArticles();
		processFeeds();
//		ProcessDBDataData();
    }

    //In settings
    //Add "check for updates when app startes"
    //Save Nasa Neo data (to view later)"  This allows you to view the NASA neo data offline.
    //Clear data (If data is present)
    //Notifications
    //--NEOs that pass about as close or closer than our moon!
    //--Really large ones
    
    public void updateNewsListView(){
	    AsteroidTrackerActivity.this.runOnUiThread(new Runnable() {
             @Override
             public void run() {
	    			AsteroidTrackerActivity.adapter_NEWS.notifyDataSetChanged();
             }
         });
    }
    public void updateNeoListView(){
	    AsteroidTrackerActivity.this.runOnUiThread(new Runnable() {
             @Override
             public void run() {
	    			AsteroidTrackerActivity.adapter_RECENT.notifyDataSetChanged();
             }
         });
    }
    private void ProcessDBDataData(){
    	Thread UpdateNewsFromDB = new Thread() {
    		public void run() {	 
    			AsteroidTrackerActivity.List_NASA_News = ProcessNEWS_DB();
    			LoadAdapters_NEWS();
    		    AsteroidTrackerActivity.this.runOnUiThread(new Runnable() {
 	               @Override
 	               public void run() {
// 	            	   dialog.setMessage("Loading NASA News Feed...");
 	            	   Log.i("DB", "Setting data: NEWS");
 	            	   SetAdapters_NEWS();
 	               }
 	           });
//    		    updateNewsListView();
//				closeDialog();
    		}};
		    UpdateNewsFromDB.start();

		    final Thread UpdateNTEST = new Thread() {
	    		public void run() {
	           	   Log.i("DB", "UpdateNTEST sleep: NEWS");
	    			try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
	    			ArrayList testing = new ArrayList();
	    			for(int i = 0; i < 10; i++){
	    				newsEntity test = new newsEntity();
	    				test.title 			= "TESTING"+i;
	    				test.description 	= "TESTING";
	    				test.artcileUrl 	= "TESTING";
	    				test.imageURL 		= "TESTING";
	    				test.pubDate 		= "TESTING";
	    				byte[] testArr 		= new byte[2];
	    				test.imageByteArray = testArr;
	    				AsteroidTrackerActivity.this.List_NASA_News.add(0, test);
//	    				testing.add(test);   				
	    			}
	    	    	ProcessNEWS_DB_AddArticles(testing);
//	    	    	AsteroidTrackerActivity.List_NASA_News = ProcessNEWS_DB();
	    	    	
	    			updateNewsListView();
	    	}};
	    	UpdateNTEST.start();
	    	
	    	
    	//Check NEO DB for data
    	//--If DB isnt emtpy, pull most recent 10 entries (if 10 are available)
    	//--Load the entries to the screen
    	//IF empty, check for new updates
    	
    	//Check News Data
    	//--If DB isnt emtpy, pull most recent 10 entries (if 10 are available)
    	//--Load the entries to the screen
    	//IF empty, check for new updates
    	
    	//Check for Impact Data
    	//--If DB isnt emtpy, pull most recent 10 entries (if 10 are available)
    	//--Load the entries to the screen
    	//IF empty, check for new updates
    	
    }
    
    private ArrayList<newsEntity> ProcessNEWS_DB(){
		ArrayList<newsEntity> ArticleList = new ArrayList<newsEntity>();
    	Log.i("DB", "ProcessNEWS_DB");
    	cursor = NewDBHelper.fetchAllArticles();
    	startManagingCursor(cursor);
    	int ArticleCount = cursor.getCount();
    	Log.i("DB", "Found this many Artciles ("+ArticleCount+")");
    	if(ArticleCount >= 1){
        	Log.i("DB", "Checking Articles ("+ArticleCount+")");
    		for(int i = 1; i < ArticleCount; i++){
    			newsEntity entity = new newsEntity();
    			Cursor article = NewDBHelper.fetchArticle(i);
    			startManagingCursor(article);
    			String title = article.getString(article.getColumnIndexOrThrow(NewDBHelper.KEY_TITLE));
    			Log.i("DB", "Title ("+title+")");
    			entity.title = article.getString(article.getColumnIndexOrThrow(NewDBHelper.KEY_TITLE));
    			entity.artcileUrl = article.getString(article.getColumnIndexOrThrow(NewDBHelper.KEY_URL));
    			entity.pubDate = article.getString(article.getColumnIndexOrThrow(NewDBHelper.KEY_DATE));
    			entity.description = article.getString(article.getColumnIndexOrThrow(NewDBHelper.KEY_DESCRIPTION));
    			entity.imageURL = article.getString(article.getColumnIndexOrThrow(NewDBHelper.KEY_IMAGE_URL));
    			entity.imageDrawable = entity.setDrawableWithByteArray(article.getBlob(article.getColumnIndexOrThrow(NewDBHelper.KEY_IMAGE)));
//    			Log.i("DB", "imageDrawable ("+entity.imageDrawable+")");
    			ArticleList.add(entity);
    			if(i == 10){
    				break;
    			}
    		}
    	}else{
    		Log.i("DB", "No Data News Table");
    		//No Data News Table
    	}
    	return ArticleList;
    }

    private ArrayList<nasa_neo> ProcessNEO_DB(){
		ArrayList<nasa_neo> NeoRecentList = new ArrayList<nasa_neo>();
    	Log.i("DB", "ProcessNEO_DB");
    	Cursor NEORecentCursor = NeoDBAdapter.fetchAllEntries();
    	startManagingCursor(NEORecentCursor);
    	int NEOEntryCount = NEORecentCursor.getCount();
    	Log.i("DB", "Found this many NEOEntries ("+NEOEntryCount+")");
    	if(NEOEntryCount >= 1){
        	Log.i("DB", "Checking NEOEntries ("+NEOEntryCount+")");
    		for(int i = 1; i < NEOEntryCount; i++){
    			nasa_neo entity = new nasa_neo();
    			Cursor NEOEntity = NeoDBAdapter.fetchNEOEntry(i);
    			startManagingCursor(NEOEntity);
    			String NAME = NEOEntity.getString(NEOEntity.getColumnIndexOrThrow(NeoDBAdapter.KEY_NAME));
    			Log.i("DB", "NAME ("+NAME+")");
    			entity.setName(NEOEntity.getString(NEOEntity.getColumnIndexOrThrow(NeoDBAdapter.KEY_NAME)));
    			entity.setDateStr(NEOEntity.getString(NEOEntity.getColumnIndexOrThrow(NeoDBAdapter.KEY_CLOSEAPPROACHDATE_STR)));
    			entity.setEstimatedDiameter(NEOEntity.getString(NEOEntity.getColumnIndexOrThrow(NeoDBAdapter.KEY_ESTIMATEDDIAMETER)));
    			entity.setHmagnitude(NEOEntity.getString(NEOEntity.getColumnIndexOrThrow(NeoDBAdapter.KEY_HMAGNITUDE)));
    			Log.i("DB", "####################KEY_MISSDISTANCE_AU ("+NEOEntity.getString(NEOEntity.getColumnIndexOrThrow(NeoDBAdapter.KEY_MISSDISTANCE_AU))+")");
    			entity.SetMissDistance_AU(NEOEntity.getString(NEOEntity.getColumnIndexOrThrow(NeoDBAdapter.KEY_MISSDISTANCE_AU)));
    			entity.setMissDistance_LD(NEOEntity.getString(NEOEntity.getColumnIndexOrThrow(NeoDBAdapter.KEY_MISSDISTANCE_LD)));
    			entity.setRelativeVelocity(NEOEntity.getString(NEOEntity.getColumnIndexOrThrow(NeoDBAdapter.KEY_RELETIVEVELOCITY)));
    			NeoRecentList.add(entity);
    			if(i == 10){
    				break;
    			}
    		}
    	}else{
    		Log.i("DB", "No Data News Table");
    		//No Data News Table
    	}
    	return NeoRecentList;
    }
    
    private void ProcessNEWS_DB_AddArticles(List<newsEntity> newsEntities){
    	String timeNow = Long.toString(System.currentTimeMillis());
		cursor = NewDBHelper.fetchAllArticles();
		startManagingCursor(cursor);
		Log.i("DB", "BEFORE_Count: "+ cursor.getCount());
		for(int i = 0; i < newsEntities.size(); i++){
      	   Log.i("DB", "Saving title:"+newsEntities.get(i).title);
      	   Log.i("DB", "Saving description:"+newsEntities.get(i).description);
      	   Log.i("DB", "Saving artcileUrl:"+newsEntities.get(i).artcileUrl);
      	   Log.i("DB", "Saving imgURL:"+newsEntities.get(i).imageURL);
      	   Log.i("DB", "Saving pubDate:"+newsEntities.get(i).pubDate);
      	 NewDBHelper.createNewsArticle(newsEntities.get(i).title, newsEntities.get(i).description, newsEntities.get(i).artcileUrl, 
      			 newsEntities.get(i).getDrawableImageByteArray(), newsEntities.get(i).imageURL, newsEntities.get(i).pubDate, timeNow);
	    cursor = NewDBHelper.fetchAllArticles();
		Log.i("DB", "AFTER_Count: "+ cursor.getCount());
		
		}    	
    }
    private void ProcessNEO_DB_AddArticles(List<nasa_neo> neoEntities){
    	
    	String timeNow = Long.toString(System.currentTimeMillis());
		cursor = NeoDBAdapter.fetchAllEntries();
		startManagingCursor(cursor);
		Log.i("DB", "BEFORE_Count: "+ cursor.getCount());
		for(int i = 0; i < neoEntities.size(); i++){
			Log.i("DB", "Saving Name:"+neoEntities.get(i).getName());
      	   	Log.i("DB", "Saving getMissDistance_AU:"+neoEntities.get(i).getMissDistance_AU());
      	   	Log.i("DB", "Saving getMissDistance_LD:"+neoEntities.get(i).getMissDistance_LD());
      	   	Log.i("DB", "Saving getRelativeVelocity:"+neoEntities.get(i).getRelativeVelocity());
      	   	Log.i("DB", "Saving getDateStr:"+neoEntities.get(i).getDateStr());
//      	   	Log.i("DB", "Saving getDate:"+neoEntities.get(i).getDate().toString());
      	   	Log.i("DB", "Saving getEstimatedDiameter:"+neoEntities.get(i).getEstimatedDiameter());
      	   	Log.i("DB", "Saving getHmagnitude:"+neoEntities.get(i).getHmagnitude());
      	   	Log.i("DB", "Saving getURL:"+neoEntities.get(i).getURL());
      	   	NeoDBAdapter.createNewsArticle(neoEntities.get(i).getName(), neoEntities.get(i).getDateStr(), 
      			neoEntities.get(i).getMissDistance_AU(), neoEntities.get(i).getMissDistance_LD(), neoEntities.get(i).getEstimatedDiameter(), 
      			neoEntities.get(i).getHmagnitude(), neoEntities.get(i).getRelativeVelocity(), neoEntities.get(i).getURL().toString(), timeNow);
      	cursor = NeoDBAdapter.fetchAllEntries();
		Log.i("DB", "AFTER_Count: "+ cursor.getCount());
		
		}    	
    }
    
    private void fillData() {
		cursor = NewDBHelper.fetchAllArticles();
		startManagingCursor(cursor);
		String[] from = new String[] { NewsDB_Adtapter.KEY_TITLE };
		Log.i("DB", "FETCH_SIZE: "+from.length);
		Log.i("DB", "FETCH_STRING: "+from[0].toLowerCase());
		Log.i("DB", "first cursor count: "+ cursor.getCount());
		Drawable imgURL = null;
		byte[] test = null;
		long created = NewDBHelper.createNewsArticle("TitleTest", "CoolArtcile", "test.blah.com", test, "test.com",  "10302011", "10292011");
		Log.i("DB", "created: "+created);
		cursor = NewDBHelper.fetchAllArticles();
		Log.i("DB", "next cursor count: "+ cursor.getCount());
		
		if(cursor.getCount() >= 1){
			Cursor article = NewDBHelper.fetchArticle(1);
			startManagingCursor(article);
			String title = article.getString(article.getColumnIndexOrThrow(NewDBHelper.KEY_TITLE));
			Log.i("DB", "NEWS_title: "+title);
			
			String LastModified = article.getString(article.getColumnIndexOrThrow(NewDBHelper.KEY_LASTMODIFIED));
			Log.i("DB", "NEWS_LastModified: "+LastModified);

			if(LastModified.equals("10302011")){
				Log.i("DB", "Up to date");
			}else{
				Log.i("DB", "Not UpToDate");
			}
		}	
		NewDBHelper.deleteAllArticles();
		cursor = NewDBHelper.fetchAllArticles();
		Log.i("DB", "next cursor count: "+ cursor.getCount());
		
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
    	progressDialog();
//      startTime = System.currentTimeMillis();
    	tabHost.setCurrentTab(0);
    	processImpactFeed();
		processAsteroidNewsFeed();
		processNEOFeed();
//		processStatsFeed();
    }
    
    public void processStatsFeed(){};
    
    public void closeDialog(){
    	closeDialog++;
    	if(closeDialog == 1){
    		handler.sendEmptyMessage(0);
    		}
    	}
    
    public void progressDialog(){
    	  dialog = ProgressDialog.show(this, "", "Loading NASA Asteroid Feed...", true);
    	  handler = new Handler() {
			public void handleMessage(Message msg) {
				dialog.dismiss();}
			};
    }
	
    public void processNEOFeed(){

    	Thread UpdateNEOFromDB = new Thread() {
    		public void run() {
    			Log.i("threadStatus", "Starting UpdateNEOFromDB");
    		   	ArrayList<nasa_neo> ProcessNEO_List = ProcessNEO_DB();
    			if(!ProcessNEO_List.isEmpty())
    			{				
    				AsteroidTrackerActivity.List_NASA_RECENT = ProcessNEO_List;
    				LoadAdapters_NEO();
    			    AsteroidTrackerActivity.this.runOnUiThread(new Runnable() {
    		               @Override
    		               public void run() {
//    		            	   dialog.setMessage("Loading NASA News Feed...");
    		            	   Log.i("DB", "Setting data: NEO");
    		            	   SetAdapters_NEO();
    		               }
    		           });
        			updateNeoListView();
    			}else{
    		    	   Log.i("DB", "No DB data: NEWS");
    			}
    		}};
    		UpdateNEOFromDB.start();
    		
			Thread checkUpdate = new Thread() {
			public void run() {	 
					if(!refresh){
						String HTTPDATA =  AsteroidTrackerActivity.neo_AstroidFeed.getAstroidFeedDATA(AsteroidTrackerActivity.neo_AstroidFeed.URL_NASA_NEO);
						loadEntityLists_NEO(HTTPDATA);
					}
					LoadAdapters_NEO();
					ProcessNEO_DB_AddArticles(List_NASA_RECENT);
					refresh = true;
				AsteroidTrackerActivity.this.runOnUiThread(new Runnable() {
		               @Override
		               public void run() {
			           	Log.i("HTTPFEED", "Setting data: NEO");
		            	   SetAdapters_NEO();
		               }
		           });
				Log.i("HTTPFEED", "closeing-Dialog:"+closeDialog);
				closeDialog();
			}};
//			checkUpdate.start();
			}
    public void processImpactFeed(){
		Thread ImpactUpdate = new Thread() {
			public void run() {			 
					if(!refresh){
						String HTTP_IMPACT_DATA =  AsteroidTrackerActivity.neo_AstroidFeed.getAstroidFeedDATA(AsteroidTrackerActivity.neo_AstroidFeed.URL_NASA_NEO_IMPACT_FEED);
						loadEntityLists_IMPACT(HTTP_IMPACT_DATA);
					}
					LoadAdapters_IMPACT();
					refresh = true;
				AsteroidTrackerActivity.this.runOnUiThread(new Runnable() {
		               @Override
		               public void run() {
		            	   dialog.setMessage("Loading NASA Impact Risk Feed...");
		            	   Log.i("HTTPFEED", "Setting data: IMPACT");
		            	   SetAdapters_IMPACT();
		               }
		           });
				Log.i("HTTPFEED", "closeing-Dialog:"+closeDialog);
				closeDialog();
			}};
			ImpactUpdate.start();
    }   
    public void processAsteroidNewsFeed(){
    	Thread UpdateNewsFromDB = new Thread() {
    		public void run() {
    			Log.i("threadStatus", "Starting UpdateNewsFromDB");
    			ArrayList<newsEntity> ProcessNEWS_List = ProcessNEWS_DB();
    			if(!ProcessNEWS_List.isEmpty())
    				{				
    					AsteroidTrackerActivity.List_NASA_News = ProcessNEWS_List;
    					LoadAdapters_NEWS();
    					AsteroidTrackerActivity.this.runOnUiThread(new Runnable() {
		               @Override
		               public void run() {
//		            	   dialog.setMessage("Loading NASA News Feed...");
		            	   Log.i("DB", "Setting data: NEWS");
		            	   SetAdapters_NEWS();
		               }
		           });
    	    			updateNewsListView();
    				}else{
    					Log.i("DB", "No DB data: NEWS");
    				}
    		}};
		UpdateNewsFromDB.start();

	    Thread NewsUpdate = new Thread() {
			public void run() {
				Log.i("threadStatus", "Starting NewsUpdate");
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
					if(!refresh){
						String HTTP_NEWS_DATA = AsteroidTrackerActivity.neo_AstroidFeed.getAstroidFeedDATA(AsteroidTrackerActivity.neo_AstroidFeed.URL_JPL_AsteroidNewsFeed);
							loadEntityLists_NEWS(HTTP_NEWS_DATA);			
					}
					LoadAdapters_NEWS();
					refresh = true;
					if(!List_NASA_News.isEmpty()){
						ProcessNEWS_DB_AddArticles(List_NASA_News);						
					}

					AsteroidTrackerActivity.this.runOnUiThread(new Runnable() {
		               @Override
		               public void run() {
		            	   dialog.setMessage("Loading NASA News Feed...");
		            	   Log.i("HTTPFEED", "Setting data: NEWS");
		            	   SetAdapters_NEWS();
		               }
		           });
				Log.i("HTTPFEED", "closeing-Dialog:"+closeDialog);
				closeDialog();
				updateNewsListView();
			}};
			NewsUpdate.start();
    }

    public void loadEntityLists_NEO(String HTTPDATA){
	  	AsteroidTrackerActivity.List_NASA_RECENT = AsteroidTrackerActivity.neo_AstroidFeed.getRecentList(HTTPDATA);
    	AsteroidTrackerActivity.List_NASA_UPCOMING = AsteroidTrackerActivity.neo_AstroidFeed.getUpcomingList(HTTPDATA);
    }
    public void loadEntityLists_IMPACT(String HTTPDATA){
    	AsteroidTrackerActivity.List_NASA_IMPACT = AsteroidTrackerActivity.neo_AstroidFeed.getImpactList(HTTPDATA);
    }
    public void loadEntityLists_NEWS(String HTTPDATA){
    	AsteroidTrackerActivity.List_NASA_News = AsteroidTrackerActivity.NewsProxy.parseNewsFeed(HTTPDATA);
    }

    public void LoadAdapters_NEO(){
    	Log.i("NEO", "Calling adapter_RECENT");
    	AsteroidTrackerActivity.this.adapter_RECENT = new nasa_neoArrayAdapter(AsteroidTrackerActivity.this, R.layout.nasa_neolistview, AsteroidTrackerActivity.List_NASA_RECENT);
    	Log.i("NEO", "Calling adapter_UPCOMING");
    	AsteroidTrackerActivity.this.adapter_UPCOMING = new nasa_neoArrayAdapter(AsteroidTrackerActivity.this, R.layout.nasa_neolistview, AsteroidTrackerActivity.List_NASA_UPCOMING);
    }
    public void LoadAdapters_IMPACT(){
    	AsteroidTrackerActivity.this.adapter_IMPACT = new nasa_neoImpactAdapter(AsteroidTrackerActivity.this, R.layout.nasa_neo_impact_listview, AsteroidTrackerActivity.List_NASA_IMPACT);
    }
    public void LoadAdapters_NEWS(){
		AsteroidTrackerActivity.adapter_NEWS = new asteroidNewsAdapter(AsteroidTrackerActivity.this, R.layout.jpl_asteroid_news, AsteroidTrackerActivity.List_NASA_News);
    }
    
    public void SetAdapters_NEO(){
    	setListAdapter(adapter_RECENT);
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
		        	ls2_ListView_Upcoming.setAdapter(AsteroidTrackerActivity.adapter_UPCOMING);
		            return ls2_ListView_Upcoming;
		        }       
		    });
    }
    public void SetAdapters_IMPACT(){
    	TabSpec3_Impact.setContent(new TabHost.TabContentFactory(){
	        public View createTabContent(String tag)
	        {
	        	ls3_ListView_Impact.setAdapter(AsteroidTrackerActivity.adapter_IMPACT);
	        	ls3_ListView_Impact.setOnItemClickListener(ImpactRiskClickListener);
	        	return ls3_ListView_Impact;
	        }       
	    });	
    }
    public void SetAdapters_NEWS(){
    	TabSpec4_News.setContent(new TabHost.TabContentFactory(){
   	        public View createTabContent(String tag)
   	        {
   	        	ls4_ListView_News.setAdapter(AsteroidTrackerActivity.adapter_NEWS);
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
		Intent i = new Intent(AsteroidTrackerActivity.this, about.class);
        startActivity(i);	
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