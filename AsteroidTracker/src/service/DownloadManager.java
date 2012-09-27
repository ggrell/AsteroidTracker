package service;

import java.util.List;

import activities.fragment.AsteroidTabFragments;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import domains.NearEarthObject;
import utils.LoadingDialogHelper;

public class DownloadManager {

    AsteroidTrackerService AsteroidGitService =  new AsteroidTrackerService();

    public DownloadManager(){
        
    }
    
    
    public void StartDownloads(){
        if(AsteroidGitService.isGitServiceAvailable()){
            LoadingDialogHelper.messageTitle = "AsteroidTracker service";
            //Start Git Downloads
        } else{
            LoadingDialogHelper.messageTitle = "Nasa service";
            //Start Nasa Downloads
        }
    }
    

    public void processNEOFeedRecent(){
        Thread checkUpdate = new Thread() {
        public void run() {
                AsteroidTabFragments.contentManager.List_NASA_RECENT = (List<NearEarthObject>) AsteroidTabFragments.GitService.getNEOList(AsteroidTabFragments.GitService.URI_RECENT);
                AsteroidTabFragments.contentManager.loadAdapters_NEO_Recent(AsteroidTabFragments.cText);
                ((Activity) AsteroidTabFragments.cText).runOnUiThread(new Runnable() {
                   public void run() {
                       LoadingDialogHelper.dialog.setMessage("Loading NASA NEO Recent Feed...");
                       AsteroidTabFragments.contentManager.adapter_RECENT.notifyDataSetChanged();
                       LoadingDialogHelper.killDialog();
                       }
               });
        }};
        checkUpdate.start();
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
                        AsteroidTabFragments.contentManager.List_NASA_IMPACT = AsteroidTabFragments.GitService.getImpactData();
                        AsteroidTabFragments.contentManager.loadAdapters_NEO_Impact(AsteroidTabFragments.cText);
                    ((Activity) AsteroidTabFragments.cText).runOnUiThread(new Runnable() {
                    public void run() {
                            LoadingDialogHelper.dialog.setMessage("Loading NASA Impact Risk Feed...");
                            AsteroidTabFragments.contentManager.adapter_IMPACT.notifyDataSetChanged();
                            LoadingDialogHelper.killDialog();
                        }
                });
            }};
        ImpactUpdate.start();
    }

    public void processAsteroidNewsFeed(){
        Thread NewsUpdate = new Thread() {
            public void run() {
                AsteroidTabFragments.contentManager.List_NASA_News = AsteroidTabFragments.GitService.getLatestNews();
                AsteroidTabFragments.contentManager.loadAdapters_NEO_News(AsteroidTabFragments.cText);
                ((Activity) AsteroidTabFragments.cText).runOnUiThread(new Runnable() {
                       public void run() {
                           LoadingDialogHelper.dialog.setMessage("Loading NASA News Feed...");
                           AsteroidTabFragments.contentManager.adapter_NEWS.notifyDataSetChanged();
                           LoadingDialogHelper.killDialog();
                       }
                   });
            }};
            NewsUpdate.start();
    }

    public void processNEOFeedUpcoming(){
        Thread checkUpdate = new Thread() {
        public void run() {
            AsteroidTabFragments.contentManager.List_NASA_UPCOMING =  AsteroidTabFragments.GitService.getNEOList(AsteroidTabFragments.GitService.URI_UPCOMING);
            AsteroidTabFragments.contentManager.loadAdapters_NEO_Upcoming(AsteroidTabFragments.cText);
            ((Activity) AsteroidTabFragments.cText).runOnUiThread(new Runnable() {
                   public void run() {
                       LoadingDialogHelper.dialog.setMessage("Loading NASA NEO Upcoming Feed...");
                       AsteroidTabFragments.contentManager.adapter_UPCOMING.notifyDataSetChanged();
                       LoadingDialogHelper.killDialog();
                   }
            });
        }};
        checkUpdate.start();
        }
}
