package com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary;

import com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.bookcreation.SzechenyiBookCreator;
import com.reka.lakatos.searchbyisbn.document.Book;
import com.reka.lakatos.searchbyisbn.webdocument.WebDocument;
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
    private final SzechenyiBookCreator bookCreator;

    public List<Book> getCrawledBooks(final WebDocument webDocument) {

        List<String> bookEditionsPageLinks = documentReader.getBookEditionsPageLinks(webDocument);

        List<String> allBooksEditionsLink = getAllBooksEditionsLink(bookEditionsPageLinks);
        for (String visitUrl : allBooksEditionsLink) {
            WebDocument webDocument1 = documentFactory.visitBook(visitUrl);
            Map<String, String> bookPropertiesMap = createBookPropertiesMap(webDocument1);
            Optional<Book> book = bookCreator.createBook(bookPropertiesMap);
            System.out.println(book);
        }

        return null;
    }

    private List<String> getAllBooksEditionsLink(List<String> bookEditionsPageLinks) {
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
    }

    private List<String> getBookEditionsLink(final String allEditionsPageUrl) {
        final WebDocument webDocument = documentFactory.visitBookEditionsLink(allEditionsPageUrl);
        return documentReader.getEditionsLink(webDocument);
    }

    private Map<String, String> createBookPropertiesMap(final WebDocument webDocument) {
        final List<String> bookPropertiesName = documentReader.getBookPropertiesName(webDocument);
        final List<String> bookPropertiesValue = documentReader.getBookPropertiesValue(webDocument);

        return IntStream.range(0, bookPropertiesValue.size())
                .boxed()
                .collect(
                        Collectors.toMap(
                                bookPropertiesName::get,
                                bookPropertiesValue::get
                        )
                );
    }
}
