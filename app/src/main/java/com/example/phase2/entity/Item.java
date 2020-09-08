package com.example.phase2.entity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.phase2.database.SaveData;

import java.io.File;
import java.io.Serializable;
import java.time.LocalDateTime;

public class Item implements Serializable {

    public int itemNumber;

    private String name;
    private String description;
    private boolean status = false;
    private String username;
    private int number;
    private LocalDateTime shelfTime;

    // adding simScore
    private double simScore;
    // adding the matching item id
    private int matchingItemNumber;

    // resource file for item image
    private String imagePath;

    /**
     * Initialize an item with its name and description
     * Default item ID, each item should have its own identity number
     * @param name the name of the item
     * @param description the item's description
     */
    public Item(String name, String description){
        this.name = name;
        this.description = description;
//        this.number = itemNumber;
//        itemNumber += 1;
    }

    /**
     * Initialize an item with its name, description and its owner
     * @param name the name of the item
     * @param description the item's description
     * @param imagePath the path of the item image
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Item(String name, String description, String imagePath){
        this.name = name;
        this.description = description;
        this.number = itemNumber;
        this.imagePath = imagePath;
        this.shelfTime = LocalDateTime.now();
      //  itemNumber += 1;
    }

    /**
     * Setter to change the name of the item
     * @param username an updated item name
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Setter of the status
     * @param status an updated status
     */
    public void setStatus(boolean status) {
        this.status = status;
    }

    /**
     * Getter for the description
     * @return the description of the item
     */
    public String getDescription() {
        return description;
    }

    /**
     * Getter for the name of the Item
     * @return the item's name
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for item's status
     * @return getter for the status
     */
    public boolean getStatus() {
        return status;
    }

    /**
     * Getter of owner's username
     * @return the owner's username
     */
    public String getUsername(){return username;}

    /**
     * Getter of the identity number of this number
     * @return the identity number of this number
     */
    public int getItemNumber(){return number;}

    /**
     * Set the similarity score
     * @param simScore similarity score
     */
    public void setSimScore (double simScore) {
        this.simScore = simScore;
    }

    /**
     * Get similarity score
     * @return similarity score
     */
    public double getSimScore () {
        return this.simScore;
    }

    /**
     * Set the matched Item ID number
     * @param matchingItemNumber matched item id number
     */
    public void setMatchingItemNumber (int matchingItemNumber) {
        this.matchingItemNumber = matchingItemNumber;
    }

    /**
     * Get the matched Item ID number
     * @return item id number
     */
    public int getMatchingItemNumber() {
        return matchingItemNumber;
    }

    /**
     * Get image resource
     * @return Bitmap instance of resource
     */
    @RequiresApi(api = Build.VERSION_CODES.R)
    public Bitmap getImageResource(){
        File file = new File(SaveData.context.getFilesDir().getPath() + "/item_image/" + this.imagePath);
        return BitmapFactory.decodeFile(file.getAbsolutePath());
    }

    /**
     * Get image path
     * @return the image path
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     * Get local date time
     * @return local date time
     */
    public LocalDateTime getShelfTime(){return shelfTime;}

    /**
     * set local date time
     * @param time local date time
     */
    public void setShelfTime(LocalDateTime time){ shelfTime = time;}
}

