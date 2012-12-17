package service;

import java.util.ArrayList;

import domains.Impact;
import domains.NearEarthObject;
import domains.News;

public class DownloadManager {

    public AsteroidTrackerService AsteroidGitService =  new AsteroidTrackerService();
//    NetworkUtil nUtil = new NetworkUtil();
    public boolean isNetworkAvailable;

    
    public DownloadManager(){}


    public ArrayList<NearEarthObject> retrieveAsteroidData(String URI, boolean IsNetworkAvailable) {
        if (IsNetworkAvailable) {
            return AsteroidGitService.getNEOList(URI);
        } 
        return AsteroidGitService.getNeoErrorList();
     }

    public ArrayList<News> retrieveAsteroidNews(boolean IsNetworkAvailable) {
        if (IsNetworkAvailable) {
            return AsteroidGitService.getLatestNews();
        } 
        return AsteroidGitService.getNewsErrorList();
     }

    public ArrayList<Impact> retrieveAsteroidImpact(boolean IsNetworkAvailable) {
        if (IsNetworkAvailable) {
            return AsteroidGitService.getImpactData();
        } 
        return AsteroidGitService.getImpactErrorList();
     }
    }
