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
public class Test_Scenarios_For_NSano_Login_Functionality extends BaseTestWebClassContext {

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

    @Test(priority = 1, description = "TC001_Verify that user is not able to do Login with invalid username & password")
    @Story("verify login functionality")
    @Description("Verify that user is not able to do Login with invalid username & password")
    public void verify_UserNotAbleToLoginWithInvalidUsernameAndPassword() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail("ladmin");
        loginPage.enterPassword("password@1");
        loginPage.clickOnLoginButton();

        if (loginPage.isInvalidUsernameAndOrPasswordErrorMsgDisplay()) {
            Allure.step("User is not able to do login with invalid username & password, Status.PASSED");
        } else {
            Allure.step("User is able to do login with invalid username & password", Status.FAILED);
            Assert.fail("Fail");
        }
    }

    @Test(priority = 1, description = "TC002_Verify that user is not able to do Login with invalid password")
    @Story("verify login functionality")
    @Description("Verify that user is not able to do Login with invalid password")
    public void verify_UserNotAbleToLoginWithInvalidPassword() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail("LAdmin");
        loginPage.enterPassword("password@1");
        loginPage.clickOnLoginButton();

        if (loginPage.isInvalidUsernameAndOrPasswordErrorMsgDisplay()) {
            Allure.step("User is not able to do login with invalid password, Status.PASSED");
        } else {
            Allure.step("User is able to do login with invalid password", Status.FAILED);
            Assert.fail("Fail");
        }
    }

    @Test(priority = 1, description = "TC003_Verify that user is not able to do Login with invalid username")
    @Story("verify login functionality")
    @Description("Verify that user is not able to do Login with invalid username")
    public void verify_UserNotAbleToLoginWithInvalidUsername() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail("RAdmin");
        loginPage.enterPassword(FrameworkConfig.getStringConfigProperty("Password"));
        loginPage.clickOnLoginButton();

        if (loginPage.isInvalidUsernameAndOrPasswordErrorMsgDisplay()) {
            Allure.step("User is not able to do login with invalid username, Status.PASSED");
        } else {
            Allure.step("User is able to do login with invalid username", Status.FAILED);
            Assert.fail("Fail");
        }
    }

}