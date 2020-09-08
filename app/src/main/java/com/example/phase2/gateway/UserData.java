package com.example.phase2.gateway;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.phase2.entity.*;
import com.example.phase2.database.*;
import com.example.phase2.use_case.RegularUserActionStack;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;


public class UserData implements Serializable {
    private ArrayList<User> users;
    private ArrayList<RegularUserActionStack> undoStacks;

    /**
     * A default constructor that get users as an empty ArrayList and get the undo stacks as an
     * empty ArrayList.
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public UserData(){
        users = new ArrayList<>();
        users.add(new DemoUser());
        undoStacks = new ArrayList<>();
    }

    /**
     * Initialize a UserData with the target User.
     * @param initialUser an input initial User
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public UserData(User initialUser){
        users = new ArrayList<>();
        undoStacks = new ArrayList<>();
        users.add(initialUser);
        users.add(new DemoUser());
        if (initialUser.getUserType() == 1) {
            undoStacks.add(new RegularUserActionStack((RegularUser) initialUser, this));
        }
    }

    /**
     * Initialize the UserData with all Users stored in the System
     * @param allUsers all user store from the system
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public UserData(ArrayList<User> allUsers){
        users = allUsers;
        users.add(new DemoUser());
        undoStacks = new ArrayList<>();
        for (User user: allUsers){
            this.addUserToUndoStack(user);
        }
    }

    /**
     * Add a RegularUserActionStack to this UserData with given user.
     * @param user the user
     * @return the RegularUserActionStack
     */
    private RegularUserActionStack addUserToUndoStack(User user){
        if (user.getUserType() == 1){
            RegularUserActionStack action = new RegularUserActionStack((RegularUser) user, this);
            this.undoStacks.add(action);
            return action;
        }
        return null;
    }

    /**
     * Instantiates and stores a new user with given username and PW
     * @param username the target User's username you want to store
     * @param password the target User's password you want to store
     * @param type the target User's type (RegularUser or AdminUser) you want to store
     */
    @RequiresApi(api = Build.VERSION_CODES.R)
    public void addUser(String username, String password, String type){
        //
        User newUser;
        if (type.equals("regular")){
            newUser = new RegularUser(username, password);
        }else{
            newUser = new AdminUser(username, password);
        }
        users.add(newUser);
        SaveData savingData = new SaveData();
        savingData.saveData(newUser);
        RegularUserActionStack actionStack = this.addUserToUndoStack(newUser);
        if (actionStack != null) {savingData.saveData(actionStack);}
    }

    /**
     * Store the value of the user and add it to the <users>
     * @param user the target user you want to store and add
     */
    @RequiresApi(api = Build.VERSION_CODES.R)
    public void addUser(User user){
        users.add(user);
        SaveData savingData = new SaveData();
        savingData.saveData(user);
        RegularUserActionStack actionStack = this.addUserToUndoStack(user);
        if (actionStack != null) {savingData.saveData(actionStack);}
    }

    /**
     * A getter method of the Users.
     * @return the users for this User
     */
    public ArrayList<User> getUsers() {
        return users;
    }

    /**
     * Check and show if the username and password are valid.
     * @param username the username to be checked
     * @param password the password to be checked
     * @return  -1 if user does not exist, 0 if PW is wrong, 1 if successful
     */
    public int validateUser(String username, String password){
        for (User currentUser: users){
            if (currentUser.getUsername().equals(username)){
                if (currentUser.getPassword().equals(password)){
                    return 1;
                }
                return 0;
            }
        }
        return -1;
    }

