package com.meetme.contacts;

import java.util.ArrayList;

import com.meetme.app.R;
import com.meetme.app.User;
import com.meetme.search.SearchManager;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class ContactsActivity extends Activity {

	ContactsDataManager cdm;
	ListView contactsList;
	ContactAdapter adapter;
	ArrayList<User> contacts;
	
	ViewFlipper flipper;
	
	User currentViewedUser;
	
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
		TextView name = (TextView)findViewById(R.id.contact_name_label);
		name.setText(user.getName());
		TextView company = (TextView)findViewById(R.id.contact_company_label);
		company.setText(user.getCompany());
		TextView position = (TextView)findViewById(R.id.contact_position_label);
		position.setText(user.getPosition());
		TextView twitter = (TextView)findViewById(R.id.contact_twitter_label);
		if(user.getTwitter() != null){
			twitter.setText(user.getTwitter());
		}
		else{
			TableRow twitterRow = (TableRow)findViewById(R.id.contact_twitter_row);
			twitterRow.setVisibility(View.GONE);
		}
		flipper.showNext();
		
	}
    
    @Override
	protected void onDestroy() {
		super.onDestroy();
		cdm.closeDb();
    }

	public void changeToSearchView(View view){
		flipper.showPrevious();
	}
    
    public void showDeleteContactDialog(View view){
		//TODO: fer una confirmacio
	}
}
