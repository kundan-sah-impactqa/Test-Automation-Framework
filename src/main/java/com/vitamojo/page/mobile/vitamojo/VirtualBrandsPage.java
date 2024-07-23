package com.vitamojo.page.mobile.vitamojo;

import com.vitamojo.page.mobile.BasePage;
import com.vitamojo.utilities.PageObjectRepoHelper;
import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Step;

public class VirtualBrandsPage extends BasePage {
    private static final String PageObjectRepoFileName = "VirtualBrandsPage.xml";

    /**
     * Constructor for AssetsPage.
     *
     * @param driver   a {@link AppiumDriver} object
     * @param platform a {@link PageObjectRepoHelper.PLATFORM} object
     */
    public VirtualBrandsPage(AppiumDriver driver, PageObjectRepoHelper.PLATFORM platform) {
        super(driver, PageObjectRepoFileName, platform);
    }

    @Step("Is Virtual Brands Page Displayed")
    public boolean isVirtualBrandsPageDisplayed() {
        try {
            return appiumUtils.isElementDisplayed("lblVirtualBrands", 30);
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

    @Step("Is Log Out Button Displayed")
    public boolean isLogOutButtonDisplayed() {
        try {
            return appiumUtils.isElementDisplayed("btnLogOut", 20);
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * Click on button : Log Out
     */
    @Step("Click on Log Out Button")
    public void clickOnLogOutButton() {
        if (appiumUtils.isElementDisplayed("btnLogOut", 10)) {
            appiumUtils.click("btnLogOut");
        }
    }

}
