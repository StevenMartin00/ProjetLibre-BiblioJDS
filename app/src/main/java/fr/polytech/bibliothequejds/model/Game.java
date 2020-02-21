package fr.polytech.bibliothequejds.model;

public class Game
{
    private String gameName;
    private String thumbnail;
    private int minPlayers;
    private int maxPlayers;
    private int meanTime;
    private double notation;
    private int age;
    private String difficulty;
    private String yearOfPublication;
    private String categoryName;

    public Game()
    {
        this.gameName = "";
        this.thumbnail = "";
        this.minPlayers = 0;
        this.maxPlayers = 0;
        this.meanTime = 0;
        this.notation = 0d;
        this.age = 0;
        this.difficulty = "";
        this.yearOfPublication = "";
        this.categoryName = "";
    }

    public Game(String gameName, String thumbnail)
    {
        this.gameName = gameName;
        this.thumbnail = thumbnail;
        this.minPlayers = 0;
        this.maxPlayers = 0;
        this.meanTime = 0;
        this.notation = 0d;
        this.age = 0;
        this.difficulty = "";
        this.yearOfPublication = "";
        this.categoryName = "";
    }

    public Game(String gameName, String thumbnail, int minPlayers, int maxPlayers, int meanTime, double notation, int age, String difficulty, String yearOfPublication, String categoryName)
    {
        this.gameName = gameName;
        this.thumbnail = thumbnail;
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
        this.meanTime = meanTime;
        this.notation = notation;
        this.age = age;
        this.difficulty = difficulty;
        this.yearOfPublication = yearOfPublication;
        this.categoryName = categoryName;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public int getMinPlayers() {
        return minPlayers;
    }

    public void setMinPlayers(int minPlayers) {
        this.minPlayers = minPlayers;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public int getMeanTime() {
        return meanTime;
    }

    public void setMeanTime(int meanTime) {
        this.meanTime = meanTime;
    }

    public double getNotation() {
        return notation;
    }

    public void setNotation(double notation) {
        this.notation = notation;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getYearOfPublication() {
        return yearOfPublication;
    }

    public void setYearOfPublication(String yearOfPublication) {
        this.yearOfPublication = yearOfPublication;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
