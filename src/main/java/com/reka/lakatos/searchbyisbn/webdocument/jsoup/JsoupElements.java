package com.reka.lakatos.searchbyisbn.webdocument.jsoup;

import com.reka.lakatos.searchbyisbn.webdocument.WebElement;
import com.reka.lakatos.searchbyisbn.webdocument.WebElements;
import org.jsoup.select.Elements;

public class JsoupElements implements WebElements {

    private final Elements elements;

    public JsoupElements(Elements elements) {
        this.elements = elements;
    }

    @Override
    public WebElement first() {
        return new JsoupElement(elements.first());
    }
}
