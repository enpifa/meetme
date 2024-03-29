package com.meetme.search;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.meetme.app.R;
import com.meetme.app.User;

public class UsersAdapter  extends ArrayAdapter<User>{
	private ArrayList<User> users;
	
	/**
	 * M�tode encarregat de definir les diferents variables
	 * @param context context actual
	 * @param textViewResourceId id del text
	 * @param users llista d'usuaris
	 */
	
	public UsersAdapter(Context context, int textViewResourceId, ArrayList<User> users){
		super(context, textViewResourceId, users);
		
		this.users = users;
	}
	
	/**
	 * Estableix els diferents camps necess�ris per la vista de l'usuari
	 * @param position posici� de l'usuari
	 * @param convertView la vista actual
	 * @param parent
	 * @return la vista amb els camps establerts
	 */
	
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
            LayoutInflater vi = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.user_item, null);
		}
		User user = users.get(position);
		if(user != null){
			TextView name = (TextView)v.findViewById(R.id.user_name);
			TextView company = (TextView)v.findViewById(R.id.user_company);
			TextView com_position = (TextView)v.findViewById(R.id.user_position);
			TextView username = (TextView)v.findViewById(R.id.user_username);
			if(name != null){
				name.setText(user.getName());
			}
			if(company != null){
				company.setText(user.getCompany());
			}
			if(com_position != null){
				com_position.setText(user.getPosition());
			}
			if(username != null){
				username.setText(user.getUsername());
			}
		}
		
		return v;
	}
}