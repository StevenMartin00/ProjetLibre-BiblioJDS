package fr.polytech.bibliothequejds;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import fr.polytech.bibliothequejds.model.EncryptionUtils;
import fr.polytech.bibliothequejds.model.User;
import fr.polytech.bibliothequejds.model.database.UserManager;

public class LoginActivity extends AppCompatActivity {

    private UserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userManager = new UserManager(this.getApplicationContext());
        Button loginButton = findViewById(R.id.login_button);
        final EditText loginEditText = findViewById(R.id.login_editText);
        final EditText passwordEditText = findViewById(R.id.password_editText);
        Button goToRegisterButton = findViewById(R.id.goToRegister_button);

        final SharedPreferences sharedPreferences = this.getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        sharedPreferences.getString("userLoggedIn", "");

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User loginUser = userManager.getUser(loginEditText.getText().toString());
                if(loginUser != null)
                {
                    try
                    {
                        if(EncryptionUtils.decrypt(loginUser.getPassword()).equals(passwordEditText.getText().toString()))
                        {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("userLoggedIn", loginUser.getUsername());
                            editor.apply();

                            Intent intent = new Intent(LoginActivity.this, LibraryActivity.class);
                            intent.putExtra("classFrom", LoginActivity.class.toString());
                            intent.putExtra("loggedInUsername", loginUser.getUsername());
                            startActivity(intent);
                        }
                        else
                        {
                            passwordEditText.setError("Mot de passe invalide");
                            passwordEditText.requestFocus();
                        }
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                else
                {
                    loginEditText.setError("Nom d'utilisateur invalide");
                    loginEditText.requestFocus();
                }
            }
        });

        goToRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }
}
