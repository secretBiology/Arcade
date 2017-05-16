package com.secretbiology.arcade.user;

import android.os.Bundle;

import com.secretbiology.arcade.R;
import com.secretbiology.arcade.common.BaseActivity;

public class Dashboard extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
    }

    @Override
    protected int setNavigationMenu() {
        return R.id.nav_dashboard;
    }
}
