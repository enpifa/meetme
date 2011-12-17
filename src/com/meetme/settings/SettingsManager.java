package com.meetme.settings;

import android.content.Context;

import com.meetme.app.MeetMeDbAdapter;
import com.meetme.app.PreferencesAdapter;

public class SettingsManager {
	private MeetMeDbAdapter mDbHelper;
	private PreferencesAdapter pa;
	
	
	
	public SettingsManager(Context context) {
		super();
		mDbHelper = new MeetMeDbAdapter(context);
		pa = new PreferencesAdapter(context);

	}

	public void deleteCurrentUser() {
		String username = pa.getActiveUsername();
		mDbHelper.deleteProfile(username);
		mDbHelper.deleteContactsOfUser(username);
		mDbHelper.deletePhonesOfUser(username);
		mDbHelper.deleteWebsOfUser(username);
		mDbHelper.deleteEmailsOfUSer(username);
		mDbHelper.deleteUser(username);	
	}
	
	public void logout() {
		pa.setActiveUser(null);
	}
	
	
	public void changePassword(String password) {
		mDbHelper.updateUser(pa.getActiveUsername(), password);
	}
	
	public void closeDb() {
		if (mDbHelper != null) mDbHelper.close();
	}
}
