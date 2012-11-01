package service;

import java.util.List;

import com.vitruviussoftware.bunifish.asteroidtracker.R;

import activities.fragment.AsteroidTabFragments;
import activities.fragment.FragPageAdapter;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import domains.NearEarthObject;
import fragments.ImpactFragment;
import fragments.NewsFragment;
import fragments.RecentFragment;
import fragments.UpcomingFragment;
import utils.LoadingDialogHelper;
import utils.NetworkUtil;

public class DownloadManager {

    AsteroidTrackerService AsteroidGitService =  new AsteroidTrackerService();
    NetworkUtil nUtil = new NetworkUtil();
    private FragPageAdapter adap;

    public DownloadManager(){}

    public void startDownloads(){

        boolean networkAvailable = nUtil.IsNetworkAvailable(AsteroidTabFragments.cText);
        if(networkAvailable) {

            if(AsteroidGitService.isGitServiceAvailable())
            {
                LoadingDialogHelper.dialog.setMessage("Calling AsteroidTracker Service");
                Log.d("DownloadManager", "Git Service");
                            processNEOFeedRecent();
                            processNEOFeedUpcoming();
                            processImpactFeed(true);
                            processAsteroidNewsFeed(true);
            } else
            {
                LoadingDialogHelper.dialog.setMessage("Calling AsteroidTracker Nasa Service");
                Log.d("DownloadManager", "Nasa service");
                processNeoNasaFeeds();
                processImpactFeed(false);
                processAsteroidNewsFeed(false);
            }
            
        }else{
            Toast.makeText(AsteroidTabFragments.cText, "The Network is Unavailable, please check mobile/wifi connection", Toast.LENGTH_LONG).show();
        }
    }

    public void clearAdapters(){
        ((Activity) AsteroidTabFragments.cText).runOnUiThread(new Runnable() 
        {
            public void run() {
                RecentFragment recentFragzz = (RecentFragment) adap.getItem(0);
                UpcomingFragment upcomingFragment = (UpcomingFragment) adap.getItem(1);
                ImpactFragment impactFragment = (ImpactFragment) adap.getItem(2);
                NewsFragment newsFragment = (NewsFragment) adap.getItem(3);

                recentFragzz.setAdap(null);
                upcomingFragment.setAdap(null);
                impactFragment.setAdap(null);
                newsFragment.setAdap(null);
            }
   });

    }

    public void processNeoNasaFeeds(){
        Thread update = new Thread() 
        {
            public void run() 
            {
                String HTTPDATA =  AsteroidTabFragments.contentManager.neo_AstroidFeed.getAstroidFeedDATA(AsteroidTabFragments.contentManager.neo_AstroidFeed.URL_NASA_NEO);
                AsteroidTabFragments.contentManager.loadEntityLists_NEO(HTTPDATA);
                AsteroidTabFragments.contentManager.List_NASA_RECENT = AsteroidTabFragments.contentManager.neo_AstroidFeed.getRecentList(HTTPDATA);
                AsteroidTabFragments.contentManager.List_NASA_UPCOMING = AsteroidTabFragments.contentManager.neo_AstroidFeed.getUpcomingList(HTTPDATA);

                AsteroidTabFragments.contentManager.loadAdapters_NEO_Recent(AsteroidTabFragments.cText);
                AsteroidTabFragments.contentManager.loadAdapters_NEO_Upcoming(AsteroidTabFragments.cText);

            ((Activity) AsteroidTabFragments.cText).runOnUiThread(new Runnable() 
            {
                public void run() {
                    LoadingDialogHelper.dialog.setMessage("Loading NASA NEO Recent Feed...");
                        RecentFragment recentFragzz = (RecentFragment) adap.getItem(0);
                        recentFragzz.setAdap(AsteroidTabFragments.contentManager.adapter_RECENT);

                        UpcomingFragment upcomingFragment = (UpcomingFragment) adap.getItem(1);
                        upcomingFragment.setAdap(AsteroidTabFragments.contentManager.adapter_UPCOMING);

                        LoadingDialogHelper.closeDialog();
                        LoadingDialogHelper.closeDialog();
                    }
           });
        }};
        update.start();
    }

