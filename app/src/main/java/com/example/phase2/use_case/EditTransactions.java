package com.example.phase2.use_case;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import com.example.phase2.entity.*;
import com.example.phase2.use_case.*;

public class EditTransactions {
    // lotus added two getters and two setters for the number of incomplete transactions
    // , items borrowed and items lent

    RegularUser currentUser;

    /**
     * The target user edits the transaction's attributes
     * @param currentUser the target RegularUser
     */
    public void setCurrentUser(RegularUser currentUser) {
        this.currentUser = currentUser;
    }

    // chen added addTransaction for ConfirmMeeting class
    public void addTransaction(Transaction tr){
        String stringTransaction = tr.getBorrowerName() + ";" + tr.getLenderName() + ";" + tr.getMeetingLocation() + ";" + tr.getMeetingTime() + ";" +tr.getIsActive() + ";" + tr.getDueDate() + ";" + tr.getIsComplete() + ";" + tr.getTransactionType() + ";" + tr.getCreatedDate()+ ";" + tr.getConfirmationNum();
        currentUser.addTransaction(tr);
        try {
            File file = new File("transactions.txt");
            FileWriter fw = new FileWriter(file);
            fw.write(stringTransaction);
            fw.close();
        } catch (IOException e) { }
    }

}
