package com.secretbiology.arcade.constants;

import com.secretbiology.arcade.R;

/**
 * Created by Dexter for Arcade .
 * Code is released under MIT license
 */

public enum Species {

    OTHER(0, R.drawable.emo_unknown, R.string.other),
    CHILD(1, R.drawable.emo_baby, R.string.child),
    MALE(2, R.drawable.emo_boy, R.string.male),
    FEMALE(3, R.drawable.emo_girl, R.string.female),
    ROBOT(4, R.drawable.emo_robot, R.string.robot),
    ALIEN(5, R.drawable.emo_alien, R.string.alien);

    private int ID;
    private int icon;
    private int name;

    Species(int ID, int icon, int name) {
        this.ID = ID;
        this.icon = icon;
        this.name = name;
    }

    public int getID() {
        return ID;
    }

    public int getIcon() {
        return icon;
    }

    public int getName() {
        return name;
    }

    public static Species getByID(int id) {
        for (Species species : Species.values()) {
            if (species.getID() == id) {
                return species;
            }
        }
        return OTHER;
    }
}
