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

    private NearEarthObjectAdapter neoAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadingMessage = resources.getString(R.string.text_content_loading_neo);
    }

    @Override
    public void onStart(){
        super.onStart();
        Log.d("updateFrag", "onStart - Call Loader");
        getLoaderManager().initLoader(1, null, this);
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("updateFrag", "Call Loader");
        getListView().setOnItemClickListener(clickListener);
    }

    @Override
    public Loader<List> onCreateLoader(int id, Bundle args) {
        AsyncTaskLoader<List> loader = new AsyncTaskLoader<List>(getActivity()) {
        @Override
        public List<NearEarthObject> loadInBackground() {
            Log.d("updateFrag", "loadInBackground(): doing some work....");
            return AsteroidGitService.getNEOList(AsteroidGitService.URI_UPCOMING); 
            }
        };
    loader.forceLoad();
    return loader;
    }

    public void onLoaderReset(Loader<List> loader) {
        neoAdapter.setData(null);
    }

    @Override
    public void onLoadFinished( Loader<List> arg0, List data ) 
    {
        super.onLoadFinished(arg0, data);
        Log.d("updateFrag", "onLoadFinished(): done loading!"+data.size());
        if (this.neoAdapter == null) {
            neoAdapter = new NearEarthObjectAdapter(AsteroidTabFragments.cText, R.layout.view_neo_fragment, data);
            setListAdapter(neoAdapter);
        }
    }

    public OnItemClickListener clickListener = new OnItemClickListener() {
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            NearEarthObject neo = (NearEarthObject) getListAdapter().getItem(position);
            String headline = "Asteroid " + " " + neo.getName() + ",";
            String message = "#Asteroid " + neo.getName() + ",missDistance is " + neo.getMissDistance_AU_Kilometers() + "km " +
                    "See Details "  + neo.getURL() + " #AsteroidTracker<http://bit.ly/nkxCx1>" ;
            AsteroidTabFragments.shareSvc.createAndShowShareIntent(headline, message);
        };
    };

    protected void restartLoading(MenuItem item) {
        Log.d("updateFrag", "onOptionsItemSelected menu");
        Toast.makeText(AsteroidTabFragments.cText, "updateFrag fragment " , Toast.LENGTH_LONG).show();
        reloadItem = item;
        setRefreshIcon(true);
        Log.d("updateFrag", "restartLoading(): re-starting loader");
        getLoaderManager().restartLoader(1, null, this);
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