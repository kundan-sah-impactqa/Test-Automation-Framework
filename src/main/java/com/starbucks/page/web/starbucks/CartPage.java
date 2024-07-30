package com.starbucks.page.web.starbucks;

import com.starbucks.page.web.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Map;


/**
 * @author Kundan Kumar Sah
 * @description Implemented logic to handle validations
 */
public class CartPage extends BasePage {
    private static final String PageObjectRepoFileName = "CartPage.xml";

    public CartPage(WebDriver driver) {
        super(driver, PageObjectRepoFileName);
    }

    @Step("Get Page Title")
    public String getPageTitle() {
        seleniumUtils.waitForPageToLoad();
        seleniumUtils.waitForLoader(implicitWaitSec);
        return seleniumUtils.getDriver().getTitle();
    }

    @Step("Click on Plus icon")
    public void clickOnPlusIcon() {
        seleniumUtils.isElementDisplayed("icnPlus", 20);
        seleniumUtils.click("icnPlus");
    }

    @Step("Click on Minus icon")
    public void clickOnMinusIcon() {
        seleniumUtils.isElementDisplayed("icnMinus", 20);
        seleniumUtils.click("icnMinus");
        seleniumUtils.sleep(4000);
    }

    @Step("Click on Repeat Last Button")
    public void clickOnRepeatLastButton() {
        seleniumUtils.isElementDisplayed("btnRepeatLast", 20);
        seleniumUtils.click("btnRepeatLast");
        seleniumUtils.sleep(6000);
    }

    @Step("Is Cart Value Increases")
    public boolean isCartValueIncreases(String cartValue) {
        try {
            WebElement e = seleniumUtils.getDriver().findElement(By.xpath("//div[@class='wrap']//span[text()='" + cartValue + "']"));
            return e.isDisplayed();
        } catch (Exception ex) {
            return false;
        }
    }

    @Step("Click on Other Payment Methods")
    public void clickOnOtherPaymentMethods() {
        seleniumUtils.isElementDisplayed("lnkOtherPaymentMethods", 20);
        seleniumUtils.click("lnkOtherPaymentMethods");
    }

    @Step("Is Cards Displayed")
    public boolean isCardsDisplayed() {
        try {
            return seleniumUtils.isElementDisplayed("rdbCardAroma", 20);
        } catch (Exception ex) {
            return false;
        }
    }

    @Step("Select Card For Payment")
    public void selectCardForPayment() {
        seleniumUtils.isElementDisplayed("rdbCardAroma", 20);
        seleniumUtils.javaScriptClick("rdbCardAroma");
        if (seleniumUtils.isElementDisplayed("rdbCardIndia", 8)) {
            seleniumUtils.javaScriptClick("rdbCardIndia");
        }
    }

    @Step("Is Payment Method Selected")
    public boolean isPaymentMethodSelected() {
        try {
            return (seleniumUtils.isElementDisplayed("lblOtherPaymentMethods", 20));
        } catch (Exception ex) {
            return false;
        }
    }

    @Step("Click on Place Order Button")
    public void clickOnPlaceOrderButton() {
        seleniumUtils.sleep(5000);
        seleniumUtils.isElementDisplayed("btnPlaceOrder", 20);
        seleniumUtils.waitForElementToBeClickable("btnPlaceOrder");
        seleniumUtils.click("btnPlaceOrder");
    }

    @Step("Is Order Placed Successfully")
    public boolean isOrderPlacedSuccessfully() {
        seleniumUtils.sleep(5000);
        try {
            return (seleniumUtils.isElementDisplayed("lblTrackMyOrder", 40));
        } catch (Exception ex) {
            return false;
        }
    }

}
