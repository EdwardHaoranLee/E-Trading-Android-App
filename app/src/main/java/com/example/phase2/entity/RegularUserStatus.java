package com.example.phase2.entity;

import java.io.Serializable;
import java.util.Stack;

public class RegularUserStatus implements Serializable {
    /**
     * This class is design to record a piece of history of the regular user.
     */

    private RegularUser historyUser; // The history to be recovered.
    private String information; // The information to be displayed.
    private int id; // The id to compare if two piece of history from two users are resulted from the same action.
    private RegularUser mutualUser = null; // The other user that might be affected. null means the action is sole.

    public RegularUserStatus(RegularUser currentUser, UndoNotification notification){
        this.historyUser = currentUser.deepCopy();
        this.information = notification.getInfo();
        this.id = notification.getId();
        this.mutualUser = notification.getMutualUser();
    }

    public RegularUser getHistory(){
        return this.historyUser;
    }

    public String getInfo(){
        return this.information;
    }

    public boolean isSameID(RegularUserStatus action){
        return this.id == action.id;
    }

    public RegularUser getMutualUser(){
        return this.mutualUser;
    }
}
