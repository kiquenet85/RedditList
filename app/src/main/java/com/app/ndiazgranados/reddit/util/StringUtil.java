package com.app.ndiazgranados.reddit.util;

/**
 * @author n.diazgranados
 */
public class StringUtil {
    public static String getUnknownIfEmpty(String value) {
        return (value == null || value.isEmpty()) ? "Unknown" : value;
    }
}
