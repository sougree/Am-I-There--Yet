package com.sougree.amithereyet;

import java.util.HashMap;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class AlertContentProvider extends ContentProvider {

	static final String PROVIDER_NAME = "com.sougree.amithereyet"; 
	static final String URL = "content://" + PROVIDER_NAME + "/alerts"; 
	public static final Uri CONTENT_URI = Uri.parse(URL);
	
	public static final String _ID = "_id"; 
	public static final String NAME = "name"; 
	public static final String LATITUDE = "latitude";
	public static final String LONGITUDE = "longitude";
	public static final String RADIUS = "radius";
	public static final String NOTIFICATIONS = "notifications";
	
	/** * Database specific constant declarations */ 
	private SQLiteDatabase db; 
	static final String DATABASE_NAME = "AmIThereYet"; 
	static final String ALERT_TABLE_NAME = "Alert"; 
	static final int DATABASE_VERSION = 1; 
	static final String CREATE_DB_TABLE = " CREATE TABLE " + ALERT_TABLE_NAME + 
			" (_id INTEGER PRIMARY KEY AUTOINCREMENT, " + 
			" name TEXT NOT NULL, " +
			" latitude TEXT NOT NULL, " +
			" longitude TEXT NOT NULL, " +
			" radius TEXT NOT NULL, " +
			" notifications TEXT NOT NULL);";
	
	private static HashMap<String, String> ALERT_PROJECTION_MAP;
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		String id = uri.getPathSegments().get(1);
		int retVal = db.delete(ALERT_TABLE_NAME, _ID + "=" + id, selectionArgs);
		
		if (retVal>0) {
			getContext().getContentResolver().notifyChange(CONTENT_URI, null);
		}
		
		return retVal;
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		long id = db.insert(ALERT_TABLE_NAME, "", values);
		
		if(id > 0) {
			Uri retVal = ContentUris.withAppendedId(CONTENT_URI, id);
			getContext().getContentResolver().notifyChange(CONTENT_URI, null);
			return retVal;
		}
		throw new SQLException("Failed to insert record into " + CONTENT_URI);
	}

	@Override
	public boolean onCreate() {
		Context context = getContext();
		DatabaseHelper helper = new DatabaseHelper(context);
		db = helper.getWritableDatabase();
		return (db != null);
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(ALERT_TABLE_NAME);
		qb.setProjectionMap(ALERT_PROJECTION_MAP);
		Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, NAME);
		c.setNotificationUri(getContext().getContentResolver(), uri);
		return c;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		String whereClause = _ID + " = " + uri.getPathSegments().get(1);
		return db.update(ALERT_TABLE_NAME, values, whereClause, null);
	}
	
	private class DatabaseHelper extends SQLiteOpenHelper {

		public DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(CREATE_DB_TABLE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			//TODO:DO A SAFE UPGRADE
		}

		
		
	}

}
