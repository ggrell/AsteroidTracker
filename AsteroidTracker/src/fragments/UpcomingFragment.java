package fragments;

import java.util.List;

import com.actionbarsherlock.view.MenuItem;
import com.vitruviussoftware.bunifish.asteroidtracker.R;
import domains.NearEarthObject;
import activities.fragment.AsteroidTabFragments;
import adapters.NearEarthObjectAdapter;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class UpcomingFragment extends AsteroidFragmentBase {

    private NearEarthObjectAdapter recentNeoAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadingMessage = resources.getString(R.string.text_content_loading_neo);
    }

    @Override
    public void onStart(){
        super.onStart();
        getLoaderManager().initLoader(LOADER_UPCOMING, null, this);
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getListView().setOnItemClickListener(clickListener);
    }

    @Override
    public Loader<List> onCreateLoader(int id, Bundle args) {
        super.onCreateLoader(id, args);
        AsyncTaskLoader<List> loader = new AsyncTaskLoader<List>(getActivity()) {
        @Override
        public List<NearEarthObject> loadInBackground() {
            return (List<NearEarthObject>) downloadManager.retrieveAsteroidData(downloadManager.AsteroidGitService.URI_UPCOMING, isNetworkAvailable);
            }
        };
    loader.forceLoad();
    return loader;
    }

    public void onLoaderReset(Loader<List> loader) {
        recentNeoAdapter.setData(null);
    }

    @Override
    public void onLoadFinished(Loader<List> list, List data)
    {
        super.onLoadFinished(list, data);
        if (recentNeoAdapter != null) {
            
            if (recentNeoAdapter.getItem(0).getName().equals("Unable to retrieve Asteroid Data")) {
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
        recentNeoAdapter = new NearEarthObjectAdapter(AsteroidTabFragments.cText, R.layout.view_neo_fragment, data);
        setListAdapter(recentNeoAdapter);
    }

    public OnItemClickListener clickListener = new OnItemClickListener() {
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            if (!recentNeoAdapter.getItem(0).getName().equals("Unable to retrieve Asteroid Data")) {
                NearEarthObject neo = (NearEarthObject) getListAdapter().getItem(position);
                defaultTracker.sendEvent("upcoming_action", "upcoming_click", "Title: "+ neo.getName(), null);
                String headline = "Asteroid " + " " + neo.getName() + ",";
                String message = "#Asteroid " + neo.getName() + ",missDistance is " + neo.getMissDistance_AU_Kilometers() + "km " +
                        "See Details "  + neo.getURL() + " #AsteroidTracker<http://bit.ly/nkxCx1>" ;
                AsteroidTabFragments.shareSvc.createAndShowShareIntent(headline, message);
                }
            };
    };

    protected void restartLoading(MenuItem item) {
        reloadItem = item;
        setRefreshIcon(true, "Upcoming");
        getLoaderManager().restartLoader(LOADER_UPCOMING, null, this);
  }

    public boolean onOptionsItemSelected(final MenuItem item) {
      switch (item.getItemId()) {
      case R.id.reload:
          restartLoading(item);
          return super.onOptionsItemSelected(item);
      default:
        return false;
        }
    }

}