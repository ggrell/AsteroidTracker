package service;

import java.util.ArrayList;

import utils.NetworkUtil;

import android.util.Log;

import domains.Impact;
import domains.NearEarthObject;
import domains.News;

public class DownloadManager {

    public AsteroidTrackerService AsteroidGitService =  new AsteroidTrackerService();
    private ContentManager contentManager = new ContentManager();
    public NetworkUtil nUtil = new NetworkUtil();

    public boolean isNetworkAvailable;

    public DownloadManager() {}

    public ArrayList<NearEarthObject> retrieveAsteroidData(String URI, boolean IsNetworkAvailable) {
        if (IsNetworkAvailable) {
            if (AsteroidGitService.isGitServiceAvailable()) {
                Log.d("DownloadManager", "retrieveAsteroidData - git service");
                return AsteroidGitService.getNEOList(URI);
            }else {
                Log.d("DownloadManager", "retrieveAsteroidData - nasa service");
                if (URI.equals(AsteroidTrackerService.URI_RECENT)) {
                    return (ArrayList<NearEarthObject>) contentManager.neo_AstroidFeed.getRecentList(contentManager.neo_AstroidFeed.getAstroidFeedDATA(NeoAstroidFeed.URL_NASA_NEO));
                } else if (URI.equals(AsteroidTrackerService.URI_UPCOMING)) {
                    return (ArrayList<NearEarthObject>) contentManager.neo_AstroidFeed.getUpcomingList(contentManager.neo_AstroidFeed.getAstroidFeedDATA(NeoAstroidFeed.URL_NASA_NEO));
                }
            }
        }
        return AsteroidGitService.getNeoErrorList();
     }

    public ArrayList<News> retrieveAsteroidNews(boolean IsNetworkAvailable) {
        if (IsNetworkAvailable) {
            if (AsteroidGitService.isGitServiceAvailable()) {
                Log.d("DownloadManager", "retrieveAsteroidNews - git service");
                return AsteroidGitService.getLatestNews();
            } else {
                Log.d("DownloadManager", "retrieveAsteroidNews - nasa service");
                return contentManager.neo_AstroidFeed.parseNewsFeed(contentManager.neo_AstroidFeed.getAstroidFeedDATA(NeoAstroidFeed.URL_JPL_AsteroidNewsFeed));
            }
        }
        return AsteroidGitService.getNewsErrorList();
     }

    public ArrayList<Impact> retrieveAsteroidImpact(boolean IsNetworkAvailable) {
        if (IsNetworkAvailable) {
            if (AsteroidGitService.isGitServiceAvailable()) {
                Log.d("DownloadManager", "retrieveAsteroidImpact - git service");
                return AsteroidGitService.getImpactData();
            } else {
                Log.d("DownloadManager", "retrieveAsteroidImpact - nasa service");
                return (ArrayList<Impact>) contentManager.neo_AstroidFeed.getImpactList(contentManager.neo_AstroidFeed.getAstroidFeedDATA(NeoAstroidFeed.URL_NASA_NEO_IMPACT_FEED));
            }
        }
        return AsteroidGitService.getImpactErrorList();
    }

}