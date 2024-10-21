package com.nsano.page.web.nsano;

import com.nsano.page.web.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;


/**
 * @author Kundan Kumar Sah
 * @description Implemented logic to handle validations
 */
public class LoginPage extends BasePage {
    private static final String PageObjectRepoFileName = "LoginPage.xml";

    public LoginPage(WebDriver driver) {
        super(driver, PageObjectRepoFileName);
    }

    @Step("Enter email address {0}")
    public void enterEmail(String email) {
        seleniumUtils.sleep(3000);
        seleniumUtils.isElementDisplayed("txtUsername", 30);
        seleniumUtils.enterText("txtUsername", email);
    }

    @Step("Enter password {0}")
    public void enterPassword(String password) {
        seleniumUtils.isElementDisplayed("txtPassword", 30);
        seleniumUtils.enterText("txtPassword", password);
    }

    @Step("Click on Login Button")
    public void clickOnLoginButton() {
        seleniumUtils.isElementDisplayed("btnLogin", 30);
        seleniumUtils.click("btnLogin");
        seleniumUtils.sleep(4000);
        seleniumUtils.waitForThePageLoad();
        seleniumUtils.waitForLoader(implicitWaitSec);
    }

    @Step("Is Log In Page Display")
    public boolean isLogInPageDisplay() {
        try {
            return (seleniumUtils.isElementDisplayed("txtUsername", 30));
        } catch (Exception ex) {
            return false;
        }
    }

    @Step("Is Invalid Username And Or Password dError Message Display")
    public boolean isInvalidUsernameAndOrPasswordErrorMsgDisplay() {
        try {
            return (seleniumUtils.isElementDisplayed("lblInvalidUsernameAndOrPassword", 60));
        } catch (Exception ex) {
            return false;
        }
    }

}