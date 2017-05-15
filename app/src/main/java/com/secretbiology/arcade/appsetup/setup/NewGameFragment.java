package com.secretbiology.arcade.appsetup.setup;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.secretbiology.arcade.R;
import com.secretbiology.arcade.common.Game;
import com.secretbiology.helpers.general.Log;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dexter for Arcade .
 * Code is released under MIT license
 */

public class NewGameFragment extends Fragment {

    private SetupGameActions actions;

    @BindView(R.id.game_list_recycler)
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_game_fragment, container, false);
        ButterKnife.bind(this, view);
        GameListAdapter adapter = new GameListAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        adapter.setOnButtonClick(new GameListAdapter.OnButtonClick() {
            @Override
            public void createGame(Game game) {
                actions.startTable(game);
            }

            @Override
            public void learnGame(Game game) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(game.getUrl())));
                startActivity(browserIntent);
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            actions = (SetupGameActions) context;
        } catch (ClassCastException castException) {
            Log.error(castException.getMessage());
        }

    }


    interface SetupGameActions {
        void startTable(Game game);
    }
}
