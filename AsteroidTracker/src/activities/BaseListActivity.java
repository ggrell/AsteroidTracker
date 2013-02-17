package activities;

import com.actionbarsherlock.app.SherlockListActivity;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Tracker;

public class BaseListActivity extends SherlockListActivity {

    protected Tracker defaultTracker;

    @Override
    public void onStart() {
      super.onStart();
      EasyTracker.getInstance().activityStart(this);
      defaultTracker = EasyTracker.getTracker();
    }

    @Override
    public void onStop() {
      super.onStop();
      EasyTracker.getInstance().activityStop(this);
    }


    public void sentTrackingEvent(String Category, String Action, String Label, Long value) {
        try {
            defaultTracker.sendEvent(Category, Action, Label, value);
        } catch(NullPointerException e) {
            EasyTracker.getInstance().setContext(this);
            defaultTracker = EasyTracker.getTracker();
            defaultTracker.sendEvent(Category, Action, Label, value);
        }
    }
}
