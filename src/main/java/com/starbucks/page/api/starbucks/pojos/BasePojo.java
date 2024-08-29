package com.starbucks.page.api.starbucks.pojos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BasePojo {

    @JsonIgnore
    private String scenarioId;

    @JsonIgnore
    private String scenarioDesc;

    @JsonIgnore
    private String expectedStatusCode;
    @JsonIgnore
    private String expectedErrorMessage;
}
