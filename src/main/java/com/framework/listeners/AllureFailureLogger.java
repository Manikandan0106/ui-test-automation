package com.framework.listeners;

import io.qameta.allure.Allure;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import com.framework.utils.DriverFactory;
import java.io.ByteArrayInputStream;

public class AllureFailureLogger implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        Throwable cause = result.getThrowable();
        String message = (cause != null && cause.getMessage() != null) ? cause.getMessage() : "Unknown Error";
        Allure.addAttachment("Failure Reason", message);
        attachScreenshot("Failure Screenshot");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        attachScreenshot("Success Screenshot");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        String message = (result.getThrowable() != null && result.getThrowable().getMessage() != null)
                ? result.getThrowable().getMessage()
                : "No reason provided";
        Allure.addAttachment("Test Skipped", message);
        attachScreenshot("Skipped Screenshot");
    }

    private void attachScreenshot(String name) {
        try {
            WebDriver driver = DriverFactory.getDriver();
            if (driver instanceof TakesScreenshot) {
                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                Allure.addAttachment(name, new ByteArrayInputStream(screenshot));
            }
        } catch (Exception e) {
            Allure.addAttachment(name + " (Failed to capture)", e.getMessage());
        }
    }

    // other overrides can be left empty
    @Override public void onTestStart(ITestResult result) {}
    @Override public void onTestFailedButWithinSuccessPercentage(ITestResult result) {}
    @Override public void onStart(ITestContext context) {}
    @Override public void onFinish(ITestContext context) {}
} 