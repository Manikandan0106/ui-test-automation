package com.framework.listeners;

import io.qameta.allure.Description;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.framework.utils.JiraUtil;

public class TestListener implements ITestListener {
    private static final String testExecutionId = System.getenv("JIRA_EXECUTION_ID");

    @Override
    public void onTestSuccess(ITestResult result) {
        updateJira(result, "Passed ✅");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        updateJira(result, "Failed ❌");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        updateJira(result, "Skipped ⏭️");
    }

    private void updateJira(ITestResult result, String status) {
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
} 