package com.meetme.profile;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.meetme.app.MeetMeDbAdapter;
import com.meetme.app.PreferencesAdapter;
import com.meetme.app.User;

public class ProfileDataManager {

	private MeetMeDbAdapter mDbHelper;
	private PreferencesAdapter pa;
	
	/**
	 * Crea les variables de la classe
	 * @param context el context actual
	 */
	
	public ProfileDataManager(Context context) {
		super();
		mDbHelper = new MeetMeDbAdapter(context);
		pa = new PreferencesAdapter(context);
	}

	public String getActiveUsername() {
		return pa.getActiveUsername();
	}

	/**
	 * MËtode encarregat d'actualitzar totes les dades del perfil d'un usuari.
	 * @param user conté el username i els valors a donar als atributs del profile
	 * @return cert si no ha donat cap error
	 */
	public boolean updateProfile(User user) {

		/**
		 * UPDATE NORMAL DE LA FILA DE LA TAULA PROFILES
		 */

		mDbHelper.updateProfile(user);

		/**
		 * PER A SIMPLIFICAR-HO FARÉ QUE S'ESBORRIN TOTES LES FILES DE L'USUARI
		 * A LES TAULES AUXILIARS I QUE S'INSEREIXIN LES DELS ARRAYS
		 */

		String username = user.getUsername();
		ArrayList<String> phones = user.getPhones();
		ArrayList<String> emails = user.getEmails();	    	
		ArrayList<String> webs = user.getWebs();

		System.out.print("hola1");

		// ESBORRAT
		mDbHelper.deletePhonesOfUser(username);
		mDbHelper.deleteEmailsOfUSer(username);
		mDbHelper.deleteWebsOfUser(username);

		//INSERCIÓ
		for (int i = 0; i < phones.size(); i++) {
			if (!mDbHelper.createPhone(username, phones.get(i))) return false;	
		}

		for (int i = 0; i < emails.size(); i++) {
			if (!mDbHelper.createMail(username, emails.get(i))) return false;
		}

		for (int i = 0; i < webs.size(); i++) {
			if (!mDbHelper.createWeb(username, webs.get(i))) return false;
		}

		return true;
	}
	
	
	/**
	 * MËtode que a partir d'un username retorna les dades del perfil d'aquest usuari
	 * encapsulades en una instància de User.
	 * @param username identificador de l'usuari del perfil que es vol obtenir
	 * @return instància de User amb les dades del perfil de l'usuari username
	 */
	public User getProfile(String username) {
		User user = mDbHelper.fetchProfile(username);
		return user;
	}
	
	/**
	 * Recuperar directament l'usuari actual
	 * @return
	 */
	public User getCurrentUserData(){
		return getProfile(pa.getActiveUsername());
	}
	
	/**
	 * Enviar les dades de l'usuari a la BD de web
	 */
	
	public void syncData(){
		saveUserToWeb(getCurrentUserData());
	}
	
	/**
	 * Tanca la base de dades
	 */
	
    public void closeDb() {
    	if (mDbHelper != null) mDbHelper.close();
    }

	
	/**
	 * Enviar les dades de l'usuari a la BD de web
	 * @param user
	 */
	private void saveUserToWeb(User user){
		//preparar les dades
		JSONObject dadesAEnviar = new JSONObject();
		try {
			dadesAEnviar.put("username", user.getUsername());
			dadesAEnviar.put("name", user.getName());
			dadesAEnviar.put("company", user.getCompany());
			dadesAEnviar.put("position", user.getPosition());
			dadesAEnviar.put("twitter", user.getTwitter());
			JSONArray phones = new JSONArray(user.getPhones());
			dadesAEnviar.put("phones", phones);
			JSONArray emails = new JSONArray(user.getEmails());
			dadesAEnviar.put("emails", emails);
			JSONArray webs = new JSONArray(user.getWebs());
			dadesAEnviar.put("webs", webs);
		} catch (JSONException e) {
			Log.e("log_tag", "error amb JSON a " + e.toString());
		}
		
		//enviar les dades
		String urlAmieggs = "http://www.amieggs.com/meetme/saveUserData.php";
		
		//preparem dades a enviar
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("data", dadesAEnviar.toString()));
		    
		//http post
		InputStream is = null;
		try{
			HttpClient httpclient = new DefaultHttpClient();
	        HttpPost httppost = new HttpPost(urlAmieggs);
	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	        HttpResponse response = httpclient.execute(httppost);
	        HttpEntity entity = response.getEntity();
	        is = entity.getContent();
		}
		catch(Exception e){
			e.printStackTrace();
			Log.e("log_tag", "Error in http connection "+e.toString());
		}
		String recievedData = "";
		//convert response to string
		try{
		    BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
		    StringBuilder sb = new StringBuilder();
		    String line = null;
		    while ((line = reader.readLine()) != null) {
		        sb.append(line + "\n");
		    }
		    is.close();
		 
		    recievedData=sb.toString();
		}
		catch(Exception e){
		        Log.e("log_tag", "Error converting result "+e.toString());
		}
		
		Log.e("log_tag", recievedData);
	}
}