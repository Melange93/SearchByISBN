package com.reka.lakatos.searchbyisbn.webdocument.jsoup;

import com.reka.lakatos.searchbyisbn.webdocument.WebDocument;
import com.reka.lakatos.searchbyisbn.webdocument.WebElement;
import org.jsoup.nodes.Document;

import java.util.List;
import java.util.stream.Collectors;

class JsoupWebDocument implements WebDocument {

    private final Document document;

    JsoupWebDocument(Document document) {
        this.document = document;
    }

    @Override
    public String text() {
        return document.text();
    }

    @Override
    public List<WebElement> select(String cssQuery) {
        return document.select(cssQuery).stream()
                .map(JsoupWebElement::new)
                .collect(Collectors.toList());
    }
}
