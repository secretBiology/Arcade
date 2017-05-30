package com.secretbiology.arcade.background;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

public class BackgroundService extends IntentService {


    private static final String ACTION_UPDATE_PRESENCE = "com.secretbiology.arcade.background.action.presence";


    public BackgroundService() {
        super("BackgroundService");
    }


    public static void updatePresence(Context context) {
        Intent intent = new Intent(context, BackgroundService.class);
        intent.setAction(ACTION_UPDATE_PRESENCE);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPDATE_PRESENCE.equals(action)) {
                sendPresenceDetails();
            }
        }
    }

    private void sendPresenceDetails() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myConnectionsRef = database.getReference("online_users");

        // stores the timestamp of my last disconnect (the last time I was seen online)
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final DatabaseReference lastOnlineRef = database.getReference("/users/" + userID + "/lastOnline");

        final DatabaseReference connectedRef = database.getReference(".info/connected");
        connectedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                boolean connected = snapshot.getValue(Boolean.class);
                if (connected) {
                    // add this device to my connections list
                    // this value could contain info about the device or a timestamp too
                    DatabaseReference con = myConnectionsRef.push();
                    if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                        con.setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    }
                    // when this device disconnects, remove it
                    con.onDisconnect().removeValue();
                    // when I disconnect, update the last time I was seen online
                    lastOnlineRef.onDisconnect().setValue(ServerValue.TIMESTAMP);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.err.println("Listener was cancelled at .info/connected");
            }
        });
    }


}
