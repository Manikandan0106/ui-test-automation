package com.framework.tests;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import com.framework.pages.LoginPage;
import com.framework.utils.DriverFactory;
import com.framework.utils.ConfigBaseClass;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BaseTest {
    protected WebDriver driver;
    protected static final Logger logger = LogManager.getLogger(BaseTest.class);

    @BeforeMethod
    public void setUpAndLogin() {
        driver = DriverFactory.getDriver();
        driver.get(ConfigBaseClass.URL);
        new LoginPage().login(ConfigBaseClass.USERNAME, ConfigBaseClass.PASSWORD);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        try {
            DriverFactory.quitDriver();
        } catch (Exception e) {
            logger.error("Error while quitting driver: " + e.getMessage(), e);
        }
    }
}