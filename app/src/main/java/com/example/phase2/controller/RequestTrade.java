package com.example.phase2.controller;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import com.example.phase2.entity.*;
import com.example.phase2.exception.*;
import com.example.phase2.use_case.*;

public class RequestTrade {
    /**
     * This class is used to request trade from a user.
     */
    private RegularUser tradeRequestUser;
    private RegularUser tradeAcceptUser;
    public ArrayList<AdminUser> AdminUsers;

    public RequestTrade(RegularUser tradeStarter, RegularUser tradeReceiver, Item item) throws ItemNotApprovedException{
        if (!item.getStatus()) {
            throw new ItemNotApprovedException();
        }
        this.tradeRequestUser = tradeStarter;
        this.tradeAcceptUser = tradeReceiver;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public TransactionRequest requestTrade(int year, int month, int date, int hours, int minute,
                                           String location, ArrayList<Item> items, boolean isTwoWay, boolean isTemporary)
            throws DateTimeException, NotValidForTransactionException {
        /**
         * This method is used to request trade from some user. The user will input an initial time and place,
         * and transaction type from UI, and then this method will generate a transaction request to communicate
         * between users.
         */

        ValidateEligibility validation = new ValidateEligibility(this.tradeRequestUser, this.tradeAcceptUser, AdminUsers);
        if (!validation.validate()){
            throw new NotValidForTransactionException();
        }

        LocalDateTime time = LocalDateTime.of(year, month, date, hours, minute);
        return new TransactionRequest(this.tradeRequestUser, this.tradeAcceptUser,
                location, time, items, isTwoWay, isTemporary);
    }

}
