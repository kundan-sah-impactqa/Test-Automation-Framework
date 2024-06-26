package com.pricer.page.mobile.pricer;

import com.pricer.page.mobile.BasePage;
import com.pricer.utilities.PageObjectRepoHelper;
import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Step;

public class LoginPage extends BasePage {
	private static final String PageObjectRepoFileName = "LoginPage.xml";

	/**
	 * Constructor for AssetsPage.
	 *
	 * @param driver   a {@link AppiumDriver} object
	 * @param platform a {@link PageObjectRepoHelper.PLATFORM} object
	 */
	public LoginPage(AppiumDriver driver, PageObjectRepoHelper.PLATFORM platform) {
		super(driver, PageObjectRepoFileName, platform);
	}

	/**
	 * Click on button : Continue
	 */
	@Step("Click on continue button")
	public void clickOnContinueButton() {
		appiumUtils.waitForElementToBeClickable("btnContinue");
		appiumUtils.click("btnContinue");
	}
}
