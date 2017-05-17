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
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.secretbiology.arcade.R;
import com.secretbiology.arcade.Splash;
import com.secretbiology.arcade.player.Dashboard;
import com.secretbiology.helpers.general.General;

import butterknife.ButterKnife;

/**
 * Created by Dexter for Arcade .
 * Code is released under MIT license
 * <p>
 * Base activity which will take care of all navigation and smooth transition between activities.
 * This will handle navigation, however menu should be inflated in respective child activities.
 */

public abstract class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private FrameLayout baseLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout drawerLayout;
    public Toolbar toolbar; //Keep it public so that child activities can access

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.base_layout);
        toolbar = ButterKnife.findById(this, R.id.toolbar);
        baseLayout = ButterKnife.findById(this, R.id.base_view);
        navigationView = ButterKnife.findById(this, R.id.navigation_view);
        drawerLayout = ButterKnife.findById(this, R.id.drawer_layout);
        navigationView.setNavigationItemSelectedListener(this);
        setSupportActionBar(toolbar);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, 0, 0);
        drawerLayout.addDrawerListener(drawerToggle);
        changeHeader();
    }

    /**
     * Override all setContentView constructors so that we can just extend and use it like regular
     * appcombat activity.
     */
    @Override
    public void setContentView(int layoutResID) {
        if (baseLayout != null) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            View stubView = inflater.inflate(layoutResID, baseLayout, false);
            baseLayout.addView(stubView, params);
        }
    }

    @Override
    public void setContentView(View view) {
        if (baseLayout != null) {
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            baseLayout.addView(view, params);
        }
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        if (baseLayout != null) {
            baseLayout.addView(view, params);
        }
    }

    /**
     * Change header color and gradient.
     * All header titles and clicks can be handled from here.
     */
    private void changeHeader() {
        View header = navigationView.getHeaderView(0);
        GradientDrawable backgroundGradient = (GradientDrawable) header.getBackground();
        backgroundGradient.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
        backgroundGradient.setGradientCenter(10, 0);
        backgroundGradient.setColor(General.getColor(getApplicationContext(), R.color.colorPrimary));
        drawerToggle.syncState();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull final MenuItem item) {
        // Handle navigation view item clicks here.
        final Activity activity = this;
        if (activity.getClass() != getNavClass(item.getItemId())) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(activity, getNavClass(item.getItemId())));
                    animateTransition();
                }
            }, 300);
        }
        drawerLayout.closeDrawer(GravityCompat.START);
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

    /**
     * Useful for navigating between activities.
     *
     * @param navID : Navigation ID
     * @return : Class to change the activity
     */
    private Class getNavClass(int navID) {
        switch (navID) {
            case R.id.nav_dashboard:
                return Dashboard.class;
        }
        return Splash.class;
    }

    /**
     * Force child activity to set navigation ID.
     * Default is 0, which will lead to no effect on navigation item click.
     *
     * @return : ID of navigation menu item
     */
    protected abstract int setNavigationMenu();
}
