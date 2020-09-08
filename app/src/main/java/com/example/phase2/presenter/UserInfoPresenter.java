package com.example.phase2.presenter;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import com.example.phase2.database.*;
import com.example.phase2.entity.*;
import com.example.phase2.comparator.*;

public class UserInfoPresenter {

    private ReadData readData = new ReadData();
    public String username;
    private Map<String, RegularUser> usersMap = new HashMap<>();
    private RegularUser targetUser;
    private ArrayList<Item> tradeItems = new ArrayList<>();


    public UserInfoPresenter(String username){
        this.username = username;
    }

    public UserInfoPresenter(RegularUser user){
        this.targetUser = user;
        this.username = user.getUsername();
    }

    /**
     * show the WishList of this user
     * @return An arraylist of items for this user's WishList
     */
    public ArrayList<Item> showUserWishList(){
        return targetUser.getWillingToBorrowItems();
    }

    /**
     * show the
     * @return
     */
    public ArrayList<Item> showUserInventory(){
        return targetUser.getWillingToLendItems();
    }

    /**
     * Return the last threshold of tradingItems
     * @param threshold the recently n number from trading items
     * @return the recent threshold trading items for this user.
     */
    @RequiresApi(api = Build.VERSION_CODES.R)
    public ArrayList<Item> presentRecentTradingItems(int threshold){
        usersMap = readData.readRegularUsers();
        if(!usersMap.containsKey(username)){
            return new ArrayList<>();
        }else{
            targetUser = usersMap.get(username);
            tradeItems = targetUser.getTradingItems();
            ArrayList<Item> result = new ArrayList<>();
            if(threshold <= tradeItems.size()) {
                for (int i = tradeItems.size() - 1; i >= tradeItems.size() - threshold; i--) {
                    result.add(tradeItems.get(i));
                }
                return result;
            }else{
                return tradeItems;
            }
        }
    }

    /**
     * Return a list of the username of the most <threshold> partners
     * @return  an ArrayList of the username of the most <threshold> partners
     */
    @RequiresApi(api = Build.VERSION_CODES.R)
    public ArrayList<String> favoritePartners(){
        ArrayList<String> result = new ArrayList<>();
        usersMap = readData.readRegularUsers();
        HashMap<String, Integer> getCountPartner;
        ArrayList<String> partners = usersMap.get(username).getPartnerList();
        getCountPartner = counting(partners);
        ArrayList<Integer> getCountingNums = new ArrayList<Integer>();
        if (!getCountPartner.isEmpty()){
            getCountingNums = (ArrayList<Integer>) getCountPartner.values();
        }
        getCountingNums.sort(new IntegersComparator());
        int maxNum = 0;
        if(getCountingNums.size() == 0){
            return result;
        }
        maxNum = getCountingNums.get(getCountingNums.size() - 1);
        for(String x: partners){
            if(getCountPartner.get(x).equals(maxNum)){
                result.add(x);
            }
        }

        return result;

    }

    /**
     * A counting method that is to return the Username = Frequency pair of ArrayList partners
     * @param partners An array list that contains a list of usernames
     * @return return the Username = Frequency pair of ArrayList partners
     */

    private HashMap<String, Integer> counting(ArrayList<String> partners){
        HashMap<String, Integer> result = new HashMap<>();
        HashSet<String> usernameMap = new HashSet<>();
        for(String x: partners){
            usernameMap.add(x);
        }

        for(String x: usernameMap){
            int count = 0;
            for(String y: partners){
                if(y.equals(x)){
                    count++;
                }
            }
            result.put(x, count);
        }
        return result;
    }


}
