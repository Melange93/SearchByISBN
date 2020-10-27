package com.reka.lakatos.searchbyisbn.crawler.universitylibraries;

import com.reka.lakatos.searchbyisbn.crawler.defaultbookcreation.DefaultBookCreator;
import com.reka.lakatos.searchbyisbn.crawler.defaultbookcreation.DefaultBookListPreparatory;
import com.reka.lakatos.searchbyisbn.crawler.universitylibraries.webdocumentumfactory.WebDocumentFactory;
import com.reka.lakatos.searchbyisbn.document.Book;
import com.reka.lakatos.searchbyisbn.document.CoverType;
import com.reka.lakatos.searchbyisbn.webdocument.WebDocument;
import com.reka.lakatos.searchbyisbn.webdocument.WebElement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "crawler.book-crawler", havingValue = "universities")
public class BookListCreator {

    private final WebDocumentFactory documentFactory;
    private final DocumentReader documentReader;
    private final DefaultBookCreator defaultBookCreator;
    private final DefaultBookListPreparatory bookListPreparatory;

    public List<Book> createBookListDocumentType(WebDocument bookListPage, CoverType coverType) {
        List<String> searchingResultDetailLinks = documentReader.getSearchingResultDetailLinks(bookListPage);
        List<Book> books = searchingResultDetailLinks.stream()
                .map(this::getBook)
                .flatMap(Optional::stream)
                .collect(Collectors.toList());
        books.forEach(book -> book.setCoverType(coverType));
        return books;
    }

    private Optional<Book> getBook(String bookUrl) {
        try {
            WebDocument visitBook = documentFactory.visitBook(bookUrl);
            Map<String, String> bookProperties = createBookPropertiesMap(visitBook);
            return defaultBookCreator.createBook(bookProperties);
        } catch (Exception e) {
            log.error("Failed to create this book. Url: " + bookUrl + " " + e);
            return Optional.empty();
        }
    }

    private Map<String, String> createBookPropertiesMap(final WebDocument webDocument) {
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
                    bookPropertiesValues,
                    "span"
            );
        }
        return bookListPreparatory.createPropertiesMap(bookPropertiesName, bookPropertiesValues);
    }
}
