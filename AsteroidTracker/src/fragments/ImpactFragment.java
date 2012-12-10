package fragments;

import java.util.ArrayList;
import java.util.List;

import com.actionbarsherlock.view.MenuItem;
import com.vitruviussoftware.bunifish.asteroidtracker.R;
import domains.Impact;
import activities.fragment.AsteroidTabFragments;
import adapters.ImpactAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ImpactFragment extends AsteroidFragmentBase {

    public ImpactAdapter adapter_IMPACT;
    public static ArrayList<Impact> dataList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadingMessage = resources.getString(R.string.text_content_loading_impact);
    }

    @Override
    public void onStart(){
        super.onStart();
        Log.d("impactFrag", "onStadt - Call Loader");
        getLoaderManager().initLoader(2, null, this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
            Log.d("impactFrag", "Call Loader");
            getListView().setOnItemClickListener(ImpactRiskClickListener);
    }

    @Override
    public Loader<List> onCreateLoader(int id, Bundle args) {
        AsyncTaskLoader<List> loader = new AsyncTaskLoader<List>(getActivity()) {
        @Override
        public List<Impact> loadInBackground() {
            Log.d("impactFrag", "loadInBackground(): doing some work....");
            return AsteroidGitService.getImpactData(); 
            }
        };
    loader.forceLoad();
    return loader;
    }
    
    public void onLoaderReset(Loader<List> loader) {
        adapter_IMPACT.setData(null);
    }

    @Override
    public void onLoadFinished( Loader<List> arg0, List data ) 
    {
        super.onLoadFinished(arg0, data);
        Log.d("impactFrag", "onLoadFinished(): done loading!"+data.size());
        if (this.adapter_IMPACT == null) {
            adapter_IMPACT = new ImpactAdapter(AsteroidTabFragments.cText, R.layout.view_impact_fragment, data);
            setListAdapter(adapter_IMPACT);
        }
        dataList = (ArrayList) data;
    }

    public OnItemClickListener ImpactRiskClickListener = new OnItemClickListener() {
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            Intent openArticleView = new Intent(getActivity(), activities.ImpactRiskDetailView.class);
            openArticleView.putExtra("position", position);
            startActivity(openArticleView);
        };
    };

    protected void restartLoading(MenuItem item) {
        Log.d("impactFrag", "onOptionsItemSelected menu");
        Toast.makeText(AsteroidTabFragments.cText, "impactFrag fragment " , Toast.LENGTH_LONG).show();
        reloadItem = item;
        setRefreshIcon(true);
        Log.d("impactFrag", "restartLoading(): re-starting loader");
        getLoaderManager().restartLoader(2, null, this);
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