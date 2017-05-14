package com.secretbiology.arcade.appsetup;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.secretbiology.arcade.R;
import com.secretbiology.arcade.common.AppPrefs;
import com.secretbiology.arcade.common.BaseActivity;
import com.secretbiology.arcade.common.DatabaseConstants;
import com.secretbiology.arcade.common.Helper;
import com.secretbiology.arcade.network.models.GameDetails;
import com.secretbiology.helpers.general.General;
import com.secretbiology.helpers.general.Log;
import com.secretbiology.helpers.general.views.ScrollUpRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GameLobby extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.swipe_view)
    SwipeRefreshLayout layout;
    @BindView(R.id.game_list_recycler)
    ScrollUpRecyclerView recyclerView;
    @BindView(R.id.empty_text)
    TextView emptyText;

    private AppPrefs prefs;
    private List<GameDetails> games;
    private GameLobbyAdapter adapter;
    private DatabaseReference myRef;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setTitle(R.string.game_lobby);
        prefs = new AppPrefs(getApplicationContext());
        games = new ArrayList<>();
        adapter = new GameLobbyAdapter(games);
        progressDialog = new ProgressDialog(GameLobby.this);
        progressDialog.setMessage(getString(R.string.searching_games));
        myRef = FirebaseDatabase.getInstance().getReference(DatabaseConstants.LOBBY);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);

        if (General.isOnProxy()) {
            showError(getString(R.string.proxy_warning));
        }

        layout.setOnRefreshListener(this);

        if (prefs.wasInGame()) {
            //// TODO: 14-05-2017
        }
    }

    @Override
    protected int setLayout() {
        return R.layout.game_lobby;
    }

    @Override
    protected int setNavigationMenu() {
        return R.id.nav_lobby;
    }

    private void showError(String message) {
        new Helper().showErrorDialog(GameLobby.this, message);
    }

    @Override
    public void onRefresh() {
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                layout.setRefreshing(false);
                if (dataSnapshot.getValue() == null) {
                    games.clear();
                    adapter.notifyDataSetChanged();
                    emptyText.setText(getString(R.string.no_games_found));
                } else {
                    Log.inform(dataSnapshot);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                layout.setRefreshing(false);
                showError(databaseError.getMessage());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        layout.setRefreshing(true);
        onRefresh();
    }


}
