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
import android.database.ContentObserver;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v4.content.Loader.ForceLoadContentObserver;
import android.util.Log;
import android.view.LayoutInflater;

public class RecentFragment extends ListFragment implements LoaderCallbacks<List>
{
    Activity context; 
    private NearEarthObjectAdapter neoAdapter;
    public List<NearEarthObject> List_NASA_RECENT;
    private static final int RECENT_LIST_LOADER = 0x01;
    private LayoutInflater mInflater;
    AsteroidTrackerService AsteroidGitService =  new AsteroidTrackerService();

    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) 
    {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);


        if (neoAdapter == null) 
        {
            Log.d("recentFrag", "set EVERYTHING");
//            List_NASA_RECENT = new ArrayList();
            //TODO may need to update adapter to not take list in constructor
//            neoAdapter = new NearEarthObjectAdapter(AsteroidTabFragments.cText, R.layout.nasa_neolistview, AsteroidTabFragments.contentManager.List_NASA_RECENT);
            neoAdapter = new NearEarthObjectAdapter(AsteroidTabFragments.cText, R.layout.nasa_neolistview);
            setListAdapter(neoAdapter);
            getLoaderManager().initLoader(0, null, this);
        }
        
    }

    protected void startLoading() {
        Log.d("recentFrag", "NEW onActivityCreated(): start loading!");
        getLoaderManager().initLoader(RECENT_LIST_LOADER, null, this);
    }

    protected void restartLoading() {
//        List_NASA_RECENT.clear();
//        neoAdapter.notifyDataSetChanged();
//        getListView().invalidateViews();
//        Log.d(TAG, "restartLoading(): re-starting loader");
//        getLoaderManager().restartLoader(RECENT_LIST_LOADER, null, this);
    }

    public void onLoadFinished( Loader<List> arg0, List data ) 
    {
      //TODO add setDATA to adapter
        neoAdapter.setData(data);
//        getListView().invalidateViews();
//        neoAdapter.notifyDataSetChanged();
        Log.d("recentFrag", "onLoadFinished(): done loading!");
//        ((Activity) AsteroidTabFragments.cText).runOnUiThread(new Runnable() 
//        {
//            public void run() {
//                getListView().invalidateViews();
//                neoAdapter.notifyDataSetChanged();
////                neoAdapter.notifyDataSetInvalidated();
//                Log.d("recentFrag", "onLoadFinished(): done loading! IN UI");
//                
//            }
//         });
    }

    public Loader<List> onCreateLoader(int arg0, Bundle arg1) {
       return new AsteroidLoader(getActivity());
    }

    public void onLoaderReset(Loader<List> loader) {
        // Clear the data in the adapter.
        //TODO add setDATA to adapter
        neoAdapter.setData(null);
    }

}