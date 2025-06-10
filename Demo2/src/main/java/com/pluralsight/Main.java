package com.pluralsight;

import com.mysql.cj.jdbc.MysqlDataSource;
import java.sql.*;
import javax.sql.DataSource;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        displayCities(103);
    }


    public static void displayCities(int countryId) throws SQLException, ClassNotFoundException  {

        if (args.length != 2) {
            System.out.println(
                    "Application needs two arguments to run: " +
                            "java com.pluralsight.Main <username> <password>");
            System.exit(1);
        }

        // get the user name and password from the command line args
        String username = args[0];
        String password = args[1];

        System.out.println("Hello world!");

        String connectionString = "jdbc:mysql://localhost:3306/sakila";


        // load the MySQL Driver
        Class.forName("com.mysql.cj.jdbc.Driver");
// 1. open a connection to the database
// use the database URL to point to the correct database
        Connection connection;
        connection = DriverManager.getConnection(connectionString, username,password);


        // define your query
        String query = "SELECT city FROM city " +
                "WHERE country_id = ?";

        // create statement
// the statement is tied to the open connection


        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, countryId);



// 2. Execute your query
        ResultSet results = ps.executeQuery();

// process the results
        while (results.next()) {
            String city = results.getString("city");
            System.out.println(city);
        }
// 3. Close the connection
        results.close();
        ps.close();
        connection.close();

    }


}



