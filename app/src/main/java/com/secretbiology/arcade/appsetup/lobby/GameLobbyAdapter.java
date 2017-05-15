package com.secretbiology.arcade.appsetup.lobby;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.secretbiology.arcade.R;
import com.secretbiology.arcade.common.Helper;
import com.secretbiology.arcade.network.models.GameDetails;
import com.secretbiology.helpers.general.Log;
import com.secretbiology.helpers.general.TimeUtils.ConverterMode;
import com.secretbiology.helpers.general.TimeUtils.DateConverter;

import java.text.ParseException;
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
        if (Helper.getGameByID(details.getGameID()) != null) {
            holder.icon.setImageResource(Helper.getGameByID(details.getGameID()).getIcon());
        }
        holder.gameID.setText(context.getString(R.string.table_id, details.getTableID()));
        holder.userName.setText(details.getGameCreator());
        try {
            holder.timestamp.setText(DateConverter.changeFormat(ConverterMode.MONTH_FIRST, details.getGameCreationTime(), "EEE hh:mm a"));
        } catch (ParseException e) {
            Log.error(e.getMessage());
            holder.timestamp.setText(details.getGameCreationTime());
        }

        if (details.getMaxPlayers() > details.getNumberOfPlayers()) {
            holder.gameStatus.setColorFilter(ContextCompat.getColor(context, R.color.green));
        } else {
            holder.gameStatus.setColorFilter(ContextCompat.getColor(context, R.color.red));
        }

    }

    @Override
    public int getItemCount() {
        return detailsList.size();
    }

    class ItemView extends RecyclerView.ViewHolder {
        ImageView icon, userIcon, gameStatus;
        TextView gameName, gameID, userName, timestamp;

        ItemView(View itemView) {
            super(itemView);
            icon = ButterKnife.findById(itemView, R.id.lobby_item_icon);
            gameStatus = ButterKnife.findById(itemView, R.id.game_status_indicator);
            userIcon = ButterKnife.findById(itemView, R.id.lobby_item_user_icon);
            gameName = ButterKnife.findById(itemView, R.id.lobby_item_game_name);
            gameID = ButterKnife.findById(itemView, R.id.lobby_item_game_id);
            userName = ButterKnife.findById(itemView, R.id.lobby_item_user_name);
            timestamp = ButterKnife.findById(itemView, R.id.lobby_item_timestamp);

        }
    }
}
