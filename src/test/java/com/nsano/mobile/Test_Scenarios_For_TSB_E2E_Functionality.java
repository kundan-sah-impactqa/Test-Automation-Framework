package com.nsano.mobile;

import com.nsano.base.BaseTestMobile;
import com.nsano.listeners.TestAllureListener;
import com.nsano.page.mobile.nsano.*;
import com.nsano.utilities.ExcelUtil;
import com.nsano.utilities.FrameworkConfig;
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
public class Test_Scenarios_For_TSB_E2E_Functionality extends BaseTestMobile {

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
    @Test(priority = 1, description = "Starbucks E2E flow")
    @Story("Starbucks E2E flow")
    @Description("Verify Starbucks E2E flow")
    public void verify_TSB_E2E_Flow() throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver, platform);
        loginPage.allowApplicationPermission();
        loginPage.enterUsername("9891001234");
        loginPage.enterPassword("Test@123456");
        loginPage.clickOnLoginButton();
        if (loginPage.isLoginSuccessWithValidCredentials()) {
            Allure.step("User is successfully logged in", Status.PASSED);
        } else {
            Allure.step("User is not able to login successfully", Status.FAILED);
            Assert.fail("Fail");
        }
        loginPage.navigateToDashboardPage();

        DashboardPage dashboardPage = new DashboardPage(driver, platform);
        dashboardPage.clickOnCloseVideoAd();
        dashboardPage.clearCartIfItemExist();
        dashboardPage.clickOnCloseRatingBar();
        dashboardPage.clickOnOrderButton();

        OrderPage orderPage = new OrderPage(driver, platform);
        if (orderPage.isOrderPageDisplayed()) {
            Allure.step("User successfully able to land Cart page", Status.PASSED);
        } else {
            Allure.step("User successfully not able to land on Cart page", Status.FAILED);
            Assert.fail("Fail");
        }
        orderPage.clickOnSearchIcon();
        String item = "Cold coffee";
        orderPage.enterItemName(item);
        orderPage.clickOnAddItemButton();
        orderPage.clickOnAddToCartButton();
        orderPage.clickOnViewCartButton();

        CartPage cartPage = new CartPage(driver, platform);
        if (cartPage.isCartPageDisplayed()) {
            Allure.step("User successfully able to land Cart page", Status.PASSED);
        } else {
            Allure.step("User successfully not able to land on Cart page", Status.FAILED);
            Assert.fail("Fail");
        }
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
