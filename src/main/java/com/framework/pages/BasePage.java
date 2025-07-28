package com.framework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.framework.utils.DriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;

public class BasePage {
    protected final Logger logger = LogManager.getLogger(getClass());
    protected final WebDriver driver;
    protected final WebDriverWait wait;
    private static final int LOCATOR_RETRY_LIMIT = 2;

    public BasePage() {
        this.driver = DriverFactory.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    protected WebElement findElement(By locator, String elementName) {
        int attempts = 0;
        while (attempts < LOCATOR_RETRY_LIMIT) {
            try {
                logger.info("Finding element: " + elementName);
                WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
                logger.info("Element found: " + elementName);
                return element;
            } catch (StaleElementReferenceException e) {
                logger.warn("StaleElementReferenceException caught, retrying findElement for: " + elementName);
                attempts++;
            } catch (Exception e) {
                logger.error("Failed to find element: " + elementName + " - " + e.getMessage());
                throw e;
            }
        }
        throw new RuntimeException("Failed to find element after retry: " + elementName);
    }

    protected void click(By locator, String elementName) {
        try {
            WebElement element = findElement(locator, elementName);
            logger.info("Clicking element: " + elementName);
            element.click();
            logger.info("Successfully clicked element: " + elementName);
        } catch (Exception e) {
            logger.error("Failed to click element: " + elementName + " - " + e.getMessage());
            throw e;
        }
    }

    protected void enterText(By locator, String text, String elementName) {
        try {
            WebElement element = findElement(locator, elementName);
            logger.info("Entering text in element: " + elementName + " - Text: " + text);
            element.clear();
            element.sendKeys(text);
            logger.info("Successfully entered text in element: " + elementName);
        } catch (Exception e) {
            logger.error("Failed to enter text in element: " + elementName + " - " + e.getMessage());
            throw e;
        }
    }

    protected String getText(By locator, String elementName) {
        try {
            WebElement element = findElement(locator, elementName);
            logger.info("Getting text from element: " + elementName);
            String text = element.getText();
            logger.info("Text retrieved from element: " + elementName + " - Text: " + text);
            return text;
        } catch (Exception e) {
            logger.error("Failed to get text from element: " + elementName + " - " + e.getMessage());
            throw e;
        }
    }

    public boolean isElementDisplayed(By locator, String elementName) {
        try {
            WebElement element = findElement(locator, elementName);
            boolean isDisplayed = element.isDisplayed();
            logger.info("Element display status: " + elementName + " - Displayed: " + isDisplayed);
            return isDisplayed;
        } catch (Exception e) {
            logger.warn("Element not found or not displayed: " + elementName + " - " + e.getMessage());
            return false;
        }
    }
}
