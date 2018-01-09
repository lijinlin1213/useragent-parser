package com.sogou.hive.udf;

import com.sogou.useragent.Browser;
import com.sogou.useragent.BrowserParser;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.SafeConstructor;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class GetBroswerName extends UDF {
    private final static String CONFIG_FILE = "/BrowserConfig.yaml";
    private static BrowserParser parser;

    static {
        Yaml yaml = new Yaml(new SafeConstructor());
        InputStream stream = Browser.class.getResourceAsStream(CONFIG_FILE);
        List<Map<String, String>> props = (List<Map<String, String>>) yaml.load(stream);
        parser = BrowserParser.fromList(props);
    }

    public String evaluate(String useragent) {
        if (useragent != null || !useragent.isEmpty()) {
            Browser browser=parser.parse(useragent);
            return browser.browerBrand+":"+browser.browerVersion;
        } else {
            return "-";
        }
    }

}
