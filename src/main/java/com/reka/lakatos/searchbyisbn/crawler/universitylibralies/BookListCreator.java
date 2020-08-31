package com.reka.lakatos.searchbyisbn.crawler.universitylibralies;

import com.reka.lakatos.searchbyisbn.crawler.bookcreation.DefaultBookCreator;
import com.reka.lakatos.searchbyisbn.crawler.bookcreation.DefaultBookListPreparatory;
import com.reka.lakatos.searchbyisbn.crawler.universitylibralies.webdocumentumfactory.WebDocumentFactory;
import com.reka.lakatos.searchbyisbn.document.Book;
import com.reka.lakatos.searchbyisbn.document.CoverType;
import com.reka.lakatos.searchbyisbn.webdocument.WebDocument;
import com.reka.lakatos.searchbyisbn.webdocument.WebElement;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
                .map(documentFactory::visitBook)
                .map(this::createBookPropertiesMap)
                .flatMap(Optional::stream)
                .map(defaultBookCreator::createBook)
                .flatMap(Optional::stream)
                .collect(Collectors.toList());
        books.forEach(book -> book.setCoverType(coverType));
        return books;
    }

    private Optional<Map<String, String>> createBookPropertiesMap(final WebDocument webDocument) {
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
        return Optional.of(bookListPreparatory.createPropertiesMap(bookPropertiesName, bookPropertiesValues));
    }

}
