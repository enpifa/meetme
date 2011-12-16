package com.meetme.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.meetme.app.MeetMeActivity;
import com.meetme.app.R;

public class LoginActivity extends Activity {

	ViewFlipper flipper;
	Button login;
	Button create;
	TextView errorText;
	EditText et1;
	EditText et2;
	EditText et3;
	LoginManager lm;
		
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
                
        lm = new LoginManager(this);
        
        //load login.xml from layout
        setContentView(R.layout.login);
        
        //tool that let us exchange between login and register
        flipper = (ViewFlipper)findViewById(R.id.login_flip);
        login = (Button) findViewById(R.id.login_button);
        create = (Button)findViewById(R.id.create_button);
        errorText = (TextView)findViewById(R.id.login_error_message);
        et1 = (EditText)findViewById(R.id.username_box);
        et2 = (EditText)findViewById(R.id.password_box);
		
        login.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String username = et1.getEditableText().toString();
				String password = et2.getEditableText().toString();
				if (lm.correctUserAndPassword(username, password)) {
					//posar el username de l'usuari actual a les shared
					lm.setActiveUser(username);
					//Iniciar l'aplicaci—
					changeToApp();
				}
				else
					errorText.setText("ERROR: username or password are not correct " + username + " " + password);
				//Intent i = new Intent(LoginActivity.this, ProfileActivity.class);
				//startActivity(i);
				//carregar la pagina de perfil amb el nom d'usuari
			}
		});
        
        create.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				String username = et1.getEditableText().toString();
				String password = et2.getEditableText().toString();
				et3 = (EditText)findViewById(R.id.password_confirm_box);
				String cPass = et3.getEditableText().toString();
				if (password.equals(cPass)) { //passwords iguals
					if (lm.existsUser(username)) errorText.setText("ERROR CREATING NEW USER: The user already exists");
					else {
						lm.registerUser(username, password);
						lm.setActiveUser(username);
						//TODO iniciar activity meetme
						changeToApp();
					}
				}
				else errorText.setText("ERROR: Passwords do not match");
			}
        });
        
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
	
	private void changeToApp(){
		Intent mainIntent = new Intent(this, MeetMeActivity.class);
		startActivity(mainIntent);
	}
}