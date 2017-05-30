package com.secretbiology.arcade.network;

/**
 * Created by Rohit on 5/30/2017.
 */

public class CurrentInfo {
    private String status;
    private String currentGame;
    private String currentTable;
    private long lastOnline;

    public CurrentInfo() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCurrentGame() {
        return currentGame;
    }

    public void setCurrentGame(String currentGame) {
        this.currentGame = currentGame;
    }

    public String getCurrentTable() {
        return currentTable;
    }

    public void setCurrentTable(String currentTable) {
        this.currentTable = currentTable;
    }

    public long getLastOnline() {
        return lastOnline;
    }

    public void setLastOnline(long lastOnline) {
        this.lastOnline = lastOnline;
    }
}
