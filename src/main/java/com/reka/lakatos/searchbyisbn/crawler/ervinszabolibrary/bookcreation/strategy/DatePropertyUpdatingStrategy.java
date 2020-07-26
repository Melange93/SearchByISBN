package com.reka.lakatos.searchbyisbn.crawler.ervinszabolibrary.bookcreation.strategy;

import com.reka.lakatos.searchbyisbn.document.Book;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DatePropertyUpdatingStrategy implements PropertyUpdatingStrategy {

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

        if (isValidDate(matcher) && matcher.find()) {
            book.getEditions().get(INDEX_OF_BASIC_EDITION).setYearOfRelease(Integer.parseInt(matcher.group()));
        }
    }

    private boolean isValidDate(Matcher matcher) {
        int counter = 0;
        while (matcher.find()) {
            counter++;
        }
        matcher.reset();
        return counter == 1;
    }
}
