package com.nsano.utilities;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Allure;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.SpecificationQuerier;

import java.io.File;
import java.util.Map;

public class RestUtils {

    public RestUtils() {
    }

    private static RequestSpecification getRequestSpecification(String endpoint, Object requestPayload, Map<String, String> headers) {
        return RestAssured.given()
                .baseUri(endpoint)
                .headers(headers)
                .contentType(ContentType.JSON)
                .body(requestPayload);
    }

    private static void printRequestLogInReport(RequestSpecification requestSpecification) {
        QueryableRequestSpecification queryableRequestSpecification = SpecificationQuerier.query(requestSpecification);
        Allure.step("Endpoint: " + queryableRequestSpecification.getBaseUri());
        Allure.step("Method: " + queryableRequestSpecification.getMethod());
        Allure.step("Request Headers: " + queryableRequestSpecification.getHeaders().asList().toString());
//        Allure.step("Request Body: " + queryableRequestSpecification.getBody().toString());
        String requestBody = queryableRequestSpecification.getBody().toString();
        requestBody = requestBody.replaceAll(",", ", ");
        requestBody = requestBody.replaceAll(":", ": ");
        Allure.step("Request Body: " + requestBody);
    }

    private static void printResponseLogInReport(Response response) {
        Allure.step("Response Status: " + response.getStatusCode());
        Allure.step("Response Headers: " + response.getHeaders().asList().toString());
        Allure.step("Response Body: " + response.getBody().prettyPrint());
    }

    /**
     * @param endpoint
     * @param requestPayload
     * @param headers
     * @return
     */
    public static Response performPost(String endpoint, String requestPayload, Map<String, String> headers) {
        RequestSpecification requestSpecification = getRequestSpecification(endpoint, requestPayload, headers);
        Response response = requestSpecification.post();
        printRequestLogInReport(requestSpecification);
        printResponseLogInReport(response);
        return response;
//        return RestAssured.given().log().all()
//                .baseUri(endpoint)
//                .headers(headers)
//                .contentType(ContentType.JSON)
//                .body(requestPayload)
//                .post()
//                .then().log().all().extract().response();
    }

    /**
     * @param endpoint
     * @param requestPayload
     * @param headers
     * @return
     */
    public static Response performPost(String endpoint, Map<String, Object> requestPayload, Map<String, String> headers) {
        RequestSpecification requestSpecification = getRequestSpecification(endpoint, requestPayload, headers);
        Response response = requestSpecification.post();
        printRequestLogInReport(requestSpecification);
        printResponseLogInReport(response);
        return response;
    }

    /**
     * @param endpoint
     * @param requestPayloadAsPojo
     * @param headers
     * @return
     */
    public static Response performPost(String endpoint, Object requestPayloadAsPojo, Map<String, String> headers) {
        RequestSpecification requestSpecification = getRequestSpecification(endpoint, requestPayloadAsPojo, headers);
        Response response = requestSpecification.post();
        printRequestLogInReport(requestSpecification);
        printResponseLogInReport(response);
        return response;
    }

    /**
     * @param jsonFileName
     * @return
     */
    public static Map<String, Object> getJsonDataAsMap(String jsonFileName) {
        try {
            String completeJsonFilePath = System.getProperty("user.dir") + "/src/test/resources/TestData/" + jsonFileName;
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> data = objectMapper.readValue(new File(completeJsonFilePath), new TypeReference<>() {
            });
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to read JSON data", e);
        }
    }

    //=====================================================================

    private static RequestSpecification getRequestSpecification(String endpoint, Map<String, String> headers) {
        return RestAssured.given()
                .baseUri(endpoint)
                .headers(headers)
                .contentType(ContentType.JSON);
    }

    private static void printRequestLogReport(RequestSpecification requestSpecification) {
        QueryableRequestSpecification queryableRequestSpecification = SpecificationQuerier.query(requestSpecification);
        Allure.step("Endpoint: " + queryableRequestSpecification.getBaseUri());
        Allure.step("Method: " + queryableRequestSpecification.getMethod());
        Allure.step("Request Headers: " + queryableRequestSpecification.getHeaders().asList().toString());
    }

    public static Response performGet(String endpoint, Map<String, String> headers) {
        RequestSpecification requestSpecification = getRequestSpecification(endpoint, headers);
        printRequestLogReport(requestSpecification);
        Response response = requestSpecification.get();
        printResponseLogInReport(response);
        return response;
    }


}
