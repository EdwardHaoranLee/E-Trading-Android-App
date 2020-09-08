package com.example.phase2.UIs;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.example.phase2.R;
import com.example.phase2.database.*;
import com.example.phase2.entity.*;
import com.example.phase2.gateway.UserData;
import com.example.phase2.presenter.UserInfoPresenter;
import com.example.phase2.use_case.*;
import com.example.phase2.exception.*;
import com.example.phase2.controller.*;


public class UIManager {

    private User currentUser;
    private int userType;
    private UserData userData;
    private SaveData savingData;

    @RequiresApi(api = Build.VERSION_CODES.R)
    public UIManager(User currentUser, UserData ud){
        this.currentUser = currentUser;
        this.userType = currentUser.getUserType();
        this.userData = ud;
        this.savingData = new SaveData();
    }

    public void changeUser(User newUser){
        this.currentUser = newUser;
        this.userType = currentUser.getUserType();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void displayMenu(){
        // 0=admin 1=regular 2=frozen
        switch (userType){
            case 0:
                AdminUIManager adminMenu = new AdminUIManager(currentUser, userData);
                adminMenu.run();
                break;
            case 1: this.regularMenu();
                break;
            case 2: this.frozenMenu();
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void regularMenu(){
        while (true) {
            Scanner in = new Scanner(System.in);
            String menu = "Welcome to the regular menu! To complete an action, enter the corresponding integer:" +
                    "\n0-Transfer items from Inventory to WishToLend List"+
                    "\n1-Request to add an item to your list of belongings" +
                    "\n2-Confirm a transaction" +
                    "\n3-View pending requests" +
                    "\n4-View market" +
                    "\n5-View your info\n" +
                    "\n6-Log out";
            System.out.println(menu);

            // This try-catch block is used to handle cases when the input is not a number.
            int input;
            try {
                input = in.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input, please try again.");
                continue;
            }

            switch (input) {
                case 0: // Transfer items from Self List to WishToLend List
                    System.out.println("Here are the items that you can add to the WishToLend List");
                    HashMap<Integer, Item> itemsAbleToTransfer = new HashMap<>();
                    for(Item item: ((RegularUser)currentUser).getInventory()){
                        if(!((RegularUser) currentUser).getWillingToLendItems().contains(item)){
                            System.out.println("Item number: " + item.getItemNumber() + "--> Item name: " + item.getName() + "--> Item description: " + item.getDescription());
                            itemsAbleToTransfer.put(item.getItemNumber(), item);
                        }
                    }
                    System.out.println("Enter the number of the item you wish to transfer: ");
                    int inputAdding;
                    try{
                        inputAdding = in.nextInt();
                        if(itemsAbleToTransfer.containsKey(inputAdding)){
                            System.out.println("transferring item successfully");
                            RegularUserEditItems editItems = new RegularUserEditItems(itemsAbleToTransfer.get(inputAdding), (RegularUser) currentUser);
                            editItems.addToAvailableList();
                        }else{
                            System.out.println("You don't have such items to transferred OR item is already in you WishToLend List");
                        }
                    }catch (InputMismatchException e){
                        System.out.println("Invalid input, please enter an integer");
                    }
                    break;

                case 1: // Request to add a new item
                    System.out.println("Enter the name of the item. No semicolon (;) allowed.");

                    // The nextLine() is not working. The same for the second one.
                    // Solution: https://www.jianshu.com/p/9b9ea2ffee5a
                    String fixBug = in.nextLine(); // newly added by Edward

                    String itemName = in.nextLine();
                    System.out.println("Enter the description of your item. No semicolon (;) allowed.");
                    String itemDescription = in.nextLine();
                    Item newItem = new Item(itemName, itemDescription);
                    RequestAddingItem addRequest = new RequestAddingItem();
                    addRequest.addingToFiles(currentUser.getUsername(), newItem);
                    System.out.println("Your request has been filed.\n");
                    break;
                /*case 2: // Arrange transaction
                    this.arrangeTransaction();
                    break;*/
                case 2: // Confirm transaction
                    ArrayList<Transaction> transactions = ((RegularUser) currentUser).getTransactions();
                    if (transactions.isEmpty()){
                        System.out.println("You don't have any transaction yet! Enter 0 to go back to the main menu.");
                        input = in.nextInt();
                        if (input == 0){
                            break;
                        }
                    }
                    for (Transaction t : transactions) {
                        System.out.println(t.toString());
                        System.out.println("Enter 1 to confirm this transaction has taken place:");
                        input = in.nextInt();
                        if (input == 1) {
                            ConfirmTransaction confirm = new ConfirmTransaction();
                            confirm.confirmTransaction(t);
                        }
                    }
                    break;
                case 3: // View pending requests
                    ArrayList<TransactionRequest> requests = ((RegularUser) currentUser).getTransactionRequests();
                    if (requests.isEmpty()){
                        System.out.println("You don't have any request yet! Enter 0 to go back to the main menu.");
                        input = in.nextInt();
                        if (input == 0){
                            break;
                        }
                    }
                    for (int i = 0; i < requests.size(); i++){
                        requests.get(i).requestNum = i;
                    }
                    TransactionRequest realRe = null;
                    for (TransactionRequest re : requests) {
                        System.out.println(re.toString());
                    }
                    System.out.println("Enter the number of a request or Enter -1 to go back to the main menu.");
                    Scanner in8 = new Scanner(System.in);
                    int num = in8.nextInt();
                    if (num == -1){
                        break;
                    }
                    for (TransactionRequest re : requests) {
                        if (re.requestNum == num) {
                            System.out.println(re.toString());
                            realRe = re;
                        }
                    }
                    System.out.println("Enter 1 to accept this request"); // transactions will be generated automatically
                    System.out.println("Enter 2 to edit this request");
                    System.out.println("Enter 3 to deny this request");
                    System.out.println("Enter 4 to go back to the main menu");
                    input = in.nextInt();
                    switch(input){
                        case 1:
                            realRe.setAccepted(true);
                            ConfirmMeeting confirm = new ConfirmMeeting(realRe);
                            confirm.confirmMeeting();
                            break;
                        case 2:
                            if (realRe.getEditCounter() <= 6){
                                System.out.println("Where would you like this transaction to take place");
                                Scanner in7 = new Scanner(System.in);
                                String location = in7.nextLine();
                                System.out.println("The year you'd like this transaction to happen:");
                                int year;
                                try{
                                    year = in.nextInt();
                                }catch (InputMismatchException e){
                                    System.out.println("Invalid enter breaking down the system. Please try again and enter a number");
                                    break;
                                }
                                System.out.println("The month you'd like this transaction to happen:");
                                int month;
                                try{
                                    month = in.nextInt();
                                }catch (InputMismatchException e){
                                    System.out.println("Invalid enter breaking down the system. Please try again and enter a number");
                                    break;
                                }
                                System.out.println("The date you'd like this transaction to happen:");
                                int day;
                                try{
                                    day = in.nextInt();
                                }catch (InputMismatchException e){
                                    System.out.println("Invalid enter breaking down the system. Please try again and enter a number");
                                    break;
                                }
                                System.out.println("the hour(in 24 hour format) you'd like this transaction to happen:");
                                int hour;
                                try{
                                    hour = in.nextInt();
                                }catch (InputMismatchException e){
                                    System.out.println("Invalid enter breaking down the system. Please try again and enter a number");
                                    break;
                                }
                                System.out.println("Enter the minute you'd like this transaction to happen:");
                                int minute;
                                try{
                                    minute = in.nextInt();
                                }catch (InputMismatchException e){
                                    System.out.println("Invalid enter breaking down the system. Please try again and enter a number");
                                    break;
                                }
                                if (month < 1 || month > 12 || day < 1 || day > 31 || hour < 0 || hour > 23
                                        || minute < 0 || minute > 59) {
                                    System.out.println("Invalid time format.");
                                    break;
                                }
                                realRe.modifyRequest(location,year,month,day,hour,minute);
                                User searchUser1 = userData.findUser(realRe.getOwner().getUsername());
                                User searchUser2 = userData.findUser(realRe.getTheOtherUser().getUsername());
                                ((RegularUser) searchUser2).setTransactionRequests(realRe);
                                ((RegularUser) searchUser1).removeTransactionRequest(realRe);
                            }
                            else{
                                System.out.println("You can't edit this request");
                            }
                            break;
                        case 3:
                            User searchUser1 = userData.findUser(realRe.getOwner().getUsername());
                            ((RegularUser) searchUser1).removeTransactionRequest(realRe);
                            requests.remove(realRe);
                            break;
                        case 4:
                            break;
                    }
                    break;
                case 4: // View market
                    this.marketMenu();
                    break;
                case 5: // Personal info
                    this.userInfoMenu();
                    break;
                case 6: // Log out
                    System.out.println("Logging out..");
                    return;
            }

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void frozenMenu(){
        // Cannot arrange transaction, can request unfreeze
        Scanner in = new Scanner(System.in);
        System.out.println("You are an AdminUser");
        String menu = "Welcome to the frozen user menu! To complete an action, " +
                "enter the corresponding integer:" +
                "\n1-Request to unfreeze" +
                "\n2-View market\n3-View your info\n" +
                "4-Log out";
        System.out.println(menu);
        int input;
        try{
            input = in.nextInt();
        }catch (InputMismatchException e){
            System.out.println("Invalid enter will break the system. Please try again");
            input = in.nextInt();
        }
        switch (input){
            case 1: // Unfreeze request
                RequestUnfreeze unfreeze = new RequestUnfreeze();
                unfreeze.requestUnfreeze(currentUser.getUsername());
                System.out.println("Your unfreeze request has been sent to the admins!");
                break;
            case 2: // View market
                this.marketMenu();
                break;
            case 3: // Personal info
                this.userInfoMenu();
                break;
            case 4: // Log out
                System.out.println("Logging out..");
                return;
        }
        this.displayMenu();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void userInfoMenu(){
        UserInfoPresenter presenter = new UserInfoPresenter(currentUser.getUsername());
        System.out.println("This is the personal information menu for " +
                currentUser.getUsername() + ":\n1-View recent traded items\n" +
                "2-View your favorite trading partners\n3-View your inventory and wish list");
        Scanner in = new Scanner(System.in);
        int input;
        try{
            input = in.nextInt();
        }catch (InputMismatchException e){
            System.out.println("Invalid enter will break the system. Please try again");
            input = in.nextInt();
        }
        switch (input){
            case 1:
                System.out.println("Your most recent traded items are: ");
                ArrayList<Item> items = presenter.presentRecentTradingItems(3);
                for (Item i: items){
                    System.out.println(i.getName());
                }
                System.out.println("Enter 0 to go back to the main menu.");
                try{
                    input = in.nextInt();
                }catch (InputMismatchException e){
                    System.out.println("Invalid enter will break the system. Please try again");
                    input = in.nextInt();
                }
                if (input == 0){
                    break;
                }
            case 2:
                System.out.println("Your favorite trading partners are:");
                ArrayList<String> favorites = presenter.favoritePartners();
                for (String s: favorites){
                    System.out.println(s);
                }
                System.out.println("Enter 0 to go back to the main menu.");
                try{
                    input = in.nextInt();
                }catch (InputMismatchException e){
                    System.out.println("Invalid enter will break the system. Please try again");
                    input = in.nextInt();
                }
                if (input == 0){
                    break;
                }
            case 3:
                ArrayList<Item> inventory = ((RegularUser) currentUser).getInventory();
                ArrayList<Item> lendList = ((RegularUser) currentUser).getWillingToLendItems();
                ArrayList<Item> wishList = ((RegularUser) currentUser).getWillingToBorrowItems();
                System.out.println("Your inventory include:");
                for(Item i: inventory){
                    System.out.println(i.getItemNumber() + ":" + i.getName());
                }
                System.out.println("Your wish to lend list include:");
                for (Item i: lendList){
                    System.out.println(i.getItemNumber()+": "+i.getName());
                }
                System.out.println("Your wish to borrow list include:");
                for (Item i: wishList){
                    System.out.println(i.getItemNumber()+": "+ i.getName());
                }
                System.out.println("Enter 0 to go back to the main menu.");
                try{
                    input = in.nextInt();
                }catch (InputMismatchException e){
                    System.out.println("Invalid enter will break the system. Please try again");
                    input = in.nextInt();
                }
                if (input == 0){
                    break;
                }
        }
        this.displayMenu();
    }

    // return allLendingItems
    public ArrayList<Item> allLendingItems() {

        ArrayList<Item> allLendingItems = new ArrayList<>();
        ArrayList<RegularUser> allRegularUsers = userData.getRegularUsers();

        for (RegularUser u: allRegularUsers){
            if(u.getUsername().equals(this.currentUser.getUsername())){
                continue;
            }
            else if (u.getIsBusy() || u.getIsFrozen()) {
                continue;
            }
            ArrayList<Item> userLendingItem = u.getWillingToLendItems();
            if(userLendingItem.size() == 0){
                continue;
            }else {

                allLendingItems.addAll(userLendingItem);
            }
        }
        return allLendingItems;
    }

    // return all Transactions
    public ArrayList<Transaction> allTransactions() {

        ArrayList<Transaction> allTransactions = new ArrayList<>();
        RegularUser user = (RegularUser) currentUser;
        allTransactions.addAll(user.getTransactions());
        return allTransactions;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void marketMenu(){
        // User should be able to add items to their wish list and request transactions here
        ArrayList<Item> allLendingItems = new ArrayList<>();
        ArrayList<RegularUser> allRegularUsers = userData.getRegularUsers();
        for (RegularUser u: allRegularUsers){
            if(u.getUsername().equals(this.currentUser.getUsername())){
                continue;
            }
            ArrayList<Item> userLendingItem = u.getWillingToLendItems();
            System.out.println("Items for lending of user " + u.getUsername() + ":");
            if(userLendingItem.size() == 0){
                System.out.println("... The above user has no items to lend ...");
            }else {
                System.out.println("The following items can be lent by " + u.getUsername() + ": ");

                for (Item i : userLendingItem) {
                    System.out.println("Item number " + i.getItemNumber() + " - " + i.getName() + " : " + i.getDescription());
                    allLendingItems.add(i);
                }
            }
            System.out.println();
        }
        if (allLendingItems.isEmpty()){
            System.out.println("There are no items in the market! Enter 0 to go back to the main menu.");
            Scanner in3 = new Scanner(System.in);
            int ne;
            try{
                ne = in3.nextInt();
            }catch (InputMismatchException e){
                System.out.println("Invalid enter will break the system. Please try again");
                ne = in3.nextInt();
            }
            switch (ne){
                case 0:
                    return;
            }
        }
        System.out.println("Do you wish to add any items to your wish list or make a transaction?");
        System.out.println("Enter 1 if you want to add items to your wish list");
        System.out.println("Enter 2 if you want to arrange a transaction");
        Scanner in = new Scanner(System.in);
        int input;
        try{
            input = in.nextInt();
        }catch (InputMismatchException e){
            System.out.println("Invalid enter will break the system. Please try again");
            input = in.nextInt();
        }
        if (input == 1){
            System.out.println("Please enter the item number you wish to add to your wish list:");
//            ArrayList<Integer> itemNums = new ArrayList<>();
//            for(Item item: new ReadData().readItemInventory()){
//                itemNums.add(item.getItemNumber());
//            }
//            System.out.println("Here are the available item numbers that you can choose");
//            System.out.println(itemNums);
            try {
                input = in.nextInt();
            }catch (InputMismatchException e){
                System.out.println("You should enter a number, try again");
            }
            for (Item i: allLendingItems){
                if(i.getItemNumber() == input){
                    /*EditItems editItemsForLender = new RegularUserEditItems(i, (RegularUser) userData.findUser(i.getUsername()));
                    editItemsForLender.removeFromAvailableList();
                    i.setUsername(currentUser.getUsername());*/
                    EditItems editItems = new RegularUserEditItems(i, (RegularUser) currentUser);
                    editItems.addToWishList();
                    return;
                }
            }
            System.out.println("The item number does not correspond to any item.");
        }
        else if(input == 2){
            System.out.println("Please enter the name of the user you want to trade with:");
            Scanner in2 = new Scanner(System.in);
            String target = in2.nextLine();
            User searchUser = userData.findUser(target);
            if (searchUser == null) {
                System.out.println("This user does not exist!");
            }
            else {
                ArrayList<Item> availableItems = ((RegularUser) searchUser).getWillingToLendItems();
                System.out.println("Here is a list of items " + searchUser.getUsername() + " is willing to lend:");
                for (Item i : availableItems) {
                    System.out.println(i.getItemNumber() + " : " + i.getName() + " : " + i.getDescription());
                }
                System.out.println("Please enter the item number you wish to make a transact with:");
                int input2 = in.nextInt();
                for (Item i : availableItems) {
                    if (i.getItemNumber() == input2){
                        System.out.println("Enter 1 to send a trading request to "
                                + searchUser.getUsername() + " for this item.");
                        int input3 = in.nextInt();
                        if (input3 == 1) {
                            System.out.println("Enter 0 to borrow this item in exchange for one of your items, " +
                                    "enter 1 to borrow this item without exchange");
                            input = in.nextInt();
                            boolean twoWay = false;
                            if (input == 0) {
                                twoWay = true;
                            }
                            System.out.println("Enter 0 to request temporary transaction, " +
                                    "enter 1 if you want to make this exchange permanent:");
                            input = in.nextInt();
                            boolean temporary = false;
                            if (input == 0) {
                                temporary = true;
                            }
                            ArrayList<Item> items = new ArrayList<>();
                            if (twoWay) {
                                System.out.println("Pick one of your items in exchange for this transaction: ");
                                ArrayList<Item> yourItems = ((RegularUser) currentUser).getInventory();
                                Item tradeItem = null;
                                for (Item j : yourItems) {
                                    System.out.println(j.getItemNumber() + " : " +
                                            j.getName() + " : " + j.getDescription());
                                }
                                System.out.println("Please enter the number of the item you picked:");
                                input = in.nextInt();
                                for (Item j : yourItems) {
                                    if (j.getItemNumber() == input){
                                        System.out.println("Enter 1 to use this item");
                                        input = in.nextInt();
                                        if (input == 1) {
                                            tradeItem = j;
                                        }
                                    }
                                }
                                if (tradeItem == null) {
                                    System.out.println("Operation failed, did not select item");
                                    break;
                                }
                                items.add(tradeItem);
                            }
                            items.add(i);
                            System.out.println("Enter the time you want this transaction to take place");
                            System.out.println("Enter the year:");
                            int year = in.nextInt();
                            System.out.println("Enter the month:");
                            int month = in.nextInt();
                            System.out.println("Enter the date:");
                            int day = in.nextInt();
                            System.out.println("Enter the hour(in 24 hour format):");
                            int hour = in.nextInt();
                            System.out.println("Enter the minute:");
                            int minute = in.nextInt();
                            if (month < 1 || month > 12 || day < 1 || day > 31 || hour < 0 || hour > 23
                                    || minute < 0 || minute > 59) {
                                System.out.println("Invalid time format.");
                                return;
                            }
                            System.out.println("Enter the location:");
                            Scanner in6 = new Scanner(System.in);
                            String location = in6.nextLine();
                            try {
                                RequestTrade tradeRequest = new RequestTrade(
                                        (RegularUser) currentUser, (RegularUser) searchUser, i);
                                try {
                                    TransactionRequest re = tradeRequest.requestTrade(year, month, day, hour, minute,
                                            location, items, twoWay, temporary);
                                    ((RegularUser) searchUser).setTransactionRequests(re);
                                } catch (NotValidForTransactionException e) {
                                    System.out.println("You are not eligible for making this transaction happen");
                                }
                            } catch (ItemNotApprovedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }
}

    /*private void arrangeTransaction(){
        System.out.println("Enter the target username you wish to trade with: ");
        Scanner in = new Scanner(System.in);
        String target = in.nextLine();
        User searchUser = userData.findUser(target);
        if (searchUser == null) {
            System.out.println("This user does not exist!");
        } else if (searchUser.getUserType() == 0) {
            System.out.println("You cannot trade with an admin!");
        } else {
            System.out.println("Here is a list of items " + searchUser.getUsername() + " is willing to lend:");
            ArrayList<Item> availableItems = ((RegularUser) searchUser).getWillingToLendItems();
            for (Item i : availableItems) {
                System.out.println(i.getItemNumber() + " : " + i.getName() + " : " + i.getDescription());
                System.out.println("Enter 1 to send a trading request to "
                        + searchUser.getUsername() + " for this item.");
                int input = in.nextInt();
                if (input == 1) {
                    System.out.println("Enter 0 to borrow this item in exchange for one of your items, " +
                            "enter 1 to borrow this item without exchange");
                    input = in.nextInt();
                    boolean twoWay = false;
                    if (input == 0) {
                        twoWay = true;
                    }
                    System.out.println("Enter 0 to request temporary transaction, " +
                            "enter 1 if you want to make this exchange permanent:");
                    boolean temporary = false;
                    if (input == 0) {
                        temporary = true;
                    }
                    ArrayList<Item> items = new ArrayList<>();
                    if (twoWay) {
                        System.out.println("Pick one of your items in exchange for this transaction: ");
                        ArrayList<Item> yourItems = ((RegularUser) currentUser).getSelfItems();
                        Item tradeItem = null;
                        for (Item j : yourItems) {
                            System.out.println(j.getItemNumber() + " : " +
                                    j.getName() + " : " + j.getDescription());
                            System.out.println("Enter 1 to use this item");
                            input = in.nextInt();
                            if (input == 1) {
                                tradeItem = j;
                            }
                        }
                        if (tradeItem == null) {
                            System.out.println("Operation failed, did not select item");
                            break;
                        }
                        items.add(tradeItem);
                        items.add(i);
                    }
                    System.out.println("Enter the time you want this transaction to take place");
                    System.out.println("Enter the year:");
                    int year = in.nextInt();
                    System.out.println("Enter the month:");
                    int month = in.nextInt();
                    System.out.println("Enter the date:");
                    int day = in.nextInt();
                    System.out.println("Enter the hour(in 24 hour format):");
                    int hour = in.nextInt();
                    System.out.println("Enter the minute:");
                    int minute = in.nextInt();
                    if (month < 1 || month > 12 || day < 1 || day > 31 || hour < 0 || hour > 23
                            || minute < 0 || minute > 59) {
                        System.out.println("Invalid time format.");
                        return;
                    }
                    System.out.println("Enter the location:");
                    String location = in.nextLine();
                    try {
                        RequestTrade tradeRequest = new RequestTrade(
                                (RegularUser) currentUser, (RegularUser) searchUser, i);
                        try {
                            tradeRequest.requestTrade(year, month, day, hour, minute, location, items,
                                    twoWay, temporary);
                        } catch (NotValidForTransactionException e) {
                            e.printStackTrace();
                        }
                    } catch (ItemNotApprovedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }*/