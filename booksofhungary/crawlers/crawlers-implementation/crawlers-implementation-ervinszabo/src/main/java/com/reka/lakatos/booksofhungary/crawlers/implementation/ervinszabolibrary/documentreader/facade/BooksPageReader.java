package com.reka.lakatos.booksofhungary.crawlers.implementation.ervinszabolibrary.documentreader.facade;

import org.springframework.stereotype.Service;
import com.reka.lakatos.booksofhungary.crawlers.webdocument.domain.WebDocument;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class BooksPageReader {

    public Map<String, String> getBookPropertiesUrlInformation(WebDocument booksPage) {
        String searchLineNumberAndBookId = "(\\d+,\\d+)";
        String linesContainingInformationCssQuery = ".short_item_back script";
        int lineNumber = 0;
        int bookId = 1;

        return booksPage
                .select(linesContainingInformationCssQuery)
                .stream()
                .map(webElement ->
                        Pattern
                                .compile(searchLineNumberAndBookId)
                                .matcher(webElement.toString()))
                .filter(Matcher::find)
                .map(matcher -> matcher.group().split(","))
                .collect(Collectors.toMap(
                        information -> information[lineNumber],
                        information -> information[bookId]));
    }

}
