package com.starbucks.listeners;

import com.starbucks.utilities.FrameworkConfig;
import com.starbucks.utilities.JiraIntegration;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestJiraListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        // Only log bug if the failure is due to an assertion
        if (result.getThrowable() instanceof AssertionError) {
            String projectKey = FrameworkConfig.getStringConfigProperty("projectKey"); // replace with your project key
            String summary = "Bug: " + result.getMethod().getDescription() + " failed";
            String description = result.getThrowable().toString();
            JiraIntegration.logBug(projectKey, summary, description);
        }
    }

    // Other overridden methods from ITestListener can be left empty or used as needed

    @Override
    public void onTestStart(ITestResult result) {
    }

    @Override
    public void onTestSuccess(ITestResult result) {
    }

    @Override
    public void onTestSkipped(ITestResult result) {
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    }

    @Override
    public void onStart(ITestContext context) {
    }

    @Override
    public void onFinish(ITestContext context) {
    }
}

