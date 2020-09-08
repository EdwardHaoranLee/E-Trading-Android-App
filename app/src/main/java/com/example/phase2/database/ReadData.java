package com.example.phase2.database;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import com.example.phase2.entity.*;
import com.example.phase2.use_case.RegularUserActionStack;

public class ReadData {

    private HashMap<String, AdminUser> storedAdminUsers;
    private HashMap<String, RegularUser> storedRegularUsers;
    private ArrayList<Item> itemInventory;
    private ArrayList<TransactionRequest> requests;
    private ArrayList<RegularUserActionStack> actionStacks;
    private ArrayList<Transaction> transactions;
    private File file;

    /**
     * A default constructor
     */
    public ReadData(){}


    /**
     * A read file method that modify the HashMap or the array Lists
     * @param filename a string indicate the file path of the file to be read
     * @throws ClassNotFoundException a exception thrown once the file contains no Object
     */
    @RequiresApi(api = Build.VERSION_CODES.R)
    private void readFile(String filename) throws ClassNotFoundException{
        try {
            file = new File(filename);
            Log.e("Read Storing data: ### ", file.getAbsolutePath()+"<---");
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            ObjectInputStream ois = new ObjectInputStream(bis);
            if(filename.equals(SaveData.context.getFilesDir().getPath() + "/" + SaveData.ITEM_INVENTORY_FILENAME)){
                this.itemInventory = (ArrayList<Item>) ois.readObject(); // Return IOException, what's wrong??
            }else if(filename.equals(SaveData.context.getFilesDir().getPath() + "/" + SaveData.ADMINUSER_FILENAME )) {
                this.storedAdminUsers = (HashMap<String, AdminUser>) ois.readObject();
            }else if(filename.equals(SaveData.context.getFilesDir().getPath() + "/" + SaveData.REGULARUSER_FILENAME)){
                this.storedRegularUsers = (HashMap<String, RegularUser>) ois.readObject();
            }else if(filename.equals(SaveData.context.getFilesDir().getPath() + "/" + SaveData.ACTION_STACK_FILENAME)){
                this.actionStacks = (ArrayList<RegularUserActionStack>) ois.readObject();
            }else if(filename.equals(SaveData.context.getFilesDir().getPath() + "/" + SaveData.REQUESTS_FILENAME)){
                this.requests = (ArrayList<TransactionRequest>) ois.readObject();
            }else if(filename.equals(SaveData.context.getFilesDir().getPath() + "/" + SaveData.TRANSACTION_FILENAME)){
                this.transactions = (ArrayList<Transaction>) ois.readObject();
            }
            fis.close();
            bis.close();
            ois.close();
        }catch (IOException e){
            int x = 1;
        }
    }


    /**
     * readingRegularUsers from file regularUser.ser
     * @return a HashMap that contains all RegularUsers
     */

    @RequiresApi(api = Build.VERSION_CODES.R)
    public HashMap<String, AdminUser> readAdminUsers(){
        try
        {
            readFile(SaveData.context.getFilesDir().getPath() + "/" + SaveData.ADMINUSER_FILENAME);
            if(storedAdminUsers == null){
                return new HashMap<>();
            }
            return storedAdminUsers;
        }catch (ClassNotFoundException e){
            return new HashMap<>();
        }
    }

    /**
     * Return a hash map whose key is the regularUser's name and the value is its corresponding object
     * @return a hash map whose key is the regularUser's name and the value is its corresponding object
     */
    @RequiresApi(api = Build.VERSION_CODES.R)
    public HashMap<String, RegularUser> readRegularUsers(){
        try
        {
            readFile(SaveData.context.getFilesDir().getPath() + "/" + SaveData.REGULARUSER_FILENAME);
            if(storedRegularUsers == null){
                return new HashMap<>();
            }
            return storedRegularUsers;
        }catch (ClassNotFoundException e){
            return new HashMap<>();
        }
    }

    /**
     * Return an array List that are the items stored in the ArrayList inventory.
     * @return a list of items stored in the inventory
     */
    @RequiresApi(api = Build.VERSION_CODES.R)
    public ArrayList<Item> readItemInventory(){
        try{
            readFile(SaveData.context.getFilesDir().getPath() + "/" + SaveData.ITEM_INVENTORY_FILENAME);
            if(itemInventory == null){
                return new ArrayList<>();
            }
            return itemInventory;
        }catch (ClassNotFoundException e){
            return new ArrayList<>();
        }
    }

    /**
     * Read the request file.
     * @return a list of transaction requests.
     */
    @RequiresApi(api = Build.VERSION_CODES.R)
    public ArrayList<TransactionRequest> readRequests(){
        try{
            readFile(SaveData.context.getFilesDir().getPath() + "/" + SaveData.REQUESTS_FILENAME);
            if(requests == null){
                return new ArrayList<>();
            }
            return requests;
        }catch (ClassNotFoundException e){
            return new ArrayList<>();
        }
    }

    /**
     * Read the undo history from file.
     * @return a list of undo histories.
     */
    @RequiresApi(api = Build.VERSION_CODES.R)
    public ArrayList<RegularUserActionStack> readActionStack(){
        try {
            readFile(SaveData.context.getFilesDir().getPath() + "/" + SaveData.ACTION_STACK_FILENAME);
            if (actionStacks == null){
                return new ArrayList<>();
            }
            return actionStacks;
        }catch (ClassNotFoundException e){
            return new ArrayList<>();
        }
    }

    /**
     * Read the saved transactions from file.
     * @return a list of saved transactions.
     */
    public ArrayList<Transaction> readTransactions(){
        try {
            readFile(SaveData.context.getFilesDir().getPath() + "/" + SaveData.TRANSACTION_FILENAME);
            if (transactions == null){
                return new ArrayList<>();
            }else {
                return transactions;
            }
        } catch (ClassNotFoundException e){
            return new ArrayList<>();
        }
    }



}
