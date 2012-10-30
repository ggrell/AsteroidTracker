package fragments;

import java.util.List;

import com.vitruviussoftware.bunifish.asteroidtracker.R;

import domains.NearEarthObject;
import activities.fragment.AsteroidTabFragments;
import adapters.NearEarthObjectAdapter;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

public class UpcomingFragment extends AsteroidFragmentBase  {

    private NearEarthObjectAdapter neoAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Loader<List> onCreateLoader(int id, Bundle args) {
        AsyncTaskLoader<List> loader = new AsyncTaskLoader<List>(getActivity()) {
        @Override
        public List<NearEarthObject> loadInBackground() {
            Log.d("recentFrag", "loadInBackground(): doing some work....");
            return AsteroidGitService.getNEOList(AsteroidGitService.URI_UPCOMING); 
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
        neoAdapter.setData(null);
    }

    protected void restartLoading() {}
}