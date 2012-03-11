package com.vitruviussoftware.bunifish.asteroidtracker;

import android.app.Activity;
import android.app.ListActivity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class notificationManager extends Activity {

//	String ns = Context.NOTIFICATION_SERVICE;
	NotificationManager mNotificationManager;// = (NotificationManager) getSystemService(ns);
	Notification notification;
	
	notificationManager() {
		String ns = Context.NOTIFICATION_SERVICE;
		mNotificationManager = (NotificationManager) getSystemService(ns);
		int icon = R.drawable.asteroid;
		CharSequence tickerText = "AsteroidTracker";
		long when = System.currentTimeMillis();
		notification = new Notification(icon, tickerText, when);
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		
	}
	
	public void setupNotificationMessage(Notification notification, String notificationTitle, String notifiationText){
		Context context = getApplicationContext();
		CharSequence contentTitle = notificationTitle;
		CharSequence contentText = notifiationText;
		Intent notificationIntent = new Intent(this, AsteroidTrackerActivity.class);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
		notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
		
	}
	
	public void callNotifyService(Notification notification) {
		final int HELLO_ID = 1;
		mNotificationManager.notify(HELLO_ID, notification);
	}
	
}
