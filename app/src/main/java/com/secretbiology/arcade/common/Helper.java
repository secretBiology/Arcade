package com.secretbiology.arcade.common;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.secretbiology.arcade.R;
import com.secretbiology.arcade.Splash;
import com.secretbiology.arcade.appsetup.GameLobby;
import com.secretbiology.arcade.appsetup.dashboard.Dashboard;

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

    public Class getNavClass(int navID) {
        switch (navID) {
            case R.id.nav_dashboard:
                return Dashboard.class;
            case R.id.nav_lobby:
                return GameLobby.class;
        }
        return Splash.class;
    }

    public int getGenderIcon(Context context, String string) {
        String s = string.toLowerCase().trim();
        if (s.equals(formatString(context, R.string.child))) {
            return R.drawable.emo_baby;
        } else if (s.equals(formatString(context, R.string.male))) {
            return R.drawable.emo_boy;
        } else if (s.equals(formatString(context, R.string.female))) {
            return R.drawable.emo_girl;
        } else if (s.equals(formatString(context, R.string.alien))) {
            return R.drawable.emo_alien;
        } else if (s.equals(formatString(context, R.string.robot))) {
            return R.drawable.emo_robot;
        } else {
            return R.drawable.emo_unknown;
        }
    }

    private String formatString(Context context, int id) {
        return context.getString(id).toLowerCase().trim();
    }
}
