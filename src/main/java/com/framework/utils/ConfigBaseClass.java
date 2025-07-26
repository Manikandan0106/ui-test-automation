package com.framework.utils;

import java.util.Properties;

public class ConfigBaseClass {
    private static Properties properties = new Properties();
    private static Properties configProperties = new Properties();
    private static Properties localProperties = new Properties();
    
    // Static final variables for all configuration values
    public static final String URL;
    public static final String USERNAME;
    public static final String PASSWORD;
    public static final String BROWSER;
    public static final String JIRA_URL;
    public static final String JIRA_EMAIL;
    public static final String JIRA_TOKEN;
    public static final String JIRA_EXECUTION_ID;
    public static final String INFLUX_URL;
    public static final String INFLUX_USERNAME;
    public static final String INFLUX_PASSWORD;
    public static final String ENV_FILE;
    
    static {
        // Load local.properties first
        localProperties = PropertyFileReader.loadProperties("config/local.properties");
        
        // Load config.properties
        configProperties = PropertyFileReader.loadProperties("config/config.properties");
        
        // Determine environment file to load based on env value from local.properties
        String envValue = localProperties.getProperty("env", "dev");
        String envFile = "config/" + envValue + ".properties";
        
        // Load environment-specific properties file
        properties = PropertyFileReader.loadProperties(envFile);
        
        // Initialize all static final variables with priority: Jenkins env > properties file
        URL = getValueWithPriority("URL", null);
        USERNAME = getValueWithPriority("TEST_USERNAME", null);
        PASSWORD = getValueWithPriority("PASSWORD", null);
        BROWSER = getValueWithPriority("BROWSER", "chrome");
        JIRA_URL = getValueWithPriority("JIRA_URL", null);
        JIRA_EMAIL = getValueWithPriority("JIRA_EMAIL", null);
        JIRA_TOKEN = getValueWithPriority("JIRA_TOKEN", null);
        JIRA_EXECUTION_ID = getValueWithPriority("JIRA_EXECUTION_ID", null);
        INFLUX_URL = getValueWithPriority("INFLUX_URL", null);
        INFLUX_USERNAME = getValueWithPriority("INFLUX_USERNAME", null);
        INFLUX_PASSWORD = getValueWithPriority("INFLUX_PASSWORD", null);
        ENV_FILE = envFile;
    }
    
    /**
     * Get value with priority: Jenkins environment variable > properties file > system property
     * @param key The configuration key
     * @param defaultValue Default value if not found
     * @return The configuration value (trimmed)
     */
    private static String getValueWithPriority(String key, String defaultValue) {
        // Priority 1: Jenkins environment variable
        String envValue = System.getenv(key);
        if (envValue != null && !envValue.trim().isEmpty()) {
            return envValue.trim();
        }
        
        // Priority 2: Local properties file
        String localPropValue = localProperties.getProperty(key);
        if (localPropValue != null && !localPropValue.trim().isEmpty()) {
            return localPropValue.trim();
        }
        
        // Priority 3: Environment-specific properties file
        String envPropValue = properties.getProperty(key);
        if (envPropValue != null && !envPropValue.trim().isEmpty()) {
            return envPropValue.trim();
        }
        
        // Priority 4: Config properties file
        String configPropValue = configProperties.getProperty(key);
        if (configPropValue != null && !configPropValue.trim().isEmpty()) {
            return configPropValue.trim();
        }
        
        // Priority 5: System property (lowest priority)
        String sysPropValue = System.getProperty(key);
        if (sysPropValue != null && !sysPropValue.trim().isEmpty()) {
            return sysPropValue.trim();
        }
        
        // Priority 6: Default value
        return defaultValue != null ? defaultValue.trim() : null;
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static String getEnv(String key) {
        return System.getenv(key);
    }

    public static String getConfig(String key) {
        return getValueWithPriority(key, null);
    }
} 