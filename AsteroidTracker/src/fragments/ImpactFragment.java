package fragments;

import java.util.ArrayList;
import java.util.List;

import com.vitruviussoftware.bunifish.asteroidtracker.R;

import domains.Impact;
import domains.NearEarthObject;
import activities.AsteroidTrackerActivity;
import activities.fragment.AsteroidTabFragments;
import adapters.ImpactAdapter;
import adapters.NearEarthObjectAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class ImpactFragment extends AsteroidFragmentBase {

    public ImpactAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setListAdapter(AsteroidTabFragments.contentManager.adapter_IMPACT);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getListView().setOnItemClickListener(ImpactRiskClickListener);
    }
    
    public OnItemClickListener ImpactRiskClickListener = new OnItemClickListener() {
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            Object object = getListAdapter().getItem(position);    
            Impact asteroidEntity = (Impact) object;
            Intent openArticleView = new Intent(getActivity(), activities.ImpactRiskDetailView.class);
            openArticleView.putExtra("position", position);
            startActivity(openArticleView);
        };
    };

    @Override
    public Loader<List> onCreateLoader(int id, Bundle args) {
        AsyncTaskLoader<List> loader = new AsyncTaskLoader<List>(getActivity()) {
        @Override
        public ArrayList<Impact> loadInBackground() {
            Log.d("recentFrag", "loadInBackground(): doing some work....");
            return AsteroidGitService.getImpactData(); 
            }
        };
    loader.forceLoad();
    return loader;
    }

    public void onLoadFinished( Loader<List> arg0, List data ) 
    {
        Log.d("recentFrag", "onLoadFinished(): done loading!"+data.size());
        adapter = new ImpactAdapter(AsteroidTabFragments.cText, R.layout.nasa_neo_impact_listview, data);
        setListAdapter(adapter);
    }
}