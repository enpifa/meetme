package com.meetme.contacts;

import java.util.ArrayList;

import com.meetme.app.R;
import com.meetme.app.User;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ContactAdapter extends ArrayAdapter<User>{
	private ArrayList<User> contacts;
	
	/**
	 * Defineix els camps i variables necessaris per la pantalla de contactes
	 * @param context context actual
	 * @param textViewResourceId número identificador del camp de text
	 * @param contacts llista de usuaris de contacte
	 */
	
	public ContactAdapter(Context context, int textViewResourceId, ArrayList<User> contacts){
		super(context, textViewResourceId, contacts);
		
		this.contacts = contacts;
	}
	
	/**
	 * Posa tots els camps del contacte a la vista
	 * @param position número per identificar el contacte
	 * @param convertView la vista actual
	 * @param parent
	 * @return la nova vista amb les dades de l'usuari afegides
	 */
	
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
            LayoutInflater vi = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.user_item, null);
		}
		User contact = contacts.get(position);
		if(contact != null){
			TextView name = (TextView)v.findViewById(R.id.user_name);
			TextView company = (TextView)v.findViewById(R.id.user_company);
			TextView com_position = (TextView)v.findViewById(R.id.user_position);
			TextView username = (TextView)v.findViewById(R.id.user_username);
			if(username != null) username.setText(contact.getUsername());
			if(name != null && contact.getName() != null){
				name.setText(contact.getName());
			}
			else if(name != null)name.setText("");
			if(company != null && contact.getCompany() != null){
				company.setText(contact.getCompany());
			}
			else if(company != null) company.setText("");
			if(com_position != null && contact.getPosition() != null){
				com_position.setText(contact.getPosition());
			}
			else if(com_position != null) com_position.setText("");
		}
    
		return v;
	}
}