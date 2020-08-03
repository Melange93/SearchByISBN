package com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary;

import com.reka.lakatos.searchbyisbn.webdocument.WebDocument;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocumentReader {

    public List<String> getBookPropertiesPageLinks(WebDocument webDocument) {
        return webDocument.getElementsByTag("a")
                .stream()
                .filter(element -> element.hasAttr("href")
                        && !element.hasAttr("target")
                        && element.hasText())
                .map(element -> element.attr("href"))
                .collect(Collectors.toList());
    }
}
