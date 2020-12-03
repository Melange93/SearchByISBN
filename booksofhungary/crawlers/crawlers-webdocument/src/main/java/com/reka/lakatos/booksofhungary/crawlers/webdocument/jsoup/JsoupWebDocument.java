package com.reka.lakatos.booksofhungary.crawlers.webdocument.jsoup;

import com.reka.lakatos.booksofhungary.crawlers.webdocument.domain.WebDocument;
import com.reka.lakatos.booksofhungary.crawlers.webdocument.domain.WebElement;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

public class JsoupWebDocument implements WebDocument {

    private final Document document;

    public JsoupWebDocument(Document document) {
        this.document = document;
    }

    @Override
    public String text() {
        return document.text();
    }

    @Override
    public List<WebElement> select(final String cssQuery) {
        final Elements select = document.select(cssQuery);
        return createWebElementFromElements(select);
    }

    @Override
    public String toString() {
        return document.toString();
    }

    @Override
    public List<WebElement> getElementsByAttributeValueStarting(
            final String attributeKey,
            final String startOfAttributeValue
    ) {
        final Elements elementsByAttributeValueStarting = document
                .getElementsByAttributeValueStarting(attributeKey, startOfAttributeValue);;
        return createWebElementFromElements(elementsByAttributeValueStarting);
    }

    @Override
    public List<WebElement> getElementsByAttributeValueMatching(
            final String attributeName,
            final String regex
    ) {
        final Elements elementsByAttributeValueMatching = document
                .getElementsByAttributeValueMatching(attributeName, regex);
        return createWebElementFromElements(elementsByAttributeValueMatching);
    }

    @Override
    public List<WebElement> getElementsByTag(final String tag) {
        final Elements elementsByTag = document.getElementsByTag(tag);
        return createWebElementFromElements(elementsByTag);
    }

    private List<WebElement> createWebElementFromElements(Elements elements) {
        return elements.stream().map(JsoupWebElement::new)
                .collect(Collectors.toList());
    }
}
