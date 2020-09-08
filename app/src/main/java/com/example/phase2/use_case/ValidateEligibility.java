package com.example.phase2.use_case;

import android.annotation.TargetApi;
import android.os.Build;

import java.time.LocalDateTime;
import java.util.ArrayList;

import com.example.phase2.entity.*;

public class ValidateEligibility {

    public static int thresholdMaxTransactionPerWeek = 0;
    public static int maxIncompleteTransactions = 0;
    public static int thresholdLendingMinusBorrowing = 1;
    public static int thresholdMaxEditing = 6;

    private RegularUser userA;
    private RegularUser userB;
    private ArrayList<AdminUser> Admins;

    public ValidateEligibility(RegularUser starter, RegularUser receipt, ArrayList<AdminUser> admins){
        this.userA = starter;
        this.userB = receipt;
        this.Admins = admins;
    }

    public static void setThresholdMaxTransactionPerWeek(int threshold){
        ValidateEligibility.thresholdMaxTransactionPerWeek = threshold;
    }

    public static void setMaxIncompleteTransactions(int maxIncompleteTransactions) {
        ValidateEligibility.maxIncompleteTransactions = maxIncompleteTransactions;
    }

    public static void setThresholdLendingMinusBorrowing(int thresholdLendingMinusBorrowing) {
        ValidateEligibility.thresholdLendingMinusBorrowing = thresholdLendingMinusBorrowing;
    }

    public static void setThresholdMaxEditing(int thresholdMaxEditing) {
        ValidateEligibility.thresholdMaxEditing = thresholdMaxEditing;
    }

    private boolean validateLendingForOneUser(RegularUser user){
        ArrayList<Transaction> transactions = user.getTransactions();
        int counterLend = 0;
        int counterBorrow = 0;
        int incompleteCounter = 0;
        for (Transaction transaction: transactions){
            if(!transaction.getTransactionCompleteness()){
                incompleteCounter += 1;
            }
        }
        if(incompleteCounter <= maxIncompleteTransactions){
            for(Transaction transaction: transactions) {
                // since each user has a unique username, we can use username instead
                if (transaction instanceof OneWayTrade){
                    if (((OneWayTrade) transaction).getLenderName().equals(user.getUsername())){
                        counterLend += 1;
                    } else {
                        counterBorrow += 1;
                    }
                }
                else{
                    counterLend += 1;
                    counterBorrow += 1;
                }

            }
            if(counterLend - counterBorrow <= thresholdLendingMinusBorrowing){
                return true;
            }
            else{
                user.setSuspicious(true);
                return false;
            }
        }
        else{
            user.setSuspicious(true);
        }
        return false;
    }

    @TargetApi(Build.VERSION_CODES.O)
    private boolean validateMaxTransactionPerWeekForOneUser(RegularUser user){
        ArrayList<Transaction> transactions = user.getTransactions();
        int counterTransactionOneWeek = 0;
        LocalDateTime oneWeekBeforeNow = LocalDateTime.now().minusWeeks(1);
        for (Transaction transaction: transactions) {
            LocalDateTime time = transaction.getCreatedDate();
            if (time.isAfter(oneWeekBeforeNow)) {
                counterTransactionOneWeek += 1;
            }
        }
        return (ValidateEligibility.thresholdMaxTransactionPerWeek <= counterTransactionOneWeek);
    }


    public boolean validate(){
        return this.validateLendingForOneUser(this.userA) &&
                this.validateLendingForOneUser(this.userB) &&
                this.validateMaxTransactionPerWeekForOneUser(this.userA) &&
                this.validateMaxTransactionPerWeekForOneUser(this.userB);

    }
}
