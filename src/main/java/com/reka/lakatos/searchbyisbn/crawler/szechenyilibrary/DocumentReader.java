package com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary;

import com.reka.lakatos.searchbyisbn.webdocument.WebDocument;
import com.reka.lakatos.searchbyisbn.webdocument.WebElement;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocumentReader {

    public List<String> getBookEditionsPageLinks(final WebDocument webDocument) {
        return webDocument.getElementsByTag("a")
                .stream()
                .filter(element -> element.hasAttr("href")
                        && !element.hasAttr("target")
                        && element.hasText())
                .map(element -> element.attr("href"))
                .collect(Collectors.toList());
    }

    public List<String> getEditionsLink(final WebDocument webDocument) {
        return webDocument.select(".fullrekview a")
                .stream()
                .map(webElement -> webElement.attr("href"))
                .collect(Collectors.toList());
    }

    public List<WebElement> getBookPropertiesName(WebDocument webDocument) {
        return webDocument.select(".fieldLabel").stream()
                .map(webElement -> webElement.select("td"))
                .flatMap(List::stream)
                .filter(WebElement::hasText)
                .collect(Collectors.toList());
    }

    public List<WebElement> getBookPropertiesValue(WebDocument webDocument) {
        return webDocument.select(".fieldValue").stream()
                .filter(WebElement::hasText)
                .collect(Collectors.toList());
    }

    public String getScanTermToNextPage(WebDocument webDocument) {
        List<String> scanTerms = webDocument.select("input").stream()
                .filter(webElement -> webElement.attr("name").equals("SCAN_TERM"))
                .map(webElement -> webElement.attr("value"))
                .collect(Collectors.toList());
        return scanTerms.get(scanTerms.size() - 1);
    }
}
