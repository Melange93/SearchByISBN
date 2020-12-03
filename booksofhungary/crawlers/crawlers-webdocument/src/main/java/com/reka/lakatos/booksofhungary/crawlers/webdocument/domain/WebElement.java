package com.reka.lakatos.booksofhungary.crawlers.webdocument.domain;

import java.util.List;

public interface WebElement {

    String text();

    List<WebElement> select(String cssQuery);

    String toString();

    String attr(String attributeKey);

    boolean hasAttr(String attr);

    boolean hasText();
}
