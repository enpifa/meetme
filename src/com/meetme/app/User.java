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
	
	/**
	 * Defineix les variables de la classe
	 */
	
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
	}
	
	/**
	 * Retorna la contrasenya de l'usuari
	 * @return contrasenya
	 */
	
	public String getPassword() {
		return password;
	}

	/**
	 * Estableix una nova contrasenya per l'usuari
	 * @param password la contrasenya
	 */
	
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Retorna l'username de l'usuari
	 * @return username
	 */
	
	public String getUsername(){
		return username;
	}
	
	/**
	 * Retorna el nom de l'usuari
	 * @return name
	 */
	
	public String getName(){
		return name;
	}
	
	/**
	 * Retorna la companyia de l'usuari
	 * @return companyia
	 */
	
	public String getCompany(){
		return company;
	}
	
	/**
	 * Retorna la posició de l'usuari
	 * @return position
	 */
	
	public String getPosition(){
		return position;
	}
	
	/**
	 * Retorna el twitter de l'usuari
	 * @return twitter
	 */
	
	public String getTwitter(){
		return twitter;
	}
	
	/**
	 * Retorna els emails de l'usuari
	 * @return llista d'emails
	 */
	
	public ArrayList<String> getEmails(){
		return emails;
	}
	
	/**
	 * Retorna els números de telèfon de l'usuari
	 * @return llista de telèfons
	 */
	
	public ArrayList<String> getPhones(){
		return phones;
	}
	
	/**
	 * Retorna les pàgines web de l'usuari
	 * @return llista de webs
	 */
	
	public ArrayList<String> getWebs(){
		return webs;
	}
	
	/**
	 * Retorna el comentari sobre un contacte
	 * @return comment
	 */
	
	public String getComment(){
		return comment;
	}
	
	/**
	 * Retorna la localització quan es va afegir un contacte
	 * @return location
	 */
	
	public String getLocation(){
		return location;
	}
	
	/**
	 * Retorna la imatge d'un usuari
	 * @return image
	 */
	
	public String getImage() {
		return image;
	}
	
	/**
	 * Estableix el nom d'usuari per l'usuari
	 * @param newUsername nom d'usuari
	 */
	
	public void setUsername(String newUsername){
		if(newUsername.length() > 0) username = newUsername;
	}
	
	/**
	 * Estableix un nou nom per l'usuari
	 * @param newName nou nom de l'usuari
	 */
	
	public void setName(String newName){
		if(newName != null && newName.length() > 0) name = newName;
	}
	
	/**
	 * Estableix una companyia per l'usuari
	 * @param newCompany nom de la companyia
	 */
	
	public void setCompany(String newCompany){
		if(newCompany != null && newCompany.length() > 0) company = newCompany;
	}
	
	/**
	 * Estableix una nova posició per l'usuari
	 * @param newPosition la nova posició d'empleat per l'usuari
	 */
	
	public void setPosition(String newPosition){
		if(newPosition != null && newPosition.length() > 0) position = newPosition;
	}
	
	/**
	 * Estableix un nou compte de twitter per l'usuari
	 * @param newTwitter el nou twitter
	 */
	
	public void setTwitter(String newTwitter){
		if(newTwitter != null && newTwitter.length() > 0) twitter = newTwitter;
	}
	
	/**
	 * Estableix un nou conjunt d'emails per l'usuari
	 * @param newEmails llista dels emails
	 */
	
	public void setEmails(ArrayList<String> newEmails){
		emails = newEmails;
	}
	
	/**
	 * Afegeix un nou email a la llista d'emails d'un usuari
	 * @param newEamil nou email a afegir
	 */
	
	public void addEmail(String newEmail){
		if(newEmail != null && newEmail.length() > 0) emails.add(newEmail);
	}
	
	/**
	 * Estableix una nova llista de números de telèfon per l'usuari
	 * @param newPhones llista de telèfons
	 */
	
	public void setPhones(ArrayList<String> newPhones){
		phones = newPhones;
	}
	
	/**
	 * Afegeix un nou número de telèfon a un usuari
	 * @param newPhone número de telèfon a afegir a la llista
	 */
	
	public void addPhone(String newPhone){
		if(newPhone != null && newPhone.length() > 0) phones.add(newPhone);
	}
	
	/**
	 * Estableix una nova llista de webs per l'usuari
	 * @param newWebs nova llista de webs
	 */
	
	public void setWebs(ArrayList<String> newWebs){
		webs = newWebs;
	}
	
	/**
	 * Afegeix una nova web a la llista de webs d'un usuari
	 * @param newWeb nova web a afegir
	 */
	
	public void addWeb(String newWeb){
		if(newWeb != null && newWeb.length() > 0) webs.add(newWeb);
	}
	
	/**
	 * Estableix un nou comentari per l'usuari
	 * @param newComment nou comentari
	 */
	
	public void setComment(String newComment){
		if(newComment != null && newComment.length() > 0) comment = newComment;
	}
	
	/**
	 * Estableix una nova localització per l'usuari
	 * @param newLocation nova localització
	 */
	
	public void setLocation(String newLocation){
		if(newLocation != null && newLocation.length() > 0) location = newLocation;
	}

	/**
	 * Estableix una imatge per l'usuari
	 * @param imatge la imatge a establir
	 */
	
	public void setImage(String image) {
		this.image = image;
	}
}