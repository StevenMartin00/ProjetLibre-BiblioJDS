package fr.polytech.bibliothequejds.ui;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import fr.polytech.bibliothequejds.R;

public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    private TextView title;
    private ImageView image;
    private ItemClickListener itemClickListener;

    public CustomViewHolder(View itemView)
    {
        super(itemView);
        title = itemView.findViewById(R.id.title);
        image = itemView.findViewById(R.id.list_image);
        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener)
    {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v)
    {
        this.itemClickListener.onItemClick(this.getLayoutPosition());
    }

    public TextView getTitle() {
        return title;
    }

    public void setTitle(TextView title) {
        this.title = title;
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }
}
