package activities;

import activities.fragment.AsteroidTabFragments;
import adapters.SkyLogInfoAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.actionbarsherlock.app.SherlockActivity;
import com.google.analytics.tracking.android.EasyTracker;
import com.vitruviussoftware.bunifish.asteroidtracker.R;

public class SkyLogInfo extends SherlockActivity {

    public static String TAG = "SkyLogInfo";
    public static String showDialogKey = "showSkylogInfo";
    public Context appContext;
    SkyLogInfoAdapter skyAdpter;
    RelativeLayout relativeLayout;

    
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.skyloginfo);
        appContext = this;
        final Button voteNowButton = (Button) findViewById(R.id.skylogButtonVoteNow);
        voteNowButton.setOnClickListener(voteForSkyLog);
        final Button skyLogoreInfo = (Button) findViewById(R.id.skylogButtonMoreInfo);
        skyLogoreInfo.setOnClickListener(moreInfoSkyLog);
        final Button continueToApp = (Button) findViewById(R.id.skyLogContinueToApp);
        continueToApp.setOnClickListener(continueToAsteroidTrackerApp);
        final Button neveShow = (Button) findViewById(R.id.skylogDontShow);
        neveShow.setOnClickListener(neverShowPageAgain);

//        skyAdpter = new SkyLogInfoAdapter(this, R.layout.skyloginfo);

    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }

    @Override
    public void onStart() {
      super.onStart();
      EasyTracker.getInstance().activityStart(this);
    }

    @Override
    public void onStop() {
      super.onStop();
      EasyTracker.getInstance().activityStop(this);
    }

    public OnClickListener voteForSkyLog = new OnClickListener() {
        public void onClick(View view) {
            Log.v(TAG, "Click action for Vote");
            String twitterMessage = getString(R.string.skylog_twitter_message);
            String twitterShareLink = getString(R.string.skylog_twitter_share_link);
            Intent shareMe = AsteroidTabFragments.shareSvc.createTwitterShareIntent(twitterMessage, twitterShareLink, appContext);
            ((Activity)appContext).startActivity(shareMe);
        }
    };

    public OnClickListener moreInfoSkyLog = new OnClickListener() {
        public void onClick(View view) {
            Log.v(TAG, "Click action for MoreInfo");
            String spaceAppsPage = getString(R.string.skylog_weebly_site);
            Intent goToSpaceAppsPage = new Intent(Intent.ACTION_VIEW, Uri.parse(spaceAppsPage));
            startActivity(goToSpaceAppsPage);
        }
    };

    public OnClickListener continueToAsteroidTrackerApp = new OnClickListener() {
        public void onClick(View view) {
            Log.v(TAG, "Click Action to Continue to App");
            startMainActivity();
        }
    };

    public OnClickListener neverShowPageAgain = new OnClickListener() {
        public void onClick(View view) {
            Log.v(TAG, "Click Action to NeverShow again to App");
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()); 
            Editor edit = preferences.edit();
            edit.putString(showDialogKey, "false");
            edit.commit();
            startMainActivity();
        }
    };
    
    private void startMainActivity(){
        startActivity(new Intent(getApplicationContext(), AsteroidTabFragments.class));
        finish();
    }

}
