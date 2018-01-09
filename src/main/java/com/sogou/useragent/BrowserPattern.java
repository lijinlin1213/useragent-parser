package com.sogou.useragent;


import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.sogou.useragent.Constant.*;


/**
 *
 */
public class BrowserPattern {
    private final Pattern pattern;
    private final String brandReplacement;
    private final String familyReplacement;
    private final String versionReplacement;

    BrowserPattern(Pattern pattern,String brandReplacement,String familyReplacement,String versionReplacement){
        this.pattern = pattern;
        this.brandReplacement = brandReplacement;
        this.familyReplacement = familyReplacement;
        this.versionReplacement = versionReplacement;
    }

    public static BrowserPattern patternFromMap(Map<String,String> configMap){
        String regex = configMap.get(REGEX);
        if(regex==null){
            throw  new IllegalArgumentException("browser's regex is lose");
        }
        String brand = configMap.get(BRAND);
        String family = configMap.get(FAMILY);
        String version = configMap.get(VERSION);

        return new BrowserPattern(Pattern.compile(regex,Pattern.CASE_INSENSITIVE),brand,family,version);
    }

    public Browser match(String uaString){
        String family = null,version=null;
        Matcher matcher = pattern.matcher(uaString.toLowerCase());
        if (!matcher.find()) {
            return null;
        }

        int groupCount = matcher.groupCount();
        if (familyReplacement != null) {
            family = familyReplacement;
        } else if (groupCount > 0) {
            /**
             * if groupCount equals to one,need to run
             * example:
             *    regex: (360[SE]E)
             *    useragent: Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; 360SE)
             **/
            /* this is matcher.group(1) because of matcher.group(0) is the full expression */
            family = matcher.group(1);
        }

        String brand = (brandReplacement == null || brandReplacement.isEmpty()) ? family : brandReplacement;
        if (!(versionReplacement == null || versionReplacement.isEmpty())) {
            version = versionReplacement;
        } else if (groupCount > 1) {
            version = matcher.group(2);
        }



        return family == null ? null : new Browser(brand, family, version);
    }
}
