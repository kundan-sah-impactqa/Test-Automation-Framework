package com.nsano.page.web.nsano;

import com.nsano.page.web.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


/**
 * @author Kundan Kumar Sah
 * @description Implemented logic to handle validations
 */
public class SidebarMenu extends BasePage {
    private static final String PageObjectRepoFileName = "SidebarMenu.xml";

    public SidebarMenu(WebDriver driver) {
        super(driver, PageObjectRepoFileName);
    }

    @Step("Click on Dashboard Button")
    public void clickOnDashboardButton() {
        seleniumUtils.isElementDisplayed("icnDashboard", 30);
        seleniumUtils.click("icnDashboard");
    }

    @Step("Click on Top Up History Button")
    public void clickOnTopUpHistoryButton() {
        seleniumUtils.isElementDisplayed("icnTopUpHistory", 30);
        seleniumUtils.click("icnTopUpHistory");
        seleniumUtils.isElementDisplayed("tblTopupHistory", 30);
        seleniumUtils.waitForThePageLoad();
        seleniumUtils.waitForLoader(implicitWaitSec);
    }

    @Step("Click on Reports Button")
    public void clickOnReportsButton() {
        seleniumUtils.isElementDisplayed("icnReports", 30);
        seleniumUtils.click("icnReports");
        seleniumUtils.waitForThePageLoad();
        seleniumUtils.waitForLoader(implicitWaitSec);
    }

    @Step("Is SMS Reports Display")
    public boolean isSmsReportsDisplay() {
        try {
            return (seleniumUtils.isElementDisplayed("icnSmsReports", 30));
        } catch (Exception ex) {
            return false;
        }
    }

    @Step("Click on SMS Reports Button")
    public void clickOnSmsReportsButton() {
        seleniumUtils.isElementDisplayed("icnSmsReports", 30);
        seleniumUtils.click("icnSmsReports");
        seleniumUtils.waitForThePageLoad();
        seleniumUtils.waitForLoader(implicitWaitSec);
    }

}
