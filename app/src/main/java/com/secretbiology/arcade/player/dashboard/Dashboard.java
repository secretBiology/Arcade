package com.secretbiology.arcade.player.dashboard;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.secretbiology.arcade.R;
import com.secretbiology.arcade.common.AppPrefs;
import com.secretbiology.arcade.common.BaseActivity;
import com.secretbiology.arcade.constants.ProfileIcon;
import com.secretbiology.arcade.constants.Species;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Dashboard extends BaseActivity {

    @BindView(R.id.dashboard_recycler)
    RecyclerView recyclerView;
    @BindView(R.id.dashboard_game_played)
    TextView gamePlayed;
    @BindView(R.id.dashboard_game_won)
    TextView gameWon;
    @BindView(R.id.dashboard_all_games)
    TextView gameAll;
    @BindView(R.id.dashboard_name)
    TextView name;
    @BindView(R.id.dashboard_icon)
    ImageView icon;

    private AppPrefs prefs;
    private DashboardAdapter adapter;
    private List<DashboardItem> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        ButterKnife.bind(this);
        prefs = new AppPrefs(getApplicationContext());
        itemList = new ArrayList<>();
        setUpItems();
        adapter = new DashboardAdapter(itemList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    private void setUpItems() {
        Species sp = Species.getByID(prefs.getSpecies());
        itemList.add(new DashboardItem(0, prefs.getEmail(), getString(R.string.email), R.drawable.icon_email));
        itemList.add(new DashboardItem(1, prefs.getUID(), getString(R.string.uid), R.drawable.icon_game_lobby));
        itemList.add(new DashboardItem(2, getString(sp.getName()), getString(R.string.species), sp.getIcon()));

        name.setText(prefs.getName());
        icon.setImageResource(ProfileIcon.getByID(prefs.getProfileIcon()).getIcon());
        gameAll.setText(String.valueOf(prefs.getNoOfGames()));
        gamePlayed.setText(String.valueOf(prefs.getGamePlayed()));
        gameWon.setText(String.valueOf(prefs.getGameWon()));
    }

    @Override
    protected int setNavigationMenu() {
        return R.id.nav_dashboard;
    }
}