    /**
     *   This version checks only for username
     *  returns true iff the username already exists
     * @param username the target username you want to check
     * @return true iff the username already exists
     */
    public boolean validateUser(String username){

        for (User currentUser: users){
            if (currentUser.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the user entity that is associated with username
     * @param username target user's username
     * @return the User Object if the username is in the system. Else return null.
     */
    public User findUser(String username){
        //
        for (User currentUser: users){
            if (currentUser.getUsername().equals(username)){
                return currentUser;
            }
        }
        return null;
    }

    /**
     * Getter method for regular users in the system
     * @return An ArrayList of all RegularUsers in the system
     */
    public ArrayList<RegularUser> getRegularUsers(){
        ArrayList<RegularUser> regularUsers = new ArrayList<>();
        for (User u: users){
            if (u.getUserType() == 1){
                regularUsers.add((RegularUser)u);
            }
        }
        return regularUsers;
    }

    /**
     * Getter method for AdminUsers
     * @return An ArrayList of all AdminUsers in the system
     */
    public ArrayList<AdminUser> getAdminUsers(){
        ArrayList<AdminUser> adminUsers = new ArrayList<>();
        for (User u: users){
            if (u.getUserType() == 0){
                adminUsers.add((AdminUser)u);
            }
        }
        return adminUsers;
    }


    public DemoUser getDemoUser(){
        for (User u: users){
            if (u.getUserType() == 3){
                return (DemoUser) u;
            }
        }
        return null;
    }

    /**
     * Getter method for Suspicious Users
     * @return An ArrayList of all Suspicious users in the system
     */
    public ArrayList<RegularUser> getSuspiciousUsers(){
        ArrayList<RegularUser> susUsers = new ArrayList<>();
        for (User u: users){
            if (u instanceof RegularUser){
                if(((RegularUser) u).getIsSuspicious())
                    susUsers.add((RegularUser) u);
            }
        }
        return susUsers;
    }

    /**
     * Getter method for Frozen Users
     * @return An ArrayList of all Frozen users in the system
     */
    public ArrayList<RegularUser> getFrozenUsers(){
        ArrayList<RegularUser> froUsers = new ArrayList<>();
        for (User u: users){
            if (u instanceof RegularUser){
                if(((RegularUser) u).getIsFrozen())
                    froUsers.add((RegularUser) u);
            }
        }
        return froUsers;
    }

    /**
     * Return an HashMap of username=RegularUser pairs for all RegularUsers
     * @return an HashMap of username=RegularUser pairs for all RegularUsers
     */
    @RequiresApi(api = Build.VERSION_CODES.R)
    public HashMap<String, RegularUser> hashRegularUsers(){
//        ArrayList<RegularUser> regularUsers = this.getRegularUsers();
//        HashMap<String, RegularUser> result = new HashMap<>();
//        for (RegularUser user: regularUsers){
//            result.put(user.getUsername(), user);
//        }
//        return result;
        ReadData readData = new ReadData();
        return readData.readRegularUsers();
    }

    public ArrayList<RegularUserActionStack> getUndoStacks(){
        return this.undoStacks;
    }

    public RegularUserActionStack getUndoStack(String username){
        for (RegularUserActionStack stack: this.undoStacks){
            if (stack.getUsername().equals(username)){
                return stack;
            }
        }
        return null;
    }

    /**
     * Save all the data of RegularUsers and AdminUsers.
     */
    @RequiresApi(api = Build.VERSION_CODES.R)
    public void saveAll(){
        SaveData saveData = new SaveData();
        ArrayList<Transaction> transactionsToSave = new ArrayList<>();
        for (RegularUser user: this.getRegularUsers()){
            saveData.saveData(user);
            for (Transaction transaction: user.getTransactions()){
                if (!transactionsToSave.contains(transaction)){
                    saveData.saveData(transaction);
                    transactionsToSave.add(transaction);
                }
            }
        }
        for (AdminUser user: this.getAdminUsers()) saveData.saveData(user);
    }

    /**
     * When data of action stacks (undo history) are read from file, this method will be called to
     * insert the data into the UserData class.
     * @param actionStacks data read from file.
     */
    public void updateUndoStacks(ArrayList<RegularUserActionStack> actionStacks){
        for (RegularUserActionStack outerStack: actionStacks){
            for (RegularUserActionStack innerStack: this.undoStacks){
                if (innerStack.getUsername().equals(outerStack.getUsername())){
                    innerStack.updateStack(outerStack.getActions());
                }
            }
        }
    }

    /**
     * update all the user's transaction list.
     * @param transactions the transactions read from file.
     */
    public void updateTransactions(ArrayList<Transaction> transactions){
        for (RegularUser user: this.getRegularUsers()){
            ArrayList<Transaction> temp = new ArrayList<>();
            for (Transaction tr: transactions){
                if (tr.borrower.getUsername().equals(user.getUsername()) ||
                    tr.lender.getUsername().equals(user.getUsername())){
                    temp.add(tr);
                }
            }
            user.substituteTransactionList(temp);
        }


    }
}

