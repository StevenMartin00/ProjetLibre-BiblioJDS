package fr.polytech.bibliothequejds.ui.bibliotheque;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import fr.polytech.bibliothequejds.CustomAdapter;
import fr.polytech.bibliothequejds.R;
import fr.polytech.bibliothequejds.model.Game;

public class BibliothequeFragment extends Fragment
{
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        ListView list = root.findViewById(R.id.list);
        ArrayList<Game> games = new ArrayList<>();
        games.add(new Game("Gloomhaven", R.drawable.gloomhaven));
        games.add(new Game("Pandemic Legacy Saison 1", R.drawable.pandemic_legacy_s1));
        games.add(new Game("Terraforming Mars", R.drawable.terraforming_mars));
        games.add(new Game("ISSOUUUUUUUUUUUUUU", R.drawable.issou));
        CustomAdapter customAdapter = new CustomAdapter(this.getActivity(), games);
        list.setAdapter(customAdapter);
        AppCompatTextView actionBarTitle = getActivity().findViewById(R.id.tvTitle);
        actionBarTitle.setText(R.string.title_home);
        return root;      //Sets the view for the fragment
    }
}