package org.jacuzzi.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by girish.desai on 5/3/2016.
 */
public class Utils {
    private static Random rand = null;
    private static String strUpperChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static String strLowerChars = "abcdefghijklmnopqrstuvwxyz";

    public static boolean isEmpty(String... str) {
        String[] var1 = str;
        int var2 = str.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            String s = var1[var3];
            if(s == null) {
                return true;
            }

            if(s.trim().length() == 0) {
                return true;
            }
        }

        return false;
    }

    public static String dateToString(Date date, String format) {
        String sDate = null;
        if(date != null && !Utils.isEmpty(new String[]{format})) {
            try {
                SimpleDateFormat df = new SimpleDateFormat(format);
                sDate = df.format(date);
            } catch (IllegalArgumentException var4) {
                ;
            }

            return sDate;
        } else {
            return null;
        }
    }

    public static String randomString(int len) {
        String str = "";

        for(int i = 0; i < len; ++i) {
            str = str + randomChar(strUpperChars + strLowerChars);
        }

        return str;
    }
    private static char randomChar(String str) {
        if(rand == null) {
            rand = new Random();
        }

        int i = rand.nextInt(str.length());
        return str.charAt(i);
    }

}
