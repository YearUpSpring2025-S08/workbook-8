package com.pluralsight;

import com.mysql.cj.jdbc.MysqlDataSource;
import java.sql.*;
import javax.sql.DataSource;

public class Main {

    private static sqlConnectionInfo sqlConnectionInfo;

    public static void main(String[] args) {

        if (args.length != 3) {
            System.out.println(
                    "Application needs three arguments to run: " +
                            "java com.pluralsight.Main <username> <password> <sqlUrl>");
            System.exit(1);
        }

        sqlConnectionInfo = getSqlConnectionInfoFromArgs(args);

        try{
            displayCities(103);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }




    }

    public static sqlConnectionInfo getSqlConnectionInfoFromArgs(String[] args){
        // get the user name and password from the command line args
        String username = args[0];
        String password = args[1];

        String connectionString = args[2];

        return new sqlConnectionInfo(connectionString, username, password);
    }


    public static void displayCities(int countryId) throws SQLException, ClassNotFoundException {

        // load the MySQL Driver
        Class.forName("com.mysql.cj.jdbc.Driver");


        try (Connection connection = DriverManager.getConnection( sqlConnectionInfo.getConnectionString(), sqlConnectionInfo.getUsername(), sqlConnectionInfo.getPassword());
             PreparedStatement ps = connection.prepareStatement("SELECT city FROM city WHERE country_id = ?");)
        {
            ps.setInt(1, countryId);

            try(ResultSet results = ps.executeQuery()) {
                while (results.next()) {
                    String city = results.getString("city");
                    System.out.println(city);
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void displayAllCities() throws SQLException, ClassNotFoundException {

        // load the MySQL Driver
        Class.forName("com.mysql.cj.jdbc.Driver");


        try (Connection connection = DriverManager.getConnection( sqlConnectionInfo.getConnectionString(), sqlConnectionInfo.getUsername(), sqlConnectionInfo.getPassword());
             PreparedStatement ps = connection.prepareStatement("SELECT city FROM city where country_id");
             ResultSet results = ps.executeQuery();
             )
        {
                while (results.next()) {
                    String city = results.getString("city");
                    System.out.println(city);
                }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}



