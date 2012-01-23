package com.meetme.contacts;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.meetme.app.MeetMeDbAdapter;
import com.meetme.app.PreferencesAdapter;
import com.meetme.app.User;
import com.meetme.app.WebAccessAdapter;

public class ContactsDataManager {
 //TODO fer el inserir un nou username comment i location i recuperar llista de usernames, i comment i location de un username
	MeetMeDbAdapter mda;
	WebAccessAdapter wad;
	PreferencesAdapter pa;
	
	/**
	 * Defineix les variables de la classe
	 * @param context el context actual
	 */
	
	public ContactsDataManager(Context context){
		//mContext = context;
		mda  = new MeetMeDbAdapter(context);
		wad = WebAccessAdapter.getInstance();
		pa = new PreferencesAdapter(context);
	}
	
	/**
	 * Afegeix un contacte
	 * @param contact l'usuari que volem afegir com a contacte
	 */
	
	public void addContact(User contact) {
		mda.createContact(pa.getActiveUsername(), contact.getUsername(), contact.getComment(), contact.getLocation());
	}
	
	/**
	 * Obt� els contactes d'un usuari
	 * @return llista d'usuaris que figuren com a contactes
	 */
	
	public ArrayList<User> getContacts(){
		
        //PreferencesAdapter pa = new PreferencesAdapter(mContext);
        String username = pa.getActiveUsername();
        ArrayList<User> contacts = mda.fetchContacts(username);
        System.out.println("contacts: " + contacts.size());
        //actualitzar per web la resta de dades basiques
        if(contacts.size() > 0) addWebInfoToContacts(contacts);
        
        return contacts;
	}
	
	/**
	 * Tanca la base de dades
	 */
	
	public void closeDb() {
		if (mda != null) mda.close();
	}
	
	/**
	 * Aconsegueix la informaci� dels contactes d'un usuari per mostrar-la per la pantalla de contactes
	 * @param contacts llista d'usuaris que s�n contactes
	 */
	
	public void addWebInfoToContacts(ArrayList<User> contacts){
    	String url = "http://www.amieggs.com/meetme/getUsersBasicData.php";

		//preparem dades a enviar
		JSONArray usernames = new JSONArray();
		for(User contact : contacts){
			usernames.put(contact.getUsername());
		}
		JSONObject json_object = new JSONObject();
		try {
			json_object.put("usernames", usernames);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		
		String recievedData = wad.getWebAccessData(url, json_object.toString());
		
		//parse json data
		try{
			JSONArray json_data = new JSONArray(recievedData);
			for (int i = 0; i < json_data.length(); i++) {
				JSONObject user_data = json_data.getJSONObject(i);
				String json_username = user_data.getString("username");
				for(User contact : contacts){
					if(contact.getUsername().equals(json_username)){
						contact.setName(user_data.getString("name"));
						contact.setCompany(user_data.getString("company"));
						contact.setPosition(user_data.getString("position"));
					}
				}
				
			}
		}
		catch(JSONException e){
		        Log.e("log_tag", "Error parsing data "+e.toString());
		}

    }
}