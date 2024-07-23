package com.vitamojo.mobile;

import com.pricer.base.BaseTestMobile;
import com.pricer.listeners.TestAllureListener;
import com.pricer.page.mobile.starbucks.CartPage;
import com.pricer.page.mobile.starbucks.DashboardPage;
import com.pricer.page.mobile.starbucks.OrderPage;
import com.pricer.page.mobile.vitamojo.LoginPage;
import com.pricer.page.mobile.vitamojo.NewOrderPage;
import com.pricer.page.mobile.vitamojo.StockQuantityPage;
import com.pricer.page.mobile.vitamojo.VirtualBrandsPage;
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
public class Test_Scenarios_For_VitaMojo_E2E_Functionality extends BaseTestMobile {

    @BeforeClass
    @Parameters({"dataID"})
    @Description("Read test data with testID {0}")
    public void getTestData(String dataID) {
        ExcelUtil excel = new ExcelUtil();
        excel.setWorkbook(FrameworkConfig.getStringConfigProperty("TestDataFileLocation"), FrameworkConfig.getStringConfigProperty("TestDataSheetName_mobile"));

        testDataMap = excel.getRowDataMatchingDataId(dataID);
        if (testDataMap.size() < 1)
            Assert.fail("dataID '" + dataID + "' is valid the excel sheet. please check the test data sheet");
    }

    @Test(priority = 1, description = "TC001_Verify that user is not able login into Vita Mojo application using invalid Password")
    @Story("verify login functionality")
    @Description("Verify that user is not able login into Vita Mojo application using invalid Password")
    public void verify_LoginFailsWithInvalidPassword() {
        LoginPage loginPage = new LoginPage(driver, platform);
        loginPage.allowApplicationPermission();
        loginPage.enterUsername(FrameworkConfig.getStringConfigProperty("VitaMojo_Email"));
        loginPage.enterPassword("Test@12345678");
        loginPage.clickOnSignInButton();
        NewOrderPage newOrderPage = new NewOrderPage(driver, platform);
        if (newOrderPage.isLoginSuccessWithValidCredentials()) {
            Allure.step("User is successfully logged in", Status.PASSED);
        } else {
            Allure.step("User is not able to login successfully", Status.FAILED);
            Assert.fail("Fail");
        }
    }

    @Test(priority = 1, description = "TC002_Verify that user is able Login into Vita Mojo Application using valid Credential")
    @Story("verify login functionality")
    @Description("Verify that user is able Login into Vita Mojo Application using valid Credential")
    public void verify_LoginSuccessWithCorrectCredentials() {
        LoginPage loginPage = new LoginPage(driver, platform);
        loginPage.allowApplicationPermission();
        loginPage.enterUsername(FrameworkConfig.getStringConfigProperty("VitaMojo_Email"));

        loginPage.enterPassword(FrameworkConfig.getStringConfigProperty("VitaMojo_Password"));
        loginPage.clickOnSignInButton();
        NewOrderPage newOrderPage = new NewOrderPage(driver, platform);
        if (newOrderPage.isLoginSuccessWithValidCredentials()) {
            Allure.step("User is successfully logged in", Status.PASSED);
        } else {
            Allure.step("User is not able to login successfully", Status.FAILED);
            Assert.fail("Fail");
        }
    }

    @Test(priority = 1, description = "TC003_Verify that user is able to see Hamburger menu icon")
    @Story("verify menu functionality")
    @Description("Verify that user is able to see Hamburger menu icon")
    public void verify_HamburgerMenuIconDisplayed() throws InterruptedException {
        NewOrderPage newOrderPage = new NewOrderPage(driver, platform);
        newOrderPage.wait(6000);
        if (newOrderPage.isHamburgerMenuIconDisplayed()) {
            Allure.step("User successfully able to see Hamburger menu icon", Status.PASSED);
        } else {
            Allure.step("User successfully not able to see Hamburger menu icon", Status.FAILED);
            Assert.fail("Fail");
        }
    }

    @Test(priority = 1, description = "TC004_Verify that user is able to see Stock Quantity")
    @Story("verify menu functionality")
    @Description("Verify that user is able to see Stock Quantity button")
    public void verify_StockQuantityDisplayed() {
        NewOrderPage newOrderPage = new NewOrderPage(driver, platform);
        newOrderPage.clickOnHamburgerIcon();
        if (newOrderPage.isStockQuantityDisplayed()) {
            Allure.step("User is able to see Stock Quantity", Status.PASSED);
        } else {
            Allure.step("User is not able to see Stock Quantity", Status.FAILED);
            Assert.fail("Fail");
        }
    }

