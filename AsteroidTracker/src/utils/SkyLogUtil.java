package utils;

import activities.SkyLogInfo;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.format.Time;
import android.util.Log;

public class SkyLogUtil {

    public static String TAG = "SkyLogUtil"; 

    public boolean checkRunDateIsValid() {
        Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();

        Time EndDate = new Time(Time.getCurrentTimezone());
        EndDate.set(18, 04, 2013);
        boolean runDate = (today.after(EndDate)) ? false : true; 
//        Log.v(TAG, "RunDate: " +  runDate);
        return runDate;
    }
    
    public boolean showDialogPerPref(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String showDialog = preferences.getString(SkyLogInfo.showDialogKey, "true");
        Log.v(TAG, "showDialog "+showDialog);
        if (showDialog == null) {
            showDialog = "true";
        }
        boolean pref = (showDialog.equals("true") ? true : false);
//        Log.v(TAG, "SkyLogUtil pref: " +  pref);
        return pref;
    }
    
    
    public boolean showDialog(Context context) {
        return (checkRunDateIsValid() && showDialogPerPref(context)) ? true : false;
    }
   
}
