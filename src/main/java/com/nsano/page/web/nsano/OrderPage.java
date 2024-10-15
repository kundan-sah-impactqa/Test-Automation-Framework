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
public class OrderPage extends BasePage {
    private static final String PageObjectRepoFileName = "OrderPage.xml";

    public OrderPage(WebDriver driver) {
        super(driver, PageObjectRepoFileName);
    }

    @Step("Get Page Title")
    public String getPageTitle() {
        seleniumUtils.waitForPageToLoad();
        seleniumUtils.waitForLoader(implicitWaitSec);
        return seleniumUtils.getDriver().getTitle();
    }

    @Step("Is Ordering Page Displayed")
    public boolean isOrderingPageDisplayed() {
        try {
            return seleniumUtils.isElementDisplayed("lnkBestSeller", 20);
        } catch (Exception ex) {
            return false;
        }
    }

    @Step("Click on Add Item Button {0}")
    public void clickOnAddItemButton(String itemName) {
        seleniumUtils.sleep(3000);
        seleniumUtils.scrollDown();
        seleniumUtils.sleep(3000);
        WebElement e1 = seleniumUtils.getDriver().findElement(By.xpath("//h3[@class='card-title' and text()='" + itemName +"']/ancestor::div[@class='order-card']//button[contains(text(),'Add Item')]"));
        e1.click();
    }

    @Step("Is Signature Hot Chocolate Page Displayed")
    public boolean isSignatureHotChocolatePageDisplayed() {
        try {
            return seleniumUtils.isElementDisplayed("lblSignatureHotChocolate", 20);
        } catch (Exception ex) {
            return false;
        }
    }

    @Step("Click on Add Item Button")
    public void clickOnAddItemButton() {
        seleniumUtils.isElementDisplayed("btnAddItem", 20);
        seleniumUtils.click("btnAddItem");
    }

    @Step("Is View Cart Button Displayed")
    public boolean isViewCartButtonDisplayed() {
        try {
            return seleniumUtils.isElementDisplayed("btnViewCart", 20);
        } catch (Exception ex) {
            return false;
        }
    }

    @Step("Click on View Cart Button")
    public void clickOnViewCartButton() {
        seleniumUtils.sleep(2000);
        seleniumUtils.isElementDisplayed("btnViewCart", 20);
        seleniumUtils.click("btnViewCart");
        seleniumUtils.sleep(4000);
    }

}
