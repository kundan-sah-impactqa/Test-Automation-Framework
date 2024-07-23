package com.starbucks.page.web.starbucks;

import com.starbucks.page.web.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;


/**
 * @author Kundan Kumar Sah
 * @description Implemented logic to handle validations
 */
public class ProfilePage extends BasePage {
    private static final String PageObjectRepoFileName = "ProfilePage.xml";

    public ProfilePage(WebDriver driver) {
        super(driver, PageObjectRepoFileName);
    }

    @Step("Get Page Title")
    public String getPageTitle() {
        seleniumUtils.waitForPageToLoad();
        seleniumUtils.waitForLoader(implicitWaitSec);
        return seleniumUtils.getDriver().getTitle();
    }

    @Step("Click on Login Or SignUp button")
    public void clickOnLoginOrSignUpButton() {
        seleniumUtils.isElementDisplayed("btnLoginOrSignUp", implicitWaitSec);
        seleniumUtils.click("btnLoginOrSignUp");
    }
    
}
