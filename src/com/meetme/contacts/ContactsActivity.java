package com.meetme.contacts;

import java.util.ArrayList;

import com.meetme.app.ProfileViewAdapter;
import com.meetme.app.R;
import com.meetme.app.User;
import com.meetme.search.SearchManager;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class ContactsActivity extends Activity {

	ContactsDataManager cdm;
	ListView contactsList;
	ContactAdapter adapter;
	ArrayList<User> contacts;
	
	ViewFlipper flipper;
	
	User currentViewedUser;
	
	ProfileViewAdapter pva;
	
	/**
	 * Defineix els camps i variables necessaris per la pantalla de contactes
	 * @param savedInstanceState
	 */
	
    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.contacts);
        
        cdm  = new ContactsDataManager(this);
        contacts = cdm.getContacts();
        
        
        contactsList = (ListView)findViewById(R.id.contacts_list);
        adapter = new ContactAdapter(this, android.R.layout.simple_list_item_1, contacts);
        contactsList.setAdapter(adapter);
        contactsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(@SuppressWarnings("rawtypes") AdapterView parent, View v, int position, long id){
                // Start your Activity according to the item just clicked.
            	TextView name = (TextView)v.findViewById(R.id.user_username);
            	changeToProfileView(name.getText().toString());
            }
        });
        
        flipper = (ViewFlipper)findViewById(R.id.contacts_flipper);
    }
    
    /**
	 * Carrega de nou els contactes
	 */
    
    public void onReload(){
    	System.out.println("reload contacts");
    	contacts = cdm.getContacts();
    	adapter.notifyDataSetChanged();
    }
    
    /**
	 * Mètode encarregat de canviar la vista a la pantalla de perfil d'un usuari
	 * @param username usuari al que volem accedir
	 */
    
    private void changeToProfileView(String username){
		SearchManager sm = new SearchManager(this);
    	User user = sm.searchForUser(username);
		currentViewedUser = user;
		
		View contactProfile;
		LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		contactProfile = vi.inflate(R.layout.profile, null);
		
		pva = new ProfileViewAdapter(this, contactProfile);
		pva.loadUserInfo(currentViewedUser);
		flipper.addView(contactProfile);
		
		flipper.showNext();
		
	}
    

    @Override
	protected void onDestroy() {
		cdm.closeDb();
    	super.onDestroy();
    }

    /**
	 * Gestiona l'acció de prémer el botó per anar enrere
	 * @param view la vista actual
	 */
    
    public void onClickButton(View view){
    	if(view.getId() == R.id.profile_back_button){
    		changeToSearchView();
    	}
    }
    
    /**
	 * Canvia a la vista de buscar contactes
	 */
    
    public void changeToSearchView(){
		flipper.showPrevious();
		flipper.removeViewAt(1);
	}
    
    public void showDeleteContactDialog(View view){
		//TODO: fer una confirmacio
	}
}