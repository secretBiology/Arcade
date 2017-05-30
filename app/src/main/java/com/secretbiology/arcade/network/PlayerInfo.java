package com.secretbiology.arcade.network;

/**
 * Created by Rohit on 5/30/2017.
 */

public class PlayerInfo {
    private String name;
    private String email;
    private String uid;
    private String messageToken;
    private int profileIconID;
    private int speciesID;
    private int role;

    public PlayerInfo() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMessageToken() {
        return messageToken;
    }

    public void setMessageToken(String messageToken) {
        this.messageToken = messageToken;
    }

    public int getProfileIconID() {
        return profileIconID;
    }

    public void setProfileIconID(int profileIconID) {
        this.profileIconID = profileIconID;
    }

    public int getSpeciesID() {
        return speciesID;
    }

    public void setSpeciesID(int speciesID) {
        this.speciesID = speciesID;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
