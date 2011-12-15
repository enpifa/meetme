package com.meetme.settings;

import com.meetme.app.MeetMeDbAdapter;
import com.meetme.app.PreferencesAdapter;

public class SettingsManager {
	private MeetMeDbAdapter mDbHelper;
	private PreferencesAdapter pa;
	public void deleteUser(String username) {
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
	
}
