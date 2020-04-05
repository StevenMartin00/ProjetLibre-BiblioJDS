package fr.polytech.bibliothequejds.model.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import fr.polytech.bibliothequejds.model.EncryptionUtils;
import fr.polytech.bibliothequejds.model.User;

import static fr.polytech.bibliothequejds.model.database.DBHelper.TABLE_USER;

/**
 * Class used to manage the database
 */
public class DBManager {

	/**
	 * The database helper.
	 */
	private DBHelper helper;

	/**
	 * The database.
	 */
	protected SQLiteDatabase db;
	
	/**
	 * DBManager sole builder.
	 *
	 * @param context the context for database helper generation
	 */
	public DBManager(Context context ) {
		if(helper == null)
		{
			helper = new DBHelper(context);
			db = helper.getWritableDatabase();
			helper.onCreate(db);
		}
	}

	/**
	 * Close the dataBase.
	 */
	public void closeDB() {
		db.close();
	}
}
