package com.starbucks.page.mobile.starbucks;

import com.starbucks.page.mobile.BasePage;
import com.starbucks.utilities.PageObjectRepoHelper;
import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Step;

public class DashboardPage extends BasePage {
    private static final String PageObjectRepoFileName = "DashboardPage.xml";

    /**
     * Constructor for AssetsPage.
     *
     * @param driver   a {@link AppiumDriver} object
     * @param platform a {@link PageObjectRepoHelper.PLATFORM} object
     */
    public DashboardPage(AppiumDriver driver, PageObjectRepoHelper.PLATFORM platform) {
        super(driver, PageObjectRepoFileName, platform);
    }

    /**
     * Click on button : Close video ad
     */
    @Step("Click on Close video ad")
    public void clickOnCloseVideoAd() throws InterruptedException {
        int x = 425, y = 1455;
        appiumUtils.clickAtCoordinates(x, y, "Close video ad");
    }

    /**
     * Click on button : Close rating bar
     */
    @Step("Click on Close Rating Bar button")
    public void clickOnCloseRatingBar() {
        if (appiumUtils.isElementDisplayed("btnCloseRatingBar", 10)) {
            appiumUtils.click("btnCloseRatingBar");
        }
    }

    /**
     * Click on button : Order
     */
    @Step("Click on Order button")
    public void clickOnOrderButton() {
        if (appiumUtils.isElementDisplayed("btnOrder", 10)) {
            appiumUtils.click("btnOrder");
        }
        appiumUtils.sleepForMiliseconds(2000);
    }

    /**
     * Clear cart : If item exists in cart
     */
    @Step("Clear cart if item exists in cart")
    public void clearCartIfItemExist() {
        if (appiumUtils.isElementDisplayed("btnViewCart", 10)) {
            appiumUtils.click("btnViewCart");
        }
        if (appiumUtils.isElementDisplayed("btnMinusOrder", 10)) {
            appiumUtils.click("btnMinusOrder");
            appiumUtils.click("btnDelete");
        }
    }

}
