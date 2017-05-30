package com.secretbiology.arcade.network;

import java.util.HashMap;

/**
 * Created by Rohit on 5/30/2017.
 */

public class Player {

    private PlayerInfo playerInfo;
    private CurrentInfo currentInfo;
    private HashMap<String, PlayerStat> gameStats;

    public Player() {
    }

    public PlayerInfo getPlayerInfo() {
        return playerInfo;
    }

    public void setPlayerInfo(PlayerInfo playerInfo) {
        this.playerInfo = playerInfo;
    }

    public CurrentInfo getCurrentInfo() {
        return currentInfo;
    }

    public void setCurrentInfo(CurrentInfo currentInfo) {
        this.currentInfo = currentInfo;
    }

    public HashMap<String, PlayerStat> getGameStats() {
        return gameStats;
    }

    public void setGameStats(HashMap<String, PlayerStat> gameStats) {
        this.gameStats = gameStats;
    }
}
