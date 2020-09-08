package com.example.phase2.controller;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.HashMap;
import com.example.phase2.entity.*;
import com.example.phase2.database.*;
import com.example.phase2.gateway.*;

public class ApproveAddingItem {
    private boolean confirmAdding;
    private ArrayList<String> fileRows = new ArrayList<>();

    /**
     * Initialize an ApproveAddingItem class. Only AdminUser can call the method in this class.
     * @param confirmAdding true iff the Admin wants to add items. False iff the Admin rejects.
     */
    public ApproveAddingItem(boolean confirmAdding){
        this.confirmAdding = confirmAdding;
    }

    /**
     * Adding the item to the target user's self Item List iff Admin confirms adding. Else do nothing for the target User.
     * After that we delete the corresponding request in the itemAddingRequest.txt
     * @param username the target regular user's username
     * @param item the item to be added or dis-added
     * @param userData all data in this system
     */
    @RequiresApi(api = Build.VERSION_CODES.R)
    public void changingItem(String username, Item item, UserData userData) {
        RequestAddingItem requestAddingItem = new RequestAddingItem();
        if (confirmAdding) {
            item.setStatus(true);
            item.setUsername(username);
//            HashMap<String, RegularUser> userNameToRegUser = userData.hashRegularUsers();
//            if(userNameToRegUser.containsKey(username)){
//                RegularUser targetUser = userNameToRegUser.get(username);
            RegularUser targetUser = (RegularUser) userData.findUser(username);
            targetUser.execute("Admin has un-approved your item \"" + item.getName() + ".");
            targetUser.addInventory(item);
//                ArrayList<Item> itemsToOperate = targetUser.getInventory();
//                itemsToOperate.add(item);
//                targetUser.setInventory(itemsToOperate);
//                userData.addUser(targetUser);
            SaveData savingData = new SaveData();
            savingData.saveData(targetUser);
            savingData.saveData(item);
            AdminUser admin = (AdminUser) userData.findUser("PrimaryAdmin");
            admin.getItemRequests().get(username).remove(item);
            savingData.saveData(admin);
            requestAddingItem.deleteOneRow(username, item.getName(), item.getDescription());
            }
        else {

            SaveData savingData = new SaveData();
            savingData.saveData(item);
            AdminUser admin = (AdminUser) userData.findUser("PrimaryAdmin");
            admin.getItemRequests().get(username).remove(item);
            savingData.saveData(admin);
            requestAddingItem.deleteOneRow(username, item.getName(), item.getDescription());
        }
//
//        }else{
//            requestAddingItem.deleteOneRow(username, item.getName(), item.getDescription());
//        }


        }
    }

