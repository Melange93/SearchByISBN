package com.reka.lakatos.searchbyisbn.crawler.defaultbookcreation.creation;

import com.reka.lakatos.searchbyisbn.document.Book;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DefaultDatePropertyUpdatingStrategy implements PropertyUpdatingStrategy {

    private static final int INDEX_OF_BASIC_EDITION = 0;

    @Override
    public void updateProperty(Book book, String property) {
        setEditionNumber(book, property);
        setDate(book, property);
    }

    private void setEditionNumber(Book book, String property) {
        Matcher matcher = Pattern.compile("[\\d]+(?=\\.\\skiad\\.)").matcher(property);
        if (matcher.find()) {
            String editionNumber = matcher.group().trim();
            book.getEditions().get(INDEX_OF_BASIC_EDITION)
                    .setEditionNumber(Integer.parseInt(editionNumber));
        }
    }

    private void setDate(Book book, String property) {
        String dateRegex= "[\\d]{4}";
        Matcher matcher = Pattern.compile(dateRegex).matcher(property);

        if (matcher.results().count() == 1 && matcher.find()) {
            book.getEditions().get(INDEX_OF_BASIC_EDITION).setYearOfRelease(Integer.parseInt(matcher.group()));
        }
    }
}
