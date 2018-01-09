package com.sogou.useragent;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**

 */
public class BrowserParser {
    private final List<BrowserPattern> patterns;

    BrowserParser(List<BrowserPattern> patterns) {
        this.patterns = patterns;
    }

    public static BrowserParser fromList(List<Map<String, String>> configList) {
        List<BrowserPattern> configPatterns = new ArrayList<>();
        for (Map<String, String> configMap : configList) {
            configPatterns.add(BrowserPattern.patternFromMap(configMap));
        }
        return new BrowserParser(configPatterns);
    }

    public Browser parse(final String uaString) {
        if (uaString == null || uaString.isEmpty()) {
            return Browser.DEFAULT_BROWSER;
        }
        Browser browser;
        for (BrowserPattern p : patterns) {
            if ((browser = p.match(uaString)) != null) {
                return browser;
            }
        }

        return Browser.DEFAULT_BROWSER;

    }
}
