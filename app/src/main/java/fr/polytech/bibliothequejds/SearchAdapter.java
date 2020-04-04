package fr.polytech.bibliothequejds;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;

import fr.polytech.bibliothequejds.model.Game;
import fr.polytech.bibliothequejds.ui.CustomViewHolder;
import fr.polytech.bibliothequejds.ui.ItemClickListener;
import fr.polytech.bibliothequejds.utils.BitmapFromUrlTask;

/**
 * Custom Adapter used to create a list view with a text view and an image view
 */
public class SearchAdapter extends RecyclerView.Adapter<CustomViewHolder> {

    private Context c;
    private List<Game> games;

    public SearchAdapter(Context c, List<Game> games) {
        super();
        this.c = c;
        this.games = games;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(c).inflate(R.layout.list_row,parent,false);
        return new CustomViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, final int position) {

        //BIND DATA
        holder.getTitle().setText(games.get(position).getGameName());

        Bitmap bitmap;
        try {
            bitmap = new BitmapFromUrlTask().execute(games.get(position).getThumbnail()).get();
            holder.getImage().setImageBitmap(Bitmap.createScaledBitmap(bitmap, 400, 300, false));
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //ITEM CLICK
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(int pos) {

                //Affichage vue
                Intent myIntent = new Intent(c, GameActivity.class);
                myIntent.putExtra("game", games.get(pos).getGameName());
                myIntent.putExtra("category", games.get(pos).getCategoryName());
                myIntent.putExtra("notation", games.get(pos).getNotation());
                myIntent.putExtra("yearOfPublication", games.get(pos).getYearOfPublication());
                myIntent.putExtra("minPlayers", games.get(pos).getMinPlayers());
                myIntent.putExtra("maxPlayers", games.get(pos).getMaxPlayers());
                myIntent.putExtra("meanTime", games.get(pos).getMeanTime());
                myIntent.putExtra("age", games.get(pos).getAge());
                myIntent.putExtra("difficulty", games.get(pos).getDifficulty());
                myIntent.putExtra("thumbnail", games.get(pos).getThumbnail());

                myIntent.putExtra("src","search");
                c.startActivity(myIntent); //View Game
            }
        });

    }

    @Override
    public int getItemCount() {
        return games.size();
    }
}