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
    

    public void processNEOFeedRecent(Context listText){
        Thread checkUpdate = new Thread() {
        public void run() {
                AsteroidTabFragments.contentManager.List_NASA_RECENT = (List<NearEarthObject>) AsteroidTabFragments.GitService.getNEOList(AsteroidTabFragments.GitService.URI_RECENT);
                AsteroidTabFragments.contentManager.loadAdapters_NEO_Recent(AsteroidTabFragments.cText);
                ((Activity) AsteroidTabFragments.cText).runOnUiThread(new Runnable() {
                   public void run() {
                       LoadingDialogHelper.dialog.setMessage("Loading NASA NEO Recent Feed...");
//                       AsteroidTabFragments.setRecentAdapter();
                       Log.i("closeDialog", "closeDialog Try to close Recent");
                       LoadingDialogHelper.closeDialog();
                       }
               });
        }};
        checkUpdate.start();
    }
    
}
