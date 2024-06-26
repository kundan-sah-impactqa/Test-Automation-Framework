package com.pricer.mobile;

import com.pricer.base.BaseTestWebClassContext;
import com.pricer.listeners.TestAllureListener;
import com.pricer.page.web.HomePage;
import com.pricer.page.web.LoginPage;
import com.pricer.page.web.StorePage;
import com.pricer.page.web.TenantPage;
import com.pricer.utilities.ExcelUtil;
import com.pricer.utilities.FrameworkConfig;
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

@Epic("com/pricer")
@Feature("Service Provided")
@Listeners(TestAllureListener.class)
public class Test_Scenarios_For_Pricer_Emulation_E2E_Functionality extends BaseTestWebClassContext {

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

    @Test(priority = 1, description = "TC006_Verify that user is able to navigate to Homescreen Page after selecting a store")
    @Story("Verify content of Store page")
    @Description("Verify that user is able to navigate to Homescreen Page after selecting a store")
    public void verify_UserLandsOnHomePageSuccessfully() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail(FrameworkConfig.getStringConfigProperty("Email"));
        loginPage.clickOnNextButton();
        loginPage.enterPassword(FrameworkConfig.getStringConfigProperty("Password"));
        loginPage.clickOnEnterYourPlaza();

        TenantPage tenantPage = new TenantPage(driver);
        String store = "IMPACTQA";
        tenantPage.searchForStore(store);
        tenantPage.clickStore();

        StorePage storePage = new StorePage(driver);
        storePage.clickStoreUIIcon();
        String title = "Pricer Store";
        HomePage homePage = new HomePage(driver);
        if (homePage.getPageTitle().equalsIgnoreCase(title)) {
            Allure.step("Verify user is able to navigate to Homescreen Page after selecting a store", Status.PASSED);
        } else {
            Allure.step("Verify user is not able to navigate to Homescreen Page after selecting a store", Status.FAILED);
            Assert.fail("Fail");
        }
    }

    @Test(priority = 1, description = "TC007_Verify that 'Pricer Labels Cards' should be available in Overview section of Status tab")
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

    @Test(priority = 1, description = "TC008_Verify that Request History Table should be present in Request History section of Status tab")
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

    @Test(priority = 1, description = "TC009_Verify that Reports Table should be present in Reports section of Status tab")
    @Story("Verify content of Home page")
    @Description("Verify that Reports Table should be present in Reports section of Status tab")
    public void verify_ReportsTableDisplayed() {
        HomePage homePage = new HomePage(driver);
        homePage.clickOnScrollRightArrow();
        homePage.clickOnReportsTab();
        if (homePage.isReportsTableDisplayed()) {
            Allure.step("Verify Reports Table should be present in Reports section of Status tab", Status.PASSED);
        } else {
            Allure.step("Verify Reports Table is not present in Reports section of Status tab", Status.FAILED);
            Assert.fail("Fail");
        }
    }

    @Test(priority = 1, description = "TC010_Verify that Items Table should be present in Items tab")
    @Story("Verify content of Home page")
    @Description("Verify that Items Table should be present in Items tab")
    public void verify_ItemsTableDisplayed() {
        HomePage homePage = new HomePage(driver);
        homePage.clickOnItemsTab();
        if (homePage.isItemsTableDisplayed()) {
            Allure.step("Verify Items Table should be present in Items tab", Status.PASSED);
        } else {
            Allure.step("Verify Items Table is not present in Items tab", Status.FAILED);
            Assert.fail("Fail");
        }
    }

}
