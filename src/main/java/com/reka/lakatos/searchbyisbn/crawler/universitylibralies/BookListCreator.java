package com.reka.lakatos.searchbyisbn.crawler.universitylibralies;

import com.reka.lakatos.searchbyisbn.crawler.universitylibralies.webdocumentumfactory.WebDocumentFactory;
import com.reka.lakatos.searchbyisbn.document.Book;
import com.reka.lakatos.searchbyisbn.webdocument.WebDocument;
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

    public List<Book> createBookList(String isbn) {
        WebDocument webDocument = documentFactory.getMainPage();
        Optional<String> pAuthorCode = documentReader.getPAuthorCode(webDocument);
        if (pAuthorCode.isEmpty()) {
            // TODO: 2020. 08. 25. Create a unique exception
            throw new RuntimeException("Can't find the Author code.");
        }

        documentFactory.navigateToComplexSearch();
        WebDocument searchResult = documentFactory.searchingByISBN(pAuthorCode.get(), isbn);
        List<String> searchingResultDetailLinks = documentReader.getSearchingResultDetailLinks(searchResult);
        List<Map<String, String>> collect = searchingResultDetailLinks.stream()
                .map(documentFactory::visitBook)
                .map(documentReader::getBookProperties)
                .collect(Collectors.toList());
        System.out.println(collect);
        return null;
    }

}
