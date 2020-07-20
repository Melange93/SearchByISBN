package com.reka.lakatos.searchbyisbn.webdocument;

import java.util.List;

public interface WebDocument {

    String text();

    List<WebElement> select(String cssQuery);

    String toString();
}
