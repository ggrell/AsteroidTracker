package fragments;

import java.util.List;

import utils.LoadingDialogHelper;

import com.actionbarsherlock.view.MenuItem;
import com.vitruviussoftware.bunifish.asteroidtracker.R;

import domains.NearEarthObject;
import domains.News;
import activities.AsteroidTrackerActivity;
import activities.fragment.AsteroidTabFragments;
import adapters.NearEarthObjectAdapter;
import adapters.NewsAdapter;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ProgressBar;

public class NewsFragment extends AsteroidFragmentBase {

    public NewsAdapter adapter_NEWS;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadingMessage = resources.getString(R.string.text_content_loading_news);
    }

    @Override
    public void onStart(){
        super.onStart();
        Log.d("newsFrag", "onStadt - Call Loader");
        getLoaderManager().initLoader(3, null, this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getListView().setOnItemClickListener(Asteroid_NewsArticle_ClickListener);
    }

    @Override
    public Loader<List> onCreateLoader(int id, Bundle args) {
        super.onCreateLoader(id, args);
        AsyncTaskLoader<List> loader = new AsyncTaskLoader<List>(getActivity()) {
        @Override
        public List<News> loadInBackground() {
            Log.d("newsFrag", "loadInBackground(): doing some work....");
            return downloadManager.retrieveAsteroidNews(isNetworkAvailable);
            }
        };
    loader.forceLoad();
    return loader;
    }

    @Override
    public void onLoadFinished( Loader<List> arg0, List data ) 
    {
        super.onLoadFinished(arg0, data);
        Log.d("newsFrag", "onLoadFinished(): done loading!"+data.size());
        if (adapter_NEWS != null) {
            if (adapter_NEWS.getItem(0).title.equals("Unable to retrieve Asteroid Data")) {
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
        adapter_NEWS = new NewsAdapter(AsteroidTabFragments.cText, R.layout.view_news_fragment, data);
        setListAdapter(adapter_NEWS);
    }
    public OnItemClickListener Asteroid_NewsArticle_ClickListener = new OnItemClickListener() {
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            if (!adapter_NEWS.getItem(0).title.equals("Unable to retrieve Asteroid Data")) {
                Object object = getListAdapter().getItem(position);    
                News asteroidEntity = (News) object;
                Intent i = new Intent(Intent.ACTION_VIEW);
                try {
                    i.setData(Uri.parse(asteroidEntity.artcileUrl));
                    startActivity(i);
                } catch (ActivityNotFoundException e){
                    Log.d("News", "ActivityNotFound on news article listner", e);
                }
                }
            };
    };
    
    protected void restartLoading(MenuItem item) {
        Log.d("newsFrag", "onOptionsItemSelected menu");
        reloadItem = item;
        setRefreshIcon(true);
        Log.d("newsFrag", "restartLoading(): re-starting loader");
        getLoaderManager().restartLoader(3, null, this);
    }

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