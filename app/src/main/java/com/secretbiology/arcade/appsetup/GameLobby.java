package com.secretbiology.arcade.appsetup;

import android.os.Bundle;

import com.secretbiology.arcade.R;
import com.secretbiology.arcade.common.BaseActivity;

public class GameLobby extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int setLayout() {
        return R.layout.game_lobby;
    }

    @Override
    protected int setNavigationMenu() {
        return 0;
    }
}
