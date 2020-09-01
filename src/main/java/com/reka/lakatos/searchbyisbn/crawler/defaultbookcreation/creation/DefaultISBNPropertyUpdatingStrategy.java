package com.reka.lakatos.searchbyisbn.crawler.defaultbookcreation.creation;

import com.reka.lakatos.searchbyisbn.document.Book;
import com.reka.lakatos.searchbyisbn.document.CoverType;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DefaultISBNPropertyUpdatingStrategy implements PropertyUpdatingStrategy {

    private static final String ISBN13_REGEX = "((?:[\\dX]{13})|(?:[\\d\\-\\sX]{17}))";
    private static final String ISBN10_REGEX = "((?:[\\dX]{10})|(?:[\\d\\-\\sX]{13}))";

    @Override
    public void updateProperty(Book book, String property) {
        setISBN(book, property);
        setBasicCoverType(property, book);
    }

    private void setISBN(Book book, String property) {
        Optional<String> isbn13 = findISBN(property, ISBN13_REGEX);
        isbn13.ifPresent(book::setIsbn);
        if (isbn13.isEmpty()) {
            Optional<String> isbn10 = findISBN(property, ISBN10_REGEX);
            isbn10.ifPresent(book::setIsbn);
        }
    }

    private Optional<String> findISBN(final String property, final String ISBNRegex) {
        Pattern isbnPattern = Pattern.compile(ISBNRegex);
        Matcher isbnMatcher = isbnPattern.matcher(property);
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
