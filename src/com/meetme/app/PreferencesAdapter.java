package com.meetme.app;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesAdapter {
	private SharedPreferences userPreferences;
	
	public PreferencesAdapter(Context context){
		userPreferences = context.getSharedPreferences("general", 0);
	}
	
	public void setActiveUser(String username) {
		SharedPreferences.Editor prefEditor = userPreferences.edit();
		prefEditor.putString("activeUser", username);
		prefEditor.commit();
	}
    
	public String getActiveUsername(){
		return userPreferences.getString("activeUser", null);
	}
}
