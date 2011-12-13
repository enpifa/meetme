package com.meetme.profile;

import java.util.ArrayList;

import com.meetme.app.MeetMeDbAdapter;
import com.meetme.search.User;

public class ProfileDataManager {

    private MeetMeDbAdapter mDbHelper;

	 public boolean updateProfile(User user) {
		 
		 	String username = user.getUsername();
	    	/**
	    	 * UPDATE NORMAL DE LA FILA DE LA TAULA PROFILES
	    	 */
		 
	    	mDbHelper.updateProfile(username, user.getName(), user.getCompany(), user.getPosition(),
	    			user.getImage(), user.getTwitter(), user.getTwitterPass());
	    	
	    	/**
	    	 * PER A SIMPLIFICAR-HO FARƒ QUE S'ESBORRIN TOTES LES FILES DE L'USUARI
	    	 * A LES TAULES AUXILIARS I QUE S'INSEREIXIN LES DELS ARRAYS
	    	 */
	    	
	    	// ESBORRAT
	    	if (!mDbHelper.deletePhonesOfUser(username)) return false;
	    	if (!mDbHelper.deleteMailsOfUSer(username)) return false;
	    	if (!mDbHelper.deleteWebsOfUser(username)) return false;
	    	
	    	ArrayList<String> phones = user.getPhones();
	    	ArrayList<String> emails = user.getEmails();
	    	ArrayList<String> webs = user.getWebs();

	    	
	    	//INSERCIî
	    	for (int i = 0; i < phones.size(); ++i) {
	        	if (mDbHelper.createPhone(username, phones.get(i)) < 0) return false;	
	    	}
	    	
	    	for (int i = 0; i < emails.size(); ++i) {
	    		if (mDbHelper.createMail(username, emails.get(i)) < 0) return false;
	    	}
	    	
	    	for (int i = 0; i < webs.size(); ++i) {
	    		if (mDbHelper.createWeb(username, webs.get(i)) < 0) return false;
	    	}
	    	
	    	return true;
	    }
}
