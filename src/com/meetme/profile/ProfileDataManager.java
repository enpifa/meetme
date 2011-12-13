package com.meetme.profile;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;

import com.meetme.app.MeetMeDbAdapter;
import com.meetme.search.User;

public class ProfileDataManager {

	private MeetMeDbAdapter mDbHelper;

	
	public ProfileDataManager(Context ctx) {
		super();
		mDbHelper = new MeetMeDbAdapter(ctx);
		mDbHelper.open();
	}


	public boolean updateProfile(User user) {

		/**
		 * UPDATE NORMAL DE LA FILA DE LA TAULA PROFILES
		 */

		mDbHelper.updateProfile(user);

		/**
		 * PER A SIMPLIFICAR-HO FARƒ QUE S'ESBORRIN TOTES LES FILES DE L'USUARI
		 * A LES TAULES AUXILIARS I QUE S'INSEREIXIN LES DELS ARRAYS
		 */

		String username = user.getUsername();
		ArrayList<String> phones = user.getPhones();
		ArrayList<String> emails = user.getEmails();	    	
		ArrayList<String> webs = user.getWebs();


		// ESBORRAT
		if (!mDbHelper.deletePhonesOfUser(username)) return false;
		if (!mDbHelper.deleteMailsOfUSer(username)) return false;
		if (!mDbHelper.deleteWebsOfUser(username)) return false;


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
	
	public User getProfile(String username) {
		Cursor cursor = mDbHelper.fetchProfile(username);
		
		User user = new User();
		
		user.setUsername(username);
		user.setName(cursor.getString(cursor.getColumnIndex(MeetMeDbAdapter.KEY_NAME)));
		return user;
	}
}
