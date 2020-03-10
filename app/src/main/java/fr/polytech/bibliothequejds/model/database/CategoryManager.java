package fr.polytech.bibliothequejds.model.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import fr.polytech.bibliothequejds.model.Category;

import static fr.polytech.bibliothequejds.model.database.DBHelper.TABLE_CATEGORY;

public class CategoryManager extends DBManager
{

    /**
     * DBManager sole builder.
     *
     * @param context the context for database helper generation
     */
    public CategoryManager(Context context) {
        super(context);
    }

    public boolean addCategory(Category category) {

        boolean isAdded;
        this.db.beginTransaction();
        try
        {
            // Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();
            values.put("categoryName", category.getCategoryName());

            db.insertOrThrow(TABLE_CATEGORY, null, values);
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

    public Category getCategory(String categoryNameParam)
    {
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CATEGORY + " WHERE categoryName like ?", new String[]{categoryNameParam});
        cursor.moveToNext();
        Category category = new Category();
        category.setCategoryName(cursor.getString(cursor.getColumnIndex("username")));
        cursor.close();
        return category;
    }

    public boolean deleteCategory(String categoryNameParam)
    {
        boolean isDeleted;
        db.beginTransaction();
        try
        {
            db.execSQL("DELETE FROM " + TABLE_CATEGORY + " WHERE categoryName like '" + categoryNameParam + "'");
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
}
