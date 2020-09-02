package com.reka.lakatos.searchbyisbn.crawler.universitylibraries;

import com.reka.lakatos.searchbyisbn.crawler.BookCrawler;
import com.reka.lakatos.searchbyisbn.crawler.universitylibraries.webdocumentumfactory.WebDocumentFactory;
import com.reka.lakatos.searchbyisbn.document.Book;
import com.reka.lakatos.searchbyisbn.document.CoverType;
import com.reka.lakatos.searchbyisbn.webdocument.WebDocument;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "crawler.book-crawler", havingValue = "universities")
public class Crawler implements BookCrawler {

    private final WebDocumentFactory documentFactory;
    private final DocumentReader documentReader;
    private final BookListCreator bookListCreator;
    private final List<String> documentType;
    private final Map<String, CoverType> coverTypeByDocumentType;

    //other possibilities: 12, 24, 48
    private static final int RESULTS_PER_PAGE = 48;

    private int documentTypeIndex = 0;
    private int pageNumber = 1;
    private String pAuthor;

    @Override
    public List<Book> getNextBooks() {
        if (documentType.size() - 1 < documentTypeIndex) {
            return null;
        }

        prepareSearching();
        WebDocument bookListPage = documentFactory.searchingByDocumentType(pAuthor, documentType.get(documentTypeIndex));
        int numberOfSearchingResult = documentReader.getNumberOfSearchingResult(bookListPage);
        CoverType coverType = coverTypeByDocumentType.get(documentType.get(documentTypeIndex));

        if (numberOfSearchingResult < RESULTS_PER_PAGE * pageNumber) {
            documentTypeIndex++;
            pageNumber = 1;
        }

        if (pageNumber == 1) {
            pageNumber++;
            return bookListCreator.createBookListDocumentType(bookListPage, coverType);
        }

        String furtherSearchingUrl = documentReader.getFurtherSearchingUrl(bookListPage, String.valueOf(pageNumber++), String.valueOf(RESULTS_PER_PAGE));
        WebDocument furtherSearching = documentFactory.furtherSearchingByDocumentType(furtherSearchingUrl);
        return bookListCreator.createBookListDocumentType(furtherSearching, coverType);
    }

    private void prepareSearching() {
        log.info("Prepare searching.");
        WebDocument webDocument = documentFactory.getMainPage();
        Optional<String> pAuthorCode = documentReader.getPAuthorCode(webDocument);
        if (pAuthorCode.isEmpty()) {
            throw new PAuthorCodeException("Can't find the Author code.");
        }
        pAuthor = pAuthorCode.get();
        documentFactory.navigateToComplexSearch();
    }
}
