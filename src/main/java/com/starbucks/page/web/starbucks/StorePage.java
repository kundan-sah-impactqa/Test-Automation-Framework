package com.starbucks.page.web.starbucks;

import com.starbucks.page.web.BasePage;
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
public class StorePage extends BasePage {
    private static final String PageObjectRepoFileName = "StorePage.xml";

    public StorePage(WebDriver driver) {
        super(driver, PageObjectRepoFileName);
    }

    @Step("Get Page Title")
    public String getPageTitle() {
        seleniumUtils.waitForPageToLoad();
        seleniumUtils.waitForLoader(implicitWaitSec);
        return seleniumUtils.getDriver().getTitle();
    }

    @Step("Enter and Search Store {0}")
    public void enterAndSearchStore(String store) {
        seleniumUtils.waitForPageToLoad();
        seleniumUtils.waitForLoader(implicitWaitSec);
        seleniumUtils.sleep(5000);
        seleniumUtils.isElementDisplayed("txtSearchStore", 30);
        seleniumUtils.enterText("txtSearchStore", store);
        seleniumUtils.sleep(3000);
        seleniumUtils.pressTabKey("txtSearchStore");
        seleniumUtils.sleep(3000);
        WebElement e1 = seleniumUtils.getDriver().findElement(By.xpath("//input[@id='searchbox']"));
        Actions actions = new Actions(seleniumUtils.getDriver());
        actions.moveToElement(e1).click().sendKeys(Keys.DOWN).perform();
        seleniumUtils.sleep(3000);
        WebElement e2 = seleniumUtils.getDriver().findElement(By.xpath("//div[@class='pac-container pac-logo hdpi']/div[@class='pac-item']//span[contains(text(),'Netaji Subhash P')]"));
        actions.moveToElement(e2).click().perform();
        seleniumUtils.sleep(8000);
    }

    @Step("Select Store {0}")
    public void selectStore(String store) {
        seleniumUtils.getDriver().findElement(By.xpath("//h3[normalize-space()='" + store + "']")).click();
    }

    @Step("Is Store Details Page Displayed")
    public boolean isStoreDetailsPageDisplayed () {
        try {
            return seleniumUtils.isElementDisplayed("btnOrderNow", 20);
        } catch (Exception ex) {
            return false;
        }
    }

    @Step("Click on Order Now Button")
    public void clickOnOrderNowButton() {
        seleniumUtils.isElementDisplayed("btnOrderNow", 20);
        seleniumUtils.javaScriptClick("btnOrderNow");
    }

}
