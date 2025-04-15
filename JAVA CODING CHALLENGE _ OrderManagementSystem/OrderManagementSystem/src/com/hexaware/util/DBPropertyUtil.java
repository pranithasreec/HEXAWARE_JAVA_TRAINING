package com.hexaware.util;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
public class DBPropertyUtil {
    public static String getConnectionString(String fileName) {
        String connString = "";
        try {
            Properties props = new Properties();
            InputStream fis = new FileInputStream(fileName);
            props.load(fis);
            String url = props.getProperty("url");
            String user = props.getProperty("username");
            String pwd = props.getProperty("password");
            connString = url + "?user=" + user + "&password=" + pwd;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connString;
    }
}
