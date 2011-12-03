package com.meetme.contacts;

import java.util.ArrayList;

import com.meetme.app.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class ContactsActivity extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.contacts);
        
        //TODO: obtenir els contactes de la DB
        Contact prova1 = new Contact();
        prova1.setName("John");
        prova1.setCompany("Apple, Inc.");
        prova1.setPosition("Intern");
        Contact prova2 = new Contact();
        prova2.setName("Annie");
        prova2.setCompany("Awesome Co.");
        prova2.setPosition("CEO");
        ArrayList<Contact> contacts = new ArrayList<Contact>();
        contacts.add(prova1);
        contacts.add(prova2);
        
        ListView contactsList = (ListView)findViewById(R.id.contacts_list);
        contactsList.setAdapter(new ContactAdapter(this, android.R.layout.simple_list_item_1, contacts));
    }
}
