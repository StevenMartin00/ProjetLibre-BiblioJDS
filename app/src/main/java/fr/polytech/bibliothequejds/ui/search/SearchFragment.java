package fr.polytech.bibliothequejds.ui.search;

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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.polytech.bibliothequejds.SearchAdapter;
import fr.polytech.bibliothequejds.R;
import fr.polytech.bibliothequejds.model.Game;
import fr.polytech.bibliothequejds.model.database.CategoryManager;
import fr.polytech.bibliothequejds.model.database.GameManager;
import fr.polytech.bibliothequejds.utils.JsonParserTask;

import static android.content.Context.MODE_PRIVATE;

public class SearchFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private List<Game> games;
    private ProgressBar pbLoadingGames;
    private TextView tvLoadingGames;
    private GameManager gameManager;
    private CategoryManager categoryManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel = ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_search, container, false);

        AppCompatTextView actionBarTitle = getActivity().findViewById(R.id.tvTitle);
        actionBarTitle.setText(R.string.title_search);

        TextView lblUp = root.findViewById(R.id.lblTextUp);
        TextView lblDown = root.findViewById(R.id.lblTextDown);
        final ConstraintLayout open = root.findViewById(R.id.layoutFiltreOpen);
        final ConstraintLayout close = root.findViewById(R.id.layoutFiltreClose);
        open.setVisibility(View.GONE);

        lblUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View root) {
                open.setVisibility(View.GONE);
                close.setVisibility(View.VISIBLE);
            }
        });

        lblDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View root) {
                open.setVisibility(View.VISIBLE);
                close.setVisibility(View.GONE);
            }
        });

        pbLoadingGames = root.findViewById(R.id.pbLoadingGames);
        pbLoadingGames.setVisibility(View.GONE);
        tvLoadingGames = root.findViewById(R.id.tvLoadingGames);
        tvLoadingGames.setVisibility(View.GONE);
        gameManager = new GameManager(this.getActivity().getApplicationContext());
        categoryManager = new CategoryManager(this.getActivity().getApplicationContext());

        games = gameManager.getAllGames();
        return root;
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
        SearchAdapter adapter = new SearchAdapter(this.getActivity(), games);
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