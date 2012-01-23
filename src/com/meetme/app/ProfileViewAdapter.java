package com.meetme.app;

import java.io.FileInputStream;
import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ProfileViewAdapter {

	Context mContext;
	View profileView;
	
	/**
	 * Defineix les variables de la classe
	 * @param context el context actual
	 * @param profile la vista de perfil actual
	 */
	
	public ProfileViewAdapter(Context context, View profile){
		mContext = context;
		profileView = profile;
	}
	
	/**
	 * Carrega totes les dades d'un usuari
	 * @param user usuari de qui agafem les dades
	 */
	
	public void loadUserInfo(User user){
		if (user.getName() != null) {
            TextView name = (TextView) profileView.findViewById(R.id.profile_name_label);
            name.setText(user.getName());
        }
        
        if (user.getCompany() != null) {
            TextView company = (TextView) profileView.findViewById(R.id.profile_company_label);
            company.setText(user.getCompany());
        }
        
        if (user.getPosition() != null) {
            TextView position = (TextView) profileView.findViewById(R.id.profile_position_label);
            position.setText(user.getPosition());
        }
        
        if(user.getEmails().size() > 0) {
        	ListView emailsList = (ListView) profileView.findViewById(R.id.profile_emails_list);
        	emailsList.setAdapter(new DataTableAdapter(mContext, 0, user.getEmails(), R.drawable.ic_email));
        }
        
        if(user.getPhones().size() > 0) {
        	ListView phonesList = (ListView) profileView.findViewById(R.id.profile_phones_list);
        	phonesList.setAdapter(new DataTableAdapter(mContext, 0, user.getPhones(), R.drawable.ic_phone));
        }
        
        if(user.getWebs().size() > 0) {
        	ListView webList = (ListView) profileView.findViewById(R.id.profile_webs_list);
        	webList.setAdapter(new DataTableAdapter(mContext, 0, user.getWebs(), R.drawable.ic_web));
        }
        
        if(user.getTwitter() != null){
        	TextView twitter = (TextView)profileView.findViewById(R.id.profile_twitter_label);
        	twitter.setText(user.getTwitter());
		}
		else{
			View twitterRow = (View)profileView.findViewById(R.id.profile_twitter_layout);
			twitterRow.setVisibility(View.GONE);
		}
        
        PreferencesAdapter pa = new PreferencesAdapter(mContext);
        View saveContactButton = profileView.findViewById(R.id.profile_save_contact_button);
        if(pa.getActiveUsername().equals(user.getUsername())){
        	saveContactButton.setVisibility(View.GONE);
            View backButton = profileView.findViewById(R.id.profile_back_button);
            backButton.setVisibility(View.GONE);
        }
        else {
        	saveContactButton.setEnabled(true);
        	View syncButton = profileView.findViewById(R.id.profile_sync_button);
        	syncButton.setVisibility(View.GONE);
            View editButton = profileView.findViewById(R.id.profile_edit_button);
            editButton.setVisibility(View.GONE);
        }
        
        try {
        	Bitmap image;
        	image = getImage(user);
        	ImageView profileImage = (ImageView)profileView.findViewById(R.id.profile_image);
        	if(image != null) profileImage.setImageBitmap(image);
        }
        catch(Exception e){
        	
        }
        
	}
	
	/**
	 * Retorna la imatge d'un usuari
	 * @param user usuari de qui volem la imatge
	 * @return la imatge que volem
	 * @throws IOException
	 */
	
	private Bitmap getImage(User user) throws IOException {
		FileInputStream fi = mContext.openFileInput(user.getUsername());
		fi = mContext.openFileInput(user.getUsername());
		Bitmap result = BitmapFactory.decodeStream(fi);
		fi.close();
		return result;
	}
}