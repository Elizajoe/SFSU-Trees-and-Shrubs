/*  Copyright (C) 2013   Divya Muthyala & Meenal Nitin Honap.

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>. */

package com.herps.database;
 
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.herps.data.Observation;
 
public class DatabaseHandler extends SQLiteOpenHelper {
 
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "SNFCHerpsObservations";
 
    // Contacts table name
    private static final String TABLE_OBSERVATIONS = "observations";
 
    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String COMMON_NAME = "comonName";
    private static final String IMAGE_NAME = "image";
    private static final String FAMILY = "family";
    private static final String LATITUDE = "latitude";
    private static final String LONGITUDE = "longitude";
    private static final String SPECIES = "species";
    private static final String TIMESTAMP = "timestamp";
    
 
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_OBSERVATION_TABLE = "CREATE TABLE " + TABLE_OBSERVATIONS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + COMMON_NAME + " TEXT,"
                + IMAGE_NAME + " TEXT," + FAMILY + " TEXT," + LATITUDE + " TEXT," + LONGITUDE + " TEXT," + SPECIES + " TEXT," + TIMESTAMP + " TEXT " + ")";
        db.execSQL(CREATE_OBSERVATION_TABLE);
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OBSERVATIONS);
 
        // Create tables again
        onCreate(db);
    }
 
    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */
 
    // Adding new Observation
    public void addObservation(Observation observation) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(COMMON_NAME, observation.getCommonName()); // Contact Name
        values.put(IMAGE_NAME, observation.getImageName()); // Contact image
        values.put(FAMILY, observation.getFamily()); // Contact family
        values.put(LATITUDE, observation.getLatitude()); // Contact latitude
        values.put(LONGITUDE, observation.getLongitude()); // Contact Longitude
        values.put(SPECIES, observation.getSpecies()); // Contact species
        values.put(TIMESTAMP, observation.getTimeStamp()); // Contact time
 
        // Inserting Row
        db.insert(TABLE_OBSERVATIONS, null, values);
        db.close(); // Closing database connection
    }
 
    // Getting single Observation
    public Observation getObservation(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
 
        Cursor cursor = db.query(TABLE_OBSERVATIONS, new String[] { KEY_ID,
                COMMON_NAME, IMAGE_NAME, FAMILY, LATITUDE, LONGITUDE, SPECIES, TIMESTAMP }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
 
        Observation observation = new Observation(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6),cursor.getString(7));
        // return contact
        return observation;
    }
 
    // Getting All Observations
    public List<Observation> getAllObservations() {
        List<Observation> observations = new ArrayList<Observation>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_OBSERVATIONS;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        String text = null;
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Observation observation = new Observation();
                observation.setId(Integer.parseInt(cursor.getString(0)));
                observation.setCommonName(cursor.getString(1));
                observation.setImageName(cursor.getString(2));
                observation.setFamily(cursor.getString(3));
                observation.setLatitude(cursor.getString(4));
                observation.setLongitude(cursor.getString(5));
                observation.setSpecies(cursor.getString(6));
                observation.setTimeStamp(cursor.getString(7));
                // Adding observations to list
                observations.add(observation);

            } while (cursor.moveToNext());
        }
 
        // return Observation list
        return observations;
		
    }
    public String AllObservation()
    
	{
		String AO= null;
		 String selectQuery = "SELECT  * FROM " + TABLE_OBSERVATIONS;
		 
	        SQLiteDatabase db = this.getWritableDatabase();
	        Cursor cursor = db.rawQuery(selectQuery, null);
	       
	        // looping through all rows and adding to list
	        if (cursor.moveToFirst()) {
	            do {
	            	AO = cursor.getString(1)+" ";
	            	AO = AO + cursor.getString(2)+" ";
	            	AO = AO + cursor.getString(3)+" ";
	            	AO = AO + cursor.getString(4)+" ";
	            	AO = AO + cursor.getString(5)+" ";
	            	AO = AO + cursor.getString(6)+" ";
	            	AO = AO + cursor.getString(7)+"\n";

	                // Adding Observations to list
	          
	            } while (cursor.moveToNext());
	        }
		
		return AO;
	
	}
	

    // Updating single Observation
    public int updateObservation(Observation observation) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(COMMON_NAME, observation.getCommonName());
        values.put(IMAGE_NAME, observation.getImageName());
        values.put(FAMILY, observation.getFamily()); 
        values.put(LATITUDE, observation.getLatitude()); 
        values.put(LONGITUDE, observation.getLongitude()); 
        values.put(SPECIES, observation.getSpecies()); 
        values.put(TIMESTAMP, observation.getTimeStamp()); 
 
        // updating row
        return db.update(TABLE_OBSERVATIONS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(observation.getId()) });
    }
 
    // Deleting single Observation
    public void deleteContact(Observation observation) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_OBSERVATIONS, KEY_ID + " = ?",
                new String[] { String.valueOf(observation.getId()) });
        db.close();
    }
 
    // Getting Observation Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_OBSERVATIONS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
 
        // return count
        return cursor.getCount();
    }
 
}