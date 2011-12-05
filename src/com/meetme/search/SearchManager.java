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
		    user.setCompany(json_data.getString("company"));
		    user.setPosition(json_data.getString("position"));
		}
		catch(JSONException e){
		        Log.e("log_tag", "Error parsing data "+e.toString());
		}
		return user;
	}
}
