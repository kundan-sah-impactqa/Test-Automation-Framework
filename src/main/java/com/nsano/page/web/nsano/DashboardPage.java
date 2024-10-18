package com.nsano.page.web.nsano;

import com.nsano.page.web.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;


/**
 * @author Kundan Kumar Sah
 * @description Implemented logic to handle validations
 */
public class DashboardPage extends BasePage {
    private static final String PageObjectRepoFileName = "DashboardPage.xml";

    public DashboardPage(WebDriver driver) {
        super(driver, PageObjectRepoFileName);
    }

    @Step("Is Dashboard Page Display")
    public boolean isDashboardPageDisplay() {
        try {
            return (seleniumUtils.isElementDisplayed("lnkDashboard", 90));
        } catch (Exception ex) {
            return false;
        }
    }

    @Step("Click on Filter at Top")
    public void clickOnFilterIcon() {
        seleniumUtils.isElementDisplayed("lnkFilter", 30);
        seleniumUtils.click("lnkFilter");
    }

    @Step("Click on Date Sent")
    public void clickOnDateSent() {
        seleniumUtils.isElementDisplayed("lnkDateSent", 30);
        seleniumUtils.click("lnkDateSent");
    }

    @Step("Select Date Sent {0}")
    public void selectDateSent(String type) {
        seleniumUtils.sleep(3000);
        WebElement e1 = seleniumUtils.getDriver().findElement(By.xpath("(//div[@class='ranges'])[1]//li[contains(text(),'" + type + "')]"));
        e1.click();
    }

    @Step("Select Account {0}")
    public void selectAccount(String account) {
        seleniumUtils.isElementDisplayed("lstAccount", 30);
        seleniumUtils.click("lstAccount");
        seleniumUtils.selectDropdown("lstAccount", account);
    }

    @Step("Click on Filter Button")
    public void clickOnFilterButton() {
        seleniumUtils.isElementDisplayed("btnFilter", 30);
        seleniumUtils.click("btnFilter");
        seleniumUtils.waitForElementToBeNotDisplayed("lnkDateSent");
    }

    @Step("Is Filter Value Display: {0}")
    public boolean isFilteredDataDisplay(String totalCount) {
        try {
            return (seleniumUtils.getText("totalCount").trim().equalsIgnoreCase(totalCount));
        } catch (Exception ex) {
            return false;
        }
    }

    @Step("Select From and To date {3}")
    public void selectFromAndToDate(String fromMonth, String fromDay, String toMonth, String toDay) {
        seleniumUtils.sleep(2000);
        WebElement selectFromDate = seleniumUtils.getDriver().findElement(By.xpath("//th[text()='" + fromMonth + " 2024']/ancestor::table//td[text()='" + fromDay + "']"));
        selectFromDate.click();
        seleniumUtils.sleep(2000);
        WebElement selectToDate = seleniumUtils.getDriver().findElement(By.xpath("//th[text()='" + toMonth + " 2024']/ancestor::table//td[text()='" + toDay + "']"));
        selectToDate.click();
        seleniumUtils.click("btnApply");
    }

    @Step("Is Account Balance Display")
    public boolean isAccountBalanceDisplay() {
        try {
            return (seleniumUtils.isElementDisplayed("lblBalanceSms", 30));
        } catch (Exception ex) {
            return false;
        }
    }

    @Step("Is Sms Maximum Limit Message Display")
    public boolean isSmsMaxLimitMessageDisplay(String expectedMessage) {
        seleniumUtils.mouseHover("lblBalanceSms");
        try {
            return (seleniumUtils.getAttribute("lblBalanceSms", "data-original-title").equalsIgnoreCase(expectedMessage));
        } catch (Exception ex) {
            return false;
        }
    }

    @Step("get total count using API: {0}")
    public String getTotalCount() {
        // Step 1: Login to authenticate and fetch data from the dashboard API at once
        String baseUri = "https://smsdashboard.nsano.com";
        String loginPayload = "{\"username\":\"ysharma\",\"password\":\"Impactqa@2024\"}";
        String dashboardPayload = "{\"start_date\":\"2024-09-16\",\"end_date\":\"2024-10-15\",\"account\":\"\"}";

        // Execute login request
        Response loginResponse = RestAssured
                .given()
                .baseUri(baseUri)
                .basePath("/login")
                .contentType(ContentType.JSON)
                .body(loginPayload)
                .post();

        // If login fails, return error message
        if (loginResponse.getStatusCode() != 200) {
            System.out.println("Login failed. Status Code: " + loginResponse.getStatusCode());
            return "Login failed"; // Indicate failure
        }

        // Step 2: Fetch data from the dashboard API, using cookies from the login response
        Response dashboardResponse = RestAssured
                .given()
                .baseUri(baseUri)
                .basePath("/filter_sms_graph_metrics")
                .contentType(ContentType.JSON)
                .cookies(loginResponse.getCookies()) // Use cookies from the login response
                .body(dashboardPayload)
                .redirects().follow(true) // Allow redirects
                .post();

        // If the dashboard request fails, return error message
        if (dashboardResponse.getStatusCode() != 200) {
            System.out.println("Dashboard request failed. Status Code: " + dashboardResponse.getStatusCode());
            return "Dashboard request failed"; // Indicate failure
        }

        // Step 3: Parse the dashboard response to calculate the total count
        String responseBody = dashboardResponse.getBody().asString();
        JSONObject jsonResponse = new JSONObject(responseBody);
        JSONArray dataArray = jsonResponse.getJSONArray("data");

        int totalCount = 0;
        for (int i = 0; i < dataArray.length(); i++) {
            JSONObject dataObject = dataArray.getJSONObject(i);
            totalCount += dataObject.getInt("count");
        }

        // Output the total count
        String totalCountString = String.valueOf(totalCount);
        System.out.println("Total Count: " + totalCountString);
        return totalCountString;
    }

}
