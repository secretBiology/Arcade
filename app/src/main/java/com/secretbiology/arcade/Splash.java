package com.secretbiology.arcade;

import android.content.Intent;
import android.os.Bundle;

import com.secretbiology.arcade.common.BaseActivity;
import com.secretbiology.arcade.player.Dashboard;

public class Splash extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        startActivity(new Intent(this, Dashboard.class));
    }

    @Override
    protected int setNavigationMenu() {
        return 0;
    }
}
