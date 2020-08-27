package com.reka.lakatos.searchbyisbn.crawler.universitylibralies;

import com.reka.lakatos.searchbyisbn.crawler.BookCrawler;
import com.reka.lakatos.searchbyisbn.crawler.universitylibralies.webdocumentumfactory.WebDocumentFactory;
import com.reka.lakatos.searchbyisbn.document.Book;
import com.reka.lakatos.searchbyisbn.webdocument.WebDocument;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "crawler.book-crawler", havingValue = "universities")
public class Crawler implements BookCrawler {

    private final WebDocumentFactory documentFactory;
    private final DocumentReader documentReader;
    private final BookListCreator bookListCreator;
    private final List<String> documentType = new ArrayList<>(Arrays.asList("book", "edocument", "map"));

    private int pageNumber = 2;
    private String pAuthor;

    @Override
    public List<Book> getNextBooks() {
        prepareSearching();
        WebDocument bookListPage = documentFactory.searchingByDocumentType(pAuthor, documentType.get(0));
        if (pageNumber == 1) {
            pageNumber++;
            return bookListCreator.createBookListDocumentType(bookListPage);
        }

        String furtherSearchingUrl = documentReader.getFurtherSearchingUrl(bookListPage);
        String furtherSearchingUrlWithPageNumber = furtherSearchingUrl.replace("page_placeholder", String.valueOf(pageNumber++));
        WebDocument furtherSearching = documentFactory.furtherSearchingByDocumentType(furtherSearchingUrlWithPageNumber);
        System.out.println(furtherSearching);
        return bookListCreator.createBookListDocumentType(furtherSearching);
    }

    private void prepareSearching() {
        log.info("Prepare searching.");
        WebDocument webDocument = documentFactory.getMainPage();
        Optional<String> pAuthorCode = documentReader.getPAuthorCode(webDocument);
        if (pAuthorCode.isEmpty()) {
            // TODO: 2020. 08. 25. Create a unique exception
            throw new RuntimeException("Can't find the Author code.");
        }
        pAuthor = pAuthorCode.get();
        documentFactory.navigateToComplexSearch();
    }
}
