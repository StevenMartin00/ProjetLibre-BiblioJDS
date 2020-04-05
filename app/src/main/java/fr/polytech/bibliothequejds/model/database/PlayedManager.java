package fr.polytech.bibliothequejds.model.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.polytech.bibliothequejds.model.Game;
import fr.polytech.bibliothequejds.model.User;

import static fr.polytech.bibliothequejds.model.database.DBHelper.TABLE_GAMES;
import static fr.polytech.bibliothequejds.model.database.DBHelper.TABLE_USER;
import static fr.polytech.bibliothequejds.model.database.DBHelper.TABLE_PLAYED;

public class PlayedManager extends DBManager
{

    private UserManager userManager;
    private GameManager gameManager;

    /**
     * DBManager sole builder.
     *
     * @param context the context for database helper generation
     */
    public PlayedManager(Context context) {
        super(context);
        this.userManager = new UserManager(context);
        this.gameManager = new GameManager(context);
    }

    public boolean addPlayed(String username, String gameName, double score, int numberOfGamesPlayed)
    {
        boolean isAdded;
        this.db.beginTransaction();
        try
        {
            // Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();
            values.put("score", score);
            values.put("numberOfGamesPlayed", numberOfGamesPlayed + 1);

            Cursor gameCursor = db.rawQuery("SELECT * FROM " + TABLE_GAMES + " WHERE gameName like ?", new String[]{gameName});
            gameCursor.moveToNext();

            values.put("gameId", gameCursor.getInt(gameCursor.getColumnIndex("gameId")));

            Cursor userCursor = db.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE username like ?", new String[]{username});
            userCursor.moveToNext();

            values.put("userId", userCursor.getInt(userCursor.getColumnIndex("userId")));

            gameCursor.close();
            userCursor.close();

            db.insertOrThrow(TABLE_PLAYED, null, values);
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

    public List<Game> getGamesPlayedByUsername(String usernameParam)
    {
        User player = userManager.getUser(usernameParam);
        List<Game> gamesPlayed = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PLAYED + " WHERE userId = ?", new String[]{String.valueOf(userManager.getUserId(usernameParam))});
        while(cursor.moveToNext())
        {
            Game gamePlayed = gameManager.getGameById(cursor.getInt(cursor.getColumnIndex("gameId")));
            gamesPlayed.add(gamePlayed);
        }
        cursor.close();
        return gamesPlayed;
    }

    public List<User> getGamePlayers(String gameNameParam)
    {
        Game gamePlayed = gameManager.getGame(gameNameParam);
        List<User> gamePlayers = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PLAYED + " WHERE gameId = ?", new String[]{String.valueOf(gameManager.getGameId(gameNameParam))});
        while(cursor.moveToNext())
        {
            User player = userManager.getUserById(cursor.getInt(cursor.getColumnIndex("userId")));
            gamePlayers.add(player);
        }
        cursor.close();
        return gamePlayers;
    }

    public String getMostCommonCategory(List<Game> games)
    {
        Map<String, Integer> map = new HashMap<>();
        for(Game game : games)
        {
            for(String category : game.getCategoryName().split(","))
            {
                Integer value = map.get(category);
                map.put(category, value == null ? 1 : value + 1);
            }
        }

        Map.Entry<String, Integer> mostCommon = null;

        for(Map.Entry<String, Integer> entry : map.entrySet())
        {
            if(mostCommon == null || entry.getValue() > mostCommon.getValue())
                mostCommon = entry;
        }

        return mostCommon != null ? mostCommon.getKey() : null;
    }

    public int getNumberOfGamesPlayedByUsernameAndByGame(String username, String gameName)
    {
        int numberOfGamesPlayed = 0;
        Cursor cursor = db.rawQuery("SELECT numberOfGamesPlayed FROM " + TABLE_PLAYED + " WHERE userId = ? AND gameId = ?", new String[]{String.valueOf(userManager.getUserId(username)), String.valueOf(gameManager.getGameId(gameName))});
        if(cursor.moveToNext())
        {
            numberOfGamesPlayed = cursor.getInt(cursor.getColumnIndex("numberOfGamesPlayed"));
        }
        return numberOfGamesPlayed;
    }

    public boolean updateNumberOfGamesPlayed(String username, String gameName, int numberOfGamesPlayed)
    {
        db.beginTransaction();
        boolean isUpdated;
        try
        {
            Cursor gameCursor = db.rawQuery("SELECT * FROM " + TABLE_GAMES + " WHERE gameName like ?", new String[]{gameName});
            gameCursor.moveToNext();
            int gameId = gameCursor.getInt(gameCursor.getColumnIndex("gameId"));

            Cursor userCursor = db.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE username like ?", new String[]{username});
            userCursor.moveToNext();
            int userId = userCursor.getInt(userCursor.getColumnIndex("userId"));

            db.execSQL("UPDATE " + TABLE_PLAYED + " SET numberOfGamesPlayed = " + numberOfGamesPlayed + " WHERE gameId = " + gameId + " AND userId = " + userId);
            db.setTransactionSuccessful();
            isUpdated = true;

            gameCursor.close();
            userCursor.close();
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
    }

    public boolean deletePlayed(String username, String gameName)
    {
        boolean isDeleted;
        db.beginTransaction();
        try
        {
            Cursor gameCursor = db.rawQuery("SELECT * FROM " + TABLE_GAMES + " WHERE gameName like ?", new String[]{gameName});
            gameCursor.moveToNext();
            int gameId = gameCursor.getInt(gameCursor.getColumnIndex("gameId"));

            Cursor userCursor = db.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE username like ?", new String[]{username});
            userCursor.moveToNext();
            int userId = userCursor.getInt(userCursor.getColumnIndex("userId"));

            db.execSQL("DELETE FROM " + TABLE_PLAYED + " WHERE gameId = " + gameId + " AND userId = " + userId);
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
