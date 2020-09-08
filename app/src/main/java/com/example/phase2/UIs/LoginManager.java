package com.example.phase2.UIs;

import java.util.InputMismatchException;
import java.util.Scanner;
import com.example.phase2.gateway.*;
import com.example.phase2.entity.*;
import com.example.phase2.exception.ExitSystemException;

public class LoginManager{

    private UserData userData;

    /**
     * Initialize the LoginManager
     * @param ud the UserData containing all user data of this system
     */
    public LoginManager(UserData ud){
        userData= ud;
    }

    /**
     * run this login menu.
     * @return the target user that is going to displayed
     * @throws ExitSystemException if the user want to exit the program, this exception would be thrown and the algorithm will terminate
     */
    public User run() throws ExitSystemException{
        while (true) {
            Scanner in = new Scanner(System.in);
            System.out.println("Enter 0 for login, 1 to register a new user, 2 to exit the system.");

            // This try-catch block is used to handle cases when the input is not a number.
            int input;
            try {
                input = in.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input, please try again.");
                continue;
            }

            // This block of code is deleted because the default case in switch can handle the same
            // situation.

//            while (input != 0 && input != 1) {
//                System.out.println("Invalid input, please try again.");
//                input = in.nextInt();
//            }

            User loggedInUser;
            switch (input) {
                case 0:
                    loggedInUser = this.login();
                    break;
//                case 1:
//                    loggedInUser = this.register();
//                    break;
                case 2:
                    throw new ExitSystemException();
                default:
                    System.out.println("Invalid input, please try again.");
                    continue;
            }
            return loggedInUser;
        }
    }

    /**
     * log into this system with existing username and password
     * @return the correct user object
     */
    private User login(){
        while (true) {
            System.out.println("Please enter your username:");
            Scanner in = new Scanner(System.in);
            String username = in.nextLine();
            System.out.println("Please enter your password:");
            String password = in.nextLine();
            int validation = userData.validateUser(username, password);
            switch (validation) {
                case -1:
                    System.out.println("This username does not exist. Please try again.");
                    break;
                case 0:
                    System.out.println("Password incorrect! Please try again.");
                    break;
                case 1:
                    System.out.println("Login successful!\n");
                    return this.userData.findUser(username);
            }
        }
    }

    /**
     * Register for a new RegularUser with the input username and password
     * @return
     */
    public int register(String username, String password){
        // This method only registers regularUser
        // Only an admin can add additional adminUser

//            System.out.println("Please enter your desired username:");
//            Scanner in = new Scanner(System.in);
//            String username = in.nextLine();
            boolean duplication = userData.validateUser(username);
            if (duplication) {
                System.out.println("This username already exists! Please try again.");
            }
            else if (username.isEmpty()) {
                System.out.println("This username is empty!");
                return -1;
            }
            else {
//                System.out.println("Please enter your desired password:");
//                String password = in.nextLine();
//                System.out.println("Your username: " + username);
//                System.out.println("Your password: " + password);
                userData.addUser(username, password, "regular");
                System.out.println("Registration successful! You are logged in.\n");
                return 1;
//                return this.userData.findUser(username);
            }
        return 0;
    }
}
