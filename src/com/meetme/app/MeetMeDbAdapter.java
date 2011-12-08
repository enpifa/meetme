package com.meetme.app;

import org.apache.http.entity.ByteArrayEntity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class MeetMeDbAdapter {
	public static final String KEY_USERNAME = "username";
	public static final String KEY_PASSWORD = "password";
	public static final String KEY_NAME = "name";
	public static final String KEY_COMPANY = "company";
	private static final String KEY_POSITION = "position";
	public static final String KEY_IMAGE = "image";
	public static final String KEY_TWITTERNAME = "twittername";
	public static final String KEY_TWITTERPASS = "twitterpass";


	public static final String KEY_PHONE = "phonenumber";
	public static final String KEY_WEB = "webpage";
	public static final String KEY_MAIL = "mail";
	public static final String KEY_USERID = "userId";
	


	public static final String KEY_ROWID = "_id";
	
	private static final String TAG = "MeetMeDbAdapter";
	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDb;
	
	private static final String DATABASE_CREATE_USERS = 
		"create table users (_id integer primary key autoincrement, "
		+ "username text not null, password text not null, name text, company text, position text, image blob, twittername text, twitterpass text";
	
	private static final String DATABASE_CREATE_PHONES =
		"create table phones (_id integer primary key autoincrement, "
		+ "userId integer not null, phonenumber text not null";
	
	private static String DATABASE_CREATE_WEBS = 
		"create table webs (_id integer primary key autoincrement, "
		+ "userId integer not null, webpage text not null";
	
	private static final String DATABASE_CREATE_MAILS = 
		"create table mails (_id integer primary key autoincrement, "
		+ "userId integer not null, mail text not null";
	
	private static final String DATABASE_NAME = "data";
    private static final String DATABASE_TABLE_USERS = "users";
    private static final String DATABASE_TABLE_PHONES = "phones";
	private static final String DATABASE_TABLE_WEBS = "webs";
	private static final String DATABASE_TABLE_MAILS = "mails";

    private static final int DATABASE_VERSION = 1;
	
    private final Context mCtx;
    
    private static class DatabaseHelper extends SQLiteOpenHelper {
    	DatabaseHelper(Context context) {
    		super(context, DATABASE_NAME, null, DATABASE_VERSION);
    	}
    	
    	@Override
    	public void onCreate(SQLiteDatabase db) {
    		db.execSQL(DATABASE_CREATE_USERS);
    		db.execSQL(DATABASE_CREATE_PHONES);
    		db.execSQL(DATABASE_CREATE_WEBS);
    		db.execSQL(DATABASE_CREATE_MAILS);
    	}
    	

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS users");
            onCreate(db);			
		}
    	
    }
    
    public MeetMeDbAdapter(Context ctx) {
    	mCtx = ctx;
    }
    
    public MeetMeDbAdapter open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }
 
    public void close() {
        mDbHelper.close();
    }
    
    
    
    
    /**
     * Es crea una fila a la taula phones per a representar que
     * l'usuari username té el telèfone phoneNumber.
     * @param username l'identificador de l'usuari
     * @param phoneNumber un dels números de telèfon de l'usuari
     * @return rowId o -1 si ha fallat
     */
    public long createPhone(String userId, String phoneNumber) {
    	ContentValues initialValues = new ContentValues();
    	initialValues.put(KEY_USERID, userId);
    	initialValues.put(KEY_PHONE, phoneNumber);
    	return mDb.insert(DATABASE_TABLE_PHONES, null, initialValues);
    	
    }
    
    
    /**
     * Es crea una fila a la taula webs per a representar que
     * l'usuari username té la web webPage.
     * @param username l'identificador de l'usuari
     * @param webPage una de les webs de l'usuari
     * @return rowId o -1 si ha fallat
     */
    public long createWeb(String userId, String webPage) {
    	ContentValues initialValues = new ContentValues();
    	initialValues.put(KEY_USERID, userId);
    	initialValues.put(KEY_WEB, webPage);
    	return mDb.insert(DATABASE_TABLE_WEBS, null, initialValues);
    	
    }
    
    /**
     * Es crea una fila a la taula mails per a representar que
     * l'usuaru username té el mail mail.
     * @param username l'identificador de l'usuari
     * @param mail un dels mails de l'usuari
     * @return rowId o -1 si ha fallat
     */
    public long createMail(String userId, String mail) {
    	ContentValues initialValues = new ContentValues();
    	initialValues.put(KEY_USERID, userId);
    	initialValues.put(KEY_MAIL, mail);
    	return mDb.insert(DATABASE_TABLE_MAILS, null, initialValues);  	

    }
    
    
    
    /**
     * MÈTODES DE FETCH
     */
    
    /**
     * Retorna un cursor sobre els usuaris amb la seva informació (excepte passwords).
     * 
     * @return
     */
    public Cursor fetchAllUsers() {
    	return mDb.query(DATABASE_TABLE_USERS, new String[] {KEY_ROWID, KEY_USERNAME,
    			KEY_NAME, KEY_COMPANY, KEY_POSITION, KEY_IMAGE, KEY_TWITTERNAME},
    			null, null, null, null, KEY_USERNAME);
    }
    
    public Cursor fetchUser(String username) throws SQLException {
    	Cursor mCursor = mDb.query(true, DATABASE_TABLE_USERS, new String[] {KEY_ROWID,
    			KEY_USERNAME, KEY_NAME, KEY_COMPANY, KEY_POSITION, KEY_IMAGE,
    			KEY_TWITTERNAME}, KEY_USERNAME + "=" + username, null, null, null,
    			null, null);
    	if (mCursor != null) mCursor.moveToFirst();
    	return mCursor;
    }
    
    
    /**
     * Retorna un cursor sobre els telèfons d'un usuari.
     * @param userId id de la fila de l'usuari a la taula users
     * @return cursor sobre els telèfons de l'usuari amb id userId
     * @throws SQLException
     */
    public Cursor fetchPhonesOf(long userId) throws SQLException {
    	return mDb.query(true, DATABASE_TABLE_PHONES, new String[] {KEY_ROWID,
    			KEY_PHONE}, KEY_USERID + "=" + userId, null, null, null, null, null);    	
    }
    
    /**
     * Retorna un cursor sobre els mails d'un usuari.
     * @param userId id de la fila de l'usuari a la taula users
     * @return cursor sobre els mails de l'usuari amb id userId
     * @throws SQLException
     */
    public Cursor fetchMailsOf(long userId) throws SQLException {
    	return mDb.query(true, DATABASE_TABLE_MAILS, new String[] {KEY_ROWID,
    			KEY_MAIL}, KEY_USERID + "=" + userId, null, null, null, null, null);    	
    }
    
    /**
     * Retorna un cursor sobre les webs d'un usuari.
     * @param userId id de la fila de l'usuari a la taula users
     * @return cursor sobre les webs de l'usuari amb id userId
     * @throws SQLException
     */
    public Cursor fetchWebsOf(long userId) throws SQLException {
    	return mDb.query(true, DATABASE_TABLE_WEBS, new String[] {KEY_ROWID,
    			KEY_WEB}, KEY_USERID + "=" + userId, null, null, null, null, null);    	
    }
    
    
    /**
     * MÈTODES D'ESBORRAT "INTEL·LIGENTS"
     */
    
    
    /**
     * Esborra un usuari per complet: la fila de la taula d'usuaris i les files
     * associades al mateix usuari de les taules phones, mails i webs.
     * @param rowId id de l'usuari que volem esborrar
     * @return cert si s'ha esborrat tota la informació relativa a l'usuari
     */
    public boolean deleteUser(long rowId) {
        if (mDb.delete(DATABASE_TABLE_MAILS, KEY_USERID + "=" + rowId, null) <= 0) return false;
    	if (mDb.delete(DATABASE_TABLE_PHONES, KEY_USERID + "=" + rowId, null) <= 0) return false;
    	if (mDb.delete(DATABASE_TABLE_WEBS, KEY_USERID + "=" + rowId, null) <= 0) return false;
    	return mDb.delete(DATABASE_TABLE_USERS, KEY_ROWID + "=" + rowId, null) > 0;
    }

    /**
     * Esborra el telèfon phoneNumber de l'usuari amb rowId userId.
     * @param phoneNumber número de telèfon a esborrar
     * @param userId rowId de l'usuari associat al telèfon
     * @return cert si s'ha esborrat la fila correctament
     */
    public boolean deletePhoneOfUser(String phoneNumber, long userId) {
    	return mDb.delete(DATABASE_TABLE_PHONES, KEY_USERID + "=" + userId + 
    			" and " + KEY_PHONE + "=" + phoneNumber, null) > 0;
    }
    
    /**
     * Esborra el mail mail de l'usuari amb rowId userId.
     * @param mail mail a esborrar
     * @param userId rowId de l'usuari associat al mail
     * @return cert si s'ha esborrat la fila correctament
     */
    public boolean deleteMailOfUser(String mail, long userId) {
    	return mDb.delete(DATABASE_TABLE_MAILS, KEY_USERID + "=" + userId + 
    			" and " + KEY_MAIL + "=" + mail, null) > 0;
    }
    
    /**
     * Esborra la web webPage de l'usuari amb rowId userId.
     * @param webPage web a esborrar
     * @param userId rowId de l'usuari associat a la web
     * @return cert si s'ha esborrat la fila correctament
     */
    public boolean deleteWebOfUser(String webPage, long userId) {
    	return mDb.delete(DATABASE_TABLE_WEBS, KEY_USERID + "=" + userId + 
    			" and " + KEY_WEB + "=" + webPage, null) > 0;
    }
    
    //També podem fer un update user al que se li passin vectors de phones, mails i webs i updategi
    //intel·ligentment, creant noves files a les respectives taules

    /**
     * L'update ha de ser super intel·ligent... ha de deixar l'usuari només
     * amb els paràmetres que li passen. Per tant, s'ha d'encarregar d'esborrar
     * de les taules auxiliars totes les referències a l'usuari que no es trobin a
     * l'update i crear les noves.
     */
    public boolean updateUser(long rowId, String username, String password, String name,
    		String company, String position, ByteArrayEntity image, String twitter, String twitterpass) {
				
    	
    	return false;
    	
    }
    

    
    
    
    /**
     * MÈTODES D'ESBORRAT SIMPLES PER A LES TAULES AUXILIARS
     */
    
    
    /**
     * Esborra la fila de la taula phones amb id rowId
     * @param rowId id de la fila
     * @return cert si s'ha esborrat correctament
     */
    public boolean deletePhone(long rowId) {
        return mDb.delete(DATABASE_TABLE_PHONES, KEY_ROWID + "=" + rowId, null) > 0;
    }

    /**
     * Esborra la fila de la taula webs amb id rowId
     * @param rowId id de la fila
     * @return cert si s'ha esborrat correctament
     */
    public boolean deleteWeb(long rowId) {
        return mDb.delete(DATABASE_TABLE_WEBS, KEY_ROWID + "=" + rowId, null) > 0;
    }
    
    /**
     * Esborra la fila de la taula mails amb id rowId
     * @param rowId id de la fila
     * @return cert si s'ha esborrat correctament
     */
    public boolean deleteMail(long rowId) {
        return mDb.delete(DATABASE_TABLE_MAILS, KEY_ROWID + "=" + rowId, null) > 0;
    }
    
    
    
    /**
     * MÈTODES ESPECÍFICS PER AL REGISTRE I LOGIN
     */
    
    /**
     * Com que un usuari es crearà en el moment del registre, només
     * tindrem username i password.
     * @param username el nom d'usuari únic
     * @param password la contrassenya associada al nom d'usuari
     * @return rowId o -1 si ha fallat
     */
    public long createUser(String username, String password) {
    	ContentValues initialValues = new ContentValues();
    	initialValues.put(KEY_USERNAME, username);
    	initialValues.put(KEY_PASSWORD, password);
    	return mDb.insert(DATABASE_TABLE_USERS, null, initialValues);
    }
    
    /**
     * Comprova si és correcte l'usuari + password
     * @param username el nom d'usuari
     * @param password la contrassenya
     * @return cert si existeix un usuari username amb contrassenya password.
     */
    public boolean correctUserAndPassword(String username, String password) {
    	Cursor mCursor = mDb.query(DATABASE_TABLE_USERS, new String[] {KEY_ROWID},
    			KEY_USERNAME + "=" + username + " and " + KEY_PASSWORD + "=" + password,
    			null, null, null, null);
    	
    	if (mCursor == null) return false;
    	return true;
    }
    
    /**
     * Comprova si ja existeix l'usuari.
     * @param username el nom d'usuari
     * @return cert si existeix ja un usuari username.
     */
    public boolean existsUser(String username) {
    	Cursor mCursor = mDb.query(DATABASE_TABLE_USERS, new String[] {KEY_ROWID},
    			KEY_USERNAME + "=" + username, null, null, null, null);
    	
    	if (mCursor == null) return false;
    	return true;
    }
    
}
