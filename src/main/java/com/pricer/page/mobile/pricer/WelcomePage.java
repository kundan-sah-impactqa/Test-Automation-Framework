package com.pricer.page.mobile.pricer;

import com.pricer.page.mobile.BasePage;
import com.pricer.utilities.PageObjectRepoHelper;
import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Set;

public class WelcomePage extends BasePage {
	private static final String PageObjectRepoFileName = "WelcomePage.xml";

	/**
	 * Constructor for AssetsPage.
	 *
	 * @param driver   a {@link AppiumDriver} object
	 * @param platform a {@link PageObjectRepoHelper.PLATFORM} object
	 */
	public WelcomePage(AppiumDriver driver, PageObjectRepoHelper.PLATFORM platform) {
		super(driver, PageObjectRepoFileName, platform);
	}

	/**
	 * Click on button : Continue
	 */
	@Step("Click on continue button")
	public void clickOnContinueButton() {
		appiumUtils.waitForElementToBeClickable("btnContinue");
		appiumUtils.click("btnContinue");
		appiumUtils.sleepForMiliseconds(3000);
		// Get all available contexts
		Set<String> contexts = appiumUtils.getDriver().getContextHandles();
		System.out.println("Available contexts: " + contexts);
		// Switch to WebView context
		for (String context : contexts) {
			if (context.contains("WEBVIEW_chrome")) {
				appiumUtils.getDriver().context(context);
				System.out.println("Switched to WEBVIEW_chrome");
				break;
			}
		}
	}

}
