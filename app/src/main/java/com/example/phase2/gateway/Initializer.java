package com.example.phase2.gateway;

import java.util.ArrayList;
import java.util.Collection;
import com.example.phase2.database.*;
import com.example.phase2.entity.*;
import com.example.phase2.use_case.RegularUserActionStack;

public class Initializer {
    // First class the program instantiates, runs before LoginManager
    // Execution Flow: PSVM -> Initializer -> LoginManager <-> UIManager

//    public Initializer(){}
    // Needs to read and return UserData to PSVM, then PSVM passes UserData to
    // LoginManager or UIManager

    /**
     * Initialize the UserData Stored in the system
     * @return an UserData object containing all data for this system.
     */
    public static UserData initialize(){
        ReadData readData = new ReadData();
        Collection<RegularUser> regularUsers = readData.readRegularUsers().values();
        Collection<AdminUser> adminUsers = readData.readAdminUsers().values();
        ArrayList<RegularUserActionStack> actionStacks = readData.readActionStack();
        ArrayList<User> allUsers = new ArrayList<>();
        allUsers.addAll(regularUsers);
        allUsers.addAll(adminUsers);
//        for (AdminUser user: adminUsers){
//            allUsers.add(user);
//        }
//        for (RegularUser user: regularUsers){
//            allUsers.add(user);
//        }

        UserData userData = new UserData(allUsers);
        if (userData.findUser("PrimaryAdmin") == null){
            userData.addUser(new AdminUser("PrimaryAdmin", "admin"));
        }
        userData.updateUndoStacks(actionStacks);
        userData.updateTransactions(readData.readTransactions());
        return userData;
    }

//    public ArrayList<User> loadUsers(){
//        // Read from .ser
//        // Instantiate UserData
//        return new ArrayList<>();
//    }
}
