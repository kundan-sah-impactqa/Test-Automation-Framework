package com.starbucks.page.api.starbucks.pojos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.poiji.annotation.ExcelCellName;
import com.starbucks.utilities.RandomDataGenerator;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder(toBuilder = true)
public class Reqres {

//    public enum Gender{
//        male,
//        female,
//        others
//    }
//    private String gender = Stream.of("male", "female").findAny().get();
//    private Gender gender = Arrays.stream(Gender.values()).findAny().get();
    @ExcelCellName("Name")
    private String name = RandomDataGenerator.getRandomDataFor(RandomDataGenerator.RandomDataTypeNames.FULL_NAME);

    @ExcelCellName("Job")
    private String job = RandomDataGenerator.getRandomDataFor(RandomDataGenerator.RandomDataTypeNames.JOB_TITLE);

    private String id = RandomDataGenerator.getRandomDataFor(RandomDataGenerator.RandomDataTypeNames.ID_NUMBER);

    @ExcelCellName("Id")
    @JsonIgnore
    private String idValue;

}
