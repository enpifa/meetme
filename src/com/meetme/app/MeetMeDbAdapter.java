package com.meetme.app;

import com.android.demo.notepad3.NotesDbAdapter;
import com.android.demo.notepad3.NotesDbAdapter.DatabaseHelper;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class MeetMeDbAdapter {
	public static final String KEY_USERNAME = "username";
	public static final String KEY_ROWID = "_id";
	
	private static final String TAG = "MeetMeDbAdapter";
	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDb;
	
	private static final String DATABASE_CREATE_USERS = 
		"create table users (_id integer primary key autoincrement, "
		+ "username text not null, name text not null, company text, position text, image blob, twittername text, twitterpass text";
	
	private static final String DATABASE_CREATE_PHONES =
		"create table phones (_id integer primary key autoincrement, "
		+ "username text not null, phonenumber text not null";
	
	private static String DATABASE_CREATE_WEBS = 
		"create table webs (_id integer primary key autoincrement, "
		+ "username text not null, webpage text not null";
	
	private static final String DATABASE_CREATE_MAILS = 
		"create table mails (_id integer primary key autoincrement, "
		+ "username text not null, mail text not null";
	
	private static final String DATABASE_NAME = "data";
    private static final String DATABASE_TABLE = "users";
    private static final int DATABASE_VERSION = 1;
	
    private final Context mCtx;
    
    private static class DatabaseHelper extends SQLiteOpenHelper {
    	DatabaseHelper(Context context) {
    		super(context, DATABASE_NAME, null, DATABASE_VERSION);
    	}
    	
    	@Override
    	public void onCreate(SQLiteDatabase db) {
    		db.execSQL(DATABASE_CREATE_USERS);
    		db.execSQL(DATABASE_CREATE_PHONES);
    		db.execSQL(DATABASE_CREATE_WEBS);
    		db.execSQL(DATABASE_CREATE_MAILS);
    	}
    	

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS users");
            onCreate(db);			
		}
    	
    }
    
    public MeetMeDbAdapter(Context ctx) {
    	mCtx = ctx;
    }
    
    public MeetMeDbAdapter open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }
 
    public void close() {
        mDbHelper.close();
    }
}
