package com.starbucks.mobile;

import com.pricer.base.BaseTestMobile;
import com.pricer.listeners.TestAllureListener;
import com.pricer.page.mobile.starbucks.*;
import com.pricer.utilities.ExcelUtil;
import com.pricer.utilities.FrameworkConfig;
import io.qameta.allure.*;
import io.qameta.allure.model.Status;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

@Epic("Test Android or iOS")
@Feature("Test Android Execution")
@Listeners(TestAllureListener.class)
public class Test_Scenarios_For_Starbucks_E2E_Functionality extends BaseTestMobile {

    @BeforeClass
    @Parameters({"dataID"})
    @Description("Read test data with testID {0}")
    public void getTestData(String dataID) {
        ExcelUtil excel = new ExcelUtil();
        excel.setWorkbook(FrameworkConfig.getStringConfigProperty("TestDataFileLocation"),
                FrameworkConfig.getStringConfigProperty("TestDataSheetName_mobile"));

        testDataMap = excel.getRowDataMatchingDataId(dataID);
        if (testDataMap.size() < 1)
            Assert.fail("dataID '" + dataID + "' is valid the excel sheet. please check the test data sheet");
    }

    @Test(priority = 1, description = "TC001_Verify that user is not able login into Starbucks application using invalid Password")
    @Story("verify login functionality")
    @Description("Verify that user is not able login into Starbucks application using invalid Password")
    public void verify_LoginFailsWithInvalidPassword() {
        LoginPage loginPage = new LoginPage(driver, platform);
        loginPage.allowApplicationPermission();
        loginPage.enterUsername(FrameworkConfig.getStringConfigProperty("Mobile_Email"));
        loginPage.enterPassword("Test@654321");
        loginPage.clickOnLoginButton();
        if (loginPage.isLoginSuccessWithValidCredentials()) {
            Allure.step("User is successfully logged in", Status.PASSED);
        } else {
            Allure.step("User is not able to login successfully", Status.FAILED);
            Assert.fail("Fail");
        }
    }
    @Test(priority = 1, description = "TC002_Verify that user is able Login into Starbucks Application using valid Credential")
    @Story("verify login functionality")
    @Description("Verify that user is able Login into Starbucks Application using valid Credential")
    public void verify_LoginSuccessWithCorrectCredentials() {
        LoginPage loginPage = new LoginPage(driver, platform);
        loginPage.enterPassword(FrameworkConfig.getStringConfigProperty("Mobile_Password"));
        loginPage.clickOnLoginButton();
        if (loginPage.isLoginSuccessWithValidCredentials()) {
            Allure.step("User is successfully logged in", Status.PASSED);
        } else {
            Allure.step("User is not able to login successfully", Status.FAILED);
            Assert.fail("Fail");
        }
    }

    @Test(priority = 1, description = "TC003_Verify that user is able to land on Order page successfully")
    @Story("verify order page functionality")
    @Description("Verify that user is able to land on Order page successfully")
    public void verify_UserLandOnOrderPageSuccessfully() throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver, platform);
        loginPage.navigateToDashboardPage();

        DashboardPage dashboardPage = new DashboardPage(driver, platform);
        dashboardPage.clickOnCloseVideoAd();
        dashboardPage.clearCartIfItemExist();
        dashboardPage.clickOnCloseRatingBar();
        dashboardPage.clickOnOrderButton();

        OrderPage orderPage = new OrderPage(driver, platform);
        if (orderPage.isOrderPageDisplayed()) {
            Allure.step("User successfully able to land on Order page", Status.PASSED);
        } else {
            Allure.step("User successfully not able to land on Order page", Status.FAILED);
            Assert.fail("Fail");
        }
    }

    @Test(priority = 1, description = "TC004_Verify that user is able to land on Cart page successfully")
    @Story("verify cart page functionality")
    @Description("Verify that user is able to land on Cart page successfully")
    public void verify_UserLandOnCartPageSuccessfully() {
        OrderPage orderPage = new OrderPage(driver, platform);
        orderPage.clickOnSearchIcon();
        String item = "Cold coffee";
        orderPage.enterItemName(item);
        orderPage.clickOnAddItemButton();
        orderPage.clickOnAddToCartButton();
        orderPage.clickOnViewCartButton();

        CartPage cartPage = new CartPage(driver, platform);
        if (cartPage.isCartPageDisplayed()) {
            Allure.step("User successfully able to land on Cart page", Status.PASSED);
        } else {
            Allure.step("User successfully not able to land on Cart page", Status.FAILED);
            Assert.fail("Fail");
        }
    }

    @Test(priority = 1, description = "TC005_Verify that user is able to land on Add Card page successfully")
    @Story("verify dd card page functionality")
    @Description("Verify that user is able to land on Add Card page successfully")
    public void verify_UserLandOnAddCardPageSuccessfully() {
        CartPage cartPage = new CartPage(driver, platform);
        cartPage.clickOnSelectPaymentMode();
        cartPage.clickOnOtherPaymentMethods();
        cartPage.clickOnPlaceOrderButton();

        PaymentPage paymentPage = new PaymentPage(driver, platform);
        paymentPage.clickOnCreditDebitCards();
        if (paymentPage.isAddCardPageDisplayed()) {
            Allure.step("User successfully able to land on Add Card details page", Status.PASSED);
        } else {
            Allure.step("User successfully not able to land on Add Card details page", Status.FAILED);
            Assert.fail("Fail");
        }
    }

    @Test(priority = 1, description = "TC006_Verify that user is successfully able to enter card details & proceed to pay")
    @Story("verify payment page functionality")
    @Description("Verify that user is successfully able to enter card details & proceed to pay")
    public void verify_ProceedToPay() {
        PaymentPage paymentPage = new PaymentPage(driver, platform);
        String cardNumber = "5123456789012346";
        String expiry = "05/25";
        String cvv = "123";
        paymentPage.enterCardDetails(cardNumber, expiry, cvv);
        paymentPage.clickOnProceedToPay();
        if (paymentPage.isOptOutForNowButtonDisplayed()) {
            Allure.step("User successfully able to enter card details & proceed to pay", Status.PASSED);
        } else {
            Allure.step("User is not able to enter card details & proceed to pay successfully", Status.FAILED);
            Assert.fail("Fail");
        }
    }

}
