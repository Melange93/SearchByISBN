package com.reka.lakatos.searchbyisbn.webdocument.jsoup;

import com.reka.lakatos.searchbyisbn.webdocument.WebElement;
import com.reka.lakatos.searchbyisbn.webdocument.WebElements;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;

public class JsoupElement implements WebElement {

    private final Element element;

    public JsoupElement(Element element) {
        this.element = element;
    }

    public JsoupElement(Tag tag, String baseUri) {
        this.element = new Element(tag, baseUri);
    }

    @Override
    public String text() {
        return element.text();
    }

    @Override
    public WebElements select(String cssQuery) {
        return new JsoupElements(element.select(cssQuery));
    }

    @Override
    public WebElements getElementsByAttributeValueMatching(String key, String regex) {
        return new JsoupElements(element.getElementsByAttributeValueMatching(key, regex));

    }

    @Override
    public WebElements getElementsByAttributeValueStarting(String key, String valuePrefix) {
        return new JsoupElements(element.getElementsByAttributeValueStarting(key, valuePrefix));
    }
}
