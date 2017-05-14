package com.secretbiology.arcade.common;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

import com.secretbiology.arcade.R;

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
}
