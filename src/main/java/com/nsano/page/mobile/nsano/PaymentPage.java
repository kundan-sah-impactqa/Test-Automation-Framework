package com.nsano.page.mobile.nsano;

import com.nsano.page.mobile.BasePage;
import com.nsano.utilities.PageObjectRepoHelper;
import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Step;

public class PaymentPage extends BasePage {
	private static final String PageObjectRepoFileName = "PaymentPage.xml";

	/**
	 * Constructor for AssetsPage.
	 *
	 * @param driver   a {@link AppiumDriver} object
	 * @param platform a {@link PageObjectRepoHelper.PLATFORM} object
	 */
	public PaymentPage(AppiumDriver driver, PageObjectRepoHelper.PLATFORM platform) {
		super(driver, PageObjectRepoFileName, platform);
	}

	/**
	 * Click on link : Credit / Debit Cards
	 */
	@Step("Click on Credit Debit Cards")
	public void clickOnCreditDebitCards() {
		appiumUtils.isElementDisplayed("lnkCreditDebitCards", 30);
		appiumUtils.waitForElementToBeClickable("lnkCreditDebitCards");
		appiumUtils.click("lnkCreditDebitCards");
	}

	@Step("Is Add Card Page Displayed")
	public boolean isAddCardPageDisplayed() {
		try {
			return appiumUtils.isElementDisplayed("txtCardNumber", 30);
		} catch (Exception ex) {
			return false;
		}
	}

	/**
	 * Enter : Card Details
	 */
	@Step("Enter Card Details {2}")
	public void enterCardDetails(String cardNumber, String expiry, String cvv) {
		appiumUtils.isElementDisplayed("txtCardNumber", 30);
		appiumUtils.enterText("txtCardNumber", cardNumber);
		appiumUtils.enterText("txtExpiry", expiry);
		appiumUtils.enterText("txtCVV", cvv);
	}

	/**
	 * Click on Button : Proceed to Pay
	 */
	@Step("Click on Proceed to Pay")
	public void clickOnProceedToPay() {
		appiumUtils.waitForElementToBeClickable("btnProceedToPay");
		appiumUtils.click("btnProceedToPay");
	}

	@Step("Is Opt Out For Now Button Displayed")
	public boolean isOptOutForNowButtonDisplayed() {
		try {
			return appiumUtils.isElementDisplayed("btnOptOutForNow", 30);
		} catch (Exception ex) {
			return false;
		}
	}

	/**
	 * Click on Button : Opt Out For Now
	 */
	@Step("Click on Opt Out For Now Button")
	public void clickOnOptOutForNow() {
		appiumUtils.waitForElementToBeClickable("btnOptOutForNow");
		appiumUtils.click("btnOptOutForNow");
	}

}
