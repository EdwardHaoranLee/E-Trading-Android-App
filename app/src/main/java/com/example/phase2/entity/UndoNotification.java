package com.example.phase2.entity;

public class UndoNotification {

    /**
     * This method is design to pass the information of an action from the action starter, the user,
     * to the action listener, the RegularUserActionStack.
     */
    private String text; // The information for this action.
    private int id; // The id of this action.
    public static int GLOBAL_ID = 0; // A static variable to generate different ids for different action.
    private RegularUser mutualUser = null; // Another user that might be affected.

    public UndoNotification(String text){
        this.text = text;
        this.id = UndoNotification.GLOBAL_ID;
        UndoNotification.GLOBAL_ID += 1;
    }

    public UndoNotification(String text, RegularUser mutualUser){
        this.text = text;
        this.id = UndoNotification.GLOBAL_ID;
        UndoNotification.GLOBAL_ID += 1;
        this.mutualUser = mutualUser;
    }

    public boolean isSameID(int id){
        return id == this.id;
    }

    public String getInfo(){
        return this.text;
    }

    public int getId(){
        return this.id;
    }

    public RegularUser getMutualUser(){
        return this.mutualUser;
    }

    public void setId(int id){
        this.id = id;
    }
}
