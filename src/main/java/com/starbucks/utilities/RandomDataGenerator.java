package com.starbucks.utilities;

import net.datafaker.Faker;
import org.apache.commons.lang3.RandomStringUtils;

public class RandomDataGenerator {
    public enum RandomDataTypeNames {
        FIRST_NAME,
        LAST_NAME,
        FULL_NAME,
        COUNTRY,
        CITY_NAME,
        EMAIL,
        PHONE_NUMBER,
        JOB_TITLE,
        COMPANY_NAME,
        ID_NUMBER
    }

   public static Faker faker = new Faker();

    public static String getRandomDataFor(RandomDataTypeNames dataTypeNames){
        switch (dataTypeNames){
            case FIRST_NAME:
                return faker.name().firstName();
            case LAST_NAME:
                return faker.name().lastName();
            case FULL_NAME:
                return faker.name().fullName();
            case COUNTRY:
                return faker.address().country();
            case CITY_NAME:
                return faker.address().cityName();
            case EMAIL:
                return RandomStringUtils.randomAlphabetic(6) + "@yopmail.com";
            case PHONE_NUMBER:
                return faker.phoneNumber().toString();
            case JOB_TITLE:
                return faker.job().title();
            case COMPANY_NAME:
                return faker.company().name();
            case ID_NUMBER:
                return faker.number().digits(4);
            default:
                return "No data found";
        }
    }

    public static String getRandomNumber(int digits) {
        return RandomStringUtils.randomNumeric(digits);
    }

    public static String getRandomAlphabetic(int digits) {
        return RandomStringUtils.randomAlphabetic(digits);
    }

    public static String getRandomAlphaNumeric(int digits) {
        return RandomStringUtils.randomAlphanumeric(digits);
    }
}
