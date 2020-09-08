package com.example.phase2.entity;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.phase2.exception.NotValidForTransactionException;
import com.example.phase2.use_case.ValidateEligibility;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
public class TransactionRequest implements java.io.Serializable{

    private int id;
    private RegularUser owner;
    private String location;
    private LocalDateTime time;
    private boolean isAccepted;
    private ArrayList<Item> itemsToTrade;

    //Lotus added new attribute requestType(one way/two way)
    private boolean isTwoWay;

    //Edward added new attributes
    private boolean isTemporary;
    private RegularUser theOtherUser;
    private int editCounter;
    public int requestNum;

    /**
     * The constructor of a transactionrequest
     * @param owner The regular user who is the current owner of the transaction request
     * @param theOtherUser The regular user who is the a part of the transaction request but also not the owner
     * @param location The geographical location where the transaction would take place
     * @param time The datetime when the transaction would take place
     * @param items The items to be traded
     * @param isTwoWay Whether or not the transaction will be a two way transaction
     * @param isTemporary Whether or not the transaction is a temporary transaction
     */
    public TransactionRequest(RegularUser owner, RegularUser theOtherUser, String location, LocalDateTime time,
                              ArrayList<Item> items, boolean isTwoWay, boolean isTemporary) {
        this.owner = owner;
        this.theOtherUser = theOtherUser;
        this.location = location;
        this.time = time;
        this.isAccepted = false;
        this.itemsToTrade = items;
        this.isTwoWay = isTwoWay;
        this.isTemporary = isTemporary;
        this.editCounter = 0;
        this.requestNum = 0;
    }

    /**
     * Setting an arrayList of itemsToTrade
     * @param itemsToTrade an arrayList of items to trade
     */
    public void setItemsToTrade(ArrayList<Item> itemsToTrade) {
        this.itemsToTrade = itemsToTrade;
    }

    /**
     * Setting whether this trade is Temporary
     * @param temporary a setter method of whether this trade is temporary
     */
    public void setTemporary(boolean temporary) {
        isTemporary = temporary;
    }

    /**
     * Setting if this trade is accepted
     * @param accepted the traded is accepted or not
     */
    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    /**
     * Setting the local date time of this trade
     * @param time setting the local date time
     */
    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    /**
     * Setting the location of this trade
     * @param location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * The getter of Owner attribute
     * @return the RegularUser who is the owner of the transaction request
     */
    public RegularUser getOwner() {
        return this.owner;
    }

    /**
     * The getter of theOtherUser attribute
     * @return the RegularUser who is a part of the transaction request but not the owner
     */
    public RegularUser getTheOtherUser(){
        return this.theOtherUser;
    }

    /**
     * The getter of location attribute
     * @return the String of the geographic location of the transaction request
     */
    public String getLocation(){
        return this.location;
    }

    /**
     * The getter of isTwoWay attribute
     * @return the Boolean of weather or not the transaction request is two way
     */
    public boolean getIsTwoWay(){
        return this.isTwoWay;
    }

    /**
     * The getter of isTemporary attribute
     * @return the Boolean of weather or not the transaction request is temporary
     */
    public boolean getIsTemporary(){
        return this.isTemporary;
    }

    /**
     * The getter of isAccepted attribute
     * @return the Boolean of weather or not the transaction request is accepted, which means both the owner and the
     * other user agreed on everything of the transaction.
     */
    public boolean getIsAccepted(){
        return this.isAccepted;
    }

    /**
     * The getter of time attribute
     * @return the Datetime when the transaction would take place.
     */
    public LocalDateTime getTime(){
        return this.time;
    }

//    public boolean checkIfAccepted() {
//        return this.isAccepted;
//    }

    /**
     * The getter of itemsToTrade attribute
     * @return the ArrayList of items to trade.
     */
    public ArrayList<Item> getItemsToTrade(){
        return this.itemsToTrade;
    }

    /**
     * Get how many times this request has been edited
     * @return this.editCounter
     */

    public int getEditCounter(){
        return this.editCounter;
    }

    public String toString() {
        return "Request Number " + this.requestNum + ": This transaction is going to happen at " + this.getLocation() +
                " on " + this.getTime();
    }



    /**
     *
     * @param location The String of the geographical location where the transaction would take place
     * @param year The int of the year when the transaction would take place
     * @param month The int of the month when the transaction would take place
     * @param date The int of the date when the transaction would take place
     * @param hours The int of the hour when the transaction would take place
     * @param minute The minute when the transaction would take place
     * @throws DateTimeException throws exception when the entered datetime is not valid.
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public int modifyRequest(String location, int year, int month, int date, int hours, int minute)
            throws DateTimeException {
        this.getOwner().executeMutual("Un-modify transaction request from " +
                this.getOwner().getUsername() + " to " + this.getTheOtherUser().getUsername(),
                this.getTheOtherUser());
        if (this.editCounter <= ValidateEligibility.thresholdMaxEditing) {
            this.time = LocalDateTime.of(year, month, date, hours, minute);
            this.location = location;
            RegularUser tempUser = this.owner;
            this.owner = this.theOtherUser;
            this.theOtherUser = tempUser;
            this.editCounter += 1;
            if (this.isTwoWay){
                Collections.swap(this.itemsToTrade, 0, 1);
            }
            return 0;
        }
        else {
            return 1;
        }
    }
}
