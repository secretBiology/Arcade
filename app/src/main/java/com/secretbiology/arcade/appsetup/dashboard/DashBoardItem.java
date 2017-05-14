package com.secretbiology.arcade.appsetup.dashboard;

/**
 * Created by Dexter for Arcade .
 * Code is released under MIT license
 */

class DashBoardItem {

    private int icon;
    private String description;
    private String subDescription;

    DashBoardItem() {
    }

    public DashBoardItem(int icon, String description, String subDescription) {
        this.icon = icon;
        this.description = description;
        this.subDescription = subDescription;
    }

    public DashBoardItem(int icon, String description) {
        this.icon = icon;
        this.description = description;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSubDescription() {
        return subDescription;
    }

    public void setSubDescription(String subDescription) {
        this.subDescription = subDescription;
    }
}
