package com.secretbiology.arcade.player.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.secretbiology.arcade.R;
import com.secretbiology.arcade.common.AppPrefs;
import com.secretbiology.arcade.common.Helper;
import com.secretbiology.arcade.constants.DatabaseConstants;
import com.secretbiology.arcade.constants.ProfileIcon;
import com.secretbiology.arcade.constants.Species;
import com.secretbiology.arcade.player.Player;
import com.secretbiology.arcade.player.dashboard.Dashboard;
import com.secretbiology.helpers.general.General;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Login extends AppCompatActivity {

    @BindView(R.id.login_email)
    EditText email;
    @BindView(R.id.login_password)
    EditText password;

    private ProgressDialog progressDialog;
    private AppPrefs prefs;
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ButterKnife.bind(this);
        progressDialog = new ProgressDialog(Login.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(getString(R.string.loading));
        prefs = new AppPrefs(getApplicationContext());
        database = FirebaseDatabase.getInstance().getReference(DatabaseConstants.USERS);
    }

    @OnClick(R.id.login_button)
    public void validate() {
        if (!General.isValidEmail(email.getText().toString())) {
            Snackbar.make(email, R.string.error_invalid_email, Snackbar.LENGTH_SHORT).show();
        } else {
            if (password.getText().toString().length() < 6) {
                Snackbar.make(email, R.string.error_invalid_password, Snackbar.LENGTH_SHORT).show();
            } else {
                progressDialog.show();
                login();
            }
        }
    }

    private void login() {

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    fetchData();
                } else {
                    showError(task.getException().getMessage());
                }
            }
        });

    }

    private void fetchData() {
        database.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).
                addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() == null) {
                            createNew();
                        } else {
                            progressDialog.hide();
                            Player player = dataSnapshot.getValue(Player.class);
                            prefs.updateInfo(player);
                            prefs.userLoggedIn();
                            startActivity(new Intent(Login.this, Dashboard.class));
                            finish();
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        showError(databaseError.getMessage());
                    }
                });
    }

    private void createNew() {
        Player player = new Player();
        player.setEmail(email.getText().toString());
        player.setUid(FirebaseAuth.getInstance().getCurrentUser().getUid());
        player.setIconID(General.randInt(0, ProfileIcon.values().length - 1));
        player.setSpeciesID(Species.OTHER.getID());
        player.setName(createRandomName());
        player.setMessageToken(FirebaseInstanceId.getInstance().getToken());
        prefs.updateInfo(player);
        database.child(player.getUid()).setValue(player).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    progressDialog.hide();
                    prefs.userLoggedIn();
                    Intent i = new Intent(Login.this, Dashboard.class);
                    i.setAction(Dashboard.ACTION_FIRST_USE);
                    startActivity(i);
                    finish();
                } else {
                    showError(task.getException().getMessage());
                }
            }
        });
    }

    private String createRandomName() {
        String[] firstName = new String[]{"Awesome", "Best", "Sweet", "Hot", "Determined"};
        String[] lastName = new String[]{"Gamer", "Player", "User", "Person"};
        return firstName[General.randInt(0, firstName.length - 1)] + " " + lastName[General.randInt(0, lastName.length - 1)];
    }

    private void showError(String message) {
        progressDialog.hide();
        Helper.showError(Login.this, message);
    }
}
