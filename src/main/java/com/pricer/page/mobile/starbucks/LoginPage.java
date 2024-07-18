package com.pricer.page.mobile.starbucks;

import com.pricer.page.mobile.BasePage;
import com.pricer.utilities.PageObjectRepoHelper;
import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Step;

public class LoginPage extends BasePage {
    private static final String PageObjectRepoFileName = "LoginPage.xml";

    /**
     * Constructor for AssetsPage.
     *
     * @param driver   a {@link AppiumDriver} object
     * @param platform a {@link PageObjectRepoHelper.PLATFORM} object
     */
    public LoginPage(AppiumDriver driver, PageObjectRepoHelper.PLATFORM platform) {
        super(driver, PageObjectRepoFileName, platform);
    }

    /**
     * allow application permissions
     */
    @Step("Allow Application Permissions")
    public void allowApplicationPermission() {
        if (appiumUtils.isElementDisplayed("btnAllowNotifications", 10)) {
            appiumUtils.click("btnAllowNotifications");
        }
        if (appiumUtils.isElementDisplayed("btnAllowLocation", 10)) {
            appiumUtils.click("btnAllowLocation");
        }
        if (appiumUtils.isElementDisplayed("btnAllowDeviceLocation", 10)) {
            appiumUtils.click("btnAllowDeviceLocation");
            appiumUtils.click("rdbAllowAllTheTime");
            appiumUtils.click("btnArrowBack");
        }
    }

    @Step("Enter username {0}")
    public void enterUsername(String email) {
        appiumUtils.isElementDisplayed("txtUsername", 90);
        appiumUtils.enterText("txtUsername", email);
    }

    @Step("Enter password {0}")
    public void enterPassword(String password) {
        appiumUtils.isElementDisplayed("txtPassword", 90);
        appiumUtils.enterText("txtPassword", password);
    }

    /**
     * Click on button : Login
     */
    @Step("Click on Login button")
    public void clickOnLoginButton() {
        appiumUtils.waitForElementToBeClickable("btnLogin");
        appiumUtils.click("btnLogin");
    }

    @Step("Is Login Success with valid credentials")
    public boolean isLoginSuccessWithValidCredentials() {
        try {
            return appiumUtils.isElementDisplayed("icnSwapArrow", 10);
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * Navigate to Dashboard page
     */
    @Step("Navigate to Dashboard page")
    public void navigateToDashboardPage() {
        appiumUtils.waitForElementToBeClickable("icnSwapArrow");
        appiumUtils.click("icnSwapArrow");
        appiumUtils.click("btnSkipConnectToInStoreWifi");
    }

}
