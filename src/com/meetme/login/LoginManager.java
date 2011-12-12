package com.meetme.login;

import android.content.SharedPreferences;
import android.database.Cursor;

import com.meetme.app.MeetMeDbAdapter;

public class LoginManager {
    
	private MeetMeDbAdapter mDbHelper;
    private SharedPreferences userPreferences;
    
    /**
     * Per a registrar un nou usuari. Es cridarà després d'haver comprovat que no existeix.
     * @param username el nom d'usuari
     * @param password la contrassenya
     * @return la id del nou user o -1 si no s'ha creat
     */
    public long registerUser(String username, String password) {
    	return mDbHelper.createUser(username, password); // retorna la rowId perquè el login/register li haurà de passar la rowId de l'usuari a l'activitat principal o guardarla en alguna property com a active user
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

	public LoginManager(MeetMeDbAdapter mDbHelper) {
		super();
		this.mDbHelper = mDbHelper;
	}
	
	public void setActiveUser(String username) {
		SharedPreferences.Editor prefEditor = userPreferences.edit();
		prefEditor.putString("activeUser", username);
		prefEditor.commit();
	}
    
}
