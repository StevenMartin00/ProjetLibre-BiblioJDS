package fr.polytech.bibliothequejds.model;

public class Played
{
    private User player;
    private Game gamePlayed;
    private double score;
    private int numberOfGamesPlayed;

    public Played()
    {
        this.player = new User();
        this.gamePlayed = new Game();
        this.score = 0d;
        this.numberOfGamesPlayed = 0;
    }

    public Played(User player, Game gamePlayed, double score) {
        this.player = player;
        this.gamePlayed = gamePlayed;
        this.score = score;
        this.numberOfGamesPlayed += 1;
    }

    public User getPlayer() {
        return player;
    }

    public void setPlayer(User player) {
        this.player = player;
    }

    public Game getGamePlayed() {
        return gamePlayed;
    }

    public void setGamePlayed(Game gamePlayed) {
        this.gamePlayed = gamePlayed;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public int getNumberOfGamesPlayed() {
        return numberOfGamesPlayed;
    }

    public void setNumberOfGamesPlayed(int numberOfGamesPlayed) {
        this.numberOfGamesPlayed = numberOfGamesPlayed;
    }
}
