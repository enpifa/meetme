package com.meetme.profile;

import java.util.ArrayList;

import com.meetme.app.MeetMeDbAdapter;

public class ProfileDataManager {

    private MeetMeDbAdapter mDbHelper;

	 public boolean updateProfile(String username, String name,
	    		String company, String position, String image, String twitter, String twitterpass,
	    		ArrayList<String> phones, ArrayList<String> mails, ArrayList<String> webs) {
			
	    	/**
	    	 * UPDATE NORMAL DE LA FILA DE LA TAULA PROFILES
	    	 */
		 
	    	mDbHelper.updateProfile(username, name, company, position, image, twitter, twitterpass);
	    	
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
	        	if (mDbHelper.createPhone(username, phones.get(i)) < 0) return false;	
	    	}
	    	
	    	for (int i = 0; i < mails.size(); ++i) {
	    		if (mDbHelper.createMail(username, mails.get(i)) < 0) return false;
	    	}
	    	
	    	for (int i = 0; i < webs.size(); ++i) {
	    		if (mDbHelper.createWeb(username, webs.get(i)) < 0) return false;
	    	}
	    	
	    	return true;
	    }
}
