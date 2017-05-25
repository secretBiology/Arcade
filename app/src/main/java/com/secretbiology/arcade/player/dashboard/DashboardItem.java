package com.secretbiology.arcade.player.dashboard;

/**
 * Created by Dexter for Arcade .
 * Code is released under MIT license
 */

class DashboardItem {
    private int action;
    private String title;
    private String subTitle;
    private int icon;

    DashboardItem() {
    }

    DashboardItem(int action, String title, String subTitle, int icon) {
        this.action = action;
        this.title = title;
        this.subTitle = subTitle;
        this.icon = icon;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
