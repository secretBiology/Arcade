package com.secretbiology.arcade.common;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.annotation.Nullable;

import com.secretbiology.arcade.R;
import com.secretbiology.arcade.Splash;
import com.secretbiology.arcade.appsetup.dashboard.Dashboard;
import com.secretbiology.arcade.appsetup.lobby.GameLobby;

import static android.R.string.ok;

/**
 * Created by Dexter for Arcade .
 * Code is released under MIT license
 */

public class Helper {

    public void showErrorDialog(Activity activity, String message) {
        new AlertDialog.Builder(activity)
                .setTitle(activity.getString(R.string.oops))
                .setMessage(message)
                .setPositiveButton(ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();
    }

    public static int getProfileIcon(int id) {
        for (ProfileIcons icons : ProfileIcons.values()) {
            if (icons.getID() == id) {
                return icons.getIcon();
            }
        }
        return R.drawable.emo_face1;
    }

    Class getNavClass(int navID) {
        switch (navID) {
            case R.id.nav_dashboard:
                return Dashboard.class;
            case R.id.nav_lobby:
                return GameLobby.class;
        }
        return Splash.class;
    }

    public static Gender getGenderByIcon(int iconID) {
        for (Gender g : Gender.values()) {
            if (g.getIcon() == iconID) {
                return g;
            }
        }
        return Gender.OTHER;
    }

    public static Gender getGenderByID(int id) {
        for (Gender g : Gender.values()) {
            if (g.getID() == id) {
                return g;
            }
        }
        return Gender.OTHER;
    }

    @Nullable
    public static Game getGameByID(int id) {
        for (Game g : Game.values()) {
            if (g.getID() == id) {
                return g;
            }
        }
        return null;
    }
}
