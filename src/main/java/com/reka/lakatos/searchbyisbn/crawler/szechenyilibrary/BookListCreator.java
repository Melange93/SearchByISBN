package com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary;

import com.reka.lakatos.searchbyisbn.crawler.bookcreation.DefaultBookCreator;
import com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.exception.BookEditionsPageException;
import com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.exception.BookIdException;
import com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.exception.RelatedBookException;
import com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.exception.VisitBookException;
import com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.factory.WebDocumentFactory;
import com.reka.lakatos.searchbyisbn.document.Book;
import com.reka.lakatos.searchbyisbn.webdocument.WebDocument;
import com.reka.lakatos.searchbyisbn.webdocument.WebElement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(name = "crawler.book-crawler", havingValue = "szechenyi")
public class BookListCreator {

    private final DocumentReader documentReader;
    private final WebDocumentFactory documentFactory;
    private final DefaultBookCreator bookCreator;
    private final BookListPreparatory bookListPreparatory;

    public List<Book> getCrawledBooks(final WebDocument webDocument) {
        List<Book> books = new ArrayList<>();

        List<String> bookEditionsPageLinks = documentReader.getBookEditionsPageLinks(webDocument);
        for (String edition : bookEditionsPageLinks) {
            List<String> allBooksEditionsLink = getAllBooksEditionsLink(edition);
            for (String url : allBooksEditionsLink) {
                try {
                    WebDocument webDocument1 = documentFactory.visitBook(url);
                    Optional<Map<String, String>> bookPropertiesMap = createBookPropertiesMap(webDocument1);
                    bookPropertiesMap.ifPresent(bookProperties -> {
                        Optional<Book> book = bookCreator.createBook(bookProperties);
                        book.ifPresent(books::add);
                    });
                } catch (VisitBookException e) {
                    log.error(String.valueOf(e));
                }
            }
        }
        return books;
    }

    private List<String> getAllBooksEditionsLink(String bookEditionsPageLink) {
        List<String> booksEditionsLink = new ArrayList<>();
        try {
            Thread.sleep(1000L);
            List<String> bookEditionsLink = getBookEditionsLink(bookEditionsPageLink);
            booksEditionsLink.addAll(bookEditionsLink);
        } catch (InterruptedException e) {
            log.error("Failed to wait 1000 ms.");
            return booksEditionsLink;
        } catch (BookEditionsPageException e) {
            log.error(String.valueOf(e));
            return booksEditionsLink;
        }
        return booksEditionsLink;
    }

    private List<String> getBookEditionsLink(final String allEditionsPageUrl) {
        final WebDocument webDocument = documentFactory.visitBookEditionsLink(allEditionsPageUrl);
        return documentReader.getEditionsLink(webDocument);
    }

    private Optional<Map<String, String>> createBookPropertiesMap(final WebDocument webDocument) {
        try {
            if (hasRelatedBooks(webDocument)) {
                return Optional.empty();
            }
        } catch (BookIdException | RelatedBookException e) {
            log.error(String.valueOf(e));
            return Optional.empty();
        }

        final List<String> bookPropertiesName =
                bookListPreparatory.prepareBookProperties(documentReader.getBookPropertiesName(webDocument));
        final List<WebElement> bookPropertiesValuesWebElement =
                documentReader.getBookPropertiesValue(webDocument);
        List<String> bookPropertiesValues =
                bookListPreparatory.prepareBookProperties(bookPropertiesValuesWebElement);

        if (bookListPreparatory.hasContributors(bookPropertiesName)) {
            bookListPreparatory.setContributors(
                    bookPropertiesName,
                    bookPropertiesValuesWebElement,
                    bookPropertiesValues
            );
        }

        return Optional.of(bookListPreparatory.createPropertiesMap(bookPropertiesName, bookPropertiesValues));
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
