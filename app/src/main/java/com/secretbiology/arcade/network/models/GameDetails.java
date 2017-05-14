package com.secretbiology.arcade.network.models;

/**
 * Created by Dexter for Arcade .
 * Code is released under MIT license
 */

public class GameDetails {

    private String gameID;
    private String gameName;
    private String gameCreator;
    private String gameCreationTime;
    private int numberOfPlayers;
    private int maxPlayers;

    public GameDetails() {
    }

    public String getGameID() {
        return gameID;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getGameCreator() {
        return gameCreator;
    }

    public void setGameCreator(String gameCreator) {
        this.gameCreator = gameCreator;
    }

    public String getGameCreationTime() {
        return gameCreationTime;
    }

    public void setGameCreationTime(String gameCreationTime) {
        this.gameCreationTime = gameCreationTime;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }
}
