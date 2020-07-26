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

    @Override
    public String toString() {
        return document.toString();
    }

    @Override
    public List<WebElement> getElementsByAttributeValueStarting(String attributeKey, String startOfAttributeValue) {
        return document.getElementsByAttributeValueStarting(attributeKey, startOfAttributeValue)
                .stream()
                .map(JsoupWebElement::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<WebElement> getElementsByAttributeValueMatching(String attributeName, String regex) {
        return document.getElementsByAttributeValueMatching(attributeName, regex)
                .stream()
                .map(JsoupWebElement::new)
                .collect(Collectors.toList());
    }
}
