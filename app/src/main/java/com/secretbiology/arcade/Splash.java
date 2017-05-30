package com.secretbiology.arcade;

import android.content.Intent;
import android.os.Bundle;

import com.secretbiology.arcade.background.BackgroundService;
import com.secretbiology.arcade.common.AppPrefs;
import com.secretbiology.arcade.common.BaseActivity;
import com.secretbiology.arcade.player.dashboard.Dashboard;
import com.secretbiology.arcade.player.login.Login;

public class Splash extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        if (new AppPrefs(getApplicationContext()).isAuthenticated()) {
            BackgroundService.updatePresence(getApplicationContext());
            startActivity(new Intent(this, Login.class));
        } else {
            startActivity(new Intent(this, Login.class));
        }
        finish();
    }

    @Override
    protected int setNavigationMenu() {
        return 0;
    }
}
