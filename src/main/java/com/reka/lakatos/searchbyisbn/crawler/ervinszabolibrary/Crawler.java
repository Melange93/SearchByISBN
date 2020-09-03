package com.reka.lakatos.searchbyisbn.crawler.ervinszabolibrary;

import com.reka.lakatos.searchbyisbn.crawler.BookCrawler;
import com.reka.lakatos.searchbyisbn.crawler.ervinszabolibrary.documentreader.DocumentReaderFacade;
import com.reka.lakatos.searchbyisbn.crawler.ervinszabolibrary.exception.BookListDownloadException;
import com.reka.lakatos.searchbyisbn.document.Book;
import com.reka.lakatos.searchbyisbn.webdocument.WebClient;
import com.reka.lakatos.searchbyisbn.webdocument.WebDocument;
import com.reka.lakatos.searchbyisbn.webdocument.exception.WebClientException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "crawler.book-crawler", havingValue = "ervin")
public class Crawler implements BookCrawler {

    private final URLFactory urlFactory;
    private final DocumentReaderFacade documentReaderFacade;
    private final CrawledBooksCreator crawledBooksCreator;
    private final WebClient webClient;

    private static final String NAME = "Ervin Szabo Library";
    private static final String[] ISBN_GROUPS = {"978963", "978615", "963"};
    private static final String PAGE_SIZE = "10";
    private static final int MAX_NUMBER = 99;

    private int resultNumber;
    private int page = 5;
    private int isbnSeventhNumber = 99;
    private int ISBNGroupIndex = 2;

    @Override
    public List<Book> getNextBooks() {
        try {
            if (ISBNGroupIndex == ISBN_GROUPS.length) {
                System.out.println("here");
                return null;
            }

            String lastNumber = calculateSearchingLastNumber();
            printSearchingInformation(lastNumber);
            List<Book> books = getCrawledBooks(lastNumber);
            booksPageScrolling();

            return books;
        } catch (Exception e) {
            log.error("Exception happened while crawling list book location! Page " + page
                    + " Searching ISBN: " + ISBN_GROUPS[ISBNGroupIndex] + isbnSeventhNumber, e);

            booksPageScrollingWhenExceptionHappened();
            return getNextBooks();
        }
    }

    private void printSearchingInformation(String lastNumber) {
        log.info("Start crawling: {}, Page number: {}, Searching ISBN: {}",
                NAME, page + 1, ISBN_GROUPS[ISBNGroupIndex] + lastNumber);
    }

    private String calculateSearchingLastNumber() {
        String lastNumber = String.valueOf(isbnSeventhNumber);
        if (isbnSeventhNumber < 10) {
            lastNumber = "0" + isbnSeventhNumber;
        }
        return lastNumber;
    }

    private List<Book> getCrawledBooks(String lastNumber) {

        String isbnSearchingUrl = urlFactory.createISBNSearchingUrl(
                page,
                ISBN_GROUPS[ISBNGroupIndex] + lastNumber,
                PAGE_SIZE
        );
        final Map<String, String> booksPropertiesPageUrlInformation =
                getInformationForBookPropertiesUrl(isbnSearchingUrl);

        return crawledBooksCreator.getCrawledBooks(booksPropertiesPageUrlInformation);
    }

    private Map<String, String> getInformationForBookPropertiesUrl(String ISBNSearchingUrl) {
        try {
            final WebDocument booksPage = webClient.sendGetRequest(ISBNSearchingUrl);
            resultNumber = documentReaderFacade.getResultNumber(booksPage);
            return documentReaderFacade.getInformationForBookPropertiesPage(booksPage);
        } catch (WebClientException e) {
            throw new BookListDownloadException("Unable to download the list book page! Url: " + ISBNSearchingUrl, e);
        }
    }

    private void booksPageScrollingWhenExceptionHappened() {
        page++;
    }

    private void booksPageScrolling() {
        int remainingBooks = resultNumber - ((page + 1) * Integer.parseInt(PAGE_SIZE));
        if (remainingBooks > 0) {
            page++;
        }

        if (remainingBooks <= 0) {
            page = 0;
            if (isbnSeventhNumber >= MAX_NUMBER) {
                isbnSeventhNumber = 0;
                ISBNGroupIndex++;
            } else {
                isbnSeventhNumber++;
            }
        }
    }
}
