package com.meetme.settings;

import com.meetme.app.R;

import android.app.Activity;
import android.os.Bundle;

public class SettingsActivity extends Activity {

	@Override
	protected void onDestroy() {
		sm.closeDb();
		super.onDestroy();
	}

	private SettingsManager sm;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.settings);
    }
	
	public void logout() {
		sm.logout();
		finish();
	}
	

	
}
