package com.meetme.app;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesAdapter {
	private SharedPreferences userPreferences;
	
	/**
	 * Defineix la variable de la classe
	 * @param context el context actual
	 */
	
	public PreferencesAdapter(Context context){
		userPreferences = context.getSharedPreferences("general", 0);
	}
	
	/**
	 * Estableix les preferences de l'usuari
	 * @param username nom d'usuari
	 */
	
	public void setActiveUser(String username) {
		SharedPreferences.Editor prefEditor = userPreferences.edit();
		prefEditor.putString("activeUser", username);
		prefEditor.commit();
	}
    
	/**
	 * Aconsegueix les preferences de l'usuari
	 * @return retorna les preferences de l'usuari actiu
	 */
	
	public String getActiveUsername(){
		return userPreferences.getString("activeUser", null);
	}
}