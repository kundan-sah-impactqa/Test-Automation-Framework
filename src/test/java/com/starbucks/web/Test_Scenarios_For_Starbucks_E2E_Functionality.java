package com.starbucks.web;

import com.starbucks.base.BaseTestWebClassContext;
import com.starbucks.listeners.TestAllureListener;
import com.starbucks.page.web.starbucks.HomePage;
import com.starbucks.page.web.starbucks.LoginPage;
import com.starbucks.page.web.starbucks.StorePage;
import com.starbucks.page.web.starbucks.TenantPage;
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

    @Test(priority = 1, description = "TC006_Verify Enter store icon functionality of pin store for Tenant page")
    @Story("Verify content of Tenant page")
    @Description("Verify Enter store icon functionality of pin store for Tenant page")
    public void verify_StoreNavigationFunctionality() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail(FrameworkConfig.getStringConfigProperty("Email"));
        loginPage.clickOnNextButton();
        loginPage.enterPassword(FrameworkConfig.getStringConfigProperty("Password"));
        loginPage.clickOnEnterYourPlaza();

        TenantPage tenantPage = new TenantPage(driver);
        String store = "IMPACTQA";
        tenantPage.searchStore(store);
        tenantPage.clickOnStore();

        StorePage storePage = new StorePage(driver);
        if (storePage.isStoreUIDisplayed()) {
            Allure.step("Enter store icon functionality of pin store is working", Status.PASSED);
        } else {
            Allure.step("Enter store icon functionality of pin store is not working", Status.FAILED);
            Assert.fail("Fail");
        }
    }

    @Test(priority = 1, description = "TC007_Verify that user is able to navigate to Homescreen Page after selecting a store")
    @Story("Verify content of Home page")
    @Description("Verify that user is able to navigate to Homescreen Page after selecting a store")
    public void verify_UserLandsOnHomePageSuccessfully() {
        StorePage storePage = new StorePage(driver);
        storePage.clickOnStoreUIIcon();
        String title = "Pricer Store";
        HomePage homePage = new HomePage(driver);
        if (homePage.getPageTitle().equalsIgnoreCase(title)) {
            Allure.step("Verify user is able to navigate to Homescreen Page after selecting a store", Status.PASSED);
        } else {
            Allure.step("Verify user is not able to navigate to Homescreen Page after selecting a store", Status.FAILED);
            Assert.fail("Fail");
        }
    }

    @Test(priority = 1, description = "TC008_Verify that 'Pricer Labels Cards' should be available in Overview section of Status tab")
    @Story("Verify content of Home page")
    @Description("Verify that 'Pricer Labels Cards' should be available in Overview section of Status tab")
    public void verify_PricerLabelsCardsExists() {
        HomePage homePage = new HomePage(driver);
        if (homePage.isPricerLabelsCardsDisplayed()) {
            Allure.step("Verify 'Pricer Labels Cards' available in Overview section of Status tab", Status.PASSED);
        } else {
            Allure.step("Verify 'Pricer Labels Cards' is not available in Overview section of Status tab", Status.FAILED);
            Assert.fail("Fail");
        }
    }

    @Test(priority = 1, description = "TC009_Verify that Request History Table should be present in Request History section of Status tab")
    @Story("Verify content of Home page")
    @Description("Verify that Request History Table should be present in Request History section of Status tab")
    public void verify_RequestHistoryTableDisplayed() {
        HomePage homePage = new HomePage(driver);
        homePage.clickOnRequestHistoryTab();
        if (homePage.isRequestHistoryTableDisplayed()) {
            Allure.step("Verify Request History Table should be present in Request History section of Status tab", Status.PASSED);
        } else {
            Allure.step("Verify Request History Table is not present in Request History section of Status tab", Status.FAILED);
            Assert.fail("Fail");
        }
    }

    @Test(priority = 1, description = "TC010_Verify that Reports Table should be present in Reports section of Status tab")
    @Story("Verify content of Home page")
    @Description("Verify that Reports Table should be present in Reports section of Status tab")
    public void verify_ReportsTableDisplayed() {
        HomePage homePage = new HomePage(driver);
        homePage.clickOnReportsTab();
        if (homePage.isReportsTableDisplayed()) {
            Allure.step("Verify Reports Table should be present in Reports section of Status tab", Status.PASSED);
        } else {
            Allure.step("Verify Reports Table is not present in Reports section of Status tab", Status.FAILED);
            Assert.fail("Fail");
        }
    }
}
