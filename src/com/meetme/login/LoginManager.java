package com.meetme.login;

import java.util.ArrayList;

import android.content.Context;

import com.meetme.app.MeetMeDbAdapter;
import com.meetme.app.PreferencesAdapter;
import com.meetme.app.User;

public class LoginManager {
    
	private MeetMeDbAdapter mDbHelper;
    private PreferencesAdapter pa;
    
    public LoginManager(Context context) {
		super();
		mDbHelper = new MeetMeDbAdapter(context);
		pa = new PreferencesAdapter(context);
	}
    
    /**
     * Per a registrar un nou usuari. Es cridarà després d'haver comprovat que no existeix.
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
    
    public void closeDb() {
    	if (mDbHelper != null) mDbHelper.close();
    }
    
    public ArrayList<User> getUsers() {
    	return mDbHelper.fetchAllProfiles();
    }

	public void setmDbHelper(MeetMeDbAdapter mDbHelper) {
		this.mDbHelper = mDbHelper;
	}
	
	public void setActiveUser(String username){
		pa.setActiveUser(username);
	}
}
