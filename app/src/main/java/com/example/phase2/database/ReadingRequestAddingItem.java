package com.example.phase2.database;

import android.graphics.drawable.Drawable;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import com.example.phase2.R;
import com.example.phase2.entity.*;

public class ReadingRequestAddingItem {
    private File file = new File(SaveData.context.getFilesDir().getPath() + "/" + "itemAddingRequest.txt");
    private HashMap<String, ArrayList<Item>> userNameToItems = new HashMap<>();

    /**
     * A default constructor
     */
    public ReadingRequestAddingItem(){}

    /**
     * A method that reads all request from txt file and add it to a hashMap
     */
    private void readingAddingRequestFile(){
        try{
            if(!file.exists()){
                file.createNewFile();
            }else{
                Scanner sc = new Scanner(file);
                while (sc.hasNextLine() && !(sc == null)){
                    String aLine = sc.nextLine();
                    String[] splittingLine = aLine.split(";");
                    if(splittingLine.length >= 3) {
                        Item newItem = new Item(splittingLine[1], splittingLine[2]);
                        if (this.userNameToItems.containsKey(splittingLine[0])) {
                            userNameToItems.get(splittingLine[0]).add(newItem);
                        } else {
                            ArrayList<Item> newList = new ArrayList<>();
                            newList.add(newItem);
                            this.userNameToItems.put(splittingLine[0], newList);
                        }
                    }

                }
                sc.hasNextLine();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * A method that displays all requesting adding items strings in an arrayList
     * @return a ArrayList containing strings about request adding
     */
    public ArrayList<String> displayRequest(){
        ArrayList<String> result = new ArrayList<>();
//        readingAddingRequestFile();
        for(String username: userNameToItems.keySet()){
            for(Item item: userNameToItems.get(username)){
                String newString = new String("A user named " + username + " is requesting to add an item named " + item.getName() + " into his own list. This item's description is "+ item.getDescription()+"\r");
                result.add(newString);
            }

        }
        return result;
    }

    /**
     * Return a hashMap whose key is the username to be added and the value is the item to be added
     * @return a HashMap whose key is the username to be added and the value is the item to be added
     */
    public HashMap<String, ArrayList<Item>> getUserNameToItemAfterReading() {
        readingAddingRequestFile();
        return userNameToItems;
    }
}
