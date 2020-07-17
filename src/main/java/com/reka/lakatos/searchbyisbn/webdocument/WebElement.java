package com.reka.lakatos.searchbyisbn.webdocument;

public interface WebElement {
    String text();
    WebElements select(String cssQuery);
    WebElements getElementsByAttributeValueMatching(String key, String regex);
    WebElements getElementsByAttributeValueStarting(String key, String valuePrefix);
}
