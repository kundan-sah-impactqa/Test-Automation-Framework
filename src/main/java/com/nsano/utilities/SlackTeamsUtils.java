package com.nsano.utilities;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class SlackTeamsUtils {

    private static final String SLACK_WEBHOOK_URL;

    static {
        try {
            SLACK_WEBHOOK_URL = CryptoUtils.decryptTheValue(FrameworkConfig.getStringConfigProperty("SLACK_WEBHOOK_URL"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void sendSlackMessage(String message) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpPost httpPost = new HttpPost(SLACK_WEBHOOK_URL);
            String jsonPayload = "{\"text\": \"" + message + "\"}";

            StringEntity entity = new StringEntity(jsonPayload);
            httpPost.setEntity(entity);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            CloseableHttpResponse response = httpClient.execute(httpPost);
            response.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static final String TEAMS_WEBHOOK_URL;

    static {
        try {
            TEAMS_WEBHOOK_URL = CryptoUtils.decryptTheValue(FrameworkConfig.getStringConfigProperty("TEAMS_WEBHOOK_URL"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void sendTeamsMessage(String message) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpPost httpPost = new HttpPost(TEAMS_WEBHOOK_URL);
            String jsonPayload = "{\"text\": \"" + message + "\"}";

            StringEntity entity = new StringEntity(jsonPayload);
            httpPost.setEntity(entity);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            CloseableHttpResponse response = httpClient.execute(httpPost);
            response.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
