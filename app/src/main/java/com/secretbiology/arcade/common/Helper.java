package com.secretbiology.arcade.common;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.secretbiology.arcade.R;

import static android.R.string.ok;

/**
 * Created by Dexter for Arcade .
 * Code is released under MIT license
 */

public class Helper {

    public static void showError(Activity activity, String message) {
        new AlertDialog.Builder(activity)
                .setTitle(activity.getString(R.string.oops))
                .setMessage(message)
                .setPositiveButton(ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }

}
