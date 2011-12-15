package com.meetme.contacts;

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
import com.meetme.search.User;

public class ContactsDataManager {
 //TODO fer el inserir un nou username comment i location i recuperar llista de usernames, i comment i location de un username
	MeetMeDbAdapter mda;
	Context mContext;
	
	public ContactsDataManager(Context context){
		mContext = context;
		mda  = new MeetMeDbAdapter(mContext);
	}
	
	public ArrayList<User> getContacts(){
		
        PreferencesAdapter pa = new PreferencesAdapter(mContext);
        String username = pa.getActiveUsername();
        ArrayList<User> contacts = mda.fetchContacts(username);
        //actualitzar per web la resta de dades basiques
        if(contacts.size() > 0) addWebInfoToContacts(contacts);
        
        return contacts;
	}
	
	public void addWebInfoToContacts(ArrayList<User> contacts){
    	String urlAmieggs = "http://www.amieggs.com/meetme/getUsersBasicData.php";

		//preparem dades a enviar
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
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
		nameValuePairs.add(new BasicNameValuePair("data", json_object.toString()));
		    
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
		    System.out.println(recievedData);
		}
		catch(Exception e){
		        Log.e("log_tag", "Error converting result "+e.toString());
		}
		
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
