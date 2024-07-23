package com.starbucks.page.web.starbucks;

import com.starbucks.page.web.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;


/**
 * @author Kundan Kumar Sah
 * @description Implemented logic to handle validations
 */
public class StorePage extends BasePage {
    private static final String PageObjectRepoFileName = "StorePage.xml";

    public StorePage(WebDriver driver) {
        super(driver, PageObjectRepoFileName);
    }

    @Step("Is store UI displayed")
    public boolean isStoreUIDisplayed() {
        try {
            return seleniumUtils.isElementDisplayed("icnStoreUI", 30);
        } catch (Exception ex) {
            return false;
        }
    }

    @Step("Click on store UI icon")
    public void clickOnStoreUIIcon() {
        seleniumUtils.isElementDisplayed("icnStoreUI", implicitWaitSec);
        seleniumUtils.waitForElementToBeClickable("icnStoreUI");
        seleniumUtils.click("icnStoreUI");
        seleniumUtils.switchTabByIndex(1);
    }

    @Step("Get Page Title")
    public String getPageTitle() {
        seleniumUtils.waitForPageToLoad();
        seleniumUtils.waitForLoader(maxWaitTimeInSec);
        return seleniumUtils.getDriver().getTitle();
    }

    //---------Mobile Emulation-------------//

    @Step("Click store UI icon")
    public void clickStoreUIIcon() {
        seleniumUtils.isElementDisplayed("icnStoreUI", implicitWaitSec);
        seleniumUtils.waitForElementToBeClickable("icnStoreUI");
        seleniumUtils.javaScriptClick("icnStoreUI");
        seleniumUtils.switchTabByIndex(1);
        seleniumUtils.sleep(2000);
    }

}
