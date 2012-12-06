package fragments;

import java.util.List;

import activities.fragment.AsteroidTabFragments;
import adapters.NearEarthObjectAdapter;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import com.vitruviussoftware.bunifish.asteroidtracker.R;
import domains.NearEarthObject;
;

public class RecentFragment extends AsteroidFragmentBase {

    public NearEarthObjectAdapter neoAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadingMessage = resources.getString(R.string.text_content_loading_neo);
        // progress = View.inflate(AsteroidTabFragments.cText,
        // R.layout.loading_content, null);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("recentFrag", "onStadt - Call Loader");
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
        // text.setText("Loading NASA NEO Feed ");

        // if (this.neoAdapter == null)
        // {
        // getLoaderManager().initLoader(0, null, this);
        // }
        Log.d("recentFrag", "Call Loader (onActivityCreated)");
        getListView().setOnItemClickListener(neoClickListener);
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

    public void onLoaderReset(Loader<List> loader) {
        neoAdapter.setData(null);
    }

    @Override
    public void onLoadFinished(Loader<List> list, List data)
    {
        super.onLoadFinished(list, data);
        Log.d("recentFrag", "onLoadFinished(): done loading!" + data.size());
        if (this.neoAdapter == null) {
            neoAdapter = new NearEarthObjectAdapter(AsteroidTabFragments.cText, R.layout.view_neolist, data);
            setListAdapter(neoAdapter);
        }
    }

    public void refreshFragment() {
        // setListAdapter(null);
        neoAdapter.notifyDataSetChanged();
        getLoaderManager().restartLoader(0, null, this);
    }

    public OnItemClickListener neoClickListener = new OnItemClickListener() {
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            Log.d("OnItemClickListener", "HERE");
            NearEarthObject neo = (NearEarthObject) getListAdapter().getItem(position);

            String headline = "Asteroid " + neo.getName();
            String message = "Asteroid " + neo.getName() + ",missDistance is " + neo.getMissDistance_AU_Kilometers()
                    + "(km) " +
                    "Check it out " + neo.getURL() + " #AsteroidTracker http://bit.ly/nkxCx1";
            AsteroidTabFragments.shareSvc.createAndShowShareIntent(headline, message);
        };
    };
}
