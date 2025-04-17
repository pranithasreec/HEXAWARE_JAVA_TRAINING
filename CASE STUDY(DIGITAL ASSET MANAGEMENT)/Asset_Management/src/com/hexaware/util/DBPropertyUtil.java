package com.hexaware.util;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
public class DBPropertyUtil {
    private static final Properties props = new Properties();

    static {
        try (InputStream input = DBPropertyUtil.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input != null) {
                props.load(input);
            } else {
                System.err.println("Unable to find db.properties file.");
            }
        } catch (IOException e) {
            System.err.println("Error loading DB properties: " + e.getMessage());
        }
    }

    public static String getProperty(String key) {
        return props.getProperty(key);
    }
}
