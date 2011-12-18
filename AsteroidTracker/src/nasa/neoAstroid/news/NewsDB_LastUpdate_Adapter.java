package nasa.neoAstroid.news;

import com.vitruviussoftware.bunifish.asteroidtracker.database.AsteroidTracker_DatabaseHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class NewsDB_LastUpdate_Adapter {

	private AsteroidTracker_DatabaseHelper dbHelper;
	private Context context;
	private SQLiteDatabase database;
	private static final String DATABASE_TABLE = "lastRssBuildUpdate";
	public static final String KEY_LASTBUILD_RSSDATE = "lastnewsdate";
	public static final String KEY_ROWID = "_id";
	
	public NewsDB_LastUpdate_Adapter(Context context) {
		this.context = context;
	}

	public NewsDB_LastUpdate_Adapter open() throws SQLException {
		dbHelper = new AsteroidTracker_DatabaseHelper(context);
		database = dbHelper.getWritableDatabase();
		return this;
	}
	
	public void close() {
		dbHelper.close();
	}
	
	public long updateLastBuildDate(String lastBuildDate){
		ContentValues initialValues = createContentValues(lastBuildDate);
		return database.insert(DATABASE_TABLE, null, initialValues);
	}
	
	private ContentValues createContentValues(String lastBuildDate) {
		ContentValues values = new ContentValues();
		values.put(KEY_LASTBUILD_RSSDATE, lastBuildDate);
		return values;
	}
	
	public Cursor fetchLastBuildDate(){
		Cursor mCursor = database.query(true, DATABASE_TABLE, new String[] {
				KEY_LASTBUILD_RSSDATE},
				KEY_ROWID + "=" + 1, null, null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}
	
	public boolean updateNewsArticle(long rowId, String lastBuildDate) {
		ContentValues updateValues = createContentValues(lastBuildDate);
		return database.update(DATABASE_TABLE, updateValues, KEY_ROWID + "=" + rowId, null) > 0;
	}
	
}
