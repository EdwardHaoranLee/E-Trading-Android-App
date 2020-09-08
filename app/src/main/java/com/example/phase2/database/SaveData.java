package com.example.phase2.database;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import com.example.phase2.entity.*;
import com.example.phase2.use_case.RegularUserActionStack;

@RequiresApi(api = Build.VERSION_CODES.R)
public class SaveData<mDiskCache> {

    private HashMap<String, RegularUser> regularUsersToAdd;
    private HashMap<String, AdminUser> adminUsersToAdd;
    private ArrayList<Item> itemsToAdd;
    private ArrayList<TransactionRequest> requestsToAdd;
    private ArrayList<RegularUserActionStack> actionStacksToAdd;
    private ArrayList<Transaction> transactionsToAdd;
    //discuss if the name should be changed to "current users, current items"
    private String filename;
    public static Context context;

    public static final String ITEM_INVENTORY_FILENAME = "itemInventory.ser";
    public static final String REGULARUSER_FILENAME = "regularUser.ser";
    public static final String ADMINUSER_FILENAME =  "adminUser.ser";
    public static final String REQUESTS_FILENAME = "requests.ser";
    public static final String ACTION_STACK_FILENAME = "actionStack.ser";
    public static final String DEMOUSER_FILENAME = "demoUser.ser";
    public static final String TRANSACTION_FILENAME = "transactions.ser";


    /**
     * Default constructor
     */
    public SaveData(Context context){
        this.context = context;
    }

    public SaveData(){};

    /**
     * Removing the obj from the data set
     * @param obj the object(either RegularUser, AdminUser, Item or RegularUserActionStack) to be removed from specific file.
     */
    @RequiresApi(api = Build.VERSION_CODES.R)
    public void removeData(Object obj) {
        File file;
        ReadData readData = new ReadData();
        if(obj instanceof Item){
            file = new File(context.getFilesDir(), ITEM_INVENTORY_FILENAME);
            itemsToAdd = readData.readItemInventory();
            if (itemsToAdd == null || itemsToAdd.size() == 0) {
                itemsToAdd = new ArrayList<>();
            }
            for (Item item : itemsToAdd)
                if (item.getItemNumber() == ((Item) obj).getItemNumber()) {
                    itemsToAdd.remove(obj);
                }

        }else if(obj instanceof RegularUser){
            file = new File(context.getFilesDir(), REGULARUSER_FILENAME);
            regularUsersToAdd = readData.readRegularUsers();
            if(regularUsersToAdd == null){
                regularUsersToAdd = new HashMap<>();
            }
            regularUsersToAdd.remove(((RegularUser)obj).getUsername());
        }else if(obj instanceof AdminUser){
            file = new File(context.getFilesDir(), ADMINUSER_FILENAME);
            adminUsersToAdd = readData.readAdminUsers();
            if(adminUsersToAdd == null){
                adminUsersToAdd = new HashMap<>();
            }
            adminUsersToAdd.remove(((AdminUser)obj).getUsername());
        }else{
            file = new File(context.getFilesDir(), "otherObjects.ser");
            // IMPORTANT!! This is for extension, no objects should be stored in this ser.
            // You can still add any filename here if you want to store other entity.
        }

        // There should be multiple same items, hence item should be stored in an array list.

        try{
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            if(obj instanceof RegularUser){
                objectOutputStream.writeObject(regularUsersToAdd);
            }else if(obj instanceof AdminUser){
                objectOutputStream.writeObject(adminUsersToAdd);
            }else if(obj instanceof Item){
                objectOutputStream.writeObject(itemsToAdd);
            }else{
                // This is also for extension only
            }

            fileOutputStream.close();
            objectOutputStream.close();
        }catch (IOException e){

        }
    }

    /**
     * An obj to be saved into the data Set
     * @param obj an obj that is going to be saved into the specific file.
     */
    public void saveData(Object obj){
        File file;
        ReadData readData = new ReadData();
        if(obj instanceof Item){
            file = new File(context.getFilesDir(), ITEM_INVENTORY_FILENAME);
            itemsToAdd = readData.readItemInventory();
            if (itemsToAdd == null || itemsToAdd.size() == 0) {
                itemsToAdd = new ArrayList<>();
            }
            itemsToAdd.add((Item) obj);
        }else if(obj instanceof RegularUser){
            file = new File(context.getFilesDir(), REGULARUSER_FILENAME);
            regularUsersToAdd = readData.readRegularUsers();
            if(regularUsersToAdd == null){
                regularUsersToAdd = new HashMap<>();
            }
            regularUsersToAdd.put(((RegularUser)obj).getUsername(), (RegularUser)obj);
        }else if(obj instanceof AdminUser){
            file = new File(context.getFilesDir(), ADMINUSER_FILENAME);
            adminUsersToAdd = readData.readAdminUsers();
            if(adminUsersToAdd == null){
                adminUsersToAdd = new HashMap<>();
            }
            adminUsersToAdd.put(((AdminUser)obj).getUsername(), (AdminUser)obj);
        }
        else if (obj instanceof TransactionRequest) {
            file = new File(context.getFilesDir(), REQUESTS_FILENAME);
            requestsToAdd = readData.readRequests();
            if (requestsToAdd == null){
                requestsToAdd = new ArrayList<>();
            }
            requestsToAdd.add((TransactionRequest) obj);
        }
        else if (obj instanceof RegularUserActionStack){
            file = new File(context.getFilesDir(), ACTION_STACK_FILENAME);
            actionStacksToAdd = readData.readActionStack();
            if (actionStacksToAdd == null){
                actionStacksToAdd = new ArrayList<>();
            }
            RegularUserActionStack currentStack = (RegularUserActionStack) obj;
            for (RegularUserActionStack actions: actionStacksToAdd){
                if (actions.getUsername().equals(currentStack.getUsername())) {
                    actionStacksToAdd.remove(actions);
                    break;
                }
            }
            actionStacksToAdd.add((RegularUserActionStack) obj);
        }
        else if (obj instanceof Transaction){
            file = new File(context.getFilesDir(), TRANSACTION_FILENAME);
            transactionsToAdd = readData.readTransactions();
            for (Transaction tr: transactionsToAdd){
                if (tr.isTheSameTransaction((Transaction) obj)){
                    transactionsToAdd.remove(tr);
                    break;
                }
            }
            if (transactionsToAdd == null){
                transactionsToAdd = new ArrayList<>();
            }
            transactionsToAdd.add((Transaction) obj);
        }
        else{
            file = new File(context.getFilesDir(),"otherObjects.ser");
            // IMPORTANT!! This is for extension, no objects should be stored in this ser.
            // You can still add any filename here if you want to store other entity.
        }

        // There should be multiple same items, hence item should be stored in an array list.

        try{
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            if(obj instanceof RegularUser){
                objectOutputStream.writeObject(regularUsersToAdd);
            }else if(obj instanceof AdminUser){
                objectOutputStream.writeObject(adminUsersToAdd);
            }else if(obj instanceof Item){
                objectOutputStream.writeObject(itemsToAdd);
            }else if(obj instanceof RegularUserActionStack) {
                objectOutputStream.writeObject(actionStacksToAdd);
            }else if(obj instanceof TransactionRequest){
                objectOutputStream.writeObject(requestsToAdd);
            }else if(obj instanceof Transaction){
                objectOutputStream.writeObject(transactionsToAdd);
            }

            fileOutputStream.close();
            objectOutputStream.close();
        }catch (IOException e){
            int x = 1;
        }
    }


}
