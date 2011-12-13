package com.meetme.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;

import com.meetme.app.MeetMeDbAdapter;
import com.meetme.search.User;

public class LoginManager {
    
	private MeetMeDbAdapter mDbHelper;
    private SharedPreferences userPreferences;
    
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
	void setActiveUser(String username) {
		
		SharedPreferences.Editor prefEditor = userPreferences.edit();
		prefEditor.putString("activeUser", username);
		prefEditor.commit();
	}

=======
=======
>>>>>>> 3015c695c5eb378fa6e6252bdb151771c283969c
=======
>>>>>>> 3015c695c5eb378fa6e6252bdb151771c283969c
=======
>>>>>>> 3015c695c5eb378fa6e6252bdb151771c283969c
    public LoginManager(Context context, MeetMeDbAdapter mDbHelper) {
		super();
		this.mDbHelper = mDbHelper;
		userPreferences = context.getSharedPreferences("general", 0);
	}
    
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
>>>>>>> 3015c695c5eb378fa6e6252bdb151771c283969c
=======
>>>>>>> 3015c695c5eb378fa6e6252bdb151771c283969c
=======
>>>>>>> 3015c695c5eb378fa6e6252bdb151771c283969c
=======
>>>>>>> 3015c695c5eb378fa6e6252bdb151771c283969c
    /**
     * Per a registrar un nou usuari. Es cridarà després d'haver comprovat que no existeix.
     * @param username el nom d'usuari
     * @param password la contrassenya
     */
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
    public long registerUser(String username, String password) {
    	return mDbHelper.createUser(username, password);
    	this.setActiveUser(username);
=======
=======
>>>>>>> 3015c695c5eb378fa6e6252bdb151771c283969c
=======
>>>>>>> 3015c695c5eb378fa6e6252bdb151771c283969c
=======
>>>>>>> 3015c695c5eb378fa6e6252bdb151771c283969c
    public void registerUser(String username, String password) {
    	mDbHelper.createUser(username, password);
    	User user = new User();
    	user.setUsername(username);
    	mDbHelper.createProfile(user);
    	setActiveUser(username);
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
>>>>>>> 3015c695c5eb378fa6e6252bdb151771c283969c
=======
>>>>>>> 3015c695c5eb378fa6e6252bdb151771c283969c
=======
>>>>>>> 3015c695c5eb378fa6e6252bdb151771c283969c
=======
>>>>>>> 3015c695c5eb378fa6e6252bdb151771c283969c
    }
    
	/**
     * Comprova si és correcte l'usuari + password
     * @param username el nom d'usuari
     * @param password la contrassenya
     * @return cert si existeix un usuari username amb contrassenya password.
     */
    public boolean correctUserAndPassword(String username, String password) {
    	if (!existsUser(username)) return false;
    	Cursor user = mDbHelper.fetchUser(username);
    	return user.getString(user.getColumnIndex(MeetMeDbAdapter.KEY_PASSWORD)).equals(password);
    }
    
    /**
     * Comprova si ja existeix l'usuari.
     * @param username el nom d'usuari
     * @return cert si ja existeix un usuari username.
     */
    public boolean existsUser(String username) {
    	return mDbHelper.existsUser(username);
    }

	public void setmDbHelper(MeetMeDbAdapter mDbHelper) {
		this.mDbHelper = mDbHelper;
	}
	
    
	public String getActiveUsername(){
		return userPreferences.getString("activeUser", null);
	}
}
