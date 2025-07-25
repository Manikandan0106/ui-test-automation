package com.framework.utils;

public class CommandLineReader {
    public static final String BROWSER = System.getProperty("browser", "chrome");
    public static final String ENV_FILE = System.getProperty("env", "config/env.properties");
} 