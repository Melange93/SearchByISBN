package com.reka.lakatos.booksofhungary.crawlers.implementation.szechenyilibrary;

import com.reka.lakatos.booksofhungary.crawlers.implementation.szechenyilibrary.exception.FirstSearchingPageException;
import com.reka.lakatos.booksofhungary.crawlers.implementation.szechenyilibrary.exception.NextSearchingPageException;
import com.reka.lakatos.booksofhungary.crawlers.implementation.szechenyilibrary.factory.WebDocumentFactory;
import com.reka.lakatos.booksofhungary.crawlers.domain.crawler.BookCrawler;
import com.reka.lakatos.booksofhungary.crawlers.domain.database.Book;
import com.reka.lakatos.booksofhungary.crawlers.webdocument.domain.WebDocument;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "crawler.book-crawler", havingValue = "szechenyi")
public class Crawler implements BookCrawler {
    private final BookListCreator bookListCreator;
    private final WebDocumentFactory documentFactory;
    private final DocumentReader documentReader;

    private final String[] isbnSearchingTerm = {"978-963", "978-615", "963", "615"};
    private final String[] nextInvalidIsbnGroup = {"978-964", "978-616", "964", "616"};
    private String scanTermToNextPage;
    private int isbnGroupIndex = 0;

    @Override
    public List<Book> getNextBooks() {
        try {
            if (isbnGroupIndex > isbnSearchingTerm.length - 1) {
                return null;
            }
            WebDocument searchingResult = getSearchingResult();
            String newScanTermToNextPage = documentReader.getScanTermToNextPage(searchingResult);
            if (Objects.equals(scanTermToNextPage, newScanTermToNextPage)) {
                return null;
            }
            scanTermToNextPage = newScanTermToNextPage;
            if (scanTermToNextPage.startsWith(nextInvalidIsbnGroup[isbnGroupIndex])) {
                scrollingToNextISBNGroup();
            }
            return bookListCreator.getCrawledBooks(searchingResult);
        } catch (FirstSearchingPageException e) {
            log.error(String.valueOf(e));
            errorScrolling();
            return getNextBooks();
        } catch (NextSearchingPageException e) {
            log.error(e + " ISBN Group: " + isbnSearchingTerm[isbnGroupIndex]);
            errorScrolling();
            return getNextBooks();
        }
    }

    private WebDocument getSearchingResult() {
        if (scanTermToNextPage == null) {
            return getWebDocumentFromFirstSearch();
        }
        return getWebDocumentFromFurtherSearching();
    }

    private WebDocument getWebDocumentFromFurtherSearching() {
        WebDocument webDocument;
        log.info("Next page crawling. ScanTerm: " + scanTermToNextPage);
        webDocument = documentFactory.getNextSearchingPage(scanTermToNextPage);
        return webDocument;
    }

    private WebDocument getWebDocumentFromFirstSearch() {
        log.info("First page crawling. Searching group: " + isbnSearchingTerm[isbnGroupIndex]);
        return documentFactory.getFirstSearchingResult(isbnSearchingTerm[isbnGroupIndex]);
    }

    private void scrollingToNextISBNGroup() {
        log.info("Finished the " + isbnSearchingTerm[isbnGroupIndex] + " book group.");
        scanTermToNextPage = null;
        isbnGroupIndex++;
    }

    private void errorScrolling() {
        scanTermToNextPage = null;
        isbnGroupIndex++;
    }
}
