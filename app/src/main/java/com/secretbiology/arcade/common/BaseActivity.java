package com.secretbiology.arcade.common;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

import com.secretbiology.arcade.R;
import com.secretbiology.helpers.general.General;

import butterknife.ButterKnife;

/**
 * Created by Dexter for Arcade .
 * Code is released under MIT license
 */

public abstract class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    NavigationView navigationView;
    DrawerLayout drawer;
    public Toolbar toolbar;
    public AppPrefs prefs;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_layout);
        navigationView = ButterKnife.findById(this, R.id.nav_view);
        toolbar = ButterKnife.findById(this, R.id.toolbar);
        drawer = ButterKnife.findById(this, R.id.drawer_layout);
        prefs = new AppPrefs(getApplicationContext());
        setUpDrawer();
        setUpActivity();
    }


    private void setUpDrawer() {
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        navigationView.inflateHeaderView(R.layout.base_header); //Header
        navigationView.inflateMenu(R.menu.drawer);
        View header = navigationView.getHeaderView(0);
        GradientDrawable backgroundGradient = (GradientDrawable) header.getBackground();
        backgroundGradient.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
        backgroundGradient.setGradientCenter(10, 0);
        backgroundGradient.setColor(General.getColor(getApplicationContext(), R.color.colorPrimary));
        navigationView.setNavigationItemSelectedListener(this);
        ((TextView) ButterKnife.findById(header, R.id.nav_header_title)).setText(prefs.getName());
        ((TextView) ButterKnife.findById(header, R.id.nav_header_subtitle)).setText(prefs.getEmail());
        ((ImageView) ButterKnife.findById(header, R.id.nav_header_icon)).setImageResource(Helper.getProfileIcon(prefs.getProfileIcon()));
        toggle.syncState();
    }

    private void setUpActivity() {
        ViewStub viewStub = ButterKnife.findById(this, R.id.base_view);
        viewStub.setLayoutResource(setLayout());
        viewStub.inflate();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull final MenuItem item) {
        // Handle navigation view item clicks here.
        final Activity activity = this;
        if (activity.getClass() != new Helper().getNavClass(item.getItemId())) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(activity, new Helper().getNavClass(item.getItemId())));
                    animateTransition();
                }
            }, 300);
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        MenuItem item = navigationView.getMenu().findItem(setNavigationMenu());
        if (item != null) {
            item.setChecked(true);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            animateTransition();
        }
    }

    public void animateTransition() {
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    protected abstract int setLayout();

    protected abstract int setNavigationMenu();
}
