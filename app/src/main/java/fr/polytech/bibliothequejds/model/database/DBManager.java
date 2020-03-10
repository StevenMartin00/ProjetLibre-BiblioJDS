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
	






	/*
	public List<Record> query() {
		ArrayList<Record> records = new ArrayList<>();
		Cursor c       = queryTheCursor();
		while ( c.moveToNext() ) {
			Record record = new Record();
			record.setName( c.getString( c.getColumnIndex( "Name" ) ) );
			record.setPath( c.getString( c.getColumnIndex( "Path" ) ) );
			record.setJitter( c.getDouble( c.getColumnIndex("Jitter") ) );
			record.setShimmer( c.getDouble( c.getColumnIndex("Shimmer") ) );
			record.setF0( c.getDouble( c.getColumnIndex("F0") ) );
			records.add( record );
		}
		c.close();
		return records;
	}*/


	/*
	*/

	/*
	public boolean updateRecordVoiceFeatures(String name, double jitter, double shimmer, double f0)
	{
		db.beginTransaction();
		boolean isUpdated;
		try
		{
			db.execSQL("UPDATE Voices SET Jitter = " + jitter + ", Shimmer = " + shimmer + ", F0 = " + f0 + " WHERE Name like '" + name + "'");
			db.setTransactionSuccessful();
			isUpdated = true;
		}
		catch(Exception e)
		{
			isUpdated = false;
		}
		finally
		{
			db.endTransaction();
		}
		return isUpdated;
	}*/

	/*
	private Cursor queryTheCursor() {
		return db.rawQuery( "SELECT * FROM Voices", null );
	}*/

	/*
	public boolean isDatabaseEmpty()
	{
		Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
		boolean isEmpty;
		isEmpty = !cursor.moveToFirst();
		return isEmpty;
	}*/

	/*
	public void resetDB()
	{
		db.beginTransaction();
		db.execSQL("DELETE FROM " + TABLE_NAME );
		db.setTransactionSuccessful();
		db.endTransaction();
	}*/

}
