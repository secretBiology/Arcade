package com.secretbiology.arcade.appsetup.setup;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.secretbiology.arcade.R;
import com.secretbiology.helpers.general.Log;

import butterknife.ButterKnife;

/**
 * Created by Dexter for Arcade .
 * Code is released under MIT license
 */

public class TwoPlayerTable extends Fragment {

    private GameTableActions actions;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.game_table, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            actions = (GameTableActions) context;
        } catch (ClassCastException castException) {
            Log.error(castException.getMessage());
        }

    }

    interface GameTableActions {
        void startNewGame();
    }
}
