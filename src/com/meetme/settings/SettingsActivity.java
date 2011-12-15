package com.meetme.settings;

import com.meetme.app.R;

import android.app.Activity;
import android.os.Bundle;

public class SettingsActivity extends Activity {

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
