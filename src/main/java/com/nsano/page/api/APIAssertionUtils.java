package com.nsano.page.api;

import io.qameta.allure.Allure;
import io.restassured.response.Response;

import java.util.*;

public class APIAssertionUtils {

    public static boolean assertExpectedValuesWithJsonPath(Response response, Map<String, Object> expectedValuesMap) {
        List<AssertionKeys> actualValuesMap = new ArrayList<>();
//        actualValuesMap.add(new AssertionKeys("JSON_PATH", "EXPECTED_VALUE", "ACTUAL_VALUE", "RESULT"));
        boolean allMatched = true;

        Set<String> jsonPaths = expectedValuesMap.keySet();
        for (String jsonPath : jsonPaths) {
            Optional<Object> actualValue = Optional.ofNullable(response.jsonPath().get(jsonPath));
            if (actualValue.isPresent()) {
                Object value = actualValue.get();
                if (value.equals(expectedValuesMap.get(jsonPath)))
                    actualValuesMap.add(new AssertionKeys(jsonPath, expectedValuesMap.get(jsonPath), value, "MATCHED"));
                else {
                    allMatched = false;
                    actualValuesMap.add(new AssertionKeys(jsonPath, expectedValuesMap.get(jsonPath), value, "NOT_MATCHED"));
                }
            } else {
                allMatched = false;
                actualValuesMap.add(new AssertionKeys(jsonPath, expectedValuesMap.get(jsonPath), "VALUE_NOT_FOUND", "NOT_MATCHED"));
            }
        }
        if (allMatched)
            Allure.step("All assertions are passed. ✅");
        else {
            Allure.step("All assertions are not passed. ❌");
        }

        String[][] finalAssertionMap = actualValuesMap.stream().map(assertions -> new String[]{assertions.getJsonPath(), String.valueOf(assertions.getExpectedValue()), String.valueOf(assertions.getActualValue()), assertions.getResult()})
                .toArray(String[][]::new);

//        StringBuilder output = new StringBuilder();
//        output.append("Assertion Results:");
//        output.append("[\n");
//        for (String[] row : finalAssertionMap) {
//            output.append("  ").append(Arrays.toString(row)).append(",\n");
//        }
//        output.append("]");
//        Allure.attachment("Assertion Results", output.toString());

        // Add header to the final output
        finalAssertionMap = prependHeader(finalAssertionMap);

        int numColumns = finalAssertionMap[0].length;

        // Determine column widths
        int[] columnWidths = new int[numColumns];
        Arrays.fill(columnWidths, 0);

        for (String[] row : finalAssertionMap) {
            for (int i = 0; i < numColumns; i++) {
                columnWidths[i] = Math.max(columnWidths[i], row[i].length());
            }
        }

        // Build format string
        StringBuilder formatString = new StringBuilder();
        formatString.append("+");
        for (int width : columnWidths) {
            formatString.append(repeat("-", width)).append("-+");
        }
        String separator = formatString.toString();

        // Header format
        formatString = new StringBuilder("|");
        for (int width : columnWidths) {
            formatString.append(" %-").append(width).append("s |");
        }
        String headerFormat = formatString.toString();

        // Build the table
        StringBuilder table = new StringBuilder();
        table.append(separator).append("\n");
        for (String[] row : finalAssertionMap) {
            table.append(String.format(headerFormat, (Object[]) row)).append("\n");
            table.append(separator).append("\n");
        }

        Allure.attachment("Assertion Results", table.toString());
        return allMatched;
    }

    private static String[][] prependHeader(String[][] data) {
        String[] header = {"JSON_PATH", "EXPECTED_VALUE", "ACTUAL_VALUE", "RESULT"};
        String[][] result = new String[data.length + 1][header.length];
        result[0] = header;
        System.arraycopy(data, 0, result, 1, data.length);
        return result;
    }

    private static String repeat(String str, int times) {
        if (times <= 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(str.length() * times);
        for (int i = 0; i < times; i++) {
            sb.append(str);
        }
        return sb.toString();
    }

}