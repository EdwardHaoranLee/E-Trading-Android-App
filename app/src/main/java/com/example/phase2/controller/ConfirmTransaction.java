package com.example.phase2.controller;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.phase2.activities.MainActivity;
import com.example.phase2.database.SaveData;
import com.example.phase2.entity.Item;
import com.example.phase2.entity.OneWayTrade;
import com.example.phase2.entity.RegularUser;
import com.example.phase2.entity.Transaction;
import com.example.phase2.entity.TwoWayTrade;
import com.example.phase2.gateway.UserData;

public class ConfirmTransaction {

    private UserData ud = MainActivity.getUserData();

    // we also need a ConfirmationTransaction controller to call this use case
    // if this use case is called twice, then the transaction is completed

    /**
     * increment the confirmNum by one when confirmTransaction is called one time
     * @param t the transaction being operated on
     */
    @RequiresApi(api = Build.VERSION_CODES.R)
    public void confirmTransaction(Transaction t){
        t.borrower.executeMutual("Undo confirming transaction of " + t.borrower.getUsername() +
                " and " + t.lender.getUsername() + ".", t.lender);
        t.setConfirmationNum(1);
        if(t.getConfirmationNum() == 2){
            t.setTransactionCompleteness(true);
            RegularUser userA = (RegularUser) ud.findUser(t.getBorrowerName());
            RegularUser userB = (RegularUser) ud.findUser(t.getLenderName());
            if (t.getTransactionType().equals("OneWay Permanent")|| t.getTransactionType().equals("OneWay Temporary")) {
                OneWayTrade oneWay = (OneWayTrade) t;
                Item item = oneWay.getItem();
                for (Item i : userB.getInventory()) {
                    if (i.getName().equals(item.getName()) && i.getDescription().equals(item.getDescription())) {
                        if (t.getTransactionType().equals("OneWay Permanent")) {
                        i.setUsername(userA.getUsername());
                        }
                        t.setBorrower(userB);
                        t.setLender(userA);
                        userB.getInventory().remove(i);
                        userB.getWillingToLendItems().remove(i);
                        userA.getInventory().add(i);
                    }
                }
            }
            else if (t.getTransactionType().equals("TwoWay Permanent")|| t.getTransactionType().equals("TwoWay Temporary")) {
                TwoWayTrade twoWay = (TwoWayTrade) t;
                Item senderItem = twoWay.getBorrowerItem();
                Item receiverItem = twoWay.getLenderItem();
                for (Item i : userB.getInventory()) {
                    if (i.getName().equals(receiverItem.getName()) && i.getDescription().equals(receiverItem.getDescription())) {
                        if (t.getTransactionType().equals("TwoWay Permanent")) {
                            i.setUsername(userA.getUsername());
                        }
                        userB.getInventory().remove(i);
                        userB.getWillingToLendItems().remove(i);
                        userA.getInventory().add(i);
                    }
                }
                for (Item i : userA.getInventory()) {
                    if (i.getName().equals(senderItem.getName()) && i.getDescription().equals(senderItem.getDescription())) {
                        if (t.getTransactionType().equals("TwoWay Permanent")) {
                            i.setUsername(userB.getUsername());
                        }
                        userA.getInventory().remove(i);
                        userA.getWillingToLendItems().remove(i);
                        userB.getInventory().add(i);
                    }
                }
            }
            SaveData saveData = new SaveData();
            saveData.saveData(userA);
            saveData.saveData(userB);
        }
        t.setIsActive(false);
    }
}
