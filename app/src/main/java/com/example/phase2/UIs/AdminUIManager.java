package com.example.phase2.UIs;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;
import com.example.phase2.entity.*;
import com.example.phase2.gateway.*;
import com.example.phase2.database.*;
import com.example.phase2.use_case.*;
import com.example.phase2.controller.*;

public class AdminUIManager {

    private User currentUser;
    private UserData userData;

    /**
     * Initialize an AdminUIManger with the currentUser and UserData
     * @param currentUser the target user that you want to display
     * @param userData the userData that you want to display
     */
    public AdminUIManager(User currentUser, UserData userData){
        this.currentUser = currentUser;
        this.userData = userData;
    }

    /**
     * A running method that can help you get the operations in the admin's UI.
     * input 1: Freeze the target User
     * input 2: Unfreeze the target User
     * input 3: Approve adding the item
     * input 4: Changing thresold
     * input 5: Add new Admins
     * input 6: Log out
     */
    @RequiresApi(api = Build.VERSION_CODES.R)
    public void run() {
        while (true) {
            boolean terminate = false;
            Scanner in = new Scanner(System.in);
            String menu = "Welcome to the admin menu! To complete an action, enter the corresponding integer:" +
                    "\n1-View suspicious users and Freeze users" +
                    "\n2-View Frozen users and Unfreeze users\n3-Approve a user's new item request" +
                    "\n4-Adjust threshold values\n5-Add additional administrative users\n" +
                    "6-Log out";
            System.out.println(menu);
            int input;
            try{
                input = in.nextInt();}
            catch (InputMismatchException e){
                System.out.println("You should input an integer");
                break;
            }
            switch (input) {
                case 1: // View suspicious users
                    ArrayList<RegularUser> suspicious = userData.getSuspiciousUsers();
                    if (suspicious.isEmpty()){
                        System.out.println("There are no suspicious users!\nEnter 0 to go back to the main menu.");
                        int input1 = in.nextInt();
                        if (input1 == 0){
                            break;
                        }
                    }
                    for (RegularUser u : suspicious){
                        System.out.println(u.getUsername() + "\n");
                    }
                    System.out.println("Enter the username of the user you wish to freeze: ");
                    Scanner in2 = new Scanner(System.in);
                    String target = in2.nextLine();
                    User searchUser = userData.findUser(target);
                    if (searchUser != null) {
                        if (searchUser.getUserType() == 1) {
                            FreezingAccount freezer = new FreezingAccount();
                            freezer.freezeOperation(true, (RegularUser) searchUser);
                        } else {
                            System.out.println("This user is an admin!");
                        }
                    } else {
                        System.out.println("This user does not exist!");
                    }
                    break;
                case 2: // View Frozen users and Unfreeze users
                    ArrayList<RegularUser> frozen = userData.getFrozenUsers();
                    if (frozen.isEmpty()){
                        System.out.println("There are no frozen users!\nEnter 0 to go back to the main menu.");
                        int input1 = in.nextInt();
                        if (input1 == 0){
                            break;
                        }
                    }
                    for (RegularUser u : frozen){
                        System.out.println(u.getUsername() + "\n");
                    }
                    System.out.println("Enter the username of the user you wish to unfreeze: ");
                    Scanner in3 = new Scanner(System.in);
                    String tar = in3.nextLine();
                    searchUser = userData.findUser(tar);
                    if (searchUser != null) {
                        if (searchUser.getUserType() == 1) {
                            ((RegularUser) searchUser).setFrozen(false);
                        } else {
                            System.out.println("This user is an admin!");
                        }
                    } else {
                        System.out.println("This user does not exist!");
                    }
                    break;
                case 3: // Approve new item requests
                    ReadingRequestAddingItem reader = new ReadingRequestAddingItem();
                    HashMap<String, ArrayList<Item>> usernameToItem = reader.getUserNameToItemAfterReading();
                    ArrayList<String> requests = reader.displayRequest();
                    for (String request : requests) {
                        System.out.println(request);
                    }
                    System.out.println("Enter the username of the user you wish to grant items to: ");
                    Scanner in5 = new Scanner(System.in);
                    String grantUser = in5.nextLine();
                    ArrayList<Item> items = usernameToItem.get(grantUser);
                    if (items == null){
                        System.out.println("This user does not exist.");
                    } else if (items.size() == 0) {
                        System.out.println("This user does not have item to be approved.");
                    } else {
                        System.out.println("This user has following items to be approved.");
                        for (int i = 0; i < items.size(); i++) {
                            System.out.print(i);
                            System.out.println(". " + items.get(i).getName());
                        }
                        System.out.println();
                        System.out.println("Please input the number in front of item. Only one number allowed.");
                        Item itemToApprove;
                        try {
                            itemToApprove = items.get(in.nextInt());
                        } catch (IndexOutOfBoundsException e){
                            System.out.println("This index is not valid.");
                            break;
                        }catch (InputMismatchException e){
                            System.out.println("You should input an integer");
                            break;
                        }
                        System.out.println("Please input your decision. Yes/No?");
                        Scanner in4 = new Scanner(System.in);
                        String decision = in4.nextLine();
                        while (!(decision.equals("Yes") || decision.equals("No"))) {
                            System.out.println("Invalid input. Please try again.");
                            decision = in.nextLine();
                        }
                        ApproveAddingItem approver;
                        if (decision.equals("Yes")) {
                            approver = new ApproveAddingItem(true);
                        } else {
                            approver = new ApproveAddingItem(false);
                        }
                        approver.changingItem(grantUser, itemToApprove, this.userData);

//                    for (Item i : items) {
//                        System.out.println(i.getName());
//                        System.out.println("Enter 1 to approve this item:");
//                        input = in.nextInt();
//                        if (input == 1) {
//                            approver.changingItem(grantUser, i);
//                            itemRequests.remove(i);
//
//                        }
//                    }
                    }
                    break;
                case 4: // Change threshold
                    System.out.println("Enter how many more items a user needs to lend before they can borrow: ");
                    input = in.nextInt();
                    if (input >= 0) {
                        ValidateEligibility.thresholdLendingMinusBorrowing = input;
                    } else {
                        System.out.println("Input cannot be negative!");
                    }
                    break;
                case 5: // Add new admins
                    System.out.println("Enter the username of the new admin user: ");
                    String username;
                    username= in.nextLine();
                    if (userData.validateUser(username)) {
                        System.out.println("This username is already taken!");
                    } else {
                        System.out.println("Enter the password: ");
                        String password = in.nextLine();
                        userData.addUser(username, password, "admin");
                        System.out.println("Success!");
                    }
                    break;
                case 6: // Log out
                    SaveData savingData = new SaveData();
                    for(User user: this.userData.getUsers()){
                        savingData.saveData(user);
                    }
                    System.out.println("Logging out..");
                    terminate = true;
            }
            if (terminate) {
                return;
            }

        }
    }

}
