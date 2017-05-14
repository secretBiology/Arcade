package com.secretbiology.arcade.appsetup.dashboard;

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

class DashBoardAdapter extends RecyclerView.Adapter<DashBoardAdapter.DashView> {

    private List<DashBoardItem> itemList;
    private OnItemClick itemClick;

    DashBoardAdapter(List<DashBoardItem> itemList) {
        this.itemList = itemList;
    }

    @Override
    public DashView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DashView(LayoutInflater.from(parent.getContext()).inflate(R.layout.dash_board_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final DashView holder, int position) {
        DashBoardItem item = itemList.get(position);
        holder.title.setText(item.getDescription());
        holder.icon.setImageResource(item.getIcon());
        if (item.getSubDescription() != null) {
            holder.subTitle.setVisibility(View.VISIBLE);
            holder.subTitle.setText(item.getSubDescription());
        } else {
            holder.subTitle.setVisibility(View.GONE);
        }
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClick.doAction(holder.getLayoutPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


    class DashView extends RecyclerView.ViewHolder {

        ImageView icon;
        TextView title, subTitle;
        ConstraintLayout layout;

        DashView(View itemView) {
            super(itemView);
            icon = ButterKnife.findById(itemView, R.id.dash_item_icon);
            title = ButterKnife.findById(itemView, R.id.dash_item_title);
            subTitle = ButterKnife.findById(itemView, R.id.dash_item_subtitle);
            layout = ButterKnife.findById(itemView, R.id.dash_item_layout);
        }
    }

    void setOnItemClick(OnItemClick click){
        itemClick = click;
    }

    interface OnItemClick{
        void doAction(int position);
    }
}
