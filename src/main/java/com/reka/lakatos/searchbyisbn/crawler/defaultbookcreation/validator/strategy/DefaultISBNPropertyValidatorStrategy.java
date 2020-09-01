package com.reka.lakatos.searchbyisbn.crawler.defaultbookcreation.validator.strategy;

import com.reka.lakatos.searchbyisbn.crawler.defaultbookcreation.validator.PropertyValidatorStrategy;
import com.reka.lakatos.searchbyisbn.service.util.BookISBNManager;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class DefaultISBNPropertyValidatorStrategy implements PropertyValidatorStrategy {

    private final BookISBNManager bookISBNManager;

    private static final String[] HUNGARY_ISBN_STARTING_NUMBERS = {"978963", "978615", "963"};
    private static final String ISBN13_REGEX = "((?:[\\dX]{13})|(?:[\\d\\-\\sX]{17}))";
    private static final String ISBN10_REGEX = "((?:[\\dX]{10})|(?:[\\d\\-\\sX]{13}))";
    private static final int ISBN13_CLEAN_LENGTH = 13;
    private static final int ISBN10_CLEAN_LENGTH = 10;

    @Override
    public boolean validateProperty(final String property) {
        if (property == null) {
            return false;
        }
        return !containsISBNMoreThanOneBookISBN(property);
    }

    private boolean containsISBNMoreThanOneBookISBN(final String ISBNFieldValue) {
        final List<String> isbnFromISBNField = getISBNsFromISBNField(ISBNFieldValue);

        if (isbnFromISBNField.size() == 1) {
            return false;
        }

        if (isbnFromISBNField.size() > 2 || isbnFromISBNField.size() == 0) {
            return true;
        }

        if (isSameISBNLength(isbnFromISBNField, ISBN13_CLEAN_LENGTH)
                || isSameISBNLength(isbnFromISBNField, ISBN10_CLEAN_LENGTH)) {
            return true;
        }
        final String isbn13 = isbnFromISBNField.get(0);
        final String isbn10 = isbnFromISBNField.get(1);

        return !isTheSameBook(isbn13, isbn10);
    }

    private boolean isTheSameBook(String isbn13, String isbn10) {
        isbn13 = bookISBNManager.cleanISBN(isbn13);
        isbn10 = bookISBNManager.cleanISBN(isbn10);
        return bookISBNManager.convertISBNToISBN13(isbn10).equals(isbn13);
    }

    private boolean isSameISBNLength(final List<String> isbns, final int isbnLength) {
        return isbns.stream()
                .map(bookISBNManager::cleanISBN)
                .mapToInt(String::length)
                .allMatch(i -> i == isbnLength);
    }

    private List<String> getISBNsFromISBNField(String ISBNFieldValue) {
        final List<String> isbn13s = getISBNs(ISBNFieldValue, ISBN13_REGEX);

        for (String isbn13 : isbn13s) {
            ISBNFieldValue = ISBNFieldValue.replace(isbn13, "");
        }

        final List<String> isbNs10s = getISBNs(ISBNFieldValue, ISBN10_REGEX);
        return Stream.concat(isbn13s.stream(), isbNs10s.stream())
                .collect(Collectors.toList());
    }

    private List<String> getISBNs(final String ISBNFieldValue, final String ISBNRegex) {
        List<String> isbns = new ArrayList<>();

        Pattern isbnPattern = Pattern.compile(ISBNRegex);
        Matcher isbnMatcher = isbnPattern.matcher(ISBNFieldValue);
        while (isbnMatcher.find()) {
            String isbn = isbnMatcher.group();
            if (isHungaryISBN(isbn)) {
                isbns.add(isbn);
            }
        }

        return isbns;
    }

    private boolean isHungaryISBN(String ISBN) {
        for (String startingNumber : HUNGARY_ISBN_STARTING_NUMBERS) {
            if (ISBN.startsWith(startingNumber)) {
                return true;
            }
        }
        return false;
    }
}
