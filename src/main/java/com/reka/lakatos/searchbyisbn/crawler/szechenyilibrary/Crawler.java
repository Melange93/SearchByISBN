package com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary;

import com.reka.lakatos.searchbyisbn.crawler.BookCrawler;
import com.reka.lakatos.searchbyisbn.document.Book;
import com.reka.lakatos.searchbyisbn.webdocument.WebDocument;
import com.reka.lakatos.searchbyisbn.webdocument.WebElement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "crawler.book-crawler", havingValue = "szechenyi")
public class Crawler implements BookCrawler {
    private final DocumentReader reader;
    private final WebDocumentFactory documentFactory;


    @Override
    public List<Book> getNextBooks() {
        System.out.println(getBookListLinks());
        return null;
    }

    private List<String> getBookListLinks() {
        WebDocument webDocument = documentFactory.getSearchingResult();

        List<String> bookEditionsLinks = reader.getBookEditionsPageLinks(webDocument);
        List<String> collect = bookEditionsLinks.stream()
                .map(this::getBookEditionsLink)
                .flatMap(List::stream)
                .collect(Collectors.toList());
        WebDocument webDocument1 = documentFactory.visitBook("lv_view_records.html?SESSION_ID=1596452707_1012502954&lv_action=LV_View_Records&NR_RECORDS_TO_SHOW=1&GOTO_RECORD=1&RESULT_SET_NAME=set_1596461906_0&DISPLAY_RECORD_XSLT=html&ELEMENT_SET_NAME=F");

        List<String> collect1 = webDocument1.select(".fieldLabel").stream()
                .map(webElement -> webElement.select("td"))
                .flatMap(List::stream)
                .filter(WebElement::hasText)
                .map(WebElement::text)
                .collect(Collectors.toList());

        List<String> collect2 = webDocument1.select(".fieldValue").stream()
                .filter(WebElement::hasText)
                .map(WebElement::text)
                .collect(Collectors.toList());

        Map<String, String> bookProperties = IntStream.range(0, collect2.size())
                .boxed()
                .collect(Collectors.toMap(collect1::get, collect2::get));

        System.out.println(bookProperties);

        return null;
    }

    private List<String> getBookEditionsLink(final String allEditionsPageUrl) {
        final WebDocument webDocument = documentFactory.visitBookEditionsLink(allEditionsPageUrl);
        return reader.getEditionsLink(webDocument);
    }
}
