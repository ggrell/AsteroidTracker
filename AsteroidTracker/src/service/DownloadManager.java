package service;

import java.util.List;

import activities.fragment.AsteroidTabFragments;
import activities.fragment.FragPageAdapter;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import domains.NearEarthObject;
import fragments.ImpactFragment;
import fragments.NewsFragment;
import fragments.RecentFragOld;
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
        if(networkAvailable){
            if(AsteroidGitService.isGitServiceAvailable()){
                            processNEOFeedRecent();
                            processNEOFeedUpcoming();
                            processImpactFeed();
                            processAsteroidNewsFeed();
            } else{
                    LoadingDialogHelper.messageTitle = "Nasa service";
                }
        }
    }

    public void NotifyUIOfDataChange(){
        ((Activity) AsteroidTabFragments.cText).runOnUiThread(new Runnable() 
        {
            public void run() {
                AsteroidTabFragments.contentManager.adapter_RECENT.notifyDataSetChanged();
                AsteroidTabFragments.contentManager.adapter_UPCOMING.notifyDataSetChanged();
                AsteroidTabFragments.contentManager.adapter_IMPACT.notifyDataSetChanged();
                AsteroidTabFragments.contentManager.adapter_NEWS.notifyDataSetChanged();
                LoadingDialogHelper.killDialog();
            }
   });

    }
    
    public Thread processNEOFeedRecent(){
        Thread checkUpdate = new Thread() {
        public void run() {
                AsteroidTabFragments.contentManager.List_NASA_RECENT = (List<NearEarthObject>) AsteroidGitService.getNEOList(AsteroidGitService.URI_RECENT);
                AsteroidTabFragments.contentManager.loadAdapters_NEO_Recent(AsteroidTabFragments.cText);

                ((Activity) AsteroidTabFragments.cText).runOnUiThread(new Runnable() 
                {
                    public void run() {
                        LoadingDialogHelper.dialog.setMessage("Loading NASA NEO Recent Feed...");

//                        AsteroidTabFragments.contentManager.adapter_RECENT.notifyDataSetChanged();
                        RecentFragOld recentFragzz = (RecentFragOld) adap.getItem(0);
                        recentFragzz.setAdap(AsteroidTabFragments.contentManager.adapter_RECENT);

                        LoadingDialogHelper.closeDialog();
                    }
           });
        }};
        checkUpdate.start();
        return checkUpdate;
    }

    public void processImpactFeed(){
        Thread ImpactUpdate = new Thread() {
            public void run() {
//                    if(AsteroidTabFragments.UseGitService){
//                        AsteroidTabFragments.contentManager.List_NASA_IMPACT = AsteroidTabFragments.GitService.getImpactData();
//                        AsteroidTabFragments.contentManager.loadAdapters_NEO_Impact(AsteroidTabFragments.cText);
//                    } else {
//                        String HTTP_IMPACT_DATA =  AsteroidTabFragments.contentManager.neo_AstroidFeed.getAstroidFeedDATA(AsteroidTabFragments.contentManager.neo_AstroidFeed.URL_NASA_NEO_IMPACT_FEED);
//                        AsteroidTabFragments.contentManager.List_NASA_IMPACT = AsteroidTabFragments.contentManager.neo_AstroidFeed.getImpactList(HTTP_IMPACT_DATA);
//                    }
//                    AsteroidTabFragments.contentManager.loadAdapters_NEO_Impact(AsteroidTabFragments.cText);
                AsteroidTabFragments.contentManager.List_NASA_IMPACT = AsteroidGitService.getImpactData();
                AsteroidTabFragments.contentManager.loadAdapters_NEO_Impact(AsteroidTabFragments.cText);
//                LoadingDialogHelper.dialog.setMessage("Loading NASA Impact Risk Feed...");
                ((Activity) AsteroidTabFragments.cText).runOnUiThread(new Runnable() 
                {
                    public void run() {
                        LoadingDialogHelper.dialog.setMessage("Loading NASA Impact Risk Feed...");
                        AsteroidTabFragments.contentManager.adapter_IMPACT.notifyDataSetChanged();

                        ImpactFragment impactFragment = (ImpactFragment) adap.getItem(2);
                        impactFragment.setAdap(AsteroidTabFragments.contentManager.adapter_IMPACT);

                        LoadingDialogHelper.closeDialog();
                    }
           });
            }};
        ImpactUpdate.start();
    }

    public void processAsteroidNewsFeed(){
        Thread NewsUpdate = new Thread() {
            public void run() {
                AsteroidTabFragments.contentManager.List_NASA_News = AsteroidGitService.getLatestNews();
                AsteroidTabFragments.contentManager.loadAdapters_NEO_News(AsteroidTabFragments.cText);

//                LoadingDialogHelper.dialog.setMessage("Loading NASA News Feed...");
                ((Activity) AsteroidTabFragments.cText).runOnUiThread(new Runnable() 
                {
                    public void run() {
                        LoadingDialogHelper.dialog.setMessage("Loading NASA News Feed...");
                        AsteroidTabFragments.contentManager.adapter_NEWS.notifyDataSetChanged();

                        NewsFragment newsFragment = (NewsFragment) adap.getItem(3);
                        newsFragment.setAdap(AsteroidTabFragments.contentManager.adapter_NEWS);

                        LoadingDialogHelper.closeDialog();
                    }
           });
            }};
            NewsUpdate.start();
    }

    public void processNEOFeedUpcoming(){
        Thread checkUpcoming = new Thread() {
        public void run() {
            AsteroidTabFragments.contentManager.List_NASA_UPCOMING =  AsteroidGitService.getNEOList(AsteroidGitService.URI_UPCOMING);
            AsteroidTabFragments.contentManager.loadAdapters_NEO_Upcoming(AsteroidTabFragments.cText);
//            LoadingDialogHelper.dialog.setMessage("Loading NASA NEO Upcoming Feed...");
            ((Activity) AsteroidTabFragments.cText).runOnUiThread(new Runnable() 
            {
                public void run() {
                    LoadingDialogHelper.dialog.setMessage("Loading NASA NEO Upcoming Feed...");
//                    AsteroidTabFragments.contentManager.adapter_UPCOMING.notifyDataSetChanged();

                    UpcomingFragment upcomingFragment = (UpcomingFragment) adap.getItem(1);
                    upcomingFragment.setAdap(AsteroidTabFragments.contentManager.adapter_UPCOMING);

                    LoadingDialogHelper.closeDialog();
                }
       });

        }};
        checkUpcoming.start();
        }

    public void setFragPageAdapter(FragPageAdapter adapter)
    {
        this.adap = adapter;
    }

}
