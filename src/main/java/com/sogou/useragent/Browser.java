package com.sogou.useragent;


public class Browser {
    public static final Browser DEFAULT_BROWSER = new Browser("-","-",null);
    public final String browerBrand; //品牌
    public final String family;
    public final String browerVersion;


    Browser(String browerBrand,String family,String browerVersion){
            this.browerBrand=browerBrand;
            this.family=family;
            this.browerVersion=browerVersion;
    }
}