package com.secretbiology.arcade.appsetup.lobby;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.secretbiology.arcade.R;
import com.secretbiology.arcade.network.models.GameDetails;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Dexter for Arcade .
 * Code is released under MIT license
 */

class GameLobbyAdapter extends RecyclerView.Adapter<GameLobbyAdapter.ItemView> {

    private List<GameDetails> detailsList;

    GameLobbyAdapter(List<GameDetails> detailsList) {
        this.detailsList = detailsList;
    }

    @Override
    public ItemView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemView(LayoutInflater.from(parent.getContext()).inflate(R.layout.game_lobby_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ItemView holder, int position) {
        Context context = holder.itemView.getContext();
        GameDetails details = detailsList.get(position);

        holder.gameName.setText(details.getGameName());
        holder.gameID.setText(details.getGameID());
        holder.userName.setText(details.getGameCreator());
        holder.timestamp.setText(details.getGameCreationTime());

    }

    @Override
    public int getItemCount() {
        return detailsList.size();
    }

    class ItemView extends RecyclerView.ViewHolder {
        ImageView icon, userIcon;
        TextView gameName, gameID, userName, timestamp;

        ItemView(View itemView) {
            super(itemView);
            icon = ButterKnife.findById(itemView, R.id.lobby_item_icon);
            userIcon = ButterKnife.findById(itemView, R.id.lobby_item_user_icon);
            gameName = ButterKnife.findById(itemView, R.id.lobby_item_game_name);
            gameID = ButterKnife.findById(itemView, R.id.lobby_item_game_id);
            userName = ButterKnife.findById(itemView, R.id.lobby_item_user_name);
            timestamp = ButterKnife.findById(itemView, R.id.lobby_item_timestamp);

        }
    }
}
