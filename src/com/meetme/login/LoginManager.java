package com.meetme.login;

import android.database.Cursor;

import com.meetme.app.MeetMeDbAdapter;
import com.meetme.profile.ProfileDataManager;

public class LoginManager {
    private MeetMeDbAdapter mDbHelper;
    private ProfileDataManager pdm;
    //Així o que comprovi dins registerUser si existeix o no? -->
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
    	Cursor user = pdm.getUser(username);
    	return user != null && user.getString(user.getColumnIndex(MeetMeDbAdapter.KEY_PASSWORD)) == password;
    }
    
    /**
     * Comprova si ja existeix l'usuari.
     * @param username el nom d'usuari
     * @return cert si ja existeix un usuari username.
     */
    public boolean existsUser(String username) {
    	return mDbHelper.fetchUser(username) != null;
    }
}
