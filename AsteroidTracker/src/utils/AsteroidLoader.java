package utils;

import java.util.List;

import com.vitruviussoftware.bunifish.asteroidtracker.R;

import service.AsteroidTrackerService;

import domains.NearEarthObject;
import activities.fragment.AsteroidTabFragments;
import adapters.NearEarthObjectAdapter;
import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.widget.ArrayAdapter;

public class AsteroidLoader extends AsyncTaskLoader{

    AsteroidTrackerService AsteroidGitService =  new AsteroidTrackerService();
    NetworkUtil nUtil = new NetworkUtil();
    public Context ctext;
    private List mData;
    public NearEarthObjectAdapter adapter_RECENT;
    
    public AsteroidLoader(Context context) {
        super(context);
        this.ctext = context;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public List loadInBackground() {
        return  AsteroidGitService.getNEOList(AsteroidGitService.URI_RECENT);
//        AsteroidTabFragments.contentManager.List_NASA_RECENT = (List<NearEarthObject>) AsteroidGitService.getNEOList(AsteroidGitService.URI_RECENT);
//        AsteroidTabFragments.contentManager.loadAdapters_NEO_Recent(AsteroidTabFragments.cText);
//        return new NearEarthObjectAdapter(this.ctext, R.layout.nasa_neolistview, AsteroidTabFragments.contentManager.List_NASA_RECENT);
    }
    
    public void deliverResult(List data) {
      if (isReset()) {
        // The Loader has been reset; ignore the result and invalidate the data.
        onReleaseResources(data);
        return;
      }
   
      // Hold a reference to the old data so it doesn't get garbage collected.
      // The old data may still be in use (i.e. bound to an adapter, etc.), so
      // we must protect it until the new data has been delivered.
      List oldData = mData;
      mData = data;
   
      if (isStarted()) {
        // If the Loader is in a started state, deliver the results to the
        // client. The superclass method does this for us.
        super.deliverResult(data);
      }
   
      // Invalidate the old data as we don't need it any more.
      if (oldData != null && oldData != data) {
        onReleaseResources(oldData);
      }
    }

    @Override
    protected void onStartLoading() {
      if (mData != null) {
        // Deliver any previously loaded data immediately.
        deliverResult(mData);
      }
   
      // Begin monitoring the underlying data source.
      if (mObserver == null) {
        mObserver = new DataObserver();
        // TODO: register the observer
      }
   
      if (takeContentChanged() || mData == null) {
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
        mObserver = null;
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
   
    /*********************************************************************/
    /** (4) Observer which receives notifications when the data changes **/
    /*********************************************************************/
    
    // NOTE: Implementing an observer is outside the scope of this post (this example
    // uses a made-up “SampleObserver” to illustrate when/where the observer should 
    // be initialized). 
     
    // The observer could be anything so long as it is able to detect content changes
    // and report them to the loader with a call to onContentChanged(). For example,
    // if you were writing a Loader which loads a list of all installed applications
    // on the device, the observer could be a BroadcastReceiver that listens for the
    // ACTION_PACKAGE_ADDED intent, and calls onContentChanged() on the particular 
    // Loader whenever the receiver detects that a new application has been installed.
    // Please don’t hesitate to leave a comment if you still find this confusing! :)
    private DataObserver mObserver;

}
