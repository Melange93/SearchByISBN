package com.reka.lakatos.searchbyisbn.webdocument.jsoup;

import com.reka.lakatos.searchbyisbn.webdocument.WebDocument;
import org.jsoup.nodes.Document;
import org.jsoup.parser.ParseSettings;
import org.jsoup.parser.Tag;

class JsoupWebDocument extends JsoupWebElement implements WebDocument {

    private final Document document;

    JsoupWebDocument(Document document) {
        super(Tag.valueOf("#root", ParseSettings.htmlDefault), document.location());
        this.document = document;
    }

    @Override
    public String text() {
        return document.text();
    }
}
