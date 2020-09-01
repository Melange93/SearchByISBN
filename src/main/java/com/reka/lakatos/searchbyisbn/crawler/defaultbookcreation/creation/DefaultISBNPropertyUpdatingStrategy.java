package com.reka.lakatos.searchbyisbn.crawler.defaultbookcreation.creation;

import com.reka.lakatos.searchbyisbn.document.Book;
import com.reka.lakatos.searchbyisbn.document.CoverType;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DefaultISBNPropertyUpdatingStrategy implements PropertyUpdatingStrategy {

    private static final String ISBN13_REGEX = "((?:[\\dX]{13})|(?:[\\d\\-\\sX]{17}))";
    private static final String ISBN10_REGEX = "((?:[\\dX]{10})|(?:[\\d\\-\\sX]{13}))";

    @Override
    public void updateProperty(Book book, String property) {
        setISBN(property, book);
        setBasicCoverType(property, book);
    }

    private void setISBN(String preISBN, Book book) {
        Pattern isbn13Pattern = Pattern.compile(ISBN13_REGEX);
        Matcher isbn13Matcher = isbn13Pattern.matcher(preISBN);
        if (isbn13Matcher.find()) {
            book.setIsbn(isbn13Matcher.group());
            return;
        }

        Pattern isbn10Pattern = Pattern.compile(ISBN10_REGEX);
        Matcher isbn10Matcher = isbn10Pattern.matcher(preISBN);
        if (isbn10Matcher.find()) {
            book.setIsbn(isbn10Matcher.group());
        }
    }

    private void setBasicCoverType(String value, Book book) {
        if (book.getCoverType() != null) {
            return;
        }

        Pattern coverTypePattern = Pattern.compile("([^0-9\\s\\(\\)\\:\\-]*)");
        Matcher matcher = coverTypePattern.matcher(value);
        while (matcher.find()) {
            if (!matcher.group().trim().isBlank()) {
                String result = matcher.group().trim();

                CoverType.findTypeByName(result)
                        .ifPresent(book::setCoverType);
            }
        }
    }
}
