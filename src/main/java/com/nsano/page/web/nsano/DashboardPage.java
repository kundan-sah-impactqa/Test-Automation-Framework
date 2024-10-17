package com.nsano.page.web.nsano;

import com.nsano.page.web.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;


/**
 * @author Kundan Kumar Sah
 * @description Implemented logic to handle validations
 */
public class DashboardPage extends BasePage {
    private static final String PageObjectRepoFileName = "DashboardPage.xml";

    public DashboardPage(WebDriver driver) {
        super(driver, PageObjectRepoFileName);
    }

    @Step("Is Dashboard Page Display")
    public boolean isDashboardPageDisplay() {
        try {
            return (seleniumUtils.isElementDisplayed("lnkDashboard", 90));
        } catch (Exception ex) {
            return false;
        }
    }

    @Step("Click on Filter at Top")
    public void clickOnFilterIcon() {
        seleniumUtils.isElementDisplayed("lnkFilter", 30);
        seleniumUtils.click("lnkFilter");
    }

    @Step("Click on Date Sent")
    public void clickOnDateSent() {
        seleniumUtils.isElementDisplayed("lnkDateSent", 30);
        seleniumUtils.click("lnkDateSent");
    }

    @Step("Select Date Sent {0}")
    public void selectDateSent(String type) {
        seleniumUtils.sleep(3000);
        WebElement e1 = seleniumUtils.getDriver().findElement(By.xpath("(//div[@class='ranges'])[1]//li[contains(text(),'" + type + "')]"));
        e1.click();
    }

    @Step("Select Account {0}")
    public void selectAccount(String account) {
        seleniumUtils.isElementDisplayed("lstAccount", 30);
        seleniumUtils.click("lstAccount");
        seleniumUtils.selectDropdown("lstAccount", account);
    }

    @Step("Click on Filter Button")
    public void clickOnFilterButton() {
        seleniumUtils.isElementDisplayed("btnFilter", 30);
        seleniumUtils.click("btnFilter");
        seleniumUtils.waitForElementToBeNotDisplayed("lnkDateSent");
    }

    @Step("Is Filter Value Display {0})")
    public boolean isFilteredDataDisplay(String totalCount) {
        try {
            return (seleniumUtils.getText("totalCount").trim().equalsIgnoreCase(totalCount));
        } catch (Exception ex) {
            return false;
        }
    }

    @Step("Select From and To date {3}")
    public void selectFromAndToDate(String fromMonth, String fromDay, String toMonth, String toDay) {
        seleniumUtils.sleep(2000);
        WebElement selectFromDate = seleniumUtils.getDriver().findElement(By.xpath("//th[text()='" + fromMonth + " 2024']/ancestor::table//td[text()='" + fromDay + "']"));
        selectFromDate.click();
        seleniumUtils.sleep(2000);
        WebElement selectToDate = seleniumUtils.getDriver().findElement(By.xpath("//th[text()='" + toMonth + " 2024']/ancestor::table//td[text()='" + toDay + "']"));
        selectToDate.click();
        seleniumUtils.click("btnApply");
    }

    @Step("Is Account Balance Display")
    public boolean isAccountBalanceDisplay() {
        try {
            return (seleniumUtils.isElementDisplayed("lblBalanceSms", 30));
        } catch (Exception ex) {
            return false;
        }
    }

    @Step("Is Sms Maximum Limit Message Display")
    public boolean isSmsMaxLimitMessageDisplay(String expectedMessage) {
        seleniumUtils.mouseHover("lblBalanceSms");
        try {
            return (seleniumUtils.getAttribute("lblBalanceSms", "data-original-title").equalsIgnoreCase(expectedMessage));
        } catch (Exception ex) {
            return false;
        }
    }

}
