package com.meetme.settings;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.meetme.app.R;

public class SettingsActivity extends Activity {
	private SettingsManager sm;

	/**
	 * M�tode encarregat de definir les diferents variables
	 * @param savedInstanceState
	 */
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sm = new SettingsManager(this);
        setContentView(R.layout.settings);
    }
	
	@Override
	protected void onDestroy() {
		sm.closeDb();
		super.onDestroy();
	}
	
	/**
	 * M�tode encarregat de tancar sessi� d'usuari
	 * @param v la vista actual
	 */
	
	public void logout(View v) {
		sm.logout();
		finish();
	}
	
	/**
	 * M�tode encarregat de borrar un usuari
	 * @param v vista actual
	 */
	
	public void deleteCurrentUser(View v) {
		sm.deleteCurrentUser();
		sm.logout();
		finish();
	}
}