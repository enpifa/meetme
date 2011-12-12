package com.meetme.login;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.meetme.app.MeetMeDbAdapter;
import com.meetme.app.R;

public class LoginActivity extends Activity {

	ViewFlipper flipper;
	Button login;
	Button register;
	Button create;
	Button btLogin;
	TextView errorText;
	EditText et1;
	EditText et2;
	EditText et3;
	LoginManager lm;
	
	private MeetMeDbAdapter mDbHelper;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mDbHelper = new MeetMeDbAdapter(this);
        mDbHelper.open();
        
        lm = new LoginManager(mDbHelper);
        
        
        
        /**
         * PROVA 
         */
        
        
       // if (lm.registerUser("prova", "pass") != -1) System.out.print("inserci—");
      //  Boolean a = true;
      //  if (a) System.out.print("a");
      //  if (lm.existsUser("prova")) System.out.print("Correcto!");
        
        
        /**
         * FI PROVA
         */
        //load login.xml from layout
        setContentView(R.layout.login);
        
        //tool that let us exchange between login and register
        flipper = (ViewFlipper)findViewById(R.id.login_flip);
        login = (Button)findViewById(R.id.login_button);
        register = (Button)findViewById(R.id.register_button);
        create = (Button)findViewById(R.id.create_button);
        btLogin = (Button)findViewById(R.id.back_to_login_button);
        errorText = (TextView)findViewById(R.id.login_error_message);
        et1 = (EditText)findViewById(R.id.username_box);
        et2 = (EditText)findViewById(R.id.password_box);
		
        login.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String user = et1.getEditableText().toString();
				String psw = et2.getEditableText().toString();
				if (lm.correctUserAndPassword(user, psw)) errorText.setText("El login s'ha fet correctament");
				else
					errorText.setText("ERROR: username or password are not correct " + user + " " + psw);
				//Intent i = new Intent(LoginActivity.this, ProfileActivity.class);
				//startActivity(i);
				//carregar la pagina de perfil amb el nom d'usuari
			}
		});
        
        register.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				changeToRegister(v);
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
						errorText.setText("You have created a new user successfully"); 
						changeToLogin(v);
					}
				}
				else errorText.setText("ERROR: Passwords do not match");
			}
        });
        
        btLogin.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				changeToLogin(v);
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
}