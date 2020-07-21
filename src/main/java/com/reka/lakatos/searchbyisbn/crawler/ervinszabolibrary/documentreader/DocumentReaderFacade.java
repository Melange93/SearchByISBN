package com.reka.lakatos.searchbyisbn.crawler.ervinszabolibrary.documentreader;

import com.reka.lakatos.searchbyisbn.crawler.ervinszabolibrary.documentreader.facade.BookPropertiesPageReader;
import com.reka.lakatos.searchbyisbn.crawler.ervinszabolibrary.documentreader.facade.BooksPageReader;
import com.reka.lakatos.searchbyisbn.webdocument.WebDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class DocumentReaderFacade {

    private final BooksPageReader booksPageReader;
    private final BookPropertiesPageReader bookPropertiesPageReader;

    public Map<String, String> getInformationForBookPropertiesPage(WebDocument booksPage) {
        return booksPageReader.getInformation(booksPage);
    }

    public Map<String, String> getBookProperties(WebDocument bookPropertiesPage) {
        return bookPropertiesPageReader.getBookProperties(bookPropertiesPage);
    }


}
