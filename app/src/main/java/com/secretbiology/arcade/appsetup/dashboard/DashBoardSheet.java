package com.secretbiology.arcade.appsetup.dashboard;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.secretbiology.arcade.R;
import com.secretbiology.arcade.appsetup.IconModel;
import com.secretbiology.arcade.appsetup.UserIconAdapter;
import com.secretbiology.arcade.common.ProfileIcons;
import com.secretbiology.helpers.general.General;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Dexter for Arcade .
 * Code is released under MIT license
 */

public class DashBoardSheet extends BottomSheetDialogFragment {

    private onIconClick selected;

    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback =
            new BottomSheetBehavior.BottomSheetCallback() {
                @Override
                public void onStateChanged(@NonNull View bottomSheet, int newState) {
                    if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                        dismiss();
                    }
                }

                @Override
                public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                }
            };

    @Override
    public void setupDialog(final Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View contentView = View.inflate(getContext(), R.layout.dashboard_sheet, null);
        dialog.setContentView(contentView);
        setUpHeight(contentView);

        TextView title = ButterKnife.findById(dialog, R.id.dash_sheet_title);
        RecyclerView recyclerView = ButterKnife.findById(dialog, R.id.dash_sheet_recycler);

        final List<IconModel> models = new ArrayList<>();
        for (ProfileIcons p : ProfileIcons.values()) {
            models.add(new IconModel(p));
        }
        UserIconAdapter adapter = new UserIconAdapter(models);

        title.setText("Something");

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 6));

        adapter.seOnIconClick(new UserIconAdapter.OnIconSelected() {
            @Override
            public void clicked(int position) {
                selected.selected(models.get(position).getProfileIcon());
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            selected = (onIconClick) context;
        } catch (ClassCastException castException) {
            /* The activity does not implement the listener. */
            General.makeLongToast(context, "Unable to start activity!");
        }
    }

    private void setUpHeight(View contentView) {
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();

        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);
        }

        View parent = (View) contentView.getParent();
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(parent);
        int screenHeight = (int) getContext().getResources().getDimension(R.dimen.dashboard_bottom_sheet_height);
        bottomSheetBehavior.setPeekHeight(screenHeight);
        params.height = screenHeight;
        parent.setLayoutParams(params);
    }

    interface onIconClick {
        void selected(ProfileIcons icon);
    }

}
