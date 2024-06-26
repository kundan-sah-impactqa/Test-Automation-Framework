package com.pricer.page.web;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


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
        seleniumUtils.isElementDisplayed("txtEmail", 90);
        seleniumUtils.sleep(3000);
        seleniumUtils.enterText("txtEmail", email);
    }

    @Step("Enter password {0}")
    public void enterPassword(String password) {
        seleniumUtils.isElementDisplayed("txtPassword", 90);
        seleniumUtils.enterText("txtPassword", password);
    }

    @Step("Is Pricer Application Opens On Browser")
    public boolean isPricerApplicationOpensOnBrowser() {
        try {
            return seleniumUtils.isElementDisplayed("txtEmail", implicitWaitSec);
        } catch (Exception ex) {
            return false;
        }
    }

    @Step("Click on Next Button")
    public void clickOnNextButton() {
        seleniumUtils.click("btnNext");
    }

    @Step("Click on Enter Your Plaza Button")
    public void clickOnEnterYourPlaza() {
        seleniumUtils.waitForElementToBeClickable("btnEnterYourPlaza");
        seleniumUtils.click("btnEnterYourPlaza");
        seleniumUtils.sleep(3000);
    }

    @Step("Click on password field eye icon")
    public void clickOnPasswordFieldEyeIcon() {
        seleniumUtils.waitForElementToBeClickable("icnEyePasswordField");
        seleniumUtils.click("icnEyePasswordField");
    }

    @Step("Is password field eye icon working")
    public boolean isPasswordFieldEyeIconWorking(String type) {
        try {
            seleniumUtils.sleep(1000);
            WebElement element = seleniumUtils.getDriver().findElement(By.xpath("//input[@id='password' and @type='" + type + "']"));
            return element.isDisplayed();
        } catch (Exception ex) {
            return false;
        }
    }
}