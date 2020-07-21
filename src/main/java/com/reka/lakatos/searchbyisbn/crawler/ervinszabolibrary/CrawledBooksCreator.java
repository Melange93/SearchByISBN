package com.reka.lakatos.searchbyisbn.crawler.ervinszabolibrary;

import com.reka.lakatos.searchbyisbn.crawler.ervinszabolibrary.bookcreation.BookCreator;
import com.reka.lakatos.searchbyisbn.crawler.ervinszabolibrary.documentreader.DocumentReaderFacade;
import com.reka.lakatos.searchbyisbn.document.Book;
import com.reka.lakatos.searchbyisbn.exception.BookDownloadException;
import com.reka.lakatos.searchbyisbn.webdocument.WebClient;
import com.reka.lakatos.searchbyisbn.webdocument.WebDocument;
import com.reka.lakatos.searchbyisbn.webdocument.exception.WebClientException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CrawledBooksCreator {

    private final BookCreator bookCreator;
    private final URLFactory urlFactory;
    private final DocumentReaderFacade documentReaderFacade;
    private final WebClient webClient;

    public List<Book> getCrawledBooks(Map<String, String> booksPropertiesPageInformation) {
        return booksPropertiesPageInformation
                .entrySet()
                .stream()
                .map(bookPropertiesPageUrl ->
                        urlFactory.createBookPropertiesUrl(
                                bookPropertiesPageUrl.getKey(),
                                bookPropertiesPageUrl.getValue()
                        )
                )
                .map(this::visitBookPropertiesPage)
                .flatMap(Optional::stream)
                .collect(Collectors.toList());
    }

    private Optional<Book> visitBookPropertiesPage(final String bookPropertiesPageUrl) {
        try {
            log.info("Visit this book: {}", bookPropertiesPageUrl);
            return getBook(bookPropertiesPageUrl);
        } catch (Exception e) {
            log.error("Exception happened while crawling book location: " + bookPropertiesPageUrl, e);

            return Optional.empty();
        }
    }

    private Optional<Book> getBook(String bookPropertiesPageUrl) {
        try {
            WebDocument bookPage = webClient.sendGetRequest(bookPropertiesPageUrl);
            Map<String, String> bookProperties = documentReaderFacade.getBookProperties(bookPage);
            return bookCreator.createBook(bookProperties);
        } catch (WebClientException e) {
            throw new BookDownloadException("Unable to download book page! Url: " + bookPropertiesPageUrl, e);
        }
    }

}
