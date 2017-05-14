package com.secretbiology.arcade.appsetup;

import com.secretbiology.arcade.common.ProfileIcons;

/**
 * Created by Dexter for Arcade .
 * Code is released under MIT license
 */

public class IconModel {
    private int icon;
    private int title;
    private boolean isSelected;
    private ProfileIcons profileIcon;

    IconModel(int icon, int title) {
        this.icon = icon;
        this.title = title;
    }

    public IconModel(int icon, int title, boolean isSelected) {
        this.icon = icon;
        this.title = title;
        this.isSelected = isSelected;
    }

    public IconModel(ProfileIcons profileIcon) {
        this.profileIcon = profileIcon;
    }

    int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public ProfileIcons getProfileIcon() {
        return profileIcon;
    }

    public void setProfileIcon(ProfileIcons profileIcon) {
        this.profileIcon = profileIcon;
    }
}
