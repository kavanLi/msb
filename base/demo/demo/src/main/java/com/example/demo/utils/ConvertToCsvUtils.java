package com.example.demo.utils;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author: kavanLi
 * @create: 2019-08-01 15:53
 * To change this template use File | Settings | File and Code Templates.
 */
public final class ConvertToCsvUtils {

    /* fields -------------------------------------------------------------- */

    private ConvertToCsvUtils() {
    }

    /* constructors -------------------------------------------------------- */


    /* public methods ------------------------------------------------------ */

    /**
     * https://www.baeldung.com/java-csv
     * @param data
     * @return
     */
    public static String convertToCSV(String[] data) {
        return Stream.of(data).map(o -> escapeSpecialCharacters(o)).collect(Collectors.joining(","));
    }

    /**
     * https://www.baeldung.com/java-csv
     * @param data
     * @return
     */
    public static String escapeSpecialCharacters(String data) {
        if (null == data) {
            data = "null";
        }
        String escapedData = data.replaceAll("\\R", " ");
        if (data.contains(",") || data.contains("\"") || data.contains("'")) {
            data = data.replace("\"", "\"\"");
            escapedData = "\"" + data + "\"";
        }
        return escapedData;
    }

    /* private methods ----------------------------------------------------- */


    /* getters/setters ----------------------------------------------------- */

}
