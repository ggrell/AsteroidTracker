package activities;

import activities.fragment.AsteroidTabFragments;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.format.Time;
import android.util.Log;

import com.vitruviussoftware.bunifish.asteroidtracker.R;

public class SplashScreen extends Activity
{

    private final static int MSG_CONTINUE = 1234;
    private final static long DELAY = 2000;
//    public DownloadManager dManager = new DownloadManager(); 
    private String TAG = "SplashScreen";

    @Override
    protected void onCreate(Bundle args)
    {
        super.onCreate(args);
        setContentView(R.layout.splash);
        mHandler.sendEmptyMessageDelayed(MSG_CONTINUE, DELAY);
//        dManager.startDownloads();
    }
    
    @Override
    protected void onDestroy()
    {
        mHandler.removeMessages(MSG_CONTINUE);
        super.onDestroy();
    }

    private void _continue()
    {
        //TODO Add switch based on date
        //TODO check Prefs for if to show skylog info
        //TODO, Add logic to switch from Skylog info to AsteroidFragment
    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this); 
    String showDialog = preferences.getString(SkyLogInfo.showDialogKey, "true");
    Log.v(TAG, "showDialog "+showDialog);

    if (showDialog == null) {
        showDialog = "true";
    }
    if (checkRunDateIsValid() && showDialog.equals("true")) {
        startActivity(new Intent(this, SkyLogInfo.class));
    } else {
        startActivity(new Intent(this, AsteroidTabFragments.class));
    }
    finish();

}

    public boolean checkRunDateIsValid() {
        Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();

        Time EndDate = new Time(Time.getCurrentTimezone());
        EndDate.set(18, 04, 2013);
        boolean runDate = (today.after(EndDate)) ? false : true; 

//        Log.v(TAG, "RunDate today: " +  today);
//        Log.v(TAG, "RunDate EndDate: " +  EndDate);
        Log.v(TAG, "RunDate: " +  runDate);
        return runDate;
    }

    private final Handler mHandler = new Handler()
    {
        public void handleMessage(android.os.Message msg)
        {
            switch(msg.what){
                case MSG_CONTINUE:
                    _continue();
                    break;
            }
        }
    };

}
