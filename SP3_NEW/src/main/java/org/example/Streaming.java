package org.example;

import java.util.ArrayList;

/**
 * @author Mads, Kevin, Daniel
 * Streaming class
 * This class has a setup method which contains our "main" funtions
 * simply just here to call it in the main class to have the main class as clean as possible
 */
public class Streaming {
    ArrayList<User>user=new ArrayList<>();
    Menu menu = new Menu();
    static FileIODatabase ioDatabase = new FileIODatabase();
    private static ArrayList<User> newUsersList = new ArrayList<>();
    /**
     * Following method is a setup method
     * runs the main method of our program
     */
    public void setup(){
      menu.loginMenu();
//ioDatabase.readMovieData();
       // ioDatabase.readSeriesData();
       // ioDatabase.readUserData();

    }
    /**
     * Following method creates a user
     * @param uInputUsername
     * @param uInputPassword
     * @param isAdmin
     * taking user inputs as parameters
     * isAdmin is always set to false so a new user won't be admin per default
     */

    public static void createUser(String uInputUsername, String uInputPassword, boolean isAdmin) {
        User newUser = new User(uInputUsername, uInputPassword, isAdmin);
        newUsersList.add(newUser);
        ioDatabase.saveUserData(newUsersList);

    }



}
