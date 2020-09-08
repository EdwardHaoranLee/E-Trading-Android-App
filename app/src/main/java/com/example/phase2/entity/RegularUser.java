package com.example.phase2.entity;


import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.util.ArrayList;
import java.time.LocalDateTime;

/**
 * An instance of this class is a regular user
 */
public class RegularUser extends User implements Serializable {

    private ArrayList<Transaction> transactions;
    private boolean isFrozen;
    private boolean isSuspicious;
    private ArrayList<Item> inventory;
    private ArrayList<Item> willingToBorrowItems;
    private ArrayList<Item> willingToLendItems;
    private ArrayList<String> partnerList;
    private ArrayList<Item> tradingItems;
    private ArrayList<TransactionRequest> transactionRequests;
    private boolean isBusy = false;
    private String unfreezeRequest = "";
    private boolean isUnfreezeRequestSent = false;



    /**
     * Initialize an instance of regular user
     * @param username username of the regular user
     * @param password password of the regular user
     */
    public RegularUser(String username, String password) {
        super(username, password, 1);
        boolean isFrozen = false;
        boolean isSuspicious = false;
        transactions = new ArrayList<>();
        inventory = new ArrayList<>();
        willingToLendItems = new ArrayList<>();
        willingToBorrowItems = new ArrayList<>();
        partnerList = new ArrayList<>();
        tradingItems = new ArrayList<>();
        transactionRequests = new ArrayList<>();
    }

    /**
     * The getter method of isBusy attribute
     * @return a bool of isBusy
     */
    public boolean getIsBusy() {
        return isBusy;
    }

    /**
     * The getter method of isBusy attribute
     * @return bool of whether or not the user is busy
     */
    public String getUnfreezeRequest() {
        return unfreezeRequest;
    }

    /**
     * The setter method of isBusy attribute
     * @param isBusy bool of whether or not the user is busy
     */
    public void setIsBusy(Boolean isBusy){
        this.isBusy = isBusy;
    }



    /**
     * The getter method of isFrozen attribute
     * @return bool of whether or not the user is frozen
     */
    public boolean getIsFrozen(){
        return isFrozen;
    }

    /**
     * The setter method of isFrozen attribute
     * @param frozen bool of whether or not the user is frozen
     */
    public void setFrozen(boolean frozen) {
        isFrozen= frozen;
        if(!frozen){
            setIsUnfreezeRequestSent(false);
            setUnfreezeRequest(null);
        }
    }

    /**
     * The setter method of isUnfreezeRequestSent attribute
     * @param isRequestSent bool of whether or not the user's unfrozen request has been sent
     */
    public void setIsUnfreezeRequestSent(boolean isRequestSent){
        isUnfreezeRequestSent = isRequestSent;
    }

    /**
     * The getter method of isUnfreezeRequestSent attribute
     * @return bool of whether or not the user's unfrozen request has been sent
     */
    public boolean getIsUnfreezeRequestSent(){
        return this.isUnfreezeRequestSent;
    }

    /**
     * The getter method of isUnfreezeRequestSent attribute
     * @return bool of whether or not the user's unfrozen request has been sent
     */
    public boolean getIsSuspicious(){ return isSuspicious; }

    /**
     * The setter method of isSuspicious attribute
     * @param suspicious bool of whether or not the user is suspicious
     */
    public void setSuspicious(boolean suspicious) {
        isSuspicious = suspicious;
    }

    /**
     * The getter method of the transactions attribute
     * @return an array list of transactions
     */
    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    /**
     * The getter method of the inventory attribute
     * @return an array list of items that are in the user's inventory
     */
    public ArrayList<Item> getInventory() {
        return inventory;
    }

    /**
     * The method for adding item to a user's inventory
     * @param item the item to be added
     */
    public void addInventory(Item item){
        this.inventory.add(item);
    }

    /**
     * The method for adding transaction request to the user's arraylist
     * @param re the transaction request to be added
     */
    public void setTransactionRequests(TransactionRequest re) {
        re.getOwner().executeMutual("Un-add transaction request from " +
                        re.getOwner().getUsername() + " to " + re.getTheOtherUser().getUsername(),
                re.getTheOtherUser() );
        this.transactionRequests.add(re);
    }

    /**
     * The method to add item to the user's willing to lend item list
     * @param item the item to be added
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setWillingToLendItems (Item item) {
        this.willingToLendItems.add(item);
        item.setShelfTime(LocalDateTime.now());
    }

    /**
     * The getter for tradingItems attribute
     * @return an arraylist of the trading items
     */
    public ArrayList<Item> getTradingItems() {
        return tradingItems;
    }

    /**
     * The getter for willingToBorrowItems attribute
     * @return an arraylist of the willing to borrow items
     */
    public ArrayList<Item> getWillingToBorrowItems() {
        return willingToBorrowItems;
    }

