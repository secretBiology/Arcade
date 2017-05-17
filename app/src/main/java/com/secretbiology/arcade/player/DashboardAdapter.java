package com.secretbiology.arcade.player;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.secretbiology.arcade.R;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Dexter for Arcade .
 * Code is released under MIT license
 */

class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.DashView> {

    private List<DashboardItem> itemList;

    public DashboardAdapter(List<DashboardItem> itemList) {
        this.itemList = itemList;
    }

    @Override
    public DashView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DashView(LayoutInflater.from(parent.getContext()).inflate(R.layout.dashboard_item, parent, false));
    }

    @Override
    public void onBindViewHolder(DashView holder, int position) {
        DashboardItem item = itemList.get(position);
        holder.title.setText(item.getTitle());
        holder.subTitle.setText(item.getSubTitle().toLowerCase());
        holder.icon.setImageResource(item.getIcon());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    class DashView extends RecyclerView.ViewHolder {
        TextView title, subTitle;
        ImageView icon;
        ConstraintLayout layout;

        DashView(View itemView) {
            super(itemView);
            title = ButterKnife.findById(itemView, R.id.dashboard_item_title);
            subTitle = ButterKnife.findById(itemView, R.id.dashboard_item_subtitle);
            icon = ButterKnife.findById(itemView, R.id.dashboard_item_icon);
            layout = ButterKnife.findById(itemView, R.id.dashboard_item_layout);
        }
    }
}
