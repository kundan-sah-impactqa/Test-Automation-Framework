package com.pricer.page.mobile.vitamojo;

import com.pricer.page.mobile.BasePage;
import com.pricer.utilities.PageObjectRepoHelper;
import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Step;

public class StockQuantityPage extends BasePage {
    private static final String PageObjectRepoFileName = "vStockQuantityPage.xml";

    /**
     * Constructor for AssetsPage.
     *
     * @param driver   a {@link AppiumDriver} object
     * @param platform a {@link PageObjectRepoHelper.PLATFORM} object
     */
    public StockQuantityPage(AppiumDriver driver, PageObjectRepoHelper.PLATFORM platform) {
        super(driver, PageObjectRepoFileName, platform);
    }

    @Step("Is Stock Quantity Page Displayed")
    public boolean isStockQuantityPageDisplayed() {
        try {
            return appiumUtils.isElementDisplayed("lblStockQuantity", 10);
        } catch (Exception ex) {
            return false;
        }
    }

    @Step("Search Product Name {0}")
    public void searchProductName(String product) {
        appiumUtils.isElementDisplayed("txtSearchProductName", 30);
        appiumUtils.enterText("txtSearchProductName", product);
    }

    @Step("Is Product Count Displayed Below Search Box")
    public boolean isProductCountDisplayedBelowSearchBox() {
        try {
            return appiumUtils.isElementDisplayed("txtProductCount", 30);
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * Click on button : Hamburger menu icon
     */
    @Step("Click on Hambuger menu icon")
    public void clickOnHamburgerIcon() {
        if (appiumUtils.isElementDisplayed("icnHamburger", 10)) {
            appiumUtils.click("icnHamburger");
        }
    }

    @Step("Is Virtual Brands Displayed")
    public boolean isVirtualBrandsDisplayed() {
        try {
            return appiumUtils.isElementDisplayed("btnVirtualBrands", 20);
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * Click on button : Virtual Brands
     */
    @Step("Click on Virtual Brands")
    public void clickOnVirtualBrands() {
        if (appiumUtils.isElementDisplayed("btnVirtualBrands", 10)) {
            appiumUtils.click("btnVirtualBrands");
        }
    }

}
