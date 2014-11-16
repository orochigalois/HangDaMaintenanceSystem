package info.androidhive.expandablelistview;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class _DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "contactsManager";

	// Contacts table name
	private static final String TABLE_CONTACTS = "contacts";

	// Contacts Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_USER = "user";
	private static final String KEY_PWD = "pwd";
	private static final String KEY_LOCK = "lock";
	private final ArrayList<_Contact> contact_list = new ArrayList<_Contact>();

	public _DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
		+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_USER + " TEXT,"
		+ KEY_PWD + " TEXT," + KEY_LOCK + " TEXT" + ")";
		db.execSQL(CREATE_CONTACTS_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

		// Create tables again
		onCreate(db);
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	// Adding new contact
	public void Add_Contact(_Contact contact) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_USER, contact.getUser()); // Contact Name
		values.put(KEY_PWD, contact.getPwd()); // Contact Phone
		values.put(KEY_LOCK, contact.getLock()); // Contact Phone
		// Inserting Row
		db.insert(TABLE_CONTACTS, null, values);
		db.close(); // Closing database connection
	}

	// Getting single contact
	_Contact Get_Contact(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
				KEY_USER, KEY_PWD,KEY_LOCK }, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		_Contact contact = new _Contact(Integer.parseInt(cursor.getString(0)),
		cursor.getString(1), cursor.getString(2), cursor.getString(3));
		// return contact
		cursor.close();
		db.close();

		return contact;
	}

	// Getting All Contacts
	public ArrayList<_Contact> Get_Contacts() {
		try {
			contact_list.clear();

			// Select All Query
			String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

			SQLiteDatabase db = this.getWritableDatabase();
			Cursor cursor = db.rawQuery(selectQuery, null);

			// looping through all rows and adding to list
			if (cursor.moveToFirst()) {
				do {
					_Contact contact = new _Contact();
					contact.setID(Integer.parseInt(cursor.getString(0)));
					contact.setUser(cursor.getString(1));
					contact.setPwd(cursor.getString(2));
					contact.setLock(cursor.getString(3));
					// Adding contact to list
					contact_list.add(contact);
				} while (cursor.moveToNext());
			}

			// return contact list
			cursor.close();
			db.close();
			return contact_list;
		} catch (Exception e) {
			// TODO: handle exception
			Log.e("all_contact", "" + e);
		}

		return contact_list;
	}

	
	public int UpdatePWD(String user,String pwd)
	{
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();

		values.put(KEY_PWD, pwd);
	

		// updating row
		return db.update(TABLE_CONTACTS, values, KEY_USER + " = ?",
				new String[] { user });
	}
	
	public int UnlockOrLockUser(String userID,String action)
	{
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();

		values.put(KEY_LOCK, action);
	

		// updating row
		return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
				new String[] { userID });
	}
	// Updating single contact
	public int Update_Contact(_Contact contact) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_USER, contact.getUser());
		values.put(KEY_PWD, contact.getPwd());
		values.put(KEY_LOCK, contact.getLock());
	

		// updating row
		return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
				new String[] { String.valueOf(contact.getID()) });
	}
	
	// Updating single contact
	public int Update_Contact_For_Lock(_Contact contact) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_LOCK, contact.getLock());
	
		// updating row
		return db.update(TABLE_CONTACTS, values, KEY_USER + " = ?",
				new String[] { contact.getUser() });
	}

	// Deleting single contact
	public void Delete_Contact(int id) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
				new String[] { String.valueOf(id) });
		db.close();
	}

	// Getting contacts Count
	public int Get_Total_Contacts() {
		String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}

}
