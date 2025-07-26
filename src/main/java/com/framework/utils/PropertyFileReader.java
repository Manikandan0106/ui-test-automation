package com.framework.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Utility class for reading properties files
 */
public class PropertyFileReader {
    
    /**
     * Load properties from a file
     * @param filePath Path to the properties file
     * @return Properties object containing the loaded properties
     * @throws RuntimeException if file cannot be loaded
     */
    public static Properties loadProperties(String filePath) {
        Properties properties = new Properties();
        try {
            FileInputStream fis = new FileInputStream(filePath);
            properties.load(fis);
            fis.close();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load properties file: " + filePath, e);
        }
        return properties;
    }
    
    /**
     * Get a property value from a properties file
     * @param filePath Path to the properties file
     * @param key Property key
     * @return Property value or null if not found
     */
    public static String getProperty(String filePath, String key) {
        Properties properties = loadProperties(filePath);
        return properties.getProperty(key);
    }
    
    /**
     * Get a property value from a properties file with default value
     * @param filePath Path to the properties file
     * @param key Property key
     * @param defaultValue Default value if property not found
     * @return Property value or default value
     */
    public static String getProperty(String filePath, String key, String defaultValue) {
        Properties properties = loadProperties(filePath);
        return properties.getProperty(key, defaultValue);
    }
} 