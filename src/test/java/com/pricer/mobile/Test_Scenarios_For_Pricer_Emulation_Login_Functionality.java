package com.pricer.mobile;

import com.pricer.base.BaseTestWebClassContext;
import com.pricer.listeners.RetryAnalyzer;
import com.pricer.listeners.TestAllureListener;
import com.pricer.page.web.pricer.LoginPage;
import com.pricer.page.web.pricer.TenantPage;
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
public class Test_Scenarios_For_Pricer_Emulation_Login_Functionality extends BaseTestWebClassContext {

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

    @Test(priority = 1, description = "TC001_Verify that user is able to launch the Pricer application", retryAnalyzer = RetryAnalyzer.class)
    @Story("verify login functionality")
    @Description("Verify that user is able to launch the Pricer application")
    public void verify_PricerApplicationOpensSuccessfully() {
        LoginPage loginPage = new LoginPage(driver);
        if (loginPage.isPricerApplicationOpensOnBrowser()) {
            Allure.step("User is successfully able to launch the Pricer application", Status.PASSED);
        } else {
            Allure.step("User is not able to launch the Pricer application", Status.FAILED);
            Assert.fail("Fail");
        }
    }

    @Test(priority = 1, description = "TC002_Verify that user is able to show and hide password by using eye icon")
    @Story("verify login functionality")
    @Description("Verify that user is able to show and hide password by using eye icon")
    public void verify_PasswordFieldEyeIconFunctionality() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail(FrameworkConfig.getStringConfigProperty("Email"));
        loginPage.clickOnNextButton();
        String password = "Kundan@1234";
        loginPage.enterPassword(password);
        loginPage.clickOnPasswordFieldEyeIcon();
        if (loginPage.isPasswordFieldEyeIconWorking("text")) {
            Allure.step("User is able to show password by using eye icon", Status.PASSED);
        } else {
            Allure.step("User is not able to able to show password by using eye icon", Status.FAILED);
            Assert.fail("Fail");
        }
        loginPage.clickOnPasswordFieldEyeIcon();
        if (loginPage.isPasswordFieldEyeIconWorking("password")) {
            Allure.step("User is able to hide password by using eye icon", Status.PASSED);
        } else {
            Allure.step("User is not able to able to hide password by using eye icon", Status.FAILED);
            Assert.fail("Fail");
        }
    }

    @Test(priority = 1, description = "TC003_Verify that user is not able login into Pricer application using invalid Password")
    @Story("verify login functionality")
    @Description("Verify that user is not able login into Pricer application using invalid Password")
    public void verify_LoginFailsWithInvalidPassword() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickOnEnterYourPlaza();
        String title = "Pricer Plaza Home";
        TenantPage tenantPage = new TenantPage(driver);
        if (tenantPage.getPageTitle().equalsIgnoreCase(title)) {
            Allure.step("User is successfully logged in", Status.PASSED);
        } else {
            Allure.step("User is not able to login successfully", Status.FAILED);
            Assert.fail("Fail");
        }
    }

    @Test(priority = 1, description = "TC004_Verify that user is able Login into Pricer Application using valid Credential")
    @Story("verify login functionality")
    @Description("Verify that user is able Login into Pricer Application using valid Credential")
    public void verify_LoginSuccessWithCorrectCredentials() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterPassword(FrameworkConfig.getStringConfigProperty("Password"));
        loginPage.clickOnEnterYourPlaza();
        String title = "Pricer Plaza Home";
        TenantPage tenantPage = new TenantPage(driver);
        if (tenantPage.getPageTitle().equalsIgnoreCase(title)) {
            Allure.step("User is successfully logged in", Status.PASSED);
        } else {
            Allure.step("User is not able to login successfully", Status.FAILED);
            Assert.fail("Fail");
        }
    }

    @Test(priority = 1, description = "TC005_Verify that user is able to land on Tenant page after successful login")
    @Story("verify login functionality")
    @Description("Verify that user is able to land on Tenant page after successful login")
    public void verify_TenantPageTitle() {
        String title = "Pricer Plaza Home";
        TenantPage tenantPage = new TenantPage(driver);
        if (tenantPage.getPageTitle().equalsIgnoreCase(title)) {
            Allure.step("User is able to land on Tenant page successfully after login", Status.PASSED);
        } else {
            Allure.step("User is not able to land on Tenant page successfully after login", Status.FAILED);
            Assert.fail("Fail");
        }
    }

}
