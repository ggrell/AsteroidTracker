package fragments;

import java.util.ArrayList;
import java.util.List;
import com.vitruviussoftware.bunifish.asteroidtracker.R;
import domains.NearEarthObject;
import service.AsteroidTrackerService;
import utils.AsteroidLoader;
import activities.fragment.AsteroidTabFragments;
import adapters.NearEarthObjectAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;

public class RecentFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Void>
{
    Activity context; 
    private NearEarthObjectAdapter neoAdapter;
    public List<NearEarthObject> List_NASA_RECENT;
    private static final int RECENT_LIST_LOADER = 0x01;
    private LayoutInflater mInflater;
    AsteroidTrackerService AsteroidGitService =  new AsteroidTrackerService();
    
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
////        neoAdapter = new NearEarthObjectAdapter(this.getActivity(), R.layout.nasa_neolistview, List_NASA_RECENT);
//        getLoaderManager().initLoader(RECENT_LIST_LOADER, null, this);
//    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) 
    {
        Log.d("recentFrag", "onActivityCreated");
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
        mInflater = LayoutInflater.from(getActivity());
        
        if (neoAdapter == null) {
            List_NASA_RECENT = new ArrayList();
            neoAdapter = new NearEarthObjectAdapter(getActivity(), R.layout.nasa_neolistview, List_NASA_RECENT);
            setListAdapter(neoAdapter);
//            getListView().setAdapter(neoAdapter);


            LoaderManager lm = getLoaderManager();
            lm.initLoader(RECENT_LIST_LOADER, null, this);
//            if (lm.getLoader(RECENT_LIST_LOADER) != null) {
//                Log.d("recentFrag", "call init()");
//                lm.initLoader(RECENT_LIST_LOADER, null, this);
//            }
        }
        
    }

    protected void startLoading() {
        Log.d("recentFrag", "onActivityCreated(): start loading!");
        getLoaderManager().initLoader(RECENT_LIST_LOADER, null, this);
    }

    protected void restartLoading() {
        List_NASA_RECENT.clear();
        neoAdapter.notifyDataSetChanged();
        getListView().invalidateViews();
//        Log.d(TAG, "restartLoading(): re-starting loader");
        getLoaderManager().restartLoader(RECENT_LIST_LOADER, null, this);
    }

    @Override
    public Loader<Void> onCreateLoader(int id, Bundle args) {
        AsyncTaskLoader<Void> loader = new AsyncTaskLoader<Void>(getActivity()) {

            @Override
            public Void loadInBackground() {
                Log.d("recentFrag", "loadInBackground");
                List_NASA_RECENT = (AsteroidGitService.getNEOList(AsteroidGitService.URI_RECENT));
                Log.d("recentFrag", "loadInBackground size "+ List_NASA_RECENT.size());
                return null;
            }
        };
        // somehow the AsyncTaskLoader doesn't want to start its job without
        // calling this method
        loader.forceLoad();
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Void> arg0, Void arg1) {
        neoAdapter.notifyDataSetChanged();
        Log.d("recentFrag", "onLoadFinished(): done loading!");
    }

    @Override
    public void onLoaderReset(Loader<Void> arg0) {
        // TODO Auto-generated method stub
        
    }

}