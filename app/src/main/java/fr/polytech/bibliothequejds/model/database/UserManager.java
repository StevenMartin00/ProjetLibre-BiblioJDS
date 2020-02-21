package fr.polytech.bibliothequejds.model.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import fr.polytech.bibliothequejds.model.EncryptionUtils;
import fr.polytech.bibliothequejds.model.User;

import static fr.polytech.bibliothequejds.model.database.DBHelper.TABLE_USER;

public class UserManager extends DBManager
{

    /**
     * DBManager sole builder.
     *
     * @param context the context for database helper generation
     */
    public UserManager(Context context) {
        super(context);
    }

    public boolean addUser(User user) {

        boolean isAdded;
        this.db.beginTransaction();
        try
        {
            // Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();
            values.put("username", user.getUsername());
            values.put("password", EncryptionUtils.encrypt(user.getPassword()));
            values.put("birthDate", user.getBirthDate());

            db.insertOrThrow(TABLE_USER, null, values);
            db.setTransactionSuccessful();
            isAdded = true;
        }
        catch(Exception e)
        {
            isAdded = false;
        }
        finally
        {
            db.endTransaction();
        }
        return isAdded;
    }

    public User getUser(String usernameParam)
    {
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE username like ?", new String[]{usernameParam});
        cursor.moveToNext();
        User user = new User();
        user.setUsername(cursor.getString(cursor.getColumnIndex("username")));
        user.setPassword(cursor.getString(cursor.getColumnIndex("password")));
        user.setBirthDate(cursor.getString(cursor.getColumnIndex("birthDate")));
        user.setCreationDate(cursor.getString(cursor.getColumnIndex("created_at")));
        cursor.close();
        return user;
    }

    public boolean deleteUserByUsername(String usernameParam)
    {
        boolean isDeleted;
        db.beginTransaction();
        try
        {
            db.execSQL("DELETE FROM " + TABLE_USER + " WHERE username like '" + usernameParam + "'");
            db.setTransactionSuccessful();
            isDeleted = true;
        }
        catch(Exception e)
        {
            isDeleted = false;
        }
        finally
        {
            db.endTransaction();
        }
        return isDeleted;
    }

    public int getUserId(String usernameParam)
    {
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE username like ?", new String[]{usernameParam});
        cursor.moveToNext();
        int userId = cursor.getInt(cursor.getColumnIndex("userId"));
        cursor.close();
        return userId;
    }

    public User getUserById(int userId)
    {
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE userId = ?", new String[]{String.valueOf(userId)});
        cursor.moveToNext();
        User user = new User();
        user.setUsername(cursor.getString(cursor.getColumnIndex("username")));
        user.setPassword(cursor.getString(cursor.getColumnIndex("password")));
        user.setBirthDate(cursor.getString(cursor.getColumnIndex("birthDate")));
        user.setCreationDate(cursor.getString(cursor.getColumnIndex("created_at")));
        cursor.close();
        return user;
    }
}
