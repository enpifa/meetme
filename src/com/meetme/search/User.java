package com.meetme.search;

import java.util.ArrayList;

public class User {
	private String username;
	private String name;
	private String company;
	private String position;
	private String twitter;
	private ArrayList<String> emails;
	private ArrayList<String> phones;
	
	public User(){
		
	}
	
	public String getUsername(){
		return username;
	}
	
	public String getName(){
		return name;
	}
	
	public String getCompany(){
		return company;
	}
	
	public String getPosition(){
		return position;
	}
	
	public String getTwitter(){
		return twitter;
	}
	
	public ArrayList<String> getEmails(){
		return emails;
	}
	
	public ArrayList<String> getPhones(){
		return phones;
	}
	
	public void setUsername(String newUsername){
		username = newUsername;
	}
	
	public void setName(String newName){
		name = newName;
	}
	
	public void setCompany(String newCompany){
		company = newCompany;
	}
	
	public void setPosition(String newPosition){
		position = newPosition;
	}
	
	public void setTwitter(String newTwitter){
		twitter = newTwitter;
	}
	
	public void setEmails(ArrayList<String> newEmails){
		emails = newEmails;
	}
	
	public void setPhones(ArrayList<String> newPhones){
		phones = newPhones;
	}
}
