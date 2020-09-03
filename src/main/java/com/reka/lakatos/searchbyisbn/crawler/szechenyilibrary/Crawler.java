package com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary;

import com.reka.lakatos.searchbyisbn.crawler.BookCrawler;
import com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.exception.FirstSearchingPageException;
import com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.exception.NextSearchingPageException;
import com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.factory.WebDocumentFactory;
import com.reka.lakatos.searchbyisbn.document.Book;
import com.reka.lakatos.searchbyisbn.webdocument.WebDocument;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
            WebDocument webDocument;
            String newScanTermToNextPage;

            if (scanTermToNextPage == null) {
                log.info("First page crawling. Searching group: " + isbnSearchingTerm[isbnGroupIndex]);
                webDocument = documentFactory.getFirstSearchingResult(isbnSearchingTerm[isbnGroupIndex]);
            } else {
                log.info("Next page crawling. ScanTerm: " + scanTermToNextPage);
                webDocument = documentFactory.getNextSearchingPage(scanTermToNextPage);
            }

            newScanTermToNextPage = documentReader.getScanTermToNextPage(webDocument);
            if (Objects.equals(scanTermToNextPage, newScanTermToNextPage)) {
                return null;
            }
            scanTermToNextPage = newScanTermToNextPage;
            List<Book> crawledBooks = bookListCreator.getCrawledBooks(webDocument);

            List<Book> invalidBooks = filterBooksIsbn(crawledBooks, nextInvalidIsbnGroup[isbnGroupIndex]);
            if (invalidBooks.size() != 0) {
                log.info("Finished the " + isbnSearchingTerm[isbnGroupIndex] + " book group.");
                scanTermToNextPage = null;
                isbnGroupIndex++;
            }

            return filterBooksIsbn(crawledBooks, isbnSearchingTerm[isbnGroupIndex]);
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

    private List<Book> filterBooksIsbn(List<Book> crawledBooks, String isbnScanTerm) {
        return crawledBooks.stream()
                .filter(book -> book.getIsbn().startsWith(isbnScanTerm))
                .collect(Collectors.toList());
    }

    private void errorScrolling() {
        scanTermToNextPage = null;
        isbnGroupIndex++;
    }
}
