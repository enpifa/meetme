package com.meetme.search;

import java.util.ArrayList;

import com.meetme.app.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class SearchActivity extends Activity {

	SearchManager sm;
	ArrayList<User> results;
	UsersAdapter adapter;
	EditText searchBox;
	ListView usersList;
	
	ViewFlipper flipper;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sm = new SearchManager();
        
        setContentView(R.layout.search);
        
        searchBox = (EditText)findViewById(R.id.web_search_box);
        
        results = new ArrayList<User>();
        
        usersList = (ListView)findViewById(R.id.users_list);
        adapter = new UsersAdapter(this, android.R.layout.simple_list_item_1, results);
        
        usersList.setAdapter(adapter);
        
        usersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(@SuppressWarnings("rawtypes") AdapterView parent, View v, int position, long id){
                // Start your Activity according to the item just clicked.
            	TextView name = (TextView)v.findViewById(R.id.user_username);
            	changeToProfileView(name.getText().toString());
            }
        });
        
        flipper = (ViewFlipper)findViewById(R.id.users_flipper);
    }
	
	public void searchForUsers(View view){
		String search = searchBox.getText().toString();
		if(search.length() > 0){
			results = sm.searchForUsers(search);
			adapter = new UsersAdapter(this, android.R.layout.simple_list_item_1, results);
			usersList.setAdapter(adapter);
		}
	}
	
	private void changeToProfileView(String username){
		User user = sm.searchForUser(username);
		TextView name = (TextView)findViewById(R.id.user_name_label);
		name.setText(user.getName());
		TextView company = (TextView)findViewById(R.id.user_company_label);
		company.setText(user.getCompany());
		TextView position = (TextView)findViewById(R.id.user_position_label);
		position.setText(user.getPosition());
		flipper.showNext();
	}
	
}
