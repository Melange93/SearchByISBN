package com.reka.lakatos.searchbyisbn.crawler.ervinszabolibrary;

import com.reka.lakatos.searchbyisbn.crawler.BookCrawler;
import com.reka.lakatos.searchbyisbn.document.Book;
import com.reka.lakatos.searchbyisbn.exception.BookListDownloadException;
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
    private final PageReader pageReader;
    private final BookListCreator bookListCreator;
    private final WebClient webClient;

    private static final String NAME ="Ervin Szabo Library";
    private static final String ISBN963 = "978963";
    private static final String ISBN615 = "978615";
    private static final String PAGE_SIZE = "10";
    private static final int ISBN_MAX_SEVENTH_NUMBER = 9;

    private int page = 0;
    private int isbnSeventhNumber = 0;
    private String searchingISBNMainGroup = ISBN963;

    @Override
    public List<Book> getNextBooks() {
        try {
            log.info("Start crawling: {}, Page number: {}, Searching ISBN: {}",
                    NAME, page + 1, searchingISBNMainGroup + isbnSeventhNumber);

            List<Book> books = getCrawledBooks();
            page++;

            if (books != null
                    && books.size() == 0
                    && isbnSeventhNumber <= ISBN_MAX_SEVENTH_NUMBER) {
                page = 0;
                isbnSeventhNumber++;
            }

            if (books != null
                    && books.size() == 0
                    && isbnSeventhNumber > ISBN_MAX_SEVENTH_NUMBER
                    && searchingISBNMainGroup.equals(ISBN963)) {
                page = 0;
                isbnSeventhNumber = 0;
                searchingISBNMainGroup = ISBN615;
            }

            if (books != null
                    && books.size() == 0
                    && isbnSeventhNumber > ISBN_MAX_SEVENTH_NUMBER
                    && searchingISBNMainGroup.equals(ISBN615)) {
                return null;
            }
            return books;
        } catch (Exception e) {
            log.error("Exception happened while crawling list book location! Page " + page
                    + " Searching ISBN: " + searchingISBNMainGroup + isbnSeventhNumber, e);
            page++;
            return getNextBooks();
        }
    }

    private List<Book> getCrawledBooks() {
        final Map<String, String> booksDetailsInformation = getBooksDetailsInformation(
                urlFactory.createISBNSearchingUrl(page, searchingISBNMainGroup + isbnSeventhNumber, PAGE_SIZE));

        return bookListCreator.getBookListFromBooksDetailsInformation(booksDetailsInformation);
    }

    private Map<String, String> getBooksDetailsInformation(String ISBNSearchingUrl) {
        try {
            final WebDocument bookListPage = webClient.sendGetRequest(ISBNSearchingUrl);

            return pageReader.getBookDetailsLinkInformation(bookListPage);
        } catch (WebClientException e) {
            throw new BookListDownloadException("Unable to download the list book page! Url: " + ISBNSearchingUrl, e);
        }
    }
}
