package com.starbucks.page.api.starbucks.pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class CreateReqres extends BasePojo{

    private String name;
    private String job ;
    private String id;

}
