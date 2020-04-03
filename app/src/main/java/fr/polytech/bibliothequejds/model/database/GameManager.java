package fr.polytech.bibliothequejds.model.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import fr.polytech.bibliothequejds.model.Category;
import fr.polytech.bibliothequejds.model.Game;

import static fr.polytech.bibliothequejds.model.database.DBHelper.TABLE_CATEGORY;
import static fr.polytech.bibliothequejds.model.database.DBHelper.TABLE_GAMES;

public class GameManager extends DBManager
{
    private CategoryManager categoryManager;

    /**
     * DBManager sole builder.
     *
     * @param context the context for database helper generation
     */
    public GameManager(Context context) {
        super(context);
    }

    public boolean addGame(Game game) {

        boolean isAdded;
        this.db.beginTransaction();
        try
        {
            // Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();
            values.put("gameName", game.getGameName());
            values.put("thumbnail", game.getThumbnail());
            values.put("minPlayers", game.getMinPlayers());
            values.put("maxPlayers", game.getMaxPlayers());
            values.put("meanTime", game.getMeanTime());
            values.put("notation", game.getNotation());
            values.put("age", game.getAge());
            values.put("difficulty", game.getDifficulty());
            values.put("yearOfPublication", game.getYearOfPublication());

            Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CATEGORY + " WHERE categoryName like ?", new String[]{game.getCategoryName()});
            cursor.moveToNext();
            values.put("categoryName", cursor.getString(cursor.getColumnIndex("categoryName")));
            cursor.close();

            db.insertOrThrow(TABLE_GAMES, null, values);
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

    public Game getGame(String gameNameParam)
    {
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_GAMES + " WHERE gameName like ?", new String[]{gameNameParam});
        cursor.moveToNext();
        Game game = new Game();
        game.setGameName(cursor.getString(cursor.getColumnIndex("gameName")));
        game.setAge(cursor.getInt(cursor.getColumnIndex("age")));

        Cursor categoryCursor = db.rawQuery("SELECT * FROM " + TABLE_CATEGORY + " WHERE categoryName = ?", new String[]{String.valueOf(cursor.getString(cursor.getColumnIndex("categoryName")))});
        categoryCursor.moveToNext();
        game.setCategoryName(categoryCursor.getString(categoryCursor.getColumnIndex("categoryName")));

        game.setDifficulty(cursor.getFloat(cursor.getColumnIndex("difficulty")));
        game.setMaxPlayers(cursor.getInt(cursor.getColumnIndex("maxPlayers")));
        game.setMinPlayers(cursor.getInt(cursor.getColumnIndex("minPlayers")));
        game.setMeanTime(cursor.getInt(cursor.getColumnIndex("meanTime")));
        game.setNotation(cursor.getFloat(cursor.getColumnIndex("notation")));
        game.setThumbnail(cursor.getString(cursor.getColumnIndex("thumbnail")));
        game.setYearOfPublication(cursor.getString(cursor.getColumnIndex("yearOfPublication")));

        categoryCursor.close();
        cursor.close();
        return game;
    }

    public List<Game> getAllGames()
    {
        List<Game> games = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_GAMES, null);
        while(cursor.moveToNext())
        {
            Game game = new Game();
            game.setGameName(cursor.getString(cursor.getColumnIndex("gameName")));
            game.setAge(cursor.getInt(cursor.getColumnIndex("age")));

            Cursor categoryCursor = db.rawQuery("SELECT * FROM " + TABLE_CATEGORY + " WHERE categoryName = ?", new String[]{String.valueOf(cursor.getString(cursor.getColumnIndex("categoryName")))});
            categoryCursor.moveToNext();
            game.setCategoryName(categoryCursor.getString(categoryCursor.getColumnIndex("categoryName")));
            categoryCursor.close();

            game.setDifficulty(cursor.getFloat(cursor.getColumnIndex("difficulty")));
            game.setMaxPlayers(cursor.getInt(cursor.getColumnIndex("maxPlayers")));
            game.setMinPlayers(cursor.getInt(cursor.getColumnIndex("minPlayers")));
            game.setMeanTime(cursor.getInt(cursor.getColumnIndex("meanTime")));
            game.setNotation(cursor.getFloat(cursor.getColumnIndex("notation")));
            game.setThumbnail(cursor.getString(cursor.getColumnIndex("thumbnail")));
            game.setYearOfPublication(cursor.getString(cursor.getColumnIndex("yearOfPublication")));

            games.add(game);
        }
        cursor.close();
        return games;
    }

    public boolean deleteGame(String gameNameParam)
    {
        boolean isDeleted;
        db.beginTransaction();
        try
        {
            db.execSQL("DELETE FROM " + TABLE_GAMES + " WHERE gameName like '" + gameNameParam + "'");
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

    public int getGameId(String gameNameParam)
    {
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_GAMES + " WHERE gameName like ?", new String[]{gameNameParam});
        cursor.moveToNext();
        int gameId = cursor.getInt(cursor.getColumnIndex("gameId"));
        cursor.close();
        return gameId;
    }

    public Game getGameById(int gameId)
    {
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_GAMES + " WHERE gameId = ?", new String[]{String.valueOf(gameId)});
        cursor.moveToNext();
        Game game = new Game();
        game.setGameName(cursor.getString(cursor.getColumnIndex("gameName")));
        game.setAge(cursor.getInt(cursor.getColumnIndex("age")));

        Cursor categoryCursor = db.rawQuery("SELECT * FROM " + TABLE_CATEGORY + " WHERE categoryName = ?", new String[]{String.valueOf(cursor.getString(cursor.getColumnIndex("categoryName")))});
        categoryCursor.moveToNext();
        game.setCategoryName(categoryCursor.getString(categoryCursor.getColumnIndex("categoryName")));

        game.setDifficulty(cursor.getFloat(cursor.getColumnIndex("difficulty")));
        game.setMaxPlayers(cursor.getInt(cursor.getColumnIndex("maxPlayers")));
        game.setMinPlayers(cursor.getInt(cursor.getColumnIndex("minPlayers")));
        game.setMeanTime(cursor.getInt(cursor.getColumnIndex("meanTime")));
        game.setNotation(cursor.getFloat(cursor.getColumnIndex("notation")));
        game.setThumbnail(cursor.getString(cursor.getColumnIndex("thumbnail")));
        game.setYearOfPublication(cursor.getString(cursor.getColumnIndex("yearOfPublication")));

        categoryCursor.close();
        cursor.close();
        return game;
    }
}
