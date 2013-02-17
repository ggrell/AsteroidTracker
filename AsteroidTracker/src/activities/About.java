package activities;

import java.util.ArrayList;

import adapters.AboutAdapter;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockListActivity;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Tracker;
import com.vitruviussoftware.bunifish.asteroidtracker.R;

import domains.AboutAsteroidTracker;

public class About extends BaseListActivity {


    public static Drawable drawableAbout;
    static AboutAdapter AboutDapter;
    ArrayList<AboutAsteroidTracker> aboutEntityList = new ArrayList();
    ActionBar actionBar;
    ListView ListView_acout;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actionBar=getSupportActionBar();
        setTitle(getResources().getString(R.string.about));
        AboutAsteroidTracker about = new AboutAsteroidTracker();
        aboutEntityList.add(about);
        final ProgressDialog ArtcleDialog = ProgressDialog.show(this, "","", true);
        final Handler Artclehandler = new Handler() {
            public void handleMessage(Message msg) {
                ArtcleDialog.dismiss();
            }
        };
        Thread checkUpdate = new Thread() {
            public void run() {
                Artclehandler.sendEmptyMessage(0);
                About.this.runOnUiThread(new Runnable() {
                       public void run() {
                           AboutDapter = new AboutAdapter(About.this, R.layout.about_main, aboutEntityList);
                           setListAdapter(About.this.AboutDapter);
                       }
                   });
            }
        };
        checkUpdate.start();
    }

    @Override
    public void onStart() {
      super.onStart();
    }

    @Override
    public void onStop() {
      super.onStop();
    }

    private OnClickListener GoToNASANeoSite = new OnClickListener() {
        public void onClick(View v) {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://neo.jpl.nasa.gov"));
            startActivity(intent);
        }
    };

    private OnClickListener GoToBFsite = new OnClickListener() {
        public void onClick(View v) {
            String url = "";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        }
    };

    public void rateTheApp(View view) {
        Log.d("About", "rateTheApp");
        sentTrackingEvent("About", "RateTheApp_Click", "RateTheApp", null);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.vitruviussoftware.bunifish.asteroidtracker"));
        if (MyStartActivity(intent) == false) {
            Toast.makeText(this, "Unable to open the Android market, please install the market app.  Thanks", Toast.LENGTH_SHORT).show();
        }
    }

    public void followTumblr(View view) {
        Log.d("About", "followTumblr");
        sentTrackingEvent("About", "FollowTumblr_Click", "FollowTumblr", null);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://spacetracks.tumblr.com/"));
        if (MyStartActivity(intent) == false) {
            Toast.makeText(this, "Unable to open the Tumblr Page", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean MyStartActivity(Intent aIntent) {
        try
        {
            startActivity(aIntent);
            return true;
        }
        catch (ActivityNotFoundException e)
        {
            return false;
        }
    }

}
