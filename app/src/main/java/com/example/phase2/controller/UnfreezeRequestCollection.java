package com.example.phase2.controller;

import com.example.phase2.entity.RegularUser;
import com.example.phase2.gateway.UserData;

import java.util.HashMap;

public class UnfreezeRequestCollection {
    private HashMap<String, String> userNameToUnfreezeRequest = new HashMap<>();

    /**
     * constructor
     * @param userData userdata of this system
     */
    public UnfreezeRequestCollection(UserData userData){
        for(RegularUser user: userData.getRegularUsers()){
            addUnfreezeRequestToSystem(user.getUsername(), user.getUnfreezeRequest());
        }
    }

    /**
     * Clean out unfreeze request for taget user
     * @param username username for target user
     */
    public void cleanOut(String username){
        userNameToUnfreezeRequest.remove(username);
    }

    /**
     * Adding unfreeze request with username to the system
     * @param username target's username
     * @param request target's user request
     */
    private void addUnfreezeRequestToSystem(String username, String request){

        userNameToUnfreezeRequest.put(username, request);
    }

}
