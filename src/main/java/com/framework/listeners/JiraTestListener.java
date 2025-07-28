package com.framework.listeners;

import io.qameta.allure.Description;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.framework.utils.JiraUtil;
import com.framework.utils.ConfigBaseClass;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import com.framework.utils.DriverFactory;

public class JiraTestListener implements ITestListener {
    private static final Logger logger = LogManager.getLogger(JiraTestListener.class);
    private static final String testExecutionId = ConfigBaseClass.getConfig("JIRA_EXECUTION_ID");

    @Override
    public void onTestSuccess(ITestResult result) {
        updateJira(result, "Passed ✅");
        attachScreenshotToJira(result, "Success");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        updateJira(result, "Failed ❌");
        attachScreenshotToJira(result, "Failure");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        updateJira(result, "Skipped ⏭️");
        attachScreenshotToJira(result, "Skipped");
    }

    private void updateJira(ITestResult result, String status) {
        // Check for Jira credentials
        if (ConfigBaseClass.JIRA_URL == null || ConfigBaseClass.JIRA_EMAIL == null || ConfigBaseClass.JIRA_TOKEN == null) {
            logger.info("Jira credentials or URL not set, skipping Jira update.");
            return;
        }
        Description description = result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(Description.class);
        if (description != null && description.value().startsWith("JIRA-")) {
            String jiraKey = description.value().split(":")[0];
            String comment = String.format(
                    "*Test Case Result:*\n" +
                    "- Test: `%s`\n" +
                    "- Status: `%s`\n" +
                    "- Execution ID: `%s`\n" +
                    "- Method: `%s`\n",
                    result.getMethod().getMethodName(), status, testExecutionId, result.getMethod().getConstructorOrMethod().getMethod().getName()
            );
            JiraUtil.updateTestExecutionSet(testExecutionId, jiraKey, status, comment);
        }
    }

    private void attachScreenshotToJira(ITestResult result, String status) {
        Description description = result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(Description.class);
        if (description != null && description.value().startsWith("JIRA-")) {
            String jiraKey = description.value().split(":")[0];
            try {
                WebDriver driver = DriverFactory.getDriver();
                if (driver instanceof TakesScreenshot) {
                    byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                    String filename = String.format("%s_%s.png", result.getMethod().getMethodName(), status);
                    JiraUtil.attachScreenshotToJira(jiraKey, screenshot, filename);
                }
            } catch (Exception e) {
                logger.warn("Failed to capture or attach screenshot to Jira: " + e.getMessage(), e);
            }
        }
    }
} 