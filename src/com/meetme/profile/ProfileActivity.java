package com.meetme.profile;

import com.meetme.app.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ViewFlipper;

public class ProfileActivity extends Activity {

	ViewFlipper flipper;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.profile);
        
        flipper = (ViewFlipper)findViewById(R.id.flipper);
    }
	
	public void changeToEditView(View view){
		flipper.showNext();
	}
	
	public void changeToProfileView(View view){
		flipper.showPrevious();
	}
}
