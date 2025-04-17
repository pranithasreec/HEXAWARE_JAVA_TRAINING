package com.hexaware.util;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
public class DBConnUtil {
    private static Connection connection;
    static {
        try (InputStream input = DBConnUtil.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                throw new RuntimeException("Unable to find db.properties file in classpath");
            }
            Properties props = new Properties();
            props.load(input);
            String url = props.getProperty("db.url");
            String user = props.getProperty("db.username");
            String password = props.getProperty("db.password");
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connection established.");
        } catch (IOException e) {
            System.err.println("Failed to load db.properties.");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Connection failed.");
            e.printStackTrace();
        }
    }
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                try (InputStream input = DBConnUtil.class.getClassLoader().getResourceAsStream("db.properties")) {
                    Properties props = new Properties();
                    props.load(input);

                    String url = props.getProperty("db.url");
                    String user = props.getProperty("db.username");
                    String password = props.getProperty("db.password");

                    connection = DriverManager.getConnection(url, user, password);
                    System.out.println("Connection re-established.");
                }
            }
        } catch (IOException | SQLException e) {
            System.err.println("Error getting connection.");
            e.printStackTrace();
        }
        return connection;
    }
}
