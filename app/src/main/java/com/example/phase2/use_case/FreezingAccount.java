package com.example.phase2.use_case;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.phase2.entity.*;
import com.example.phase2.database.*;

public class FreezingAccount {

    public FreezingAccount(){}

    /**
     * Set the RegularUser user's isFrozen to be True and then resave the file into ser
     * @param user The target User to freeze the account
     */
    @RequiresApi(api = Build.VERSION_CODES.R)
    private void setFreezing(RegularUser user){
        user.setFrozen(true);
        user.setSuspicious(false);
        SaveData savingData = new SaveData();
        savingData.saveData(user);
    }

    /**
     * a judge is received from the Controller, if judge is True, then freeze the account, else do nothing
     * @param judge a boolean value received from Controller
     * @param user the target user
     */
    @RequiresApi(api = Build.VERSION_CODES.R)
    public void freezeOperation(boolean judge, RegularUser user){
        if(judge){
            setFreezing(user);
        }
    }

}
