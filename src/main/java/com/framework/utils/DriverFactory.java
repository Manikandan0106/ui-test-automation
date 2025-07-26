package com.framework.utils;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import com.framework.utils.ConfigBaseClass;

public class DriverFactory {
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static final Logger logger = LogManager.getLogger(DriverFactory.class);

    public static WebDriver getDriver() {
        if (driver.get() == null) {
            String browser = ConfigBaseClass.BROWSER;
            switch (browser.toLowerCase()) {
                case "chrome": {
                    ChromeOptions chromeOptions = new ChromeOptions();
                    // Disable save password prompt
                    Map<String, Object> prefs = new HashMap<>();
                    prefs.put("credentials_enable_service", false);
                    prefs.put("profile.password_manager_enabled", false);
                    chromeOptions.setExperimentalOption("prefs", prefs);
                    chromeOptions.addArguments("--disable-popup-blocking");
                    chromeOptions.addArguments("--disable-notifications");
                    chromeOptions.addArguments("--start-maximized");
                    chromeOptions.addArguments("--disable-features=PasswordChange");
                    String userDataDir = "C:/temp/temp-chrome-profile-" + Thread.currentThread().getId();
                    chromeOptions.addArguments("--user-data-dir=" + userDataDir);
                    chromeOptions.addArguments("--incognito");
                    driver.set(new ChromeDriver(chromeOptions));
                    break;
                }
                case "firefox": {
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    firefoxOptions.addPreference("dom.webnotifications.enabled", false);
                    firefoxOptions.addPreference("dom.disable_open_during_load", true);
                    driver.set(new FirefoxDriver(firefoxOptions));
                    break;
                }
                default:
                    throw new IllegalArgumentException("Browser not supported: " + browser);
            }
            // Set implicit wait
            driver.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        }
        return driver.get();
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
} 