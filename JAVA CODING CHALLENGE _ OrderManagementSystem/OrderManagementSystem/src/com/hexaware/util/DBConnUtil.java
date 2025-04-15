package com.hexaware.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnUtil {
    private static String url;
    private static String username;
    private static String password;

    static {
        try (InputStream input = DBConnUtil.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                throw new RuntimeException("Unable to find db.properties file in classpath");
            }
            Properties props = new Properties();
            props.load(input);
            url = props.getProperty("db.url");
            username = props.getProperty("db.username");
            password = props.getProperty("db.password");
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (IOException e) {
            System.err.println("Failed to load db.properties.");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
        }
    }
    public static Connection getDBConn() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}
