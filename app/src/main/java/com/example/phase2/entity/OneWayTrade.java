package com.example.phase2.entity;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;

public class OneWayTrade extends Transaction {

    private Item item;

    /**
     * Initialize an one way trade
     * @param re a transaction request generated before this transaction
     * @param meetingTime the meeting time in real world to transact
     */

    @RequiresApi(api = Build.VERSION_CODES.O)
    public OneWayTrade(TransactionRequest re, LocalDateTime meetingTime) {
        super(re.getOwner(), re.getTheOtherUser(), re, meetingTime);
        this.item = re.getItemsToTrade().get(0);
    }

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

    public Item getItem() {
        return item;
    }

    /**
     * get all the information about this transaction in a string
     * @return all the information about this transaction in a string
     */



    @Override
    public String toString() {
        String completeStatus;
        if (this.getTransactionCompleteness()) {
            completeStatus = "This transaction is complete.";
        } else {
            completeStatus = "This transaction is incomplete.";
        }
        return "This is a one-way trade where " + this.getBorrowerName() + " borrows " + this.item.getName() + " from " +
                this.getLenderName() + ". \nMeeting time & place: " + this.getMeetingTime().toString() + " at " +
                this.getMeetingLocation() + ". \n" + completeStatus;
    }
}
