package com.meetme.profile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.meetme.app.ProfileViewAdapter;
import com.meetme.app.R;
import com.meetme.app.User;

public class ProfileActivity extends Activity {

	ViewFlipper flipper;
	
	private Uri mImageCaptureUri;
	private ImageView mImageView;
	private EditText mNameBox;
	private EditText mCompanyBox;
	private EditText mPositionBox;
	private EditText mMailBox;
	private EditText mPhoneBox;
	private EditText mWebBox;
	private EditText mTwitterBox;
	
	private ProfileDataManager pdm;
	private User mUser;
	private ProfileViewAdapter pva;
	
	private static final int PICK_FROM_CAMERA = 1;
	private static final int CROP_FROM_CAMERA = 2;
	private static final int PICK_FROM_FILE = 3;
	
	/**
	 * Funció que carrega totes les dades de l'usuari i les mostra per pantalla
	 * @param savedInstanceState
	 */
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pdm = new ProfileDataManager(this);

        setContentView(R.layout.flipper);
        
        flipper = (ViewFlipper)findViewById(R.id.flipper);
        
        View profile;
        LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        profile = vi.inflate(R.layout.profile, null);
        pva = new ProfileViewAdapter(this, profile);
        
        if (mUser == null) mUser = pdm.getProfile(pdm.getActiveUsername());
        
        pva.loadUserInfo(mUser);
        flipper.addView(profile);
        
        View editProfile;
        editProfile = vi.inflate(R.layout.profile_edit, null);
        flipper.addView(editProfile);
        
        final String [] items			= new String [] {"Take from camera", "Select from gallery"};				
		ArrayAdapter<String> adapter	= new ArrayAdapter<String> (this, android.R.layout.select_dialog_item,items);
		AlertDialog.Builder builder		= new AlertDialog.Builder(this);
		
