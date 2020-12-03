package com.reka.lakatos.booksofhungary.crawlers.webdocument.domain;

import java.util.List;

public interface WebDocument {

    String text();

    List<WebElement> select(String cssQuery);

    String toString();

    List<WebElement> getElementsByAttributeValueStarting(String attributeKey, String startOfAttributeValue);

    List<WebElement> getElementsByAttributeValueMatching(String attributeName, String regex);

    List<WebElement> getElementsByTag(String tag);
}
