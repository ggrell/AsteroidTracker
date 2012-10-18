package utils;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

public class AsteroidReceiver extends BroadcastReceiver 
{
    final AsteroidLoader mLoader;

    public AsteroidReceiver(AsteroidLoader loader)
    {
        mLoader = loader;
//        mLoader.getContext().registerReceiver(this, new IntentFilter("updateui"));
//        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
//                new IntentFilter("custom-event-name"));
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        // Get extra data included in the Intent
//        String message = intent.getStringExtra("message");
        Log.d("receiver", "Got message onRecieve: ");
        mLoader.onContentChanged();

    }

}
