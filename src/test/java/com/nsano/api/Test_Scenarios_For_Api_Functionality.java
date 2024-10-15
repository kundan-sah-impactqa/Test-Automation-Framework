package com.nsano.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poiji.bind.Poiji;
import com.nsano.page.api.APIAssertionUtils;
import com.nsano.page.api.nsano.Payloads;
import com.nsano.page.api.nsano.pojos.Reqres;
import com.nsano.listeners.TestAllureListener;
import com.nsano.page.api.nsano.ReqresAPIs;
import com.nsano.utilities.ExcelUtil;
import com.nsano.utilities.FrameworkConfig;
import com.nsano.utilities.SeleniumUtils;
import io.qameta.allure.*;
import io.qameta.allure.model.Status;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.File;
import java.util.*;

/**
 * @author Kundan Kumar Sah
 * @description Validate that user should be able to Log in.
 * Test data should be provided in the data sheet located
 * src/test/resources/TestData/testdata1.xlsx DataID and Browser
 * should be passed from testng_web.xml
 */

@Epic("com/nsano")
@Feature("Test API Execution")
@Listeners(TestAllureListener.class)
public class Test_Scenarios_For_Api_Functionality extends ReqresAPIs {

    private Map<String, String> testDataMap;

    @BeforeMethod
    @Parameters({"dataID"})
    @Description("Read test data with testID {0}")
    public void getTestData(String dataID) {
        ExcelUtil excel = new ExcelUtil();
        excel.setWorkbook(FrameworkConfig.getStringConfigProperty("TestDataFileLocation"), FrameworkConfig.getStringConfigProperty("TestDataSheetNameWeb"));
        testDataMap = excel.getRowDataMatchingDataId(dataID);
        System.out.println(testDataMap);
        if (testDataMap.size() < 1)
            Assert.fail("dataID '" + dataID + "' is valid the excel sheet. please check the test data sheet");
    }

    @Test(priority = 1, description = "TC001_Verify that user is able to Create an User")
    @Story("POST - Create User")
    @Description("Verify that user is able to Create an User")
    public void verify_createUser0() throws JsonProcessingException {
//        Map<String, Object> payload = Payloads.getCreateUserPayloadFromMap("Kundan", "QA", "101");
//        Map<String, Object> payload = Payloads.getCreateUserPayloadFromMap();
        Reqres payload = Payloads.getCreateUserPayloadFromPojo();
//        Reqres payload = new Reqres();
//        Reqres payload = new Reqres().toBuilder().build();
//        System.out.println(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(payload));

        Response response = createUser(payload);

        if (response.getStatusCode() == 201) {
            Allure.step("User created successfully and status code is 201", Status.PASSED);
        } else {
            Allure.step("User not created successfully and status code is not 201", Status.FAILED);
            Assert.fail("Fail");
        }

    }

    @Test(priority = 1, description = "TC001_Verify that user is able to Create an User")
    @Story("POST - Create User")
    @Description("Verify that user is able to Create an User")
    public void verify_createUser1() throws JsonProcessingException {
//        Map<String, Object> payload = Payloads.getCreateUserPayloadFromMap("Kundan", "QA", "101");
//        Map<String, Object> payload = Payloads.getCreateUserPayloadFromMap();
//        Reqres payload = Payloads.getCreateUserPayloadFromPojo();
//        Reqres payload = new Reqres();
//        Reqres payload = new Reqres().toBuilder().build();
//        System.out.println(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(payload));

        Reqres payload = new Reqres().toBuilder().name("Kundan").build();
        Response response = createUser(payload);

        if (response.getStatusCode() == 201) {
            Allure.step("User created successfully and status code is 201", Status.PASSED);
        } else {
            Allure.step("User not created successfully and status code is not 201", Status.FAILED);
            Assert.fail("Fail");
        }

    }

    @Test(priority = 1, description = "TC001_Verify that user is able to Create an User")
    @Story("POST - Create User")
    @Description("Verify that user is able to Create an User")
    public void verify_createUserAndVerifyResponse2() throws JsonProcessingException {
//        Reqres payload = new Reqres().toBuilder().name("Kundan").build();
        Reqres payload = new Reqres();
        Response response = createUser(payload);
//        Assert.assertEquals(response.jsonPath().getString("name"),payload.getName());

        ObjectMapper objectMapper = new ObjectMapper();
        Reqres createUserResponse = objectMapper.readValue(response.getBody().asString(), Reqres.class);
        Assert.assertEquals(createUserResponse, payload);
        System.out.println(">>>>>>>>>> "+createUserResponse.getId());

        if (response.getStatusCode() == 201) {
            Allure.step("User created successfully and status code is 201", Status.PASSED);
        } else {
            Allure.step("User not created successfully and status code is not 201", Status.FAILED);
            Assert.fail("Fail");
        }

    }

    @Test(priority = 1, description = "TC001_Verify that user is able to Create an User")
    @Story("POST - Create User")
    @Description("Verify that user is able to Create an User")
    public void verify_createUserAndVerifyResponse3() {
        Reqres payload = Payloads.getCreateUserPayloadFromPojo();
        Response response = createUser(payload);

        Map<String, Object> expectedValueMap = new HashMap<>();
        expectedValueMap.put("name", payload.getName());
        expectedValueMap.put("job", payload.getJob());
        expectedValueMap.put("id", payload.getId() + "1");

        if (APIAssertionUtils.assertExpectedValuesWithJsonPath(response, expectedValueMap)) {
            Allure.step("User created successfully and status code is 201", Status.PASSED);
        } else {
            Allure.step("User not created successfully and status code is not 201", Status.FAILED);
            Assert.fail("Fail");
        }
    }

