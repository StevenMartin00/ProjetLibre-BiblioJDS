package fr.polytech.bibliothequejds;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.concurrent.ExecutionException;

import fr.polytech.bibliothequejds.model.Game;
import fr.polytech.bibliothequejds.model.Played;
import fr.polytech.bibliothequejds.ui.CustomViewHolder;
import fr.polytech.bibliothequejds.ui.ItemClickListener;
import fr.polytech.bibliothequejds.utils.BitmapFromUrlTask;

/**
 * Custom Adapter used to create a list view with a text view and an image view
 */
public class LibraryAdapter extends RecyclerView.Adapter<CustomViewHolder> {

    private Context c;
    private List<Game> gamesPlayedList;

    public LibraryAdapter(Context c, List<Game> gamesPlayedList) {
        super();
        this.c = c;
        this.gamesPlayedList = gamesPlayedList;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(c).inflate(R.layout.list_row,parent,false);
        return new CustomViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, final int position) {

        //BIND DATA
        holder.getTitle().setText(gamesPlayedList.get(position).getGameName());

        Bitmap bitmap;
        try {
            bitmap = new BitmapFromUrlTask().execute(gamesPlayedList.get(position).getThumbnail()).get();
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
                myIntent.putExtra("game", gamesPlayedList.get(pos).getGameName());
                myIntent.putExtra("category", gamesPlayedList.get(pos).getCategoryName());
                myIntent.putExtra("notation", gamesPlayedList.get(pos).getNotation());
                myIntent.putExtra("yearOfPublication", gamesPlayedList.get(pos).getYearOfPublication());
                myIntent.putExtra("minPlayers", gamesPlayedList.get(pos).getMinPlayers());
                myIntent.putExtra("maxPlayers", gamesPlayedList.get(pos).getMaxPlayers());
                myIntent.putExtra("meanTime", gamesPlayedList.get(pos).getMeanTime());
                myIntent.putExtra("age", gamesPlayedList.get(pos).getAge());
                myIntent.putExtra("difficulty", gamesPlayedList.get(pos).getDifficulty());
                myIntent.putExtra("thumbnail", gamesPlayedList.get(pos).getThumbnail());

                myIntent.putExtra("src","bibli");
                c.startActivity(myIntent); //View Game
            }
        });

    }

    @Override
    public int getItemCount() {
        return gamesPlayedList.size();
    }
}