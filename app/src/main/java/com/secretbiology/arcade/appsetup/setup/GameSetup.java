package com.secretbiology.arcade.appsetup.setup;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.secretbiology.arcade.R;
import com.secretbiology.arcade.common.BaseActivity;
import com.secretbiology.arcade.common.DatabaseConstants;
import com.secretbiology.arcade.common.Game;
import com.secretbiology.arcade.common.Helper;
import com.secretbiology.arcade.network.models.GameDetails;
import com.secretbiology.helpers.general.General;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.R.string.ok;

public class GameSetup extends BaseActivity implements NewGameFragment.SetupGameActions, TwoPlayerTable.GameTableActions {

    public static final String GAME_ID = "gameID";

    @BindView(R.id.game_setup_frame)
    FrameLayout gameFrame;
    private Game currentGame;
    private GameDetails gameDetails;
    private DatabaseReference myRef;
    private ProgressDialog progressDialog;
    private boolean isCreator;
    private String oldTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        showProxyError();
        progressDialog = new ProgressDialog(GameSetup.this);
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.setCancelable(false);

        myRef = FirebaseDatabase.getInstance().getReference(DatabaseConstants.LOBBY);

        oldTable = getIntent().getStringExtra(GAME_ID);
        if (oldTable != null) {
            isCreator = false;
            setUpJoin();
        } else {
            isCreator = true;
            setUpNew();
        }
    }

    private void setUpJoin() {
        progressDialog.show();
        myRef.child(oldTable).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                gameDetails = dataSnapshot.getValue(GameDetails.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                showError(databaseError.getMessage());
            }
        });

    }

    private void setUpNew() {
        setTitle(R.string.create_game);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.game_setup_frame, new NewGameFragment());
        transaction.commit();
    }

    @Override
    protected int setLayout() {
        return R.layout.game_setup;
    }

    @Override
    protected int setNavigationMenu() {
        return 0;
    }

    private void showProxyError() {
        if (General.isOnProxy()) {
            new AlertDialog.Builder(GameSetup.this)
                    .setTitle("Sorry!")
                    .setIcon(R.drawable.icon_game_lobby)
                    .setCancelable(false)
                    .setMessage(getString(R.string.proxy_warning))
                    .setPositiveButton(ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).show();
        }
    }

    @Override
    public void startTable(final Game game) {
        currentGame = game;
        String tableID = myRef.push().getKey();
        gameDetails = new GameDetails();
        gameDetails.setGameID(game.getID());
        gameDetails.setTableID(tableID);
        gameDetails.setGameCreationTime(General.timeStamp());
        gameDetails.setGameCreatorUID(prefs.getUID());
        gameDetails.setGameCreator(prefs.getName());
        gameDetails.setNumberOfPlayers(1);
        gameDetails.setMaxPlayers(2); //todo
        gameDetails.setGameName(getString(game.getName()));
        progressDialog.show();
        myRef.child(tableID).setValue(gameDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    progressDialog.hide();
                    makeGameTable(game.getID());
                } else {
                    showError(task.getException().getMessage());
                }
            }
        });

    }

    @Override
    public void startNewGame() {

    }

    private void makeGameTable(int gameID) {
        setTitle(currentGame.getName());
        prefs.setCurrentTable(gameDetails.getTableID());
        if (gameID == Game.BATTLESHIP.getID()) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
            transaction.replace(R.id.game_setup_frame, new TwoPlayerTable());
            transaction.commit();
        } else {
            setTitle(R.string.create_game);
            showError("No Such game exists!");
        }
    }

    private void showError(String message) {
        progressDialog.hide();
        new Helper().showErrorDialog(GameSetup.this, message);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isCreator && gameDetails != null) {
            myRef.child(gameDetails.getTableID()).removeValue();
        }
    }


}