    public void processNEOFeedRecent(){
        Thread checkUpdate = new Thread() {
        public void run() {
                AsteroidTabFragments.contentManager.List_NASA_RECENT = (List<NearEarthObject>) AsteroidGitService.getNEOList(AsteroidGitService.URI_RECENT);
                AsteroidTabFragments.contentManager.loadAdapters_NEO_Recent(AsteroidTabFragments.cText);

                ((Activity) AsteroidTabFragments.cText).runOnUiThread(new Runnable() 
                {
                    public void run() {
                        LoadingDialogHelper.dialog.setMessage("Loading NASA NEO Recent Feed...");

                        RecentFragment recentFragzz = (RecentFragment) adap.getItem(0);
                        recentFragzz.setAdap(AsteroidTabFragments.contentManager.adapter_RECENT);

                        LoadingDialogHelper.closeDialog();
                    }
           });
        }};
        checkUpdate.start();
    }

    public void processNEOFeedUpcoming(){
        Thread checkUpcoming = new Thread() {
        public void run() {
            AsteroidTabFragments.contentManager.List_NASA_UPCOMING =  AsteroidGitService.getNEOList(AsteroidGitService.URI_UPCOMING);
            AsteroidTabFragments.contentManager.loadAdapters_NEO_Upcoming(AsteroidTabFragments.cText);
            ((Activity) AsteroidTabFragments.cText).runOnUiThread(new Runnable() 
            {
                public void run() {
                    LoadingDialogHelper.dialog.setMessage("Loading NEO Upcoming Feed...");

                    UpcomingFragment upcomingFragment = (UpcomingFragment) adap.getItem(1);
                    upcomingFragment.setAdap(AsteroidTabFragments.contentManager.adapter_UPCOMING);

                    LoadingDialogHelper.closeDialog();
                    }
                });
        }};
        checkUpcoming.start();
        }

    public void processImpactFeed( final boolean useGitService ){
        Thread ImpactUpdate = new Thread() {
            public void run() {
                    if(useGitService){
                        AsteroidTabFragments.contentManager.List_NASA_IMPACT = AsteroidGitService.getImpactData();
                    } else {
                        String HTTP_IMPACT_DATA =  AsteroidTabFragments.contentManager.neo_AstroidFeed.getAstroidFeedDATA(AsteroidTabFragments.contentManager.neo_AstroidFeed.URL_NASA_NEO_IMPACT_FEED);
                        AsteroidTabFragments.contentManager.List_NASA_IMPACT = AsteroidTabFragments.contentManager.neo_AstroidFeed.getImpactList(HTTP_IMPACT_DATA);
                    }
                    AsteroidTabFragments.contentManager.loadAdapters_NEO_Impact(AsteroidTabFragments.cText);
                ((Activity) AsteroidTabFragments.cText).runOnUiThread(new Runnable() 
                {
                    public void run() {
                        LoadingDialogHelper.dialog.setMessage("Loading Impact Risk Feed...");
                        AsteroidTabFragments.contentManager.adapter_IMPACT.notifyDataSetChanged();

                        ImpactFragment impactFragment = (ImpactFragment) adap.getItem(2);
                        impactFragment.setAdap(AsteroidTabFragments.contentManager.adapter_IMPACT);

                        LoadingDialogHelper.closeDialog();
                    }
                });
            }};
        ImpactUpdate.start();
    }

    public void processAsteroidNewsFeed( final boolean useGitService ){
        Thread NewsUpdate = new Thread() {
            public void run() 
            {
                if(useGitService)
                {
                    AsteroidTabFragments.contentManager.List_NASA_News = AsteroidGitService.getLatestNews();
                } else
                {
                    String HTTP_NEWS_DATA = AsteroidTabFragments.contentManager.neo_AstroidFeed.getAstroidFeedDATA(AsteroidTabFragments.contentManager.neo_AstroidFeed.URL_JPL_AsteroidNewsFeed);
                    AsteroidTabFragments.contentManager.List_NASA_News = AsteroidTabFragments.contentManager.neo_AstroidFeed.parseNewsFeed(HTTP_NEWS_DATA);
                }
                AsteroidTabFragments.contentManager.loadAdapters_NEO_News(AsteroidTabFragments.cText);
                ((Activity) AsteroidTabFragments.cText).runOnUiThread(new Runnable() 
                {
                    public void run() {
                        LoadingDialogHelper.dialog.setMessage("Loading News Feed...");
                        AsteroidTabFragments.contentManager.adapter_NEWS.notifyDataSetChanged();

                        NewsFragment newsFragment = (NewsFragment) adap.getItem(3);
                        newsFragment.setAdap(AsteroidTabFragments.contentManager.adapter_NEWS);

                        LoadingDialogHelper.closeDialog();
                    }
                });
            }};
        NewsUpdate.start();
    }

    public void setFragPageAdapter(FragPageAdapter adapter)
    {
        this.adap = adapter;
    }

}
