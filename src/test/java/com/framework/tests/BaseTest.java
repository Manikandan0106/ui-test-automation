package com.framework.tests;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

import com.framework.pages.LoginPage;
import com.framework.utils.ConfigReader;
import com.framework.utils.DriverFactory;

import io.qameta.allure.testng.AllureTestNg;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Listeners({ AllureTestNg.class })
public class BaseTest {
    protected WebDriver driver;
    private static final Logger logger = LogManager.getLogger(BaseTest.class);

    @BeforeMethod
    public void setUpAndLogin() {
        driver = DriverFactory.getDriver();
        driver.get(ConfigReader.get("URL"));
        LoginPage.login(ConfigReader.get("USERNAME"), ConfigReader.get("PASSWORD"));
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