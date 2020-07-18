package com.reka.lakatos.searchbyisbn.crawler.ervinszabolibrary;

import com.reka.lakatos.searchbyisbn.document.Book;
import com.reka.lakatos.searchbyisbn.exception.BookDownloadException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookListCreator {

    private final BookCreator bookCreator;
    private final URLFactory urlFactory;
    private final PageReader pageReader;

    public List<Book> getBookListFromBooksDetailsInformation(Map<String, String> booksDetailsInformation) {
        return booksDetailsInformation.entrySet().stream()
                .map(bookInformationEntry -> urlFactory.createBookDetailsUrl(bookInformationEntry.getKey(),
                        bookInformationEntry.getValue()))
                .map(this::visitBookDetailsUrl)
                .flatMap(Optional::stream)
                .collect(Collectors.toList());
    }

    private Optional<Book> visitBookDetailsUrl(final String bookDetailsUrl) {
        try {
            log.info("Visit this book: {}", bookDetailsUrl);
            return getBook(bookDetailsUrl);
        } catch (Exception e) {
            log.error("Exception happened while crawling book location: " + bookDetailsUrl, e);

            return Optional.empty();
        }
    }

    private Optional<Book> getBook(String bookDetailsUrl) {
        try {
            Document bookPage = Jsoup.connect(bookDetailsUrl).get();
            return bookCreator.createBook(
                    pageReader.getBookInformation(bookPage));
        } catch (IOException e) {
            throw new BookDownloadException("Unable to download book page! Url: " + bookDetailsUrl, e);
        }
    }

}
