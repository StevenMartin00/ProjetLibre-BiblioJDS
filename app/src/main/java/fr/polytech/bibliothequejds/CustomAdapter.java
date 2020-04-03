package fr.polytech.bibliothequejds;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.polytech.bibliothequejds.model.Game;
import fr.polytech.bibliothequejds.ui.CustomViewHolder;
import fr.polytech.bibliothequejds.ui.ItemClickListener;

/**
 * Custom Adapter used to create a list view with a text view and an image view
 */
public class CustomAdapter extends RecyclerView.Adapter<CustomViewHolder> {

    private Context c;
    private List<Game> games;

    public CustomAdapter(Context c, List<Game> games) {
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
        holder.getImage().setImageURI(Uri.parse(games.get(position).getThumbnail()));
        //ITEM CLICK
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                //Toast.makeText(c, games.get(pos).getGameName(), Toast.LENGTH_SHORT).show();

                //Affichage vue
                Intent myIntent = new Intent(c, GameActivity.class);
                myIntent.putExtra("game", games.get(pos).getGameName());
                //myIntent.putExtra("category", games.get(pos).getCategoryName());

                myIntent.putExtra("src","bibli");
                c.startActivity(myIntent); //View Game
            }
        });

    }

    @Override
    public int getItemCount() {
        return games.size();
    }
}