    @Test(dataProvider = "getCreateUserData", priority = 1, description = "TC001_Verify that user is able to Create an User")
    @Story("POST - Create User")
    @Description("Verify that user is able to Create an User")
    public void verify_createUserAndVerifyResponse4(Reqres payload) {
//        Reqres payload = Payloads.getCreateUserPayloadFromPojo();
        Response response = createUser(payload);

        Map<String, Object> expectedValueMap = new HashMap<>();
        expectedValueMap.put("name", payload.getName());
        expectedValueMap.put("job", payload.getJob());
        expectedValueMap.put("id", payload.getId());

        if (APIAssertionUtils.assertExpectedValuesWithJsonPath(response, expectedValueMap)) {
            Allure.step("User created successfully and status code is 201", Status.PASSED);
        } else {
            Allure.step("User not created successfully and status code is not 201", Status.FAILED);
            Assert.fail("Fail");
        }
    }

    @DataProvider(name = "getCreateUserData")
    public Iterator<Object[]> getCreateUserData() {
        List<LinkedHashMap<String, String>> excelDataAsListOfMap = ExcelUtil.getExcelDataAsListOfMap("testdata-api", "apiData1");
        List<Object[]> reqresData = new ArrayList<>();

        for (LinkedHashMap<String, String> data : excelDataAsListOfMap) {
            Reqres payload = Reqres.builder()
                    .name(data.get("Name"))
                    .job(data.get("Job"))
                    .id(data.get("Id"))
                    .build();
            reqresData.add(new Object[]{payload});
        }
        return reqresData.iterator();
    }

    @Test(dataProvider = "getCreateUserData", priority = 1, description = "TC001_Verify that user is able to Create an User")
    @Story("POST - Create User")
    @Description("Verify that user is able to Create an User")
    public void verify_createUserAndVerifyResponse5(Reqres payload) {
//        Reqres payload = Payloads.getCreateUserPayloadFromPojo();
        Response response = createUser(payload);

        Map<String, Object> expectedValueMap = new HashMap<>();
        expectedValueMap.put("name", payload.getName());
        expectedValueMap.put("job", payload.getJob());
        expectedValueMap.put("id", payload.getId());

        if (APIAssertionUtils.assertExpectedValuesWithJsonPath(response, expectedValueMap)) {
            Allure.step("User created successfully and status code is 201", Status.PASSED);
        } else {
            Allure.step("User not created successfully and status code is not 201", Status.FAILED);
            Assert.fail("Fail");
        }
    }


    @DataProvider(name = "getCreateUserDataPoiji")
    public Iterator<Object[]> getCreateUserDataPoiji() {
        List<Reqres> reqresData = Poiji.fromExcel(new File("src/test/resources/TestData/testdata-api.xlsx"), Reqres.class);
        List<Object[]> data = new ArrayList<>();
        for (Reqres reqres : reqresData) {
            data.add(new Object[]{reqres});
        }
        return data.iterator();
    }

    @Test(dataProvider = "getCreateUserDataPoiji", priority = 1, description = "TC001_Verify that user is able to Create an User")
    @Story("POST - Create User")
    @Description("Verify that user is able to Create an User")
    public void verify_createUserAndVerifyResponsePoiji1(Reqres payload) {

        String cellValue = payload.getIdValue();
        if (cellValue.contains("RandomNumber")) {
            int size = 6;
            // With Size
            if (cellValue.contains("_")) {
                size = Integer.parseInt((cellValue.split("_"))[1]);
            }
            SeleniumUtils seleniumUtils = new SeleniumUtils();
            cellValue = seleniumUtils.randomNumeric(size);
        }
        payload.setId(cellValue);

        Response response = createUser(payload);

        Map<String, Object> expectedValueMap = new HashMap<>();
        expectedValueMap.put("name", payload.getName());
        expectedValueMap.put("job", payload.getJob());
        expectedValueMap.put("id", payload.getId());

        if (APIAssertionUtils.assertExpectedValuesWithJsonPath(response, expectedValueMap)) {
            Allure.step("User created successfully and status code is 201", Status.PASSED);
        } else {
            Allure.step("User not created successfully and status code is not 201", Status.FAILED);
            Assert.fail("Fail");
        }
    }

    // =================GET=================================//

    @Test(priority = 1, description = "TC002_Verify existing user - get method")
    @Story("GET - Existing User")
    @Description("Verify existing user - get method")
    public void verify_getMethod_CreatedUserResponse() throws JsonProcessingException {
//        Reqres payload = Payloads.getCreateUserPayloadFromPojo();
        Response response = getUser();

        Map<String, Object> expectedValueMap = new HashMap<>();
        expectedValueMap.put("data.id", 2);
        expectedValueMap.put("data.first_name", "Janet");
        expectedValueMap.put("data.last_name", "Weaver");

//        String id = response.jsonPath().getString("data.id");
//        String name = response.jsonPath().getString("data.first_name");
//        String job = response.jsonPath().getString("data.last_name");
//        System.out.println(">>>>>>>>" + id + " " + name + " " + job);


        if (APIAssertionUtils.assertExpectedValuesWithJsonPath(response, expectedValueMap)) {
            Allure.step("User created successfully and status code is 200", Status.PASSED);
        } else {
            Allure.step("User not created successfully and status code is not 200", Status.FAILED);
            Assert.fail("Fail");
        }
    }
}