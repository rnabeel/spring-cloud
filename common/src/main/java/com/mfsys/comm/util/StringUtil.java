package com.mfsys.comm.util;

public class StringUtil {

    private StringUtil() {

    }

    // ParseStringToArray also accepts ("Regex");
    public static String[] parseStringToArray(String dataString, String dataDelimiter) {
        return dataString.split(dataDelimiter);
    }

    public static boolean isEmpty(String str) {
        return (str == null) || "".equals(str.trim());
    }
}
 