package com.secretbiology.arcade.appsetup.setup;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.secretbiology.arcade.R;
import com.secretbiology.arcade.common.Game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Dexter for Arcade .
 * Code is released under MIT license
 */

class GameListAdapter extends RecyclerView.Adapter<GameListAdapter.GameView> {

    private List<Game> gameList;
    private OnButtonClick click;

    public GameListAdapter() {
        gameList = new ArrayList<>();
        Collections.addAll(gameList, Game.values());
    }

    @Override
    public GameView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GameView(LayoutInflater.from(parent.getContext()).inflate(R.layout.game_details_item, parent, false));
    }

    @Override
    public void onBindViewHolder(GameView holder, int position) {
        final Game game = gameList.get(position);
        Context context = holder.itemView.getContext();
        holder.icon.setImageResource(game.getIcon());
        holder.title.setText(context.getString(game.getName()));
        holder.details.setText(context.getString(game.getDetails()));
        holder.make.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.createGame(game);
            }
        });
        holder.learn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.learnGame(game);
            }
        });
    }

    @Override
    public int getItemCount() {
        return gameList.size();
    }

    class GameView extends RecyclerView.ViewHolder {

        ImageView icon;
        TextView title, details;
        Button make, learn;

        GameView(View itemView) {
            super(itemView);
            icon = ButterKnife.findById(itemView, R.id.game_details_icon);
            title = ButterKnife.findById(itemView, R.id.game_details_title);
            details = ButterKnife.findById(itemView, R.id.game_details_details);
            make = ButterKnife.findById(itemView, R.id.game_details_create_game);
            learn = ButterKnife.findById(itemView, R.id.game_details_learn_game);
        }
    }

    void setOnButtonClick(OnButtonClick c) {
        click = c;
    }

    interface OnButtonClick {
        void createGame(Game game);

        void learnGame(Game game);
    }
}
