package com.meetme.login;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.meetme.app.R;
import com.meetme.app.User;

public class LoginAdapter extends ArrayAdapter<User> {

	private ArrayList<User> users;
	private Context mContext;
	
	public LoginAdapter(Context context, int textViewResourceId,
			ArrayList<User> objects) {
		super(context, textViewResourceId, objects);
		users = objects;
		mContext = context;
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
			try {
	        	Bitmap image;
	        	image = getImage(user);
	        	ImageView profileImage = (ImageView)v.findViewById(R.id.login_image);
	        	if(image != null) {
	        		Bitmap resizedbitmap=Bitmap.createScaledBitmap(image, 100, 100, true);
	        		profileImage.setImageBitmap(resizedbitmap);
	        	}
	        	
	        }
	        catch(Exception e){
	        	e.printStackTrace();
	        }
		}
		return v;
	}

	private Bitmap getImage(User user) throws IOException {
		FileInputStream fi = mContext.openFileInput(user.getUsername());
		fi = mContext.openFileInput(user.getUsername());
		Bitmap result = BitmapFactory.decodeStream(fi);
		fi.close();
		return result;
	}
}
