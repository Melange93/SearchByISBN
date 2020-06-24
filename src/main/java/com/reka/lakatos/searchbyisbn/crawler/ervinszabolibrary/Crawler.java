package com.reka.lakatos.searchbyisbn.crawler.ervinszabolibrary;

import com.reka.lakatos.searchbyisbn.crawler.BookCrawler;
import com.reka.lakatos.searchbyisbn.document.Book;
import com.reka.lakatos.searchbyisbn.exception.BookDownloadException;
import com.reka.lakatos.searchbyisbn.exception.BookListDownloadException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class Crawler implements BookCrawler {

    private final BookCreator bookCreator;
    private final URLFactory urlFactory;
    private final PageReader pageReader;

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
        final Map<String, String> pageBooksInformation = getSearchingBookListPageBooksInformation(
                urlFactory.createUrlForBookList(page, searchingISBNMainGroup + isbnSeventhNumber, PAGE_SIZE));

        return pageBooksInformation.entrySet().stream()
                .map(bookInformationEntry -> urlFactory.createBookDetailsUrl(bookInformationEntry.getKey(),
                        bookInformationEntry.getValue()))
                .map(this::visitBookDetailsUrl)
                .flatMap(Optional::stream)
                .collect(Collectors.toList());
    }

    private Optional<Book> visitBookDetailsUrl(final String bookDetailsUrl) {
        try {
            return getBook(bookDetailsUrl);
        } catch (Exception e) {
            log.error("Exception happened while crawling book location: " + bookDetailsUrl, e);

            return Optional.empty();
        }
    }

    private Map<String, String> getSearchingBookListPageBooksInformation(String url) {
        try {
            final Document bookListPage = Jsoup.connect(url).get();

            return pageReader.getBookDetailsLinkInformation(bookListPage);
        } catch (IOException e) {
            throw new BookListDownloadException("Unable to download the list book page! Url: " + url, e);
        }
    }

    private Optional<Book> getBook(String bookDetailsUrl) {
        try {
            Document bookPage = Jsoup.connect(bookDetailsUrl).get();
            return bookCreator.createBook(
                    pageReader.getBookInformation(bookPage),
                    PageReader.getSpecialSeparationCharacter());
        } catch (IOException e) {
            throw new BookDownloadException("Unable to download book page! Url: " + bookDetailsUrl, e);
        }
    }
}
