package com.pricer.base;

import com.google.common.collect.ImmutableMap;
import com.pricer.utilities.*;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;
import java.util.Map;

import static com.pricer.utilities.PostExecutionClass.createReportVersionWithDateTime;
import static com.pricer.utilities.ZipUtils.zipFolder;


/**
 * @description All Test classes should be extended by this class. It will manage browser sessions before and after each test case execution
 * @since 22020-11-10
 */
public class BaseTestMobile {

    protected AppiumDriver driver;
    protected PageObjectRepoHelper.PLATFORM platform;
    protected Map<String, String> testDataMap;
    public static long startTime; // change
    public static long endTime; // change

    /**
     * This method opens the mobile session with the input dataID.
     *
     * @param dataID string that has the dataID.
     * @throws MalformedURLException if any.
     * @throws InterruptedException
     */
    @BeforeTest(description = "Open new mobile session")
    @Parameters({ "dataID" })
    public void openMobileSession(String dataID) throws MalformedURLException, InterruptedException {

/*        ExcelUtil excel = new ExcelUtil();
        excel.setWorkbook(FrameworkConfig.getStringConfigProperty("TestDataFileLocation"),
                FrameworkConfig.getStringConfigProperty("TestDataSheetName_mobile"));

        testDataMap = excel.getRowDataMatchingDataId(dataID);

        if (testDataMap.size() < 1)
            Assert.fail("dataID '" + dataID + "' is valid the excel sheet. please check the com.impactqa.test data sheet");

        excel.setWorkbook(FrameworkConfig.getStringConfigProperty("TestDataFileLocation"),
                "MobileSessionDetails");

        Map<String, String> sessionDetails = excel.getRowDataMatchingDataId(testDataMap.get("MobileSessionID1"));

        if ("ios".equals(sessionDetails.get("platformName").toLowerCase()))
            platform = PageObjectRepoHelper.PLATFORM.IOS;
        else*/
        platform = PageObjectRepoHelper.PLATFORM.ANDROID;
//        driver = DriverProvider.createNewMobileSession(platform, sessionDetails);
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "13");
        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Galaxy A71");
        desiredCapabilities.setCapability(MobileCapabilityType.UDID, "RZ8N21G4DTP");
        desiredCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
//        desiredCapabilities.setCapability("appium:appPackage", "com.starbucks.in.beta");
//        desiredCapabilities.setCapability("appium:appActivity", "com.tsb.app.home.presentation.view.activity.HomeActivity");
        desiredCapabilities.setCapability("appium:appPackage", "com.vitamojo.pos.demo");
        desiredCapabilities.setCapability("appium:appActivity", "vmos.vitamojo.app.MainActivity");
        desiredCapabilities.setCapability("appium:chromeOptions", ImmutableMap.of("w3c", false));

        URL url = new URL("http://127.0.0.1:4723/wd/hub");

        driver = new AndroidDriver(url, desiredCapabilities);
        Thread.sleep(12000);
    }

    /**
     * This method returns the current mobile driver.
     *
     * @return Appium driver instance.
     */
    public AppiumDriver getDriver() {
        return driver;
    }

    /**
     * This method closes the mobile session.
     */
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
        createReportVersionWithDateTime();
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
            zipFolder(sourceFolderPath, zipFilePath);
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



//    protected AppiumDriver driver;
//    public static PageObjectRepoHelper.PLATFORM platform;
//    protected Map<String, String> testDataMap;
//    public static long startTime; // change
//    public static long endTime; // change
//
//    @BeforeTest(description = "Open new mobile session")
//    @Parameters({"dataID"})
//    public void openMobileSession(String dataID) throws MalformedURLException {
//        //--------------- change
//
//        startTime = System.currentTimeMillis();
//        System.out.println("wfbiechigrbigvhbwekjgvhjkrgk 1"+startTime);
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("startTime", startTime);
//        try {
//            FileWriter file = null;
//            try {
//                file = new FileWriter(System.getProperty("user.dir") + "/TestExecutionTime.json");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            file.write(jsonObject.toJSONString());
//            file.close();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        //--------------- change
//        ExcelUtil excel = new ExcelUtil();
//        excel.setWorkbook(FrameworkConfig.getStringConfigProperty("TestDataFileLocation"),
//                FrameworkConfig.getStringConfigProperty("TestDataSheetName_mobile"));
//
//        testDataMap = excel.getRowDataMatchingDataId(dataID);
//
//        if (testDataMap.size() < 1)
//            Assert.fail("dataID '" + dataID + "' is valid the excel sheet. please check the test data sheet");
//
//        excel.setWorkbook(FrameworkConfig.getStringConfigProperty("TestDataFileLocation"),
//                "MobileSessionDetails");
//
//        Map<String, String> sessionDetails = excel.getRowDataMatchingDataId(testDataMap.get("MobileSessionID1"));
//
//        if ("ios".equals(sessionDetails.get("platformName").toLowerCase())){
//            platform = PageObjectRepoHelper.PLATFORM.IOS;
//            AllureEnvironmentPropertyUtil.addProperty("PlatFormName", "iOS");
//        }
//        else{
//            platform = PageObjectRepoHelper.PLATFORM.ANDROID;
//            AllureEnvironmentPropertyUtil.addProperty("PlatFormName", "Android");
//        }
//        AllureEnvironmentPropertyUtil.addProperty("Environment", FrameworkConfig.getStringConfigProperty("ApplicationEnvironment"));
//        driver = DriverProvider.createNewMobileSession(platform, sessionDetails);
//    }
//
//    public AppiumDriver getDriver() {
//        return driver;
//    }
//
//    public void switchContext(String context)
//    {
//        System.out.println("Before Switching : "+driver.getContext());
//        Set<String> con = driver.getContextHandles();
//        System.out.println("Set : " + con);
//        for(String c : con)
//        {
//            System.out.println("Available Context : "+c);
//            if(c.contains(context))
//            {
//                driver.context(c);
//                break;
//            }
//        }
//        System.out.println("After Switching : "+driver.getContext());
//    }
//
//    @Step("Wait for {0}")
//    public void waitForTrackToComplete(int minutes){
//        int second = minutes*2;
//        for(int i =0; i<second; i++) { // 30x30sec=15min
//            try{
//                Thread.sleep(30000);
//            }
//            catch(Exception e){
//            } //every 30 sec
//            getDriver().getPageSource();
//        }
//    }
//
//    @AfterTest(description = "close the mobile session")
//    public void teardownDriverInstance() {
//        if (driver != null)
//            driver.quit();
//    }
//}
