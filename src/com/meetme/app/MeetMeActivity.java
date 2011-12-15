package com.meetme.app;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;

import com.meetme.contacts.ContactsActivity;
import com.meetme.profile.ProfileActivity;
import com.meetme.search.SearchActivity;
import com.meetme.settings.SettingsActivity;

public class MeetMeActivity extends TabActivity {
	
	TabHost tabHost;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);

    	System.out.println("OK");
        setContentView(R.layout.main);
        
        Resources res = getResources(); // Resource object to get Drawables
        tabHost = getTabHost();  // The activity TabHost
        TabHost.TabSpec spec;  // Resusable TabSpec for each tab
        Intent intent;  // Reusable Intent for each tab

        // Create an Intent to launch an Activity for the tab (to be reused)
        intent = new Intent().setClass(this, ProfileActivity.class);

        // Initialize a TabSpec for each tab and add it to the TabHost
        spec = tabHost.newTabSpec("profile").setIndicator("Profile",
                          res.getDrawable(R.drawable.ic_tab_profile))
                      .setContent(intent);
        tabHost.addTab(spec);

        // Do the same for the other tabs
        intent = new Intent().setClass(this, ContactsActivity.class);
        spec = tabHost.newTabSpec("contacts").setIndicator("Contacts",
                          res.getDrawable(R.drawable.ic_tab_contacts))
                      .setContent(intent);
        tabHost.addTab(spec);

        intent = new Intent().setClass(this, SearchActivity.class);
        spec = tabHost.newTabSpec("search").setIndicator("Search",
                          res.getDrawable(R.drawable.ic_tab_search))
                      .setContent(intent);
        tabHost.addTab(spec);
        
        intent = new Intent().setClass(this, SettingsActivity.class);
        spec = tabHost.newTabSpec("settings").setIndicator("Settings",
                          res.getDrawable(R.drawable.ic_tab_settings))
                      .setContent(intent);
        tabHost.addTab(spec);

        tabHost.setCurrentTab(0);
        
        tabHost.setOnTabChangedListener(new TabListener());
    }
    
    public class TabListener implements OnTabChangeListener {

		@Override
		public void onTabChanged(String tabId) {
			// TODO Auto-generated method stub
			if(tabId == "profile"){
				
			}
			else if (tabId == "contacts"){
				ContactsActivity activity = (ContactsActivity)getLocalActivityManager().getActivity(tabId);
				activity.onReload();
			}
			else if (tabId == "search"){
				
			}
			else if (tabId == "settings"){
				
			}
		}
    	
    }
}