package com.starbucks.web;

import com.starbucks.base.BaseTestWebClassContext;
import com.starbucks.listeners.RetryAnalyzer;
import com.starbucks.listeners.TestAllureListener;
import com.starbucks.page.web.starbucks.*;
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

    @Test(priority = 1, description = "TC001_Verify that user is able to launch the Starbucks application on Browser", retryAnalyzer = RetryAnalyzer.class)
    @Story("verify login functionality")
    @Description("Verify that user is able to launch the Starbucks application on Browser")
    public void verify_StarbucksApplicationOpensOnBrowserSuccessfully() {
        LoginPage loginPage = new LoginPage(driver);
        String username = "Starbucksdev";
        String password = "tsbdev@star7";
        loginPage.enterSecureLoginCredentials(username, password);
        DashboardPage dashboardPage = new DashboardPage(driver);
        if (dashboardPage.isStarbucksApplicationOpensOnBrowser()) {
            Allure.step("User is successfully able to launch the Starbucks application on browser", Status.PASSED);
        } else {
            Allure.step("User is not able to launch the Starbucks application on browser", Status.FAILED);
            Assert.fail("Fail");
        }
    }

    @Test(priority = 1, description = "TC002_Verify that user is successfully able to land on Profile Page")
    @Story("verify profile page functionality")
    @Description("Verify that user is successfully able to land on Profile Page")
    public void verify_UserSuccessfullyAbleToLandOnProfilePage() {
        DashboardPage dashboardPage = new DashboardPage(driver);
        dashboardPage.clickOnProfileIcon();
        ProfilePage profilePage = new ProfilePage(driver);
        String title = "Profile | Tata Starbucks";
        if (profilePage.getPageTitle().equalsIgnoreCase(title)) {
            Allure.step("User is successfully able to land on Profile Page", Status.PASSED);
        } else {
            Allure.step("User is not able to land on Profile Page", Status.FAILED);
            Assert.fail("Fail");
        }
    }

    @Test(priority = 1, description = "TC003_Verify that user is successfully able to do Login with valid credentials")
    @Story("verify login functionality")
    @Description("Verify that user is successfully able to do Login with valid credentials")
    public void verify_UserSuccessfullyAbleToLoginWithValidCredentials() {
        ProfilePage profilePage = new ProfilePage(driver);
        profilePage.clickOnLoginOrSignUpButton();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail(FrameworkConfig.getStringConfigProperty("Email"));
        loginPage.enterPassword(FrameworkConfig.getStringConfigProperty("Password"));
        loginPage.clickOnLoginButton();

        DashboardPage dashboardPage = new DashboardPage(driver);
        if (dashboardPage.isLoginSuccessWithValidCredentials("dashboard")) {
            Allure.step("User is successfully able to do Login with valid credentials", Status.PASSED);
        } else {
            Allure.step("User is not able to do Login with valid credentials", Status.FAILED);
            Assert.fail("Fail");
        }
    }

    @Test(priority = 1, description = "TC004_Verify that the user is able to see total stars, rewards, and another reward level status on top of the dashboard")
    @Story("verify dashboard page functionality")
    @Description("Verify that the user is able to see total stars, rewards, and another reward level status on top of the dashboard")
    public void verify_DashboardPageFunctionality() {
        DashboardPage dashboardPage = new DashboardPage(driver);
        if (dashboardPage.isTotalStarsRewardsAndRewardLevelDisplayed()) {
            Allure.step("User is able to see total stars, rewards, and another reward level status", Status.PASSED);
        } else {
            Allure.step("User is not able to see total stars, rewards, and another reward level status", Status.FAILED);
            Assert.fail("Fail");
        }
    }

    @Test(priority = 1, description = "TC005_Verify that user is successfully able to land on Store Page")
    @Story("verify store page functionality")
    @Description("Verify that user is successfully able to land on Store Page")
    public void verify_UserSuccessfullyAbleToLandOnStorePage() {
        DashboardPage dashboardPage = new DashboardPage(driver);
        dashboardPage.clearExistingCart();
        dashboardPage.clickOnStorePage();
        StorePage storePage = new StorePage(driver);
        String title = "Store | Tata Starbucks";
        if (storePage.getPageTitle().equalsIgnoreCase(title)) {
            Allure.step("User is successfully able to land on Store Page", Status.PASSED);
        } else {
            Allure.step("User is not able to land on Store Page", Status.FAILED);
            Assert.fail("Fail");
        }
    }

    @Test(priority = 1, description = "TC006_Verify that user is successfully able to select desired store navigate to Store Details Page")
    @Story("verify store page functionality")
    @Description("Verify that user is successfully able to select desired store navigate to Store Details Page")
    public void verify_UserAbleToSelectDesiredStoreAndNavigateToStoreDetailsPage() {
        StorePage storePage = new StorePage(driver);
        storePage.enterAndSearchStore("Netaji Subhash Place, Shakurpur, Delhi, India");
        storePage.selectStore("Netaji Subhash Place");
        if (storePage.isStoreDetailsPageDisplayed()) {
            Allure.step("User is successfully able to select desired store navigate to Store Details Page", Status.PASSED);
        } else {
            Allure.step("User is not able to select desired store navigate to Store Details Page", Status.FAILED);
            Assert.fail("Fail");
        }
    }

    @Test(priority = 1, description = "TC007_Verify that user is successfully able to navigate to Ordering Page")
    @Story("verify store page functionality")
    @Description("Verify that user is successfully able to navigate to Ordering Page")
    public void verify_UserSuccessfullyAbleToNavigateToOrderingPage() {
        StorePage storePage = new StorePage(driver);
        storePage.clickOnOrderNowButton();
        OrderPage orderPage = new OrderPage(driver);
        if (orderPage.isOrderingPageDisplayed()) {
            Allure.step("User is successfully able to navigate to Ordering Page", Status.PASSED);
        } else {
            Allure.step("User is not able to navigate to Ordering Page", Status.FAILED);
            Assert.fail("Fail");
        }
    }

    @Test(priority = 1, description = "TC008_Verify that user is successfully able to add item and navigate to product page")
    @Story("verify product page functionality")
    @Description("Verify that user is successfully able to add item and navigate to product page")
    public void verify_AddItemAndNavigateToProductPage() {
        OrderPage orderPage = new OrderPage(driver);
        orderPage.clickOnAddItemButton("Signature Hot Chocolate");
        if (orderPage.isSignatureHotChocolatePageDisplayed()) {
            Allure.step("User is successfully able to add item and navigate to product page", Status.PASSED);
        } else {
            Allure.step("User is not able to add item and navigate to product page", Status.FAILED);
            Assert.fail("Fail");
        }
    }

    @Test(priority = 1, description = "TC009_Verify that user is able to see the view cart button after adding item")
    @Story("verify order page functionality")
    @Description("Verify that user is able to see the view cart button after adding item")
    public void verify_UserAbleToSeeTheViewCartButtonAfterAddingItem() {
        OrderPage orderPage = new OrderPage(driver);
        orderPage.clickOnAddItemButton();
        if (orderPage.isViewCartButtonDisplayed()) {
            Allure.step("User is able to see the view cart button after adding item", Status.PASSED);
        } else {
            Allure.step("User is not able to see the view cart button after adding item", Status.FAILED);
            Assert.fail("Fail");
        }
    }

    @Test(priority = 1, description = "TC010_Verify that user is successfully able to land on Cart Page")
    @Story("verify cart page functionality")
    @Description("Verify that user is successfully able to land on Cart Page")
    public void verify_UserSuccessfullyAbleToLandOnCartPage() {
        OrderPage orderPage = new OrderPage(driver);
        orderPage.clickOnViewCartButton();
        CartPage cartPage = new CartPage(driver);
        String title = "Cart | Tata Starbucks";
        if (cartPage.getPageTitle().equalsIgnoreCase(title)) {
            Allure.step("User is successfully able to land on Cart Page", Status.PASSED);
        } else {
            Allure.step("User is not able to land on Cart Page", Status.FAILED);
            Assert.fail("Fail");
        }
    }

    @Test(priority = 1, description = "TC011_Verify that user is able to repeat same order from Cart Page")
    @Story("verify cart page functionality")
    @Description("Verify that user is able to repeat same order from Cart Page")
    public void verify_UserAbleToRepeatSameOrder() {
        CartPage cartPage = new CartPage(driver);
        cartPage.clickOnPlusIcon();
        cartPage.clickOnRepeatLastButton();
        String newCartValue = "2";
        if (cartPage.isCartValueIncreases(newCartValue)) {
            Allure.step("User is able to repeat same order from Cart Page", Status.PASSED);
        } else {
            Allure.step("User is not able to repeat same order from Cart Page", Status.FAILED);
            Assert.fail("Fail");
        }
    }

    @Test(priority = 1, description = "TC012_Verify delete/decrease item functionality on Cart Page")
    @Story("verify cart page functionality")
    @Description("Verify delete/decrease item functionality on Cart Page")
    public void verify_UserAbleToDeleteItem() {
        CartPage cartPage = new CartPage(driver);
        cartPage.clickOnMinusIcon();
        String newCartValue = "1";
        if (cartPage.isCartValueIncreases(newCartValue)) {
            Allure.step("User is able to delete/decrease item", Status.PASSED);
        } else {
            Allure.step("User is not able to delete/decrease item", Status.FAILED);
            Assert.fail("Fail");
        }
    }

    @Test(priority = 1, description = "TC013_Verify cards displayed on make payment page")
    @Story("verify make payment page functionality")
    @Description("Verify cards displayed on make payment page")
    public void verify_CardsDisplayedOnMakePaymentPage() {
        CartPage cartPage = new CartPage(driver);
        cartPage.clickOnOtherPaymentMethods();
        if (cartPage.isCardsDisplayed()) {
            Allure.step("User is able to see cards on make payment page", Status.PASSED);
        } else {
            Allure.step("User is not able to see cards on make payment page", Status.FAILED);
            Assert.fail("Fail");
        }
    }

    @Test(priority = 1, description = "TC014_Verify payment method display in Pay With section")
    @Story("verify cart page functionality")
    @Description("Verify payment method display in Pay With section")
    public void verify_UserAbleToSeePaymentMethodInPayWithSection() {
        CartPage cartPage = new CartPage(driver);
        cartPage.selectCardForPayment();
        if (cartPage.isPaymentMethodSelected()) {
            Allure.step("User is able to see payment method in Pay With section", Status.PASSED);
        } else {
            Allure.step("User is not able see payment method in Pay With section", Status.FAILED);
            Assert.fail("Fail");
        }
    }

    @Test(priority = 1, description = "TC015_Verify user is able to place order successfully")
    @Story("verify order functionality")
    @Description("Verify user is able to place order successfully")
    public void verify_OrderPlaceSuccessfully() {
        CartPage cartPage = new CartPage(driver);
        cartPage.clickOnPlaceOrderButton();
        if (cartPage.isOrderPlacedSuccessfully()) {
            Allure.step("User is able to place order successfully", Status.PASSED);
        } else {
            Allure.step("User is not able to place order successfully", Status.FAILED);
            Assert.fail("Fail");
        }
    }

    @Test(priority = 1, description = "TC016_Verify that user is successfully able to logout from application")
    @Story("verify logout functionality")
    @Description("Verify that user is successfully able to logout from application")
    public void verify_LogOutFunctionality() {
        DashboardPage dashboardPage = new DashboardPage(driver);
        dashboardPage.wait(7000);
        dashboardPage.clickOnProfileIcon();
        ProfilePage profilePage = new ProfilePage(driver);
        profilePage.clickOnLogOutButton();
        LoginPage loginPage = new LoginPage(driver);
        if (loginPage.isLogInPageDisplayed()) {
            Allure.step("User is able to logout from application", Status.PASSED);
        } else {
            Allure.step("User is not able to logout from application", Status.FAILED);
            Assert.fail("Fail");
        }
    }
}
