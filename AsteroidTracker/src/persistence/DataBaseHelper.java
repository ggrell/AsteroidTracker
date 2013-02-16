/*
 * @(#)DataBaseHelper
 *
 * Copyright 2013 by Constant Contact Inc.,
 * Waltham, MA 02451, USA
 * Phone: (781) 472-8100
 * Fax: (781) 472-8101
 *
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Constant Contact, Inc. created for Constant Contact, Inc.
 * You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with Constant Contact, Inc.
 * 
 * History
 *
 * Date         Author      Comments
 * ====         ======      ========
 *
 * 
 **/
package persistence;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import domains.NearEarthObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseHelper extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "AsteroidTracker";
    private static final String TABLE_NEAR_EARTH_OBJECTS = "NearEarthObjects";
    private static final String TABLE_SPACE_TRACKS = "SpaceTracks";

    // NEO Table Columns names
    private static final String id = "id";
    private static final String name = "name";
    private static final String closeApproachDates= "close_approach_date";
    private static final String missDistanceAu="miss_distance_au";
    private static final String missDistanceLd="miss_distance_ld";
    private static final String estimatedDiameter="estimated_diameter";
    private static final String hMgnitude="h_magnitude";
    private static final String relativeVelocity="relative_velocity";
    private static final String url = "url";

    private SQLiteDatabase database;
    
    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void open() throws SQLException {
        database = getWritableDatabase();
      }
    
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("DATABASE", "Creating DB TABLES");

        String CREATE_NEAR_EARTH_OBJECT_TABLE = 
                "CREATE TABLE " + TABLE_NEAR_EARTH_OBJECTS + "("
                + id + " INTEGER PRIMARY KEY," 
                + name + " TEXT,"
                + closeApproachDates + " TEXT,"
                + missDistanceAu + " TEXT,"
                + missDistanceLd + " TEXT,"
                + estimatedDiameter + " TEXT,"
                + hMgnitude + " TEXT,"
                + url + " TEXT"
                + ")";
                db.execSQL(CREATE_NEAR_EARTH_OBJECT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NEAR_EARTH_OBJECTS); 
        onCreate(db);
    }
    
    // Adding new contact
    public void addNearEarthObject(NearEarthObject neo) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(name, neo.getName());

        // Inserting Row
        db.insert(TABLE_NEAR_EARTH_OBJECTS, null, values);
        db.close(); // Closing database connection
    }
    
    // Getting All Contacts
    public List<NearEarthObject> getAllNearEarthObject() {
        List<NearEarthObject> contactList = new ArrayList<NearEarthObject>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NEAR_EARTH_OBJECTS;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                NearEarthObject neo = new NearEarthObject();
//                contact.setID(Integer.parseInt(cursor.getString(0)));
                neo.setName(cursor.getString(1));
                } while (cursor.moveToNext());
        }
        return contactList;
    }



}
