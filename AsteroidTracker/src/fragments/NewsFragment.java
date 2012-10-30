package fragments;

import java.util.ArrayList;
import java.util.List;

import com.vitruviussoftware.bunifish.asteroidtracker.R;

import domains.Impact;
import domains.News;
import activities.AsteroidTrackerActivity;
import activities.fragment.AsteroidTabFragments;
import adapters.ImpactAdapter;
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
import android.widget.AdapterView.OnItemClickListener;

public class NewsFragment extends AsteroidFragmentBase {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setListAdapter(AsteroidTabFragments.contentManager.adapter_NEWS);
    }

    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getListView().setOnItemClickListener(Asteroid_NewsArticle_ClickListener);
    }
    
    public OnItemClickListener Asteroid_NewsArticle_ClickListener = new OnItemClickListener() {
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            Object object = getListAdapter().getItem(position);    
            News asteroidEntity = (News) object;
            Intent i = new Intent(Intent.ACTION_VIEW);
            try {
                i.setData(Uri.parse(asteroidEntity.artcileUrl));
                startActivity(i);
            } catch (ActivityNotFoundException e){
                Log.d("News", "ActivityNotFound on news article listner", e);
            }
        };
    };

    @Override
    public Loader<List> onCreateLoader(int id, Bundle args) {
        AsyncTaskLoader<List> loader = new AsyncTaskLoader<List>(getActivity()) {
        @Override
        public ArrayList<News> loadInBackground() {
            Log.d("recentFrag", "loadInBackground(): doing some work....");
            return AsteroidGitService.getLatestNews(); 
            }
        };
    loader.forceLoad();
    return loader;
    }

    public void onLoadFinished( Loader<List> arg0, List data ) 
    {
        Log.d("recentFrag", "onLoadFinished(): done loading!"+data.size());
        adapter = new NewsAdapter(AsteroidTabFragments.cText, R.layout.jpl_asteroid_news, data);
        setListAdapter(adapter);
    }
}