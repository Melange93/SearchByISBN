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
    private static final int MAX_NUMBER = 9;

    private int page = 0;
    private int isbnSeventhNumber = 0;
    private int ISBNGroupIndex = 2;

    @Override
    public List<Book> getNextBooks() {
        try {
            log.info("Start crawling: {}, Page number: {}, Searching ISBN: {}",
                    NAME, page + 1, ISBN_GROUPS[ISBNGroupIndex] + isbnSeventhNumber);

            List<Book> books = getCrawledBooks();
            booksPageScrolling(books);

            System.out.println(books);
            if (ISBNGroupIndex == ISBN_GROUPS.length) {
                return null;
            }

            return books;
        } catch (Exception e) {
            log.error("Exception happened while crawling list book location! Page " + page
                    + " Searching ISBN: " + ISBN_GROUPS[ISBNGroupIndex] + isbnSeventhNumber, e);

            booksPageScrollingWhenExceptionHappened();
            return getNextBooks();
        }
    }

    private List<Book> getCrawledBooks() {
        final Map<String, String> booksPropertiesPageUrlInformation =
                getInformationForBookPropertiesUrl(
                        urlFactory.createISBNSearchingUrl(
                                page,
                                ISBN_GROUPS[ISBNGroupIndex] + isbnSeventhNumber,
                                PAGE_SIZE
                        )
                );

        return crawledBooksCreator.getCrawledBooks(booksPropertiesPageUrlInformation);
    }

    private Map<String, String> getInformationForBookPropertiesUrl(String ISBNSearchingUrl) {
        try {
            final WebDocument booksPage = webClient.sendGetRequest(ISBNSearchingUrl);

            return documentReaderFacade.getInformationForBookPropertiesPage(booksPage);
        } catch (WebClientException e) {
            throw new BookListDownloadException("Unable to download the list book page! Url: " + ISBNSearchingUrl, e);
        }
    }

    private void booksPageScrollingWhenExceptionHappened() {
        page++;
    }

    private void booksPageScrolling(List<Book> books) {
        page++;

        if (isCrawlerFinishedThisSeventhISBNNumberCrawling(books)) {
            page = 0;
            isbnSeventhNumber++;
        }

        if (isCrawlerFinishedThisISBNGroup(books)) {
            page = 0;
            isbnSeventhNumber = 0;
            ISBNGroupIndex++;
        }
    }

    private boolean isCrawlerFinishedThisSeventhISBNNumberCrawling(List<Book> books) {
        return books.size() == 0
                && isbnSeventhNumber <= MAX_NUMBER;
    }

    private boolean isCrawlerFinishedThisISBNGroup(List<Book> books) {
        return books.size() == 0
                && isbnSeventhNumber > MAX_NUMBER;
    }
}
