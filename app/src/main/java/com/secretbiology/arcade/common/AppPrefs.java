package com.secretbiology.arcade.common;

import android.content.Context;

import com.secretbiology.helpers.general.Preferences;

/**
 * Created by Dexter for Arcade .
 * Code is released under MIT license
 */

public class AppPrefs extends Preferences {

    private static final String EMAIL = "email";
    private static final String UID = "uid";
    private static final String NAME = "name";
    private static final String GENDER = "gender";
    private static final String PROFILE_ICON = "profileIcon";
    private static final String FIRST_TIME_SETUP = "firstTimeSetup";
    private static final String LAST_LOGIN = "lastLogin";
    private static final String LAST_SYNC = "lastSync";
    private static final String CURRENT_GAME = "currentGame";
    private static final String CURRENT_TABLE = "currentTable";


    public AppPrefs(Context context) {
        super(context);
    }

    public void setEmail(String email) {
        put(EMAIL, email);
    }

    public String getEmail() {
        return get(EMAIL, "useremail@company.com");
    }

    public void setUID(String uid) {
        put(UID, uid);
    }

    public String getUID() {
        return get(UID, "null");
    }

    public void setName(String name) {
        put(NAME, name);
    }

    public String getName() {
        return get(NAME, "Awesome Gamer");
    }

    public void setProfileIcon(int iconID) {
        put(PROFILE_ICON, iconID);
    }

    public int getProfileIcon() {
        return get(PROFILE_ICON, 0);
    }

    public void setGender(int gender) {
        put(GENDER, gender);
    }

    public int getGender() {
        return get(GENDER, Gender.OTHER.getID());
    }

    public boolean isFirstTimeSetupCompleted() {
        return get(FIRST_TIME_SETUP, false);
    }

    public void firstTimeSetupDone() {
        put(FIRST_TIME_SETUP, true);
    }

    public String getLastLogin() {
        return get(LAST_LOGIN, "N/A");
    }

    public void setLastLogin(String timestamp) {
        put(LAST_LOGIN, timestamp);
    }

    public String getLastSync() {
        return get(LAST_SYNC, "N/A");
    }

    public void setLastSync(String timestamp) {
        put(LAST_SYNC, timestamp);
    }

    public String getCurrentGame() {
        return get(CURRENT_GAME, "null");
    }

    public String getCurrentTable() {
        return get(CURRENT_TABLE, "null");
    }

    public boolean wasInGame() {
        return !getCurrentGame().equals("null");
    }

    public boolean wasInTable() {
        return !getCurrentTable().equals("null");
    }

    public void setCurrentGame(String gameID) {
        put(CURRENT_GAME, gameID);
    }

    public void setCurrentTable(String gameID) {
        put(CURRENT_TABLE, gameID);
    }

}
