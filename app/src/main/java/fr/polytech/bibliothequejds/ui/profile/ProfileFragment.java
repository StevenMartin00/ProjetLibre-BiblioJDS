package fr.polytech.bibliothequejds.ui.profile;

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

import fr.polytech.bibliothequejds.R;

public class ProfileFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        //TODO: les trucs à mettre ici
        AppCompatTextView actionBarTitle = getActivity().findViewById(R.id.tvTitle);
        actionBarTitle.setText(R.string.title_profile);

        //Nom Utilisateur
        TextView TVnomUtilisateur = root.findViewById(R.id.nomUtilisateur);
        TVnomUtilisateur.setText("Damien29");

        //Date de Naissance
        TextView TVdateNaissance = root.findViewById(R.id.textViewDate);
        TVdateNaissance.setText("13/05/1997");

        //Complexite
        TextView TVcomplexite = root.findViewById(R.id.textViewComplexite);
        TVcomplexite.setText("4" + "/5");

        //Categorie
        TextView TVcategorie = root.findViewById(R.id.textViewCategorie);
        TVcategorie.setText("categorie1 ,categorie22");

        //Nombre de Partie
        TextView TVnbPartie= root.findViewById(R.id.textViewPartie);
        TVnbPartie.setText("52");


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