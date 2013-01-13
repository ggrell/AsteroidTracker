package fragments;

import java.util.List;

import service.AsteroidTrackerService;
import service.DownloadManager;
import utils.LoadingDialogHelper;
import activities.fragment.AsteroidTabFragments;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.util.Log;
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
    TextView text;
    ProgressBar bar;
    View progress;
    String loadingMessage = "Loading...";
    public com.actionbarsherlock.view.MenuItem reloadItem;
    DownloadManager downloadManager = new DownloadManager();
    boolean isNetworkAvailable;
    Log Log;

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
        setHasOptionsMenu(true);
        if (savedInstanceState != null) {
            return;
        }
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
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
        if (!downloadManager.nUtil.IsNetworkAvailable(AsteroidTabFragments.cText)) {
            Toast.makeText(AsteroidTabFragments.cText, getActivity().getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();   
            isNetworkAvailable = false;
        } else {
            isNetworkAvailable = true;
        }
        return null;
    }

    public void onLoaderReset(Loader<List> arg0) {}

    public void onLoadFinished(Loader<List> arg0, List arg1) {
        if (LoadingDialogHelper.hasStarted) {
            if (Build.VERSION.SDK_INT <= 8) {
                LoadingDialogHelper.killDialog();
                LoadingDialogHelper.hasStarted = false;
            }
        }
        text.setVisibility(View.GONE);
        bar.setVisibility(View.GONE);
        if (reloadItem == null) {
        } else {
            setRefreshIcon(false, "");
        }
    }

    public void onResume(Bundle savedInstanceState) {}

    public void onStop(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        clearAdap();
    }

    public void setRefreshIcon( boolean IsEnabled, String updateTag) {
        if(IsEnabled) {
            if (updateTag.length() != 0){
                if (Build.VERSION.SDK_INT <= 8) {
                    LoadingDialogHelper.progressDialog(AsteroidTabFragments.cText, "", "Retrieving "+ updateTag + " Service");
                    LoadingDialogHelper.hasStarted = true;
                }
            }
            reloadItem.setEnabled(false);
            getSherlockActivity().getSupportActionBar();
            reloadItem.setActionView(R.layout.inderterminate_progress);
            getSherlockActivity().setProgressBarIndeterminateVisibility(Boolean.TRUE);
        }else {
            reloadItem.setEnabled(true);
            reloadItem.setActionView(null);
            getActivity().setProgressBarIndeterminateVisibility(Boolean.FALSE);
        }
    }

}

