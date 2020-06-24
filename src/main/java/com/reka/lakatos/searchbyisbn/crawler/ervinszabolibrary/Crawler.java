package com.reka.lakatos.searchbyisbn.crawler.ervinszabolibrary;

import com.reka.lakatos.searchbyisbn.crawler.BookCrawler;
import com.reka.lakatos.searchbyisbn.document.Book;
import com.reka.lakatos.searchbyisbn.exception.BookListDownloadException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class Crawler implements BookCrawler {

    private final URLFactory urlFactory;
    private final PageReader pageReader;
    private final BookListCreator bookListCreator;

    private static final String ISBN963 = "978963";
    private static final String ISBN615 = "978615";
    private static final String PAGE_SIZE = "10";

    private int page = 0;
    private int isbnSeventhNumber = 0;
    private String searchingISBNMainGroup = ISBN963;

    @Override
    public List<Book> getNextBooks() {
        log.info("Start crawling: " + getClass().getSimpleName());
        List<Book> books = getCrawledBooks();
        page++;

        if (books.size() == 0 && isbnSeventhNumber <= 9) {
            page = 0;
            isbnSeventhNumber++;
        }

        if (books.size() == 0 && isbnSeventhNumber > 9 && searchingISBNMainGroup.equals(ISBN963)) {
            page = 0;
            isbnSeventhNumber = 0;
            searchingISBNMainGroup = ISBN615;
        }
        return books;
    }

    private List<Book> getCrawledBooks() {
        final Map<String, String> booksDetailsInformation = getBooksDetailsInformation(
                urlFactory.createISBNSearchingUrl(page, searchingISBNMainGroup + isbnSeventhNumber, PAGE_SIZE));

        return bookListCreator.getBookListFromBooksDetailsInformation(booksDetailsInformation);
    }

    private Map<String, String> getBooksDetailsInformation(String ISBNSearchingUrl) {
        try {
            final Document bookListPage = Jsoup.connect(ISBNSearchingUrl).get();

            return pageReader.getBookDetailsLinkInformation(bookListPage);
        } catch (IOException e) {
            throw new BookListDownloadException("Unable to download the list book page! Url: " + ISBNSearchingUrl, e);
        }
    }
}
