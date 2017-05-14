package com.secretbiology.arcade.appsetup;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.secretbiology.arcade.R;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Dexter for Arcade .
 * Code is released under MIT license
 */

public class UserIconAdapter extends RecyclerView.Adapter<UserIconAdapter.IconView> {

    private List<IconModel> modelList;
    private OnIconSelected selected;


    public UserIconAdapter(List<IconModel> modelList) {
        this.modelList = modelList;
    }

    @Override
    public IconView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new IconView(LayoutInflater.from(parent.getContext()).inflate(R.layout.user_icon_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final IconView holder, int position) {
        IconModel model = modelList.get(position);
        Context context = holder.itemView.getContext();

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected.clicked(holder.getLayoutPosition());
            }
        });

        if (model.getProfileIcon() == null) {
            holder.icon.setImageResource(model.getIcon());
            holder.title.setText(context.getString(model.getTitle()));
            if (!model.isSelected()) {
                holder.icon.setColorFilter(ContextCompat.getColor(context, R.color.divider));
            } else {
                holder.icon.setColorFilter(null);
            }
        } else {
            holder.icon.setImageResource(model.getProfileIcon().getIcon());
            holder.title.setVisibility(View.GONE);
            if (model.isSelected()) {
                Animation pulse = AnimationUtils.loadAnimation(context, R.anim.pulse);
                holder.icon.startAnimation(pulse);
            } else {
                holder.icon.clearAnimation();
            }
        }
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    class IconView extends RecyclerView.ViewHolder {

        LinearLayout layout;
        ImageView icon;
        TextView title;

        IconView(View itemView) {
            super(itemView);
            layout = ButterKnife.findById(itemView, R.id.user_icon_layout);
            icon = ButterKnife.findById(itemView, R.id.user_icon);
            title = ButterKnife.findById(itemView, R.id.user_icon_title);
        }
    }

    public void seOnIconClick(OnIconSelected s) {
        selected = s;
    }

    public interface OnIconSelected {
        void clicked(int position);
    }
}
