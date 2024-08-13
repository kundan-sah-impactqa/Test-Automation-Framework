package com.starbucks.base;

import com.starbucks.utilities.*;
import io.appium.java_client.AppiumDriver;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.Instant;
import java.util.Map;


/**
 * @description All Test classes should be extended by this class. It will manage browser sessions before and after each test case execution
 * @since 22020-11-10
 */
public class BaseTestMobile {

    protected AppiumDriver driver;
    public static PageObjectRepoHelper.PLATFORM platform;
    protected Map<String, String> testDataMap;
    public static long startTime; // change
    public static long endTime; // change

    @BeforeTest(description = "Open new mobile session")
    @Parameters({"dataID"})
    public void openMobileSession(String dataID) throws MalformedURLException {
        //--------------- change

        startTime = System.currentTimeMillis();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("startTime", startTime);
        try {
            FileWriter file = null;
            try {
                file = new FileWriter(System.getProperty("user.dir") + "/TestExecutionTime.json");
            } catch (IOException e) {
                e.printStackTrace();
            }
            file.write(jsonObject.toJSONString());
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ExcelUtil excel = new ExcelUtil();
        excel.setWorkbook(FrameworkConfig.getStringConfigProperty("TestDataFileLocation"),
                FrameworkConfig.getStringConfigProperty("TestDataSheetName_mobile"));

        testDataMap = excel.getRowDataMatchingDataId(dataID);

        if (testDataMap.size() < 1)
            Assert.fail("dataID '" + dataID + "' is valid the excel sheet. please check the test data sheet");

        excel.setWorkbook(FrameworkConfig.getStringConfigProperty("TestDataFileLocation"),
                "MobileSessionDetails");

        Map<String, String> sessionDetails = excel.getRowDataMatchingDataId(testDataMap.get("MobileSessionID1"));

        if ("ios".equals(sessionDetails.get("platformName").toLowerCase())){
            platform = PageObjectRepoHelper.PLATFORM.IOS;
            AllureEnvironmentPropertyUtil.addProperty("PlatFormName", "iOS");
        }
        else{
            platform = PageObjectRepoHelper.PLATFORM.ANDROID;
            AllureEnvironmentPropertyUtil.addProperty("PlatFormName", "Android");
        }
        AllureEnvironmentPropertyUtil.addProperty("Environment", FrameworkConfig.getStringConfigProperty("ApplicationEnvironment"));
        driver = DriverProvider.createNewMobileSession(platform, sessionDetails);
    }

    public AppiumDriver getDriver() {
        return driver;
    }

    @AfterTest(description = "close the mobile session")
    public void teardownDriverInstance() {
        if (driver != null)
            driver.quit();
    }

    @BeforeSuite
    public void clearOldAllureReport() {
        BaseTestWebClassContext b = new BaseTestWebClassContext();
        b.start = Instant.now();
        // Specify the path to your Allure report directory
        String allureResultstDirectory = System.getProperty("user.dir") + "\\allure-results";
        // Create a File object representing the directory
        File directory = new File(allureResultstDirectory);
        // Check if the directory exists
        if (directory.exists()) {
            // If the directory exists, delete it recursively
            deleteDirectory(directory);
            System.out.println("Old Allure result directory deleted successfully.");
        } else {
            System.out.println("No old Allure result directory found.");
        }
    }

    private void deleteDirectory(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteDirectory(file);
                } else {
                    file.delete();
                }
            }
        }
        directory.delete();
    }

    @AfterSuite
    public void generateAllureReport() {
        BaseTestWebClassContext b = new BaseTestWebClassContext();
        b.end = Instant.now();
        PostExecutionClass.createReportVersionWithDateTime();
        try {
//            String batchFilePath = System.getProperty("user.dir") + "\\GenerateAllureReport.bat";
//            // Execute the batch file
//            ProcessBuilder processBuilder = new ProcessBuilder(batchFilePath);
//            processBuilder.start();
            System.out.println("Allure report generated successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }

        clearOldAllureResultsZip();
        convertAllureResultsIntoZip();
        sendExecutionReportViaMail();
    }

    public void clearOldAllureResultsZip() {
        // Delete existing allure-results.zip
        // Specify the path to your Allure report directory
        String allureResultstDirectory = System.getProperty("user.dir") + "\\allure-results.zip";
        // Create a File object representing the directory
        File directory = new File(allureResultstDirectory);
        // Check if the directory exists
        if (directory.exists()) {
            // If the directory exists, delete it recursively
            deleteDirectory(directory);
            System.out.println("Old allure-results.zip deleted successfully.");
        } else {
            System.out.println("No allure-results.zip found.");
        }
    }

    public void convertAllureResultsIntoZip() {
        // Convert allure-results into zip
        String sourceFolderPath = System.getProperty("user.dir") + "\\allure-results";
        String zipFilePath = System.getProperty("user.dir") + "\\allure-results.zip";
        try {
            ZipUtils.zipFolder(sourceFolderPath, zipFilePath);
            System.out.println("allure-results folder zipped successfully!");
        } catch (IOException e) {
            System.err.println("Error zipping folder: " + e.getMessage());
        }
    }

    public void sendExecutionReportViaMail() {
        try {
            String recipientEmail = FrameworkConfig.getStringConfigProperty("ToEmailAddresses");
            String allureReportPath = System.getProperty("user.dir") + "\\allure-results.zip";
            EmailReportUtils emailReportUtils = new EmailReportUtils();
            emailReportUtils.sendEmailWithAttachment(recipientEmail, allureReportPath);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
