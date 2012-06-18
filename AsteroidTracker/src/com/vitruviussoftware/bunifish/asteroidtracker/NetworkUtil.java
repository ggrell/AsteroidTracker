package com.vitruviussoftware.bunifish.asteroidtracker;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

public class NetworkUtil {

	public static boolean netCheckin(Context ctext) {
        Log.d("NetworkUtil", "Checking Network");
            try {
            	ConnectivityManager connMgr = (ConnectivityManager) ctext.getSystemService(Context.CONNECTIVITY_SERVICE);
            	 final android.net.NetworkInfo wifi =
            		  connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            	 final android.net.NetworkInfo mobile =
            		  connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            	  if( wifi.isAvailable() ){
            		  Toast.makeText(ctext, "Wifi" , Toast.LENGTH_LONG).show();
            		  Log.d("NetworkUtil", "Network available: Wifi");
            		  }
            		  else if( mobile.isAvailable() ){
            		  Toast.makeText(ctext, "Mobile 3G " , Toast.LENGTH_LONG).show();
            		  Log.d("NetworkUtil", "Network available: Mobile");
            		  }
            		  else
            		  {Toast.makeText(ctext, "No Network " , Toast.LENGTH_LONG).show();
            		  Log.d("NetworkUtil", "Network available: No Network");
            		  }
            return false;
		    } catch (Exception e) {
		    	 Log.d("NetworkUtil", "Network available:Exception");
		        return false;
		    }
		}
	 
}
