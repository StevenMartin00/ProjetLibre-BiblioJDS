package fr.polytech.bibliothequejds.model.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import fr.polytech.bibliothequejds.model.EncryptionUtils;
import fr.polytech.bibliothequejds.model.Game;
import fr.polytech.bibliothequejds.model.Played;
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

    public boolean addPlayed(Played played)
    {
        boolean isAdded;
        this.db.beginTransaction();
        try
        {
            // Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();
            values.put("score", played.getScore());
            values.put("numberOfGamesPlayed", played.getNumberOfGamesPlayed() + 1);

            Cursor gameCursor = db.rawQuery("SELECT * FROM " + TABLE_GAMES + " WHERE gameName like ?", new String[]{played.getGamePlayed().getGameName()});
            gameCursor.moveToNext();

            values.put("gameId", gameCursor.getInt(gameCursor.getColumnIndex("gameId")));

            Cursor userCursor = db.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE username like ?", new String[]{played.getPlayer().getUsername()});
            userCursor.moveToNext();

            values.put("userId", userCursor.getInt(userCursor.getColumnIndex("userId")));

            gameCursor.close();
            userCursor.close();

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
}
