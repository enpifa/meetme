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
import android.widget.EditText;
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
    
    public void onReload(){
    	System.out.println("reload contacts");
    	contacts = cdm.getContacts();
    	adapter.notifyDataSetChanged();
    }
    
    private void changeToProfileView(String username){
		SearchManager sm = new SearchManager(this);
    	User user = sm.searchForUser(username);
		currentViewedUser = user;
		
		for(User c : contacts){
			if(c.getUsername().equals(currentViewedUser.getUsername())){
				currentViewedUser.setContact(true);
			}
		}
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

    public void onClickButton(View view){
    	if(view.getId() == R.id.profile_back_button){
    		changeToSearchView();
    	}
    	else if(view.getId() == R.id.contacts_search_button){
    		EditText searchBox = (EditText)findViewById(R.id.contacts_search_box);
    		calculateSearch(searchBox.getText().toString());
    	}
    }
    
    public void changeToSearchView(){
		flipper.showPrevious();
		flipper.removeViewAt(1);
	}
    
    public void calculateSearch(String search){
    	String lowercaseSearch = search.toLowerCase();
    	contacts = cdm.getContacts();
    	if(search.length() > 0){
    		ArrayList<User> tmp = new ArrayList<User>();
        	for(User c : contacts){
        		
        		if(c.getName().toLowerCase().indexOf(lowercaseSearch) != -1){
        			System.out.println("checking " + c.getName());
        			tmp.add(c);
        		}
        	}
        	contacts = new ArrayList<User>(tmp);
    	}
    	adapter.notifyDataSetChanged();
    }
    
    public void showDeleteContactDialog(View view){
		//TODO: fer una confirmacio
	}
}
