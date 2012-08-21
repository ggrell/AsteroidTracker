package activities;

import android.net.Uri;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import service.AsteroidTrackerService;
import service.ContentManager;
import utils.LoadingDialogHelper;
import utils.NetworkUtil;
import com.vitruviussoftware.bunifish.asteroidtracker.R;
import domains.NearEarthObject;
import domains.Impact;
import domains.News;
import android.app.ListActivity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
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
import android.widget.Toast;

public class AsteroidTrackerActivity extends ListActivity {

    ListView ls1_ListView_Recent;
    ListView ls2_ListView_Upcoming;
    ListView ls3_ListView_Impact;
    ListView ls4_ListView_News;
    View ImgView;
    public static Drawable drawable;
    public static Drawable newsDrawable;
    static TabHost tabHost;
    TabSpec TabSpec1_Recent;
    TabSpec TabSpec2_Upcoming;
    TabSpec TabSpec3_Impact;
    TabSpec TabSpec4_News;
    public static boolean refresh = false;
    boolean UseGitService;
    Handler handler;
    ProgressDialog dialog;
    NotificationManager mNotificationManager;
    public static ContentManager contentManager = new ContentManager();
    AsteroidTrackerService GitService = new AsteroidTrackerService();
    NetworkUtil nUtil = new NetworkUtil();
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.asteroid_main_tablayout);
        setTitle("Asteroid Tracker");
        TabAndListViewSetup();
        processFeeds();
        Resources res = getResources();
        drawable = res.getDrawable(R.drawable.asteroid);
        newsDrawable = res.getDrawable(R.drawable.asteroidnews);
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
        LoadingDialogHelper.progressDialog(this, "", "Checking Asteroid Service");
        boolean networkAvailable = nUtil.IsNetworkAvailable(this);
        if(networkAvailable){
            UseGitService = GitService.isGitServiceAvailable();
            Log.d("gitservice", "UseGitService: " + UseGitService);
            if(UseGitService){
                processNEOFeedRecent();
                processNEOFeedUpcoming();
            }else{
                LoadingDialogHelper.dialog.setMessage("Checking Asteroid NASA Service");
                processNEOFeed(); 
            }
            processImpactFeed();
            processAsteroidNewsFeed();            
        } else {
            LoadingDialogHelper.killDialog();
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(getApplicationContext(), "Network Unavailable, Please check your Mobile or Wifi Network", duration);
            toast.show();
        }
        tabHost.setCurrentTab(0);
    }

    public void processNEOFeed(){
            Thread checkUpdate = new Thread() {
            public void run() {
                    if(!refresh){
                        String HTTPDATA =  contentManager.neo_AstroidFeed.getAstroidFeedDATA(contentManager.neo_AstroidFeed.URL_NASA_NEO);
                        contentManager.loadEntityLists_NEO(HTTPDATA);
                        contentManager.List_NASA_RECENT = contentManager.neo_AstroidFeed.getRecentList(HTTPDATA);
                        contentManager.List_NASA_UPCOMING = contentManager.neo_AstroidFeed.getUpcomingList(HTTPDATA);
                    }
                    contentManager.loadAdapters_NEO_Recent(AsteroidTrackerActivity.this);
                    contentManager.loadAdapters_NEO_Upcoming(AsteroidTrackerActivity.this);
                    refresh = true;
                    AsteroidTrackerActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            LoadingDialogHelper.dialog.setMessage("Loading NASA NEO Feed...");
                            SetAdapters_NEO();
                        }
                     });
                LoadingDialogHelper.closeDialog();
                LoadingDialogHelper.closeDialog();
            }};
            checkUpdate.start();
            }

    public void processNEOFeedRecent(){
        Thread checkUpdate = new Thread() {
        public void run() {
            if(!refresh){
                contentManager.List_NASA_RECENT = (List<NearEarthObject>) GitService.getNEOList(GitService.URI_RECENT);
                contentManager.loadAdapters_NEO_Recent(AsteroidTrackerActivity.this);
                refresh = true;
            }
            AsteroidTrackerActivity.this.runOnUiThread(new Runnable() {
                   public void run() {
                       LoadingDialogHelper.dialog.setMessage("Loading NASA NEO Recent Feed...");
                       SetAdapters_NEO();
                   }
               });
            LoadingDialogHelper.closeDialog();
        }};
        checkUpdate.start();
        }
    
    public void processNEOFeedUpcoming(){
        Thread checkUpdate = new Thread() {
        public void run() {
            if(!refresh){
                contentManager.List_NASA_UPCOMING =  GitService.getNEOList(GitService.URI_UPCOMING);
                contentManager.loadAdapters_NEO_Upcoming(AsteroidTrackerActivity.this);
                refresh = true;
            }
            AsteroidTrackerActivity.this.runOnUiThread(new Runnable() {
                   public void run() {
                       LoadingDialogHelper.dialog.setMessage("Loading NASA NEO Upcoming Feed...");
                       SetAdapters_NEO();
                   }
               });
            LoadingDialogHelper.closeDialog();
        }};
        checkUpdate.start();
        }
    
    public void processImpactFeed(){
        Thread ImpactUpdate = new Thread() {
            public void run() {
                if(!refresh){
                    if(UseGitService){
                        contentManager.List_NASA_IMPACT = GitService.getImpactData();
                        contentManager.loadAdapters_NEO_Impact(AsteroidTrackerActivity.this);
                    } else{
                        String HTTP_IMPACT_DATA =  contentManager.neo_AstroidFeed.getAstroidFeedDATA(contentManager.neo_AstroidFeed.URL_NASA_NEO_IMPACT_FEED);
                        contentManager.List_NASA_IMPACT = contentManager.neo_AstroidFeed.getImpactList(HTTP_IMPACT_DATA);
                    }
                contentManager.loadAdapters_NEO_Impact(AsteroidTrackerActivity.this);
                refresh = true;
                }
                AsteroidTrackerActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        LoadingDialogHelper.dialog.setMessage("Loading NASA Impact Risk Feed...");
                        SetAdapters_IMPACT();
                    }
                });
                LoadingDialogHelper.closeDialog();
            }};
        ImpactUpdate.start();
    }
    
    public void processAsteroidNewsFeed(){
        Thread NewsUpdate = new Thread() {
            public void run() {
                if(!refresh){
                    if(UseGitService){
                        contentManager.List_NASA_News = GitService.getLatestNews();
                    } else {
                        String HTTP_NEWS_DATA = contentManager.neo_AstroidFeed.getAstroidFeedDATA(contentManager.neo_AstroidFeed.URL_JPL_AsteroidNewsFeed);
                        contentManager.List_NASA_News = contentManager.neo_AstroidFeed.parseNewsFeed(HTTP_NEWS_DATA);
                    }
                }
                contentManager.loadAdapters_NEO_News(AsteroidTrackerActivity.this);
                refresh = true;
                AsteroidTrackerActivity.this.runOnUiThread(new Runnable() {
                       public void run() {
                           LoadingDialogHelper.dialog.setMessage("Loading NASA News Feed...");
                           SetAdapters_NEWS();
                       }
                   });
                LoadingDialogHelper.closeDialog();
            }};
            NewsUpdate.start();
    }

    public void SetAdapters_NEO(){
        setListAdapter(contentManager.adapter_RECENT);
//        TabSpec1_Recent.setContent(new TabHost.TabContentFactory(){
//            public View createTabContent(String tag)
//            {
//                ls1_ListView_Recent.setAdapter(contentManager.adapter_RECENT);
//                return ls1_ListView_Recent;
//            }       
//        });
            TabSpec2_Upcoming.setContent(new TabHost.TabContentFactory(){
                public View createTabContent(String tag)
                {
                    ls2_ListView_Upcoming.setAdapter(contentManager.adapter_UPCOMING);
                    return ls2_ListView_Upcoming;
                }       
            });
//            checkAlerts();
    }
    
    public void SetAdapters_IMPACT(){
        TabSpec3_Impact.setContent(new TabHost.TabContentFactory(){
            public View createTabContent(String tag)
            {
                ls3_ListView_Impact.setAdapter(contentManager.adapter_IMPACT);
                ls3_ListView_Impact.setOnItemClickListener(ImpactRiskClickListener);
                return ls3_ListView_Impact;
            }       
        });    
    }
    
    public void SetAdapters_NEWS(){
        TabSpec4_News.setContent(new TabHost.TabContentFactory(){
               public View createTabContent(String tag)
               {
                   ls4_ListView_News.setAdapter(contentManager.adapter_NEWS);
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
            LoadingDialogHelper.closeDialog = 0;
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
//        nasa_neo ntest = List_NASA_UPCOMING.get(0);
        while (iterator.hasNext()) {
            NearEarthObject ntest = iterator.next();
            if(ntest.getAlertMSG() != ""){
                callNotifyService(setupNotificationMessage("AsteroidAlert", ntest.getName()+" is passing closer than our moon"));
            }
            }

//        }

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
            openArticleView.putExtra("position", position);
            startActivity(openArticleView);
        };
    };

    public OnItemClickListener Asteroid_NewsArticle_ClickListener = new OnItemClickListener() {
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            Object object = AsteroidTrackerActivity.this.ls4_ListView_News.getAdapter().getItem(position);    
            News asteroidEntity = (News) object;
            Intent i = new Intent(Intent.ACTION_VIEW);
            try {
                i.setData(Uri.parse(asteroidEntity.artcileUrl));
                startActivity(i);
            } catch (ActivityNotFoundException e){
                Log.d("News", "ActivityNotFound on news article listner", e);
            }
        };
    };
}