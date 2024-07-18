package com.pricer.page.web.pricer;

import com.pricer.page.web.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;


/**
 * @author Kundan Kumar Sah
 * @description Implemented logic to handle validations
 */
public class TenantPage extends BasePage {
    private static final String PageObjectRepoFileName = "TenantPage.xml";

    public TenantPage(WebDriver driver) {
        super(driver, PageObjectRepoFileName);
    }

    @Step("Get Page Title")
    public String getPageTitle() {
        seleniumUtils.waitForPageToLoad();
        seleniumUtils.waitForLoader(implicitWaitSec);
        return seleniumUtils.getDriver().getTitle();
    }

    @Step("Click on Log Out Icon")
    public void clickOnLogOutIcon() {
        seleniumUtils.waitForElementToBeClickable("icnLogOut");
        seleniumUtils.click("icnLogOut");
    }

    @Step("Search store {0}")
    public void searchStore(String name) {
        seleniumUtils.isElementDisplayed("txtSearchStore", maxWaitTimeInSec);
        seleniumUtils.waitForElementToBeClickable("txtSearchStore");
        seleniumUtils.enterText("txtSearchStore", name);
        seleniumUtils.pressEnterKey("txtSearchStore");
        seleniumUtils.isElementDisplayed("txtSearchStore", implicitWaitSec);
        seleniumUtils.click("txtSearchStore");
    }

    @Step("Click on store")
    public void clickOnStore() {
        seleniumUtils.isElementDisplayed("icnStore", implicitWaitSec);
        seleniumUtils.waitForElementToBeClickable("icnStore");
        seleniumUtils.click("icnStore");
    }

    //---------Mobile Emulation-------------//

    @Step("Search for store {0}")
    public void searchForStore(String name) {
        seleniumUtils.isElementDisplayed("txtSearchStore", maxWaitTimeInSec);
        seleniumUtils.waitForElementToBeClickable("txtSearchStore");
        seleniumUtils.enterText("txtSearchStore", name);
        seleniumUtils.pressEnterKey("txtSearchStore");
    }

    @Step("Click store")
    public void clickStore() {
        seleniumUtils.isElementDisplayed("icnStore", implicitWaitSec);
        seleniumUtils.waitForElementToBeClickable("icnStore");
        seleniumUtils.javaScriptClick("icnStore");
    }

}
