package com.example.phase2.testData;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.phase2.database.SaveData;
import com.example.phase2.entity.RegularUser;
import com.example.phase2.gateway.UserData;

public class DataUtil {
    RegularUser kelvin = new RegularUser("Kelvin", "kel");
    RegularUser cissy = new RegularUser("Cissy", "cis");
    RegularUser gary = new RegularUser("Gary", "gar");

//          \---------------------------\   \---------------------------\   \---------------------------\
//          \       RegularUser         \   \       RegularUser         \   \       RegularUser         \
//          \Username: Kelvin			\   \Username: Cissy			\   \Username: Gary 			\
//          \Password: kel				\   \Password: cis				\   \Password: gar				\
//          \IsFrozen : true		    \   \IsFrozen : true		    \   \IsFrozen : false		    \
//          \Non unfreeze request sent	\   \Non unfreeze request sent	\   \---------------------------\
//          \---------------------------\   \---------------------------\

    /**
     * Test cases set up for README.txt
     * @param userData
     */
    @RequiresApi(api = Build.VERSION_CODES.R)
    public void setUpUsers(UserData userData){
//        for(RegularUser user: userData.getRegularUsers()){
//            if(user.getUsername().equals("Kelvin")){
//                return;
//            }
//        }

        kelvin.setFrozen(true);
        cissy.setFrozen(true);
        userData.addUser(kelvin);
        userData.addUser(cissy);
        userData.addUser(gary);
    }

    public void defaultSetFrozen(RegularUser user){
        user.setFrozen(true);
    }
}
