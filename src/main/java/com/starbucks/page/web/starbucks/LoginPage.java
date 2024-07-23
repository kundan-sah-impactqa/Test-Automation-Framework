package com.starbucks.page.web.starbucks;

import com.starbucks.page.web.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;


/**
 * @author Kundan Kumar Sah
 * @description Implemented logic to handle validations
 */
public class LoginPage extends BasePage {
    private static final String PageObjectRepoFileName = "LoginPage.xml";

    public LoginPage(WebDriver driver) {
        super(driver, PageObjectRepoFileName);
    }

    @Step("Enter secure login credentials {1}")
    public void enterSecureLoginCredentials(String username, String password) {
        try {
            Robot robot = new Robot();
            // Type USERNAME
            copyToClipboardAndPaste(robot, username);
            // Press TAB to move to the next field
            robot.keyPress(KeyEvent.VK_TAB);
            robot.keyRelease(KeyEvent.VK_TAB);
            // Type PASSWORD
            copyToClipboardAndPaste(robot, password);
            // Press ENTER to submit
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    private void copyToClipboardAndPaste(Robot robot, String text) {
        // Copy the text to the clipboard
        StringSelection stringSelection = new StringSelection(text);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
        // Simulate pressing Ctrl+V to paste the text
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.delay(500);
    }

    @Step("Enter email address {0}")
    public void enterEmail(String email) {
        seleniumUtils.isElementDisplayed("txtEmail", 30);
        seleniumUtils.enterText("txtEmail", email);
    }

    @Step("Enter password {0}")
    public void enterPassword(String password) {
        seleniumUtils.isElementDisplayed("txtPassword", 30);
        seleniumUtils.enterText("txtPassword", password);
    }

    @Step("Click on Login Button")
    public void clickOnLoginButton() {
        seleniumUtils.click("btnLogin");
        if (seleniumUtils.isElementDisplayed("btnLogOut", 10)) {
            seleniumUtils.click("btnLogOut");
        }
        seleniumUtils.sleep(4000);
    }

}