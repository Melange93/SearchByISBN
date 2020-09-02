package com.reka.lakatos.searchbyisbn.crawler.defaultbookcreation.creation;

import com.reka.lakatos.searchbyisbn.document.Book;
import com.reka.lakatos.searchbyisbn.document.CoverType;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DefaultISBNPropertyUpdatingStrategy implements PropertyUpdatingStrategy {

    private static final String ISBN10_AND_ISBN13 =
            "(\\d{1,3}([- ])\\d{1,5}\\2\\d{1,7}\\2\\d{1,6}\\2(\\d|X))" +
            "|(\\d{1,5}([- ])\\d{1,7}\\5\\d{1,6}\\5(\\d|X))" +
            "|(\\d{13})" +
            "|(\\d{10})";

    @Override
    public void updateProperty(Book book, String property) {
        setISBN(book, property);
        setBasicCoverType(property, book);
    }

    private void setISBN(Book book, String property) {
        Optional<String> isbn = findISBN(property);
        isbn.ifPresent(book::setIsbn);
    }

    private Optional<String> findISBN(final String property) {
        Matcher isbnMatcher = Pattern.compile(ISBN10_AND_ISBN13).matcher(property);
        if (isbnMatcher.find()) {
            String result = isbnMatcher.group();
            return Optional.of(result);
        }
        return Optional.empty();
    }

    private void setBasicCoverType(final String value, Book book) {
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
