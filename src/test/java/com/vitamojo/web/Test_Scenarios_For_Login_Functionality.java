package com.vitamojo.web;

import com.vitamojo.base.BaseTestWebClassContext;
import com.vitamojo.listeners.RetryAnalyzer;
import com.vitamojo.listeners.TestAllureListener;
import com.vitamojo.page.web.vitamojo.LoginPage;
import com.vitamojo.utilities.ExcelUtil;
import com.vitamojo.utilities.FrameworkConfig;
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

@Epic("com/vitamojo")
@Feature("Service Provided")
@Listeners(TestAllureListener.class)
public class Test_Scenarios_For_Login_Functionality extends BaseTestWebClassContext {

    private Map<String, String> testDataMap;

    @BeforeMethod
    @Parameters({"dataID"})
    @Description("Read test data with testID {0}")
    public void getTestData(String dataID) {
        ExcelUtil excel = new ExcelUtil();
        excel.setWorkbook(
                FrameworkConfig.getStringConfigProperty("TestDataFileLocation"),
                FrameworkConfig.getStringConfigProperty("TestDataSheetNameWeb")
        );
        testDataMap = excel.getRowDataMatchingDataId(dataID);
        System.out.println(testDataMap);
        if (testDataMap.size() < 1)
            Assert.fail("dataID '" + dataID + "' is valid the excel sheet. please check the test data sheet");
    }

    @Test(priority = 1, description = "TC001_Verify that user is able to launch the Pricer application on Browser", retryAnalyzer = RetryAnalyzer.class)
    @Story("verify login functionality")
    @Description("Verify that user is able to launch the Pricer application on Browser")
    public void verify_PricerApplicationOpensOnBrowserSuccessfully() {
        LoginPage loginPage = new LoginPage(driver);
        if (loginPage.isPricerApplicationOpensOnBrowser()) {
            Allure.step("User is successfully able to launch the Pricer application on browser", Status.PASSED);
        } else {
            Allure.step("User is not able to launch the Pricer application on browser", Status.FAILED);
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
        loginPage.enterPassword(FrameworkConfig.getStringConfigProperty("Password"));
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

}
