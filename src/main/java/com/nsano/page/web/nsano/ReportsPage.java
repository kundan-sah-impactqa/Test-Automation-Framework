package com.nsano.page.web.nsano;

import com.nsano.page.web.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author Kundan Kumar Sah
 * @description Implemented logic to handle validations
 */
public class ReportsPage extends BasePage {
    private static final String PageObjectRepoFileName = "ReportsPage.xml";

    public ReportsPage(WebDriver driver) {
        super(driver, PageObjectRepoFileName);
    }

    @Step("Verify list of top-ups count per page display as {0}")
    public boolean verifyListOfMessagesCountPerPage(int expectedCount) {
        String topupValue = seleniumUtils.getText("lblMessagesCount");
        // Extract the total number of items using a regular expression
        Matcher matcher = Pattern.compile("of (\\d+)").matcher(topupValue);
        int totalCount = matcher.find() ? Integer.parseInt(matcher.group(1)) : 0;
        try {
            return (totalCount == expectedCount);
        } catch (Exception ex) {
            return false;
        }
    }

    @Step("Is Messages Display")
    public boolean isMessagesDisplay() {
        try {
            return (seleniumUtils.isElementDisplayed("lnkMessages", 90));
        } catch (Exception ex) {
            return false;
        }
    }

    @Step("Click on message id {0}")
    public void clickOnMessageId(String messageId) {
        seleniumUtils.sleep(6000);
        WebElement message = seleniumUtils.getDriver().findElement(By.xpath("//tbody[@id='reportsTable']//a[text()='" + messageId + "']"));
        message.click();
    }

    @Step("Is Message Content Display")
    public boolean isMessageContentDisplay() {
        try {
            return (seleniumUtils.isElementDisplayed("lblMessageContent", 40));
        } catch (Exception ex) {
            return false;
        }
    }

    @Step("Click on close icon at message details modal")
    public void closeMessageDetailsModal() {
        seleniumUtils.isElementDisplayed("iconCloseMsgDetailsModal", 30);
        seleniumUtils.click("iconCloseMsgDetailsModal");
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

    @Step("Enter bulk id {0}")
    public void enterBulkId(String bulkId) {
        seleniumUtils.isElementDisplayed("txtBulkId", 30);
        seleniumUtils.enterText("txtBulkId", bulkId);
    }

    @Step("Enter recipient {0}")
    public void enterRecipient(String recipient) {
        seleniumUtils.isElementDisplayed("txtRecipient", 30);
        seleniumUtils.enterText("txtRecipient", recipient);
    }

    @Step("Get Filtered Messages")
    public int getFilteredMessages() {
        seleniumUtils.sleep(6000);
        List<WebElement> element = seleniumUtils.getDriver().findElements(By.xpath("//tbody[@id='reportsTable']//a"));
        return element.size();
    }

}
