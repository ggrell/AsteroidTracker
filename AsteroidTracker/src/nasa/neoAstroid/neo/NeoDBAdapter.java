package nasa.neoAstroid.neo;

import com.vitruviussoftware.bunifish.asteroidtracker.database.AsteroidTracker_DatabaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class NeoDBAdapter {

	public static final String KEY_ROWID = "_id";
	public static final String KEY_NAME = "name";
	public static final String KEY_CLOSEAPPROACHDATE = "closeapproachdate";
	public static final String KEY_CLOSEAPPROACHDATE_STR = "closeapproachdate_str";
	public static final String KEY_MISSDISTANCE_AU = "missdistance_au";
	public static final String KEY_MISSDISTANCE_LD = "missdistance_ld";
	public static final String KEY_ESTIMATEDDIAMETER= "estimateddiameter";
	public static final String KEY_HMAGNITUDE = "hmagnitude";
	public static final String KEY_RELETIVEVELOCITY = "relativevelocity";
	public static final String KEY_URL = "url";
	public static final String KEY_LASTMODIFIED = "LastModified";
	private static final String DATABASE_TABLE = "neo";
	private Context context;
	private SQLiteDatabase database;
	private AsteroidTracker_DatabaseHelper dbHelper;
	
	
	public NeoDBAdapter(Context context) {
		this.context = context;
	}
	
	public NeoDBAdapter open() throws SQLException {
		dbHelper = new AsteroidTracker_DatabaseHelper(context);
		database = dbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		dbHelper.close();
	}
	
	public long createNewsArticle(String name, String closeapproachdate, String closeapproachdate_str, String missdistance_au, String missdistance_ld, String estimateddiameter, String hmagnitude, String relativevelocity, String url, String LastModified) {
		ContentValues initialValues = createContentValues(name, closeapproachdate, closeapproachdate_str, missdistance_au, missdistance_ld, estimateddiameter, hmagnitude, relativevelocity, url, LastModified);
		return database.insert(DATABASE_TABLE, null, initialValues);
	}
	
	private ContentValues createContentValues(String name, String closeapproachdate, String closeapproachdate_str, String missdistance_au, String missdistance_ld, String estimateddiameter, String hmagnitude, String relativevelocity, String url, String LastModified) {
		ContentValues values = new ContentValues();
		values.put(KEY_NAME, name);
		values.put(KEY_CLOSEAPPROACHDATE, closeapproachdate);
		values.put(KEY_CLOSEAPPROACHDATE_STR, closeapproachdate_str);
		values.put(KEY_MISSDISTANCE_AU, missdistance_au);
		values.put(KEY_MISSDISTANCE_LD, missdistance_ld);
		values.put(KEY_ESTIMATEDDIAMETER, estimateddiameter);
		values.put(KEY_HMAGNITUDE, hmagnitude);
		values.put(KEY_RELETIVEVELOCITY, relativevelocity);
		values.put(KEY_URL, url);
		values.put(KEY_LASTMODIFIED, LastModified);
return values;
	}

	/**
	 * Return a Cursor over the list of all NEO entries in the database
	 * @return Cursor over all notes
	 */
	public Cursor fetchAllEntries() {
		return database.query(DATABASE_TABLE, new String[] { KEY_ROWID, KEY_NAME, KEY_CLOSEAPPROACHDATE, KEY_CLOSEAPPROACHDATE_STR, 
				KEY_MISSDISTANCE_AU, KEY_MISSDISTANCE_LD, KEY_ESTIMATEDDIAMETER, KEY_HMAGNITUDE, KEY_RELETIVEVELOCITY, 
				KEY_URL, KEY_LASTMODIFIED}, null, null, null, null,
				null, null);
	}

	
	/**
	 * Return a Cursor positioned at the defined NEO entity
	 */
	public Cursor fetchNEOEntry(long rowId) throws SQLException {
		Cursor mCursor = database.query(true, DATABASE_TABLE, new String[] {
				KEY_ROWID, KEY_NAME, KEY_CLOSEAPPROACHDATE, KEY_CLOSEAPPROACHDATE_STR, KEY_MISSDISTANCE_AU, KEY_MISSDISTANCE_LD, 
				KEY_ESTIMATEDDIAMETER, KEY_HMAGNITUDE, KEY_RELETIVEVELOCITY, KEY_URL, KEY_LASTMODIFIED},
				KEY_ROWID + "=" + rowId, null, null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

}
