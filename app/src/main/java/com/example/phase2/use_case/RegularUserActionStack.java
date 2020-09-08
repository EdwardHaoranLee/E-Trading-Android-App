package com.example.phase2.use_case;

import com.example.phase2.database.SaveData;
import com.example.phase2.entity.RegularUser;
import com.example.phase2.entity.RegularUserStatus;
import com.example.phase2.entity.UndoNotification;
import com.example.phase2.gateway.UserData;

import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;
import java.util.Stack;

public class RegularUserActionStack implements Observer, Serializable {

    private RegularUser user;
    private Stack<RegularUserStatus> actions;
    private UserData userData;

    /**
     * Initializing the RegularUserActionStack
     * @param user
     */
    public RegularUserActionStack(RegularUser user, UserData userData){
        this.user = user;
        this.user.addObserver(this);
        this.actions = new Stack<>();
        this.userData = userData;
    }

    /**
     * Will be called when the RegularUser's execute() and executeMutual() are called.
     * @param observable
     * @param o
     */
    @Override
    public void update(Observable observable, Object o) {
        if (o != null){
            UndoNotification notification = ((UndoNotification) o);
            this.push(notification);
        } else {
            this.clear();
        }
    }

    /**
     * If an empty notice was sent from the update, then the stack will clear all the history. It
     * will happened when some action that cannot be undone happened.
     */
    private void clear() {
        this.actions = new Stack<>();
    }


    /**
     * The undo function.
     * @return the information to be displayed.
     */
    public String undo(){
        SaveData saveData = new SaveData();
        if (this.actions.isEmpty()){
            return "No action can be undone.";
        }
        RegularUserStatus history = this.actions.pop();
        if (history.getMutualUser() == null){
            this.user.deepPaste(history.getHistory());
            saveData.saveData(this.user);
            saveData.saveData(this);
            return history.getInfo();
        } else {
            RegularUser mutualUser = history.getMutualUser();
            RegularUserActionStack mutualStack = userData.getUndoStack(mutualUser.getUsername());
            RegularUserStatus mutualHistory = mutualStack.actions.pop();
            if (history.isSameID(mutualHistory)){
                this.user.deepPaste(history.getHistory());
                mutualUser.deepPaste(mutualHistory.getHistory());
                saveData.saveData(this);
                saveData.saveData(this.user);
                saveData.saveData(mutualUser);
                return history.getInfo();
            } else {
                this.actions.push(history);
                mutualStack.actions.push(mutualHistory);
                return "This action is a mutual action with user \"" + mutualUser.getUsername() +
                        ", and since the other user has done other action(s), this cannot be undone.";
            }
        }


    }

    /**
     * Check the top history record's information so the app's user can know what will be undone.
     * @return the complex of an undo history.
     */
    public RegularUserStatus peek(){
        if (this.actions.isEmpty()){
            return null;
        }
        return this.actions.peek();
    }

    /**
     * Called by the update method, this method will add a piece of history into the stack.
     * @param notification The intermediate to be passed from RegularUser.
     */
    private void push(UndoNotification notification){
        RegularUserStatus action = new RegularUserStatus(this.user, notification);
        this.actions.push(action);
    }

    public boolean isSameUser(RegularUser user){
        return user.getUsername().equals(this.user.getUsername());
    }

    public String getUsername(){
        return this.user.getUsername();
    }

    public void updateStack(Stack<RegularUserStatus> stack){
        this.actions = stack;
    }

    public Stack<RegularUserStatus> getActions(){
        return actions;
    }
}
