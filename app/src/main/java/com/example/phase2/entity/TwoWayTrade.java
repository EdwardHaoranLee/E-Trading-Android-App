package com.example.phase2.entity;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;

public class TwoWayTrade extends Transaction {

    private Item borrowerItem;
    private Item lenderItem;

    /**
     * Initialize a two way trade
     * @param re a transaction request generated before this transaction
     * @param meetingTime the meeting time in real world to transact
     */

    @RequiresApi(api = Build.VERSION_CODES.O)
    public TwoWayTrade(TransactionRequest re, LocalDateTime meetingTime) {

        super(re.getOwner(), re.getTheOtherUser(),re, meetingTime);
        this.borrowerItem = re.getItemsToTrade().get(0);
        this.lenderItem = re.getItemsToTrade().get(1);
    }

    // in two way trade, both sides are lender and borrower at the same time

    /**
     * get the borrower's name in this transaction
     * @return the borrower's name in this transaction
     */

    @Override
    public String getBorrowerName() {
        return borrower.getUsername();
    }

    /**
     * get the lender's name in this transaction
     * @return the lender's name in this transaction
     */

    @Override
    public String getLenderName() {
        return lender.getUsername();
    }

    /*
    Need to add time and place proposal for TwoWayTrade class
     */

    public Item getBorrowerItem() {
        return borrowerItem;
    }

    public Item getLenderItem() {
        return lenderItem;
    }

    @Override
    public String toString() {
        String completeStatus;
        if (this.getTransactionCompleteness()) {
            completeStatus = "This transaction is complete.";
        } else {
            completeStatus = "This transaction is incomplete.";
        }
        return "This is a two-way trade where " + this.getBorrowerName() + " swaps " + this.lenderItem.getName() +
                " from " + this.getLenderName() + " for "+ this.borrowerItem.getName() + ". \nMeeting time & place: " +
                this.getMeetingTime().toString() + " at " + this.getMeetingLocation() + ". \n" + completeStatus;
    }

}