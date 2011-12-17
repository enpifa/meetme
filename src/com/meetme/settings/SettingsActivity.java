package com.meetme.settings;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.meetme.app.R;

public class SettingsActivity extends Activity {
	private SettingsManager sm;

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
	
	public void logout(View v) {
		sm.logout();
		finish();
	}
	
	public void deleteCurrentUser(View v) {
		sm.deleteCurrentUser();
		sm.logout();
		finish();
	}
}
