package com.secretbiology.arcade.network;

/**
 * Created by Rohit on 5/30/2017.
 */

public class PlayerStat {

    private int gameParticipated;
    private int gamePlayed;
    private int gameWon;
    private int gameTied;
    private int gameCancelled;
    private String gameID;

    public PlayerStat() {
    }

    public String getGameID() {
        return gameID;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    public int getGameParticipated() {
        return gameParticipated;
    }

    public void setGameParticipated(int gameParticipated) {
        this.gameParticipated = gameParticipated;
    }

    public int getGamePlayed() {
        return gamePlayed;
    }

    public void setGamePlayed(int gamePlayed) {
        this.gamePlayed = gamePlayed;
    }

    public int getGameWon() {
        return gameWon;
    }

    public void setGameWon(int gameWon) {
        this.gameWon = gameWon;
    }

    public int getGameTied() {
        return gameTied;
    }

    public void setGameTied(int gameTied) {
        this.gameTied = gameTied;
    }

    public int getGameCancelled() {
        return gameCancelled;
    }

    public void setGameCancelled(int gameCancelled) {
        this.gameCancelled = gameCancelled;
    }
}
