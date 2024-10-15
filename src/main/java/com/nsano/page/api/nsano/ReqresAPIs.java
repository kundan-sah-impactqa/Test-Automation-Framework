package com.nsano.page.api.nsano;

import com.nsano.base.BaseTestAPI;
import com.nsano.page.api.nsano.pojos.Reqres;
import com.nsano.utilities.RestUtils;
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

    public Response deleteUser() {
        String endpoint = (String) dataFromJsonFile.get("deleteUserEndPoint");
        return RestUtils.performGet(endpoint, new HashMap<>());
    }

}
