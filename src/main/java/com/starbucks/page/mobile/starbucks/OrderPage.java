package com.starbucks.page.mobile.starbucks;

import com.starbucks.page.mobile.BasePage;
import com.starbucks.utilities.PageObjectRepoHelper;
import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Step;

public class OrderPage extends BasePage {
	private static final String PageObjectRepoFileName = "OrderPage.xml";

	/**
	 * Constructor for AssetsPage.
	 *
	 * @param driver   a {@link AppiumDriver} object
	 * @param platform a {@link PageObjectRepoHelper.PLATFORM} object
	 */
	public OrderPage(AppiumDriver driver, PageObjectRepoHelper.PLATFORM platform) {
		super(driver, PageObjectRepoFileName, platform);
	}

	@Step("Is Order Page Displayed")
	public boolean isOrderPageDisplayed() {
		try {
			return appiumUtils.isElementDisplayed("icnSearch", 30);
		} catch (Exception ex) {
			return false;
		}
	}

	/**
	 * Click on icon : Search
	 */
	@Step("Click on Search icon")
	public void clickOnSearchIcon() {
		appiumUtils.waitForElementToBeClickable("icnSearch");
		appiumUtils.click("icnSearch");
	}

	/**
	 * Enter : Item name
	 */
	@Step("Enter item name {0}")
	public void enterItemName(String item) {
		appiumUtils.isElementDisplayed("txtSearchItem", 30);
		appiumUtils.enterText("txtSearchItem", item);
	}

	/**
	 * Click on button : Add Item
	 */
	@Step("Click on Add Item button")
	public void clickOnAddItemButton() {
		appiumUtils.waitForElementToBeClickable("btnAddItem");
		appiumUtils.click("btnAddItem");
	}

	/**
	 * Click on button : Add To Cart
	 */
	@Step("Click on Add To Cart button")
	public void clickOnAddToCartButton() {
		appiumUtils.waitForElementToBeClickable("btnAddToCart");
		appiumUtils.click("btnAddToCart");
	}

	/**
	 * Click on button : View Cart
	 */
	@Step("Click on View Cart button")
	public void clickOnViewCartButton() {
		appiumUtils.isElementDisplayed("btnViewCart", 30);
		appiumUtils.waitForElementToBeClickable("btnViewCart");
		appiumUtils.click("btnViewCart");
	}

}
