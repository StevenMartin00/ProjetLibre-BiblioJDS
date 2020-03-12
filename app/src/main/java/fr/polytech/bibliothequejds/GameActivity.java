package fr.polytech.bibliothequejds;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import fr.polytech.bibliothequejds.R;

public class GameActivity extends AppCompatActivity {
    private float x1,x2;
    static final int MIN_DISTANCE = 150;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_game);

        //Recuperation de la source et du nom du jeu (depuis la liste)
        Intent intent = getIntent();
        String source = intent.getStringExtra("src");
        final String nomJeu = intent.getStringExtra("game"); //if it's a string you stored.
        //String categorie = intent.getStringExtra("category");


        //Elements impactes par la source bibli/search
        Button ajouter = findViewById(R.id.buttonAjouterJeu);
        Button retirer = findViewById(R.id.buttonRetirerJeu);
        Button add = findViewById(R.id.buttonAdd);
        Button remove = findViewById(R.id.buttonRemove);
        TextView TVlabelPartie = findViewById(R.id.lblNbPartie);
        TextView TVpartie = findViewById(R.id.textViewNbPartie);

        //NomJeu
        TextView TVnomJeu = findViewById(R.id.nomJeux);
        TVnomJeu.setText(nomJeu);

        //Note
        TextView TVnote = findViewById(R.id.note);
        TVnote.setText("9.5"); //TODO BDD

        //Image
        ImageView image = findViewById(R.id.imageView);
            //FO PAS FER CA !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! TODO
        if (nomJeu.equals("Terraforming Mars")){
            image.setImageResource(R.drawable.terraforming_mars);
        }
        if (nomJeu.equals("Pandemic Legacy Saison 1")){
            image.setImageResource(R.drawable.pandemic_legacy_s1);
        }
        if (nomJeu.equals("Gloomhaven")){
            image.setImageResource(R.drawable.gloomhaven);
        }
        if (nomJeu.equals("ISSOUUUUUUUUUUUUUU")){
            image.setImageResource(R.drawable.issou);
        }

        //TODO Ajouter l'image (je sais pas trop où elle est stockée)

        //Date
        TextView TVdate = findViewById(R.id.date);
        TVdate.setText("11/03/2020"); //TODO BDD

        //NbJoueur
        TextView TVnbJoueur = findViewById(R.id.textViewNbJoueur);
        TVnbJoueur.setText("2 - 8"); //TODO BDD

        //Duree Partie
        TextView TVdureePartie = findViewById(R.id.textViewDuree);
        TVdureePartie.setText("10" + " min"); //TODO BDD

        //Age
        TextView TVage = findViewById(R.id.textViewAge);
        TVage.setText("8 +" + " ans"); //TODO BDD

        //Complexite
        TextView TVcomplexite = findViewById(R.id.textViewComplexite);
        TVcomplexite.setText("4.5" + " / 5" ); //TODO BDD

        //Categorie
        TextView TVcategorie = findViewById(R.id.textViewCategorie);
        TVcategorie.setText("Stratégie, Coopératif" ); //TODO BDD

        //Nb Partie -- visible uniquement si jeu dans bibli
        if (source.equals("bibli")){
            TVpartie.setText("2" ); //TODO BDD
        }


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View root) {
                TextView TVpartie = findViewById(R.id.textViewNbPartie);
                int nbPartie = Integer.parseInt(TVpartie.getText().toString());
                nbPartie = nbPartie + 1;
                TVpartie.setText(String.valueOf(nbPartie));
                //TODO add save BDD
            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View root) {
                TextView TVpartie = findViewById(R.id.textViewNbPartie);
                int nbPartie = Integer.parseInt(TVpartie.getText().toString());
                nbPartie = nbPartie - 1;
                if (nbPartie >= 0){
                    TVpartie.setText(String.valueOf(nbPartie));
                    //TODO add save BDD
                }
            }
        });

        ajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View root) {
                //TODO BDD
                Toast.makeText(GameActivity.this , nomJeu + " a été ajouté à votre bibliothèque", Toast.LENGTH_SHORT).show();
            }
        });

        retirer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View root) {
                //TODO BDD
                Toast.makeText(GameActivity.this , nomJeu + " a été retiré de votre bibliothèque", Toast.LENGTH_SHORT).show();
            }
        });


        //Permert de cacher des boutons en fonction de la page d'origine
        if (source.equals("bibli")) {
            ajouter.setVisibility(View.GONE);
            retirer.setVisibility(View.VISIBLE);

            add.setVisibility(View.VISIBLE);
            remove.setVisibility(View.VISIBLE);
            TVlabelPartie.setVisibility(View.VISIBLE);
            TVpartie.setVisibility(View.VISIBLE);
        }

        if (source.equals("search")) {
            ajouter.setVisibility(View.VISIBLE);
            retirer.setVisibility(View.GONE);

            add.setVisibility(View.GONE);
            remove.setVisibility(View.GONE);
            TVlabelPartie.setVisibility(View.GONE);
            TVpartie.setVisibility(View.GONE);
        }



    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                float deltaX = x2 - x1;
                if (Math.abs(deltaX) > MIN_DISTANCE)
                {
                    //Toast.makeText(this, "left2right swipe", Toast.LENGTH_SHORT).show ();
                    finish();
                }
                break;
        }
        return super.onTouchEvent(event);
    }




}