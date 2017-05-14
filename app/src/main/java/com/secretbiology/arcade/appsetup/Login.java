package com.secretbiology.arcade.appsetup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GetTokenResult;
import com.secretbiology.arcade.R;
import com.secretbiology.arcade.appsetup.lobby.GameLobby;
import com.secretbiology.arcade.backgroud.SyncService;
import com.secretbiology.arcade.common.AppPrefs;
import com.secretbiology.arcade.common.Helper;
import com.secretbiology.arcade.network.RetroCalls;
import com.secretbiology.arcade.network.models.User;
import com.secretbiology.helpers.general.General;
import com.secretbiology.helpers.general.Log;
import com.secretbiology.helpers.general.views.InputView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    @BindView(R.id.login_email)
    InputView email;
    @BindView(R.id.login_password)
    InputView password;

    private FirebaseAuth auth;
    private ProgressDialog progressDialog;
    private AppPrefs prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ButterKnife.bind(this);
        prefs = new AppPrefs(getBaseContext());
        progressDialog = new ProgressDialog(Login.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(getString(R.string.authenticating));

        auth = FirebaseAuth.getInstance();

        // Set centered title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setCustomView(R.layout.actionbar);
            ((TextView) findViewById(R.id.action_bar_title)).setText(R.string.login);
        }
    }

    @OnClick(R.id.login_button)
    public void login() {
        email.setErrorEnabled(false);
        password.setErrorEnabled(false);
        if (General.isValidEmail(email.getText())) {
            if (password.getText().length() > 5) {
                progressDialog.show();
                startConnection();
            } else {
                password.setError(getString(R.string.login_invalid_password));
                password.getFocus(getBaseContext());
            }
        } else {
            email.setError(getString(R.string.login_invalid_email));
            email.getFocus(getBaseContext());
        }
    }

    private void startConnection() {
        auth.signInWithEmailAndPassword(email.getText(), password.getText()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    prefs.setEmail(email.getText());
                    progressDialog.setMessage(getString(R.string.loading_data));
                    getToken();
                } else {
                    if (task.getException() != null) {
                        showError(task.getException().getMessage());
                    }
                }
            }
        });
    }

    private void getToken() {
        if (auth.getCurrentUser() != null) {
            auth.getCurrentUser().getToken(false).addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                @Override
                public void onComplete(@NonNull Task<GetTokenResult> task) {
                    if (task.isSuccessful()) {
                        prefs.setUID(auth.getCurrentUser().getUid());
                        checkData(auth.getCurrentUser().getUid(), task.getResult().getToken());
                    } else {
                        showError(task.getException().getMessage());
                    }
                }
            });
        } else {
            Log.error("Firebase user is null, token can not be retrieved.");
        }
    }

    private void checkData(String uid, String token) {
        new RetroCalls().getUserInfo(uid, token).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    progressDialog.hide();
                    prefs.setLastLogin(General.timeStamp());

                    if (response.body() == null) {
                        Log.inform("New user! Sending to intro activity");
                        Intent intent = new Intent(Login.this, FirstTimeUser.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else {
                        User user = response.body();
                        prefs.setName(user.getName());
                        prefs.setGender(user.getGender());
                        prefs.setProfileIcon(user.getProfileIcon());
                        prefs.firstTimeSetupDone();
                        SyncService.startUserSync(getApplicationContext());
                        Intent intent = new Intent(Login.this, GameLobby.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }

                } else {
                    showError(response.message());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable throwable) {
                showError(throwable.getMessage());
            }
        });
    }

    private void showError(String message) {
        progressDialog.hide();
        new Helper().showErrorDialog(Login.this, message);
    }

}
