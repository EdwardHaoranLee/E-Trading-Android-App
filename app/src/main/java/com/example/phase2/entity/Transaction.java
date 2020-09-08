package com.example.phase2.entity;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Abstract class Transaction
 */
public abstract class Transaction implements Serializable {

    private String meetingLocation;
    private LocalDateTime meetingTime;
    private boolean isActive = false;
    private LocalDateTime dueDate = null;
    public RegularUser borrower;
    public RegularUser lender;
    private boolean isComplete;
    private int confirmationNum;
    public LocalDateTime createdDate = null;
    public String transactionType;

    /**
     * Initialize a transaction
     * @param borrower the one who borrows an item in this transaction
     * @param lender the one who lends an item in this transaction
     * @param re a transaction request generated before this transaction is confirmed
     * @param date the local date of the creation of this transaction
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Transaction(RegularUser borrower, RegularUser lender,
                       TransactionRequest re, LocalDateTime date){
        //
        this.borrower = borrower;
        this.lender = lender;
        //
        this.meetingLocation = re.getLocation();
        this.meetingTime = re.getTime();
        this.isActive = true;
        this.isComplete = false;
        if (re.getIsTwoWay() && re.getIsTemporary()){
            this.transactionType = "TwoWay Temporary";
        }
        else if (!re.getIsTwoWay() && !re.getIsTemporary()) {
            this.transactionType = "OneWay Permanent";
        }
        else if (re.getIsTwoWay() && !re.getIsTemporary()) {
            this.transactionType = "TwoWay Permanent";
        }
        else {
            this.transactionType = "OneWay Temporary";
        }
        this.dueDate = re.getTime();
        this.createdDate = date;
        this.confirmationNum = 0;
    }

    /**
     * get the real world meeting time for this transaction
     * @return the real world meeting time this.meetingTime
     */
    public LocalDateTime getMeetingTime(){
        return this.meetingTime;
    }

    /**
     * get the due date for this transaction
     * @return the due date this.dueDate
     */
    public LocalDateTime getDueDate() { return this.dueDate; }

    /**
     * get the date of creation for this transaction
     * @return the date of creation this.createdDate
     */
    public LocalDateTime getCreatedDate(){
        return this.createdDate;
    }

    /**
     * check whether this transaction is active or not
     * @return the activeness this.isActive of this transaction
     */
    public Boolean getIsActive(){
        return this.isActive;
    }

    /**
     * check whether this transaction is completed or not
     * @return the completeness this.isComplete of this transaction
     */
    public Boolean getIsComplete(){
        return this.isComplete;
    }

    /**
     * get the type of this transaction
     * @return the type this.transactionType of this transaction
     */
    public String getTransactionType(){
        return this.transactionType;
    }

    /**
     * get the number of confirmation of this transaction
     * @return the number this.confirmationNum of this transaction
     */
    public int getConfirmationNum(){
        return this.confirmationNum;
    }

    /**
     * set the number of confirmation of this transaction
     * @param num1 the number of confirmation will be incremented by num1
     */
    public void setConfirmationNum(int num1){
        this.confirmationNum += num1;
    }

    /**
     * check whether this transaction is completed or not
     * @return the completeness this.isComplete of this transaction
     */
    public boolean getTransactionCompleteness(){
        return this.isComplete;
    }

    /**
     * set the completeness of this transaction
     * @param b the completeness of this transaction will be set to b
     */
    public void setTransactionCompleteness(boolean b){
        this.isComplete = b;
    }

    /**
     * set the activeness of this transaction
     * @param a the activeness of this transaction will be set to a
     */
    public void setIsActive(boolean a){
        this.isActive = a;
    }

    /**
     * abstract method of getting the borrower's name
     */
    public abstract String getBorrowerName();

    /**
     * abstract method of getting the lender's name
     */
    public abstract String getLenderName();

    /**
     * get the meeting location
     * @return the meeting location this.meetingLocation
     */

    public void setBorrower (RegularUser user) {
        this.borrower = user;
    }

    public void setLender (RegularUser user) {
        this.lender = user;
    }

    public String getMeetingLocation() { return this.meetingLocation; }

    public boolean isTheSameTransaction(Transaction tr){
        if (((this.lender.getUsername().equals(tr.lender.getUsername()) &&
                this.borrower.getUsername().equals(tr.borrower.getUsername())) ||
                (this.lender.getUsername().equals(tr.borrower.getUsername()) &&
                this.borrower.getUsername().equals(tr.lender.getUsername()))) &&
            this.createdDate.equals(tr.createdDate) &&
            this.transactionType.equals(tr.transactionType) &&
            this.meetingLocation.equals(tr.meetingLocation) &&
            this.dueDate.equals(tr.dueDate)){
            return true;
        } else {
            return false;
        }
    }

}