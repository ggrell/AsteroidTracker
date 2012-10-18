package utils;

import java.util.List;

import com.vitruviussoftware.bunifish.asteroidtracker.R;

import service.AsteroidTrackerService;

import domains.NearEarthObject;
import activities.fragment.AsteroidTabFragments;
import adapters.NearEarthObjectAdapter;
import android.content.Context;
import android.content.IntentFilter;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader.ForceLoadContentObserver;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.ArrayAdapter;

public class AsteroidLoader extends AsyncTaskLoader<List> 
{

    AsteroidTrackerService AsteroidGitService =  new AsteroidTrackerService();
    NetworkUtil nUtil = new NetworkUtil();
    public Context ctext;
    private List mData;
    public NearEarthObjectAdapter adapter_RECENT;
    private final ForceLoadContentObserver mObserver = new ForceLoadContentObserver();
    AsteroidReceiver mAsteroidObserver;

    public AsteroidLoader(Context context) {
        super(context);
        this.ctext = context;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public List loadInBackground() {
        Log.d("recentFrag", "AsteroidLoader - loadInBackground");
        return  AsteroidGitService.getNEOList(AsteroidGitService.URI_RECENT);
      }

    @Override
    public void deliverResult(List data) {
      if (isReset()) 
      {
        Log.d("recentFrag", "AsteroidLoader - deliverResult");
        // The Loader has been reset; ignore the result and invalidate the data.
        onReleaseResources(data);
        if (isStarted()) 
        {
            super.deliverResult(data);
        }

      }

      List oldData = mData;
      mData = data;

      if (oldData != null && oldData != data) {
        onReleaseResources(oldData);
      }
    }

    @Override
    protected void onStartLoading() 
    {
        
        Log.d("recentFrag", "AsteroidLoader - onStartLoading");
      if (mData != null) {
        Log.d("recentFrag", "AsteroidLoader - Delivering previous data");
        // Deliver any previously loaded data immediately.
        deliverResult(mData);
      }
   
      // Begin monitoring the underlying data source.
      if (mAsteroidObserver == null) {
//          mAsteroidObserver = new AsteroidReceiver(this);
          Log.d("recentFrag", "AsteroidLoader - registerReceiver");
          LocalBroadcastManager.getInstance(this.ctext).registerReceiver(mAsteroidObserver, new IntentFilter("updateui"));
      }
//      }
//   
      if (takeContentChanged() || mData == null) {
          Log.d("recentFrag", "AsteroidLoader - takeContentChanged");
        // When the observer detects a change, it should call onContentChanged()
        // on the Loader, which will cause the next call to takeContentChanged()
        // to return true. If this is ever the case (or if the current data is
        // null), we force a new load.
        forceLoad();
      }
    }

    @Override
    protected void onStopLoading() {
      // The Loader is in a stopped state, so we should attempt to cancel the 
      // current load (if there is one).
      cancelLoad();
   
      // Note that we leave the observer as is; Loaders in a stopped state
      // should still monitor the data source for changes so that the Loader
      // will know to force a new load if it is ever started again.
    }
   
    @Override
    protected void onReset() {
      // Ensure the loader has been stopped.
      onStopLoading();
   
      // At this point we can release the resources associated with 'mData'.
      if (mData != null) {
        onReleaseResources(mData);
        mData = null;
      }
   
      // The Loader is being reset, so we should stop monitoring for changes.
      if (mObserver != null) {
        // TODO: unregister the observer
//        mObserver = null;
      }
    }
   
    public void onCanceled(List data) {
      // Attempt to cancel the current asynchronous load.
      super.onCanceled(data);
   
      // The load has been canceled, so we should release the resources
      // associated with 'data'.
      onReleaseResources(data);
    }
   
    protected void onReleaseResources(List data) {
      // For a simple List, there is nothing to do. For something like a Cursor, we 
      // would close it in this method. All resources associated with the Loader
      // should be released here.
    }

}
