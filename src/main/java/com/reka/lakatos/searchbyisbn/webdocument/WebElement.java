package com.reka.lakatos.searchbyisbn.webdocument;

import java.util.List;

public interface WebElement {

    String text();

    List<WebElement> select(String cssQuery);

    String toString();

    String attr(String attributeKey);

    boolean hasAttr(String attr);

    boolean hasText();
}
