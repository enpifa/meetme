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


	/**
	 * Mètode encarregat d'actualitzar totes les dades del perfil d'un usuari.
	 * @param user conté el username i els valors a donar als atributs del profile
	 * @return cert si no ha donat cap error
	 */
	public boolean updateProfile(User user) {

		/**
		 * UPDATE NORMAL DE LA FILA DE LA TAULA PROFILES
		 */

		mDbHelper.updateProfile(user);

		/**
		 * PER A SIMPLIFICAR-HO FARÉ QUE S'ESBORRIN TOTES LES FILES DE L'USUARI
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


		//INSERCIÓ
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
	
	
	/**
	 * Mètode que a partir d'un username retorna les dades del perfil d'aquest usuari
	 * encapsulades en una instància de User.
	 * @param username identificador de l'usuari del perfil que es vol obtenir
	 * @return instància de User amb les dades del perfil de l'usuari username
	 */
	public User getProfile(String username) {
		Cursor cursor; 
		
		/**
		 * Recuperar les dades de la taula de profiles
		 */
		cursor = mDbHelper.fetchProfile(username);
		User user = new User();
		user.setUsername(username);
		user.setName(cursor.getString(cursor.getColumnIndex(MeetMeDbAdapter.KEY_NAME)));
		user.setCompany(cursor.getString(cursor.getColumnIndex(MeetMeDbAdapter.KEY_COMPANY)));
		user.setPosition(cursor.getString(cursor.getColumnIndex(MeetMeDbAdapter.KEY_POSITION)));
		user.setImage(cursor.getString(cursor.getColumnIndex(MeetMeDbAdapter.KEY_IMAGE)));
		user.setTwitter(cursor.getString(cursor.getColumnIndex(MeetMeDbAdapter.KEY_TWITTER)));

		/**
		 * Recuperar les dades de les taules phones, emails i webs
		 */
		cursor = mDbHelper.fetchPhonesOf(username);
		for (cursor.moveToFirst(); cursor.moveToNext(); cursor.isAfterLast()) {
			user.addPhone(cursor.getString(cursor.getColumnIndex(MeetMeDbAdapter.KEY_PHONE)));
		}
		
		cursor = mDbHelper.fetchMailsOf(username);
		for (cursor.moveToFirst(); cursor.moveToNext(); cursor.isAfterLast()) {
			user.addEmail(cursor.getString(cursor.getColumnIndex(MeetMeDbAdapter.KEY_MAIL)));
		}
		
		cursor = mDbHelper.fetchWebsOf(username);
		for (cursor.moveToFirst(); cursor.moveToNext(); cursor.isAfterLast()) {
			user.addWeb(cursor.getString(cursor.getColumnIndex(MeetMeDbAdapter.KEY_WEB)));
		}
		
		return user;
	}
}
