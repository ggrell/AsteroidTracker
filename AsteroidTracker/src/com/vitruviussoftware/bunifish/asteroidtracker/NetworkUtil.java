package com.vitruviussoftware.bunifish.asteroidtracker;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

public class NetworkUtil {

	public static boolean IsNetworkAvailable(Context ctext) {
		boolean IsNetworkAvailable = false;
        Log.d("NetworkUtil", "Checking Network");
            try {
            	ConnectivityManager connMgr = (ConnectivityManager) ctext.getSystemService(Context.CONNECTIVITY_SERVICE);
//            	 final android.net.NetworkInfo wifi = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
//            	 final android.net.NetworkInfo mobile = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
//            	 Log.d("NetworkUtil", "Network isConnectedOrConnecting" + connMgr.getActiveNetworkInfo().isConnectedOrConnecting());
            	if(connMgr.getActiveNetworkInfo() == null){
            		  Log.d("NetworkUtil", "getActiveNetworkInfo is null");
            		  return IsNetworkAvailable;
            	 }else{
            		 IsNetworkAvailable = true;
            		 return IsNetworkAvailable;
            	 }
		    } catch (Exception e) {
		    	Log.d("NetworkUtil", "Network available:Exception");
		        return IsNetworkAvailable;
		    }
		}
	 
}
