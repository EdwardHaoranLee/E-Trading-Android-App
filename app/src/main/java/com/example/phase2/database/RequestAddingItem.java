package com.example.phase2.database;

import android.os.Environment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import com.example.phase2.entity.*;

public class RequestAddingItem{
    private File file = new File(SaveData.context.getFilesDir().getPath() + "/" + "itemAddingRequest.txt");

    /**
     * A default constructor
     */
    public RequestAddingItem(){
    }

    /**
     * Add a username as well as the requesting adding item into the file
     * @param username the username request to add item
     * @param item the item to be added
     */
    public void addingToFiles(String username, Item item){

        try{
            if(!file.exists()) {
                file.createNewFile();
                FileOutputStream fos = new FileOutputStream(this.file);
                byte[] userNameByte = username.getBytes();
                byte[] itemNameBytes = item.getName().getBytes();
                byte[] itemDesc = item.getDescription().getBytes();

                fos.write(userNameByte);
                fos.write(";".getBytes());
                fos.write(itemNameBytes);
                fos.write(";".getBytes());
                fos.write(itemDesc);
                fos.write("\r\n".getBytes());
                fos.close();
            }else{
                FileWriter fw = new FileWriter(file.getName(), true);
                BufferedWriter bufferedWriter = new BufferedWriter(fw);
                bufferedWriter.write(username);
                bufferedWriter.write(";");
                bufferedWriter.write(item.getName());
                bufferedWriter.write(";");
                bufferedWriter.write(item.getDescription());
                bufferedWriter.write("\r\n");
                bufferedWriter.close();
            }}
        catch (IOException e){
            e.printStackTrace();
        }

        // To make the else block works, I refer to some of the code structure from https://zhuanlan.zhihu.com/p/64489647
        // This comments is to make sure that I will not commit Academic Offence.

    }

    /**
     * Delete all content in the file
     */
    public void deleteAllInFile(){
        try {
            PrintWriter printWriter = new PrintWriter(file);
            printWriter.print("");
            printWriter.close();
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    /**
     * Delete one element in this file
     * @param username the username who's the owner of the deleting item
     * @param itemName the item name that is going to be deleted
     * @param itemDesc the description that is going to be deleted.
     */
    public void deleteOneRow(String username, String itemName, String itemDesc){
        // Read from txt and delete one row
        HashMap<String, ArrayList<Item>> finalMap = new HashMap<>();
        try {
            if (!file.exists()) {
                file.createNewFile();
                return;
            }
            ReadingRequestAddingItem readingRequestAddingItem = new ReadingRequestAddingItem();
            HashMap<String, ArrayList<Item>> previousMap = readingRequestAddingItem.getUserNameToItemAfterReading();
            for(String userName: previousMap.keySet()){
                finalMap.put(userName, new ArrayList<Item>());
                ArrayList<Item> items = previousMap.get(userName);
                for(Item item: items){
                    if(!userName.equals(username) || !itemName.equals(item.getName()) || !itemDesc.equals(item.getDescription())){
                        finalMap.get(userName).add(item);
                    }
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        // Delete all file
        deleteAllInFile();
        // Add to txt
        for(String keyVal: finalMap.keySet()){
            ArrayList<Item> itemForThisKey = finalMap.get(keyVal);
            if(itemForThisKey.size() == 0){
                continue;
            }
            for(Item item: itemForThisKey){
                addingToFiles(keyVal, item);
            }

        }
    }

//    public void readingRowInFileAndDelete(String username, String itemName, String itemDesc){
//        ReadingRequestAddingItem readingRequestAddingItem = new ReadingRequestAddingItem();
//        HashMap<String, ArrayList<Item>> userToItems = readingRequestAddingItem.getUserNameToItemAfterReading();
//        ArrayList<Item> selfRequestItems = new ArrayList<>();
//        if(userToItems.containsKey(username)){
//            selfRequestItems = userToItems.get(username);
//        }
//        ArrayList<Item> selfRequestItems_copy = ((ArrayList<Item>) selfRequestItems.clone());
//        for(Item item: selfRequestItems_copy){
//            if(item.getName().equals(itemName) && item.getDescription().equals(itemDesc)){
//                selfRequestItems.remove(item);
//            }
//            if(selfRequestItems.size() == 0){
//                userToItems.remove(username);
//            }
//        }
//        RequestAddingItem requestAddingItem = new RequestAddingItem();
//        requestAddingItem.deleteAllInFile();
//        for(String userNameKey: userToItems.keySet()){
//            for(Item item: userToItems.get(userNameKey)){
//                requestAddingItem.addingToFiles(userNameKey, item);
//            }
//        }
//
//
//    }
}
