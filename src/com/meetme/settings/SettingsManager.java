package com.meetme.settings;

import android.content.Context;

import com.meetme.app.MeetMeDbAdapter;
import com.meetme.app.PreferencesAdapter;

public class SettingsManager {
	private MeetMeDbAdapter mDbHelper;
	private PreferencesAdapter pa;
	
	/**
	 * M�tode encarregat de definir les variables
	 * @param savedInstanceState
	 */
	
	public SettingsManager(Context context) {
		super();
		mDbHelper = new MeetMeDbAdapter(context);
		pa = new PreferencesAdapter(context);
	}

	/**
	 * M�tode encarregat d'eliminar un usuari
	 */
	
	public void deleteCurrentUser() {
		String username = pa.getActiveUsername();
		mDbHelper.deleteProfile(username);
		mDbHelper.deleteContactsOfUser(username);
		mDbHelper.deletePhonesOfUser(username);
		mDbHelper.deleteWebsOfUser(username);
		mDbHelper.deleteEmailsOfUSer(username);
		mDbHelper.deleteUser(username);	
	}
	
	/**
	 * M�tode encarregat de tancar sessi�
	 */
	
	public void logout() {
		pa.setActiveUser(null);
	}
	
	/**
	 * M�tode encarregat de canviar el password d'un usuari
	 * @param password contrasenya de l'usuari
	 */
	
	public void changePassword(String password) {
		mDbHelper.updateUser(pa.getActiveUsername(), password);
	}
	
	/**
	 * Tanca la base de dades
	 */
	
	public void closeDb() {
		if (mDbHelper != null) mDbHelper.close();
	}
}