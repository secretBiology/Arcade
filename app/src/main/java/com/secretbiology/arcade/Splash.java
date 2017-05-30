package com.secretbiology.arcade;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.secretbiology.arcade.common.BaseActivity;
import com.secretbiology.arcade.network.Player;
import com.secretbiology.arcade.network.PlayerInfo;
import com.secretbiology.helpers.general.Log;

public class Splash extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        PlayerInfo i = new PlayerInfo();
        i.setEmail("asdasd");
        i.setName("ASHg jhas");
        Player p1 = new Player();
        p1.setPlayerInfo(i);

        FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Player p = dataSnapshot.getValue(Player.class);
                        Log.inform(p.getPlayerInfo().getEmail());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                }
        );

    }

    @Override
    protected int setNavigationMenu() {
        return 0;
    }
}
