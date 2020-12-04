package com.reka.lakatos.booksofhungary.crawlers.implementation.szechenyilibrary;

import com.reka.lakatos.booksofhungary.crawlers.webdocument.domain.WebDocument;
import com.reka.lakatos.booksofhungary.crawlers.webdocument.domain.WebElement;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@ConditionalOnProperty(name = "crawler.book-crawler", havingValue = "szechenyi")
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

    public Optional<String> getBookAmicusId(WebDocument webDocument) {
        List<WebElement> scripts = webDocument.select("script").stream()
                .filter(webElement -> webElement.attr("type").equals("text/javascript"))
                .collect(Collectors.toList());
        String lastScript = scripts.get(scripts.size() - 1).toString();
        Matcher matcher = Pattern.compile("amicus_azonosito[\\s\\=\\']*[\\d]*")
                .matcher(lastScript);

        return matcher.find() ?
                Optional.of(matcher.group().replaceAll("[a-z_=\\'\\s\\n]*", "")) :
                Optional.empty();
    }

    public boolean hasRelatedBooks(final WebDocument webDocument) {
        return !webDocument.select(".sub_table").isEmpty();
    }
}
