package com.secretbiology.arcade.common;

import android.content.Context;

import com.secretbiology.arcade.player.Player;
import com.secretbiology.helpers.general.Preferences;

import java.util.Calendar;

/**
 * Created by Dexter for Arcade .
 * Code is released under MIT license
 */

public class AppPrefs extends Preferences {

    private static String EMAIL = "email";
    private static String NAME = "name";
    private static String UID = "uid";
    private static String SPECIES = "species";
    private static String PROFILE_ICON = "profileIcon";
    private static String NO_OF_GAMES = "noOfGames";
    private static String GAME_PLAYED = "gamePlayed";
    private static String GAME_WON = "gameWon";
    private static String LAST_ONLINE = "lastOnline";
    private static String IS_AUTHENTICATED = "isAuthenticated";
    private static String MESSAGE_TOKEN = "message_token";

    public AppPrefs(Context context) {
        super(context);
    }

    public void updateInfo(Player player) {
        setEmail(player.getEmail());
        setUID(player.getUid());
        setGamePlayed(player.getGamePlayed());
        setGameWon(player.getGameWon());
        setNoOfGames(player.getNoOfGames());
        setName(player.getName());
        setProfileIcon(player.getIconID());
        setSpecies(player.getSpeciesID());
        if (player.getLastOnline() != 0) {
            setLastOnline(player.getLastOnline());
        }
        if (player.getMessageToken() != null) {
            setMessageToken(player.getMessageToken());
        }
    }

    public void setEmail(String email) {
        put(EMAIL, email);
    }

    public String getEmail() {
        return get(EMAIL, "email@company.com");
    }

    public void setUID(String uid) {
        put(UID, uid);
    }

    public String getUID() {
        return get(UID, "uniqueidentificationno");
    }

    public void setName(String name) {
        put(NAME, name);
    }

    public String getName() {
        return get(NAME, "Gamer");
    }

    public void setProfileIcon(int iconID) {
        put(PROFILE_ICON, iconID);
    }

    public int getProfileIcon() {
        return get(PROFILE_ICON, 0);
    }

    public void setSpecies(int speciesID) {
        put(SPECIES, speciesID);
    }

    public int getSpecies() {
        return get(SPECIES, 0);
    }

    public void setNoOfGames(int games) {
        put(NO_OF_GAMES, games);
    }

    public int getNoOfGames() {
        return get(NO_OF_GAMES, 0);
    }

    public void setGamePlayed(int gamePlayed) {
        put(GAME_PLAYED, gamePlayed);
    }

    public int getGamePlayed() {
        return get(GAME_PLAYED, 0);
    }

    public void setGameWon(int won) {
        put(GAME_WON, won);
    }

    public int getGameWon() {
        return get(GAME_WON, 0);
    }

    public void setLastOnline(long time) {
        put(LAST_ONLINE, time);
    }

    public long getLastOnline() {
        return get(LAST_ONLINE, Calendar.getInstance().getTimeInMillis());
    }

    public void userLoggedIn() {
        put(IS_AUTHENTICATED, true);
    }

    public boolean isAuthenticated() {
        return get(IS_AUTHENTICATED, false);
    }

    public void setMessageToken(String token) {
        put(MESSAGE_TOKEN, token);
    }

    public String getMessageToken() {
        return getString(MESSAGE_TOKEN);
    }
}
