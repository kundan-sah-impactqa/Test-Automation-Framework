package com.pricer.page.mobile.vitamojo;

import com.pricer.page.mobile.BasePage;
import com.pricer.utilities.PageObjectRepoHelper;
import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Step;

public class LoginPage extends BasePage {
    private static final String PageObjectRepoFileName = "vLoginPage.xml";

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
        if (appiumUtils.isElementDisplayed("btnAllowTakePictureRecordVideo", 10)) {
            appiumUtils.click("btnAllowTakePictureRecordVideo");
        }
        if (appiumUtils.isElementDisplayed("btnAllowPhoneCalls", 10)) {
            appiumUtils.click("btnAllowPhoneCalls");
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
     * Click on button : Sign In
     */
    @Step("Click on Sign In button")
    public void clickOnSignInButton() {
        appiumUtils.waitForElementToBeClickable("btnSignIn");
        appiumUtils.click("btnSignIn");
    }

    @Step("Is Login Page Displayed After Logout")
    public boolean isLoginPageDisplayedAfterLogout() {
        try {
            return appiumUtils.isElementDisplayed("imgVitaMojoLogo", 20);
        } catch (Exception ex) {
            return false;
        }
    }

}
