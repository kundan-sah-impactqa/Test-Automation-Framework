package com.vitamojo.utilities;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Base64;

public class JiraIntegration {

    private static final String JIRA_URL = FrameworkConfig.getStringConfigProperty("JIRA_URL");
    private static final String JIRA_USERNAME = FrameworkConfig.getStringConfigProperty("JIRA_USERNAME");
    private static final String JIRA_API_TOKEN = FrameworkConfig.getStringConfigProperty("JIRA_API_TOKEN");
    private static String getAuthHeader() {
        String auth = Base64.getEncoder().encodeToString((JIRA_USERNAME + ":" + JIRA_API_TOKEN).getBytes());
        return "Basic " + auth;
    }

    public static boolean issueExists(String projectKey, String summary) {
        String jql = String.format("project = %s AND summary ~ \"%s\"", projectKey, summary);
        RequestSpecification request = RestAssured.given()
                .header("Authorization", getAuthHeader())
                .header("Content-Type", "application/json");

        Response response = request.get(JIRA_URL + "/rest/api/2/search?jql=" + jql);
        int issueCount = response.jsonPath().getInt("total");
        return issueCount > 0;
    }

    public static void logBug(String projectKey, String summary, String description) {
        if (issueExists(projectKey, summary)) {
            System.out.println("Bug with summary '" + summary + "' already exists in project " + projectKey);
            return;
        }

        RequestSpecification request = RestAssured.given()
                .header("Authorization", getAuthHeader())
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "  \"fields\": {\n" +
                        "    \"project\": {\n" +
                        "      \"key\": \"" + projectKey + "\"\n" +
                        "    },\n" +
                        "    \"summary\": \"" + summary + "\",\n" +
                        "    \"description\": \"" + description + "\",\n" +
                        "    \"issuetype\": {\n" +
                        "      \"name\": \"Bug\"\n" +
                        "    }\n" +
                        "  }\n" +
                        "}");

        Response response = request.post(JIRA_URL + "/rest/api/2/issue");
        if (response.getStatusCode() == 201) {
            System.out.println("Issue created successfully: " + response.jsonPath().getString("key"));
        } else {
            System.out.println("Failed to create issue: " + response.getBody().asString());
        }
    }
}
