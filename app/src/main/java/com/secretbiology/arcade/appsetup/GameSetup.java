package com.secretbiology.arcade.appsetup;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.secretbiology.arcade.R;
import com.secretbiology.arcade.common.BaseActivity;
import com.secretbiology.helpers.general.General;

import butterknife.ButterKnife;

import static android.R.string.ok;

public class GameSetup extends BaseActivity {

    public static final String GAME_ID = "gameID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        showProxyError();

        String oldTable = getIntent().getStringExtra(GAME_ID);
        if (oldTable != null) {
            setUpJoin();
        } else {
            setUpNew();
        }
    }

    private void setUpJoin() {

    }

    private void setUpNew() {

    }

    @Override
    protected int setLayout() {
        return R.layout.game_setup;
    }

    @Override
    protected int setNavigationMenu() {
        return 0;
    }

    private void showProxyError() {
        if (General.isOnProxy()) {
            new AlertDialog.Builder(GameSetup.this)
                    .setTitle("Sorry!")
                    .setIcon(R.drawable.icon_game_lobby)
                    .setCancelable(false)
                    .setMessage(getString(R.string.proxy_warning))
                    .setPositiveButton(ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).show();
        }
    }
}
