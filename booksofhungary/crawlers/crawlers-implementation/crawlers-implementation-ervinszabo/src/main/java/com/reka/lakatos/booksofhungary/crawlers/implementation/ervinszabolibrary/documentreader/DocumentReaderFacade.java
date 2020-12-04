package com.reka.lakatos.booksofhungary.crawlers.implementation.ervinszabolibrary.documentreader;

import com.reka.lakatos.booksofhungary.crawlers.implementation.ervinszabolibrary.documentreader.facade.BookPropertiesPageReader;
import com.reka.lakatos.booksofhungary.crawlers.implementation.ervinszabolibrary.documentreader.facade.BooksPageReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.reka.lakatos.booksofhungary.crawlers.webdocument.domain.WebDocument;
import com.reka.lakatos.booksofhungary.crawlers.webdocument.domain.WebElement;

import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class DocumentReaderFacade {

    private final BooksPageReader booksPageReader;
    private final BookPropertiesPageReader bookPropertiesPageReader;

    public Map<String, String> getInformationForBookPropertiesPage(WebDocument booksPage) {
        return booksPageReader.getBookPropertiesUrlInformation(booksPage);
    }

    public Map<String, String> getBookProperties(WebDocument bookPropertiesPage) {
        return bookPropertiesPageReader.getBookProperties(bookPropertiesPage);
    }

    public int getResultNumber(WebDocument bookListPage) {
        Optional<WebElement> searchingResult = bookListPage.select(".result-size").stream().findFirst();
        if (searchingResult.isPresent()) {
            Matcher matcher = Pattern.compile("\\d+").matcher(searchingResult.get().text());
            if (matcher.find()) {
                return Integer.parseInt(matcher.group());
            }
        }
        return 0;
    }

}
