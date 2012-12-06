package fragments;

import java.util.List;

import service.AsteroidTrackerService;
import activities.fragment.AsteroidTabFragments;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockListFragment;
import com.vitruviussoftware.bunifish.asteroidtracker.R;

public class AsteroidFragmentBase extends SherlockListFragment implements LoaderCallbacks<List>{

    Resources resources;
    AsteroidTrackerService AsteroidGitService =  new AsteroidTrackerService();
//    protected ViewSwitcher switcher;
    TextView text;
    ProgressBar bar;
    View progress;
    String loadingMessage = "Loading...";

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
            setRetainInstance(true);
            if (container == null) {
                return null;
            }
            return (RelativeLayout)inflater.inflate(R.layout.view_loading_content, container, false);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resources = getResources();

        if (savedInstanceState != null) {
            return;
        }
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        resources= getResources();
        text = (TextView) getView().findViewById(R.id.loading_text);
        bar = (ProgressBar) getView().findViewById(R.id.loading_progress);
        text.setText(loadingMessage);
    }
    
    public void setAdap(ListAdapter adapter){
        setListAdapter(adapter);
    }

    public void clearAdap(){
        setListAdapter(null);
    }

    public Loader<List> onCreateLoader(int arg0, Bundle arg1) {
        // TODO Auto-generated method stub
        return null;
    }

    public void onLoadFinished(Loader<List> arg0, List arg1) {
        text.setVisibility(View.GONE);
        bar.setVisibility(View.GONE);

    }

    public void onLoaderReset(Loader<List> arg0) {
        // TODO Auto-generated method stub
        
    }
    
    public void onResume(Bundle savedInstanceState) {
        Toast.makeText(AsteroidTabFragments.cText, "TEST ONRESUME" , Toast.LENGTH_LONG).show();
      }
    
    public void onStop(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        clearAdap();
        Toast.makeText(AsteroidTabFragments.cText, "ONSTOP" , Toast.LENGTH_LONG).show();
    }

}

