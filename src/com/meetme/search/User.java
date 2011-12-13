package com.meetme.search;

import java.util.ArrayList;

public class User {
	private String username;
	private String name;
	private String company;
	private String position;
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
	private String image; //TODO posar el tipus que calgui
=======
	private String image; //TODO posar el tipus correcte a image
>>>>>>> 3015c695c5eb378fa6e6252bdb151771c283969c
=======
	private String image; //TODO posar el tipus correcte a image
>>>>>>> 3015c695c5eb378fa6e6252bdb151771c283969c
=======
	private String image; //TODO posar el tipus correcte a image
>>>>>>> 3015c695c5eb378fa6e6252bdb151771c283969c
=======
	private String image; //TODO posar el tipus correcte a image
>>>>>>> 3015c695c5eb378fa6e6252bdb151771c283969c
=======
	private String image; //TODO posar el tipus correcte a image
>>>>>>> 3015c695c5eb378fa6e6252bdb151771c283969c
=======
	private String image; //TODO posar el tipus correcte a image
>>>>>>> 3015c695c5eb378fa6e6252bdb151771c283969c
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	private String twitter;
	private ArrayList<String> emails;
	private ArrayList<String> phones;
	private ArrayList<String> webs;
	private String comment;
	private String location;
	
	public User(){
		username = null;
		name = null;
		company = null;
		position = null;
		twitter = null;
		emails = new ArrayList<String>();
		phones = new ArrayList<String>();
		webs = new ArrayList<String>();
		comment = null;
		location = null;
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
	
	public void addEmail(String newEmail){
		emails.add(newEmail);
	}
	
	public void setPhones(ArrayList<String> newPhones){
		phones = newPhones;
	}
	
	public void addPhone(String newPhone){
		phones.add(newPhone);
	}
	
	public void setWebs(ArrayList<String> newWebs){
		webs = newWebs;
	}
	
	public void addWeb(String newWeb){
		webs.add(newWeb);
	}
	
	public void setComment(String newComment){
		comment = newComment;
	}
	
	public void setLocation(String newLocation){
		location = newLocation;
	}
}
