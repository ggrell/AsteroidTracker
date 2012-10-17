package utils;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class AsteroidReceiver extends BroadcastReceiver 
{
    final AsteroidLoader mLoader;

    public AsteroidReceiver(AsteroidLoader loader)
    {
        mLoader = loader;
        mLoader.getContext().registerReceiver(this, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    @Override public void onReceive(Context context, Intent intent)
    {
        mLoader.onContentChanged();
    }

}
