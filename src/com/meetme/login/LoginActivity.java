package com.meetme.login;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ViewFlipper;

import com.meetme.app.R;

public class LoginActivity extends Activity {

	ViewFlipper flipper;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //carrega el xml del layout
        setContentView(R.layout.login);
        
        //eina que permet intercanviar entre login i register
        flipper = (ViewFlipper)findViewById(R.id.login_flip);
	}
	
	/**
	 * Funci— per quan premen el bot— de Register, que carrega el layout corresponent.
	 * @param view
	 */
	public void changeToRegister(View view) {
		//aixo carrega el segon layout dins el flipper
		flipper.showNext();
	}
	
	/**
	 * Funci— per quan estan a register i volen tornar al login
	 * @param view
	 */
	public void changeToLogin(View view) {
		//aixo torna al primer layout dins el flipper
		flipper.showPrevious();
	}
}
