package fragments;

import java.util.List;

import com.vitruviussoftware.bunifish.asteroidtracker.R;

import domains.NearEarthObject;
import service.AsteroidTrackerService;
import service.ContentManager;
import utils.AsteroidLoader;
import activities.fragment.AsteroidTabFragments;
import adapters.NearEarthObjectAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.util.Log;

public class RecentFragment extends AsteroidFragmentBase {
    Activity context; 
    private NearEarthObjectAdapter neoAdapter;
    public List<NearEarthObject> List_NASA_RECENT;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) 
    {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);

        if (neoAdapter == null) 
        {
            Log.d("recentFrag", "Call Loader");
            getLoaderManager().initLoader(0, null, this);
        }
        
    }

    protected void restartLoading() {
//        List_NASA_RECENT.clear();
//        neoAdapter.notifyDataSetChanged();
//        getListView().invalidateViews();
//        Log.d(TAG, "restartLoading(): re-starting loader");
//        getLoaderManager().restartLoader(RECENT_LIST_LOADER, null, this);
    }
    
    @Override
    public Loader<List> onCreateLoader(int id, Bundle args) {
        AsyncTaskLoader<List> loader = new AsyncTaskLoader<List>(getActivity()) {
        @Override
        public List<NearEarthObject> loadInBackground() {
            Log.d("recentFrag", "loadInBackground(): doing some work....");
            return AsteroidGitService.getNEOList(AsteroidGitService.URI_RECENT); 
            }
        };
    loader.forceLoad();
    return loader;
    }

    public void onLoadFinished( Loader<List> arg0, List data ) 
    {
        Log.d("recentFrag", "onLoadFinished(): done loading!"+data.size());
        neoAdapter = new NearEarthObjectAdapter(AsteroidTabFragments.cText, R.layout.nasa_neolistview, data);
        setListAdapter(neoAdapter);
    }

    public void onLoaderReset(Loader<List> loader) {
        // Clear the data in the adapter.
        neoAdapter.setData(null);
    }

}