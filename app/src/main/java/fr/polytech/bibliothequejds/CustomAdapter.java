package fr.polytech.bibliothequejds;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import fr.polytech.bibliothequejds.model.Game;
import fr.polytech.bibliothequejds.ui.CustomViewHolder;
import fr.polytech.bibliothequejds.ui.ItemClickListener;

/**
 * Custom Adapter used to create a list view with a text view and an image view
 */
public class CustomAdapter extends RecyclerView.Adapter<CustomViewHolder> {

    private Context c;
    private ArrayList<Game> games;

    public CustomAdapter(Context c, ArrayList<Game> games) {
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
    public void onBindViewHolder(CustomViewHolder holder, int position) {

        //BIND DATA
        holder.getTitle().setText(games.get(position).getGameName());
        Bitmap bm = BitmapFactory.decodeResource(c.getResources(), games.get(position).getThumbnailId());
        holder.getImage().setImageBitmap(Bitmap.createScaledBitmap(bm, 400, 300, false));

        //ITEM CLICK
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                Toast.makeText(c, games.get(pos).getGameName(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return games.size();
    }
}



/*public class CustomAdapter implements ListAdapter
{
    private ArrayList<Game> games;
    private Context context;

    public CustomAdapter(Context context, ArrayList<Game> games) {
        this.games = games;
        this.context = context;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
    }

    @Override
    public int getCount() {
        return games.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Game game = games.get(position);
        if(convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.list_row, null);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });

            TextView title = convertView.findViewById(R.id.title);
            ImageView imag = convertView.findViewById(R.id.list_image);
            imag.setImageResource(game.getThumbnailId());
            title.setText(game.getGameName());
            scaleImage(imag);
        }
        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return games.size();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
    
    private void scaleImage(ImageView view) throws NoSuchElementException  {
        // Get bitmap from the the ImageView.
        Bitmap bitmap;

        try {
            Drawable drawing = view.getDrawable();
            bitmap = ((BitmapDrawable) drawing).getBitmap();
        }
        catch (NullPointerException e) {
            throw new NoSuchElementException("No drawable on given view");
        }

        // Get current dimensions AND the desired bounding box
        int width;

        try {
            width = bitmap.getWidth();
        }
        catch (NullPointerException e) {
            throw new NoSuchElementException("Can't find bitmap on given view/drawable");
        }

        int height = bitmap.getHeight();
        int bounding = dpToPx(150);
        Log.i("Test", "original width = " + width);
        Log.i("Test", "original height = " + height);
        Log.i("Test", "bounding = " + bounding);

        // Determine how much to scale: the dimension requiring less scaling is
        // closer to the its side. This way the image always stays inside your
        // bounding box AND either x/y axis touches it.
        float xScale = ((float) bounding) / width;
        float yScale = ((float) bounding) / height;
        float scale = (xScale <= yScale) ? xScale : yScale;
        Log.i("Test", "xScale = " + xScale);
        Log.i("Test", "yScale = " + yScale);
        Log.i("Test", "scale = " + scale);

        // Create a matrix for the scaling and add the scaling data
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);

        // Create a new bitmap and convert it to a format understood by the ImageView
        Bitmap scaledBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        width = scaledBitmap.getWidth(); // re-use
        height = scaledBitmap.getHeight(); // re-use
        BitmapDrawable result = new BitmapDrawable(scaledBitmap);
        Log.i("Test", "scaled width = " + width);
        Log.i("Test", "scaled height = " + height);

        // Apply the scaled bitmap
        view.setImageDrawable(result);

        // Now change ImageView's dimensions to match the scaled image
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
        params.width = width;
        params.height = height;
        view.setLayoutParams(params);

        Log.i("Test", "done");
    }

    private int dpToPx(int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float)dp * density);
    }
}*/
