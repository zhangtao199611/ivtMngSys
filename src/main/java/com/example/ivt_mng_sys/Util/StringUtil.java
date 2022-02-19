package com.example.ivt_mng_sys.Util;

public class StringUtil {
    public static String getFourCharacterByInt(int number) {
        String str = "0000";
        if (number < 10) {
            str = str.substring(0, str.length() - 1) + number;
        } else if (number > 10 && number < 100) {
            str = str.substring(0, str.length() - 2) + number;
        } else if (number > 100 && number < 1000) {
            str = str.substring(0, str.length() - 3) + number;
        } else if (number > 1000 && number < 10000) {
            str = str.substring(0, str.length() - 4) + number;
        }
        return str;
    }

    public static String getTwoCharacterByInt(int number) {
        String str = "00";
        if (number < 10) {
            str = str.substring(0, str.length() - 1) + number;
        } else if (number > 10 && number < 100) {
            str = str.substring(0, str.length() - 2) + number;
        }
        return str;
    }
}
