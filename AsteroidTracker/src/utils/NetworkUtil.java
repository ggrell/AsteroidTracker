package utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

public class NetworkUtil {

    ConnectivityManager connMgr;

        public boolean IsNetworkAvailable(Context ctext) {

            Log.d("NetworkUtil", "Checking Network");
                try {
                    connMgr = (ConnectivityManager) ctext.getSystemService(Context.CONNECTIVITY_SERVICE);
                    if(connMgr.getActiveNetworkInfo() == null){
                          Log.d("NetworkUtil", "getActiveNetworkInfo is null");
                          return false;
                     }else{
                         //Network is available
                         Log.d("NetworkUtil", "getActiveNetworkInfo is ok");
                         return true;
                     }
                } catch (Exception e) {
                    Log.e("NetworkUtil", "Network available:Exception", e);
                    return false;
                }
            }
         
    }