    /**
     * The method for adding item to the willingToBorrowItem arraylist
     * @param item the item to be added
     */
    public void addWillingToBorrowItem(Item item){
        this.willingToBorrowItems.add(item);
    }

    /**
     * The method for removing item from the willingToBorrowItem arraylist
     * @param item the item to be removed
     */
    public void removeFromWillingToBorrowItem(Item item){
        this.willingToBorrowItems.remove(item);
    }

    /**
     * The getter for willingToLendItems attribute
     * @return an arraylist of the willing to lend items
     */
    public ArrayList<Item> getWillingToLendItems() {
        return willingToLendItems;
    }

    /**
     * the getter for partnerList attribute
     * @return an arraylist of the username of the partners
     */
    public ArrayList<String> getPartnerList() {
        return partnerList;
    }

    /**
     * The getter for transactionRequests attribute
     * @return an arraylist of the transaction requests
     */
    public ArrayList<TransactionRequest> getTransactionRequests() {
        return transactionRequests;
    }

    /**
     * The method for removing target transaction request from the arraylist
     * @param tr the transaction request to be removed
     */
    public void removeTransactionRequest(TransactionRequest tr){
        tr.getOwner().executeMutual("Un-remove transaction request from " +
                        tr.getOwner().getUsername() + " to " + tr.getTheOtherUser().getUsername(),
                tr.getTheOtherUser());
        this.transactionRequests.remove(tr);
    }

    /**
     * The method to add transaction request to the arraylist
     * @param tr the transaction request to be added
     */
    public void addTransactionRequest(TransactionRequest tr){
        tr.getOwner().executeMutual("Un-add transaction request from " +
                        tr.getOwner().getUsername() + " to " + tr.getTheOtherUser().getUsername(),
                tr.getTheOtherUser());
        this.transactionRequests.add(tr);
    }

    /**
     * The method for adding transaction to the transaction arraylist
     * @param tr the transaction to be added
     */
    public void addTransaction(Transaction tr){
        tr.lender.executeMutual("Un-add transaction between " +
                        tr.lender.getUsername() + " and " + tr.borrower.getUsername(),
                tr.borrower);
        this.transactions.add(tr);
    }

    /**
     * This method will notify the observers, i.e., the RegularUserActionStack, or the undo history
     * class, so the observers can store copies of user to be undone.
     * @param notice the information to display when doing undone.
     */
    public void execute(String notice){
        setChanged();
        notifyObservers(new UndoNotification(notice));
    }

    /**
     * This method will do similar things as execute() method, but it will record action that affect
     * two users.
     * @param notice the information to be displayed.
     * @param mutualUser the other affected user.
     */
    public void executeMutual(String notice, RegularUser mutualUser){
        UndoNotification notification = new UndoNotification(notice, mutualUser);
        UndoNotification notification1 = new UndoNotification(notice, this);
        notification1.setId(notification.getId());
        this.setChanged();
        this.notifyObservers(notification);
        mutualUser.setChanged();
        mutualUser.notifyObservers(notification1);
    }

    /**
     * The setter for theh unfreezeRequest attribute
     * @param request a String of the unfreeze account request
     */
    public void setUnfreezeRequest(String request){
        unfreezeRequest = request;
    }

    /**
     * Generate a deep copy of this user at present status. Thus, the change of this user will not
     * affect its history.
     * @return the deep copy of this user.
     */
    public RegularUser deepCopy(){
        RegularUser copy = new RegularUser(this.getUsername(), this.getPassword());

        copy.transactions.addAll(this.transactions);
        copy.isFrozen = this.isFrozen;
        copy.isSuspicious = this.isSuspicious;
        copy.inventory.addAll(this.inventory);
        copy.willingToBorrowItems.addAll(this.willingToBorrowItems);
        copy.willingToLendItems.addAll(this.willingToLendItems);
        copy.partnerList.addAll(this.partnerList);
        copy.tradingItems.addAll(this.tradingItems);
        copy.transactionRequests.addAll(this.transactionRequests);
        return copy;
    }

    /**
     * Similarly, this method will recover this user from a history version.
     * @param user the history.
     */
    public void deepPaste(RegularUser user){
        this.transactions = user.transactions;
        this.isFrozen = user.isFrozen;
        this.isSuspicious = user.isSuspicious;
        this.inventory = user.inventory;
        this.willingToBorrowItems = user.willingToBorrowItems;
        this.willingToLendItems = user.willingToLendItems;
        this.partnerList = user.partnerList;
        this.tradingItems = user.tradingItems;
        this.transactionRequests = user.transactionRequests;
    }

    /**
     * This method is used when initializing the system to load the transaction in the file.
     * @param transactions the new transaction list read from file that related to this user.
     */
    public void substituteTransactionList(ArrayList<Transaction> transactions){
        this.transactions = new ArrayList<>();
        this.transactions.addAll(transactions);
    }

}
