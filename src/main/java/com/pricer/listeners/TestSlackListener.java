package com.pricer.listeners;

import com.pricer.utilities.SlackUtils;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TestSlackListener implements ITestListener {

    private int passCount = 0;
    private int failCount = 0;
    private int skipCount = 0;
    private long startTime;
    private long endTime;

    @Override
    public void onStart(ITestContext context) {
        startTime = System.currentTimeMillis();
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        passCount++;
    }

    @Override
    public void onTestFailure(ITestResult result) {
        failCount++;
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        skipCount++;
    }

    @Override
    public void onFinish(ITestContext context) {
        endTime = System.currentTimeMillis();
        int totalCount = passCount + failCount + skipCount;
        long totalExecutionTime = endTime - startTime;

        String formattedExecutionTime = String.format(
                "%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(totalExecutionTime),
                TimeUnit.MILLISECONDS.toMinutes(totalExecutionTime) % 60,
                TimeUnit.MILLISECONDS.toSeconds(totalExecutionTime) % 60
        );

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = sdf.format(new Date());

        String message = String.format(
                "**************** Test Execution Report ****************\n\n" +
                        "Date: %s\n" +
                        "Total Tests: %d\n" +
                        "Passed: %d\n" +
                        "Failed: %d\n" +
                        "Skipped: %d\n" +
                        "Execution Start Time: %s\n" +
                        "Execution End Time: %s\n" +
                        "Total Execution Time: %s\n\n",
                currentDate, totalCount, passCount, failCount, skipCount,
                new Date(startTime), new Date(endTime), formattedExecutionTime
        );

        SlackUtils.sendSlackMessage(message);
    }

    @Override
    public void onTestStart(ITestResult result) {
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    }
}

