package com.example.phase2.controller;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.phase2.activities.MainActivity;
import com.example.phase2.entity.*;
import com.example.phase2.gateway.UserData;
import com.example.phase2.use_case.*;

public class ConfirmMeeting {

    /**
     * This class is used to confirm the meeting when both users made an agreement of meeting time and place.
     */
    private RegularUser userA;
    private RegularUser userB;
    private TransactionRequest re; // The transaction request that was used to communicate between two users.
    private UserData ud = MainActivity.getUserData();

    public ConfirmMeeting(TransactionRequest re) {
        this.userA = (RegularUser) ud.findUser(re.getOwner().getUsername());
        this.userB = (RegularUser) ud.findUser(re.getTheOtherUser().getUsername());
        this.re = re;
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    public void confirmMeeting(){
        /**
         * This method is used to confirm meeting. It will generate the transaction to return the
         * object if the first transaction is temporary.
         */
        this.userA.executeMutual("Undo confirming meeting of " + userA.getUsername() + " and " + userB.getUsername(), this.userB);
        Transaction tr;
        if (this.re.getIsTwoWay()) {
            tr = new TwoWayTrade(re, re.getTime());
        } else {
            tr = new OneWayTrade(re, re.getTime());
        }
        EditTransactions editTransactionsUserA = new EditTransactions();
        editTransactionsUserA.setCurrentUser(userA);
        EditTransactions editTransactionsUserB = new EditTransactions();
        editTransactionsUserB.setCurrentUser(userB);
        editTransactionsUserA.addTransaction(tr);
        editTransactionsUserB.addTransaction(tr);
        if (this.re.getIsTemporary()){
            Transaction returnTransaction;
            re.setTime(re.getTime().plusMonths(1));
            if (this.re.getIsTwoWay()) {
                returnTransaction = new TwoWayTrade(re, tr.getDueDate());
            } else {
                returnTransaction = new OneWayTrade(re, tr.getDueDate());
            }
            editTransactionsUserA.addTransaction(returnTransaction);
            editTransactionsUserB.addTransaction(returnTransaction);
        }

    }
}
