package activities;

import com.vitruviussoftware.bunifish.asteroidtracker.R;
import com.vitruviussoftware.bunifish.asteroidtracker.R.drawable;

import android.app.Activity;
import android.app.ListActivity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class notificationManager extends Activity {
	NotificationManager mNotificationManager;// = (NotificationManager) getSystemService(ns);
	Notification notification;
	
	public void setupNotificationManager(Context cx) {
//		mNotificationManager = (NotificationManager) getSystemService(cx.NOTIFICATION_SERVICE);
	}
	
	public Notification setupNotificationMessage(String notificationTitle, String notifiationText){
//		mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		Intent intent = new Intent(this,AsteroidTrackerActivity.class);  
		  Notification notification = new Notification(R.drawable.asteroid, "AsteroidTracker close-pass", System.currentTimeMillis());  
		  notification.flags |= Notification.FLAG_AUTO_CANCEL;
		  notification.setLatestEventInfo(this,"AsteroidTracker","Asteroid Passing closer than our moon!!", PendingIntent.getActivity(this.getBaseContext(), 0, intent,PendingIntent.FLAG_CANCEL_CURRENT)); 
		  return notification;
		}
	
	public void callNotifyService(NotificationManager notificationmanager, Notification notification) {
		notificationmanager.notify(0, notification);	
	}
	
}
