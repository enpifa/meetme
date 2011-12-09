package com.meetme.profile;

import java.util.ArrayList;

import android.database.Cursor;
import android.sax.StartElementListener;

import com.meetme.app.MeetMeDbAdapter;

public class ProfileDataManager {

    private MeetMeDbAdapter mDbHelper;

	 public boolean updateUser(long userId, String name,
	    		String company, String position, String image, String twitter, String twitterpass,
	    		ArrayList<String> phones, ArrayList<String> mails, ArrayList<String> webs) {
			
	    	/**
	    	 * UPDATE NORMAL DE LA FILA DE LA TAULA USER
	    	 */
		 	Cursor aux = mDbHelper.fetchUser(userId);
		 	String username = aux.getString(aux.getColumnIndexOrThrow(MeetMeDbAdapter.KEY_USERNAME));
	    	String password = aux.getString(aux.getColumnIndexOrThrow(MeetMeDbAdapter.KEY_PASSWORD));
		 	mDbHelper.updateUser(userId, username, password, name, company, position, image, twitter, twitterpass);
	    	
	    	/**
	    	 * PER A SIMPLIFICAR-HO FARƒ QUE S'ESBORRIN TOTES LES FILES DE L'USUARI
	    	 * A LES TAULES AUXILIARS I QUE S'INSEREIXIN LES DELS ARRAYS
	    	 */
	    	
	    	// ESBORRAT
	    	if (!mDbHelper.deletePhonesOfUser(userId)) return false;
	    	if (!mDbHelper.deleteMailsOfUSer(userId)) return false;
	    	if (!mDbHelper.deleteWebsOfUser(userId)) return false;
	    	
	    	
	    	//INSERCIî
	    	for (int i = 0; i < phones.size(); ++i) {
	        	if (mDbHelper.createPhone(userId, phones.get(i)) < 0) return false;	
	    	}
	    	
	    	for (int i = 0; i < mails.size(); ++i) {
	    		if (mDbHelper.createMail(userId, mails.get(i)) < 0) return false;
	    	}
	    	
	    	for (int i = 0; i < webs.size(); ++i) {
	    		if (mDbHelper.createWeb(userId, webs.get(i)) < 0) return false;
	    	}
	    	
	    	return true;
	    }
}
