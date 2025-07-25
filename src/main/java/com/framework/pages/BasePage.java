package com.framework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.framework.utils.DriverFactory;
import java.time.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.StaleElementReferenceException;

public class BasePage {
    protected final Logger logger = LogManager.getLogger(getClass());
    protected final WebDriver driver;
    protected final WebDriverWait wait;

    public BasePage() {
        this.driver = DriverFactory.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    protected WebElement findElement(By locator, String elementName) {
        try {
            logger.info("Finding element: " + elementName);
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
            logger.info("Element found: " + elementName);
            return element;
        } catch (Exception e) {
            logger.error("Failed to find element: " + elementName + " - " + e.getMessage());
            throw e;
        }
    }

    protected void click(By locator, String elementName) {
        int attempts = 0;
        while (attempts < 2) {
            try {
                logger.info("Clicking element: " + elementName);
                WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
                element.click();
                logger.info("Successfully clicked element: " + elementName);
                return;
            } catch (StaleElementReferenceException e) {
                logger.warn("StaleElementReferenceException caught, retrying click for: " + elementName);
                attempts++;
            } catch (Exception e) {
                logger.error("Failed to click element: " + elementName + " - " + e.getMessage());
                throw e;
            }
        }
        throw new RuntimeException("Failed to click element after retry: " + elementName);
    }

    protected void enterText(By locator, String text, String elementName) {
        int attempts = 0;
        while (attempts < 2) {
            try {
                logger.info("Entering text in element: " + elementName + " - Text: " + text);
                WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
                element.clear();
                element.sendKeys(text);
                logger.info("Successfully entered text in element: " + elementName);
                return;
            } catch (StaleElementReferenceException e) {
                logger.warn("StaleElementReferenceException caught, retrying enterText for: " + elementName);
                attempts++;
            } catch (Exception e) {
                logger.error("Failed to enter text in element: " + elementName + " - " + e.getMessage());
                throw e;
            }
        }
        throw new RuntimeException("Failed to enter text after retry: " + elementName);
    }

    protected String getText(By locator, String elementName) {
        int attempts = 0;
        while (attempts < 2) {
            try {
                logger.info("Getting text from element: " + elementName);
                WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
                String text = element.getText();
                logger.info("Text retrieved from element: " + elementName + " - Text: " + text);
                return text;
            } catch (StaleElementReferenceException e) {
                logger.warn("StaleElementReferenceException caught, retrying getText for: " + elementName);
                attempts++;
            } catch (Exception e) {
                logger.error("Failed to get text from element: " + elementName + " - " + e.getMessage());
                throw e;
            }
        }
        throw new RuntimeException("Failed to get text after retry: " + elementName);
    }

    protected boolean isElementDisplayed(By locator, String elementName) {
        int attempts = 0;
        while (attempts < 2) {
            try {
                logger.info("Checking if element is displayed: " + elementName);
                WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
                boolean isDisplayed = element.isDisplayed();
                logger.info("Element display status: " + elementName + " - Displayed: " + isDisplayed);
                return isDisplayed;
            } catch (StaleElementReferenceException e) {
                logger.warn("StaleElementReferenceException caught, retrying isElementDisplayed for: " + elementName);
                attempts++;
            } catch (Exception e) {
                logger.warn("Element not found or not displayed: " + elementName + " - " + e.getMessage());
                return false;
            }
        }
        return false;
    }
} 