    @Test(priority = 1, description = "TC005_Verify that user is able to land on Stock Quantity page successfully")
    @Story("verify Stock Quantity page functionality")
    @Description("Verify that user is able to land on Stock Quantity page successfully")
    public void verify_UserLandOnStockQuantityPageSuccessfully() {
        NewOrderPage newOrderPage = new NewOrderPage(driver, platform);
        newOrderPage.clickOnStockQuantity();
        StockQuantityPage stockQuantityPage = new StockQuantityPage(driver, platform);
        if (stockQuantityPage.isStockQuantityPageDisplayed()) {
            Allure.step("User successfully able to land Stock Quantity page", Status.PASSED);
        } else {
            Allure.step("User successfully not able to land on Stock Quantity page", Status.FAILED);
            Assert.fail("Fail");
        }
    }

    @Test(priority = 1, description = "TC006_Verify that user is able to see Product Count")
    @Story("verify Stock Quantity page functionality")
    @Description("Verify that user is able to see Product Count")
    public void verify_ProductCountDisplayedOnStockQuantityPage() {
        StockQuantityPage stockQuantityPage = new StockQuantityPage(driver, platform);
        String product = "Chicken";
        stockQuantityPage.searchProductName(product);
        if (stockQuantityPage.isProductCountDisplayedBelowSearchBox()) {
            Allure.step("User is able to see Product Count below Search box", Status.PASSED);
        } else {
            Allure.step("User is not able to see Product Count below Search box", Status.FAILED);
            Assert.fail("Fail");
        }
    }

    @Test(priority = 1, description = "TC007_Verify that user is able to see Virtual Brands")
    @Story("verify menu functionality")
    @Description("Verify that user is able to see Virtual Brands button")
    public void verify_VirtualBrandsDisplayed() {
        StockQuantityPage stockQuantityPage = new StockQuantityPage(driver, platform);
        stockQuantityPage.clickOnHamburgerIcon();
        if (stockQuantityPage.isVirtualBrandsDisplayed()) {
            Allure.step("User is able to see Virtual Brands", Status.PASSED);
        } else {
            Allure.step("User is not able to see Virtual Brands", Status.FAILED);
            Assert.fail("Fail");
        }
    }

    @Test(priority = 1, description = "TC008_Verify that user is able to land on Virtual Brands page successfully")
    @Story("verify Virtual Brands page functionality")
    @Description("Verify that user is able to land on Virtual Brands page successfully")
    public void verify_UserLandOnVirtualBrandsPageSuccessfully() {
        StockQuantityPage stockQuantityPage = new StockQuantityPage(driver, platform);
        stockQuantityPage.clickOnVirtualBrands();
        VirtualBrandsPage virtualBrandsPage = new VirtualBrandsPage(driver, platform);
        if (virtualBrandsPage.isVirtualBrandsPageDisplayed()) {
            Allure.step("User successfully able to land Virtual Brands page", Status.PASSED);
        } else {
            Allure.step("User successfully not able to land on Virtual Brands page", Status.FAILED);
            Assert.fail("Fail");
        }
    }

    @Test(priority = 1, description = "TC009_Verify that user is able to see Log Out button")
    @Story("verify menu functionality")
    @Description("Verify that user is able to see Log Out button")
    public void verify_LogOutButtonDisplayed() {
        VirtualBrandsPage virtualBrandsPage = new VirtualBrandsPage(driver, platform);
        virtualBrandsPage.clickOnHamburgerIcon();
        if (virtualBrandsPage.isLogOutButtonDisplayed()) {
            Allure.step("User is able to see Log Out button", Status.PASSED);
        } else {
            Allure.step("User is not able to see Log Out button", Status.FAILED);
            Assert.fail("Fail");
        }
    }

    @Test(priority = 1, description = "TC010_Verify that user is successfully able to do logout and land on login page")
    @Story("verify Virtual Brands page functionality")
    @Description("Verify that user is successfully able to do logout and land on login page")
    public void verify_LogOutAndLandOnLogInPage() {
        VirtualBrandsPage virtualBrandsPage = new VirtualBrandsPage(driver, platform);
        virtualBrandsPage.clickOnLogOutButton();

        LoginPage loginPage = new LoginPage(driver, platform);
        if (loginPage.isLoginPageDisplayedAfterLogout()) {
            Allure.step("User successfully able to do logout and land on login page", Status.PASSED);
        } else {
            Allure.step("User successfully not able to do logout and land on login page", Status.FAILED);
            Assert.fail("Fail");
        }
    }

}
