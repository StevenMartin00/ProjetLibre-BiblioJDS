package fr.polytech.bibliothequejds.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

import fr.polytech.bibliothequejds.model.Category;
import fr.polytech.bibliothequejds.model.Game;
import fr.polytech.bibliothequejds.model.database.CategoryManager;
import fr.polytech.bibliothequejds.model.database.GameManager;

public class JsonParserTask extends AsyncTask<Void, Void, Void> {

    private ProgressDialog dialog;
    private GameManager gameManager;
    private CategoryManager categoryManager;

    public JsonParserTask(Activity activity, GameManager gameManager, CategoryManager categoryManager) {
        dialog = new ProgressDialog(activity);
        this.gameManager = gameManager;
        this.categoryManager = categoryManager;
    }

    @Override
    protected void onPreExecute() {
        dialog.setMessage("Chargement des jeux en cours. Veuillez patienter...");
        dialog.show();
    }
    @Override
    protected Void doInBackground(Void... voids) {

        Map<String, Game> categoriesIdForGame = new HashMap<>();

        //Calling Board Game Atlas API
        //Retrieving games
        URL url = null;
        HttpURLConnection conn = null;
        int responsecode = 0;
        try {
            url = new URL("https://www.boardgameatlas.com/api/search?client_id=9kSXSQwhWR");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            responsecode = conn.getResponseCode();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (responsecode != 200) {
            throw new RuntimeException("HttpResponseCode " + responsecode);
        } else {
            Scanner sc = null;
            try {
                sc = new Scanner(url.openStream());
            } catch (IOException e) {
                e.printStackTrace();
            }

            String inline = "";
            while (sc.hasNext()) {
                inline += (sc.nextLine());
            }

            JSONObject obj;
            try {
                obj = new JSONObject(inline);
                JSONArray games = obj.getJSONArray("games");
                for (int i = 0; i < games.length(); i++) {
                    JSONObject gameDetails = games.getJSONObject(i);

                    Game addedGame = new Game(gameDetails.getString("name"), gameDetails.getString("thumb_url"), gameDetails.getInt("min_players"), gameDetails.getInt("max_players"), gameDetails.getInt("max_playtime") - gameDetails.getInt("min_playtime"), (float) gameDetails.getDouble("average_user_rating"), gameDetails.getInt("min_age"), 0f, gameDetails.getString("year_published"), "");
                    JSONArray categories = gameDetails.getJSONArray("categories");
                    for(int j = 0; j < categories.length(); j++)
                    {
                        JSONObject category = categories.getJSONObject(j);
                        categoriesIdForGame.put(category.getString("id"), addedGame);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            finally {
                sc.close();
            }
        }
        conn.disconnect();
        //Retrieving categories
        responsecode = 0;
        try {
            url = new URL("https://www.boardgameatlas.com/api/game/categories?client_id=9kSXSQwhWR");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            responsecode = conn.getResponseCode();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (responsecode != 200) {
            throw new RuntimeException("HttpResponseCode " + responsecode);
        } else {
            Scanner sc = null;
            try {
                sc = new Scanner(url.openStream());
            } catch (IOException e) {
                e.printStackTrace();
            }

            String inline = "";
            while (sc.hasNext()) {
                inline += (sc.nextLine());
            }

            JSONObject obj;
            try {
                obj = new JSONObject(inline);
                JSONArray categories = obj.getJSONArray("categories");
                for (int i = 0; i < categories.length(); i++) {
                    JSONObject categoryDetails = categories.getJSONObject(i);
                    String categoryName = categoryDetails.getString("name");
                    String categoryId = categoryDetails.getString("id");
                    Category addedCategory = new Category(categoryName);
                    for(Map.Entry entry : categoriesIdForGame.entrySet())
                    {
                        if(entry.getKey().equals(categoryId))
                        {
                            Game game = (Game) entry.getValue();
                            game.setCategoryName(categoryName);
                        }
                    }
                    categoryManager.addCategory(addedCategory);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            finally {
                sc.close();
            }
        }
        removeGameDuplicates(categoriesIdForGame);
        for(Map.Entry entry : categoriesIdForGame.entrySet())
        {
            gameManager.addGame((Game) entry.getValue());
        }
        conn.disconnect();
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    private static void removeGameDuplicates(final Map<String, Game> map) {
        final Iterator<Map.Entry<String, Game>> iter = map.entrySet().iterator();
        final HashSet<Game> valueSet = new HashSet<>();
        while (iter.hasNext()) {
            final Map.Entry<String, Game> next = iter.next();
            if (!valueSet.add(next.getValue())) {
                iter.remove();
            }
        }
    }
}
