package com.meetme.login;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.meetme.app.R;
import com.meetme.app.User;

public class LoginAdapter extends ArrayAdapter<User> {

	private ArrayList<User> users;
	
	public LoginAdapter(Context context, int textViewResourceId,
			ArrayList<User> objects) {
		super(context, textViewResourceId, objects);
		users = objects;
	}
	
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
            LayoutInflater vi = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.login_item, null);
		}
		User user = users.get(position);
		if(user != null){
			TextView name = (TextView)v.findViewById(R.id.login_name);
			TextView username = (TextView)v.findViewById(R.id.login_username);
			if(name != null){
				name.setText(user.getName());
			}
			if(username != null){
				username.setText(user.getUsername());
			}
		}
		return v;
	}

}
