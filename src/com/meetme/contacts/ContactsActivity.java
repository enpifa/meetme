package com.meetme.contacts;

import java.util.ArrayList;

import com.meetme.app.R;
import com.meetme.search.User;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class ContactsActivity extends Activity {

	ContactsDataManager cdm;
	ListView contactsList;
	ContactAdapter adapter;
	ArrayList<User> contacts;
	
    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.contacts);
        
        cdm  = new ContactsDataManager(this);
        contacts = cdm.getContacts();
        
        
        contactsList = (ListView)findViewById(R.id.contacts_list);
        adapter = new ContactAdapter(this, android.R.layout.simple_list_item_1, contacts);
        contactsList.setAdapter(adapter);
    }
    
    public void onReload(){
    	System.out.println("reload contacts");
    	contacts = cdm.getContacts();
    	adapter.notifyDataSetChanged();
    }
}
