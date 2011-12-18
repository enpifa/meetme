package com.meetme.app;

import java.util.ArrayList;

public class User {
	private String username;
	private String password;
	private String name;
	private String company;
	private String position;
	private String image; //TODO posar el tipus correcte a image
	private String twitter;
	private ArrayList<String> emails;
	private ArrayList<String> phones;
	private ArrayList<String> webs;
	private String comment;
	private String location;
	private boolean isContact;
	
	public User(){
		username = null;
		password = null;
		name = null;
		company = null;
		position = null;
		twitter = null;
		emails = new ArrayList<String>();
		phones = new ArrayList<String>();
		webs = new ArrayList<String>();
		comment = null;
		location = null;
		isContact = false;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
	
	public ArrayList<String> getWebs(){
		return webs;
	}
	
	public String getComment(){
		return comment;
	}
	
	public String getLocation(){
		return location;
	}
	
	public String getImage() {
		return image;
	}
	
	public void setUsername(String newUsername){
		if(newUsername.length() > 0) username = newUsername;
	}
	
	public void setName(String newName){
		if(newName != null && newName.length() > 0) name = newName;
	}
	
	public void setCompany(String newCompany){
		if(newCompany != null && newCompany.length() > 0) company = newCompany;
	}
	
	public void setPosition(String newPosition){
		if(newPosition != null && newPosition.length() > 0) position = newPosition;
	}
	
	public void setTwitter(String newTwitter){
		if(newTwitter != null && newTwitter.length() > 0) twitter = newTwitter;
	}
	
	public void setEmails(ArrayList<String> newEmails){
		emails = newEmails;
	}
	
	public void addEmail(String newEmail){
		if(newEmail != null && newEmail.length() > 0) emails.add(newEmail);
	}
	
	public void setPhones(ArrayList<String> newPhones){
		phones = newPhones;
	}
	
	public void addPhone(String newPhone){
		if(newPhone != null && newPhone.length() > 0) phones.add(newPhone);
	}
	
	public void setWebs(ArrayList<String> newWebs){
		webs = newWebs;
	}
	
	public void addWeb(String newWeb){
		if(newWeb != null && newWeb.length() > 0) webs.add(newWeb);
	}
	
	public void setComment(String newComment){
		if(newComment != null && newComment.length() > 0) comment = newComment;
	}
	
	public void setLocation(String newLocation){
		if(newLocation != null && newLocation.length() > 0) location = newLocation;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	public boolean userIsContact(){
		return isContact;
	}
	
	public void setContact(boolean isContact){
		this.isContact = isContact;
	}
}
