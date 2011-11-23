package com.vitruviussoftware.bunifish.asteroidtracker.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class LatestUpdateDB_Adapter {

	public static final String KEY_ROWID = "_id";
	public static final String KEY_LASTESTUPDATE = "lastupdate";
	private Context context;
	private SQLiteDatabase database;
	private AsteroidTracker_DatabaseHelper dbHelper;
	
	public LatestUpdateDB_Adapter(Context context) {
		this.context = context;
	}
	
	public LatestUpdateDB_Adapter open() throws SQLException {
		dbHelper = new AsteroidTracker_DatabaseHelper(context);
		database = dbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		dbHelper.close();
	}


//	public boolean updateLastUpdate(long rowId, String date) {
//		ContentValues updateValues = createContentValues(category, description, url, date, LastModified);
//		return database.update(DATABASE_TABLE, updateValues, KEY_ROWID + "="
//				+ rowId, null) > 0;
//	}

	private ContentValues createContentValues(String date) {
		ContentValues values = new ContentValues();
		values.put(KEY_LASTESTUPDATE, date);
		return values;
	}
}


