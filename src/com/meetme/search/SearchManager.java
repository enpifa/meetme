package com.meetme.search;

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

import android.util.Log;

public class SearchManager {

	public ArrayList<User> searchForUsers(String query){
		ArrayList<User> result = new ArrayList<User>();
		String urlAmieggs = "http://www.amieggs.com/meetme/getUsers.php";
		//preparem dades a enviar
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("search", query));
		    
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
		
		//parse json data
		try{
		    JSONArray jArray = new JSONArray(recievedData);
		    for(int i=0;i<jArray.length();i++){
		        JSONObject json_data = jArray.getJSONObject(i);
		        /*Log.i("log_tag","username: "+json_data.getString("username")+
		                ", name: "+json_data.getString("name")+
		                ", company: "+json_data.getString("company")+
		                ", position: "+json_data.getString("position")
		        );*/
		        User tmp = new User();
		        tmp.setUsername(json_data.getString("username"));
		        tmp.setName(json_data.getString("name"));
		        tmp.setCompany(json_data.getString("company"));
		        tmp.setPosition(json_data.getString("position"));
		        result.add(tmp);
		    }
		}
		catch(JSONException e){
		        Log.e("log_tag", "Error parsing data "+e.toString());
		}
		
		return result;
	}
	
	public User searchForUser(String username) {
		String urlAmieggs = "http://www.amieggs.com/meetme/getUserData.php";
		User user = new User();
		
		//preparem dades a enviar
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("username", username));
		    
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
	
	public void saveUser(User user){
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
