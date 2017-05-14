package com.secretbiology.arcade;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.secretbiology.arcade.appsetup.dashboard.Dashboard;
import com.secretbiology.arcade.appsetup.Login;
import com.secretbiology.arcade.common.AppPrefs;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        Intent intent;
        if (new AppPrefs(getApplicationContext()).isFirstTimeSetupCompleted()) {
            intent = new Intent(this, Dashboard.class);
        } else {
            intent = new Intent(this, Login.class);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
