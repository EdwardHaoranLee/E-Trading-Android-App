package com.example.phase2.entity;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * An instance of this class is an admin user
 */
public class AdminUser extends User {
    private int id;
    private ArrayList<RegularUser> suspiciousUsers;
    private HashMap<String, ArrayList<Item>> itemRequests; //key is the username, value is the arraylist of items of that user
    private HashMap<String, ArrayList<Item>> advertisementRequests;  //key is the username, value is the arraylist of items of that user

    /**
     * Initialize an AdminUser. With empty item Request and empty advertisement requests.
     * @param username the username of the AdminUser
     * @param password the password of the AdminUser
     */
    public AdminUser(String username, String password) {
        super(username, password, 0);
        suspiciousUsers = new ArrayList<>();
        itemRequests = new HashMap<>();
        advertisementRequests = new HashMap<>();
    }

    /**
     * Getter method for user ID
     * @return an int for user ID
     */
    public int getId(){return id;}

    /**
     * Getter method to get all suspicious RegularUsers (Users to be frozen)
     * @return an array list of regular users waiting for frozen
     */
    public ArrayList<RegularUser> getSuspiciousUsers() {
        return suspiciousUsers;
    }

//    /**
//     * Getter method to get
//     * @return
//     */
//    public HashMap<String, ArrayList<Item>> getItemRequests() {
//        return itemRequests;
//    }

    /**
     * Getter method for advertisement requests
     * @return an array list of advertisement requests
     */
    public HashMap<String, ArrayList<Item>> getAdvertisementRequests() {
        return advertisementRequests;
    }

    // chen adding getUserWishList

    /**
     * Get target User's wish to borrow lists
     * @param user target Regular User
     * @return the wish to borrow list for target user.
     */
    public ArrayList<Item> getUserWishList(RegularUser user){
        return user.getWillingToBorrowItems();
    }

    // chen adding getUserAvailableList

    /**
     * Get target User's wish to lend lists
     * @param user target Regular User
     * @return the wish to lend list for target user.
     */
    public ArrayList<Item> getUserAvailableList(RegularUser user){
        return user.getWillingToLendItems();
    }

    // Lotus added getItemRequests
    /**
     * Getter method for item requests
     * @return an array list of item requests
     */
    public HashMap<String, ArrayList<Item>> getItemRequests() {
        return this.itemRequests;
    }

    /**
     * Getter method to get all suspicious RegularUsers (Users to be frozen)
     * @return an array list of regular users waiting for frozen
     */
    public void setItemRequests (String username, ArrayList<Item> items) {
        this.itemRequests.put(username, items);
    }

}
