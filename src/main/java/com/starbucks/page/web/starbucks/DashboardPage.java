package com.starbucks.page.web.starbucks;

import com.starbucks.page.web.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;


/**
 * @author Kundan Kumar Sah
 * @description Implemented logic to handle validations
 */
public class DashboardPage extends BasePage {
    private static final String PageObjectRepoFileName = "DashboardPage.xml";

    public DashboardPage(WebDriver driver) {
        super(driver, PageObjectRepoFileName);
    }

    @Step("Get Page Title")
    public String getPageTitle() {
        seleniumUtils.waitForPageToLoad();
        seleniumUtils.waitForLoader(implicitWaitSec);
        return seleniumUtils.getDriver().getTitle();
    }

    @Step("Is Starbucks Application Opens On Browser")
    public boolean isStarbucksApplicationOpensOnBrowser() {
        try {
            return seleniumUtils.isElementDisplayed("imgStarbucksLogo", implicitWaitSec);
        } catch (Exception ex) {
            return false;
        }
    }

    @Step("Click on Profile icon")
    public void clickOnProfileIcon() {
        seleniumUtils.isElementDisplayed("icnProfile", implicitWaitSec);
        seleniumUtils.click("icnProfile");
        seleniumUtils.sleep(3000);
    }

    @Step("Is Login Success With Valid Credentials")
    public boolean isLoginSuccessWithValidCredentials (String partialUrl) {
        try {
            return seleniumUtils.getDriver().getCurrentUrl().contains(partialUrl);
        } catch (Exception ex) {
            return false;
        }
    }

}
