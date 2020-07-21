package com.reka.lakatos.searchbyisbn.webdocument.jsoup;

import com.reka.lakatos.searchbyisbn.webdocument.WebElement;
import org.jsoup.nodes.Element;

import java.util.List;
import java.util.stream.Collectors;

class JsoupWebElement implements WebElement {

    private final Element element;

    JsoupWebElement(Element element) {
        this.element = element;
    }

    @Override
    public String text() {
        return element.text();
    }

    @Override
    public List<WebElement> select(String cssQuery) {
        return element.select(cssQuery).stream()
                .map(JsoupWebElement::new)
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return element.toString();
    }
}
