package com.vitamojo.page.mobile.vitamojo;

import com.vitamojo.page.mobile.BasePage;
import com.vitamojo.utilities.PageObjectRepoHelper;
import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Step;

public class NewOrderPage extends BasePage {
    private static final String PageObjectRepoFileName = "NewOrderPage.xml";

    /**
     * Constructor for AssetsPage.
     *
     * @param driver   a {@link AppiumDriver} object
     * @param platform a {@link PageObjectRepoHelper.PLATFORM} object
     */
    public NewOrderPage(AppiumDriver driver, PageObjectRepoHelper.PLATFORM platform) {
        super(driver, PageObjectRepoFileName, platform);
    }

    @Step("Is Login Success with valid credentials")
    public boolean isLoginSuccessWithValidCredentials() {
//            appiumUtils.sleepForMiliseconds(9000);
//            appiumUtils.clickAtCoordinates(145, 1288, "New order");
        try {
            return appiumUtils.isElementDisplayed("lblNewOrder", 30);
        } catch (Exception ex) {
            return false;
        }
    }

    public void wait(int time) throws InterruptedException {
        Thread.sleep(time);
    }

    @Step("Is Hamburger Menu Displayed")
    public boolean isHamburgerMenuIconDisplayed() {
        try {
            return appiumUtils.isElementDisplayed("icnHamburger", 40);
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * Click on button : Hamburger menu icon
     */
    @Step("Click on Hambuger menu icon")
    public void clickOnHamburgerIcon() {
        if (appiumUtils.isElementDisplayed("icnHamburger", 30)) {
            appiumUtils.click("icnHamburger");
        }
    }

    @Step("Is Stock Quantity Displayed")
    public boolean isStockQuantityDisplayed() {
        try {
            return appiumUtils.isElementDisplayed("btnStockQuantity", 10);
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * Click on button : Stock Quantity
     */
    @Step("Click on Stock Quantity")
    public void clickOnStockQuantity() {
        if (appiumUtils.isElementDisplayed("btnStockQuantity", 10)) {
            appiumUtils.click("btnStockQuantity");
        }
    }

}
