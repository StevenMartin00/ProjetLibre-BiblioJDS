package fr.polytech.bibliothequejds;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ExecutionException;

import fr.polytech.bibliothequejds.model.database.PlayedManager;
import fr.polytech.bibliothequejds.utils.BitmapFromUrlTask;

public class GameActivity extends AppCompatActivity {
    private float x1;
    private float x2;
    static final int MIN_DISTANCE = 150;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_game);

        //Initialisation du manager
        final PlayedManager playedManager = new PlayedManager(this.getApplicationContext());

        //Recuperation de l'utilisateur
        final SharedPreferences sharedPreferences = this.getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        final String username = sharedPreferences.getString("userLoggedIn", "");

        //Recuperation de la source et du nom du jeu (depuis la liste)
        Intent intent = getIntent();
        String source = intent.getStringExtra("src");
        final String nomJeu = intent.getStringExtra("game");
        String miniature = intent.getStringExtra("thumbnail");
        int age = intent.getIntExtra("age", 0);
        int minJoueurs = intent.getIntExtra("minPlayers", 0);
        int maxJoueurs = intent.getIntExtra("maxPlayers", 0);
        int tpsMoyen = intent.getIntExtra("meanTime", 0);
        float note = intent.getFloatExtra("notation", 0f);
        float difficulte = intent.getFloatExtra("difficulty", 0f);
        String anneePublication = intent.getStringExtra("yearOfPublication");
        String categorie = intent.getStringExtra("category");


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
        TVnote.setText(String.valueOf(note));

        //Image
        ImageView image = findViewById(R.id.imageView);
        Bitmap bitmap;
        try {
            bitmap = new BitmapFromUrlTask().execute(miniature).get();
            image.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 300, 300, false));
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Date
        TextView TVdate = findViewById(R.id.date);
        TVdate.setText(anneePublication);

        //NbJoueur
        TextView TVnbJoueur = findViewById(R.id.textViewNbJoueur);
        TVnbJoueur.setText(minJoueurs + " - " + maxJoueurs);

        //Duree Partie
        TextView TVdureePartie = findViewById(R.id.textViewDuree);
        TVdureePartie.setText(tpsMoyen + " min");

        //Age
        TextView TVage = findViewById(R.id.textViewAge);
        TVage.setText(age + "+ ans");

        //Complexite
        TextView TVcomplexite = findViewById(R.id.textViewComplexite);
        TVcomplexite.setText(difficulte + " / 5" );

        //Categorie
        TextView TVcategorie = findViewById(R.id.textViewCategorie);
        TVcategorie.setText(categorie);

        //Nb Partie -- visible uniquement si jeu dans bibli
        if (source.equals("bibli"))
        {
            TVpartie.setText(String.valueOf(playedManager.getNumberOfGamesPlayedByUsernameAndByGame(username, nomJeu)));
        }

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View root) {
                TextView TVpartie = findViewById(R.id.textViewNbPartie);
                int nbPartie = Integer.parseInt(TVpartie.getText().toString());
                nbPartie = nbPartie + 1;
                TVpartie.setText(String.valueOf(nbPartie));
                playedManager.updateNumberOfGamesPlayed(username, nomJeu, nbPartie);
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
                    playedManager.updateNumberOfGamesPlayed(username, nomJeu, nbPartie);
                }
            }
        });

        ajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View root) {
                playedManager.addPlayed(username, nomJeu, 0, 0);
                Toast.makeText(GameActivity.this , nomJeu + " a été ajouté à votre bibliothèque", Toast.LENGTH_SHORT).show();

            }
        });

        retirer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View root) {
                playedManager.deletePlayed(username, nomJeu);
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