package com.nsano.page.api.nsano;

import com.nsano.page.api.nsano.pojos.Reqres;
import com.nsano.utilities.RandomDataGenerator;
import net.datafaker.Faker;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.HashMap;
import java.util.Map;

import static com.nsano.utilities.RandomDataGenerator.faker;

public class Payloads {

    /**
     * @param name
     * @param job
     * @param id
     * @return
     */
    public static String getCreateUserPayloadFromString(String name, String job, String id) {
        String payload = "{\n" +
                "    \"name\": \"" + name + "\",\n" +
                "    \"job\": \"" + job + "\",\n" +
                "    \"id\": \"" + id + "\"\n" +
                "}";
        return payload;
    }

    /**
     * @param name
     * @param job
     * @param id
     * @return
     */
    public static Map<String, Object> getCreateUserPayloadFromMap(String name, String job, String id) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("name", name);
        payload.put("job", job);
        payload.put("id", id);
        return payload;
    }

    public static Map<String, Object> getCreateUserPayloadFromMap() {
        Map<String, Object> payload = new HashMap<>();
        Faker faker = new Faker();
        payload.put("name", RandomDataGenerator.getRandomDataFor(RandomDataGenerator.RandomDataTypeNames.FULL_NAME));
        payload.put("job",  RandomDataGenerator.getRandomDataFor(RandomDataGenerator.RandomDataTypeNames.JOB_TITLE));
        payload.put("id",  RandomDataGenerator.getRandomDataFor(RandomDataGenerator.RandomDataTypeNames.ID_NUMBER));
//        payload.put("name", faker.name().firstName());
//        payload.put("job", faker.job().position());
//        payload.put("id", faker.number().digits(4));
//        RandomStringUtils.randomAlphabetic(2);
        return payload;
    }

    public static String getRandomNumber(int count) {
        return faker.number().digits(count);
    }

    public static String getRandomAlphabet(int count) {
        return RandomStringUtils.randomAlphabetic(count);
    }

    public static Reqres getCreateUserPayloadFromPojo() {
       return Reqres
                .builder()
                .name(RandomDataGenerator.getRandomDataFor(RandomDataGenerator.RandomDataTypeNames.FULL_NAME))
                .job(RandomDataGenerator.getRandomDataFor(RandomDataGenerator.RandomDataTypeNames.JOB_TITLE))
                .id(RandomDataGenerator.getRandomDataFor(RandomDataGenerator.RandomDataTypeNames.ID_NUMBER))
                .build();
    }


}
