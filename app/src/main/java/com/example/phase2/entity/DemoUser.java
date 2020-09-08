package com.example.phase2.entity;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.ArrayList;


/**
 * An instance of this class is a demo user, it will only be used in the demo
 */
public class DemoUser extends User implements Serializable {

    private static ArrayList<Item> inventory;
    private static ArrayList<Item> willingToBorrowItems;
    private static ArrayList<Item> willingToLendItems;
    private static ArrayList<String> partnerList;
    private static ArrayList<TransactionRequest> transactionRequests;

    /**
     * Initialize an instance of a demo user
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public DemoUser() {
        super("demo", "demo", 3);
        inventory = new ArrayList<>();
        Item apple = new Item("Apple", "Yummy");
        Item textbook = new Item("Textbook", "Good condition");
        Item potato = new Item("Potato", "Fresh");
        inventory.add(apple);
        inventory.add(textbook);
        willingToLendItems = new ArrayList<>();
        willingToLendItems.add(apple);
        willingToBorrowItems = new ArrayList<>();
        willingToBorrowItems.add(potato);
        partnerList = new ArrayList<>();
        transactionRequests = new ArrayList<>();

        ArrayList<Item> list1 = new ArrayList<>();
        list1.add(apple);
        ArrayList<Item> list2 = new ArrayList<>();
        list2.add(textbook);
        list2.add(potato);
        TransactionRequest request1 = new TransactionRequest(new RegularUser("Li Lei",
                "123"), new RegularUser("demo", "demo"),
                "Bay Station", LocalDateTime.now(), list1,
                false, true );
        TransactionRequest request2 = new TransactionRequest(new RegularUser("Lily",
                "123"), new RegularUser("demo", "demo"),
                "Toronto Island", LocalDateTime.now(), list1,
                true, true );
        transactionRequests.add(request1);
        transactionRequests.add(request2);
    }

    /**
     * The getter method of the inventory attribute
     * @return an array list of items that are in the user's inventory
     */
    public static ArrayList<Item> getInventory() {
        return inventory;
    }


    /**
     * The getter for willingToBorrowItems attribute
     * @return an arraylist of the willing to borrow items
     */
    public static ArrayList<Item> getWillingToBorrowItems() {
        return willingToBorrowItems;
    }

    /**
     * The getter for willingToLendItems attribute
     * @return an arraylist of the willing to lend items
     */
    public static ArrayList<Item> getWillingToLendItems() {
        return willingToLendItems;
    }

    /**
     * the getter for partnerList attribute
     * @return an arraylist of the username of the partners
     */
    public static ArrayList<String> getPartnerList() {
        return partnerList;
    }


    /**
     * The getter for transactionRequests attribute
     * @return an arraylist of the transaction requests
     */
    public static ArrayList<TransactionRequest> getTransactionRequests() {
        return transactionRequests;
    }
}
