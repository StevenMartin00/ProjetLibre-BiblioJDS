package fr.polytech.bibliothequejds;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import fr.polytech.bibliothequejds.model.User;
import fr.polytech.bibliothequejds.model.database.UserManager;

public class RegisterActivity extends AppCompatActivity
{
    private UserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        userManager = new UserManager(this.getApplicationContext());
        final EditText registerLogin = findViewById(R.id.register_editText);
        final EditText registerPassword = findViewById(R.id.register_password_editText);
        final EditText registerConfirmPassword = findViewById(R.id.register_confirmPassword_editText);
        final EditText registerBirthDate = findViewById(R.id.register_birthDate_editText);
        Button registerButton = findViewById(R.id.register_button);
        ImageButton birthDatePickerButton = findViewById(R.id.birthDateButton);

        //Disables input in birth date edit text
        registerBirthDate.setInputType(InputType.TYPE_NULL);
        registerBirthDate.setSingleLine(false);

        birthDatePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(RegisterActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                if(month < 9 && day < 10)
                                    registerBirthDate.setText("0" + day + "/0" + (month + 1) + "/" + year);
                                else if(month < 9 && day > 10)
                                    registerBirthDate.setText(day + "/0" + (month + 1) + "/" + year);
                                else
                                    registerBirthDate.setText(day + "/" + (month + 1) + "/" + year);
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(registerBirthDate.getText().toString().trim().isEmpty() || registerConfirmPassword.getText().toString().trim().isEmpty() || registerPassword.getText().toString().trim().isEmpty() || registerLogin.getText().toString().trim().isEmpty())
                {
                    if(registerBirthDate.getText().toString().trim().isEmpty())
                    {
                        registerBirthDate.setError("Date de naissance obligatoire");
                        if(!registerLogin.hasFocus() || !registerConfirmPassword.hasFocus() || !registerPassword.hasFocus())
                            registerBirthDate.requestFocus();
                    }

                    if(registerConfirmPassword.getText().toString().trim().isEmpty())
                    {
                        registerConfirmPassword.setError("Confirmation de mot de passe obligatoire");

                        if (!registerLogin.hasFocus() || !registerPassword.hasFocus() || !registerBirthDate.hasFocus())
                            registerConfirmPassword.requestFocus();
                    }

                    if(registerPassword.getText().toString().trim().isEmpty())
                    {
                        registerPassword.setError("Mot de passe obligatoire");

                        if(!registerLogin.hasFocus() || !registerConfirmPassword.hasFocus() || !registerBirthDate.hasFocus())
                            registerPassword.requestFocus();
                    }

                    if(registerLogin.getText().toString().trim().isEmpty())
                    {
                        registerLogin.setError("Nom d'utilisateur obligatoire");
                        if(!registerPassword.hasFocus() || !registerConfirmPassword.hasFocus() || !registerBirthDate.hasFocus())
                            registerLogin.requestFocus();
                    }
                }
                else
                {
                    if (registerConfirmPassword.getText().toString().trim().equals(registerPassword.getText().toString().trim()))
                    {
                        try {
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                            Date date = sdf.parse(registerBirthDate.getText().toString());
                            Date currentDate = new Date();
                            if(date.before(currentDate) && !sdf.format(date).equals(sdf.format(currentDate))) {
                                User registeredUser = new User(registerLogin.getText().toString().trim(), registerPassword.getText().toString().trim(), registerBirthDate.getText().toString());//FORMAT DATE
                                userManager.addUser(registeredUser);
                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                            }
                            else
                            {
                                registerBirthDate.setError("La date de naissance ne peut pas correspondre à aujourd'hui ou plus tard");
                                if (!registerLogin.hasFocus() || !registerPassword.hasFocus() || !registerConfirmPassword.hasFocus())
                                    registerBirthDate.requestFocus();
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }
                    else
                    {
                        registerConfirmPassword.setError("Ne correspond pas au mot de passe entré");
                        if (!registerLogin.hasFocus() || !registerPassword.hasFocus() || !registerBirthDate.hasFocus())
                            registerConfirmPassword.requestFocus();
                    }
                }
            }
        });

    }
}
