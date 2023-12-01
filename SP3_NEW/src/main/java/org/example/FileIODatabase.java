package org.example;

import java.sql.*;
import java.util.ArrayList;


public class FileIODatabase implements IO {
    ArrayList<Series> series = new ArrayList<>();
    ArrayList<Movie> movies = new ArrayList<>();
    static ArrayList<User> users = new ArrayList<>();
    User user;
    // database URL
    static final String DB_URL = "jdbc:mysql://localhost/streamingkmd";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "!Frik3400Rouvi1312";


    public ArrayList<User> readUserData() {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            //STEP 1: Register JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            //STEP 2: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //STEP 3: Execute a query
            System.out.println("Creating statement...");
            String sql = "SELECT username, password,admin FROM streamingkmd.user";
            stmt = conn.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery(sql);

            //STEP 4: Extract data from result set
            while (rs.next()) {
                //Retrieve by column name


                String name = rs.getString("username");
                String password = rs.getString("Password");
                boolean admin = rs.getBoolean("admin");

                User user = new User(name, password, admin);


                users.add(user);

                //System.out.println(name + ": " + genre+ ": " +year+ ": " +rating);

            }
            //STEP 5: Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
        return users;
    }

    @Override
    public void saveUserData(ArrayList<User> newUsersList) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            // Register JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Open a connectiond
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            //user.createUs
            // SQL query to insert user data
            for (User user : newUsersList) {
                String sql = "INSERT INTO streamingkmd.user (username, password,admin) VALUES (?, ?, ?)";
                stmt = conn.prepareStatement(sql);


                // Set parameters for the SQL query
                stmt.setString(1, user.getUsername());
                stmt.setString(2, user.getPassword());
                stmt.setInt(3, user.convertBooleanToTinyInt(user.getIsAdmin()));

                // Execute the update
                stmt.executeUpdate();

                System.out.println("User data saved to database.");
            }


        } catch (SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            // Clean-up environment
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            } // nothing we can do
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }


    @Override
    public ArrayList<Movie> readMovieData() {

        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            //STEP 1: Register JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            //STEP 2: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //STEP 3: Execute a query
            System.out.println("Creating statement...");
            String sql = "SELECT name, genre,year,rating FROM streamingkmd.movies";
            stmt = conn.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery(sql);

            //STEP 4: Extract data from result set
            while (rs.next()) {
                //Retrieve by column name


                String name = rs.getString("Name");
                String genre = rs.getString("Genre");
                int year = rs.getInt("Year");
                double rating = rs.getDouble("Rating");

                Movie movie = new Movie(name, year, genre, rating);


                movies.add(movie);



            }
            //STEP 5: Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try

        return movies;

    }

    @Override
    public void saveMovieData(ArrayList<Movie> movies) {


    }


    @Override
    public ArrayList<Series> readSeriesData() {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            //STEP 1: Register JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            //STEP 2: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //STEP 3: Execute a query
            System.out.println("Creating statement...");
            String sql = "SELECT name,genre,year,rating, seasons FROM streamingkmd.series";
            stmt = conn.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery(sql);
            //STEP 4: Extract data from result set
            while (rs.next()) {
                String title = rs.getString("name");
                String yearRange = rs.getString("year");

                int[] years = parseYearRange(yearRange);

                int startYear = years[0];
                int endYear = years[1];

                if (startYear == -1 || endYear == -1) {
                    // Skip this series and move to the next one
                    System.out.println("Error reading year info for series: " + title);
                    continue;
                }


                String[] genres = rs.getString("genre").split(", ");
                double rating = Double.parseDouble(rs.getString("rating"));

                int totalSeasons = 0;

                String seasonData = rs.getString("seasons");
                String[] seasonParts = seasonData.split("\\. ");

                for (String seasonPart : seasonParts) {
                    String[] seasonInfo = seasonPart.split("-");
                    if (seasonInfo.length == 2) {
                        totalSeasons = seasonParts.length;
                    } else {
                        System.out.println("Error reading season info for series: " + title);
                    }
                }
                Series serie = new Series (title,startYear,endYear,genres,rating,totalSeasons);
                series.add(serie);
                }
            } catch(SQLException se){
                //Handle errors for JDBC
                se.printStackTrace();
            } catch(Exception e){
                //Handle errors for Class.forName
                e.printStackTrace();
            } finally{
                //finally block used to close resources
             try {
                    if (stmt != null)
                        stmt.close();
                } catch (SQLException se2) {
                }// nothing we can do
                try {
                    if (conn != null)
                        conn.close();
                } catch (SQLException se) {
                    se.printStackTrace();
                }//end finally try
            }//end try
            //System.out.println(series.toString());
            return series;

        }

    private int[] parseYearRange(String yearRange) {
        int[] years = new int[2];

        String[] parts = yearRange.split("-");
        if (parts.length == 2) {
            years[0] = parseIntSafe(parts[0].trim());
            years[1] = parts[1].trim().isEmpty() ? -1 : parseIntSafe(parts[1].trim());
        } else if (parts.length == 1) {
            years[0] = parseIntSafe(parts[0].trim());
            years[1] = -1;
        } else {

            years[0] = -1;
            years[1] = -1;
        }

        return years;
    }
    

    @Override
    public void removeMovieByTitle(String title) {

    }

    @Override
    public void removeSeriesByTitle(String title) {

    }

    @Override
    public void addMovie(Movie movie) {

    }

    @Override
    public void addSeries(Series series) {

    }


    @Override
    public void saveSeriesData(ArrayList<Series> series) {

    }

    @Override
    public String arrayToString(String[] array) {
        return null;
    }

    @Override
    public String formatSeasons(int[] seasons) {
        return null;
    }

    @Override
    public int parseIntSafe(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return -1; // Handle parsing errors by returning -1
        }
    }

    @Override
    public void createTextFile(User user) {

    }


}


