package com.meetme.search;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.meetme.app.ProfileViewAdapter;
import com.meetme.app.R;
import com.meetme.app.User;

public class SearchActivity extends Activity implements OnClickListener {

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	SearchManager sm;
	ArrayList<User> results;
	UsersAdapter adapter;
	EditText searchBox;
	ListView usersList;
	ProfileViewAdapter pva;
	
	ViewFlipper flipper;
	
	Dialog addContactDialog;
	
	User currentViewedUser;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sm = new SearchManager(this);
        
        this.setContentView(R.layout.search);
        
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
		currentViewedUser = user;
		
		View contactProfile;
		LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		contactProfile = vi.inflate(R.layout.profile, null);
		
		pva = new ProfileViewAdapter(this, contactProfile);
		pva.loadUserInfo(currentViewedUser);
		flipper.addView(contactProfile);
		flipper.showNext();
		
	}
	
	public void changeToSearchView(){
		flipper.showPrevious();
		flipper.removeViewAt(1);
	}
	
	public void showAddContactDialog(){
		showDialog(0);
	}
	
	public void onClickButton(View view){
		if(view.getId() == R.id.profile_back_button) changeToSearchView();
		else if (view.getId() == R.id.profile_save_contact_button) showAddContactDialog();
	}
	
	@Override
    protected Dialog onCreateDialog (int id){
		Dialog dialog = new Dialog(this, R.style.CustomDialog);
		dialog.setTitle(R.string.add_contact);
		dialog.setContentView(R.layout.add_contact);
		TextView nameLabel = (TextView)dialog.findViewById(R.id.add_contact_name);
		nameLabel.setText(currentViewedUser.getName());
		TextView companyLabel = (TextView)dialog.findViewById(R.id.add_contact_company);
		companyLabel.setText(currentViewedUser.getCompany());
		TextView positionLabel = (TextView)dialog.findViewById(R.id.add_contact_position);
		positionLabel.setText(currentViewedUser.getPosition());
		
		View closeButton = dialog.findViewById(R.id.add_contact_save_button);
		closeButton.setOnClickListener(this);
		addContactDialog = dialog;
		return dialog;
	}
	
	public void addContact(){
		//TODO: guardar el contacte
		EditText commentBox = (EditText)addContactDialog.findViewById(R.id.add_contact_comment_box);
		if (commentBox.getText().toString() != "")
		currentViewedUser.setComment(commentBox.getText().toString());
		
		EditText locationBox = (EditText)addContactDialog.findViewById(R.id.add_contact_location_box);
		if(locationBox.getText().toString() != "")
		currentViewedUser.setLocation(locationBox.getText().toString());
		
		sm.addContact(currentViewedUser);
		
		addContactDialog.dismiss();
		
		Button addContactButton = (Button)findViewById(R.id.profile_save_contact_button);
		addContactButton.setEnabled(false);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.add_contact_save_button:
			addContact();
			break;
		}
	}
}
