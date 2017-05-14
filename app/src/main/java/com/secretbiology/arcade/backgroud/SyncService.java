package com.secretbiology.arcade.backgroud;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GetTokenResult;
import com.secretbiology.arcade.common.AppPrefs;
import com.secretbiology.arcade.network.RetroCalls;
import com.secretbiology.arcade.network.models.User;
import com.secretbiology.helpers.general.General;
import com.secretbiology.helpers.general.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SyncService extends IntentService {

    private static final String ACTION_USER_SYCN = "com.secretbiology.arcade.backgroud.action.userSync";

    public SyncService() {
        super("SyncService");
    }


    public static void startUserSync(Context context) {
        Intent intent = new Intent(context, SyncService.class);
        intent.setAction(ACTION_USER_SYCN);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_USER_SYCN.equals(action)) {
                syncUserInfo(getApplicationContext());
            }
        }
    }

    private void syncUserInfo(Context context) {
        AppPrefs prefs = new AppPrefs(context);
        final User user = new User();
        user.setEmail(prefs.getEmail());
        user.setUid(prefs.getUID());
        user.setName(prefs.getName());
        user.setLastSync(General.timeStamp());
        user.setLastLogin(prefs.getLastLogin());
        user.setGender(prefs.getGender());
        user.setProfileIcon(prefs.getProfileIcon());
        user.setLastLogin(prefs.getLastLogin());

        FirebaseAuth.getInstance().getCurrentUser().getToken(false).addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
            @Override
            public void onComplete(@NonNull Task<GetTokenResult> task) {
                if (task.isSuccessful()) {
                    new RetroCalls().updateUserInfo(user.getUid(), user, task.getResult().getToken()).enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            Log.inform("User information Successfully updated");
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable throwable) {
                            Log.error(throwable.getLocalizedMessage());
                        }
                    });
                } else {
                    Log.error(task.getException().getMessage());
                }
            }
        });

    }


}
