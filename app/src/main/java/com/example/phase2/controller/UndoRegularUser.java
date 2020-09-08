package com.example.phase2.controller;

import com.example.phase2.entity.RegularUser;
import com.example.phase2.gateway.UserData;
import com.example.phase2.use_case.RegularUserActionStack;

import java.util.ArrayList;

public class UndoRegularUser {

    /**
     * This method undo action of the user.
     * @param userData get the action stacks data from userData
     * @param user the user's action to be undo
     * @return the string of notification of undo
     */
    public static String undo(UserData userData, RegularUser user){
        ArrayList<RegularUserActionStack> undoStacks = userData.getUndoStacks();
        String info = "";
        for (RegularUserActionStack stack: undoStacks){
            if (stack.isSameUser(user)){
                info = stack.undo();
            }
        }
        return info;
    }

    /**
     * This method will peek the last action the user made, and return the info for presentation.
     * This method will not change anything of the undo history.
     * @param userData get the action stacks data from userData
     * @param user the user's action to be undone.
     * @return the information for the history.
     */
    public static String peekUndoInfo(UserData userData, RegularUser user){
        ArrayList<RegularUserActionStack> undoStacks = userData.getUndoStacks();
        String info = "";
        for (RegularUserActionStack stack: undoStacks){
            if (stack.isSameUser(user)){
                if(stack.peek() != null){
                    return stack.peek().getInfo();
                }
            }
        }
        return info;
    }
}
