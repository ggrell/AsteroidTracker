package fragments;

import java.util.List;

import service.AsteroidTrackerService;
import utils.AsteroidLoader;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockListFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.vitruviussoftware.bunifish.asteroidtracker.R;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;

public class AsteroidFragmentBase extends SherlockListFragment implements LoaderCallbacks<List> {

    AsteroidTrackerService AsteroidGitService =  new AsteroidTrackerService();
    ArrayAdapter adapter;
    ActionBar actionBar;
    
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
            setHasOptionsMenu(true);
            if (container == null) {
                return null;
            }
            return (LinearLayout)inflater.inflate(R.layout.lists, container, false);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(0, null, this);
    }
    
    public void setAdap(ListAdapter adapter){
        setListAdapter(adapter);
    }

    public Loader<List> onCreateLoader(int arg0, Bundle arg1) {
        return null;
    }

    public void onLoadFinished(Loader<List> arg0, List arg1) {}

    public void onLoaderReset(Loader<List> arg0) {}

    public void restartLoad();
}

