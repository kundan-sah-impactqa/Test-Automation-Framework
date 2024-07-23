package com.vitamojo.utilities;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.Base64;

public class JiraClient {
    private String jiraUrl;
    private String jiraUsername;
    private String jiraApiToken;

    public JiraClient(String jiraUrl, String jiraUsername, String jiraApiToken) {
        this.jiraUrl = jiraUrl;
        this.jiraUsername = jiraUsername;
        this.jiraApiToken = jiraApiToken;
    }

    public void createIssue(String summary, String description, String projectKey, String issueType) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost post = new HttpPost(jiraUrl + "/rest/api/2/issue");

            post.setHeader("Content-Type", "application/json");
            post.setHeader("Accept", "application/json");
            post.setHeader("Authorization", "Basic " +
                    Base64.getEncoder().encodeToString((jiraUsername + ":" + jiraApiToken).getBytes()));

            JSONObject issue = new JSONObject();
            JSONObject fields = new JSONObject();
            JSONObject project = new JSONObject();
            JSONObject issueTypeObj = new JSONObject();

            project.put("key", projectKey);
            issueTypeObj.put("name", issueType);

            fields.put("project", project);
            fields.put("summary", summary);
            fields.put("description", description);
            fields.put("issuetype", issueTypeObj);

            issue.put("fields", fields);

            StringEntity entity = new StringEntity(issue.toString());
            post.setEntity(entity);

            HttpResponse response = client.execute(post);
            String responseString = EntityUtils.toString(response.getEntity());

            if (response.getStatusLine().getStatusCode() == 201) {
                System.out.println("Issue created successfully: " + responseString);
            } else {
                System.out.println("Failed to create issue: " + responseString);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
