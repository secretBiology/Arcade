package com.secretbiology.arcade.appsetup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GetTokenResult;
import com.secretbiology.arcade.R;
import com.secretbiology.arcade.common.AppPrefs;
import com.secretbiology.arcade.common.Helper;
import com.secretbiology.arcade.common.ProfileIcons;
import com.secretbiology.arcade.network.RetroCalls;
import com.secretbiology.arcade.network.models.User;
import com.secretbiology.helpers.general.General;
import com.secretbiology.helpers.general.views.InputView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FirstTimeUser extends AppCompatActivity {

    @BindView(R.id.user_icon_recycler)
    RecyclerView recyclerView;
    @BindView(R.id.user_name)
    InputView name;
    @BindView(R.id.user_profile_recycler)
    RecyclerView profileRecycler;

    private List<IconModel> modelList;
    private List<IconModel> profileIcons;
    private UserIconAdapter adapter;
    private UserIconAdapter profileAdapter;
    private AppPrefs prefs;
    private FirebaseAuth auth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_time_user);
        ButterKnife.bind(this);
        prefs = new AppPrefs(getApplicationContext());
        auth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(FirstTimeUser.this);
        progressDialog.setMessage(getString(R.string.authenticating));
        progressDialog.setCancelable(false);
        if (auth.getCurrentUser() == null) {
            startActivity(new Intent(this, Login.class));
            finish();
        }

        prefs.setGender(getString(R.string.other));
        prefs.setProfileIcon(General.randInt(0, ProfileIcons.values().length - 1));
        // Set centered title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setCustomView(R.layout.actionbar);
            ((TextView) findViewById(R.id.action_bar_title)).setText(R.string.first_time_setup);
        }

        modelList = getIconList();
        profileIcons = getProfileIcons();
        adapter = new UserIconAdapter(modelList);
        profileAdapter = new UserIconAdapter(profileIcons);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);
        adapter.seOnIconClick(new UserIconAdapter.OnIconSelected() {
            @Override
            public void clicked(int position) {
                for (IconModel m : modelList) {
                    if (modelList.indexOf(m) == position) {
                        prefs.setGender(getString(m.getTitle()));
                        m.setSelected(true);
                    } else {
                        m.setSelected(false);
                    }
                }
                adapter.notifyDataSetChanged();
                name.getEditText().clearFocus();
            }
        });

        profileRecycler.setLayoutManager(new GridLayoutManager(getApplicationContext(), 6));
        profileRecycler.setAdapter(profileAdapter);

        profileAdapter.seOnIconClick(new UserIconAdapter.OnIconSelected() {
            @Override
            public void clicked(int position) {
                for (IconModel m : profileIcons) {
                    if (profileIcons.indexOf(m) == position) {
                        m.setSelected(true);
                        prefs.setProfileIcon(m.getProfileIcon().getID());
                    } else {
                        m.setSelected(false);
                    }
                }
                profileAdapter.notifyDataSetChanged();
            }
        });

    }

    @OnClick(R.id.letsgo_button)
    public void letsGo() {
        name.setErrorEnabled(false);
        if (name.isEmpty()) {
            name.getFocus(getApplicationContext());
            name.setError(getString(R.string.first_time_name_error));
        } else {
            progressDialog.show();
            prefs.setName(name.getText());
            final User user = new User();
            user.setEmail(prefs.getEmail());
            user.setUid(prefs.getUID());
            user.setName(name.getText());
            user.setLastSync(General.timeStamp());
            user.setGender(prefs.getGender());
            user.setLastLogin(prefs.getLastLogin());
            user.setProfileIcon(prefs.getProfileIcon());

            auth.getCurrentUser().getToken(false).addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                @Override
                public void onComplete(@NonNull Task<GetTokenResult> task) {
                    if (task.isSuccessful()) {
                        new RetroCalls().updateUserInfo(user.getUid(), user, task.getResult().getToken()).enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {
                                if (response.isSuccessful()) {
                                    prefs.firstTimeSetupDone();
                                    progressDialog.hide();
                                    startActivity(new Intent(FirstTimeUser.this, GameLobby.class));
                                    finish();
                                } else {
                                    showError(response.message());
                                }
                            }

                            @Override
                            public void onFailure(Call<User> call, Throwable throwable) {
                                showError(throwable.getMessage());
                            }
                        });
                    } else {
                        showError(task.getException().getMessage());
                    }
                }
            });


        }

    }

    private void showError(String message) {
        progressDialog.hide();
        new Helper().showErrorDialog(FirstTimeUser.this, message);
    }

    private List<IconModel> getIconList() {
        List<IconModel> models = new ArrayList<>();
        models.add(new IconModel(R.drawable.emo_baby, R.string.child));
        models.add(new IconModel(R.drawable.emo_boy, R.string.male));
        models.add(new IconModel(R.drawable.emo_girl, R.string.female));
        models.add(new IconModel(R.drawable.emo_alien, R.string.alien));
        models.add(new IconModel(R.drawable.emo_robot, R.string.robot));
        models.add(new IconModel(R.drawable.emo_unknown, R.string.other, true));
        return models;
    }

    private List<IconModel> getProfileIcons() {
        List<IconModel> models = new ArrayList<>();
        for (ProfileIcons icons : ProfileIcons.values()) {
            models.add(new IconModel(icons));
        }
        Collections.shuffle(models);
        return models;
    }

}
