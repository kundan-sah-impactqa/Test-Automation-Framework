package com.starbucks.web;

import com.starbucks.base.BaseTestWebClassContext;
import com.starbucks.listeners.TestAllureListener;
import com.starbucks.page.web.starbucks.DashboardPage;
import com.starbucks.page.web.starbucks.LoginPage;
import com.starbucks.utilities.ExcelUtil;
import com.starbucks.utilities.FrameworkConfig;
import io.qameta.allure.*;
import io.qameta.allure.model.Status;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.Map;

/**
 * @author Kundan Kumar Sah
 * @description Validate that user should be able to Log in.
 * Test data should be provided in the data sheet located
 * src/test/resources/TestData/testdata1.xlsx DataID and Browser
 * should be passed from testng_web.xml
 */

@Epic("com/starbucks")
@Feature("Test Web Execution")
@Listeners(TestAllureListener.class)
public class Test_Scenarios_For_Starbucks_E2E_Functionality extends BaseTestWebClassContext {

    private Map<String, String> testDataMap;

    @BeforeMethod
    @Parameters({"dataID"})
    @Description("Read test data with testID {0}")
    public void getTestData(String dataID) {
        ExcelUtil excel = new ExcelUtil();
        excel.setWorkbook(FrameworkConfig.getStringConfigProperty("TestDataFileLocation"), FrameworkConfig.getStringConfigProperty("TestDataSheetNameWeb"));
        testDataMap = excel.getRowDataMatchingDataId(dataID);
        System.out.println(testDataMap);
        if (testDataMap.size() < 1)
            Assert.fail("dataID '" + dataID + "' is valid the excel sheet. please check the test data sheet");
    }

//    @Test(priority = 1, description = "TC008_Verify that 'Pricer Labels Cards' should be available in Overview section of Status tab")
//    @Story("Verify content of Home page")
//    @Description("Verify that 'Pricer Labels Cards' should be available in Overview section of Status tab")
//    public void verify_PricerLabelsCardsExists() {
//        DashboardPage dashboardPage = new DashboardPage(driver);
//        if (dashboardPage.isPricerLabelsCardsDisplayed()) {
//            Allure.step("Verify 'Pricer Labels Cards' available in Overview section of Status tab", Status.PASSED);
//        } else {
//            Allure.step("Verify 'Pricer Labels Cards' is not available in Overview section of Status tab", Status.FAILED);
//            Assert.fail("Fail");
//        }
//    }
//
//    @Test(priority = 1, description = "TC009_Verify that Request History Table should be present in Request History section of Status tab")
//    @Story("Verify content of Home page")
//    @Description("Verify that Request History Table should be present in Request History section of Status tab")
//    public void verify_RequestHistoryTableDisplayed() {
//        DashboardPage dashboardPage = new DashboardPage(driver);
//        dashboardPage.clickOnRequestHistoryTab();
//        if (dashboardPage.isRequestHistoryTableDisplayed()) {
//            Allure.step("Verify Request History Table should be present in Request History section of Status tab", Status.PASSED);
//        } else {
//            Allure.step("Verify Request History Table is not present in Request History section of Status tab", Status.FAILED);
//            Assert.fail("Fail");
//        }
//    }
//
//    @Test(priority = 1, description = "TC010_Verify that Reports Table should be present in Reports section of Status tab")
//    @Story("Verify content of Home page")
//    @Description("Verify that Reports Table should be present in Reports section of Status tab")
//    public void verify_ReportsTableDisplayed() {
//        DashboardPage dashboardPage = new DashboardPage(driver);
//        dashboardPage.clickOnReportsTab();
//        if (dashboardPage.isReportsTableDisplayed()) {
//            Allure.step("Verify Reports Table should be present in Reports section of Status tab", Status.PASSED);
//        } else {
//            Allure.step("Verify Reports Table is not present in Reports section of Status tab", Status.FAILED);
//            Assert.fail("Fail");
//        }
//    }
}
