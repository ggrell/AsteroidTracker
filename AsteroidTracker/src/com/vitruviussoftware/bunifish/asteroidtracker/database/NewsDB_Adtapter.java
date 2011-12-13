package com.vitruviussoftware.bunifish.asteroidtracker.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class NewsDB_Adtapter {

	// Database fields
	public static final String KEY_ROWID = "_id";
	public static final String KEY_TITLE = "title";
	public static final String KEY_DESCRIPTION = "description";
	public static final String KEY_URL = "url";
	public static final String KEY_IMAGE_URL = "imageurl";
	public static final String KEY_IMAGE = "imageByteArray";
	public static final String KEY_DATE = "date";
	public static final String KEY_LASTMODIFIED = "LastModified";
	private static final String DATABASE_TABLE = "news";
	private Context context;
	private SQLiteDatabase database;
	private AsteroidTracker_DatabaseHelper dbHelper;

	public NewsDB_Adtapter(Context context) {
		this.context = context;
	}

	public NewsDB_Adtapter open() throws SQLException {
		dbHelper = new AsteroidTracker_DatabaseHelper(context);
		database = dbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		dbHelper.close();
	}

	
/**
	 * Create a new news Article.
	 * If the Article is successfully created return the new
	 * rowId, otherwise return a -1 to indicate failure.
	 */

	public long createNewsArticle(String title, String description, String url, byte[] imageArray, String imageURL, String date, String LastModified) {
		ContentValues initialValues = createContentValues(title, description, url, imageArray, imageURL, date, LastModified);
//		database.
		return database.insert(DATABASE_TABLE, null, initialValues);
	}

	public boolean isDuplicate(String title){
		Cursor mCursor = database.query(true, DATABASE_TABLE, new String[] {
				KEY_ROWID, KEY_TITLE, KEY_DESCRIPTION, KEY_URL, KEY_IMAGE, KEY_IMAGE_URL, KEY_DATE, KEY_LASTMODIFIED},
				KEY_TITLE + "=" + title, null, null, null, null, null);
		if (mCursor != null) {
			return true;
		}else{
			return false;
		}
		
	}
	
/**
	 * Update the NewsArticle
	 */

	public boolean updateNewsArticle(long rowId, String category, String description, String url,  byte[] imageArray, String imageurl, String date, String LastModified) {
		ContentValues updateValues = createContentValues(category, description, url, imageArray, imageurl, date, LastModified);
		return database.update(DATABASE_TABLE, updateValues, KEY_ROWID + "=" + rowId, null) > 0;
	}

	
/**
	 * Deletes NewsArticle
	 */

	public boolean deleteArticle(long rowId) {
		return database.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
	}

	/**
	 * Deletes NewsArticles
	 */

	public boolean deleteAllArticles() {
		return database.delete(DATABASE_TABLE, null, null) > 0;
	}
	
	
/**
	 * Return a Cursor over the list of all NewsArticles in the database
	 * @return Cursor over all notes
	 */

	public Cursor fetchAllArticles() {
		return database.query(DATABASE_TABLE, new String[] { KEY_ROWID,
				KEY_TITLE, KEY_DESCRIPTION, KEY_URL, KEY_IMAGE, KEY_IMAGE_URL, KEY_DATE, KEY_LASTMODIFIED}, null, null, null, null,
				null, null);
	}

	
	/**
	 * Return a Cursor positioned at the defined Article
	 */
	public Cursor fetchArticle(long rowId) throws SQLException {
		Cursor mCursor = database.query(true, DATABASE_TABLE, new String[] {
				KEY_ROWID, KEY_TITLE, KEY_DESCRIPTION, KEY_URL, KEY_IMAGE, KEY_IMAGE_URL, KEY_DATE, KEY_LASTMODIFIED},
				KEY_ROWID + "=" + rowId, null, null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	private ContentValues createContentValues(String title, String description, String url, byte[] imageArray, String imageurl, String date, String LastModified) {
		ContentValues values = new ContentValues();
		values.put(KEY_TITLE, title);
		values.put(KEY_DESCRIPTION, description);
		values.put(KEY_URL, url);
		values.put(KEY_IMAGE_URL, imageurl);
		values.put(KEY_IMAGE, imageArray);
		values.put(KEY_DATE, date);
		values.put(KEY_LASTMODIFIED, LastModified);
		return values;
	}
}
