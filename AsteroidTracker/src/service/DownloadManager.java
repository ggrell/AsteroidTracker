package service;

import utils.LoadingDialogHelper;

public class DownloadManager {

    AsteroidTrackerService AsteroidGitService =  new AsteroidTrackerService();

    DownloadManager(){
        
    }
    
    
    public void StartDownloads(){
        if(AsteroidGitService.IsGitServiceAvailable()){
            LoadingDialogHelper.messageTitle = "AsteroidTracker service";
            //Start Git Downloads
        } else{
            LoadingDialogHelper.messageTitle = "Nasa service";
            //Start Nasa Downloads
        }
    }
    

}
