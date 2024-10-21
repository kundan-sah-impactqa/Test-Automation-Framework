package com.nsano.web;

import com.nsano.base.BaseTestWebClassContext;
import com.nsano.listeners.TestAllureListener;
import com.nsano.page.web.nsano.*;
import com.nsano.utilities.ExcelUtil;
import com.nsano.utilities.FrameworkConfig;
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

@Epic("com/nsano")
@Feature("Test Web Execution")
@Listeners(TestAllureListener.class)
public class Test_Scenarios_For_NSano_E2E_Functionality extends BaseTestWebClassContext {

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

    @Test(priority = 1, description = "TC004_Verify that user is successfully able to do Login with valid credentials")
    @Story("Verify login functionality")
    @Description("Verify that user is successfully able to do Login with valid credentials")
    public void verify_UserSuccessfullyAbleToLoginWithValidCredentials() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail(FrameworkConfig.getStringConfigProperty("Username"));
        loginPage.enterPassword(FrameworkConfig.getStringConfigProperty("Password"));
        loginPage.clickOnLoginButton();

        DashboardPage dashboardPage = new DashboardPage(driver);
        if (dashboardPage.isDashboardPageDisplay()) {
            Allure.step("User is successfully able to do Login with valid credentials", Status.PASSED);
        } else {
            Allure.step("User is not able to do Login with valid credentials", Status.FAILED);
            Assert.fail("Fail");
        }
    }

    @Test(priority = 1, description = "TC005_Verify that user is able to see the dashboard screen")
    @Story("Verify dashboard functionality")
    @Description("Verify that user is able to see the dashboard screen")
    public void verify_DashboardScreenDisplay() {
        SidebarMenu sidebarMenu = new SidebarMenu(driver);
        sidebarMenu.clickOnDashboardButton();

        DashboardPage dashboardPage = new DashboardPage(driver);
        if (dashboardPage.isDashboardPageDisplay()) {
            Allure.step("User is able to see the dashboard screen", Status.PASSED);
        } else {
            Allure.step("User is not able to see the dashboard screen", Status.FAILED);
            Assert.fail("Fail");
        }
    }

    @Test(priority = 1, description = "TC006_Verify that user is able filter data on dashboard screen")
    @Story("Verify filter functionality")
    @Description("Verify that user is able filter data on dashboard screen")
    public void verify_DashboardScreenFilterFunctionality() {
        // Filter by "Last 30 Days"
        DashboardPage dashboardPage = new DashboardPage(driver);
        dashboardPage.clickOnFilterIcon();
        dashboardPage.clickOnDateSent();
        String filterBy = "Last 30 Days";
        dashboardPage.selectDateSent(filterBy);
        String account = "Nsano Business Team";
        dashboardPage.selectAccount(account);
        dashboardPage.clickOnFilterButton();

        String totalCount = dashboardPage.getTotalCount();
        if (dashboardPage.isFilteredDataDisplay(totalCount)) {
            Allure.step("User is able filter data for 'Last 30 Days'", Status.PASSED);
        } else {
            Allure.step("User is not able filter data for 'Last 30 Days'", Status.FAILED);
            Assert.fail("Fail");
        }

        // Filter by "Custom Date Range"
        dashboardPage.clickOnFilterIcon();
        dashboardPage.clickOnDateSent();
        filterBy = "Custom Range";
        dashboardPage.selectDateSent(filterBy);
        dashboardPage.selectFromAndToDate("Sep", "20", "Oct", "15");

        account = "Nsano Business Team";
        dashboardPage.selectAccount(account);
        dashboardPage.clickOnFilterButton();

        totalCount = dashboardPage.getTotalCount();
        if (dashboardPage.isFilteredDataDisplay(totalCount)) {
            Allure.step("User is able filter data for " + filterBy, Status.PASSED);
        } else {
            Allure.step("User is not able filter data for " + filterBy, Status.FAILED);
            Assert.fail("Fail");
        }
    }

    @Test(priority = 1, description = "TC007_Verify account balance at to right corner")
    @Story("Verify dashboard functionality")
    @Description("Verify account balance at to right corner")
    public void verify_AccountBalance() {
        DashboardPage dashboardPage = new DashboardPage(driver);
        if (dashboardPage.isAccountBalanceDisplay()) {
            Allure.step("User is able to see account balance at to right corner", Status.PASSED);
        } else {
            Allure.step("User is not able to see account balance at to right corner", Status.FAILED);
            Assert.fail("Fail");
        }
        String expectedMessage = "A maximum of 160 characters are allowed for each sms. Anything more than this will incur additional charges.";
        if (dashboardPage.isSmsMaxLimitMessageDisplay(expectedMessage)) {
            Allure.step("User is able to see SMS maximum limit message when hover on account balance", Status.PASSED);
        } else {
            Allure.step("User is not able to see SMS maximum limit message when hover on account balance", Status.FAILED);
            Assert.fail("Fail");
        }
    }

    @Test(priority = 1, description = "TC008_Verify list of top-ups per page displayed on TopUp History page")
    @Story("Verify TopUp History page functionality")
    @Description("Verify list of top-ups per page displayed on TopUp History page")
    public void verify_ListOfTopUpsPerPage() {
        SidebarMenu sidebarMenu = new SidebarMenu(driver);
        sidebarMenu.clickOnTopUpHistoryButton();

        TopupHistoryPage topupHistoryPage = new TopupHistoryPage(driver);
        if (topupHistoryPage.verifyListOfTopupsCountPerPage(3)) {
            Allure.step("User is able to see list of top-ups per page", Status.PASSED);
        } else {
            Allure.step("User is not able to see list of top-ups per page", Status.FAILED);
            Assert.fail("Fail");
        }
    }

    @Test(priority = 1, description = "TC009_Verify top-up history page filter functionality")
    @Story("Verify TopUp History page functionality")
    @Description("Verify top-up history page filter functionality")
    public void verify_TopUpHistoryPageFilterFunctionality() {
        TopupHistoryPage topupHistoryPage = new TopupHistoryPage(driver);
        topupHistoryPage.clickOnFilterIcon();

        topupHistoryPage.clickOnDate();
        String filterBy = "Last 30 Days";
        topupHistoryPage.selectDate(filterBy);
        topupHistoryPage.clickOnFilterButton();

        if (topupHistoryPage.verifyListOfTopupsCountPerPage(2)) {
            Allure.step("User is able to see list of top-ups per page", Status.PASSED);
        } else {
            Allure.step("User is not able to see list of top-ups per page", Status.FAILED);
            Assert.fail("Fail");
        }

        // Reset filter entry
        topupHistoryPage.clickOnFilterIcon();
        topupHistoryPage.clickOnResetButton();
        if (topupHistoryPage.verifyListOfTopupsCountPerPage(3)) {
            Allure.step("User is able to reset the filter entry", Status.PASSED);
        } else {
            Allure.step("User is not able to reset the filter entry", Status.FAILED);
            Assert.fail("Fail");
        }

        topupHistoryPage.clickOnFilterIcon();
        topupHistoryPage.clickOnDate();
        filterBy = "Last 30 Days";
        topupHistoryPage.selectDate(filterBy);
        topupHistoryPage.clickOnExportButton();

        String fileName = "topup-report.csv";
        String accountName = "Nsano Business Team";

        int actualCount = topupHistoryPage.getTopupCountFromExportedCsv(fileName, accountName);
        if (actualCount == 2) {
            Allure.step("Verify that user is able to export data", Status.PASSED);
        } else {
            Allure.step("Verify that user is not able to export data", Status.FAILED);
            Assert.fail("Fail");
        }
    }

    @Test(priority = 1, description = "TC010_Verify user is able to see message contents")
    @Story("Verify SMS Reports functionality")
    @Description("Verify user is able to see message contents")
    public void verify_MessageContentsDisplayed() {
        SidebarMenu sidebarMenu = new SidebarMenu(driver);
        sidebarMenu.clickOnReportsButton();
        if (sidebarMenu.isSmsReportsDisplay()) {
            Allure.step("User is able see sms reports button", Status.PASSED);
        } else {
            Allure.step("User is not able see sms reports button", Status.FAILED);
            Assert.fail("Fail");
        }
        sidebarMenu.clickOnSmsReportsButton();
        ReportsPage reportsPage = new ReportsPage(driver);
        if (reportsPage.isMessagesDisplay()) {
            Allure.step("User is able to see list of messages", Status.PASSED);
        } else {
            Allure.step("User is not able to see list of messages", Status.FAILED);
            Assert.fail("Fail");
        }
        String messageId = "670d4e461c01622dc7908a67";
        reportsPage.clickOnMessageId(messageId);
        if (reportsPage.isMessageContentDisplay()) {
            Allure.step("User is able to see message contents", Status.PASSED);
        } else {
            Allure.step("User is not able to see message contents", Status.FAILED);
            Assert.fail("Fail");
        }
    }

    @Test(priority = 1, description = "TC011_Verify SMS Reports Filter functionality")
    @Story("Verify SMS Reports functionality")
    @Description("Verify SMS Reports Filter functionality")
    public void verify_SmsFilterFunctionality() {
        ReportsPage reportsPage = new ReportsPage(driver);
        String filterBy = "Last 30 Days";
        String account = "Nsano Business Team";
        String bulkId = "IZO20241014170054185920";
        String recipient = "233542071549";

        reportsPage.closeMessageDetailsModal();
        reportsPage.clickOnFilterIcon();
        reportsPage.clickOnDateSent();
        reportsPage.selectDateSent(filterBy);
        reportsPage.selectAccount(account);
        reportsPage.enterBulkId(bulkId);
        reportsPage.enterRecipient(recipient);
        reportsPage.clickOnFilterButton();

        if (reportsPage.getFilteredMessages() >= 1) {
            Allure.step("User is able to see list of filtered messages", Status.PASSED);
        } else {
            Allure.step("User is not able to see list of filtered messages", Status.FAILED);
            Assert.fail("Fail");
        }
    }

}
