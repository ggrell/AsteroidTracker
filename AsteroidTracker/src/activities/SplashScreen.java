package activities;

import service.DownloadManager;

import com.vitruviussoftware.bunifish.asteroidtracker.R;

import activities.fragment.AsteroidTabFragments;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen  extends Activity
{

    private final static int MSG_CONTINUE = 1234;
    private final static long DELAY = 2000;
//    public DownloadManager dManager = new DownloadManager(); 
    
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
        startActivity(new Intent(this, AsteroidTabFragments.class));
        finish();
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
