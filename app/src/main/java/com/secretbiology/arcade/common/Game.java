package com.secretbiology.arcade.common;

import com.secretbiology.arcade.R;

/**
 * Created by Dexter for Arcade .
 * Code is released under MIT license
 */

public enum Game {

    BATTLESHIP(1, R.string.battleship, R.string.battleship_details, R.drawable.battleship, R.string.battleship_url);

    private int ID;
    private int name;
    private int details;
    private int icon;
    private int url;

    Game(int ID, int name, int details, int icon, int url) {
        this.ID = ID;
        this.name = name;
        this.details = details;
        this.icon = icon;
        this.url = url;
    }

    public int getID() {
        return ID;
    }

    public int getName() {
        return name;
    }

    public int getDetails() {
        return details;
    }

    public int getIcon() {
        return icon;
    }

    public int getUrl() {
        return url;
    }
}
