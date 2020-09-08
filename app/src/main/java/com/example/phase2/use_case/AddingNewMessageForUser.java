package com.example.phase2.use_case;

import android.annotation.TargetApi;
import android.os.Build;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.example.phase2.entity.*;

public class AddingNewMessageForUser {
    private User targetUser;

    /**
     * A default constructor
     */
    public AddingNewMessageForUser(){
    }

    /**
     * set a target user so as to add new messages for him/her
     * @param targetUser the target User
     */
    public void setTargetUser(User targetUser){
        this.targetUser = targetUser;
    }

    /**
     * Add new message containing (username, formattedDataTime, message to the target User's messages)
     * @param message the target user's message to be added
     */
    @TargetApi(Build.VERSION_CODES.O)
    public void addNewMessage(String message){
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        String formattedDateTime = currentDateTime.format(formatter);
        ArrayList<String> newMessage = new ArrayList<>();
        newMessage.add(targetUser.getUsername());
        newMessage.add(formattedDateTime);
        newMessage.add(message);
        targetUser.getMessages().add(newMessage);
        targetUser.setMessages(targetUser.getMessages());
    }

}
