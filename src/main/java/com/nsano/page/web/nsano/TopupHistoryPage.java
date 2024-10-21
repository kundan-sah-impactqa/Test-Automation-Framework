package com.nsano.page.web.nsano;

import com.nsano.page.web.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


/**
 * @author Kundan Kumar Sah
 * @description Implemented logic to handle validations
 */
public class TopupHistoryPage extends BasePage {
    private static final String PageObjectRepoFileName = "TopupHistoryPage.xml";

    public TopupHistoryPage(WebDriver driver) {
        super(driver, PageObjectRepoFileName);
    }

    @Step("Verify list of top-ups count per page display as {0}")
    public boolean verifyListOfTopupsCountPerPage(int expectedCount) {
        seleniumUtils.sleep(6000);
        try {
            if (seleniumUtils.isElementDisplayed("lblTopupCount", 10)) {
                String topupValue = seleniumUtils.getText("lblTopupCount");
                // Extract the total number of items using a regular expression
                Matcher matcher = Pattern.compile("of (\\d+)").matcher(topupValue);
                int totalCount = matcher.find() ? Integer.parseInt(matcher.group(1)) : 0;
                return (totalCount == expectedCount);
            } else {
                String topupValue = seleniumUtils.getText("lblFilteredTopupCount");
                // Extract the total number of items using a regular expression
                Matcher matcher = Pattern.compile("of (\\d+)").matcher(topupValue);
                int totalCount = matcher.find() ? Integer.parseInt(matcher.group(1)) : 0;
                return (totalCount == expectedCount);
            }

        } catch (Exception ex) {
            return false;
        }
    }

    @Step("Click on Filter at Top")
    public void clickOnFilterIcon() {
        seleniumUtils.isElementDisplayed("lnkFilter", 30);
        seleniumUtils.click("lnkFilter");
        seleniumUtils.waitForThePageLoad();
        seleniumUtils.waitForLoader(implicitWaitSec);
    }

    @Step("Click on Date")
    public void clickOnDate() {
        seleniumUtils.isElementDisplayed("lnkDate", 30);
        seleniumUtils.click("lnkDate");
    }

    @Step("Select Date {0}")
    public void selectDate(String type) {
        seleniumUtils.sleep(3000);
        WebElement e1 = seleniumUtils.getDriver().findElement(By.xpath("(//div[@class='ranges'])[1]//li[contains(text(),'" + type + "')]"));
        e1.click();
        seleniumUtils.sleep(3000);
    }

    @Step("Select From or To date {0}-{1}-{2}")
    public void selectDate(String day, String month, String year) {
        seleniumUtils.sleep(5000);
        int clickCount = 0;
        int maxClicks = 75;
        try {
            // Loop until the specified date element is displayed or the max clicks is reached
            while (clickCount < maxClicks) {
                WebElement expectedDate = seleniumUtils.getDriver().findElement(By.xpath("(//th[text()='" + month + " " + year + "']/ancestor::table//td[text()='" + day + "'])[1]"));
                if (expectedDate.isEnabled() && expectedDate.isDisplayed()) {
                    // Click the date once it is displayed
                    expectedDate.click();
                    break;
                } else {
                    // Click the previous button
                    seleniumUtils.sleep(500);
                    System.out.println("Attempting to click the previous button...");
                    seleniumUtils.waitForElementToBeClickable("btnPrevious");
                    seleniumUtils.javaScriptClick("btnPrevious");
                    clickCount++;
                }
            }
            // Check if max clicks were reached without finding the date
            if (clickCount == maxClicks) {
                System.out.println("The maximum number of attempts (75) was reached without finding the specified date: " + day + "-" + month + "-" + year);
            }
        } catch (NoSuchElementException e) {
            System.out.println("The specified date element was not found: " + day + "-" + month + "-" + year);
        }
    }

    @Step("Click on Apply Button")
    public void clickOnApplyButton() {
        seleniumUtils.isElementDisplayed("btnApply", 30);
        seleniumUtils.click("btnApply");
        seleniumUtils.waitForThePageLoad();
        seleniumUtils.waitForLoader(implicitWaitSec);
    }

    @Step("Click on Filter Button")
    public void clickOnFilterButton() {
        seleniumUtils.isElementDisplayed("btnFilter", 30);
        seleniumUtils.click("btnFilter");
        seleniumUtils.waitForThePageLoad();
        seleniumUtils.waitForLoader(implicitWaitSec);
    }

    @Step("Click on Reset Button")
    public void clickOnResetButton() {
        seleniumUtils.isElementDisplayed("btnReset", 30);
        seleniumUtils.click("btnReset");
        seleniumUtils.waitForThePageLoad();
        seleniumUtils.waitForLoader(implicitWaitSec);

    }

    @Step("Click on Export Button")
    public void clickOnExportButton() {
        seleniumUtils.isElementDisplayed("btnExport", 30);
        seleniumUtils.click("btnExport");
        seleniumUtils.sleep(4000);
        seleniumUtils.waitForThePageLoad();
        seleniumUtils.waitForLoader(implicitWaitSec);
    }

    @Step("Get TopUp Count From Exported CSV File")
    public int getTopupCountFromExportedCsv(String fileName, String accountName) {
        String filePath = System.getProperty("user.dir") + "\\target\\Downloads\\" + fileName;
        int topupCount = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isHeader = true;
            // Read each line from the CSV file
            while ((line = br.readLine()) != null) {
                // Skip the header row
                if (isHeader) {
                    isHeader = false;
                    continue;
                }
                // Split the line by commas to get each column value
                String[] columns = line.split(",");
                // Ensure there are enough columns to avoid ArrayIndexOutOfBoundsException
                if (columns.length < 3) {
                    continue;
                }
                // Check if the account name matches (case-insensitive comparison)
                if (columns[0].equalsIgnoreCase(accountName)) {
                    try {
                        // Parse the "Topup" value from the third column
                        double topupValue = Double.parseDouble(columns[2]);
                        topupCount += topupValue;
                    } catch (NumberFormatException e) {
                        // Handle cases where the topup value is not a valid number
                    }
                }
            }
        } catch (IOException e) {
            // Handle file I/O exceptions
        }
        return topupCount;
    }

}
