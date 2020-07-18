package com.reka.lakatos.searchbyisbn.webdocument.jsoup;

import com.reka.lakatos.searchbyisbn.webdocument.WebElement;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;

import java.util.List;
import java.util.stream.Collectors;

class JsoupWebElement implements WebElement {

    private final Element element;

    JsoupWebElement(Element element) {
        this.element = element;
    }

    JsoupWebElement(Tag tag, String baseUri) {
        this.element = new Element(tag, baseUri);
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
}
