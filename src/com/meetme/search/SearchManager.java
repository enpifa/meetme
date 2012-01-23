package com.meetme.search;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.meetme.app.PreferencesAdapter;
import com.meetme.app.User;
import com.meetme.app.WebAccessAdapter;
import com.meetme.contacts.ContactsDataManager;

import android.content.Context;
import android.util.Log;

public class SearchManager {

	//MeetMeDbAdapter mda;
	WebAccessAdapter wad;
	PreferencesAdapter pa;
	ContactsDataManager cdm;
	
	/**
	 * M�tode encarregat de definir les variables de la classe
	 * @param context el context actual
	 */
	
	public SearchManager(Context context){
		wad = WebAccessAdapter.getInstance();
		pa = new PreferencesAdapter(context);
		cdm = new ContactsDataManager(context);
	}
	
	/**
	 * M�tode encarregat d'obtenir una llista d'usuaris coneguts
	 * @param query nom que s'ha de buscar
	 * @result conjunt d'usuaris que cumpleixen amb la query
	 */
	
	public ArrayList<User> searchForUsers(String query){
		ArrayList<User> result = new ArrayList<User>();
		String url = "http://www.amieggs.com/meetme/getUsers.php";
		
		String recievedData = wad.getWebAccessData(url, query);//preparem dades a enviar
		
		//parse json data
		try{
		    JSONArray jArray = new JSONArray(recievedData);
		    for(int i=0;i<jArray.length();i++){
		        JSONObject json_data = jArray.getJSONObject(i);
		        User tmp = new User();
		        tmp.setUsername(json_data.getString("username"));
		        tmp.setName(json_data.getString("name"));
		        tmp.setCompany(json_data.getString("company"));
		        tmp.setPosition(json_data.getString("position"));
		        if(!pa.getActiveUsername().equals(tmp.getUsername())) result.add(tmp);
		    }
		}
		catch(JSONException e){
		        Log.e("log_tag", "Error parsing data "+e.toString());
		        e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * M�tode encarregat d'obtenir un usuari conegut de l'usuari loggejat
	 * @param username nom d'usuari que busquem
	 * @result usuari que busquem
	 */
	
	public User searchForUser(String username) {
		String url = "http://www.amieggs.com/meetme/getUserData.php";
		User user = new User();
		
		String recievedData = wad.getWebAccessData(url, username);
		System.out.println(recievedData);
		
		//parse json data
		try{
			JSONObject json_data = new JSONObject(recievedData);
		    user.setUsername(username);
		    user.setName(json_data.getString("name"));
		    if(json_data.getString("company") != "null"){
		    	user.setCompany(json_data.getString("company"));
		    }
		    if(json_data.getString("position") != "null"){
		    	user.setPosition(json_data.getString("position"));
		    }
		    if(json_data.getString("twitter") != "null") {
		    	user.setTwitter(json_data.getString("twitter"));
		    }

		    JSONArray json_emails = json_data.getJSONArray("emails");
		    for(int i = 0; i < json_emails.length(); ++i){
		    	user.addEmail(json_emails.getString(i));
		    }
		    
		    JSONArray json_phones = json_data.getJSONArray("phones");
		    for(int i = 0; i < json_phones.length(); ++i){
		    	user.addPhone(json_phones.getString(i));
		    }
		    
		    JSONArray json_webs = json_data.getJSONArray("webs");
		    for(int i = 0; i < json_webs.length(); ++i){
		    	user.addWeb(json_webs.getString(i));
		    }
		    
		    
		}
		catch(JSONException e){
		        Log.e("log_tag", "Error parsing data "+e.toString());
		}
		
		return user;
	}
	
	/**
	 * M�tode encarregat d'agregar un contacte d'amistat
	 * @param contact usuari que s'agrega com a amic
	 */
	
	public void addContact(User contact){
		cdm.addContact(contact);
	}
	
}