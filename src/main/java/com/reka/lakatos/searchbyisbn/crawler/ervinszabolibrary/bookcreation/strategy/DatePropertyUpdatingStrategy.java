package com.reka.lakatos.searchbyisbn.crawler.ervinszabolibrary.bookcreation.strategy;

import com.reka.lakatos.searchbyisbn.document.Book;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DatePropertyUpdatingStrategy implements PropertyUpdatingStrategy {

    @Override
    public void updateProperty(Book book, String property) {
        setDate(book, property);
    }

    private void setDate(Book book, String property) {
        String dateRegex= "[\\d]{4}";
        Matcher matcher = Pattern.compile(dateRegex).matcher(property);

        if (isValidDate(matcher) && matcher.find()) {
            book.setYearOfRelease(matcher.group());
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
