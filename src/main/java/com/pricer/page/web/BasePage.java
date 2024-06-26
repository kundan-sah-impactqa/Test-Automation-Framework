package com.pricer.page.web;

import com.pricer.utilities.SeleniumUtils;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

/**
 * @author Kundan Kumar Sah
 * @description All page classes should be extended by this class. It will have handle common elements to all the pages
 */
public class BasePage {

    protected SeleniumUtils seleniumUtils;
    public static final int maxWaitTimeInSec = 180;
    public static final int implicitWaitSec=15;

    public BasePage(WebDriver driver, String pageObjectRepoFileName) {
        this.seleniumUtils = new SeleniumUtils(driver, pageObjectRepoFileName);
    }

    public SeleniumUtils getSeleniumUtils() {
        return seleniumUtils;
    }

    @Attachment(value = "{name}", type = "image/png")
    public static byte[] takeScreenshot(String name, WebDriver driver) {
        // Capture screenshot
        TakesScreenshot ts = (TakesScreenshot) driver;
        return ts.getScreenshotAs(OutputType.BYTES);
    }
}
