package com.secretbiology.arcade.constants;

import com.secretbiology.arcade.R;

/**
 * Created by Dexter for Arcade .
 * Code is released under MIT license
 */

public enum ProfileIcon {

    HAPPY(0, R.drawable.emo_face1),
    MISCHIEF(1, R.drawable.emo_face2),
    MONSTER(2, R.drawable.emo_face3),
    GHOST(3, R.drawable.emo_face4),
    HORROR(4, R.drawable.emo_face5),
    FOX(5, R.drawable.emo_face6),
    FROG(6, R.drawable.emo_face7),
    PENGUIN(7, R.drawable.emo_face8),
    GORILLA(8, R.drawable.emo_face9),
    SHARK(9, R.drawable.emo_face10),
    BUTTERFLY(10, R.drawable.emo_face11),
    DEER(11, R.drawable.emo_face12),
    BEETLE(12, R.drawable.emo_face13),
    RABBIT(13, R.drawable.emo_face14),
    DOG(14, R.drawable.emo_face15),
    SPIDER(15, R.drawable.emo_face16),
    OCTOPUS(16, R.drawable.emo_face17),
    LION(17, R.drawable.emo_face18);

    private int ID;
    private int icon;

    ProfileIcon(int ID, int icon) {
        this.ID = ID;
        this.icon = icon;
    }

    public int getID() {
        return ID;
    }

    public int getIcon() {
        return icon;
    }

    public static ProfileIcon getByID(int id) {
        for (ProfileIcon icon : ProfileIcon.values()) {
            if (icon.getID() == id) {
                return icon;
            }
        }
        return HAPPY;
    }
}
