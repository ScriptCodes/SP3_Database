package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;
import java.nio.file.Path;

/**
 * @author Mads, Kevin, Daniel
 * User class containing all the nescessary attributes to create a user
 * contains methods relevant for a user
 */
public class User {
    FileIO io = new FileIO();
    TextUI ui = new TextUI();
    FileIODatabase ioDatabase = new FileIODatabase();
    private String username;
    private String password;
    private boolean isAdmin = false;
  //  private ArrayList<User> newUsersList = new ArrayList<>();
    private boolean loggedIn = false;

    //Constructor
    public User(String username, String password, boolean isAdmin){
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    /*
    public void createUser(String uInputUsername, String uInputPassword,boolean isAdmin) {
        User newUser = new User(uInputUsername, uInputPassword, isAdmin);
        newUsersList.add(newUser);
        ioDatabase.saveUserData(newUsersList);
        //io.createTextFile(newUser);
    }
*/
    public List<String> getFavorites() {
        try {
            String fileName = "C:\\Users\\Danie\\IdeaProjects\\SP3_Done\\SP3_NEW\\src\\main\\java\\org\\example\\favorites" + this.getUsername() + ".txt";
            Path filePath = Path.of(fileName);

            return Files.readAllLines(filePath);

        } catch (IOException e) {
            System.out.println("Error getting favorites: " + e.getMessage());
            ArrayList<String>favorites=new ArrayList<String>();
            return favorites;
        }
    }


    public void addToFavorites(String mediaString) {
        List<String> currentFavorites;
        try {
            currentFavorites = Files.readAllLines(Paths.get(getFavoritesFilePath()));
        } catch (IOException e) {
            e.printStackTrace();  // Handle the exception according to your needs
            return;
        }

        currentFavorites.add(mediaString);

        try {
            Files.write(Paths.get(getFavoritesFilePath()), currentFavorites);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private String getFavoritesFilePath() {
        return "C:\\Users\\Danie\\IdeaProjects\\SP3_Done\\SP3_NEW\\src\\main\\java\\org\\example\\favorites" + getUsername() + ".txt";
    }
    public int convertBooleanToTinyInt(boolean value) {
        return value ? 1 : 0;
    }



    //Region getters
    public String getUsername(){return username;}

    public String getPassword(){return password;}

    public boolean getIsAdmin(){return isAdmin;}
//Region end
    public boolean isAdmin() {
        return this.isAdmin;
    }
    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }



    /**
     * toString method
     * makes the toString of a user look good
     * @return
     */
    @Override
    public String toString(){

        return username +","+password+","+ isAdmin;
    }
}
