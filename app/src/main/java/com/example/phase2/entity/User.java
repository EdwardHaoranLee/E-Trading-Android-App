package com.example.phase2.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class User extends Observable implements Serializable {


    private String username;
    private String password;
    private ArrayList<List<String>> messages;
    private int type; // // 0=admin 1= regular 2=frozen 3=demo

    /**
     * Initialize a User
     * @param username the initialized username
     * @param password the initialized password
     * @param type the initialize type, // 0=admin 1= regular 2=frozen 3=demo
     */
    public User(String username, String password, int type) {
        this.username = username;
        this.password = password;
        this.messages = new ArrayList<>();
        this.type = type;
    }

    /**
     * Getter methods for password
     * @return the user's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Getter for username
     * @return the user's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Getter method for user type
     * @return the type of the user.
     */
    public int getUserType(){return type;}

    /**
     * Getter for user's message
     * @return a nested list of string messages of the user
     */
    public ArrayList<List<String>> getMessages() { return messages; }

    /**
     * Setter for user's login password
     * @param newPassword new password
     */
    public void setPassword(String newPassword) {
        password = newPassword;
    }

    public void setUsername(String newUsername) {
        password = newUsername;
    }

//    public void addNewMessage(String message) {
//        LocalDateTime currentDateTime = LocalDateTime.now();
//        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
//        String formattedDateTime = currentDateTime.format(formatter);
//        ArrayList<String> newMessage = new ArrayList<>();
//        newMessage.add(username);
//        newMessage.add(formattedDateTime);
//        newMessage.add(message);
//        this.messages.add(newMessage);
//    }

    public void setMessages(ArrayList<List<String>> messages) {
        this.messages = messages;
    }


}