		builder.setTitle("Select Image");
		builder.setAdapter( adapter, new DialogInterface.OnClickListener() {
			
			/**
			 * Funció per carregar una imatge al perfil d'usuari
			 * @param dialog llista d'opcions de selecció d'imatge
			 * @param item indica la opció seleccionada per elegir una imatge
			 */
			
			public void onClick( DialogInterface dialog, int item ) { //pick from camera
				if (item == 0) {
					Intent intent 	 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					
					mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
									   "tmp_avatar_" + String.valueOf(System.currentTimeMillis()) + ".jpg"));

					intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);

					try {
						intent.putExtra("return-data", true);
						
						startActivityForResult(intent, PICK_FROM_CAMERA);
					} catch (ActivityNotFoundException e) {
						e.printStackTrace();
					}
				} else { //pick from file
					Intent intent = new Intent();
					
	                intent.setType("image/*");
	                intent.setAction(Intent.ACTION_GET_CONTENT);
	                
	                startActivityForResult(Intent.createChooser(intent, "Complete action using"), PICK_FROM_FILE);
				}
			}
		} );
		
		final AlertDialog dialog = builder.create();
		
		Button button 	= (Button) findViewById(R.id.profile_change_image_button);
		mImageView		= (ImageView) findViewById(R.id.profile_image_edit);
		
		button.setOnClickListener(new View.OnClickListener() {	
			
			/**
			 * Funció per obrir la llista d'opcions per seleccionar una imatge
			 * @param v la vista actual
			 */
			
			public void onClick(View v) {
				dialog.show();
			}
		});
        
        
    }
	
	/**
	 * Funció per canviar de la vista de perfil a la vista d'edició de perfil. Es carreguen totes les 
	 * dades existents
	 */

	private void changeToEditView(){
		mNameBox = (EditText) findViewById(R.id.profile_name_box);
		mCompanyBox = (EditText) findViewById(R.id.profile_company_box);
		mPositionBox = (EditText) findViewById(R.id.profile_position_box);
		mMailBox = (EditText) findViewById(R.id.profile_email_box);
		mPhoneBox = (EditText) findViewById(R.id.profile_phone_box);
		mWebBox = (EditText) findViewById(R.id.profile_web_box);
		mTwitterBox = (EditText) findViewById(R.id.profile_twitter_box);
		
		if (hasText(mUser.getName())) mNameBox.setText(mUser.getName());
		if (hasText(mUser.getCompany())) mCompanyBox.setText(mUser.getCompany()); 
		if (hasText(mUser.getPosition())) mPositionBox.setText(mUser.getPosition());
		if (hasText(mUser.getTwitter())) mTwitterBox.setText(mUser.getTwitter());
		if (mUser.getEmails().size() > 0 && hasText(mUser.getEmails().get(0))) mMailBox.setText(mUser.getEmails().get(0)); 
		if (mUser.getPhones().size() > 0 && hasText(mUser.getPhones().get(0))) mPhoneBox.setText(mUser.getPhones().get(0)); 
		if (mUser.getWebs().size() > 0 && hasText(mUser.getWebs().get(0))) mWebBox.setText(mUser.getWebs().get(0)); 
		
		ImageView editProfileImage = (ImageView)findViewById(R.id.profile_image_edit);
		ImageView profileImage = (ImageView)findViewById(R.id.profile_image);
		editProfileImage.setImageDrawable(profileImage.getDrawable());
		flipper.showNext();
	}
	
	/**
	 * Funció que guarda els canvis de la vista d'edició de perfil, i torna a la vista de perfil
	 */
	
	private void saveChangesAndChangeToProfileView(){
		System.out.print("saveChangesAndChangeToProfileView");

		mNameBox = (EditText) findViewById(R.id.profile_name_box);
		mCompanyBox = (EditText) findViewById(R.id.profile_company_box);
		mPositionBox = (EditText) findViewById(R.id.profile_position_box);
		mMailBox = (EditText) findViewById(R.id.profile_email_box);
		mPhoneBox = (EditText) findViewById(R.id.profile_phone_box);
		mWebBox = (EditText) findViewById(R.id.profile_web_box);
		mTwitterBox = (EditText) findViewById(R.id.profile_twitter_box);
		
		
		mUser.setName(mNameBox.getText().toString());
		mUser.setCompany(mCompanyBox.getText().toString());
		mUser.setPosition(mPositionBox.getText().toString());
		mUser.setTwitter(mTwitterBox.getText().toString());
		if(!mUser.getEmails().contains(mMailBox.getText().toString()))
			mUser.addEmail(mMailBox.getText().toString());
		if(!mUser.getPhones().contains(mPhoneBox.getText().toString()))
			mUser.addPhone(mPhoneBox.getText().toString());
		if(!mUser.getWebs().contains(mWebBox.getText().toString()))
			mUser.addWeb(mWebBox.getText().toString());
		
		pdm.updateProfile(mUser);
		pva.loadUserInfo(mUser);

		//change to profile view
		flipper.showPrevious();
	}
	

	@Override
	protected void onDestroy() {
		pdm.closeDb();
		super.onDestroy();
	}

	/**
	 * Controla les accions que s'han de dur a terme quan es fa click a un botó
	 * @param view la vista actual
	 */
	
	public void onClickButton(View view){
		if(view.getId() == R.id.profile_sync_button){
			syncDataWithWeb();
		}
		else if(view.getId() == R.id.profile_save_changes){
			saveChangesAndChangeToProfileView();
		}
		else if(view.getId() == R.id.profile_edit_button){
			changeToEditView();
		}
	}
	
	/**
	 * Sincronitza les dades amb el web
	 */
	
	private void syncDataWithWeb(){
		pdm.syncData();
	}
	
	/**
	 * Tracta la imatge per adaptar-la a la resolució establert al perfil d'usuari
	 * @param requestCode número que indica la opció elegida per canviar la imatge de perfil
	 * @param resultCode 
	 * @param data la imatge
	 */
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (resultCode != RESULT_OK) return;
	   
	    switch (requestCode) {
		    case PICK_FROM_CAMERA:
		    	doCrop();
		    	
		    	break;
		    	
		    case PICK_FROM_FILE: 
		    	mImageCaptureUri = data.getData();
		    	
		    	doCrop();
	    
		    	break;	    	
	    
		    case CROP_FROM_CAMERA:	    	
		        Bundle extras = data.getExtras();
	
		        if (extras != null) {	        	
		            Bitmap photo = extras.getParcelable("data");
		            
		            mImageView.setImageBitmap(photo);
		            try {
						saveImage(photo);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						System.out.println("error saving photo");
						e.printStackTrace();
					}
		        }
	
		        File f = new File(mImageCaptureUri.getPath());            
		        
		        if (f.exists()) f.delete();
	
		        break;

	    }
	}
	
	/**
     * Guarda una imatge
     * @param photo la imatge que volem guardar
     */
	
	private void saveImage(Bitmap photo) throws IOException{
		String path = mUser.getUsername();
		FileOutputStream fo = openFileOutput(path, MODE_PRIVATE);
		photo.compress(Bitmap.CompressFormat.JPEG, 40, fo);
		fo.close();
	}
	
	/**
	 * Mira si un string té text
	 * @param s string que consultem
	 * @return true si té text, false d'altra banda
	 */
	
	private boolean hasText(String s) {
		return !(s == null || s.equals(""));
	}
	
	/**
	 * Estableix la configuració per poder carregar una imatge
	 */
	
    private void doCrop() {
		final ArrayList<CropOption> cropOptions = new ArrayList<CropOption>();
    	
    	Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setType("image/*");
        
        List<ResolveInfo> list = getPackageManager().queryIntentActivities( intent, 0 );
        
        int size = list.size();
        
        if (size == 0) {	        
        	Toast.makeText(this, "Can not find image crop app", Toast.LENGTH_SHORT).show();
        	
            return;
        } else {
        	intent.setData(mImageCaptureUri);
            
            intent.putExtra("outputX", 200);
            intent.putExtra("outputY", 200);
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("scale", true);
            intent.putExtra("return-data", true);
            
        	if (size == 1) {
        		Intent i 		= new Intent(intent);
	        	ResolveInfo res	= list.get(0);
	        	
	        	i.setComponent( new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
	        	
	        	startActivityForResult(i, CROP_FROM_CAMERA);
        	} else {
		        for (ResolveInfo res : list) {
		        	final CropOption co = new CropOption();
		        	
		        	co.title 	= getPackageManager().getApplicationLabel(res.activityInfo.applicationInfo);
		        	co.icon		= getPackageManager().getApplicationIcon(res.activityInfo.applicationInfo);
		        	co.appIntent= new Intent(intent);
		        	
		        	co.appIntent.setComponent( new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
		        	
		            cropOptions.add(co);
		        }
	        
		        CropOptionAdapter adapter = new CropOptionAdapter(getApplicationContext(), cropOptions);
		        
		        AlertDialog.Builder builder = new AlertDialog.Builder(this);
		        builder.setTitle("Choose Crop App");
		        builder.setAdapter( adapter, new DialogInterface.OnClickListener() {
		            public void onClick( DialogInterface dialog, int item ) {
		                startActivityForResult( cropOptions.get(item).appIntent, CROP_FROM_CAMERA);
		            }
		        });
	        
		        builder.setOnCancelListener( new DialogInterface.OnCancelListener() {
		            
		            public void onCancel( DialogInterface dialog ) {
		               
		                if (mImageCaptureUri != null ) {
		                    getContentResolver().delete(mImageCaptureUri, null, null );
		                    mImageCaptureUri = null;
		                }
		            }
		        } );
		        
		        AlertDialog alert = builder.create();
		        
		        alert.show();
        	}
        }
	}
}