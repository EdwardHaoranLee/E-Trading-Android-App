package com.example.phase2.controller;

import android.os.Build;
import androidx.annotation.RequiresApi;

import com.example.phase2.entity.*;
import com.example.phase2.use_case.CleanText;
import com.example.phase2.gateway.UserData;
import java.time.LocalDateTime;
import java.util.*;


/**
 * ItemSuggestion provides algorithms to select the most reasonable item/list of items
 */
public class ItemSuggestion {

    private RegularUser userA;
    private RegularUser userB;

    public ItemSuggestion(RegularUser userA){
        this.userA = userA;
    }

    // userA is the user who wants the suggestion
    // userB is the user who lends item to userA
    public ItemSuggestion(RegularUser userA, RegularUser userB) {
        this.userA = userA;
        this.userB = userB;
    }

    /**
     * @return a list of items that are both on userA's lending list and userB's wishlist
     */
    public ArrayList<Item> tradeSuggestion(){
        ArrayList<Item> lendingList = userA.getWillingToLendItems();
        ArrayList<Item> wishList = userB.getWillingToBorrowItems();
        ArrayList<Item> recommendations = new ArrayList<>();
        for (Item i: lendingList){
            if (wishList.contains(i)){
                recommendations.add(i);
            }
        }
        return recommendations;
    }

    /**
     *
     * @param ud an instance of UserData represents the entire market
     * @return a list of items from the search of the entire market wishSuggestion ranks first, followed by newSuggestion recommendations
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<Item> marketSuggestion(UserData ud){
        ArrayList<Item> wishSuggestion = this.wishSuggestion(ud);
        ArrayList<Item> newSuggestion = this.newSuggestion(ud);
        ArrayList<Item> recommendations = new ArrayList<>();
        recommendations.addAll(wishSuggestion);
        recommendations.addAll(newSuggestion);
        return recommendations;
    }

    /**
     *
     * @param ud an instance of UserData represents the entire market
     * @return a list of items through the searches of whether an item on <user>'s wish list is present on the market right now and recommend item
     */
    private ArrayList<Item> wishSuggestion(UserData ud){
        // Searches for whether an item on <user>'s wish list is present on the market right now and recommend item
        ArrayList<Item> recommendations = new ArrayList<>();
        ArrayList<RegularUser> users = ud.getRegularUsers();
        ArrayList<Item> marketItems = new ArrayList<>();
        for (RegularUser u: users){
            if(u.getUsername().equals(this.userA.getUsername())){
                continue;
            }
            else if (u.getIsBusy() || u.getIsFrozen()) {
                continue;
            }
            ArrayList<Item> userLendingItem = u.getWillingToLendItems();
            if(userLendingItem.size() == 0){
                continue;}
            marketItems.addAll(u.getWillingToLendItems());
        }
        ArrayList<Item> wishes = userA.getWillingToBorrowItems();
        for (Item i: marketItems){
            if (wishes.contains(i)){
                recommendations.add(i);
            }
        }
        return recommendations;
    }

    /**
     *
     * @param ud an instance of UserData represents the entire market
     * @return a list of items which are recommended latest items that have just been added to the market to userA
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private ArrayList<Item> newSuggestion(UserData ud){
        // Recommend latest items that have just been added to the market to userA
        ArrayList<Item> recommendations = new ArrayList<>();
        ArrayList<Item> allItems = new ArrayList<>();
        ArrayList<RegularUser> users = ud.getRegularUsers();

        // Retrieve all items first and determine how many items to suggest
        for (RegularUser u: users){
            if(u.getUsername().equals(this.userA.getUsername())){
                continue;
            }
            else if (u.getIsBusy() || u.getIsFrozen()) {
                continue;
            }
            ArrayList<Item> userLendingItem = u.getWillingToLendItems();
            if(userLendingItem.size() == 0){
                continue;}
           allItems.addAll(u.getWillingToLendItems());
        }
        int recommendationSize = allItems.size();

        // Loops through all items to pick the latest items
        for (int k = 0; k < recommendationSize; k++){
            // Create an item that will be the oldest every iteration
            Item latestItem = new Item("1","1");
            LocalDateTime oldTime = LocalDateTime.now().minusYears(20);
            latestItem.setShelfTime(oldTime);
            for (Item i: allItems){
                if (i.getShelfTime().isAfter(latestItem.getShelfTime())){
                    latestItem = i;
                }
            }
            recommendations.add(latestItem);
            // Removes the latest item after every iteration to get the 2nd, 3rd.. latest
            allItems.remove(latestItem);
        }
        return recommendations;
    }

    /**
     *
     * @param ct is an instance of CleanText class. The injection of this object is to call methods from CleanText class
     * @return a list of sorted list of instances of items by cosine similarity scores in a descending order
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public ArrayList<Item> smartSuggestion (CleanText ct) {
        // extract userA's available list
        List<Item> availableListA = userA.getWillingToLendItems();
        // extract userB's wishlist and copied to a new list
        List<Item> wishlistB = new ArrayList<>(userB.getWillingToBorrowItems());

        // initiate a list to store the top 3 items
        ArrayList<Item> resList = new ArrayList<>();

        for (Item itemA : availableListA) {
            // create clean tokenized words collection
            List<List<String>> tokenizedCollect = new ArrayList<>();
            // create bag of words, which is a bag of unique words in the collection of document
            // every iteration is a new bag of words
            List<String> bagOfWords = new ArrayList<>();
            // create TF-IDF score for each document wrt each word
            List<List<Double>> tfidfScores = new ArrayList<>();

            // handling userA item description
            ct.modifyWordsList(itemA, tokenizedCollect, bagOfWords);
            // handling all item descriptions from userB
            for (Item itemB : wishlistB) {
                ct.modifyWordsList(itemB, tokenizedCollect, bagOfWords);
            }

            for (int i = 0; i < tokenizedCollect.size(); i++) {
                List<String> currDoc = tokenizedCollect.get(i);
                List<Double> listOfScores = new ArrayList<>();
                for (String word : bagOfWords) {
                    listOfScores.add(ct.tfidfCalculator(word, currDoc, tokenizedCollect));
                }
                tfidfScores.add(listOfScores);
            }

            // recall userA info at index 0
            // collecting cosine similarity
            List<Double> cosSimScores = new ArrayList<>();
            for (int i = 1; i < tfidfScores.size(); i++) {
                cosSimScores.add(ct.cosSimCalculator(tfidfScores.get(0), tfidfScores.get(i)));
            }

            // cosSimScores are between itemA (each iteration from userA) desc and each of the items from userB
            double maxScore = Collections.max(cosSimScores);
            int indexNum = cosSimScores.indexOf(maxScore);
            // each iteration: itemA get a max sim score indicating the highest sim score with one of the items in userB's wishlist
            itemA.setSimScore(maxScore);
            // record the matched item ID in order to remove selected item from wishlist
            int itemIDNumber = wishlistB.get(indexNum).getItemNumber();
            itemA.setMatchingItemNumber(itemIDNumber);

            // adding to resList
            resList.add(itemA);
        }
        resList.sort(Comparator.comparing(Item::getSimScore).reversed());
        return resList;
    }

}
