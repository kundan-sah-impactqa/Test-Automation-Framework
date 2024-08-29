package com.starbucks.base;

import com.starbucks.utilities.*;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.Map;

public class BaseTestAPI {

    public static Map<String, Object> dataFromJsonFile;

    static {
        dataFromJsonFile = RestUtils.getJsonDataAsMap("API_Endpoints.json");
    }

    //=============================================//
    protected WebDriver driver;
    public WebDriver getDriver() {
        return driver;
    }
    public static Instant start = null;
    public static Instant end = null;
    @BeforeSuite
    public void clearOldAllureReport() {
        start = Instant.now();
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
        end = Instant.now();
        PostExecutionClass.createReportVersionWithDateTime();
        try {
            String batchFilePath = System.getProperty("user.dir") + "\\GenerateAllureReport.bat";
            // Execute the batch file
            ProcessBuilder processBuilder = new ProcessBuilder(batchFilePath);
            processBuilder.start();
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
