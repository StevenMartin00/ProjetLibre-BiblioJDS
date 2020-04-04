package fr.polytech.bibliothequejds.ui.bibliotheque;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.polytech.bibliothequejds.LibraryAdapter;
import fr.polytech.bibliothequejds.R;
import fr.polytech.bibliothequejds.model.Game;
import fr.polytech.bibliothequejds.model.Played;
import fr.polytech.bibliothequejds.model.database.CategoryManager;
import fr.polytech.bibliothequejds.model.database.GameManager;
import fr.polytech.bibliothequejds.model.database.PlayedManager;
import fr.polytech.bibliothequejds.utils.JsonParserTask;

import static android.content.Context.MODE_PRIVATE;

public class BibliothequeFragment extends Fragment
{
    private List<Game> gamesPlayed;
    private ProgressBar pbLoadingGames;
    private TextView tvLoadingGames;
    private GameManager gameManager;
    private CategoryManager categoryManager;
    private PlayedManager playedManager;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        //ListView list = root.findViewById(R.id.list);

        /*games.add(new Game("Gloomhaven", String.valueOf(R.drawable.gloomhaven)));
        games.add(new Game("Pandemic Legacy Saison 1", String.valueOf(R.drawable.pandemic_legacy_s1)));
        games.add(new Game("Terraforming Mars", String.valueOf(R.drawable.terraforming_mars)));
        games.add(new Game("ISSOUUUUUUUUUUUUUU", String.valueOf(R.drawable.issou)));*/


        AppCompatTextView actionBarTitle = getActivity().findViewById(R.id.tvTitle);
        actionBarTitle.setText(R.string.title_home);

        pbLoadingGames = root.findViewById(R.id.pbLoadingGames);
        pbLoadingGames.setVisibility(View.GONE);
        tvLoadingGames = root.findViewById(R.id.tvLoadingGames);
        tvLoadingGames.setVisibility(View.GONE);
        gameManager = new GameManager(this.getActivity().getApplicationContext());
        categoryManager = new CategoryManager(this.getActivity().getApplicationContext());
        playedManager = new PlayedManager(this.getActivity().getApplicationContext());

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        gamesPlayed = playedManager.getGamesPlayedByUsername(sharedPreferences.getString("userLoggedIn", ""));

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
        LibraryAdapter adapter = new LibraryAdapter(this.getActivity(), gamesPlayed);
        rv.setAdapter(adapter);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        boolean firstRun = sharedPreferences.getBoolean("firstRun", true);
        firstRun = false;
        if(firstRun)
        {
            //Get the list of games in Json from boardgameatlas
            new JsonParserTask(this.getActivity(), gameManager, categoryManager).execute();

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("firstRun", false);
            editor.apply();
        }
    }
}