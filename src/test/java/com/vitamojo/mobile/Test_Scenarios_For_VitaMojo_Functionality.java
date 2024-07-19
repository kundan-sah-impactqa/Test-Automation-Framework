package com.vitamojo.mobile;

import com.pricer.base.BaseTestMobile;
import com.pricer.listeners.TestAllureListener;
import com.pricer.page.mobile.vitamojo.NewOrderPage;
import com.pricer.page.mobile.vitamojo.LoginPage;
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
public class Test_Scenarios_For_VitaMojo_Functionality extends BaseTestMobile {

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
    @Test(priority = 1, description = "VitaMojo E2E flow")
    @Story("VitaMojo E2E flow")
    @Description("Verify VitaMojo E2E flow")
    public void verify_VitaMojo_E2E_Flow() throws InterruptedException {
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

        newOrderPage.wait(6000);
        newOrderPage.clickOnHamburgerIcon();
        if (newOrderPage.isStockQuantityDisplayed()) {
            Allure.step("User is able to see Stock Quantity", Status.PASSED);
        } else {
            Allure.step("User is not able to see Stock Quantity", Status.FAILED);
            Assert.fail("Fail");
        }

        newOrderPage.clickOnStockQuantity();
        StockQuantityPage stockQuantityPage = new StockQuantityPage(driver, platform);
        if (stockQuantityPage.isStockQuantityPageDisplayed()) {
            Allure.step("User successfully able to land Stock Quantity page", Status.PASSED);
        } else {
            Allure.step("User successfully not able to land on Stock Quantity page", Status.FAILED);
            Assert.fail("Fail");
        }

        String product = "Chicken";
        stockQuantityPage.searchProductName(product);
        if (stockQuantityPage.isProductCountDisplayedBelowSearchBox()) {
            Allure.step("User is able to see Product Count below Search box", Status.PASSED);
        } else {
            Allure.step("User is not able to see Product Count below Search box", Status.FAILED);
            Assert.fail("Fail");
        }

        stockQuantityPage.clickOnHamburgerIcon();
        if (stockQuantityPage.isVirtualBrandsDisplayed()) {
            Allure.step("User is able to see Virtual Brands", Status.PASSED);
        } else {
            Allure.step("User is not able to see Virtual Brands", Status.FAILED);
            Assert.fail("Fail");
        }

        stockQuantityPage.clickOnVirtualBrands();
        VirtualBrandsPage virtualBrandsPage = new VirtualBrandsPage(driver, platform);
        if (virtualBrandsPage.isVirtualBrandsPageDisplayed()) {
            Allure.step("User successfully able to land Virtual Brands page", Status.PASSED);
        } else {
            Allure.step("User successfully not able to land on Virtual Brands page", Status.FAILED);
            Assert.fail("Fail");
        }

        virtualBrandsPage.clickOnHamburgerIcon();
        if (virtualBrandsPage.isLogOutButtonDisplayed()) {
            Allure.step("User is able to see Log Out button", Status.PASSED);
        } else {
            Allure.step("User is not able to see Log Out button", Status.FAILED);
            Assert.fail("Fail");
        }

        virtualBrandsPage.clickOnLogOutButton();
        if (loginPage.isLoginPageDisplayedAfterLogout()) {
            Allure.step("User successfully able to land on Login Page", Status.PASSED);
        } else {
            Allure.step("User successfully not able to land on Login Page", Status.FAILED);
            Assert.fail("Fail");
        }
    }
}
