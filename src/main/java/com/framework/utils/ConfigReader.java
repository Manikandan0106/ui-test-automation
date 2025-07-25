package com.framework.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import com.framework.utils.CommandLineReader;

public class ConfigReader {
    private static Properties properties = new Properties();
    static {
        String envFile = CommandLineReader.ENV_FILE;
        try {
            FileInputStream fis = new FileInputStream(envFile);
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load " + envFile, e);
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }
} 