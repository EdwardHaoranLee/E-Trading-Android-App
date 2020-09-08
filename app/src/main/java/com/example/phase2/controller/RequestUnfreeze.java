package com.example.phase2.controller;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.HashMap;
import com.example.phase2.database.*;
import com.example.phase2.use_case.AddingNewMessageForUser;
import com.example.phase2.entity.*;

@RequiresApi(api = Build.VERSION_CODES.R)
public class RequestUnfreeze {

    /**
     * The empty constructor of RequestUnfreeze.
     */
    public RequestUnfreeze(){}
    ReadData read = new ReadData();
    HashMap<String, AdminUser> adminUsers = read.readAdminUsers();
    AddingNewMessageForUser addingNewMessageForUser;

    /**
     * Regular users who are frozen can use this method to send a request to unfreeze his/her account
     * @param username The username of the regular user who requests to be unfrozen
     */
    public void requestUnfreeze(String username) {
        addingNewMessageForUser = new AddingNewMessageForUser();
        for (HashMap.Entry<String, AdminUser> entry : adminUsers.entrySet()) {
            addingNewMessageForUser.setTargetUser(adminUsers.get(entry));
            addingNewMessageForUser.addNewMessage("User " + username + " requests account unfreeze.");
        }
    }

}

//import java.util.HashMap;
//
//public class RequestUnfreeze {
//
//    RequestUnfreeze(){}
//    ReadData read = new ReadData();
//    HashMap<String, AdminUser> adminUsers = read.readAdminUsers();
//
//    public void requestUnfreeze(String username) {
//        for (HashMap.Entry<String, AdminUser> entry : adminUsers.entrySet()) {
//            adminUsers.get(entry).addNewMessage("User " + username + " requests account unfreeze.");
//        }
//    }
//
//}
