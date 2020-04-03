package fr.polytech.bibliothequejds.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import fr.polytech.bibliothequejds.R;
import fr.polytech.bibliothequejds.model.Category;
import fr.polytech.bibliothequejds.model.Game;
import fr.polytech.bibliothequejds.model.database.CategoryManager;
import fr.polytech.bibliothequejds.model.database.GameManager;

public class SearchFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private GameManager gameManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel = ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_search, container, false);
        //TODO: mettre les trucs ici
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

        //TESTING JSON PARSING -> OK
        gameManager = new GameManager(this.getActivity().getApplicationContext());
        CategoryManager categoryManager = new CategoryManager(this.getActivity().getApplicationContext());
        for(Game game : gameManager.getAllGames())
        {
            System.out.println(game.getGameName());
        }

        for(Category category : categoryManager.getAllCategories())
        {
            System.out.println(category.getCategoryName());
        }
        return root;
    }
}