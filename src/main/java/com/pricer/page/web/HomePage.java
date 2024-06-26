package com.pricer.page.web;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;


/**
 * @author Kundan Kumar Sah
 * @description Implemented logic to handle validations
 */
public class HomePage extends BasePage {
    private static final String PageObjectRepoFileName = "HomePage.xml";

    public HomePage(WebDriver driver) {
        super(driver, PageObjectRepoFileName);
    }

    @Step("Get Page Title")
    public String getPageTitle() {
        seleniumUtils.waitForPageToLoad();
        seleniumUtils.waitForLoader(implicitWaitSec);
        return seleniumUtils.getDriver().getTitle();
    }

    @Step("Is pricer labels card displayed")
    public boolean isPricerLabelsCardsDisplayed() {
        try {
            return seleniumUtils.isElementDisplayed("lblPricerLabelsCard", 30);
        } catch (Exception ex) {
            return false;
        }
    }

    @Step("Click on Request History tab")
    public void clickOnRequestHistoryTab() {
        seleniumUtils.isElementDisplayed("lnkRequestHistory", implicitWaitSec);
        seleniumUtils.waitForElementToBeClickable("lnkRequestHistory");
        seleniumUtils.javaScriptClick("lnkRequestHistory");
    }

    @Step("Is Request History Table displayed")
    public boolean isRequestHistoryTableDisplayed() {
        try {
            return seleniumUtils.isElementDisplayed("tblRequestHistory", 30);
        } catch (Exception ex) {
            return false;
        }
    }

    @Step("Click on Reports tab")
    public void clickOnReportsTab() {
        seleniumUtils.isElementDisplayed("lnkReports", implicitWaitSec);
        seleniumUtils.waitForElementToBeClickable("lnkReports");
        seleniumUtils.javaScriptClick("lnkReports");
    }

    @Step("Is Request History Table displayed")
    public boolean isReportsTableDisplayed() {
        try {
            return seleniumUtils.isElementDisplayed("tblReports", 30);
        } catch (Exception ex) {
            return false;
        }
    }

    @Step("Click on scroll right arrow")
    public void clickOnScrollRightArrow() {
        seleniumUtils.isElementDisplayed("icnArrowScrollRight", implicitWaitSec);
        seleniumUtils.waitForElementToBeClickable("icnArrowScrollRight");
        seleniumUtils.javaScriptClick("icnArrowScrollRight");
    }

    @Step("Click on Items tab")
    public void clickOnItemsTab() {
        seleniumUtils.isElementDisplayed("lnkItems", implicitWaitSec);
        seleniumUtils.waitForElementToBeClickable("lnkItems");
        seleniumUtils.javaScriptClick("lnkItems");
    }

    @Step("Is Items Table displayed")
    public boolean isItemsTableDisplayed() {
        try {
            return seleniumUtils.isElementDisplayed("tblItems", 30);
        } catch (Exception ex) {
            return false;
        }
    }

}
