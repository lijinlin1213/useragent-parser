package com.sogou.hive.udf;

import org.apache.hadoop.hive.ql.exec.UDF;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Created by lijinlin on 2017/9/11.
 */
public class GetTime extends UDF {
    public static Pattern uigsTsPattern = Pattern.compile("(.*/.*/\\d{4}):(\\d{2}):(\\d{2}):(\\d{2})");
    public static Pattern resinTsPattern = Pattern.compile("(\\d{4}-\\d{2}-\\d{2}) (\\d{2}):(\\d{2}):(\\d{2})");
    public static SimpleDateFormat uigsfm = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss", new Locale("en"));
    public static SimpleDateFormat resinfm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", new Locale("ch"));
    public static SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm");

    public String evaluate(String timeStr) {
        String result = null;
        if (timeStr != null && !timeStr.isEmpty()) {

            try {
                result = parserTime(timeStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public String parserTime(String str) throws ParseException {
        if (uigsTsPattern.matcher(str).find()) {
            return format.format(uigsfm.parse(str));
        } else if (resinTsPattern.matcher(str).find()) {
            return format.format(resinfm.parse(str));
        } else {
            return null;
        }

    }
}
