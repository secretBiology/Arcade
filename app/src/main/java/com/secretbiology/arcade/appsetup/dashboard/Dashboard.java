package com.secretbiology.arcade.appsetup.dashboard;

import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.secretbiology.arcade.R;
import com.secretbiology.arcade.common.AppPrefs;
import com.secretbiology.arcade.common.BaseActivity;
import com.secretbiology.arcade.common.Helper;
import com.secretbiology.arcade.common.ProfileIcons;
import com.secretbiology.helpers.general.Log;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
                if(itemList.get(position).getAction()==3){
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
        //// TODO: 14-05-2017 update on cloud
    }

    @Override
    public void selectedGender(int iconID) {
        prefs.setGender(Helper.getGenderByIcon(iconID).getID());
        setDashBoardItems();
        adapter.notifyDataSetChanged();
    }
}
