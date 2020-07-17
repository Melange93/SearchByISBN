package com.reka.lakatos.searchbyisbn.webdocument.jsoup;

import com.reka.lakatos.searchbyisbn.webdocument.WebDocument;
import org.jsoup.nodes.Document;
import org.jsoup.parser.ParseSettings;
import org.jsoup.parser.Tag;

public class JsoupDocument extends JsoupElement implements WebDocument {

    private final Document document;

    public JsoupDocument(Document document) {
        super(Tag.valueOf("#root", ParseSettings.htmlDefault), document.location());
        this.document = document;
    }

    @Override
    public String text() {
        return document.text();
    }
}
