package utils;

import java.util.List;

import service.AsteroidTrackerService;

import domains.NearEarthObject;
import activities.fragment.AsteroidTabFragments;
import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

public class AsteroidLoader extends AsyncTaskLoader{

    AsteroidTrackerService AsteroidGitService =  new AsteroidTrackerService();
    NetworkUtil nUtil = new NetworkUtil();
    
    public AsteroidLoader(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    @Override
    public Object loadInBackground() {
        // TODO Auto-generated method stub
        AsteroidTabFragments.contentManager.List_NASA_RECENT = (List<NearEarthObject>) AsteroidGitService.getNEOList(AsteroidGitService.URI_RECENT);
        AsteroidTabFragments.contentManager.loadAdapters_NEO_Recent(AsteroidTabFragments.cText);

        return null;
    }

}
