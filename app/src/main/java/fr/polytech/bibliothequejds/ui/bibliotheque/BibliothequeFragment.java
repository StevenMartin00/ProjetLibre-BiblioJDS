package fr.polytech.bibliothequejds.ui.bibliotheque;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fr.polytech.bibliothequejds.CustomAdapter;
import fr.polytech.bibliothequejds.R;
import fr.polytech.bibliothequejds.model.Game;

public class BibliothequeFragment extends Fragment
{
    private ArrayList<Game> games;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        //ListView list = root.findViewById(R.id.list);
        games = new ArrayList<>();
        games.add(new Game("Gloomhaven", String.valueOf(R.drawable.gloomhaven)));
        games.add(new Game("Pandemic Legacy Saison 1", String.valueOf(R.drawable.pandemic_legacy_s1)));
        games.add(new Game("Terraforming Mars", String.valueOf(R.drawable.terraforming_mars)));
        games.add(new Game("ISSOUUUUUUUUUUUUUU", String.valueOf(R.drawable.issou)));
        /*CustomAdapter customAdapter = new CustomAdapter(this.getActivity(), games);
        list.setAdapter(customAdapter);*/

        AppCompatTextView actionBarTitle = getActivity().findViewById(R.id.tvTitle);
        actionBarTitle.setText(R.string.title_home);
        return root;      //Sets the view for the fragment
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        //Set up recycler view
        RecyclerView rv = getView().findViewById(R.id.rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rv.getContext(), layoutManager.getOrientation());
        dividerItemDecoration.setDrawable(getContext().getResources().getDrawable(R.drawable.custom_divider));
        rv.addItemDecoration(dividerItemDecoration);
        //Adapter
        CustomAdapter adapter = new CustomAdapter(this.getActivity(), games);
        rv.setAdapter(adapter);
    }
}