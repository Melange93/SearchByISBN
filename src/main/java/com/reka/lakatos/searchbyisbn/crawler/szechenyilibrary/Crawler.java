package com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary;

import com.reka.lakatos.searchbyisbn.crawler.BookCrawler;
import com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.factory.WebDocumentFactory;
import com.reka.lakatos.searchbyisbn.document.Book;
import com.reka.lakatos.searchbyisbn.webdocument.WebDocument;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "crawler.book-crawler", havingValue = "szechenyi")
public class Crawler implements BookCrawler {
    private final BookListCreator bookListCreator;
    private final WebDocumentFactory documentFactory;
    private final DocumentReader documentReader;

    private String scanTermToNextPage;

    @Override
    public List<Book> getNextBooks() {

        if (scanTermToNextPage == null) {
            WebDocument webDocument = documentFactory.getSearchingResult();
            scanTermToNextPage = documentReader.getScanTermToNextPage(webDocument);
            return bookListCreator.getCrawledBooks(webDocument);
        }
        log.info("Here");

        WebDocument nextSearchingPage = documentFactory.getNextSearchingPage(scanTermToNextPage);
        return bookListCreator.getCrawledBooks(nextSearchingPage);
    }
}
