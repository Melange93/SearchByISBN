package com.reka.lakatos.booksofhungary.crawlers.implementation.ervinszabolibrary;

import com.reka.lakatos.booksofhungary.crawlers.implementation.bookcrationlogic.defaultbookcreation.DefaultBookCreator;
import com.reka.lakatos.booksofhungary.crawlers.domain.database.Book;
import com.reka.lakatos.booksofhungary.crawlers.implementation.ervinszabolibrary.documentreader.DocumentReaderFacade;
import com.reka.lakatos.booksofhungary.crawlers.implementation.ervinszabolibrary.exception.BookDownloadException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.reka.lakatos.booksofhungary.crawlers.webdocument.domain.WebClient;
import com.reka.lakatos.booksofhungary.crawlers.webdocument.domain.WebDocument;
import com.reka.lakatos.booksofhungary.crawlers.webdocument.exception.WebClientException;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CrawledBooksCreator {

    private final DefaultBookCreator bookCreator;
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
