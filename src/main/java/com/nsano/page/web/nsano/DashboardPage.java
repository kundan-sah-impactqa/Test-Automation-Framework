package com.nsano.page.web.nsano;

import com.nsano.page.web.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;


/**
 * @author Kundan Kumar Sah
 * @description Implemented logic to handle validations
 */
public class DashboardPage extends BasePage {
    private static final String PageObjectRepoFileName = "DashboardPage.xml";

    public DashboardPage(WebDriver driver) {
        super(driver, PageObjectRepoFileName);
    }

    @Step("Is Log In Page Display")
    public boolean isDashboardPageDisplay() {
        try {
            return (seleniumUtils.isElementDisplayed("lnkDashboard", 90));
        } catch (Exception ex) {
            return false;
        }
    }

}
