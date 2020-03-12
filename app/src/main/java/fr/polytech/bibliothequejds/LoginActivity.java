package fr.polytech.bibliothequejds;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        Button loginButton = findViewById(R.id.login_button);
        final EditText loginEditText = findViewById(R.id.login_editText);
        final EditText passwordEditText = findViewById(R.id.password_editText);
        Button goToRegisterButton = findViewById(R.id.goToRegister_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(loginEditText.getText().toString().equals("admin") && passwordEditText.getText().toString().equals("admin"))
                {
                    Toast.makeText(getApplicationContext(),"Redirecting to Lib Activity...",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, LibraryActivity.class));
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Wrong Credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });

        goToRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Redirecting to RegistrActivity...",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }
}
