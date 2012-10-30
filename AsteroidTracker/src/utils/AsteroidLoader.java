package utils;

import java.util.List;

import com.vitruviussoftware.bunifish.asteroidtracker.R;

import service.AsteroidTrackerService;

import domains.NearEarthObject;
import activities.fragment.AsteroidTabFragments;
import adapters.NearEarthObjectAdapter;
import android.content.Context;
import android.content.IntentFilter;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader.ForceLoadContentObserver;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.ArrayAdapter;

public class AsteroidLoader extends AsyncTaskLoader<List> 
{

    AsteroidTrackerService AsteroidGitService =  new AsteroidTrackerService();
    NetworkUtil nUtil = new NetworkUtil();
    public Context ctext;
    private List mData;
    public NearEarthObjectAdapter adapter_RECENT;
    private final ForceLoadContentObserver mObserver = new ForceLoadContentObserver();
    AsteroidReceiver mAsteroidObserver;

    public AsteroidLoader(Context context) {
        super(context);
        this.ctext = context;
    }

    @Override
    public List loadInBackground() {
        Log.d("recentFrag", "loadInBackground(): doing some work....");
        List test =  AsteroidGitService.getNEOList(AsteroidGitService.URI_RECENT); 
        Log.d("recentFrag", "testing(): doing some work...."+test.size());
        return test;
        }

}
