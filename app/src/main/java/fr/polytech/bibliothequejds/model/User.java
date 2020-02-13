package fr.polytech.bibliothequejds.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class User
{
    private String username;
    private String password;
    private String birthDate;
    private String creationDate;

    public User()
    {
        this.username = "";
        this.password = "";
        this.birthDate = "";
        this.creationDate = "";
    }

    public User(String username, String password, String birthDate)
    {
        this.username = username;
        this.password = password;

        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("DD-MM-YYYY");

        this.birthDate = birthDate;
        this.creationDate = dateFormat.format(currentTime);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }
}
