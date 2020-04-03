package fr.polytech.bibliothequejds.ui.profile;

import android.content.Intent;
import android.media.PlaybackParams;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import java.util.List;

import fr.polytech.bibliothequejds.R;
import fr.polytech.bibliothequejds.model.Game;
import fr.polytech.bibliothequejds.model.User;
import fr.polytech.bibliothequejds.model.database.GameManager;
import fr.polytech.bibliothequejds.model.database.PlayedManager;
import fr.polytech.bibliothequejds.model.database.UserManager;

public class ProfileFragment extends Fragment {

    private UserManager userManager;
    private PlayedManager playedManager;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        AppCompatTextView actionBarTitle = getActivity().findViewById(R.id.tvTitle);
        actionBarTitle.setText(R.string.title_profile);

        userManager = new UserManager(this.getActivity().getApplicationContext());
        playedManager = new PlayedManager(this.getActivity().getApplicationContext());

        Intent intent = getActivity().getIntent();
        String username = intent.getStringExtra("loggedInUsername");
        User loggedInUser = userManager.getUser(username);

        //Nom Utilisateur
        TextView TVnomUtilisateur = root.findViewById(R.id.nomUtilisateur);
        TVnomUtilisateur.setText(loggedInUser.getUsername());

        //Date de Naissance
        TextView TVdateNaissance = root.findViewById(R.id.textViewDate);
        TVdateNaissance.setText(loggedInUser.getBirthDate());

        //Complexite
        TextView TVcomplexite = root.findViewById(R.id.textViewComplexite);
        List<Game> gamesPlayed = playedManager.getGamesPlayedByUsername(username);

        float sumDifficulty = 0f;
        float avgDifficulty = 0f;
        int gameCounter = 0;
        for(Game game : gamesPlayed)
        {
            sumDifficulty += game.getDifficulty();
            gameCounter++;
        }

        if(gameCounter != 0)
            avgDifficulty = sumDifficulty / gameCounter;

        TVcomplexite.setText(String.valueOf(avgDifficulty));

        //Categorie
        TextView TVcategorie = root.findViewById(R.id.textViewCategorie);
        String mostCommonCategory = playedManager.getMostCommonCategory(gamesPlayed);
        TVcategorie.setText(mostCommonCategory);

        //Nombre de Partie
        TextView TVnbPartie= root.findViewById(R.id.textViewPartie);
        int nbOfGamesPlayed = 0;
        for(Game game : gamesPlayed)
        {
            nbOfGamesPlayed += playedManager.getNumberOfGamesPlayedByUsernameAndByGame(username, game.getGameName());
        }
        TVnbPartie.setText(String.valueOf(nbOfGamesPlayed));


        Button button = root.findViewById(R.id.buttonRecommandation);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View root) {
                Toast.makeText(getActivity() , "Recommandation", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }

}