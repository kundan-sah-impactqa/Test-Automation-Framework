package com.starbucks.page.mobile.starbucks;

import com.starbucks.page.mobile.BasePage;
import com.starbucks.utilities.PageObjectRepoHelper;
import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Step;

public class CartPage extends BasePage {
    private static final String PageObjectRepoFileName = "CartPage.xml";

    /**
     * Constructor for AssetsPage.
     *
     * @param driver   a {@link AppiumDriver} object
     * @param platform a {@link PageObjectRepoHelper.PLATFORM} object
     */
    public CartPage(AppiumDriver driver, PageObjectRepoHelper.PLATFORM platform) {
        super(driver, PageObjectRepoFileName, platform);
    }

    @Step("Is Cart Page Displayed")
    public boolean isCartPageDisplayed() {
        try {
            return appiumUtils.isElementDisplayed("lnkSelectPaymentMode", 30);
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * Click on Link : Select Payment Mode
     */
    @Step("Click on Select Payment Mode")
    public void clickOnSelectPaymentMode() {
        appiumUtils.waitForElementToBeClickable("lnkSelectPaymentMode");
        appiumUtils.click("lnkSelectPaymentMode");
    }

    /**
     * Click on Button : Other Payment Methods
     */
    @Step("Click on Other Payment Methods Button")
    public void clickOnOtherPaymentMethods() {
        if (appiumUtils.isElementDisplayed("btnCloseTip", 10)) {
            appiumUtils.click("btnCloseTip");
        }
        appiumUtils.waitForElementToBeClickable("btnOtherPaymentMethods");
        appiumUtils.click("btnOtherPaymentMethods");
    }

    /**
     * Click on Button : Place Order
     */
    @Step("Click on Place Order Button")
    public void clickOnPlaceOrderButton() {
        appiumUtils.waitForElementToBeClickable("btnPlaceOrder");
        appiumUtils.click("btnPlaceOrder");
    }

}
