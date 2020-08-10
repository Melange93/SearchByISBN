package com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary;

import com.reka.lakatos.searchbyisbn.crawler.bookcreation.DefaultBookCreator;
import com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.factory.WebDocumentFactory;
import com.reka.lakatos.searchbyisbn.document.Book;
import com.reka.lakatos.searchbyisbn.webdocument.WebDocument;
import com.reka.lakatos.searchbyisbn.webdocument.WebElement;
import com.reka.lakatos.searchbyisbn.webdocument.exception.WebClientException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookListCreator {

    private final DocumentReader documentReader;
    private final WebDocumentFactory documentFactory;
    private final DefaultBookCreator bookCreator;

    private static final String SPECIAL_CASE_CONTRIBUTORS = "Név/nevek:";
    private static final String SPECIAL_SEPARATION_CHARACTER = "$";

    public List<Book> getCrawledBooks(final WebDocument webDocument) {
        List<Book> books = new ArrayList<>();

        List<String> bookEditionsPageLinks = documentReader.getBookEditionsPageLinks(webDocument);
        for (String edition : bookEditionsPageLinks) {
            List<String> allBooksEditionsLink = getAllBooksEditionsLink(edition);
            for (String url : allBooksEditionsLink) {
                WebDocument webDocument1 = documentFactory.visitBook(url);
                Optional<Map<String, String>> bookPropertiesMap = createBookPropertiesMap(webDocument1);
                bookPropertiesMap.ifPresent(bookProperties -> {
                    Optional<Book> book = bookCreator.createBook(bookProperties);
                    book.ifPresent(books::add);
                });
            }
        }
        return books;

        /*
        return getAllBooksEditionsLink(bookEditionsPageLinks).stream()
                .map(documentFactory::visitBook)
                .map(this::createBookPropertiesMap)
                .map(bookCreator::createBook)
                .flatMap(Optional::stream)
                .collect(Collectors.toList());

         */
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
        } catch (WebClientException e) {
            // TODO: 2020. 08. 04. create unique exception
            throw new RuntimeException("Failed to get editions page link", e);
        }
        return booksEditionsLink;

        /*
        List<String> booksEditionsLink = new ArrayList<>();
        try {
            for (String url : bookEditionsPageLinks) {
                Thread.sleep(1000L);
                List<String> bookEditionsLink = getBookEditionsLink(url);
                booksEditionsLink.addAll(bookEditionsLink);
            }
        } catch (InterruptedException e) {
            log.error("Failed to wait 1000 ms.");
            return booksEditionsLink;
        } catch (WebClientException e) {
            // TODO: 2020. 08. 04. create unique exception
            throw new RuntimeException("Failed to get editions page link", e);
        }
        return booksEditionsLink;

         */
    }

    private List<String> getBookEditionsLink(final String allEditionsPageUrl) {
        final WebDocument webDocument = documentFactory.visitBookEditionsLink(allEditionsPageUrl);
        return documentReader.getEditionsLink(webDocument);
    }

    private Optional<Map<String, String>> createBookPropertiesMap(final WebDocument webDocument) {
        if (hasRelatedBooks(webDocument)) {
            return Optional.empty();
        }

        final List<String> bookPropertiesName = documentReader
                .getBookPropertiesName(webDocument)
                .stream()
                .map(WebElement::text)
                .collect(Collectors.toList());

        final List<WebElement> bookPropertiesValuesWebElement = documentReader.getBookPropertiesValue(webDocument);
        List<String> bookPropertiesValues = bookPropertiesValuesWebElement
                .stream()
                .map(WebElement::text)
                .collect(Collectors.toList());

        if (hasContributors(bookPropertiesName)) {
            int contributorsIndex = bookPropertiesName.indexOf(SPECIAL_CASE_CONTRIBUTORS);
            String contributorsSeparateBySpecialCharacter =
                    getContributorsSeparateBySpecialCharacter(bookPropertiesValuesWebElement, contributorsIndex);
            bookPropertiesValues.set(contributorsIndex, contributorsSeparateBySpecialCharacter);
        }

        return Optional.of(createPropertiesMap(bookPropertiesName, bookPropertiesValues));
    }

    private boolean hasRelatedBooks(WebDocument webDocument) {
        Optional<String> bookAmicusId = documentReader.getBookAmicusId(webDocument);
        if (bookAmicusId.isEmpty()) {
            throw new RuntimeException("Failed to get the book id.");
        }
        WebDocument relatedBooksPage = documentFactory.getRelatedBooksPage(bookAmicusId.get());
        return documentReader.hasRelatedBooks(relatedBooksPage);
    }

    private Map<String, String> createPropertiesMap(List<String> bookPropertiesName, List<String> bookPropertiesValues) {
        return IntStream.range(0, bookPropertiesValues.size())
                .boxed()
                .collect(
                        Collectors.toMap(
                                bookPropertiesName::get,
                                bookPropertiesValues::get
                        )
                );
    }

    private String getContributorsSeparateBySpecialCharacter(List<WebElement> bookPropertiesValuesWebElement, int contributorsIndex) {
        return bookPropertiesValuesWebElement.get(contributorsIndex)
                .select("a")
                .stream()
                .map(WebElement::text)
                .collect(Collectors.joining(SPECIAL_SEPARATION_CHARACTER));
    }

    private boolean hasContributors(List<String> bookPropertiesKey) {
        return bookPropertiesKey.contains(SPECIAL_CASE_CONTRIBUTORS);
    }
}
