package com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary;

import com.reka.lakatos.searchbyisbn.crawler.BookCrawler;
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

    @Override
    public List<Book> getNextBooks() {
        System.out.println(getBookListLinks());
        return null;
    }

    private List<String> getBookListLinks() {
        WebDocument webDocument = documentFactory.getSearchingResult();
        bookListCreator.getCrawledBooks(webDocument);

        return null;
    }
}
