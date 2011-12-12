package com.vitruviussoftware.bunifish.asteroidtracker.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class AsteroidTracker_DatabaseHelper extends SQLiteOpenHelper{

	private static final String DATABASE_NAME = "applicationdata";

	private static final int DATABASE_VERSION = 1;

	// Database creation sql statement
	private static final String DATABASE_CREATE_NEWS= "create table news (_id integer primary key autoincrement, "
			+ "title text not null, description text not null, url text not null,  imageByteArray BLOB not null, imageurl text not null, date text not null, LastModified text not null);";

	private static final String DATABASE_CREATE_NEO = "create table neo (_id integer primary key autoincrement, "
		+ "title text not null, description text not null, url text not null, date text not null, LastModified text not null);";

	private static final String DATABASE_CREATE_IMPACT = "create table impact (_id integer primary key autoincrement, "
		+ "title text not null, description text not null, url text not null, date text not null, LastModified text not null);";

	private static final String DATABASE_UPDATE_NEO_TABLE = "create table lastnewsupdat (_id integer primary key autoincrement, "
		+ "date text not null);";
	private static final String DATABASE_UPDATE_NEWS_TABLE = "create table lastneoupdate (_id integer primary key autoincrement, "
		+ "date text not null);";
	private static final String DATABASE_UPDATE_IMPACT_TABLE = "create table lastimpactupdate (_id integer primary key autoincrement, "
		+ "date text not null);";
	

	public AsteroidTracker_DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Method is called during creation of the database
	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE_NEWS);
		database.execSQL(DATABASE_UPDATE_NEWS_TABLE);
//		database.execSQL(DATABASE_CREATE_NEO);
//		database.execSQL(DATABASE_UPDATE_NEO_TABLE);
//		database.execSQL(DATABASE_CREATE_IMPACT);
//		database.execSQL(DATABASE_UPDATE_IMPACT_TABLE);		
	}

	// Method is called during an upgrade of the database, e.g. if you increase
	// the database version
	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		Log.w(AsteroidTracker_DatabaseHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS news");
		onCreate(database);
	}
}