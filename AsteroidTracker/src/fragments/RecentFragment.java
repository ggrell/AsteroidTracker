package fragments;

import java.util.List;

import service.baseLoader;
import activities.fragment.AsteroidTabFragments;
import adapters.NearEarthObjectAdapter;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.actionbarsherlock.view.MenuItem;
import com.google.analytics.tracking.android.EasyTracker;
import com.vitruviussoftware.bunifish.asteroidtracker.R;

import domains.NearEarthObject;
import domains.baseEntity;

public class RecentFragment extends AsteroidFragmentBase {

    public NearEarthObjectAdapter neoAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadingMessage = resources.getString(R.string.text_content_loading_neo);
    }

    @Override
    public void onStart() {
        super.onStart();
        getLoaderManager().initLoader(LOADER_RECENT, null, this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getListView().setOnItemClickListener(neoClickListener);
    }

    @Override
    public Loader<List> onCreateLoader(int id, Bundle args) {
        super.onCreateLoader(id, args);
        baseLoader loader = new baseLoader(getActivity()) {
        @Override
        public List<NearEarthObject> loadInBackground() {
            super.loadInBackground();
            return downloadManager.retrieveAsteroidData(downloadManager.AsteroidGitService.URI_RECENT, isNetworkAvailable);
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
        //Check if view is bad, try to update.
        //If the view if good, but data is bad...dont update
        if (neoAdapter != null) {
            if (neoAdapter.getItem(0).getName().equals(baseEntity.FAILURELOADING)) {
                loadContent(data);
            } else {
                if(data.size() > 1){
                    loadContent(data);
                }
            }
        } else {
            loadContent(data);
        }
    }

    public void loadContent(List data){
        neoAdapter = new NearEarthObjectAdapter(AsteroidTabFragments.cText, R.layout.view_neo_fragment, data);
        setListAdapter(neoAdapter);
    }

    protected void restartLoading(MenuItem item) {
        reloadItem = item;
        setRefreshIcon(true, "RECENT");
        getLoaderManager().restartLoader(LOADER_RECENT, null, this);
    }


    public OnItemClickListener neoClickListener = new OnItemClickListener() {
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            if (!neoAdapter.getItem(0).getName().equals(baseEntity.FAILURELOADING)) {
                NearEarthObject neo = (NearEarthObject) getListAdapter().getItem(position);
                sentTrackingEvent("Recent", "recent_click", "Title: "+ neo.getName(), null);
                String headline = "Asteroid " + neo.getName();
                String message = "Asteroid " + neo.getName() + ",missDistance is " + neo.getMissDistance_AU_Kilometers()
                        + "(km) " +
                        "Check it out " + neo.getURL() + " #AsteroidTracker http://bit.ly/nkxCx1";
                AsteroidTabFragments.shareSvc.createAndShowShareIntent(headline, message);
            }
        };
    };

    
    @Override

    public boolean onOptionsItemSelected(final MenuItem item) 
    {
      switch (item.getItemId()) {
      case R.id.reload:
          restartLoading(item);
          return super.onOptionsItemSelected(item);
      default:
          return false;
          }
    }
}
