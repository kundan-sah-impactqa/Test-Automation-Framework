package com.starbucks.page.api.starbucks;

import com.starbucks.base.BaseTestAPI;
import com.starbucks.page.api.starbucks.pojos.Reqres;
import com.starbucks.utilities.RestUtils;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

public class ReqresAPIs extends BaseTestAPI {

    public Response createUser(Map<String, Object> createUserPayload) {
        String endpoint = (String) dataFromJsonFile.get("createUserEndPoint");
        return RestUtils.performPost(endpoint, createUserPayload, new HashMap<>());
    }

    public Response createUser(Reqres createUserPayload) {
        String endpoint = (String) dataFromJsonFile.get("createUserEndPoint");
        return RestUtils.performPost(endpoint, createUserPayload, new HashMap<>());
    }

    public Response getUser() {
        String endpoint = (String) dataFromJsonFile.get("getUserEndPoint");
        return RestUtils.performGet(endpoint, new HashMap<>());
    }

}
