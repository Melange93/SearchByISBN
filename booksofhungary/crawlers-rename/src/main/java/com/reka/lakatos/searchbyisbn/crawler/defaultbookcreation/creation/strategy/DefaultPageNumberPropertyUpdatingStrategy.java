package com.reka.lakatos.searchbyisbn.crawler.defaultbookcreation.creation.strategy;

import com.reka.lakatos.searchbyisbn.crawler.defaultbookcreation.creation.PropertyUpdatingStrategy;
import com.reka.lakatos.searchbyisbn.document.Book;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DefaultPageNumberPropertyUpdatingStrategy implements PropertyUpdatingStrategy {

    private static final int INDEX_OF_BASIC_EDITION = 0;
    private static final String PAGE_NUMBER_REGEX =
            "([0-9]*?(?=\\s*?p)[\\.]?)|([0-9]*?(?=[\\[\\s,]*?[0-9][\\]\\s]*?\\s*?p)[\\.]?)";

    @Override
    public void updateProperty(Book book, String property) {
        setPageNumber(book, property);
    }

    private void setPageNumber(Book book, String property) {
        Matcher matcher = Pattern.compile(PAGE_NUMBER_REGEX).matcher(property);
        int pageCounter = 0;
        while (matcher.find()) {
            if (!matcher.group().isBlank()) {
                pageCounter += Integer.parseInt(matcher.group());
            }
        }
        book.getEditions().get(INDEX_OF_BASIC_EDITION).setPageNumber(pageCounter);
    }
}
