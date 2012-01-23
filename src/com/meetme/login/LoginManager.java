package com.meetme.login;

import java.util.ArrayList;

import android.content.Context;

import com.meetme.app.MeetMeDbAdapter;
import com.meetme.app.PreferencesAdapter;
import com.meetme.app.User;

public class LoginManager {
    
	private MeetMeDbAdapter mDbHelper;
    private PreferencesAdapter pa;
    
    /**
	 * Funció que crea les variables de la classe
	 * @param context context actual
	 */
    
    public LoginManager(Context context) {
		super();
		mDbHelper = new MeetMeDbAdapter(context);
		pa = new PreferencesAdapter(context);
	}
    
    /**
     * Per a registrar un nou usuari. Es cridarˆ desprŽs d'haver comprovat que no existeix.
     * @param username el nom d'usuari
     * @param password la contrassenya
     */
    
    public void registerUser(String username, String password) {
    	mDbHelper.createUser(username, password);
    	User user = new User();
    	user.setUsername(username);
    	mDbHelper.createProfile(user);
    }
    
	/**
     * Comprova si és correcte l'usuari + password
     * @param username el nom d'usuari
     * @param password la contrassenya
     * @return cert si existeix un usuari username amb contrassenya password.
     */
    public boolean correctUserAndPassword(String username, String password) {
    	if (!existsUser(username)) return false;
    	User user = mDbHelper.fetchUser(username);
    	return user.getPassword().equals(password);
    }
    
    /**
     * Comprova si ja existeix l'usuari.
     * @param username el nom d'usuari
     * @return cert si ja existeix un usuari username.
     */
    public boolean existsUser(String username) {
    	return mDbHelper.existsUser(username);
    }
    
    /**
     * Tanca la mDbhelper
     */
    public void closeDb() {
    	if (mDbHelper != null) mDbHelper.close();
    }
    
    /**
     * Agafa els usuaris existents a la base de dades
     */
    public ArrayList<User> getUsers() {
    	return mDbHelper.fetchAllProfiles();
    }

    /**
     * Estableix la mDbHelper
     * @param mDbHelper l'adaptador de la base de dades
     */
	public void setmDbHelper(MeetMeDbAdapter mDbHelper) {
		this.mDbHelper = mDbHelper;
	}
	
	/**
     * Estableix el username
     * @param username nom d'usuari
     */
	public void setActiveUser(String username){
		pa.setActiveUser(username);
	}
}