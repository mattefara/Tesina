package com.example.matte.swipe_sidebar;

public class SidebarAction {
    private String actionName;
    private int icon;

    public SidebarAction(String actionName, int icon) {
        this.actionName = actionName;
        this.icon = icon;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
