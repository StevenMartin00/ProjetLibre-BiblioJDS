package fr.polytech.bibliothequejds.model;

import java.util.ArrayList;
import java.util.List;

public class Library
{
    private List<Game> gameList;
    private User owner;

    public Library()
    {
        this.gameList = new ArrayList<>();
        this.owner = new User();
    }

    public Library(List<Game> gameList, User owner) {
        this.gameList = gameList;
        this.owner = owner;
    }

    public List<Game> getGameList() {
        return gameList;
    }

    public void setGameList(List<Game> gameList) {
        this.gameList = gameList;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
