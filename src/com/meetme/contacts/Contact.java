package com.meetme.contacts;

public class Contact {

	private String name;
	private String company;
	private String position;
	//private String email;
	//private String phone;
	//private String web;
	//private String twitter;
	
	public Contact(){
		
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
	
	public void setName(String newName){
		name = newName;
	}
	
	public void setCompany(String newCompany){
		company = newCompany;
	}
	
	public void setPosition(String newPosition){
		position = newPosition;
	}
}
