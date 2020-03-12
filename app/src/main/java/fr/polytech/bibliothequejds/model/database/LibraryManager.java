package fr.polytech.bibliothequejds.model.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import fr.polytech.bibliothequejds.model.Game;
import fr.polytech.bibliothequejds.model.Library;
import fr.polytech.bibliothequejds.model.User;

import static fr.polytech.bibliothequejds.model.database.DBHelper.TABLE_LIBRARY;
import static fr.polytech.bibliothequejds.model.database.DBHelper.TABLE_USER;

public class LibraryManager extends DBManager
{
    private GameManager gameManager;
    private UserManager userManager;

    /**
     * DBManager sole builder.
     *
     * @param context the context for database helper generation
     */
    public LibraryManager(Context context) {
        super(context);
        gameManager = new GameManager(context);
        userManager = new UserManager(context);
    }

    public boolean addLibrary(Library library) {

        boolean isAdded;
        this.db.beginTransaction();
        try
        {
            // Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();

            Cursor userCursor = db.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE username like ?", new String[]{library.getOwner().getUsername()});
            userCursor.moveToNext();

            values.put("userId", userCursor.getInt(userCursor.getColumnIndex("userId")));
            for(Game game : library.getGameList())
            {
                values.put("gameName", game.getGameName());
            }
            userCursor.close();

            db.insertOrThrow(TABLE_LIBRARY, null, values);
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

    public boolean addGameToLibraryGameList(Library library, Game game) {

        boolean isAdded;
        this.db.beginTransaction();
        try
        {
            Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_LIBRARY + " WHERE libraryId = ?", new String[]{String.valueOf(getLibraryId(library.getOwner().getUsername()))});
            Library libraryToUpdate = new Library();
            List<Game> newGameList = new ArrayList<>();
            int index = 0;
            while(cursor.moveToNext())
            {
                if(index == 0)
                {
                    libraryToUpdate.setOwner(userManager.getUserById(cursor.getInt(cursor.getColumnIndex("userId"))));
                    index = 1;
                }

                Game gameFound = gameManager.getGame(cursor.getString(cursor.getColumnIndex("gameName")));
                newGameList.add(gameFound);
            }
            newGameList.add(game);
            libraryToUpdate.setGameList(newGameList);

            // Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();

            Cursor userCursor = db.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE username like ?", new String[]{library.getOwner().getUsername()});
            userCursor.moveToNext();

            values.put("userId", userCursor.getInt(userCursor.getColumnIndex("userId")));
            for(Game gameInList : libraryToUpdate.getGameList())
            {
                values.put("gameName", gameInList.getGameName());
            }
            userCursor.close();
            cursor.close();
            db.insertOrThrow(TABLE_LIBRARY, null, values);
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

    public int getLibraryId(String usernameParam)
    {
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_LIBRARY + " WHERE userId = ?", new String[]{String.valueOf(userManager.getUserId(usernameParam))});
        cursor.moveToNext();
        int libraryId = cursor.getInt(cursor.getColumnIndex("libraryId"));
        cursor.close();
        return libraryId;
    }

    public Library getLibrary(String usernameParam)
    {
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_LIBRARY + " WHERE userId = ?", new String[]{String.valueOf(userManager.getUserId(usernameParam))});
        Library library = new Library();
        List<Game> gameList = new ArrayList<>();
        int index = 0;
        while(cursor.moveToNext())
        {
            if(index == 0)
            {
                library.setOwner(userManager.getUserById(cursor.getInt(cursor.getColumnIndex("userId"))));
                index = 1;
            }
            gameList.add(gameManager.getGame(cursor.getString(cursor.getColumnIndex("gameName"))));
        }
        library.setGameList(gameList);
        cursor.close();
        return library;
    }

    public boolean deleteGameFromLibraryGameList(Library library, Game game)
    {
        boolean isDeleted;
        db.beginTransaction();
        try
        {
            db.execSQL("DELETE FROM " + TABLE_LIBRARY + " WHERE libraryId = " + getLibraryId(library.getOwner().getUsername()) + " AND gameName like '" + game.getGameName() + "'");
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
