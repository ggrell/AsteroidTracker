package utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

public class NetworkUtil {

        public boolean IsNetworkAvailable(Context ctext) {
            boolean IsNetworkAvailable = false;
            Log.d("NetworkUtil", "Checking Network");
                try {
                    ConnectivityManager connMgr = (ConnectivityManager) ctext.getSystemService(Context.CONNECTIVITY_SERVICE);
                    if(connMgr.getActiveNetworkInfo() == null){
                          Log.d("NetworkUtil", "getActiveNetworkInfo is null");
                          return IsNetworkAvailable;
                     }else{
                         IsNetworkAvailable = true;
                         return IsNetworkAvailable;
                     }
                } catch (Exception e) {
                    Log.e("NetworkUtil", "Network available:Exception", e);
                    return IsNetworkAvailable;
                }
            }
         
    }