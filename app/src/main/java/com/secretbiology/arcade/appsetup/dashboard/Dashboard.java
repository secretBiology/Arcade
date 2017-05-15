package com.secretbiology.arcade.appsetup.dashboard;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.secretbiology.arcade.R;
import com.secretbiology.arcade.appsetup.Login;
import com.secretbiology.arcade.backgroud.SyncService;
import com.secretbiology.arcade.common.AppPrefs;
import com.secretbiology.arcade.common.BaseActivity;
import com.secretbiology.arcade.common.Helper;
import com.secretbiology.arcade.common.ProfileIcons;
import com.secretbiology.helpers.general.General;
import com.secretbiology.helpers.general.Log;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.R.string.cancel;
import static android.R.string.ok;

public class Dashboard extends BaseActivity implements DashBoardSheet.onIconClick {

    @BindView(R.id.dash_recycler)
    RecyclerView recyclerView;
    @BindView(R.id.dash_profile_icon)
    ImageView profileIcon;

    private List<DashBoardItem> itemList;
    private DashBoardAdapter adapter;
    private AppPrefs prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.dashboard);
        ButterKnife.bind(this);


        prefs = new AppPrefs(getApplicationContext());
        itemList = new ArrayList<>();
        setDashBoardItems();

        adapter = new DashBoardAdapter(itemList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        profileIcon.setImageResource(Helper.getProfileIcon(prefs.getProfileIcon()));
        adapter.setOnItemClick(new DashBoardAdapter.OnItemClick() {
            @Override
            public void doAction(int position) {
                if (itemList.get(position).getAction() == 3) {
                    BottomSheetDialogFragment bottomSheetDialogFragment = DashBoardSheet.newInstance(true);
                    bottomSheetDialogFragment.show(getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
                }
            }
        });

    }

    private void setDashBoardItems() {

        Log.inform(prefs.getGender());

        itemList.clear();
        itemList.add(new DashBoardItem(0, R.drawable.icon_person, prefs.getName(), "Screen name"));
        itemList.add(new DashBoardItem(1, R.drawable.icon_email, prefs.getEmail(), "Portal key"));
        itemList.add(new DashBoardItem(2, R.drawable.icon_game_lobby, prefs.getUID(), "Secret identification number"));
        itemList.add(new DashBoardItem(3, Helper.getGenderByID(prefs.getGender()).getIcon(),
                getString(Helper.getGenderByID(prefs.getGender()).getName()), "Species type"));

    }

    @Override
    protected int setLayout() {
        return R.layout.dash_board;
    }

    @Override
    protected int setNavigationMenu() {
        return R.id.nav_dashboard;
    }

    @OnClick(R.id.dash_profile_icon)
    public void showSheet() {
        BottomSheetDialogFragment bottomSheetDialogFragment = DashBoardSheet.newInstance(false);
        bottomSheetDialogFragment.show(getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
    }


    @Override
    public void selectedProfile(ProfileIcons icon) {
        profileIcon.setImageResource(icon.getIcon());
        prefs.setProfileIcon(icon.getID());
        SyncService.startUserSync(getApplicationContext());
    }

    @Override
    public void selectedGender(int iconID) {
        prefs.setGender(Helper.getGenderByIcon(iconID).getID());
        setDashBoardItems();
        adapter.notifyDataSetChanged();
        SyncService.startUserSync(getApplicationContext());
    }

    @OnClick(R.id.change_pass)
    public void changePass() {
        new Builder(Dashboard.this)
                .setTitle(getString(R.string.are_you_sure))
                .setMessage(getString(R.string.change_pass_message, prefs.getEmail()))
                .setPositiveButton(ok, new OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth.getInstance().sendPasswordResetEmail(prefs.getEmail());
                        General.makeLongToast(getApplicationContext(), "Check your inbox for further details.");
                    }
                }).setNegativeButton(cancel, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        }).show();
    }

    @OnClick(R.id.log_out)
    public void logOut() {
        new Builder(Dashboard.this)
                .setTitle(getString(R.string.are_you_sure))
                .setIcon(R.drawable.icon_game_lobby)
                .setMessage(getString(R.string.log_out_message))
                .setPositiveButton(ok, new OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth.getInstance().signOut();
                        prefs.clear();
                        Intent intent = new Intent(Dashboard.this, Login.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }).setNegativeButton(cancel, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        }).show();
    }
}
