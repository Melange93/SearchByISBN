package com.reka.lakatos.booksofhungary.crawlers.crawlers.szechenyilibrary;

import com.reka.lakatos.booksofhungary.crawlers.crawlers.defaultbookcreation.DefaultBookCreator;
import com.reka.lakatos.booksofhungary.crawlers.crawlers.defaultbookcreation.DefaultBookListPreparatory;
import com.reka.lakatos.booksofhungary.crawlers.crawlers.szechenyilibrary.exception.BookIdException;
import com.reka.lakatos.booksofhungary.crawlers.crawlers.szechenyilibrary.factory.WebDocumentFactory;
import com.reka.lakatos.booksofhungary.crawlers.domain.database.Book;
import com.reka.lakatos.booksofhungary.crawlers.webdocument.domain.WebDocument;
import com.reka.lakatos.booksofhungary.crawlers.webdocument.domain.WebElement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(name = "crawler.book-crawler", havingValue = "szechenyi")
public class BookListCreator {

    private final DocumentReader documentReader;
    private final WebDocumentFactory documentFactory;
    private final DefaultBookCreator bookCreator;
    private final DefaultBookListPreparatory bookListPreparatory;

    public List<Book> getCrawledBooks(final WebDocument webDocument) {
        List<String> bookEditionsPageLinks = documentReader.getBookEditionsPageLinks(webDocument);

        return bookEditionsPageLinks.stream()
                .map(this::getAllBooksEditionsLink)
                .flatMap(List::stream)
                .map(this::getBook)
                .flatMap(Optional::stream)
                .collect(Collectors.toList());
    }

    private Optional<Book> getBook(String bookUrl) {
        try {
            WebDocument bookPage = documentFactory.visitBook(bookUrl);
            Optional<Map<String, String>> bookPropertiesMap = createBookPropertiesMap(bookPage);

            if (bookPropertiesMap.isPresent()) {
                return bookCreator.createBook(bookPropertiesMap.get());
            }
            return Optional.empty();
        } catch (Exception e) {
            log.error(String.valueOf(e));
            return Optional.empty();
        }
    }

    private List<String> getAllBooksEditionsLink(String bookEditionsPageLink) {
        try {
            Thread.sleep(1000L);
            final WebDocument webDocument = documentFactory.visitBookEditionsLink(bookEditionsPageLink);
            return documentReader.getEditionsLink(webDocument);
        } catch (InterruptedException e) {
            log.error("Failed to wait 1000 ms.");
            return new ArrayList<>();
        }
    }

    private Optional<Map<String, String>> createBookPropertiesMap(final WebDocument webDocument) {
        if (hasRelatedBooks(webDocument)) {
            return Optional.empty();
        }
        final List<WebElement> propertiesNameWebElements = documentReader.getBookPropertiesName(webDocument);
        final List<WebElement> propertiesValuesWebElement = documentReader.getBookPropertiesValue(webDocument);
        final List<String> propertiesName = bookListPreparatory.prepareBookProperties(propertiesNameWebElements);
        List<String> propertiesValues = bookListPreparatory.prepareBookProperties(propertiesValuesWebElement);

        if (bookListPreparatory.hasContributors(propertiesName)) {
            bookListPreparatory.setContributors(propertiesName, propertiesValuesWebElement, propertiesValues, "a");
        }
        Map<String, String> propertiesMap = bookListPreparatory.createPropertiesMap(propertiesName, propertiesValues);
        return Optional.of(propertiesMap);
    }

    private boolean hasRelatedBooks(WebDocument webDocument) {
        Optional<String> bookAmicusId = documentReader.getBookAmicusId(webDocument);
        if (bookAmicusId.isEmpty()) {
            throw new BookIdException("Failed to get the book id.");
        }
        WebDocument relatedBooksPage = documentFactory.getRelatedBooksPage(bookAmicusId.get());
        return documentReader.hasRelatedBooks(relatedBooksPage);
    }
}
