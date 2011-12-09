package com.meetme.profile;

import java.util.ArrayList;

import com.meetme.app.MeetMeDbAdapter;

import android.content.ContentValues;

public class ProfileDataManager {

    private MeetMeDbAdapter mDbHelper;

	 public boolean updateUser(long rowId, String username, String password, String name,
	    		String company, String position, String image, String twitter, String twitterpass,
	    		ArrayList<String> phones, ArrayList<String> mails, ArrayList<String> webs) {
			
	    	/**
	    	 * UPDATE NORMAL DE LA FILA DE LA TAULA USER
	    	 */
	    	mDbHelper.updateUser(rowId, username, password, name, company, position, image, twitter, twitterpass);
	    	
	    	/**
	    	 * PER A SIMPLIFICAR-HO FARƒ QUE S'ESBORRIN TOTES LES FILES DE L'USUARI
	    	 * A LES TAULES AUXILIARS I QUE S'INSEREIXIN LES DELS ARRAYS
	    	 */
	    	
	    	// ESBORRAT
	    	if (!mDbHelper.deletePhonesOfUser(username)) return false;
	    	if (!mDbHelper.deleteMailsOfUSer(username)) return false;
	    	if (!mDbHelper.deleteWebsOfUser(username)) return false;
	    	
	    	//INSERCIî
	    	for (int i = 0; i < phones.size(); ++i) {
	        	args = new ContentValues();
	        	args.put(KEY_USERNAME, username);
	        	args.put(KEY_PHONE, phones.get(i));
	        	if (mDb.insert(DATABASE_TABLE_PHONES, null, args) < 0) return false;
	    	}
	    	
	    	for (int i = 0; i < mails.size(); ++i) {
	        	args = new ContentValues();
	        	args.put(KEY_USERNAME, username);
	        	args.put(KEY_MAIL, mails.get(i));
	        	if (mDb.insert(DATABASE_TABLE_MAILS, null, args) < 0) return false;

	    	}
	    	
	    	for (int i = 0; i < webs.size(); ++i) {
	        	args = new ContentValues();
	        	args.put(KEY_USERNAME, username);
	        	args.put(KEY_WEB, webs.get(i));
	        	if (mDb.insert(DATABASE_TABLE_WEBS, null, args) < 0) return false;
	    	}
	    	
	    	return true;
	    }
}
